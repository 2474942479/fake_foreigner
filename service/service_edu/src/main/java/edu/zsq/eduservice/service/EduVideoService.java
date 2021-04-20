package edu.zsq.eduservice.service;

import edu.zsq.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.dto.VideoDTO;
import edu.zsq.eduservice.entity.vo.EduVideoVO;
import edu.zsq.eduservice.entity.vo.chapter.VideoVO;
import edu.zsq.utils.result.JsonResult;

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
     * 添加小节
     *
     * @param videoDTO 小节信息
     * @return 添加结果
     */
    JsonResult<Void> saveVideo(VideoDTO videoDTO);

    /**
     *  根据课程id查询所有小节
     * @param courseId 课程id
     * @return 小节信息
     */
    List<VideoVO> getAllVideoByCourseId(String courseId);

    /**
     * 根据课程id 批量删除小节
     * @param courseId 课程id
     * @return 删除结果
     */
    boolean removeVideoByCourseId(String courseId);

    /**
     * 删除小节并调用service_vod服务的删除阿里云上的视频
     *
     * @param id 小节id
     * @return 删除结果
     */
    JsonResult<Void> removeVideoAndVodById(String id);

    /**
     * 更新小节
     *
     * @param videoDTO 小节信息
     * @return 更新结果
     */
    JsonResult<Void> updateVideo(VideoDTO videoDTO);

    /**
     * 根据id获取小节信息
     *
     * @param id 小节id
     * @return 小节信息
     */
    EduVideoVO getVideo(String id);
}
