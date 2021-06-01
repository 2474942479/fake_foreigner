package edu.zsq.acl.controller;


import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.dto.PermissionDTO;
import edu.zsq.acl.entity.dto.RolePermissionDTO;
import edu.zsq.acl.entity.vo.PermissionVO;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-05 2020-08-29
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {


    @Resource
    private PermissionService permissionService;

    @ApiOperation("获取全部权限")
    @GetMapping("/getAllPermission")
    public JsonResult<List<PermissionVO>> getAllPermission() {
        return JsonResult.success(permissionService.getPermissionList());
    }

    @ApiOperation("获取全部权限菜单, 不包含按钮")
    @GetMapping("/getAllPermissionMenu")
    public JsonResult<List<PermissionVO>> getAllPermissionMenu() {
        return JsonResult.success(permissionService.getAllPermissionMenu());
    }

    @ApiOperation(value = "根据id获取权限信息")
    @GetMapping("/getPermissionById/{id]")
    public JsonResult<PermissionVO> getPermissionById(@PathVariable String id) {
        return JsonResult.success(permissionService.getPermissionById(id));
    }

    @ApiOperation(value = "根据角色id获取当前角色权限树")
    @PostMapping("/saveOrUpdatePermission")
    public JsonResult<Void> saveOrUpdatePermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.saveOrUpdatePermission(permissionDTO);
        return JsonResult.OK;
    }

    @ApiOperation("递归删除菜单列表")
    @DeleteMapping("/deleteAllById/{id}")
    public JsonResult<Void> deleteAllById(@PathVariable String id) {
        permissionService.deleteAllById(id);
        return JsonResult.OK;
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/assignRolePermission")
    public JsonResult<Void> assignRolePermission(@RequestBody RolePermissionDTO rolePermissionDTO) {
        permissionService.saveRolePermission(rolePermissionDTO);
        return JsonResult.OK;
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("getRolePermission/{roleId}")
    public JsonResult<List<PermissionVO>> getRolePermission(@PathVariable String roleId) {
        return JsonResult.success(permissionService.getRolePermission(roleId));
    }


}

