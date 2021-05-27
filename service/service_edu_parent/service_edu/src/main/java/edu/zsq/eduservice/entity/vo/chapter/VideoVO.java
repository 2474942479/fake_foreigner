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

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "视频名称")
    private String title;

    @ApiModelProperty(value = "视频简介")
    private String description;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "原始文件名称")
    private String videoOriginalName;

    @ApiModelProperty(value = "是否可以试听：0收费 1免费")
    private Boolean isFree;

    @ApiModelProperty(value = "视频时长（秒）")
    private Float duration;

    @ApiModelProperty(value = "视频源文件大小（字节）")
    private Long size;

    @ApiModelProperty(value = "Empty未上传 Transcoding转码中  Normal正常")
    private String status;
}
