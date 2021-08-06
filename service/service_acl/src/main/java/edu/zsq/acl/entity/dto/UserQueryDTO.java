package edu.zsq.acl.entity.dto;

import edu.zsq.utils.page.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangsongqi
 * @date 6:56 下午 2021/3/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageDTO {

    private String username;
}
