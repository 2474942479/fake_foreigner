package edu.zsq.acl.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.dto.UserQueryDTO;
import edu.zsq.acl.entity.vo.UserVO;
import edu.zsq.acl.mapper.UserMapper;
import edu.zsq.acl.service.UserService;
import edu.zsq.utils.page.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public User selectByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .last("limit 1")
                .one();
    }

    @Override
    public PageData<UserVO> pageUser(UserQueryDTO userQueryDTO) {
        Page<User> page = new Page<>(userQueryDTO.getCurrent(), userQueryDTO.getSize());
        lambdaQuery()
                .like(StringUtils.isNotBlank(userQueryDTO.getUserName()), User::getUsername, userQueryDTO.getUserName())
                .orderByDesc(User::getGmtModified)
                .page(page);

        if (page.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<UserVO> collect = page.getRecords().stream().map(this::convert2UserInfoVO).collect(Collectors.toList());
        return PageData.of(collect, page.getCurrent(), page.getSize(), page.getTotal());
    }

    private UserVO  convert2UserInfoVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .salt(user.getSalt())
                .build();
    }
}
