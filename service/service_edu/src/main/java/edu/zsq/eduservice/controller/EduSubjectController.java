package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.vo.subject.OneSubject;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程分类管理
 *
 * @author zsq
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/eduService/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

    /**
     * 查询课程分类
     */
    @GetMapping("/getAllSubject")
    public MyResultUtils getAllSubject() {

        List<OneSubject> list = eduSubjectService.getAllSubject();

        return MyResultUtils.ok().data("list", list);
    }


    /**
     * 添加课程分类
     * 获取上传的文件,并进行读取
     */

    @PostMapping("/addSubject")
    public MyResultUtils addSubject(MultipartFile file) {

        eduSubjectService.saveSubject(file, eduSubjectService);

        return MyResultUtils.ok().message("文件上传成功");
    }

}

