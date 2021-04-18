package edu.zsq.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.mapper.EduVideoMapper;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.eduservice.utils.VodClient;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private VodClient vodClient;

    /**
     * 根据课程id查询所有小节
     *
     * @param courseId 课程id
     * @return
     */
    @Override
    public List<EduVideo> getAllVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> chapterList = baseMapper.selectList(videoWrapper);

        return chapterList;
    }

    /**
     * 根据课程id删除小节和视频
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean removeVideoByCourseId(String courseId) {

//        批量删除视频
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
//        查询指定字段 优化数据库查询
        videoWrapper.select("video_source_id");
//        根据章节id获取到所有小节信息
        List<EduVideo> videoList = baseMapper.selectList(videoWrapper);

//        遍历删除掉小节中所有的阿里云视频
        ArrayList<String>  vodIdList= new ArrayList<>();

        for (EduVideo video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodIdList.add(videoSourceId);
            }
        }
        if (vodIdList.size()>0){
            //        根据多个视频id批量删除
            vodClient.removeVodList(vodIdList);
        }

        int delete = baseMapper.delete(videoWrapper);
        return delete > 0;
    }

    /**
     * 根据小节id删除小节并通过nacos调用service_vod服务的删除阿里云上的视频
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeVideoAndVodById(String id) {

//      1  根据小节id查询获取视频id 然后调用vod服务删除视频
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
//        判断是否有视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            JsonResult myResultUtils = vodClient.removeVod(videoSourceId);
            if (myResultUtils.getCode()!= 20001){
                //      2  删除小节信息
                int delete = baseMapper.deleteById(id);
                return delete > 0;
            }
        }
        return false;
    }
}
