package edu.zsq.acl.service.impl;

import edu.zsq.acl.entity.User;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.service.UserService;
import edu.zsq.security.entity.SecurityUser;
import edu.zsq.utils.exception.servicexception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);
        System.out.println(user);
        // 判断用户是否存在
        if (null == user){
            throw new MyException(20001,"用户名不存在！");
        }
        // 返回UserDetails实现类
        edu.zsq.security.entity.User curUser = new edu.zsq.security.entity.User();
        BeanUtils.copyProperties(user,curUser);

        // 根据id获取权限列表
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
