package com.yby.sys.service;

import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.NewsVo;

/**
 * 公告管理的服务接口
 *
 * @author 杨不易
 */
public interface NewsService {

    /* 加载公告列表返回layui表格数据格式 */
    DataGridView queryAllNews(NewsVo NewsVo);

    /* 添加公告 */
    ResultObj addNews(NewsVo NewsVo);

    /* 修改公告 */
    ResultObj updateNews(NewsVo NewsVo);


    /**
     * 根据id删除指定公告
     *
     * @param NewsId
     * @return
     */
    ResultObj deleteNews(Integer NewsId);

    /**
     * 根据数组遍历 删除指定公告 批量删除
     *
     * @param ids
     * @return
     */
    ResultObj deleteBatchDelete(Integer[] ids);

    /**
     * \根据id查询公告
     * @param Integer
     * @param id
     * @return
     */
    DataGridView loadNewsById(Integer id);
}