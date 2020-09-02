package edu.zsq.acl.service;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.vo.PermissionTree;

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
    List<PermissionTree> getPermissionList();

    /**
     * 通过id 递归获取到该id下的所有子id 并放进list集合
     * @param id
     * @param permissionIds
     * @return
     */
    void getPermissionIds(String id, ArrayList<String> permissionIds);

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     *
     * @param id
     * @return
     */
    List<JSONObject> selectPermissionByUserId(String id);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionId
     */
    void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionId);

    /**
     *根据角色获取菜单
     * @param roleId
     * @return
     */
    List<Permission> selectAllMenu(String roleId);
}
