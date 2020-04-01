package com.yby.sys.task;

import com.yby.sys.constast.SysConstast;
import com.yby.sys.utils.AppFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author TeouBle
 */
@Component
@EnableScheduling // 开启定时任务
public class RecycleTempFileTask {


    /**
     * 每天晚上12执行
     * Scheduled 定时时间
     */
    @Scheduled(cron = "0 0 0 * * ? ")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void recycleTempFile() {
        // 获取当前图片存储地址
        File file = new File(AppFileUtils.PATH);
        // 进行删除业务
        deleteFile(file);
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


    /**
     * 删除图片
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (null != file) {
            File[] listFiles = file.listFiles();
            if (null != listFiles && listFiles.length > 0) {
                for (File f : listFiles) {
                    System.out.println(f);
                    // 进行删除  后缀 _temp
                    if (f.getName().endsWith(SysConstast.FILE_UPLOAD_TEMP)) {
                        f.delete();
                    } else {
                        //如果是文件夹，在递归删除一次
                        deleteFile(f);
                    }
                }
            }
        }

    }


    /*
     * 可以实现定时删除检查单之类的
     * */


}