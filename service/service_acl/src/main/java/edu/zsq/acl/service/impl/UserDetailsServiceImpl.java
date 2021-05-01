package edu.zsq.acl.service.impl;

import edu.zsq.acl.entity.User;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.service.UserService;
import edu.zsq.security.entity.MyUserDetails;
import edu.zsq.security.entity.UserInfoDTO;
import edu.zsq.utils.exception.core.ExFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自定义 查询权限列表 类
 *
 * @author 张
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);
        // 判断用户是否存在
        if (user == null) {
            throw ExFactory.throwBusiness("当前用户名不存在!");
        }
        // 返回UserDetails实现类
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        // 根据id获取权限列表
        return new MyUserDetails(userInfoDTO, permissionService.selectPermissionValueByUserId(user.getId()));
    }
}
