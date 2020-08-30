package edu.zsq.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.vo.CourseQueryVo;
import edu.zsq.cms.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
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
     * @param page
     * @param courseQuery
     * @return
     */
    Map<String, Object> getCourseListByQuery(Page<EduCourse> page, CourseQueryVo courseQuery);

    /**
     * 获取课程详情页面所有信息
     * @param courseId 课程id
     * @return
     */
    CourseWebVo getCourseBaseInfo(String courseId);
}
