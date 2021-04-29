package edu.zsq.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.javafx.tk.PermissionHelper;
import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.RolePermission;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.mapper.PermissionMapper;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.service.RolePermissionService;
import edu.zsq.acl.service.UserService;
import edu.zsq.acl.utils.MenuUtil;
import edu.zsq.acl.utils.PermissionUtil;
import edu.zsq.acl.utils.RecursionFind;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
     * @return
     */
    @Override
    public List<PermissionTree> getPermissionList() {

        List<Permission> permissions = this.list();
//        复制到VO类中
        List<PermissionTree> permissionTreeList = new ArrayList<>();
        for (Permission permission : permissions) {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission, permissionTree);
            permissionTreeList.add(permissionTree);
        }

        List<PermissionTree> permissionList = new ArrayList<>();
//        查找递归入口
        for (PermissionTree permissionTree : permissionTreeList) {
            if ("0".equals(permissionTree.getPid())) {
                permissionTree.setLevel(1);
                permissionList.add(RecursionFind.findChildren(permissionTree, permissionTreeList));
            }
        }


        return permissionList;
    }

    /**
     * 通过id 递归获取到该id下的所有子id
     *
     * @param id
     * @param permissionIds
     * @return
     */
    @Override
    public void getPermissionIds(String id, List<String> permissionIds) {

//        查找该id的子id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> permissionChildIds = baseMapper.selectList(wrapper);

//        Java8新特性 Stream流 递归添加子节点
        permissionChildIds.forEach(childId -> {
//            添加子节点
            permissionIds.add(childId.getId());
//            递归添加子节点的子节点 直到没有子节点
            this.getPermissionIds(childId.getId(), permissionIds);
        });

    }


    /**
     * 根据用户id获取用户菜单
     *
     * @param id
     * @return
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
        List<Permission> selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        List<Permission> permissionList = PermissionUtil.bulid(selectPermissionList);
        return MenuUtil.bulid(permissionList);
    }


    @Override
    public void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionIds) {

        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        int count = rolePermissionService.count(wrapper);
        if (count > 0) {
            rolePermissionService.remove(wrapper);
        }

        if (permissionIds != null) {
            //roleId角色id
            //permissionId菜单id 数组形式
            //1 创建list集合，用于封装添加数据
            List<RolePermission> rolePermissionList = new ArrayList<>();
            //遍历所有菜单数组
            for (String perId : permissionIds) {
                //RolePermission对象
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(perId);
                //封装到list集合
                rolePermissionList.add(rolePermission);
            }
            //添加到角色菜单关系表
            rolePermissionService.saveBatch(rolePermissionList);
        }
    }


    //========================递归查询所有菜单================================================
    //获取全部菜单

    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }


    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());
        for (Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                it.setLevel(treeNode.getLevel() + 1);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
