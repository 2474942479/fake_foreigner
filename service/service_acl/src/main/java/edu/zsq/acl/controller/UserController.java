package edu.zsq.acl.controller;


import edu.zsq.acl.entity.dto.UserDTO;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @GetMapping("/get/{id}")
    public JsonResult<UserVO> getUser(@PathVariable String id) {
        return JsonResult.success(userService.getUser(id));
    }

    @ApiOperation(value = "获取管理用户分页列表")
    @PostMapping("/pageUser")
    public JsonResult<PageData<UserVO>> pageUser(@RequestBody UserQueryDTO userQueryDTO) {
        return JsonResult.success(userService.pageUser(userQueryDTO));
    }


    @ApiOperation(value = "添加或修改管理用户")
    @PostMapping("saveOrUpdateUserInfo")
    public JsonResult<Void> saveOrUpdateUserInfo(@RequestBody UserDTO userDTO) {
        userService.saveOrUpdateUserInfo(userDTO);
        return JsonResult.success();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public JsonResult<Void> remove(@PathVariable String id) {

        if (!userService.removeById(id)) {
            throw ExFactory.throwSystem("系统错误, 删除用户失败！");
        }
        return JsonResult.success();
    }

    @ApiOperation(value = "根据用户id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public JsonResult<Void> batchRemove(@RequestBody List<String> ids) {
        userService.batchRemove(ids);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public JsonResult toAssign(@PathVariable String userId) {
        return JsonResult.success(roleService.findRoleByUserId(userId));
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public JsonResult doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId, roleId);
        return JsonResult.success();
    }


}

