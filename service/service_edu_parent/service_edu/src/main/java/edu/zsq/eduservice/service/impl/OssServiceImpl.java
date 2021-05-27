package edu.zsq.eduservice.service.impl;

import cn.hutool.core.util.URLUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import edu.zsq.eduservice.service.OssService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.properties.OssProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String upload2Oss(MultipartFile file) {
        OSS ossClient = OssProperties.getOssClient();
        try {
            // 获取文件真实名
            String ossPath = appendOssPath(file);

            // 参数一:oss中Bucket名称  参数二:上传到oss文件路径和名称 /aa/bb/1.jpg
            ossClient.putObject(OssProperties.BUCKET_NAME, ossPath, file.getInputStream());
            // 关闭OSSClient。
            return appendUrl(ossPath);

        } catch (Exception e) {
            throw ExFactory.throwSystem("阿里云OSS异常, 上传失败: " + e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void removeBatchOssFile(List<String> fileUrls) {
        OSS ossClient = OssProperties.getOssClient();
        try {
            List<String> files = fileUrls.parallelStream().map(this::parseUrl).collect(Collectors.toList());
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(OssProperties.BUCKET_NAME);
            deleteObjectsRequest.setQuiet(true);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest.withKeys(files));

            if (!deleteObjectsResult.getDeletedObjects().isEmpty()) {
                throw ExFactory.throwBusiness("以下文件删除失败: [ " + deleteObjectsResult.getDeletedObjects() + " ]");
            }
        } catch (Exception e) {
            throw ExFactory.throwSystem("阿里云OSS异常, 删除失败: " + e.getMessage());
        } finally {
            ossClient.shutdown();
        }

    }

    /**
     * 拼接上oss文件路径
     *
     * @param file 上传文件
     * @return oss文件路径
     */
    private String appendOssPath(MultipartFile file) {
        // 把文件按年月日进行分类
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                + "/"
                + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()
                + "-"
                + file.getOriginalFilename();
    }

    private String appendUrl(String filename) {
        // 把上传之后的文件路径返回  需要把阿里oss中的路径手动拼接  使用StringBuffer拼接效率 更快
        // https://online-zsq.oss-cn-beijing.aliyuncs.com/%E5%A4%B4%E5%83%8F/%E5%BD%A9%E8%9B%8B%E7%89%88.png
        return "https://" +
                OssProperties.BUCKET_NAME +
                "." +
                OssProperties.END_POINT +
                "/" +
                filename;
    }

    private String parseUrl(String fileUrl) {
        return URLUtil.getPath(fileUrl).replaceFirst("/", "");
    }
}
