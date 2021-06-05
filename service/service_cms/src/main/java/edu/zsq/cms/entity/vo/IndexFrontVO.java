package edu.zsq.cms.entity.vo;

import edu.zsq.cms.entity.EduBanner;
import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.EduTeacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhangsongqi
 * @date 6:30 下午 2021/4/18
 */

@Data
public class IndexFrontVO {

    @ApiModelProperty(value = "广告图")
    private List<EduBanner> bannerList;

    @ApiModelProperty(value = "课程列表")
    private List<EduCourse> courseList;

    @ApiModelProperty(value = "讲师列表")
    private List<EduTeacher> teacherList;
}
