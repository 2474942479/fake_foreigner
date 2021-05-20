package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.entity.dto.VideoDTO;
import edu.zsq.eduservice.entity.vo.VideoInfoVO;
import edu.zsq.eduservice.entity.vo.chapter.VideoVO;
import edu.zsq.eduservice.mapper.EduVideoMapper;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.eduservice.service.VodService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Resource
    private VodService vodService;

    @Override
    public void saveOrUpdateVideo(VideoDTO videoDTO) {
        //TODO 添加视频详细信息
        if (!saveOrUpdate(convert2EduVideo(videoDTO))) {
            throw ExFactory.throwSystem("系统异常，课程视频添加失败");
        }
    }

    /**
     * 根据课程id查询所有小节
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @Override
    public List<VideoVO> getAllVideoByCourseId(String courseId) {
        return lambdaQuery()
                .eq(EduVideo::getCourseId, courseId)
                .select(EduVideo::getId, EduVideo::getChapterId, EduVideo::getTitle, EduVideo::getVideoSourceId)
                .orderByAsc(EduVideo::getSort)
                .list()
                .parallelStream()
                .map(this::convert2VideoVO)
                .collect(Collectors.toList());
    }


    /**
     * 删除小节并调用service_vod服务的删除阿里云上的视频
     *
     * @param id 小节id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public void removeVideoAndVodById(String id) {

        // 根据小节id查询获取视频id 然后调用vod服务删除视频
        EduVideo eduVideo = lambdaQuery()
                .eq(EduVideo::getId, id)
                .select(EduVideo::getId, EduVideo::getVideoSourceId)
                .last(Constants.LIMIT_ONE)
                .one();

        if (!removeById(id)) {
            throw ExFactory.throwSystem("服务器异常, 删除小节失败");
        }

        // 判断是否有视频
        if (StringUtils.isNotBlank(eduVideo.getVideoSourceId())
                && !vodService.removeVod(eduVideo.getVideoSourceId())) {
            throw ExFactory.throwSystem("阿里云删除视频失败");
        }
    }

    /**
     * 根据课程id 批量删除小节和视频
     *
     * @param courseId 课程ID
     * @return 删除结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean removeVideoByCourseId(String courseId) {

        List<EduVideo> videoList = lambdaQuery()
                .eq(EduVideo::getCourseId, courseId)
                .select(EduVideo::getId, EduVideo::getVideoSourceId)
                .list();

//        遍历获取要删除掉的小节中所有的阿里云视频id
        List<String> vodSourceIds = videoList.stream()
                .map(EduVideo::getVideoSourceId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        List<String> ids = videoList.stream()
                .map(EduVideo::getId)
                .collect(Collectors.toList());

        if (!ids.isEmpty() && !removeByIds(ids)) {
            throw ExFactory.throwSystem("系统异常，删除失败");
        }

        // 根据多个视频id批量删除
        if (!vodSourceIds.isEmpty() && !vodService.removeVodList(vodSourceIds)) {
            throw ExFactory.throwSystem("阿里云删除视频失败");
        }

        return true;
    }


    @Override
    public VideoInfoVO getVideoInfo(String id) {
        EduVideo eduVideo = lambdaQuery()
                .eq(EduVideo::getId, id)
                .last(Constants.LIMIT_ONE)
                .one();
        return convert2VideoInfoVO(eduVideo);
    }

    private EduVideo convert2EduVideo(VideoDTO videoDTO) {
        EduVideo eduVideo = new EduVideo();
        eduVideo.setId(videoDTO.getId());
        eduVideo.setChapterId(videoDTO.getChapterId());
        eduVideo.setDuration(videoDTO.getDuration());
        eduVideo.setCourseId(videoDTO.getCourseId());
        eduVideo.setVideoSourceId(videoDTO.getVideoSourceId());
        eduVideo.setCourseId(videoDTO.getCourseId());
        eduVideo.setIsFree(videoDTO.getIsFree());
        eduVideo.setPlayCount(videoDTO.getPlayCount());
        eduVideo.setSize(videoDTO.getSize());
        eduVideo.setSort(videoDTO.getSort());
        eduVideo.setStatus(videoDTO.getStatus());
        eduVideo.setTitle(videoDTO.getTitle());
        eduVideo.setDescription(videoDTO.getDescription());
        eduVideo.setVideoOriginalName(videoDTO.getVideoOriginalName());
        return eduVideo;
    }

    private VideoInfoVO convert2VideoInfoVO(EduVideo eduVideo) {
        return VideoInfoVO.builder()
                .id(eduVideo.getId())
                .chapterId(eduVideo.getChapterId())
                .title(eduVideo.getTitle())
                .description(eduVideo.getDescription())
                .duration(eduVideo.getDuration())
                .videoSourceId(eduVideo.getVideoSourceId())
                .isFree(eduVideo.getIsFree())
                .playCount(eduVideo.getPlayCount())
                .sort(eduVideo.getSort())
                .videoOriginalName(eduVideo.getVideoOriginalName())
                .courseId(eduVideo.getCourseId())
                .status(eduVideo.getStatus())
                .size(eduVideo.getSize())
                .build();
    }

    private VideoVO convert2VideoVO(EduVideo eduVideo) {
        return VideoVO.builder()
                .id(eduVideo.getId())
                .title(eduVideo.getTitle())
                .chapterId(eduVideo.getChapterId())
                .description(eduVideo.getDescription())
                .videoSourceId(eduVideo.getVideoSourceId())
                .build();
    }
}
