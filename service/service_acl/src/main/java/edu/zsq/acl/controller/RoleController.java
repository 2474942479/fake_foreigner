package edu.zsq.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.RolePermission;
import edu.zsq.acl.entity.UserRole;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserRoleService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private UserRoleService userRoleService;
    /**
     * 给角色批量分配权限
     * @param rid
     * @param permissionIds
     * @return
     */
    @PostMapping("/setRolePermission/{rid}")
    public JsonResult setRolePermission(@PathVariable String rid,@RequestBody String[] permissionIds ){

        boolean b = roleService.setRolePermission(rid, permissionIds);

        if (b){
            return JsonResult.success().message("角色添加权限成功！");

        }else{
            return JsonResult.failure().message("角色添加权限失败！");
        }


    }


    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public JsonResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return JsonResult.success().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public JsonResult get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return JsonResult.success().data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public JsonResult save(@RequestBody Role role) {
        roleService.save(role);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public JsonResult updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return JsonResult.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public JsonResult remove(@PathVariable String id) {
        //删除当前角色权限数据
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        rolePermissionService.remove(wrapper);
        //删除角色和用户得关系记录
        QueryWrapper<UserRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_id",id);
        userRoleService.remove(roleQueryWrapper);
        //删除角色
        roleService.removeById(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public JsonResult batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return JsonResult.success();
    }


}

