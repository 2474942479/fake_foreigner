package edu.zsq.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import edu.zsq.oss.service.OssService;
import edu.zsq.oss.utils.ReadOssPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 张
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public StringBuffer uploadAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ReadOssPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ReadOssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ReadOssPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ReadOssPropertiesUtil.BUCKET_NAME;
//        使用StringBuffer拼接从oss中获取到的url  效率比String快
        StringBuffer url = new StringBuffer("https://");
        //获取uuid值并将-替换为空
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建OSSClient实例。
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
//            用UUID生成文件名称  防止重复

            //获取文件真实名
            String originalFilename = file.getOriginalFilename();
//            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);//获取后缀名
            String filename = uuid + originalFilename;

//          把文件按年月日进行分类
//          获取当前日期并通过工具类格式化
            String datePath = new DateTime().toString("yyyy/MM/dd");

//            拼接上oss文件路径
            filename = datePath + "/" + filename;

//            参数一:oss中Bucket名称  参数二:上传到oss文件路径和名称 /aa/bb/1.jpg
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

//           把上传之后的文件路径返回  需要把阿里oss中的路径手动拼接  使用StringBuffer拼接效率 更快
//            https://online-zsq.oss-cn-beijing.aliyuncs.com/%E5%A4%B4%E5%83%8F/%E5%BD%A9%E8%9B%8B%E7%89%88.png
            url.append(bucketName);
            url.append(".");
            url.append(endpoint);
            url.append("/");
            url.append(filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
