package edu.zsq.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.dto.CourseQueryDTO;
import edu.zsq.cms.entity.vo.CourseAllInfoVO;
import edu.zsq.cms.entity.vo.CourseListVO;
import edu.zsq.utils.page.PageData;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 根据讲师id查询讲师所讲的课程 按更新时间进行倒序排序
     * @param id
     * @return
     */
    List<EduCourse> getCourseByTeacherId(String id);

    /**
     * 根据条件对课程进行分页查询
     *
     * @param courseQueryDTO 查询条件
     * @return 分页数据
     */
    PageData<CourseListVO> getCourseListByQuery(CourseQueryDTO courseQueryDTO);


    /**
     * 获取课程详情页面所有信息
     *
     * @param userId 用户id
     * @param courseId 课程id
     * @return 所有信息
     */
    CourseAllInfoVO getCourseInfo(String userId, String courseId);
}
