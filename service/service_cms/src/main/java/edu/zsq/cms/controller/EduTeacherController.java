package edu.zsq.cms.controller;


import edu.zsq.cms.entity.EduCourse;
import edu.zsq.cms.entity.EduTeacher;
import edu.zsq.cms.service.EduCourseService;
import edu.zsq.cms.service.EduTeacherService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/eduCms/teacherFront")
public class EduTeacherController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/getTeacherFront/{current}/{size}")
    public JsonResult getTeacherFront(@PathVariable long current, @PathVariable long size){
        Map<String ,Object> map = teacherService.getTeacherFront(current,size);
        return JsonResult.success().data(map);
    }


    /**
     * 根据讲师id获取讲师基本信息和所讲课程
     * @param id
     * @return
     */
    @GetMapping("/getTeacherAllById/{id}")
    public JsonResult getTeacherAllById(@PathVariable String id){
        EduTeacher teacherInfo = teacherService.getById(id);

        List<EduCourse> courseList = courseService.getCourseByTeacherId(id);

        return JsonResult.success().data("teacherInfo",teacherInfo).data("courseList",courseList);
    }

}

