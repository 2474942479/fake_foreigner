package edu.zsq.user.controller;


import com.google.gson.Gson;
import edu.zsq.user.entity.User;
import edu.zsq.user.service.UserService;
import edu.zsq.user.uitls.ConstantWxUtil;
import edu.zsq.user.uitls.HttpClientUtils;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
     * @return
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

        String redirectUrl = ConstantWxUtil.WX_OPEN_REDIRECT_URL;

        try {
            URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                baseUrl,
                ConstantWxUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");


        return "redirect:" + url;
    }


    /**
     * 根据返回的临时票据发送请求
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

//      对baseAccessTokenUrl进行格式化将对应的数据拼接到%s处   拼接成生成新的字符串
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtil.WX_OPEN_APP_ID,
                ConstantWxUtil.WX_OPEN_APP_SECRET,
                code);
        String result = HttpClientUtils.httpGet(accessTokenUrl);
        System.out.println("accessToken=============" + result);
        Gson gson = new Gson();
        Map map = gson.fromJson(result, HashMap.class);
        String access_token = (String) map.get("access_token");
        String openid = (String)map.get("openid");


        //查询数据库当前用用户是否曾经使用过微信登录
        User user = userService.getUserInfoByOpenid(openid);
        if(user == null){
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.httpGet(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new MyException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            System.out.println(mapUserInfo);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");
            Double doubleSex = (Double) mapUserInfo.get("sex");
            int sex = doubleSex.intValue();

            //向数据库中插入一条记录
            user = new User();
            user.setNickname(nickname);
            user.setOpenid(openid);
            user.setSex(sex);
            user.setAvatar(headimgurl);
            userService.save(user);
        }

        //TODO 登录
        String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
//        System.out.println(jwtToken);
        return "redirect:http://localhost:3000?token="+jwtToken;

    }

}
