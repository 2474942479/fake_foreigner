package edu.zsq.acl.service;

import edu.zsq.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.acl.entity.dto.UserDTO;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.utils.page.PageData;

import java.util.List;

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
     * 根据id获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserVO getUser(String id);

    /**
     * 分页获取用户信息
     * @param userQueryDTO 分页参数
     * @return 用户信息
     */
    PageData<UserVO> pageUser(UserQueryDTO userQueryDTO);

    /**
     * 从数据库中取出用户信息
     *
     * @param username 用户名称
     * @return
     */
    User selectByUsername(String username);

    /**
     * 添加或修改用户信息
     *
     * @param userDTO 用户信息
     */
    void saveOrUpdateUserInfo(UserDTO userDTO);

    /**
     * 根据角色用户id列表删除用户以及该用户绑定的角色
     *
     * @param userIds 用户id列表
     */
    void batchRemove(List<String> userIds);
}
