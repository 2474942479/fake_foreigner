package edu.zsq.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.excel.SubjectData;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.utils.exception.servicexception.MyException;

import java.util.Map;

/**
 * 课程excel表监听类
 * @author 张
 *
 *  自定义监听类(在web容器中 )  无法交给spring管理(spring容器)
 *  web容器是管理servlet，以及监听器(Listener)和过滤器(Filter)的。这些都是在web容器的掌控范围里。但他们不在spring和springmvc的掌控范围里。
 */

public class SubjectExcelListener  extends AnalysisEventListener<SubjectData> {

    public EduSubjectService eduSubjectService;
    public SubjectExcelListener(){}

    /**
     * 通过有参构造获取到EduSubjectService
     * @param eduSubjectService
     */
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 从数据库中查询 该类别是否存在
     * @param eduSubjectService
     * @param title  类别名称(分类名称)
     * @param pid   父ID(分类id)
     * @return
     */

    public EduSubject existSubject(EduSubjectService eduSubjectService,String title,String pid ){
        QueryWrapper<EduSubject> wapper = new QueryWrapper<>();
        wapper.eq("title",title);
        wapper.eq("parent_id",pid);
        EduSubject subject = eduSubjectService.getOne(wapper);
        return subject;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    /**
     * 一行一行读取excel中数据 并将其添加到数据库
     * @param data  从excel文件中读取的数据 excel实体类模型封装成SubjectData类型
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData data, AnalysisContext analysisContext) {

        if (data == null){
            throw new MyException(20001,"excel文件为空");
        }

//        逐行读取 每行两个值 1一级分类  2二级分类
//        判断一级分类是否存在  一级分类pid为0
        EduSubject oneSubject = this.existSubject(eduSubjectService, data.getOneSubjectName(), "0");
        if (oneSubject==null){
            //不重复就进行添加
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(data.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }

//       获取一级分类id值  不论一级分类是否存在  不存在则在上一步添加上
        String pid = oneSubject.getId();
//       判断二级分类是否存在  二级分类pid为一级分类id
        EduSubject twoSubject = this.existSubject(eduSubjectService, data.getTwoSubjectName(), pid);

        if (twoSubject==null){
            //不重复就进行添加
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(data.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }



    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
