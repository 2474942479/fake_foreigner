package edu.zsq.cms.entity.dto;

import edu.zsq.cms.entity.vo.CommentVO;
import edu.zsq.utils.page.PageDTO;
import edu.zsq.utils.page.PageData;
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
