package edu.zsq.order.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.zsq.utils.page.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryDTO extends PageDTO {

    @ApiModelProperty(value = "查询条件key")
    private String queryKey;

    @ApiModelProperty(value = "查询条件值")
    private String queryValue;

    @ApiModelProperty("订单状态")
    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询开始时间", example = "2021-01-01 10:10:10")
    private LocalDateTime begin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查询结束时间", example = "2021-12-01 10:10:10")
    private LocalDateTime end;
}
