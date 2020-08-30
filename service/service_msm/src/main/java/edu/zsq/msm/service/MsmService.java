package edu.zsq.msm.service;

import java.util.Map;

/**
 * @author 张
 */
public interface MsmService {
    /**
     *  发送给阿里云进行短信发送
     * @param parm
     * @param mobile
     * @return
     */
    boolean send(Map<String, String> parm, String mobile);
}
