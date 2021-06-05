package edu.zsq.cms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import edu.zsq.cms.service.MsmService;
import edu.zsq.utils.properties.OssProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 张
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, String> parm, String mobile) {

        if (StringUtils.isEmpty(mobile)) {return false;}

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", OssProperties.ACCESS_KEY_ID, OssProperties.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);


//        设置相关参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");

        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "我的思勤在线学习网站");
        request.putQueryParameter("TemplateCode", "SMS_200192100");
//        验证码数据  json格式(JSONObject.toJSONString(parm)map格式方便转换成json格式)
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(parm));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }
}
