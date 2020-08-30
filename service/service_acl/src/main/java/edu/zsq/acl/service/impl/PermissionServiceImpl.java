package edu.zsq.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.Permission;
import edu.zsq.acl.entity.vo.PermissionTree;
import edu.zsq.acl.mapper.PermissionMapper;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.utils.RecursionFind;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    /**
     * 查询所有的菜单
     * @return
     */
    @Override
    public List<PermissionTree> getPermissionList() {

        List<Permission> permissions = this.list();
//        复制到VO类中
        ArrayList<PermissionTree> permissionTreeList = new ArrayList<>();
        for (Permission permission : permissions){
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission,permissionTree);
            permissionTreeList.add(permissionTree);
        }

        List<PermissionTree> permissionList = new ArrayList<>();
//        查找递归入口
        for (PermissionTree permissionTree : permissionTreeList) {
            if("0".equals(permissionTree.getPid())){
                permissionTree.setLevel(1);
                permissionList.add(RecursionFind.findChildren(permissionTree,permissionTreeList));
            }
        }


        return permissionList;
    }

    /**
     * 通过id 递归获取到该id下的所有子id
     * @param id
     * @param permissionIds
     * @return
     */
    @Override
    public void getPermissionIds(String id, ArrayList<String> permissionIds) {

//        查找该id的子id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> permissionChildIds = baseMapper.selectList(wrapper);

//        Java8新特性 Stream流 递归添加子节点
        permissionChildIds.stream().forEach(childId ->{
//            添加子节点
            permissionIds.add(childId.getId());
//            递归添加子节点的子节点 直到没有子节点
            this.getPermissionIds(childId.getId(),permissionIds);
        });

    }
}
