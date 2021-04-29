package edu.zsq.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserService;
import edu.zsq.utils.page.PageDTO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public JsonResult<User> get(@PathVariable String id) {
        return JsonResult.success(userService.getById(id));
    }

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("/page")
    public JsonResult<PageData<UserVO>> index(@RequestBody UserQueryDTO userQueryDTO) {
        return JsonResult.success(userService.pageUser(userQueryDTO));
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public JsonResult save(@RequestBody User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userService.save(user);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public JsonResult updateById(@RequestBody User user) {
        userService.updateById(user);
        return JsonResult.success();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public JsonResult remove(@PathVariable String id) {
        userService.removeById(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public JsonResult batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public JsonResult toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return JsonResult.success().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public JsonResult doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return JsonResult.success();
    }


}

