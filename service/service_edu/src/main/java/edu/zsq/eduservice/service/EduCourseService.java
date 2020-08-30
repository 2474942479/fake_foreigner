package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.vo.CourseInfoVo;
import edu.zsq.eduservice.entity.vo.CourseQuery;
import edu.zsq.eduservice.entity.vo.FinalReleaseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVo 课程基本信息
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 回显课程基本信息
     *
     * @param id 课程id
     * @return 课程基本信息VO类
     */
    CourseInfoVo getCourseInfoVoById(String id);

    /**
     * 修改课程基本信息
     *
     * @param courseInfoVo 课程基本信息VO类
     * @return
     */
    void updateCourseInfoVo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询返回最终发布信息
     * @param id
     * @return
     */
    FinalReleaseVo getFinalReleaseVo(String id);

    /**
     * 条件分页查询
     * @param page 分页条件
     * @param courseQuery   课程条件
     */
    void pageQuery(Page<EduCourse> page, CourseQuery courseQuery);

    /**
     * 根据课程id删除课程相关的所有信息(课程章节 小节 描述 课程基本信息)
     *
     * @param courseId 课程id
     * @return  返回是否全部删除
     */
    Boolean removeCourseAllById(String courseId);
}
