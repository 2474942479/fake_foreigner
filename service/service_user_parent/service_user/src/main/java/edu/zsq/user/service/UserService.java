package edu.zsq.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.utils.result.JsonResult;

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
     * @param loginDTO 登陆信息
     * @return 登陆结果
     */
    JsonResult<String> login(LoginDTO loginDTO);

    /**
     * 注册
     * @param registerDTO 注册信息
     */
    void register(RegisterDTO registerDTO);

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
    Integer getRegisterNumber(LocalDate day);
}
