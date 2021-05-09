package edu.zsq.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.RolePermission;
import edu.zsq.acl.entity.UserRole;
import edu.zsq.acl.entity.dto.RoleQueryDTO;
import edu.zsq.acl.entity.vo.RoleVO;
import edu.zsq.acl.mapper.RoleMapper;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserRoleService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public PageData<RoleVO> pageRole(RoleQueryDTO roleQueryDTO) {
        Page<Role> page = new Page<>(roleQueryDTO.getCurrent(), roleQueryDTO.getSize());
        lambdaQuery()
                .like(StringUtils.isNotBlank(roleQueryDTO.getRoleName()), Role::getRoleName, roleQueryDTO.getRoleName())
                .orderByDesc(Role::getGmtModified)
                .page(page);

        if (page.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<RoleVO> collect = page.getRecords().stream().map(this::convert2RoleVO).collect(Collectors.toList());
        return PageData.of(collect, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public RoleVO getRoleVO(String id) {
        Role role = lambdaQuery()
                .eq(Role::getId, id)
                .last(Constants.LIMIT_ONE)
                .one();
        return convert2RoleVO(role);
    }

    @Override
    public List<RoleVO> getAssignedRoleInfo(String id) {
        //根据用户id拥有的角色id
        List<String> roleIds = userRoleService.lambdaQuery()
                .eq(UserRole::getUserId, id)
                .select(UserRole::getRoleId)
                .list()
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.size() == 0) {
            return Collections.emptyList();
        }
        return lambdaQuery()
                .in(Role::getId, roleIds)
                .list()
                .parallelStream()
                .map(this::convert2RoleVO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<RoleVO>> getRoleInfo(String userId) {
        //查询所有的角色
        List<RoleVO> roleList = lambdaQuery()
                .list()
                .parallelStream()
                .map(this::convert2RoleVO)
                .collect(Collectors.toList());

        Map<String, List<RoleVO>> map = new HashMap<>(2);
        map.put("assignRoleList", getAssignedRoleInfo(userId));
        map.put("roleList", roleList);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public void assignRole(String userId, List<String> roleIds) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        boolean remove = userRoleService.lambdaUpdate().eq(UserRole::getUserId, userId).remove();

        if (!remove) {
            throw ExFactory.throwSystem("服务器错误，请重新授权");
        }

        if (roleIds.isEmpty()) {
            return;
        }

        List<UserRole> userRoleList = roleIds.parallelStream()
                .map(roleId -> convert2UserRole(userId, roleId))
                .collect(Collectors.toList());

        if (!userRoleService.saveBatch(userRoleList)) {
            throw ExFactory.throwSystem("服务器错误, 请重新授权");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public void removeRole(String roleId) {
        try {
            //删除当前角色权限数据
            rolePermissionService.lambdaUpdate()
                    .eq(RolePermission::getRoleId, roleId)
                    .remove();

            //删除角色和用户得关系记录
            userRoleService.lambdaUpdate()
                    .eq(UserRole::getRoleId, roleId)
                    .remove();

            //删除角色
            removeById(roleId);
        } catch (Exception e) {
            throw ExFactory.throwSystem("删除数据失败");
        }

    }

    private RoleVO convert2RoleVO(Role role) {
        return RoleVO.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleName(role.getRoleName())
                .remark(role.getRemark())
                .build();
    }

    private UserRole convert2UserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }
}
