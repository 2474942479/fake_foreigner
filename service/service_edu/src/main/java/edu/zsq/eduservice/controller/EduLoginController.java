package edu.zsq.eduservice.controller;

import edu.zsq.eduservice.entity.vo.UserInfoVO;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.*;


/**
 * @author 张
 */

@RestController
@RequestMapping(value = "/eduService/user")
public class EduLoginController {

    @PostMapping("/login")
    public JsonResult<String> login() {
        return JsonResult.success("admin");
    }

    @GetMapping("/info")
    public JsonResult<UserInfoVO> info() {
        return JsonResult.success(UserInfoVO.builder().name("admin").roles("admin").avatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").build());
    }


}
