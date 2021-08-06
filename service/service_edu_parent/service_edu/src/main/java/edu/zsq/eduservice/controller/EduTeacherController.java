package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduTeacher;
import edu.zsq.eduservice.entity.dto.TeacherDTO;
import edu.zsq.eduservice.entity.dto.query.TeacherQueryDTO;
import edu.zsq.eduservice.entity.vo.TeacherVO;
import edu.zsq.eduservice.service.EduTeacherService;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zsq
 * @CrossOrigin 跨域注解
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/eduService/teacher")
@Api(tags = {"讲师的CRUD"})
public class EduTeacherController {

    @Resource
    private EduTeacherService teacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "获取讲师列表")
    public JsonResult<List<EduTeacher>> findAll() {
        return JsonResult.success(teacherService.list(null));
    }

    /**
     * 条件分页查询
     */
    @PostMapping("/getTeacherListPage")
    @ApiOperation(value = "讲师条件分页查询", notes = "根据获取的current size 以及teacherQuery查询并分页")
    public JsonResult<PageData<TeacherVO>> getTeacherListPage(@RequestBody TeacherQueryDTO teacherQueryDTO) {
        return JsonResult.success(teacherService.getTeacherListPage(teacherQueryDTO));
    }


    /**
     * 添加教师
     *
     * @param teacherDTO 教师信息
     * @return 添加结果
     */
    @PostMapping("/saveOrUpdateTeacher")
    @ApiOperation(value = "增加讲师", notes = "根据获取的EduTeacher对象新增讲师")
    @ApiImplicitParam(name = "teacherDTO", value = "讲师详细信息", required = true, dataType = "TeacherDTO", paramType = "body")
    public JsonResult<Void> saveOrUpdateTeacher(@RequestBody TeacherDTO teacherDTO) {
        teacherService.saveOrUpdateTeacher(teacherDTO);
        return JsonResult.OK;
    }


    /**
     * 删除教师
     *
     * @param id 教师id
     * @return 删除结果
     */
    @DeleteMapping("/removeTeacher/{id}")
    @ApiOperation(value = "删除讲师", notes = "根据获取的id删除讲师")
    @ApiImplicitParam(name = "id", value = "讲师ID", required = true, dataType = "String", paramType = "path")
    public JsonResult<Void> removeTeacher(@PathVariable String id) {
        teacherService.delTeacher(id);
        return JsonResult.OK;
    }


    /**
     * 根据id查询讲师
     *
     * @param id 教师id
     * @return 教师信息
     */
    @GetMapping("/getTeacherInfo/{id}")
    public JsonResult<TeacherVO> getTeacherInfo(@PathVariable String id) {
        return JsonResult.success(teacherService.getTeacherInfo(id));
    }
}

