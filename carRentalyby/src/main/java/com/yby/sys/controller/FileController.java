package com.yby.sys.controller;

import com.yby.sys.utils.AppFileUtils;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author LJH
 */
@Controller
@RequestMapping("file")
public class FileController {
    /**
     * 文件上传
     * 上传一个文件保存到E://upload
     *
     * @throws IOException
     */
    @RequestMapping("upload")
    @ResponseBody
    public DataGridView upload03(MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("进来了");
        System.out.println(file);
        System.out.println(file.getContentType());//文件的类型
        System.out.println(file.getName());//表单的name属性值
        System.out.println(file.getOriginalFilename());//文件名
        System.out.println(file.getSize());//文件大小
        System.out.println(file.getInputStream());//文件流

        //文件上传的父目录
        String parentPath = AppFileUtils.PATH;
        //得到当前日期作为文件夹名称
        String dirName = RandomUtils.getCurrentDateForString();
        //构造文件夹对象
        File dirFile = new File(parentPath, dirName);
        if (!dirFile.exists()) {
            dirFile.mkdirs();//创建文件夹
        }
        //得到文件原名
        String oldName = file.getOriginalFilename();
        //根据文件原名得到新名
        String newName = RandomUtils.createFileNameUseTime(oldName);
        File dest = new File(dirFile, newName);
        file.transferTo(dest);

        Map<String, Object> map = new HashMap<>();
//        data.put("src", "file/downloadFile?path=" +"upload/"+ dirName + "/" + newName);
        map.put("src", "/yby/upload/"+ dirName + "/" + newName);
        map.put("title", newName);
        map.put("path", "/yby/upload/"+dirName + "/" + newName);
        return new DataGridView(map);
    }

    /**
     * 下载的方法
     */
    @RequestMapping("downloadFile")
    public ResponseEntity<Object> downloadFile(@RequestParam("path") String path, HttpServletResponse response) {
        //3,拿到文件的老名字
        return AppFileUtils.downloadFile(response, path, "");
    }

}
