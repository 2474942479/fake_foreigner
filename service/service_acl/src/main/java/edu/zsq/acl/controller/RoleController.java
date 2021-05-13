package edu.zsq.acl.controller;


import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.dto.RoleQueryDTO;
import edu.zsq.acl.entity.vo.RoleVO;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserRoleService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @ApiOperation(value = "获取角色分页列表")
    @PostMapping("/pageRole")
    public JsonResult<PageData<RoleVO>> pageRole(@RequestBody RoleQueryDTO roleQueryDTO) {
        return JsonResult.success(roleService.pageRole(roleQueryDTO));
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public JsonResult<RoleVO> get(@PathVariable String id) {
        return JsonResult.success(roleService.getRoleVO(id));
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public JsonResult<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return JsonResult.OK;
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public JsonResult<Void> updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return JsonResult.OK;
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public JsonResult<Void> remove(@PathVariable String id) {
        roleService.removeRole(id);
        return JsonResult.OK;
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public JsonResult<Void> batchRemove(@RequestBody List<String> idList) {
        long count = idList.parallelStream().map(this::remove).count();
        return JsonResult.OK;
    }


}

