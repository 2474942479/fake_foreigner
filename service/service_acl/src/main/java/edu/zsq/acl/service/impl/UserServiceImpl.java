package edu.zsq.acl.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.dto.UserDTO;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.acl.mapper.UserMapper;
import edu.zsq.acl.service.UserRoleService;
import edu.zsq.acl.service.UserService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;

    @Override
    public UserVO getUser(String id) {
        return convert2UserVO(getById(id));
    }

    @Override
    public User selectByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .last(Constants.LIMIT_ONE)
                .one();
    }

    @Override
    public PageData<UserVO> pageUser(UserQueryDTO userQueryDTO) {
        Page<User> page = new Page<>(userQueryDTO.getCurrent(), userQueryDTO.getSize());
        lambdaQuery()
                .like(StringUtils.isNotBlank(userQueryDTO.getUsername()), User::getUsername, userQueryDTO.getUsername())
                .orderByDesc(User::getGmtModified)
                .page(page);

        if (page.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<UserVO> collect = page.getRecords().stream().map(this::convert2UserVO).collect(Collectors.toList());
        return PageData.of(collect, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public void saveOrUpdateUserInfo(UserDTO userDTO) {
        Integer count = lambdaQuery()
                .eq(User::getUsername, userDTO.getUsername())
                .count();

        if (count > 0) {
            throw ExFactory.throwBusiness("该用户名已存在!");
        }

        if (!saveOrUpdate(convert2User(userDTO))) {
            throw ExFactory.throwSystem("系统异常, 操作失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public void batchRemove(List<String> userIds) {
        if (!removeByIds(userIds)) {
            throw ExFactory.throwSystem("系统异常, 用户删除失败");
        }
        userRoleService.batchRemove(userIds);
    }

    private User convert2User(UserDTO userDTO) {
        String pwd = Optional.ofNullable(userDTO.getPassword())
                .map(SecureUtil::md5)
                .orElse(null);

        User user = new User();
        user.setId(Optional.ofNullable(userDTO.getId()).orElse(null));
        user.setUsername(userDTO.getUsername());
        user.setPassword(pwd);
        user.setNickName(userDTO.getNickName());
        user.setSalt(userDTO.getSalt());
        return user;
    }

    private UserVO  convert2UserVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickName(user.getNickName())
                .salt(user.getSalt())
                .gmtCreate(user.getGmtCreate())
                .build();
    }
}
