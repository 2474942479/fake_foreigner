package edu.zsq.cms.mapper;

import edu.zsq.cms.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zsq.cms.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 获取课程详情页面所有信息
     * @param courseId 课程id
     * @return
     */
    CourseWebVo getCourseBaseInfo(String courseId);
}
