package com.yby.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.sys.domain.News;
import com.yby.sys.domain.SysUser;
import com.yby.sys.mapper.NewsMapper;
import com.yby.sys.service.NewsService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.utils.WebUtils;
import com.yby.sys.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/* 我们使用了aop切面 实现了事务管理 所有不需要 @Transactional  声明 */
@Service
public class NewsServiceImpl implements NewsService {

    // News dao层
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 查询公告
     *
     * @param newsVo
     * @return
     */
    @Override
    public DataGridView queryAllNews(NewsVo newsVo) {
        Page<Object> page = PageHelper.startPage(newsVo.getPage(), newsVo.getLimit());
        List<News> data = this.newsMapper.queryAllNews(newsVo);
        return new DataGridView(page.getTotal(), data);
    }

    /**
     * 添加公告
     *
     * @param newsVo
     */
    @Override
    public ResultObj addNews(NewsVo newsVo) {
        try {
            newsVo.setCreatetime(new Date());
            SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
            newsVo.setOpername(user.getRealname());
            this.newsMapper.insertSelective(newsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改广告
     *
     * @param newsVo
     */
    @Override
    public ResultObj updateNews(NewsVo newsVo) {
        try {
            newsVo.setCreatetime(new Date());
            this.newsMapper.updateByPrimaryKeySelective(newsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单个公告
     *
     * @param newsid
     */
    @Override
    public ResultObj deleteNews(Integer newsid) {
        try {
            this.newsMapper.deleteByPrimaryKey(newsid);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 批量删除公告
     *
     * @param ids
     */
    @Override
    public ResultObj deleteBatchDelete(Integer[] ids) {
        try {
            for (Integer id : ids) {
                this.deleteNews(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }


    }

    @Override
    public DataGridView loadNewsById(Integer id) {
        News news = this.newsMapper.selectByPrimaryKey(id);
        return new DataGridView(news);
    }
}