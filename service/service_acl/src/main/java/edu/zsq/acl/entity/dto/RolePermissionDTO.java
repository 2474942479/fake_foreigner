package edu.zsq.acl.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张
 */
@Data
public class RolePermissionDTO {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id列表")
    private String permissionIds;
}
