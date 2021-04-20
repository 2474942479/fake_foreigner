package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.vo.subject.OneSubject;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-19
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     *
     * 添加课程分类
     * @param file 上传的文件
     * @param eduSubjectService 服务
     * @return 添加结果
     */
    JsonResult<Void> saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 获取所有分类信息
     * @return  通过OneSubject/TwoSubject实体类封装为前端需要的格式
     */
    List<OneSubject> getAllSubject();
}
