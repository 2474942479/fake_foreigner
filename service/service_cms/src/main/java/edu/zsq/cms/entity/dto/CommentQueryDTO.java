package edu.zsq.cms.entity.dto;

import edu.zsq.utils.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 张
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQueryDTO extends PageDTO {

    private String courseId;
}
