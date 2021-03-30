package edu.zsq.user.controller;


import edu.zsq.user.entity.User;
import edu.zsq.user.entity.vo.LoginVo;
import edu.zsq.user.entity.vo.RegisterVo;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginVo loginVo) {

        String token = userService.login(loginVo);
        return JsonResult.success().data("token", token).message("登录成功");
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public JsonResult register(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return JsonResult.success().message("注册成功");
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public JsonResult getUserInfo(HttpServletRequest request) {

        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) {
            return JsonResult.failure().message("请先登录");
        }
        User userInfo = userService.getById(userId);
        return JsonResult.success().data("userInfo", userInfo);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/getUserInfoById/{userId}")
    public User getUserInfoById(@PathVariable String userId) {
        User user = userService.getById(userId);
        return user;
    }


    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     *
     * @param day
     * @return
     */
    @GetMapping("/getRegisterNumber/{day}")
    public Integer getRegisterNumber(@PathVariable String day) {
        Integer count = userService.getRegisterNumber(day);
        return count;
    }

}

