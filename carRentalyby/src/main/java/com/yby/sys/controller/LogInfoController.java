package com.yby.sys.controller;

import com.yby.sys.service.LogInfoService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.LogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 日志管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("logInfo")
public class LogInfoController {

    @Autowired
    private LogInfoService LogInfoService;

    /* 加载日志列表返回layui表格数据格式 */
    @RequestMapping("loadAllLogInfo")
    public DataGridView loadAllLogInfo(LogInfoVo LogInfoVo) {
        return this.LogInfoService.queryAllLogInfo(LogInfoVo);
    }

    /**
     * 删除日志
     *
     * @param LogInfoVo
     * @return
     */
    @RequestMapping("deleteLogInfo")
    public ResultObj deleteLogInfo(LogInfoVo LogInfoVo) {
        try {
            this.LogInfoService.deleteLogInfo(LogInfoVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除日志
     *
     * @param LogInfoVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    public ResultObj deleteBatchDelete(LogInfoVo LogInfoVo) {
        try {
            this.LogInfoService.deleteBatchDelete(LogInfoVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}