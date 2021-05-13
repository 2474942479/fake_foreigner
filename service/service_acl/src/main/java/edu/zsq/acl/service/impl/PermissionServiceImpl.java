package edu.zsq.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.RolePermission;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.dto.PermissionDTO;
import edu.zsq.acl.entity.dto.RolePermissionDTO;
import edu.zsq.acl.entity.vo.PermissionVO;
import edu.zsq.acl.mapper.PermissionMapper;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.UserService;
import edu.zsq.acl.utils.MenuUtil;
import edu.zsq.acl.utils.PermissionUtil;
import edu.zsq.utils.exception.core.ExFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private UserService userService;


    /**
     * 查询所有的菜单
     *
     * @return 菜单树
     */
    @Override
    public List<PermissionVO> getPermissionList() {

        List<Permission> list = lambdaQuery().list();
//        复制到VO类中
        List<PermissionVO> collect = list.stream()
                .map(this::convert2PermissionVO)
                .collect(Collectors.toList());
        return convert2PermissionTree(collect);
    }

    @Override
    public void savePermission(PermissionDTO permissionDTO) {
        if (!save(convert2Permission(permissionDTO))) {
            throw ExFactory.throwSystem("系统异常, 菜单添加失败");
        }
    }

    @Override
    public void updatePermission(PermissionDTO permissionDTO) {
        boolean update = lambdaUpdate()
                .eq(Permission::getId, permissionDTO.getId())
                .update(convert2Permission(permissionDTO));

        if (!update) {
            throw ExFactory.throwSystem("系统异常, 菜单修改失败");
        }
    }


    /**
     * 通过id 递归获取到该id下的所有子id 并添加到permissionIds中
     *
     * @param id id
     */
    @Override
    public void deleteAllById(String id) {
        List<String> permissionIds = new ArrayList<>();
        permissionIds.add(id);
        // 根据父类id  递归获取到所有的子类id 并放进list集合以便批量删除
        getPermissionIds(id, permissionIds);
        if (!removeByIds(permissionIds)) {
            throw ExFactory.throwSystem("系统错误, 删除失败");
        }
    }

    /**
     * 根据用户id获取用户权限值
     *
     * @param id 用户id
     * @return 用户权限值
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList;
        User user = userService.getById(id);
        if (null != user && "admin".equals(user.getUsername())) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectAdminPermission() {
        List<Permission> selectPermissionList = lambdaQuery().list();
        List<Permission> permissionList = PermissionUtil.bulid(selectPermissionList);
        return MenuUtil.bulid(permissionList);
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        return MenuUtil.bulid(PermissionUtil.bulid(baseMapper.selectPermissionByUserId(userId)
                .parallelStream()
                .distinct()
                .collect(Collectors.toList())));
    }


    /**
     * 给角色分配权限
     *
     * @param rolePermissionDTO 角色权限DTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveRolePermission(RolePermissionDTO rolePermissionDTO) {

        List<RolePermission> rolePermissions = rolePermissionService.lambdaQuery().eq(RolePermission::getRoleId, rolePermissionDTO.getRoleId()).list();

        boolean remove = rolePermissionService.lambdaUpdate()
                .eq(RolePermission::getRoleId, rolePermissionDTO.getRoleId())
                .remove();
        if (!rolePermissions.isEmpty() && !remove) {
            throw ExFactory.throwSystem("系统异常, 权限清除失败, 请稍后重试");
        }

        List<String> permissionIds = Arrays.asList(rolePermissionDTO.getPermissionIds().split(","));

        if (!permissionIds.isEmpty()) {
            List<RolePermission> rolePermissionList = permissionIds.parallelStream()
                    .map(permissionId -> convert2RolePermission(rolePermissionDTO.getRoleId(), permissionId))
                    .collect(Collectors.toList());

            if (!rolePermissionService.saveBatch(rolePermissionList)) {
                throw ExFactory.throwSystem("系统异常, 添加权限失败");
            }
        }
    }

    /**
     * 根据角色id获取当前角色权限树
     *
     * @param roleId 角色id
     * @return 权限树
     */

    @Override
    public List<PermissionVO> getRolePermission(String roleId) {
        List<Permission> allPermissionList = lambdaQuery()
                .apply("CAST(id AS SIGNED)")
                .list();

        //根据角色id获取当前角色拥有的权限id列表
        List<String> permissionIds = rolePermissionService.lambdaQuery()
                .eq(RolePermission::getRoleId, roleId)
                .select(RolePermission::getPermissionId)
                .list()
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 设置是否拥有该权限
        allPermissionList.forEach(permission -> permission.setSelect(permissionIds.contains(permission.getId())));
        List<PermissionVO> treeNodes = allPermissionList.stream().map(this::convert2PermissionVO).collect(Collectors.toList());
        return convert2PermissionTree(treeNodes);
    }

    public void getPermissionIds(String id, List<String> permissionIds) {

        // 查找该id的子id
        List<Permission> permissionChildIds = lambdaQuery()
                .eq(Permission::getPid, id)
                .select(Permission::getId)
                .list();

        // 递归添加子节点
        permissionChildIds.forEach(childId -> {
            // 添加子节点
            permissionIds.add(childId.getId());
            // 递归添加子节点的子节点 直到没有子节点
            this.getPermissionIds(childId.getId(), permissionIds);
        });

    }

    private RolePermission convert2RolePermission(String roleId, String permissionId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        return rolePermission;
    }

    private Permission convert2Permission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setPid(permissionDTO.getPid());
        permission.setPermissionValue(permissionDTO.getPermissionValue());
        permission.setComponent(permissionDTO.getComponent());
        permission.setIcon(permissionDTO.getIcon());
        permission.setName(permissionDTO.getName());
        permission.setStatus(permissionDTO.getStatus());
        permission.setType(permissionDTO.getType());
        permission.setPath(permissionDTO.getPath());
        return permission;
    }

    private PermissionVO convert2PermissionVO(Permission permission) {
        return PermissionVO.builder()
                .id(permission.getId())
                .pid(permission.getPid())
                .name(permission.getName())
                .path(permission.getPath())
                .permissionValue(permission.getPermissionValue())
                .component(permission.getComponent())
                .icon(permission.getIcon())
                .type(permission.getType())
                .status(permission.getStatus())
                .level(permission.getLevel())
                .selected(permission.isSelect())
                .build();
    }


    /**
     * 构建菜单树
     *
     * @param treeNodes 树的所有节点
     * @return 树
     */
    private static List<PermissionVO> convert2PermissionTree(List<PermissionVO> treeNodes) {
        List<PermissionVO> tree = new ArrayList<>();

        treeNodes.forEach(treeNode -> {
            // 选择根节点
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                tree.add(findChildren(treeNode, treeNodes));
            }
        });
        return tree;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode  根节点
     * @param treeNodes 子节点
     * @return 子节点
     */
    private static PermissionVO findChildren(PermissionVO treeNode, List<PermissionVO> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        treeNodes.forEach(node -> {
            if (treeNode.getId().equalsIgnoreCase(node.getPid())) {
                node.setLevel(treeNode.getLevel() + 1);
                treeNode.getChildren().add(findChildren(node, treeNodes));
            }
        });

        return treeNode;
    }
}
