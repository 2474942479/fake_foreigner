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
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    UserInfoVO getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);

}
