package edu.zsq.acl.service;

import edu.zsq.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.utils.page.PageData;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
public interface UserService extends IService<User> {

    /**
     * 从数据库中取出用户信息
     *
     * @param username 用户名称
     * @return
     */
    User selectByUsername(String username);

    /**
     * 分页获取用户信息
     * @param userQueryDTO 分页参数
     * @return 用户信息
     */
    PageData<UserVO> pageUser(UserQueryDTO userQueryDTO);
}
