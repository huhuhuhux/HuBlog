package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.constants.SystemConstants;
import com.huhuhux.doman.ResponseResult;
import com.huhuhux.doman.entity.Link;
import com.huhuhux.doman.vo.LinkVo;
import com.huhuhux.mapper.LinkMapper;
import com.huhuhux.service.LinkService;
import com.huhuhux.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 15:10:50
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper();

        linkWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> links = list(linkWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.beanCopyList(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}

