package edu.zsq.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张
 */
public class TreeShapeUtil {

    /**
     * 使用递归方法建菜单树
     *
     * @param treeNodes 全部节点
     * @return 菜单树
     */
    public <T extends TreeDTO> List<T> build(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes 全部节点
     * @return 子节点
     */
    public <T extends TreeDTO> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                it.setLevel(treeNode.getLevel() + 1);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
