package edu.zsq.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.utils.result.JsonResult;

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
     * @param LoginDTO 登陆信息
     * @return 登陆结果
     */
    JsonResult<String> login(LoginDTO LoginDTO);

    /**
     * 注册
     * @param registerDTO 注册信息
     */
    JsonResult<Void> register(RegisterDTO registerDTO);

    /**
     * 根据openid获取用户信息
     * @param openid openId
     * @return 用户信息
     */
    User getUserInfoByOpenid(String openid);

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     * @param day 天
     * @return 人数
     */
    Integer getRegisterNumber(String day);
}
