package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.dto.SubjectDTO;
import edu.zsq.eduservice.entity.vo.SubjectTree;
import edu.zsq.eduservice.entity.vo.SubjectVO;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
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
    EduSubjectService subjectService;

    @GetMapping("/getAllSubject")
    @ApiOperation(value = "查询课程分类")
    public JsonResult<List<SubjectTree>> getAllSubject() {
        return JsonResult.success(subjectService.getAllSubject());
    }

    @GetMapping("/getSubjectInfo/{id}")
    @ApiOperation(value = "根据id获取课程分类详细信息")
    public JsonResult<SubjectVO> getSubjectInfo(@PathVariable("id") String id) {
        return JsonResult.success(subjectService.getSubjectInfo(id));
    }


    @PostMapping("/addSubject")
    @ApiOperation(value = "导入课程分类", notes = "根据excel导入课程分类")
    public JsonResult<Void> addSubject(MultipartFile file) {
        return subjectService.saveSubject(file, subjectService);
    }

    @PostMapping("/saveOrUpdateSubject")
    @ApiOperation(value = "添加或修改课程分类")
    public JsonResult<Void> saveOrUpdateSubject(@RequestBody SubjectDTO subjectDTO) {
        subjectService.saveOrUpdateSubject(subjectDTO);
        return JsonResult.success();
    }

    @DeleteMapping("/deleteSubject/{id}")
    @ApiOperation(value = "删除课程分类")
    public JsonResult<Void> deleteSubject(@PathVariable("id") String id) {
        subjectService.deleteSubject(id);
        return JsonResult.success();
    }


}

