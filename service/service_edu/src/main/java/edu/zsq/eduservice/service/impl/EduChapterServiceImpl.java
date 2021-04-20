package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVO;
import edu.zsq.eduservice.entity.vo.chapter.VideoVO;
import edu.zsq.eduservice.mapper.EduChapterMapper;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    EduVideoService videoService;

    /**
     * 课程大纲列表
     *
     * @param courseId 课程id
     * @return 课程大纲list集合
     */
    @Override
    public List<ChapterVO> getChapterVoByCourseId(String courseId) {

        // 查询并获取章节所有信息
        List<EduChapter> chapterList = lambdaQuery()
                .eq(EduChapter::getCourseId, courseId)
                .list();

        // 查询并获得小节所有信息
        List<VideoVO> videoList = videoService.getAllVideoByCourseId(courseId);
        return chapterList.stream().map(eduChapter -> buildChapterVO(eduChapter, videoList)).collect(Collectors.toList());

    }

    private ChapterVO buildChapterVO(EduChapter eduChapter, List<VideoVO> videoList) {
        ChapterVO chapterVO = new ChapterVO();
        String id = eduChapter.getId();

        List<VideoVO> child = videoList.stream()
                .filter(videoVO -> id.equals(videoVO.getChapterId()))
                .collect(Collectors.toList());

        chapterVO.setId(id);
        chapterVO.setTitle(eduChapter.getTitle());
        // 给章节内添加小节列表
        chapterVO.setChildren(child);

        return chapterVO;
    }

    /**
     * 删除章节  当有小节是不允许删除
     *
     * @param chapterId 章节id
     * @return 删除结果
     */
    @Override
    public JsonResult<Void> deleteChapter(String chapterId) {

        if (videoService.lambdaQuery().eq(EduVideo::getChapterId, chapterId).count() > 0) {
            throw ExFactory.throwBusiness("请先删除掉该章节下的所有小节!");
        }

        if (baseMapper.deleteById(chapterId) <= 0) {
            throw ExFactory.throwBusiness("删除失败！");
        }

        return JsonResult.OK;
    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        int delete = baseMapper.delete(chapterWrapper);
        return delete > 0;
    }
}
