package edu.zsq.acl.service;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.dto.PermissionDTO;
import edu.zsq.acl.entity.dto.RolePermissionDTO;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.entity.vo.PermissionVO;
import edu.zsq.acl.entity.vo.RouterVO;

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
     *
     * @return 菜单列表
     */
    List<PermissionVO> getPermissionList();

    /**
     * 获取全部菜单信息、不包含按钮
     *
     * @return 菜单列表
     */
    List<PermissionVO> getAllPermissionMenu();


    /**
     * 根据id查询权限详情信息
     *
     * @param id id
     * @return 权限信息
     */
    PermissionVO getPermissionById(String id);

    /**
     * 添加权限菜单
     *
     * @param permissionDTO 菜单信息
     */
    void saveOrUpdatePermission(PermissionDTO permissionDTO);

    /**
     * 递归删除该id下的所有权限
     *
     * @param id 菜单id
     */
    void deleteAllById(String id);

    /**
     * 根据用户id获取用户菜单
     *
     * @param id 用户id
     * @return 菜单列表
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id获取用户权限
     *
     * @param id 用户id
     * @return 用户权限
     */
    List<RouterVO> getMenuByUserId(String id);

    /**
     * 给角色分配权限
     *
     * @param rolePermissionDTO 角色权限DTO
     */
    void saveRolePermission(RolePermissionDTO rolePermissionDTO);

    /**
     * 根据角色id获取当前角色权限树
     *
     * @param roleId 角色id
     * @return 权限树
     */
    List<PermissionVO> getRolePermission(String roleId);
}
