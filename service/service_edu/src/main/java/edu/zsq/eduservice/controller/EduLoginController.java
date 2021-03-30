package edu.zsq.eduservice.controller;

import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;


/**
 * @author 张
 *
 * 跨域问题 协议 ://ip地址: 端口号  任意一个不一样都存在跨域错误
 * 解决跨越问题方法一 加@CrossOrigin   方法二nginx反向代理  方法三 网关跨域类 不能与@CrossOrigin 共用
 */

@RestController
@RequestMapping(value = "/eduService/user")
public class EduLoginController {

    @PostMapping("/login")
    public JsonResult login() {
        return JsonResult.success().data("token", "admin");
    }

    @GetMapping("/info")
    public JsonResult info() {
        return JsonResult.success().data("roles", "admin").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
