package edu.zsq.cms.entity.dto;

import edu.zsq.cms.entity.EduCourse;
import edu.zsq.utils.page.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQueryDTO extends PageData<EduCourse> {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "课程类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
