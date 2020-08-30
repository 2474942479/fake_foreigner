package edu.zsq.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.RolePermission;
import edu.zsq.acl.mapper.RoleMapper;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Override
    public boolean setRolePermission(String rid, String[] permissionIds) {

        ArrayList<RolePermission> rolePermissions = new ArrayList<>();

        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(rid);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }

        boolean b = rolePermissionService.saveBatch(rolePermissions);

        return b;
    }
}
