package edu.zsq.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zsq.acl.entity.UserRole;
import edu.zsq.acl.mapper.UserRoleMapper;
import edu.zsq.acl.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.utils.exception.core.ExFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public void batchRemove(List<String> userIds) {

        List<String> ids = new ArrayList<>();

        for (String userId : userIds) {
            List<UserRole> list = lambdaQuery()
                    .eq(UserRole::getUserId, userId)
                    .select(UserRole::getId)
                    .list();
            for (UserRole userRole : list) {
                ids.add(userRole.getId());
            }
        }

        if (!removeByIds(ids)) {
            throw ExFactory.throwSystem("系统异常, 删除用户对应角色失败");
        }
    }
}
