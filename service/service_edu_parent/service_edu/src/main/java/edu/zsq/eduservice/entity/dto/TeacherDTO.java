package edu.zsq.eduservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangsongqi
 * @date 10:57 上午 2021/4/19
 */
@Data
public class TeacherDTO {

    @ApiModelProperty(value = "讲师ID")
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
