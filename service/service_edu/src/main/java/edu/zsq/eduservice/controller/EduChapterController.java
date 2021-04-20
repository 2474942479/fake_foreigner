package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/eduService/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService chapterService;

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/saveChapter")
    public JsonResult saveChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return JsonResult.success().message("添加章节成功");
    }

    /**
     * 根据章节id查询
     * @param id
     * @return
     */
    @GetMapping("/getChapter/{id}")
    public JsonResult getChapter(@PathVariable String id){
        return JsonResult.success(chapterService.getById(id));
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PutMapping("/updateChapter")
    public JsonResult updateChapter(@RequestBody EduChapter chapter){
        boolean b = chapterService.updateById(chapter);
        if (!b){
            throw new MyException(20001,"修改失败");
        }
        return JsonResult.success().message("修改章节成功");
    }

    /**
     * 删除章节  当有小节是不允许删除
     * @param id 章节id
     */
    @DeleteMapping("{id}")
    public JsonResult<Void> deleteChapter(@PathVariable String id){
        return chapterService.deleteChapter(id);
    }


    /**
     * 根据课程id获取课程大纲列表
     *
     * @param courseId 课程id
     * @return 课程大纲列表
     */
    @GetMapping("/getAllChapterVo/{courseId}")
    public JsonResult<List<ChapterVO>> getAllChapterVo(@PathVariable String courseId){
        return JsonResult.success(chapterService.getChapterVoByCourseId(courseId));
    }

}

