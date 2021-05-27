package edu.zsq.eduservice.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangsongqi
 * @date 4:56 下午 2021/4/8
 */
@Data
public class ChapterDTO {
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
