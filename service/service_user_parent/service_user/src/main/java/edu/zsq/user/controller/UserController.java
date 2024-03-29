package edu.zsq.user.controller;


import edu.zsq.user.entity.dto.*;
import edu.zsq.user.entity.vo.UserVO;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public JsonResult<String> login(@RequestBody LoginDTO loginDTO) {
        return JsonResult.success(userService.login(loginDTO));
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public JsonResult<Void> register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return JsonResult.success();
    }

    @ApiOperation("获取用户列表")
    @PostMapping("/getUserList")
    public JsonResult<PageData<UserVO>> getUserList(@RequestBody UserQueryDTO userQueryDTO) {
        return JsonResult.success(userService.getUserList(userQueryDTO));
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUserInfoByToken")
    public JsonResult<UserVO> getUserInfoByToken(HttpServletRequest request) {
        return JsonResult.success(userService.getUserInfoByToken(request));
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/getUserInfoById/{userId}")
    public JsonResult<UserVO> getUserInfoById(@PathVariable String userId) {
        return JsonResult.success(userService.getUserInfoById(userId));
    }

    @GetMapping("/getRegisterNumber/{day}")
    @ApiOperation(value = "根据日期获取这一天的注册人数  用于统计服务")
    public JsonResult<Integer> getRegisterNumber(@PathVariable LocalDate day) {
        return JsonResult.success(userService.getRegisterNumber(day));
    }

    @PostMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public JsonResult<String> updateUser(@RequestBody UserDTO userDTO) {
        return JsonResult.success(userService.updateUser(userDTO));
    }

    @ApiOperation("第三方登录用户完善用户信息")
    @PostMapping("/perfectUser")
    public JsonResult<Void> perfectUser(@RequestBody ResetDTO resetDTO) {
        userService.perfectUser(resetDTO);
        return JsonResult.success();
    }

    @ApiOperation("修改密码")
    @PostMapping("/updateUserPass")
    public JsonResult<Void> updateUserPass(@RequestBody ResetDTO resetDTO) {
        userService.updateUserPass(resetDTO);
        return JsonResult.success();
    }

    @ApiOperation("修改手机号")
    @PostMapping("/updateUserMobile")
    public JsonResult<Void> updateUserMobile(@RequestBody ResetDTO resetDTO) {
        userService.updateUserMobile(resetDTO);
        return JsonResult.success();
    }

    @ApiOperation("删除用户")
    @GetMapping("/removeUser/{id}")
    public JsonResult<Void> removeUser(@PathVariable String id) {
        userService.removeById(id);
        return JsonResult.success();
    }
}

