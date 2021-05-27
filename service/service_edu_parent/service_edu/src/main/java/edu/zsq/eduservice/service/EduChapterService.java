package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.dto.ChapterDTO;
import edu.zsq.eduservice.entity.vo.chapter.ChapterInfoVO;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;

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
     * 添加章节信息
     *
     * @param chapterDTO 章节信息
     */
    void saveOrUpdateChapter(ChapterDTO chapterDTO);

    /**
     * 课程大纲列表
     *
     * @param courseId 课程id
     * @return 课程大纲集合
     */
    List<ChapterVO> getAllChapterVO(String courseId);

    /**
     * 删除章节  当有小节是不允许删除
     *
     * @param chapterId 章节id
     */
    void deleteChapter(String chapterId);

    /**
     * 根据根据课程id删除课程小节
     *
     * @param courseId 课程id
     * @return 课程小节删除结果
     */
    boolean removeChapterByCourseId(String courseId);

    /**
     * 根据章节id获取章节详细信息
     *
     * @param id 章节id
     * @return 章节详细信息
     */
    ChapterInfoVO getChapterInfo(String id);
}
