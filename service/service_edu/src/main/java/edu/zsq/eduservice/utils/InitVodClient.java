package edu.zsq.eduservice.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import edu.zsq.utils.properties.ReadOssPropertiesUtil;

/**
 * @author 张
 */
public class InitVodClient {
    public static DefaultAcsClient initVodClient() {
        // 点播服务接入区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ReadOssPropertiesUtil.ACCESS_KEY_ID, ReadOssPropertiesUtil.ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

}
