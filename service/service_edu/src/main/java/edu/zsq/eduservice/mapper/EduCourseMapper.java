package edu.zsq.eduservice.mapper;

import edu.zsq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zsq.eduservice.entity.vo.FinalReleaseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 获取最终发布信息
     * @param courseId 课程id
     * @return
     */
    public FinalReleaseVo getFinalReleaseVo(String courseId);
}
