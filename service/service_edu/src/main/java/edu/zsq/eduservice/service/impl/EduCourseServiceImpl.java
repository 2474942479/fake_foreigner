package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduCourse;
import edu.zsq.eduservice.entity.EduCourseDescription;
import edu.zsq.eduservice.entity.vo.CourseInfoVo;
import edu.zsq.eduservice.entity.vo.CourseQuery;
import edu.zsq.eduservice.entity.vo.FinalReleaseVo;
import edu.zsq.eduservice.mapper.EduCourseMapper;
import edu.zsq.eduservice.service.EduChapterService;
import edu.zsq.eduservice.service.EduCourseDescriptionService;
import edu.zsq.eduservice.service.EduCourseService;
import edu.zsq.eduservice.service.EduVideoService;
import edu.zsq.utils.exception.servicexception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-16
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;


    /**
     * 添加课程基本信息
     *
     * @param courseInfoVo 课程基本信息
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

//      1  添加课程基本信息到课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            throw new MyException(20001, "添加课程信息失败");
        }

        String courseId = eduCourse.getId();

//        2添加课程简介到课程简介表

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        //        (手动设置外键——程序控制数据一致性)获取添加之后课程id
        eduCourseDescription.setId(courseId);
        courseDescriptionService.save(eduCourseDescription);


        return courseId;
    }

    /**
     * 回显课程基本信息
     *
     * @param id 课程id
     * @return 课程基本信息VO类
     */
    @Override
    public CourseInfoVo getCourseInfoVoById(String id) {

        EduCourse course = baseMapper.selectById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

//        通过课程id获取课程简介
        String courseDescription = courseDescriptionService.getById(id).getDescription();
        courseInfoVo.setDescription(courseDescription);

        return courseInfoVo;
    }

    /**
     * 修改课程基本信息
     *
     * @param courseInfoVo 课程基本信息VO类
     * @return
     */
    @Override
    public void updateCourseInfoVo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new MyException(20001,"修改课程基本信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = courseDescriptionService.updateById(eduCourseDescription);
        if (!b){
            throw new MyException(20001,"修改课程简介失败");
        }
    }

    /**
     * 根据课程id查询返回最终发布信息
     * @param id
     * @return
     */
    @Override
    public FinalReleaseVo getFinalReleaseVo(String id) {

        FinalReleaseVo finalReleaseVo = baseMapper.getFinalReleaseVo(id);
        return finalReleaseVo;
    }

    /**
     * 条件分页查询
     * @param page 分页条件
     * @param courseQuery   课程条件
     */
    @Override
    public void pageQuery(Page<EduCourse> page, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //        坑 id为表中字段名

        wrapper.orderByDesc("gmt_create");
        if (courseQuery != null) {
            String title = courseQuery.getTitle();
            Integer status = courseQuery.getStatus();
            String begin = courseQuery.getBegin();
            String end = courseQuery.getEnd();

            if (!StringUtils.isEmpty(title)) {
                wrapper.like("title", title);
            }
            if (status != null) {
                wrapper.eq("status", status);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.le("gmt_modified", end);
            }
        }

//继承的ServiceImpl 自动注入了baseMapper
        baseMapper.selectPage(page, wrapper);
    }

    @Override
    public Boolean removeCourseAllById(String courseId) {
//      1  根据课程id删除小节
        boolean removeVideo = videoService.removeVideoByCourseId(courseId);

//      2  根据课程id删除章节
        boolean removeChapter = chapterService.removeChapterByCourseId(courseId);

//      3  根据课程id删除课程描述
        boolean removeDesc = courseDescriptionService.removeById(courseId);

//      4  根据课程id删除课程基本信息
        int delete = baseMapper.deleteById(courseId);

        if (delete==0){
            throw new MyException(20001,"删除课程基本信息失败");
        }

        if (!removeDesc){
            throw new MyException(20001,"删除课程简介失败");
        }
        if (!removeChapter){
            throw new MyException(20001,"删除课程章节失败");
        }
        if (!removeVideo){
            throw new MyException(20001,"删除课程小节失败");
        }
            return true;

    }
}
