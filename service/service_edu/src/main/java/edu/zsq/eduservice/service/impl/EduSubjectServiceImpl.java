package edu.zsq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.excel.SubjectData;
import edu.zsq.eduservice.entity.vo.subject.OneSubject;
import edu.zsq.eduservice.entity.vo.subject.TwoSubject;
import edu.zsq.eduservice.listener.SubjectExcelListener;
import edu.zsq.eduservice.mapper.EduSubjectMapper;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.utils.exception.servicexception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-15
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程  通过读取上传的excel文件写入到数据库中
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

//        获取文件输入流  参数形式会自动关闭流
        try (InputStream inputStream = file.getInputStream()) {

            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();

        } catch (IOException e) {
            throw new MyException(20001, "文件处理失败");
        } catch (NullPointerException e) {
            throw new MyException(20001, "上传文件失败,请重新上传");
        }

    }

    /**
     * 获取所有分类信息方法二
     * @return
     */
    @Override
    public List<OneSubject> getAllSubject() {

//      1  查询所有一级分类  parent_id=0
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

//       2 查询所有二级分类 parent_id != 0

        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

//        创建list集合用于最终封装的数据
        ArrayList<OneSubject> finalOneSubjectList = new ArrayList<>();

        for (EduSubject one:oneSubjectList) {

            ArrayList<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            for (EduSubject two:twoSubjectList) {
                TwoSubject twoSubject = new TwoSubject();
                if (two.getParentId() .equals(one.getId())){
                    BeanUtils.copyProperties(two,twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }

            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one,oneSubject);
            oneSubject.setChildren(finalTwoSubjectList);
            finalOneSubjectList.add(oneSubject);
        }



        return finalOneSubjectList;
    }

    /**
     * 获取所有分类信息方法一
     *
     * @return 通过OneSubject/TwoSubject实体类封装为前端需要的格式
     */

    public List<OneSubject> getAllSubject1() {


//      一级分类条件
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
//      根据一级分类条件  查询并获取到所有一级分类
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);


//      oneSubjectArrayList集合:将每个一级分类的所有二级分类 封装进一级分类集合中 此为前端所需要的格式
        ArrayList<OneSubject> finalOneSubjectArrayList = new ArrayList<>();

        String id = "";

        for (EduSubject one : oneSubjects) {
            OneSubject oneSubject = new OneSubject();

//            获取一级分类id
            id = one.getId();

//        根据获取到的一级分类id 查询并获取到该一级分类有哪些二级分类 并放进二级分类list集合中
            QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
//        二级分类条件
            twoWrapper.eq("parent_id", id);
            List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

            ArrayList<TwoSubject> finalTwoSubjectArrayList = new ArrayList<>();

            for (EduSubject two : twoSubjects) {
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(two, twoSubject);
                finalTwoSubjectArrayList.add(twoSubject);
            }

//            对Object2.setId(Object1.getId())的简写
            BeanUtils.copyProperties(one, oneSubject);
            oneSubject.setChildren(finalTwoSubjectArrayList);
            finalOneSubjectArrayList.add(oneSubject);
        }

        return finalOneSubjectArrayList;
    }
}
