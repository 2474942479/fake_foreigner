package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVo;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/eduService/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/saveChapter")
    public MyResultUtils saveChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return MyResultUtils.ok().message("添加章节成功");
    }

    /**
     * 根据章节id查询
     * @param id
     * @return
     */
    @GetMapping("/getChapter/{id}")
    public MyResultUtils getChapter(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return MyResultUtils.ok().data("chapter",chapter);
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PutMapping("/updateChapter")
    public MyResultUtils updateChapter(@RequestBody EduChapter chapter){
        boolean b = chapterService.updateById(chapter);
        if (!b){
            throw new MyException(20001,"修改失败");
        }
        return MyResultUtils.ok().message("修改章节成功");
    }

    /**
     * 删除章节  当有小节是不允许删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public MyResultUtils deleteChapter(@PathVariable String id){

        Boolean flag = chapterService.deleteChapter(id);
        if (flag){
            return MyResultUtils.ok().message("删除章节成功");
        }else {
            return MyResultUtils.error().message("删除章节失败");
        }
    }


    /**
     * 根据课程id获取课程大纲列表
     * @return
     */
    @GetMapping("/getAllChapterVo/{courseId}")
    public MyResultUtils getAllChapterVo(@PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.getChapterVoByCourseId(courseId);

        return MyResultUtils.ok().data("chapterVoList",chapterVoList);
    }

}

