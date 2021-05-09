package edu.zsq.acl.service;

import com.alibaba.fastjson.JSONObject;
import edu.zsq.acl.entity.vo.UserInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author 张
 */
public interface IndexService {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoVO getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username 用户名
     * @return 菜单
     */
    List<JSONObject> getMenu(String username);

}
