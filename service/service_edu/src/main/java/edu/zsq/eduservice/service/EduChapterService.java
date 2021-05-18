package edu.zsq.eduservice.service;

import edu.zsq.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.utils.result.JsonResult;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 课程大纲列表
     * @param courseId 课程id
     * @return  课程大纲list集合
     */

    List<ChapterVO> getAllChapterVO(String courseId);

    /**
     * 删除章节  当有小节是不允许删除
     * @param chapterId 章节id
     */
    JsonResult<Void> deleteChapter(String chapterId);

    /**
     * 根据根据课程id删除课程小节
     * @param courseId 课程id
     * @return 课程小节删除结果
     */
    boolean removeChapterByCourseId(String courseId);
}
