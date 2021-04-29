package edu.zsq.acl.entity.dto;

import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.utils.page.PageData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangsongqi
 * @date 6:56 下午 2021/3/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageData<UserVO> {

    private String userName;
}
