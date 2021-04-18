package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduComment;
import edu.zsq.cms.entity.dto.CommentDTO;
import edu.zsq.cms.entity.vo.CommentVO;
import edu.zsq.cms.mapper.EduCommentMapper;
import edu.zsq.cms.service.EduCommentService;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 根据课程id查询评论列表
     *
     * @param courseId 课程id
     * @return 评论列表
     */
    @Override
    public PageData<CommentVO> getCommentList(String courseId) {

        Page<EduComment> page = new Page<>();
        lambdaQuery()
                .eq(EduComment::getCourseId, courseId)
                .orderByDesc(EduComment::getGmtModified)
                .page(page);

        if (page.getRecords().isEmpty()) {
            return PageData.of(Collections.emptyList(), page.getCurrent(), page.getSize(), page.getTotal());
        }

        List<CommentVO> comments = page.getRecords().stream().map(this::convertEduComment).collect(Collectors.toList());

        return PageData.of(comments, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public JsonResult<Void> saveComment(CommentDTO commentDTO) {

        if (StringUtils.isEmpty(commentDTO.getUserId())){
            throw ExFactory.throwBusiness("请先登录");
        }
        if (StringUtils.isEmpty(commentDTO.getContent())){
            throw ExFactory.throwBusiness("请输入评论内容");
        }

        if (save(this.convertCommentDTO(commentDTO))) {
            throw ExFactory.throwSystem("系统异常，发表评论失败");
        }

        return JsonResult.OK;
    }

    private EduComment convertCommentDTO(CommentDTO commentDTO){
        EduComment eduComment = new EduComment();
        commentDTO.setUserId(commentDTO.getUserId());
        commentDTO.setContent(commentDTO.getContent());
        commentDTO.setTeacherId(commentDTO.getTeacherId());
        commentDTO.setCourseId(commentDTO.getCourseId());

        return eduComment;
    }

    private CommentVO convertEduComment(EduComment eduComment) {
        return CommentVO.builder()
                .id(eduComment.getId())
                .teacherId(eduComment.getTeacherId())
                .avatar(eduComment.getAvatar())
                .content(eduComment.getContent())
                .nickname(eduComment.getNickname())
                .userId(eduComment.getUserId())
                .courseId(eduComment.getCourseId())
                .build();
    }
}
