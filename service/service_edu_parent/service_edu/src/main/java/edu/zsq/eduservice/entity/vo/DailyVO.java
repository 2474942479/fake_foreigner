package edu.zsq.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 张
 */
@Data
@Builder
public class DailyVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "统计日期")
    private String dateCalculated;

    @ApiModelProperty(value = "注册人数")
    private Integer registerNum;

    @ApiModelProperty(value = "登录人数")
    private Integer loginNum;

    @ApiModelProperty(value = "每日播放视频数")
    private Integer videoViewNum;

    @ApiModelProperty(value = "每日新增课程数")
    private Integer courseNum;
}
