package edu.zsq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.EduSubject;
import edu.zsq.eduservice.entity.dto.SubjectDTO;
import edu.zsq.eduservice.entity.excel.SubjectData;
import edu.zsq.eduservice.entity.vo.SubjectTree;
import edu.zsq.eduservice.entity.vo.SubjectVO;
import edu.zsq.eduservice.listener.SubjectExcelListener;
import edu.zsq.eduservice.mapper.EduSubjectMapper;
import edu.zsq.eduservice.service.EduSubjectService;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import edu.zsq.utils.tree.TreeShapeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public JsonResult<Void> saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

//        获取文件输入流  参数形式会自动关闭流
        try (InputStream inputStream = file.getInputStream()) {
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            throw ExFactory.throwSystem("excel文件解析失败");
        }

        return JsonResult.OK;
    }

    @Override
    public SubjectVO getSubjectInfo(String id) {
        return convert2SubjectVO(lambdaQuery()
                .eq(EduSubject::getId, id)
                .last(Constants.LIMIT_ONE)
                .one());
    }

    @Override
    public List<SubjectTree> getAllSubject() {
        List<SubjectTree> treeNodes = lambdaQuery()
                .list()
                .parallelStream()
                .map(this::convert2SubjectTree)
                .collect(Collectors.toList());

        return TreeShapeUtil.build(treeNodes, "0");
    }


    @Override
    public void saveOrUpdateSubject(SubjectDTO subjectDTO) {
        if (!saveOrUpdate(convert2Subject(subjectDTO))) {
            throw ExFactory.throwBusiness("操作课程分类失败！");
        }
    }

    @Override
    public void deleteSubject(String id) {
        List<String> ids = lambdaQuery()
                .eq(EduSubject::getParentId, id)
                .select(EduSubject::getId)
                .list()
                .parallelStream()
                .map(EduSubject::getId)
                .collect(Collectors.toList());
        ids.add(id);

        if (!ids.isEmpty() && !removeByIds(ids)) {
            throw ExFactory.throwBusiness("删除课程失败");
        }
    }


    private EduSubject convert2Subject(SubjectDTO subjectDTO) {
        EduSubject eduSubject = new EduSubject();
        eduSubject.setId(subjectDTO.getId());
        eduSubject.setParentId(subjectDTO.getParentId());
        eduSubject.setTitle(subjectDTO.getTitle());
        return eduSubject;
    }

    private SubjectVO convert2SubjectVO(EduSubject subject) {
        return SubjectVO.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .parentId(subject.getParentId())
                .build();
    }

    private SubjectTree convert2SubjectTree(EduSubject subject) {
        SubjectTree subjectTree = new SubjectTree();
        subjectTree.setId(subject.getId());
        subjectTree.setParentId(subject.getParentId());
        subjectTree.setTitle(subject.getTitle());
        return subjectTree;
    }
}
