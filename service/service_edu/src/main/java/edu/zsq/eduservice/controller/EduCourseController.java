package edu.zsq.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zsq.eduservice.entity.EduCourse;
import edu.zsq.eduservice.entity.vo.CourseInfoVo;
import edu.zsq.eduservice.entity.vo.CourseQuery;
import edu.zsq.eduservice.entity.vo.FinalReleaseVo;
import edu.zsq.eduservice.service.EduCourseService;
import edu.zsq.utils.result.MyResultUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/eduService/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询所有课程
     * @return
     */
    @GetMapping("/getAllCourse")
    public MyResultUtils getAllCourse(){
        List<EduCourse> list = eduCourseService.list();
        return MyResultUtils.ok().data("list",list);
    }

    /**
     * 条件分页查询
     */
    @PostMapping("/getCourseListPage/{current}/{size}")
    @ApiOperation(value = "课程条件分页查询", notes = "根据获取的current size 以及courseQuery查询并分页")

    public MyResultUtils getCourseListPage(
            @ApiParam(name = "current", value = "当前页数", required = true)
            @PathVariable Long current,
            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody CourseQuery courseQuery
    ) {
        Page<EduCourse> page = new Page<>(current, size);
        eduCourseService.pageQuery(page, courseQuery);
        //       分页后 单页数据List集合
        List<EduCourse> list = page.getRecords();
//        分页后查询到的全部记录数
        long total = page.getTotal();
        return MyResultUtils.ok().data("total", total).data("list", list);
    }

    /**
     * 添加课程基本信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/addCourseInfo")
    public MyResultUtils addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return MyResultUtils.ok().data("id",courseId);
    }


    /**
     * 回显课程基本信息
     * @param id 课程id
     * @return  课程基本信息
     */
    @GetMapping("/getCourseInfoVoById/{id}")
    public MyResultUtils getCourseInfoById(@PathVariable String id){

        CourseInfoVo  courseInfo= eduCourseService.getCourseInfoVoById(id);
        return MyResultUtils.ok().data("courseInfo",courseInfo);
    }

    /**
     * 修改课程基本信息
     * @param courseInfoVo 课程基本信息VO类
     * @return
     */
    @PutMapping("/updateCourseInfoVo")
    public MyResultUtils updateCourseInfoVo(@RequestBody CourseInfoVo courseInfoVo){

        eduCourseService.updateCourseInfoVo(courseInfoVo);
        return MyResultUtils.ok().message("修改成功");
    }

    /**
     * 根据课程id查询返回最终发布信息
     * @param id
     * @return
     */
    @GetMapping("/getFinalReleaseVo/{id}")
    public MyResultUtils getFinalReleaseVo(@PathVariable String id){
        FinalReleaseVo finalReleaseVo = eduCourseService.getFinalReleaseVo(id);

        return MyResultUtils.ok().data("finalReleaseVo",finalReleaseVo);
    }

    /**
     * 修改课程发布状态
     * @param eduCourse
     * @return
     */
    @PostMapping("/updateReleaseStatus")
    public MyResultUtils updateReleaseStatus(@RequestBody  EduCourse eduCourse){
        boolean update = eduCourseService.updateById(eduCourse);
        if (update){
            return MyResultUtils.ok().message("保存成功");
        }else {
            return MyResultUtils.error().message("保存失败");
        }
    }

    /**
     * 根据课程id删除课程相关的所有信息(课程章节 小节 描述 课程基本信息)
     *
     * @param courseId 课程id
     * @return  返回是否全部删除
     */
    @DeleteMapping("/deleteCourse/{courseId}")
    public MyResultUtils deleteCourse(@PathVariable String courseId){

        Boolean remove = eduCourseService.removeCourseAllById(courseId);

        if (remove){
            return MyResultUtils.ok().message("已成功删除课程全部信息");
        }else {
            return MyResultUtils.error().message("删除课程失败");
        }


    }


}

