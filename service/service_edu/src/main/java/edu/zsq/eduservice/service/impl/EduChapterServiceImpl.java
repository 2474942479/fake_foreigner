package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduChapter;
import edu.zsq.eduservice.entity.EduVideo;
import edu.zsq.eduservice.entity.vo.chapter.ChapterVo;
import edu.zsq.eduservice.entity.vo.chapter.VideoVo;
import edu.zsq.eduservice.mapper.EduChapterMapper;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.exception.servicexception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    /**
     * 课程大纲列表
     *
     * @param courseId 课程id
     * @return 课程大纲list集合
     */
    @Override
    public List<ChapterVo> getChapterVoByCourseId(String courseId) {

        //        查询并获取章节所有信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);

//        查询并获得小节所有信息

        List<EduVideo> videoList = videoService.getAllVideoByCourseId(courseId);

        ArrayList<ChapterVo> finalList = new ArrayList<>();

        for (EduChapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            ArrayList<VideoVo> ChapterVideo = new ArrayList<>();
            for (EduVideo video : videoList) {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    ChapterVideo.add(videoVo);
                }
            }

            BeanUtils.copyProperties(chapter, chapterVo);
//            给章节内添加小节列表
            chapterVo.setChildren(ChapterVideo);
            finalList.add(chapterVo);
        }


        return finalList;
    }

    /**
     * 删除章节  当有小节是不允许删除
     * @param chapterId
     * @return
     */
    @Override
    public Boolean deleteChapter(String chapterId) {

        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id",chapterId);
        int videoCount = videoService.count(videoWrapper);
        if (videoCount > 0){
            throw new MyException(20001,"请先删除掉所有小节!");
        }

        int result = baseMapper.deleteById(chapterId);
        return result>0;
    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        int delete = baseMapper.delete(chapterWrapper);
        return delete>0;
    }
}
