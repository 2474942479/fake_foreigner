package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.entity.dto.ChapterDTO;
import edu.zsq.eduservice.entity.vo.chapter.ChapterInfoVO;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.eduservice.entity.vo.chapter.VideoVO;
import edu.zsq.eduservice.mapper.EduChapterMapper;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    EduVideoService videoService;

    @Override
    public void saveOrUpdateChapter(ChapterDTO chapterDTO) {
        if (!saveOrUpdate(convert2EduChapter(chapterDTO))) {
            throw ExFactory.throwSystem("系统错误, 操作失败！");
        }
    }

    /**
     * 课程大纲列表
     *
     * @param courseId 课程id
     * @return 课程大纲list集合
     */
    @Override
    public List<ChapterVO> getAllChapterVO(String courseId) {

        // 查询并获取章节所有信息
        List<EduChapter> chapterList = lambdaQuery()
                .eq(EduChapter::getCourseId, courseId)
                .orderByAsc(EduChapter::getSort)
                .list();

        // 查询并获得小节所有信息
        return chapterList.parallelStream()
                .map(eduChapter -> buildChapterVO(eduChapter, videoService.getAllVideoByCourseId(courseId)))
                .collect(Collectors.toList());

    }

    /**
     * 删除章节  当有小节是不允许删除
     *
     * @param chapterId 章节id
     */
    @Override
    public void deleteChapter(String chapterId) {

        Integer count = videoService.lambdaQuery()
                .eq(EduVideo::getChapterId, chapterId)
                .count();

        if (count != 0) {
            throw ExFactory.throwBusiness("请先删除掉该章节下的所有小节!");
        }

        if (!removeById(chapterId)) {
            throw ExFactory.throwBusiness("系统错误, 章节删除失败！");
        }

    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {

        boolean result = lambdaQuery()
                .eq(StringUtils.isNotBlank(courseId), EduChapter::getCourseId, courseId)
                .list()
                .isEmpty();

        boolean remove = lambdaUpdate()
                .eq(StringUtils.isNotBlank(courseId), EduChapter::getCourseId, courseId)
                .remove();
        return result || remove;
    }

    @Override
    public ChapterInfoVO getChapterInfo(String id) {
        EduChapter eduChapter = lambdaQuery().eq(EduChapter::getId, id).last(Constants.LIMIT_ONE).one();
        return convert2ChapterInfoVO(eduChapter);
    }

    private ChapterInfoVO convert2ChapterInfoVO(EduChapter eduChapter) {
        return ChapterInfoVO.builder()
                .id(eduChapter.getId())
                .courseId(eduChapter.getCourseId())
                .sort(eduChapter.getSort())
                .description(eduChapter.getDescription())
                .title(eduChapter.getTitle())
                .build();
    }

    private EduChapter convert2EduChapter(ChapterDTO chapterDTO) {
        EduChapter eduChapter = new EduChapter();
        eduChapter.setId(chapterDTO.getId());
        eduChapter.setCourseId(chapterDTO.getCourseId());
        eduChapter.setDescription(chapterDTO.getDescription());
        eduChapter.setTitle(chapterDTO.getTitle());
        eduChapter.setSort(chapterDTO.getSort());
        return eduChapter;
    }

    private ChapterVO buildChapterVO(EduChapter eduChapter, List<VideoVO> videoList) {
        List<VideoVO> videoVOList = videoList.parallelStream()
                .filter(videoVO -> eduChapter.getId().equals(videoVO.getChapterId()))
                .collect(Collectors.toList());

        return ChapterVO.builder()
                .id(eduChapter.getId())
                .title(eduChapter.getTitle())
                .description(eduChapter.getDescription())
                .videoVOList(videoVOList)
                .build();
    }
}
