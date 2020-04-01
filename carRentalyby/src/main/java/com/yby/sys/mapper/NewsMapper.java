package com.yby.sys.mapper;

import com.yby.sys.domain.News;
import com.yby.sys.vo.NewsVo;

import java.util.List;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    /* 查询全部公告 */
    List<News> queryAllNews(NewsVo newsVo);
}