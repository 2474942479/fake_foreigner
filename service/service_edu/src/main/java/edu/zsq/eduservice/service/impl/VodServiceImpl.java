package edu.zsq.eduservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import edu.zsq.eduservice.service.VodService;
import edu.zsq.eduservice.utils.InitVodClient;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.properties.OssProperties;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 张
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {

    @Override
    public JsonResult<String> uploadVideo(MultipartFile file) {
        try {
            // fileName:上传文件原始名称
            String fileName = file.getOriginalFilename();

            if (StringUtils.isBlank(fileName)) {
                throw ExFactory.throwBusiness("文件名不能为空");
            }

            // title:上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            // inputStream:上传文件流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(OssProperties.ACCESS_KEY_ID, OssProperties.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //请求视频点播服务的请求ID
            if (!response.isSuccess()) {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                throw ExFactory.throwSystem("回调URL无效, ErrorCode: " + response.getCode() + ", ErrorMessage: " + response.getMessage());
            }
            return JsonResult.success(response.getVideoId());
        } catch (IOException e) {
            throw ExFactory.throwSystem("上传视频失败");
        }


    }


    /**
     * 通过视频资源Id删除阿里云中的视频
     *
     * @param videoSourceId 阿里云视频id
     * @return 删除结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public boolean removeVod(String videoSourceId) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient();
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoSourceId);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            throw ExFactory.throwSystem(e.getMessage());
        }
    }


    /**
     * 通过多个id批量删除视频
     *
     * @param vodIds 视频id
     * @return 删除结果
     */
    @Override
    public boolean removeVodList(List<String> vodIds) {
        try {
            //        初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient();
//            创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoSourceIds = StringUtils.join(vodIds.toArray(), ",");
            request.setVideoIds(videoSourceIds);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            throw ExFactory.throwSystem(e.getMessage());
        }
    }

    @Override
    public JsonResult<String> getPlayAuth(String videoId) {
        try {
//          初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return JsonResult.success(response.getPlayAuth());
        } catch (ClientException e) {
            e.printStackTrace();
            throw ExFactory.throwBusiness("获取视频凭证出错了");
        }

    }
}