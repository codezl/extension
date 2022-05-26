package com.codezl.ex_one.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@RestController
public class FileController {

    @RequestMapping("/getfiles")
    public String getFiles(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.isDirectory()) {
                System.out.println("是文件");
                System.out.println("path="+file.getPath());
                System.out.println("absolutePath="+file.getAbsolutePath());
                System.out.println("name="+file.getName());
            }else if (file.isDirectory()){
                System.out.println("文件夹");
                String[] fileList = file.list();
                if (fileList==null) return "文件夹为空0";
                for (int i=0;i<fileList.length;i++) {
                    File file_1 = new File(filePath+"\\"+fileList[i]);
                    if (!file_1.isDirectory()) {
                        System.out.println("path_1="+file_1.getPath());
                        System.out.println("absolutePath_1="+file_1.getAbsolutePath());
                        System.out.println("name_1="+file_1.getName());
                        String[] split = file_1.getName().split("\\.");
                        String fileType = split[split.length-1];
                        System.out.println("fileType="+fileType);
                    }else if (file_1.isDirectory()) {
                        getFiles(filePath+"\\"+fileList[i]);
                    }
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @GetMapping("newExcel")
    public String newExcel() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("sheet1");
        Sheet sheet2 = wb.createSheet("sheet2");
        String safeSheetName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]");
        wb.createSheet(safeSheetName);
        File file = new File("D:\\workbook.xls");
        if (file.exists()) return "已存在";
        try {
            OutputStream o = new FileOutputStream("D:\\workbook.xls");
            wb.write(o);
            o.close();
            wb.close();
        }catch (Exception e) {
            e.getStackTrace();
        }
        return "成功";
    }

    //创建单元格
    @GetMapping("createCell")
    public void createCell() {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File("D:\\workbook.xls"));
            Sheet sheet1 = wb.getSheetAt(0);
            Row row = sheet1.createRow(0);
//            Row row1 = sheet1.getRow(0);
            Cell cell = row.createCell(0);
//            Cell cell2 = row1.getCell(0);
            cell.setCellValue("设置");
//            cell2.setCellValue("设置2");

            //样式
            CellStyle  cellStyle = wb.createCellStyle();
            CreationHelper creationHelper =  wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("m/d/yy h:mm"));
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(new Date());
            cell1.setCellStyle(cellStyle);
            FileOutputStream outputStream = new FileOutputStream("D:\\workbook1.xls");
            wb.write(outputStream);
            wb.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取单元格数据
    @GetMapping("cellData")
    public  String  cellDate() {
        try {
            Workbook wb = WorkbookFactory.create(new File("D:\\workbook.xls"));
            Sheet sheet1 = wb.getSheetAt(0);
            Row row1 = sheet1.getRow(0);
            Cell cell = row1.getCell(0);
            String stringCellValue = cell.getStringCellValue();
            wb.close();
            return stringCellValue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "打开文件失败";
    }
}
