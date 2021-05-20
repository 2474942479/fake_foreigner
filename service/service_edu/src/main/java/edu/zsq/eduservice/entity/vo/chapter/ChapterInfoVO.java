package edu.zsq.eduservice.entity.vo.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 张
 */
@Data
@Builder
public class ChapterInfoVO {
    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;
}
