package edu.zsq.user.controller;


import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.user.entity.dto.UserDTO;
import edu.zsq.user.entity.vo.UserVO;
import edu.zsq.user.service.UserService;
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
    public JsonResult<String> updateUser(@RequestBody UserDTO userDTO) {
        return JsonResult.success(userService.updateUser(userDTO));
    }

}

