package edu.zsq.eduservice.entity.vo;

import edu.zsq.utils.tree.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 张
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectTree extends TreeDTO {
    private String title;
}
