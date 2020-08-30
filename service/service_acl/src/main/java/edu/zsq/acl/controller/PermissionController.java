package edu.zsq.acl.controller;


import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@RestController
@RequestMapping("admin/acl/permission")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;
    /**
     * 获取所有的菜单
     * @return
     */
    @GetMapping("/getAllPermission")
    public MyResultUtils getAllPermission(){

        List<PermissionTree> permissionList =  permissionService.getPermissionList();

        return MyResultUtils.ok().data("permissionList",permissionList);
    }

    @DeleteMapping("/deleteAllById/{id}")
    public MyResultUtils deleteAllById(@PathVariable String id){
        ArrayList<String> permissionIds =new ArrayList<>();
        permissionIds.add(id);
//        根据父类id  递归获取到所有的子类id 并放进list集合以便批量删除
        permissionService.getPermissionIds(id,permissionIds);
        boolean b = permissionService.removeByIds(permissionIds);
        return MyResultUtils.ok().message("删除成功,以全部删除！");
    }


}

