package edu.zsq.acl.controller;


import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.dto.PermissionDTO;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.entity.vo.PermissionVO;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 获取所有的菜单
     *
     * @return
     */
    @GetMapping("/getAllPermission")
    public JsonResult<List<PermissionVO>> getAllPermission() {
        return JsonResult.success(permissionService.getPermissionList());
    }

    /**
     * 递归删除菜单列表
     *
     * @param id
     * @return
     */

    @DeleteMapping("/deleteAllById/{id}")
    public JsonResult<Void> deleteAllById(@PathVariable String id) {
        permissionService.deleteAllById(id);
        return JsonResult.OK;
    }


    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public JsonResult<Void> doAssign(String roleId, List<String> permissionIds) {
        permissionService.saveRolePermission(roleId, permissionIds);
        return JsonResult.OK;
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public JsonResult<List<PermissionVO>> toAssign(@PathVariable String roleId) {
        return JsonResult.success(permissionService.selectAllMenu(roleId));
    }


    /**
     * 添加菜单
     *
     * @param permission 权限
     * @return
     */
        @PostMapping("/savePermission")
    public JsonResult<Void> savePermission(@RequestBody Permission permission) {
        if (!permissionService.save(permission)) {
            throw ExFactory.throwSystem("系统异常，添加失败");
        }
        return JsonResult.OK;
    }


    @PutMapping("/updatePermission")
    public JsonResult<Void> updatePermission(@RequestBody Permission permission) {
        if (!permissionService.updateById(permission)) {
            throw ExFactory.throwSystem("系统异常，修改菜单失败");
        }
        return JsonResult.OK;
    }

}

