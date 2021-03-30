package edu.zsq.acl.controller;


import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;
    /**
     * 获取所有的菜单
     * @return
     */
    @GetMapping("/getAllPermission")
    public JsonResult getAllPermission(){

        List<PermissionTree> permissionList =  permissionService.getPermissionList();

        return JsonResult.success().data("permissionList",permissionList);
    }

    /**
     * 递归删除菜单列表
     * @param id
     * @return
     */

    @DeleteMapping("/deleteAllById/{id}")
    public JsonResult deleteAllById(@PathVariable String id){
        ArrayList<String> permissionIds =new ArrayList<>();
        permissionIds.add(id);
//        根据父类id  递归获取到所有的子类id 并放进list集合以便批量删除
        permissionService.getPermissionIds(id,permissionIds);
        permissionService.removeByIds(permissionIds);
        return JsonResult.success().message("删除成功,以全部删除！");
    }


    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public JsonResult doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public JsonResult toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return JsonResult.success().data("children", list);
    }



    /**
     * 添加菜单
     * @param permission
     * @return
     */
    @PostMapping("/savePermission")
    public JsonResult savePermission(@RequestBody Permission permission){
        boolean save = permissionService.save(permission);
        if (save){
            return JsonResult.success().message("添加菜单成功");
        }else {
            return JsonResult.failure().message("添加菜单失败");
        }
    }


    @PutMapping("/updatePermission")
    public JsonResult updatePermission(@RequestBody Permission permission){

        boolean update = permissionService.updateById(permission);
        if (update){
            return JsonResult.success().message("修改菜单成功");
        }else {
            return JsonResult.failure().message("修改菜单失败");
        }
    }


}

