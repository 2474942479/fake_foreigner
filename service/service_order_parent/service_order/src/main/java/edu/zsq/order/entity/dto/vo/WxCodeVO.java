package edu.zsq.order.entity.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 张
 */
@Data
@Builder
public class WxCodeVO {

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "应付金额（分）")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "二维码操作状态")
    private String resultCode;

    @ApiModelProperty(value = "二维码地址")
    private String codeUrl;
}
