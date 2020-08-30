package edu.zsq.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.vo.LoginVo;
import edu.zsq.user.entity.vo.RegisterVo;
import edu.zsq.user.mapper.UserMapper;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * StringRedisTemplate extends RedisTemplate<String, String>
     */
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 登录方法
     *
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "手机号或者密码为空,登陆失败");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        User user = baseMapper.selectOne(wrapper);
        if (user == null) {
            throw new MyException(20001, "手机号不存在");
        }

//        输入密码MD5加密后再比较密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            throw new MyException(20001, "密码错误");
        }

        if (user.getIsDeleted()) {
            throw new MyException(20001, "该用户已被禁用");
        }

        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {

        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(     StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new MyException(20001,"注册信息未填全");
        }

        //校验验证码
        //从redis获取发送的验证码
        String mobleCode =redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new MyException(20001,"验证码错误或已过期");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<User>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new MyException(20001,"该账户已存在");
        }

        //添加注册信息到数据库
        User user = new User();
        user.setNickname(nickname);
        user.setMobile(registerVo.getMobile());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setIsDisabled(false);
        user.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(user);
    }

    @Override
    public User getUserInfoByOpenid(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }

    /**
     * 根据日期获取这一天的注册人数  用于统计服务
     * @param day
     * @return
     */
    @Override
    public Integer getRegisterNumber(String day) {
        Integer count = baseMapper.getRegisterNumber(day);
        return count;
    }
}
