package edu.zsq.acl.service;

import edu.zsq.acl.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 批量删除用户对应的角色
     *
     * @param userIds 用户id
     */
    void batchRemove(List<String> userIds);
}
