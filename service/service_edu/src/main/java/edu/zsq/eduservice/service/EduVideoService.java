package edu.zsq.eduservice.service;

import edu.zsq.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     *  根据课程id查询所有小节
     * @param courseId 课程id
     * @return
     */
    List<EduVideo> getAllVideoByCourseId(String courseId);

    /**
     * 根据课程id批量删除小节
     * @param courseId
     * @return
     */
    boolean removeVideoByCourseId(String courseId);

    /**
     *
     * 根据小节id删除小节并通过nacos调用service_vod服务的删除阿里云上的视频
     * @param id
     * @return
     */
    boolean removeVideoAndVodById(String id);
}
