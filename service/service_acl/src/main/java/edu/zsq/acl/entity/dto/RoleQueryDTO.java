package edu.zsq.acl.entity.dto;

import edu.zsq.acl.entity.Role;
import edu.zsq.utils.page.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryDTO extends PageData<Role> {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
