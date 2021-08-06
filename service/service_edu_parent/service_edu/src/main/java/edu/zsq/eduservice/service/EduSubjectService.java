package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.dto.SubjectDTO;
import edu.zsq.eduservice.entity.vo.SubjectTree;
import edu.zsq.eduservice.entity.vo.SubjectVO;
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
     * 根据id获取分类详细信息
     *
     * @param id id
     * @return 详细信息
     */
    SubjectVO getSubjectInfo(String id);

    /**
     * 获取所有分类信息
     *
     * @return 通过OneSubject/TwoSubject实体类封装为前端需要的格式
     */
    List<SubjectTree> getAllSubject();

    /**
     * 添加或修改课程分类
     *
     * @param subjectDTO 课程DTO
     */
    void saveOrUpdateSubject(SubjectDTO subjectDTO);

    /**
     * 添加课程分类
     *
     * @param file              上传的文件
     * @param eduSubjectService 服务
     * @return 添加结果
     */
    JsonResult<Void> saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 删除该分类以及该分类下的所有子类
     *
     * @param id 课程分类id
     */
    void deleteSubject(String id);

}
