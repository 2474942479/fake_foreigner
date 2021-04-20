package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduCourse;
import edu.zsq.eduservice.entity.dto.query.CourseQueryDTO;
import edu.zsq.eduservice.entity.vo.CourseDTO;
import edu.zsq.eduservice.entity.vo.CourseVO;
import edu.zsq.eduservice.entity.vo.FinalReleaseVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     *
     * @param courseDTO 课程基本信息
     * @return 添加结果
     */
    JsonResult<Void> saveCourse(CourseDTO courseDTO);

    /**
     * 回显课程基本信息
     *
     * @param id 课程id
     * @return 课程基本信息VO类
     */
    CourseVO getCourseDTO(String id);

    /**
     * 修改课程基本信息
     *
     * @param courseDTO 课程基本信息V
     * @return 修改结果
     */
    JsonResult<Void> updateCourseDTO(CourseDTO courseDTO);

    /**
     * 根据课程id查询返回最终发布信息
     *
     * @param id 课程id
     * @return 最终发布信息
     */
    FinalReleaseVO getFinalReleaseVo(String id);

    /**
     * 根据课程id删除课程相关的所有信息(课程章节 小节 描述 课程基本信息)
     *
     * @param courseId 课程id
     * @return 返回是否全部删除
     */
    JsonResult<Void> removeCourseAllById(String courseId);

    /**
     * 查询所有课程
     *
     * @return 课程列表
     */
    List<CourseVO> getAllCourse();

    /**
     * 分页查询课程信息
     *
     * @param courseQueryDTO 查询条件
     * @return 课程信息
     */
    PageData<CourseVO> getCourseListPage(CourseQueryDTO courseQueryDTO);

    /**
     * 修改发布状态
     *
     * @param courseDTO 课程信息
     * @return 修改结果
     */
    JsonResult<Void> updateReleaseStatus(CourseDTO courseDTO);
}
