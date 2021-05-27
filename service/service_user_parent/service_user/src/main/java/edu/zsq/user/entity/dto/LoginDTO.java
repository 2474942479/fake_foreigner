package edu.zsq.user.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张
 */
@Data
public class LoginDTO {


    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
