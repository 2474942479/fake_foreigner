package edu.zsq.acl.service;

import edu.zsq.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface RoleService extends IService<Role> {

    /**
     * 给角色分配权限
     * @param rid
     * @param permissionIds
     * @return
     */
    boolean setRolePermission(String rid, String[] permissionIds);
}
