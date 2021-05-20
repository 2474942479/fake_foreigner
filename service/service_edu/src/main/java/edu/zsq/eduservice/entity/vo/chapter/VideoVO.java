package edu.zsq.eduservice.entity.vo.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 张
 */
@Data
@Builder
public class VideoVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "视频名称")
    private String title;

    @ApiModelProperty(value = "视频简介")
    private String description;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
