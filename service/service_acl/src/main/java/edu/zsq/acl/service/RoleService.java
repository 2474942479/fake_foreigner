package edu.zsq.acl.service;

import edu.zsq.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户获取角色数据
     * @param id
     * @return
     */
    List<Role> selectRoleByUserId(String id);

    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     * @param userId
     * @param roleId
     */
    void saveUserRoleRealtionShip(String userId, String[] roleId);
}
