package edu.zsq.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zsq.acl.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    /**
     * 获取所有权限
     * @return 所有权限值
     */
    List<String> selectAllPermissionValue();

    /**
     * 根据id获取权限列表
     * @param id 用户id
     * @return 权限列表
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id获取对应权限
     * @param userId 用户id
     * @return 用户权限
     */
    List<Permission> selectPermissionByUserId(String userId);
}
