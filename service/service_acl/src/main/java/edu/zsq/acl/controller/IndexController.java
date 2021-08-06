package edu.zsq.acl.controller;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.service.IndexService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    public JsonResult<UserInfoVO> info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return JsonResult.success(indexService.getUserInfo(username));
    }

    /**
     *根据username 获取菜单
     * @return 菜单列表
     */
    @GetMapping("/menu")
    public JsonResult<List<JSONObject>> getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return JsonResult.success(indexService.getMenu(username));
    }

    @PostMapping("/logout")
    public JsonResult<Void> logout(){
        return JsonResult.success();
    }

}
