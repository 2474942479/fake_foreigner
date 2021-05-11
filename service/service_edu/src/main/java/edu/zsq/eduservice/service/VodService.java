package edu.zsq.eduservice.service;

import edu.zsq.utils.result.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 张
 */
public interface VodService {

    /**
     * 上传视频到阿里云
     *
     * @param file 视频文件
     * @return 阿里云视频id
     */
    JsonResult<String> uploadVideo(MultipartFile file);

    /**
     * 通过视频资源Id删除阿里云中的视频
     *
     * @param videoSourceId 阿里云视频id
     * @return 删除结果
     */
    boolean removeVod(String videoSourceId);

    /**
     * 通过多个id批量删除视频
     *
     * @param vodIds 视频id
     * @return 删除结果
     */
    boolean removeVodList(List<String> vodIds);

    /**
     * 根据视频id获取视频播放凭证
     *
     * @param videoId 视频id
     * @return 播放凭证
     */
    JsonResult<String> getPlayAuth(String videoId);
}

