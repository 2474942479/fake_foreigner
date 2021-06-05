package edu.zsq.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.user.mapper.UserMapper;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.result.JsonResult;
import edu.zsq.servicebase.common.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * StringRedisTemplate extends RedisTemplate<String, String>
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final static String DEFAULT_AVATAR = "http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132";

    /**
     * 登录
     *
     * @param loginDTO 登陆参数
     * @return token字符串
     */
    @Override
    public JsonResult<String> login(LoginDTO loginDTO) {

        String mobile = loginDTO.getMobile();
        String password = loginDTO.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return JsonResult.failure(ErrorCode.BUSINESS_ERROR, "手机号或者密码为空,请检查～");
        }

        User userInfo = lambdaQuery()
                .eq(User::getMobile, mobile)
                .last(Constants.LIMIT_ONE)
                .one();

        if (userInfo == null || userInfo.getIsDeleted()) {
            return JsonResult.failure(ErrorCode.BUSINESS_ERROR, "该手机号尚未注册,请先注册");
        }

//        输入密码MD5加密后再比较密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userInfo.getPassword())) {
            return JsonResult.failure("密码错误");
        }

        if (userInfo.getIsDeleted()) {
            return JsonResult.failure("该用户已被禁用");
        }

        return JsonResult.success(JwtUtils.getJwtToken(userInfo.getId(), userInfo.getNickname()));
    }

    @Override
    public void register(RegisterDTO registerDTO) {

        //获取注册信息，进行校验
        String nickname = registerDTO.getNickname();
        String mobile = registerDTO.getMobile();
        String password = registerDTO.getPassword();
        String code = registerDTO.getCode();

        //校验参数
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw ExFactory.throwWith(ErrorCode.PARAM_ERROR, "注册信息未填完整");
        }

        //校验验证码
        //从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)) {
            throw ExFactory.throwBusiness("验证码错误或已过期");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = lambdaQuery()
                .eq(User::getMobile, mobile)
                .last(Constants.LIMIT_ONE)
                .count();

        if (count > 0) {
            throw ExFactory.throwBusiness("该手机号已注册");
        }

        //添加注册信息到数据库
        User user = new User()
                .setNickname(nickname)
                .setMobile(registerDTO.getMobile())
                .setPassword(DigestUtils.md5DigestAsHex(password.getBytes()))
                .setIsDeleted(Boolean.FALSE)
                .setAvatar(DEFAULT_AVATAR);
        if (!save(user)) {
            throw ExFactory.throwBusiness("注册失败！");
        }
    }

    @Override
    public User getUserInfoByOpenid(String openid) {
        return lambdaQuery()
                .eq(User::getOpenid, openid)
                .last(Constants.LIMIT_ONE)
                .one();
    }

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     *
     * @param day 日期
     * @return 人数
     */
    @Override
    public Integer getRegisterNumber(LocalDate day) {
        return baseMapper.getRegisterNumber(day);
    }
}
