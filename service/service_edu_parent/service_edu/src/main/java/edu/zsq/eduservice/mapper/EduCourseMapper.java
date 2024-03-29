package edu.zsq.eduservice.mapper;

import edu.zsq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zsq.eduservice.entity.vo.CourseVO;
import edu.zsq.eduservice.entity.vo.FinalReleaseVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 获取最终发布信息
     * @param courseId 课程id
     * @return 最终发布信息
     */
    FinalReleaseVO getFinalReleaseVO(String courseId);

    /**
     * 根据课程id获取课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseVO getCourseById(String courseId);
}
