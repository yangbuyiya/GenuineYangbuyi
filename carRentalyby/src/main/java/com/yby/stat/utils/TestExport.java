package com.yby.stat.utils;

import com.yby.sys.vo.UserVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestExport {

    public static void main(String[] args) {

        List<UserVo> userVos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserVo userVo = new UserVo();
            userVo.setUserid((i + 1));
            userVo.setRealname("用户名称" + (i + 1));
            userVo.setPwd("123456");
            userVo.setAddress("湖南郴州");
            userVo.setSex(1);
            userVos.add(userVo);
        }

        export(userVos, "D:/users.xls");
    }

    public static void export(List<UserVo> userVos, String path) {
        // 1.创建工作簿
        HSSFWorkbook Workbook = new HSSFWorkbook();
        // 2.在工作簿创建sheets
        // Workbook.createSheet(); 只是创建名字为默认的 sheetx
        HSSFSheet sheet = Workbook.createSheet("第一个工作簿");
        // 3.设置sheet的相关设置
//        sheet.setColumnHidden(columnUndex,hidden); // 设置某一列是否隐藏
//        sheet.setColumnWidth(1,20*256); // 设置某一列的宽度
        //设置默认宽度
        sheet.setDefaultColumnWidth(20);
        // 设置默认行高
//        sheet.setDefaultRowHeight((short) (20 * 20));

        // 合并行
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 4);
        sheet.addMergedRegion(cellAddresses);
        CellRangeAddress cellAddresses2 = new CellRangeAddress(1, 1, 0, 4);
        sheet.addMergedRegion(cellAddresses2);

        HSSFCellStyle titleStyle1 = ExportHSSFCellStyle.createTitleStyle(Workbook);
        // 在sheet里面创建行
        int row = 0;  // 默认从零开始
        HSSFRow row1 = sheet.createRow(row);
        // 在这一行当中创建一个单元格
        HSSFCell row1Cell = row1.createCell(0);
        // 向单元格当中添加数据
        row1Cell.setCellValue("用户数据");
        row1Cell.setCellStyle(titleStyle1);


        HSSFCellStyle subTitleStyle = ExportHSSFCellStyle.createSubTitleStyle(Workbook);
        // 第二行
        row++;
        HSSFRow row2 = sheet.createRow(row);
        HSSFCell row2Cell = row2.createCell(0);
        row2Cell.setCellValue("总条数:" + userVos.size() + " 导出当前时间" + new Date().toLocaleString());
        row2Cell.setCellStyle(subTitleStyle);

        // 标题
        final HSSFCellStyle titleStyle = ExportHSSFCellStyle.createTableTitleStyle(Workbook);

        // 第三行
        String[] titles = {"用户ID", "用户名称", "用户地址", "用户性别", "入职时间"};
        row++;
        HSSFRow row3 = sheet.createRow(row);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = row3.createCell(i);
            // 添加数据
            cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
        }

        // 设置居中
        HSSFCellStyle baseStyle = ExportHSSFCellStyle.createBaseStyle(Workbook);
        // 第四行到最后
        for (int i = 0; i < userVos.size(); i++) {
            UserVo userVo = userVos.get(i);
            row++;
            // 创建一行
            HSSFRow rowx = sheet.createRow(row);
            // 添加单元格数据
            HSSFCell cell1 = rowx.createCell(0);
            cell1.setCellValue(userVo.getUserid());
            cell1.setCellStyle(baseStyle);

            HSSFCell cell2 = rowx.createCell(1);
            cell2.setCellValue(userVo.getRealname());
            cell2.setCellStyle(baseStyle);

            HSSFCell cell3 = rowx.createCell(2);
            cell3.setCellValue(userVo.getAddress());
            cell3.setCellStyle(baseStyle);

            HSSFCell cell4 = rowx.createCell(3);
            cell4.setCellValue((userVo.getSex() == 0 ? "女" : "男"));
            cell4.setCellStyle(baseStyle);

            HSSFCell cell5 = rowx.createCell(4);
            cell5.setCellValue(new Date().toLocaleString());
            cell5.setCellStyle(baseStyle);

        }


        // 导出保存到D盘
        File file = new File(path);
        try {
            Workbook.write(file);
            System.out.println("导出成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}