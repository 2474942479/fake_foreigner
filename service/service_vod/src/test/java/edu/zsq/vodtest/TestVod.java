package edu.zsq.vodtest;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {


    public static void main(String[] args) throws Exception {

        DefaultAcsClient client = InitVodTest.initVodClient("LTAI4GBiosy6N9v4FmHCtj6e", "RX6JxzocPOABnwdAI1E019B6ln6srJ");
        GetPlayInfoResponse response1 = new GetPlayInfoResponse();
        GetVideoPlayAuthResponse response2 = new GetVideoPlayAuthResponse();


//      获取播放地址
        response1 = getPlayInfo(client);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response1.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }

//        获取播放凭证
        response2 = getVideoPlayAuth(client);
        //播放凭证
        System.out.print("PlayAuth = " + response2.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response2.getVideoMeta().getTitle() + "\n");

    }


    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("a6a87c28ce664bd898e4f9c02e2be096");
        return client.getAcsResponse(request);
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("f646e56600024156abdf25dcde6f5417");
        return client.getAcsResponse(request);
    }
}
