package edu.zsq.acl.controller;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.service.IndexService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("info")
    public JsonResult<UserInfoVO> info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return JsonResult.success(indexService.getUserInfo(username));
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public JsonResult getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return JsonResult.success().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public JsonResult logout(){
        return JsonResult.success();
    }

}
