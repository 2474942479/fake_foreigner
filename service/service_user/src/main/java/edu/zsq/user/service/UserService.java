package edu.zsq.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.vo.LoginVo;
import edu.zsq.user.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-21
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @return
     * @param loginVo
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     * @return
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    User getUserInfoByOpenid(String openid);

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     * @param day
     * @return
     */
    Integer getRegisterNumber(String day);
}
