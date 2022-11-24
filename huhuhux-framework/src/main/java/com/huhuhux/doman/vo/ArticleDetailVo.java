package com.huhuhux.doman.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //
    private Long categoryId;

    private String content;
    //所属分类
    private String categoryName;

    private String isComment;

    private Long viewCount;


}
