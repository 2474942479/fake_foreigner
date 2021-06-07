package edu.zsq.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.user.entity.User;
import edu.zsq.user.entity.dto.LoginDTO;
import edu.zsq.user.entity.dto.RegisterDTO;
import edu.zsq.user.entity.dto.UserDTO;
import edu.zsq.user.entity.vo.UserVO;
import edu.zsq.user.mapper.UserMapper;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.jwt.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public String login(LoginDTO loginDTO) {

        String mobile = loginDTO.getMobile();
        String password = loginDTO.getPassword();

        //校验参数
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
            throw ExFactory.throwBusiness("手机号或者密码为空,请检查～");
        }

        User userInfo = lambdaQuery()
                .eq(User::getMobile, mobile)
                .last(Constants.LIMIT_ONE)
                .one();

        if (userInfo == null || userInfo.getIsDeleted()) {
            throw ExFactory.throwBusiness("该手机号尚未注册,请先注册");
        }

//        输入密码MD5加密后再比较密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userInfo.getPassword())) {
            throw ExFactory.throwBusiness("密码错误");
        }

        if (userInfo.getIsDeleted()) {
            throw ExFactory.throwBusiness("该用户已被禁用");
        }

        return JwtUtils.getJwtToken(userInfo.getId(), userInfo.getNickname());
    }

    @Override
    public void register(RegisterDTO registerDTO) {

        //获取注册信息，进行校验
        String nickname = registerDTO.getNickname();
        String mobile = registerDTO.getMobile();
        String password = registerDTO.getPassword();
        String code = registerDTO.getCode();

        //校验参数
        if (StringUtils.isBlank(nickname) || StringUtils.isBlank(mobile) ||
                StringUtils.isBlank(password) || StringUtils.isBlank(code)) {
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
                .setAvatar(Constants.DEFAULT_AVATAR);
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

    @Override
    public UserVO getUserInfoByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isBlank(id)) {
            throw ExFactory.throwBusiness("id不存在!");
        }

        User user = lambdaQuery()
                .eq(User::getId, id)
                .last(Constants.LIMIT_ONE)
                .one();
        if (user == null) {
            throw ExFactory.throwBusiness("用户已注销或删除!");
        }
        return convert2UserVO(user);
    }

    @Override
    public UserVO getUserInfoById(String userId) {
        return convert2UserVO(getById(userId));
    }


    @Override
    public String updateUser(UserDTO userDTO) {

        if (!updateById(convert2User(userDTO))) {
            throw ExFactory.throwBusiness("服务器错误, 修改失败");
        }

        return JwtUtils.getJwtToken(userDTO.getId(), userDTO.getNickname());
    }

    private User convert2User(UserDTO userDTO) {
        return new User()
                .setAge(userDTO.getAge())
                .setAvatar(userDTO.getAvatar())
                .setId(userDTO.getId())
                .setNickname(userDTO.getNickname())
                .setSex(userDTO.getSex())
                .setSign(userDTO.getSign())
                .setMobile(userDTO.getMobile())
                .setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
    }

    private UserVO convert2UserVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .age(user.getAge())
                .sex(user.getSex())
                .avatar(user.getAvatar())
                .sign(user.getSign())
                .mobile(user.getMobile())
                .gmtCreate(user.getGmtCreate())
                .nickname(user.getNickname())
                .build();

    }

}
