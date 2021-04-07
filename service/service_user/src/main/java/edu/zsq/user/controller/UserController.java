package edu.zsq.user.controller;


import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        return userService.login(loginDTO);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public JsonResult<Void> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public JsonResult<User> getUserInfo(HttpServletRequest request) {
        return JsonResult.success(userService.getById(JwtUtils.getMemberIdByJwtToken(request)));
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/getUserInfoById/{userId}")
    public JsonResult<User> getUserInfoById(@PathVariable String userId) {
        return JsonResult.success(userService.getById(userId));
    }


    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     *
     * @param day
     * @return
     */
    @GetMapping("/getRegisterNumber/{day}")
    public JsonResult<Integer> getRegisterNumber(@PathVariable String day) {
        return JsonResult.success(userService.getRegisterNumber(day));
    }

}

