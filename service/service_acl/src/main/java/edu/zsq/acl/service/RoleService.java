package edu.zsq.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.dto.RoleQueryDTO;
import edu.zsq.acl.entity.vo.RoleVO;
import edu.zsq.utils.page.PageData;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页获取角色信息
     *
     * @param roleQueryDTO 分页参数
     * @return 分页结果
     */
    PageData<RoleVO> pageRole(RoleQueryDTO roleQueryDTO);

    /**
     * 根据角色id获取角色信息
     *
     * @param id 角色id
     * @return 角色信息
     */
    RoleVO getRoleVO(String id);

    /**
     * 根据用户获取角色数据
     *
     * @param id 用户id
     * @return 用户角色
     */
    List<RoleVO> getAssignedRoleInfo(String id);

    /**
     * 根据用户获取角色数据
     *
     * @param userId 用户id
     * @return 角色信息
     */
    Map<String, List<RoleVO>> getRoleInfo(String userId);

    /**
     * 根据用户分配角色
     *
     * @param userId 用户ud
     * @param roleId 角色id
     */
    void assignRole(String userId, List<String> roleId);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    void removeRole(String roleId);
}
