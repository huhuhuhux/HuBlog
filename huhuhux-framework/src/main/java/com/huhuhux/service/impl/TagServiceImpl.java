package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhuhux.doman.entity.Tag;
import com.huhuhux.mapper.TagMapper;
import com.huhuhux.service.TagService;
import org.springframework.stereotype.Service;


/**
 * 标签(Tag)表服务实现类
 *
 * @author huhuhux
 * @since 2022-12-25 10:43:05
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

