package edu.zsq.acl.service;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.entity.vo.PermissionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 查询所有的菜单
     * @return
     */
    List<PermissionVO> getPermissionList();

    /**
     * 递归删除该id下的所有权限
     * @param id id
     */
    void deleteAllById(String id);

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 获取管理员权限
     * @return 所有权限
     */
    List<JSONObject> selectAdminPermission();

    /**
     *根据用户id获取用户权限
     *
     * @param id 用户id
     * @return 用户权限
     */
    List<JSONObject> selectPermissionByUserId(String id);

    /**
     * 给角色分配权限
     * @param roleId 角色id
     * @param permissionIds 权限id列表
     */
    void saveRolePermission(String roleId, List<String> permissionIds);

    /**
     *根据角色获取菜单
     * @param roleId 角色id
     * @return 菜单数据
     */
    List<PermissionVO> selectAllMenu(String roleId);
}
