package edu.zsq.acl.utils;

import edu.zsq.acl.entity.vo.PermissionTree;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张
 * 递归查找工具类
 */
@Component
public class RecursionFind {

    /**
     * 递归查找子节点
     * @param treeNode 父节点
     * @param treeNodes 所有节点集合
     * @return
     */
    public static PermissionTree findChildren(PermissionTree treeNode,List<PermissionTree> treeNodes){
        treeNode.setChildren(new ArrayList<>());
        for (PermissionTree node : treeNodes) {
//            判断子节点的pid和父节点id是否一致  一致则是该父节点的子节点 则将该子节点添加到父节点下
            if(treeNode.getId().equals(node.getPid())){
//                子节点的层级是父节点的层级加1
                node.setLevel(treeNode.getLevel()+1);
//                初始化父节点的子节点集合
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
//                给父节点添加子节点 .add(node)只能添加一个层级的子节点 运用递归的方法,构建起一个树
                treeNode.getChildren().add(findChildren(node,treeNodes));

            }
        }
        return treeNode;
    }
}
