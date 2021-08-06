package edu.zsq.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.Role;
import edu.zsq.acl.entity.User;
import edu.zsq.acl.entity.vo.RoleVO;
import edu.zsq.acl.entity.vo.UserInfoVO;
import edu.zsq.acl.service.IndexService;
import edu.zsq.acl.service.PermissionService;
import edu.zsq.acl.service.RoleService;
import edu.zsq.acl.service.UserService;
import edu.zsq.utils.exception.core.ExFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.Name;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 张
 */
@Service
public class IndexServiceImpl implements IndexService {


    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;

    private static final String ADMIN = "admin";

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUserInfo(String username) {
        UserInfoVO userInfoVO = new UserInfoVO();
        User user = userService.selectByUsername(username);
        if (null == user) {
            throw ExFactory.throwBusiness("该用户不存在");
        }

        //根据用户id获取角色
        List<RoleVO> roleList = roleService.getAssignedRoleInfo(user.getId());
        List<String> roleNameList = roleList.stream().map(RoleVO::getRoleName).collect(Collectors.toList());
        String admin = "admin";
        if (admin.equalsIgnoreCase(username)) {
            roleNameList.add("超级管理员");
        }

        if (roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        userInfoVO.setName(user.getUsername());
        userInfoVO.setAvatar(user.getSalt());
        userInfoVO.setRoleNameList(roleNameList);
        userInfoVO.setPermissionValueList(permissionValueList);
//      "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        return userInfoVO;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username 用户名
     * @return 菜单
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        if (ADMIN.equals(username)) {
            return permissionService.selectAdminPermission();
        }

        User user = userService.selectByUsername(username);
        //根据用户id获取用户菜单权限
        return permissionService.selectPermissionByUserId(user.getId());
    }
}
