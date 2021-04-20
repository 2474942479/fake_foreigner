package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.vo.subject.OneSubject;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程分类管理
 *
 * @author zsq
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/eduService/subject")
public class EduSubjectController {

    @Resource
    EduSubjectService eduSubjectService;

    /**
     * 查询课程分类
     */
    @GetMapping("/getAllSubject")
    public JsonResult<List<OneSubject>> getAllSubject() {
        return JsonResult.success(eduSubjectService.getAllSubject());
    }


    /**
     * 添加课程分类
     * 获取上传的文件,并进行读取
     */

    @PostMapping("/addSubject")
    public JsonResult<Void> addSubject(MultipartFile file) {
        return eduSubjectService.saveSubject(file, eduSubjectService);
    }

}

