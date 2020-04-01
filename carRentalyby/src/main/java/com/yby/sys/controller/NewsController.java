package com.yby.sys.controller;

import com.yby.sys.service.NewsService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 公告管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /* 加载公告列表返回layui表格数据格式 */
    @RequestMapping("loadAllNews")
    public DataGridView loadAllNews(NewsVo NewsVo) {
        return this.newsService.queryAllNews(NewsVo);
    }


    /**
     * 添加公告
     *
     * @param newsVo
     */
    @RequestMapping("addNews")
    public ResultObj addNews(NewsVo newsVo) {
        return this.newsService.addNews(newsVo);
    }

    /**
     * 修改广告
     *
     * @param newsVo
     */
    @RequestMapping("updateNews")
    public ResultObj updateNews(NewsVo newsVo) {
        return this.newsService.updateNews(newsVo);
    }

    /**
     * 删除公告
     *
     * @param NewsVo
     * @return
     */
    @RequestMapping("deleteNews")
    public ResultObj deleteNews(NewsVo NewsVo) {
        return this.newsService.deleteNews(NewsVo.getId());
    }

    /**
     * 批量删除公告
     *
     * @param NewsVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    public ResultObj deleteBatchDelete(NewsVo NewsVo) {
        return this.newsService.deleteBatchDelete(NewsVo.getIds());
    }

    /**
     * \根据id查询公告
     * @param newsVo
     * @return
     */
    @RequestMapping("loadNewsById")
    public DataGridView loadNewsById(NewsVo newsVo){
        return this.newsService.loadNewsById(newsVo.getId());
    }
}