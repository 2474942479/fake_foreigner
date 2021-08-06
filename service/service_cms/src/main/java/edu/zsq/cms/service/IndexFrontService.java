package edu.zsq.cms.service;

import edu.zsq.cms.entity.vo.IndexFrontVO;

/**
 * @author 张
 */
public interface IndexFrontService {

    /**
     * 查询首页数据
     *
     * @return 首页数据
     */
    IndexFrontVO getIndexInfo();
}
