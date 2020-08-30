package edu.zsq.acl.controller;


import edu.zsq.acl.service.RoleService;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@RestController
@RequestMapping("admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/setRolePermission/{rid}")
    public MyResultUtils setRolePermission(@PathVariable String rid,@RequestBody String[] permissionIds ){

        boolean b = roleService.setRolePermission(rid, permissionIds);

        if (b){
            return MyResultUtils.ok().message("角色添加权限成功！");

        }else{
            return MyResultUtils.error().message("角色添加权限失败！");
        }


    }



}

