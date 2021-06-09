package edu.zsq.user.controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.user.entity.User;
import edu.zsq.user.service.UserService;
import edu.zsq.user.uitls.HttpClientUtils;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.jwt.JwtUtils;
import edu.zsq.utils.properties.WxProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 张
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxController {

    @Resource
    private UserService userService;

    /**
     * 获取微信二维码
     */
    @GetMapping("/login")
    public String getWxCode() {

//        微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = WxProperties.WX_OPEN_REDIRECT_URL;

        try {
            URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                baseUrl,
                WxProperties.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");


        return "redirect:" + url;
    }


    /**
     * 根据返回的临时票据发送请求
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {

        //得到授权临时票据code
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

//      对baseAccessTokenUrl进行格式化将对应的数据拼接到%s处   拼接成生成新的字符串
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                WxProperties.WX_OPEN_APP_ID,
                WxProperties.WX_OPEN_APP_SECRET,
                code);
        String result = HttpClientUtils.httpGet(accessTokenUrl);
        Map map = JSON.parseObject(result, Map.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");


        //查询数据库当前用用户是否曾经使用过微信登录
        User user = userService.getUserInfoByOpenid(openid);
        if (user == null) {
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo;

            try {
                resultUserInfo = HttpClientUtils.httpGet(userInfoUrl);
            } catch (Exception e) {
                throw ExFactory.throwBusiness("获取用户信息失败");
            }

            //解析json
            Map mapUserInfo = JSON.parseObject(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headImgUrl = (String) Optional.ofNullable(mapUserInfo.get("headimgurl")).orElse(Constants.DEFAULT_AVATAR);
            Integer sex =(Integer) Optional.ofNullable(mapUserInfo.get("sex")).orElse(0);

            //向数据库中插入一条记录
            user = new User();
            user.setNickname(nickname);
            user.setOpenid(openid);
            user.setSex(sex);
            user.setAvatar(headImgUrl);
            userService.save(user);
        }

        String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        return "redirect:http://localhost:3000?token=" + jwtToken;

    }

}
