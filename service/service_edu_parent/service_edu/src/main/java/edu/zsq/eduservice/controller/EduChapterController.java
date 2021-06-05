package edu.zsq.eduservice.controller;


import edu.zsq.eduservice.entity.dto.ChapterDTO;
import edu.zsq.eduservice.entity.vo.chapter.ChapterInfoVO;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.service_edu_api.entity.vo.ChapterVO;
import edu.zsq.utils.result.JsonResult;
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
     * 添加或修改章节
     *
     * @param chapterDTO 章节信息
     * @return 操作结果
     */
    @PostMapping("/saveOrUpdateChapter")
    public JsonResult<Void> saveOrUpdateChapter(@RequestBody ChapterDTO chapterDTO) {
        chapterService.saveOrUpdateChapter(chapterDTO);
        return JsonResult.OK;
    }

    /**
     * 根据章节id查询
     *
     * @param id 章节id
     * @return 章节信息
     */
    @GetMapping("/getChapterInfo/{id}")
    public JsonResult<ChapterInfoVO> getChapterInfo(@PathVariable String id) {
        return JsonResult.success(chapterService.getChapterInfo(id));
    }

    /**
     * 删除章节  当有小节是不允许删除
     *
     * @param id 章节id
     */
    @DeleteMapping("/removeChapter/{id}")
    public JsonResult<Void> deleteChapter(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return JsonResult.OK;
    }

}

