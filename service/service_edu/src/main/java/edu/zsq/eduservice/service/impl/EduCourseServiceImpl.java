package edu.zsq.eduservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import edu.zsq.eduservice.entity.EduCourse;
import edu.zsq.eduservice.entity.EduCourseDescription;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.dto.query.CourseQueryDTO;
import edu.zsq.eduservice.entity.dto.CourseDTO;
import edu.zsq.eduservice.entity.vo.CourseVO;
import edu.zsq.eduservice.entity.vo.FinalReleaseVO;
import edu.zsq.eduservice.mapper.EduCourseMapper;
import edu.zsq.eduservice.service.*;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-16
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Resource
    private EduSubjectService subjectService;
    @Resource
    private EduCourseDescriptionService courseDescriptionService;
    @Resource
    private EduChapterService chapterService;
    @Resource
    private EduVideoService videoService;
    @Resource
    private OssService ossService;


    /**
     * 添加课程基本信息
     *
     * @param courseDTO 课程基本信息
     * @return 添加结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public String saveCourse(CourseDTO courseDTO) {

//      1  添加课程基本信息到课程表
        EduCourse eduCourse = convertCourseDTO(courseDTO);
        if (!save(eduCourse)) {
            throw ExFactory.throwSystem("系统错误，添加课程信息失败");
        }

//        2添加课程简介到课程简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseDTO.getDescription());

        // (手动设置外键——程序控制数据一致性) 获取添加之后课程id
        eduCourseDescription.setId(eduCourse.getId());
        if (!courseDescriptionService.save(eduCourseDescription)) {
            throw ExFactory.throwSystem("系统错误，课程详情信息添加失败");
        }

        return eduCourse.getId();
    }

    /**
     * 根据课程id获取单个课程基本信息
     *
     * @param id 课程id
     * @return 课程基本信息
     */
    @Override
    public CourseVO getCourseInfoById(String id) {
        return baseMapper.getCourseById(id);
    }

    /**
     * 修改课程基本信息
     *
     * @param courseDTO 课程基本信息VO类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public void updateCourseDTO(CourseDTO courseDTO) {

        boolean update = lambdaUpdate()
                .eq(EduCourse::getId, courseDTO.getId())
                .set(EduCourse::getCover, courseDTO.getCover())
                .update(convertCourseDTO(courseDTO));
        if (!update) {
            throw ExFactory.throwSystem("服务器异常，修改课程基本信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseDTO.getId());
        eduCourseDescription.setDescription(courseDTO.getDescription());
        if (!courseDescriptionService.updateById(eduCourseDescription)) {
            throw ExFactory.throwSystem("服务器异常，修改课程简介失败");
        }
    }

    /**
     * 根据课程id查询返回最终发布信息
     *
     * @param id 课程id
     * @return 最终发布信息
     */
    @Override
    public FinalReleaseVO getFinalReleaseVo(String id) {
        FinalReleaseVO finalReleaseVO = baseMapper.getFinalReleaseVO(id);
        List<String> subjectIds = JSON.parseArray(finalReleaseVO.getSubjectIds(), String.class);
        String subjectName = subjectService.lambdaQuery()
                .in(EduSubject::getId, subjectIds)
                .select(EduSubject::getTitle)
                .orderByAsc(EduSubject::getId)
                .list()
                .stream()
                .map(EduSubject::getTitle)
                .collect(Collectors.joining("-"));

        finalReleaseVO.setSubjectName(subjectName);
        return finalReleaseVO;
    }

    /**
     * 分页查询课程信息
     *
     * @param courseQueryDTO 查询条件
     * @return 课程信息
     */
    @Override
    public PageData<CourseVO> getCourseListPage(CourseQueryDTO courseQueryDTO) {

        Page<EduCourse> coursePage = new Page<>(courseQueryDTO.getCurrent(), courseQueryDTO.getSize());

        String title = Optional.ofNullable(courseQueryDTO.getTitle()).map(String::toLowerCase).orElse(null);
        String status = courseQueryDTO.getStatus();
        LocalDateTime begin = courseQueryDTO.getBegin();
        LocalDateTime end = courseQueryDTO.getEnd();

        lambdaQuery()
                .apply(StringUtils.isNotBlank(title), "lower(title) like '%" + title + "%'")
                .eq(StringUtils.isNotBlank(status), EduCourse::getStatus, status)
                .ge(Objects.nonNull(begin), EduCourse::getGmtCreate, begin)
                .le(Objects.nonNull(end), EduCourse::getGmtModified, end)
                .orderByDesc(EduCourse::getGmtCreate)
                .page(coursePage);

        if (coursePage.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<CourseVO> courseList = coursePage
                .getRecords()
                .stream()
                .map(this::convertEduCourse)
                .collect(Collectors.toList());

        return PageData.of(courseList, coursePage.getCurrent(), coursePage.getSize(), coursePage.getTotal());

    }

    @Override
    public JsonResult<Void> updateReleaseStatus(CourseDTO courseDTO) {

        boolean updateResult = lambdaUpdate()
                .eq(EduCourse::getId, courseDTO.getId())
                .set(StringUtils.isNotBlank(courseDTO.getStatus()), EduCourse::getStatus, courseDTO.getStatus())
                .update();

        if (!updateResult) {
            throw ExFactory.throwSystem("系统异常，课程发布状态修改失败");
        }
        return JsonResult.OK;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Exception.class)
    public JsonResult<Void> removeCourseAllById(String courseId) {

        // 根据课程id删除小节以及视频
        if (!videoService.removeVideoByCourseId(courseId)) {
            throw ExFactory.throwSystem("系统异常, 删除课程小节失败");
        }

        // 根据课程id删除章节
        if (!chapterService.removeChapterByCourseId(courseId)) {
            throw ExFactory.throwSystem("系统异常, 删除课程章节失败");
        }

        // 根据课程id删除课程描述
        if (courseDescriptionService.getById(courseId) != null
                && !courseDescriptionService.removeById(courseId)) {
            throw ExFactory.throwSystem("系统异常, 删除课程简介失败");
        }

        // 删除OSS服务器上对应的课程封面
        // 根据课程id删除课程基本信息
        EduCourse courseInfo = getById(courseId);
        if (courseInfo != null) {
            ossService.removeBatchOssFile(Lists.newArrayList(courseInfo.getCover()));
        }

        if (!removeById(courseId)) {
            throw ExFactory.throwSystem("系统异常, 删除课程基本信息失败");
        }

        return JsonResult.OK;
    }

    @Override
    public List<CourseVO> getAllCourse() {
        return lambdaQuery().list().stream()
                .map(this::convertEduCourse)
                .collect(Collectors.toList());
    }

    private CourseVO convertEduCourse(EduCourse eduCourse) {
        return CourseVO.builder()
                .id(eduCourse.getId())
                .cover(eduCourse.getCover())
                .buyCount(eduCourse.getBuyCount())
                .lessonNum(eduCourse.getLessonNum())
                .price(eduCourse.getPrice())
                .reductionMoney(eduCourse.getReductionMoney())
                .subjectIds(eduCourse.getSubjectIds())
                .teacherId(eduCourse.getTeacherId())
                .viewCount(eduCourse.getViewCount())
                .title(eduCourse.getTitle())
                .description(Optional.ofNullable(courseDescriptionService.getById(eduCourse.getId()))
                        .map(EduCourseDescription::getDescription)
                        .orElse(null))
                .gmtCreate(eduCourse.getGmtCreate())
                .status(eduCourse.getStatus())
                .build();
    }

    private EduCourse convertCourseDTO(CourseDTO courseDTO) {

        EduCourse eduCourse = new EduCourse();
        eduCourse.setCover(courseDTO.getCover());
        eduCourse.setLessonNum(courseDTO.getLessonNum());
        eduCourse.setPrice(courseDTO.getPrice());
        eduCourse.setReductionMoney(courseDTO.getReductionMoney());
        eduCourse.setSubjectIds(courseDTO.getSubjectIds());
        eduCourse.setTeacherId(courseDTO.getTeacherId());
        eduCourse.setTitle(courseDTO.getTitle());
        eduCourse.setStatus(courseDTO.getStatus());
        return eduCourse;
    }

}
