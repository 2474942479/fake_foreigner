package edu.zsq.cms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.cms.entity.EduComment;
import edu.zsq.cms.entity.dto.CommentDTO;
import edu.zsq.cms.entity.dto.CommentQueryDTO;
import edu.zsq.cms.entity.vo.CommentVO;
import edu.zsq.cms.mapper.EduCommentMapper;
import edu.zsq.cms.service.EduCommentService;
import edu.zsq.utils.exception.ErrorCode;
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
     * @param commentQueryDTO 查询条件
     * @return 评论列表
     */
    @Override
    public PageData<CommentVO> getCommentList(CommentQueryDTO commentQueryDTO) {

        Page<EduComment> page = new Page<>(commentQueryDTO.getCurrent(), commentQueryDTO.getSize());
        lambdaQuery()
                .eq(EduComment::getCourseId, commentQueryDTO.getCourseId())
                .orderByDesc(EduComment::getGmtModified)
                .page(page);

        if (page.getRecords().isEmpty()) {
            return PageData.empty();
        }

        List<CommentVO> comments = page.getRecords()
                .stream()
                .map(this::convertEduComment)
                .collect(Collectors.toList());

        return PageData.of(comments, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public void saveComment(CommentDTO commentDTO) {

        if (StringUtils.isBlank(commentDTO.getUserId())) {
            throw ExFactory.throwWith(ErrorCode.UNAUTHORIZED, "请先登录");
        }
        if (StringUtils.isBlank(commentDTO.getContent())) {
            throw ExFactory.throwBusiness("请输入评论内容");
        }

        if (!save(convertCommentDTO(commentDTO))) {
            throw ExFactory.throwSystem("系统异常，发表评论失败");
        }
    }

    private EduComment convertCommentDTO(CommentDTO commentDTO) {
        EduComment eduComment = new EduComment();
        eduComment.setUserId(commentDTO.getUserId());
        eduComment.setContent(commentDTO.getContent());
        eduComment.setTeacherId(commentDTO.getTeacherId());
        eduComment.setCourseId(commentDTO.getCourseId());
        eduComment.setAvatar(commentDTO.getAvatar());
        eduComment.setNickname(commentDTO.getNickname());

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
                .gmtCreate(eduComment.getGmtCreate())
                .build();
    }
}
