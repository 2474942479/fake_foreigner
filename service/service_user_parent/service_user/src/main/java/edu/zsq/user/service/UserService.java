package edu.zsq.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.*;
import edu.zsq.user.entity.vo.UserVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-05
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     *
     * @param loginDTO 登陆信息
     * @return 登陆结果
     */
    String login(LoginDTO loginDTO);

    /**
     * 注册
     *
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);

    /**
     * 根据openid获取用户信息
     *
     * @param openid openId
     * @return 用户信息
     */
    User getUserInfoByOpenid(String openid);

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     *
     * @param day 天
     * @return 人数
     */
    Integer getRegisterNumber(LocalDate day);

    /**
     * 根据查询条件分页获取用户信息
     *
     * @param userQueryDTO 查询条件
     * @return 分页结果
     */
    PageData<UserVO> getUserList(UserQueryDTO userQueryDTO);

    /**
     * 根据request 中的token获取用户信息
     *
     * @param request request
     * @return 用户信息
     */
    UserVO getUserInfoByToken(HttpServletRequest request);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserVO getUserInfoById(String userId);

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息
     * @return token字符串
     */
    String updateUser(UserDTO userDTO);

    /**
     * 第三方登录用户完善用户信息
     *
     * @param resetDTO 用户完善信息
     */
    void perfectUser(ResetDTO resetDTO);

    /**
     * 修改用户密码
     *
     * @param resetDTO 用户修改信息
     */
    void updateUserPass(ResetDTO resetDTO);

    /**
     * 修改用户手机号
     *
     * @param resetDTO 用户修改信息
     */
    void updateUserMobile(ResetDTO resetDTO);

}
