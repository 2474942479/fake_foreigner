package edu.zsq.cms.entity.vo;

import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.EduTeacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhangsongqi
 * @date 7:14 下午 2021/4/18
 */
@Data
@Builder
public class TeacherAndCoursesVO {

    @ApiModelProperty(value = "讲师信息")
    private EduTeacher teacherInfo;

    @ApiModelProperty(value = "课程列表")
    private List<EduCourse> courseList;
}
