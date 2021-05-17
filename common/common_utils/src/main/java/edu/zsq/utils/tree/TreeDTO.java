package edu.zsq.utils.tree;

import lombok.Data;

import java.util.List;


/**
 * @author 张
 */
@Data
public class TreeDTO {
    private String id;
    private String parentId;
    private Integer level;
    private List<TreeDTO> children;

    public void add(TreeDTO treeDTO){
        children.add(treeDTO);
    }

}
