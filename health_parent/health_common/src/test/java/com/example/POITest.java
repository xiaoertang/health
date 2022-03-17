package com.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 唐孝顺
 * @date 2022/3/16 15:03
 */
public class POITest {
    @Test
    public void test1() throws IOException, InvalidFormatException {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook(new File("G:\\test.xlsx"));
        //获取工作表，即可根据工作表的顺序，也可以根据工作名
        XSSFSheet sheet = workbook.getSheetAt(0);
        //遍历工作表
        for (Row row : sheet) {
            //遍历每行中的每个单元格
            for (Cell cell : row) {
                //获取单元格的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }

    @Test
    public void test2() throws IOException, InvalidFormatException {
        //创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook(new File("G:\\test.xlsx"));
        //获取工作表，即可根据工作表的顺序，也可以根据工作名
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取工作表最后一行
        int lastRowNum = sheet.getLastRowNum();
        //遍历工作表
        for (int i = 0; i <= lastRowNum; i++) {
            //根据行获取每行
            XSSFRow row = sheet.getRow(i);
            //根据行获取每行最后一个单元格
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                //获取每个单元格的值
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        workbook.close();
    }

    //向excel写入数据
    @Test
    public void test3() throws IOException {
        //在内存中创建一个Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建Excel工作薄
        XSSFSheet sheet = excel.createSheet("test");
        //创建行
        XSSFRow row1 = sheet.createRow(0);
        //向单元格写入值
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("地址");
        row1.createCell(2).setCellValue("年龄");

        XSSFRow rows = sheet.createRow(1);
        rows.createCell(0).setCellValue("张三");
        rows.createCell(1).setCellValue("北京");
        rows.createCell(2).setCellValue("24");

        //通过输出流将excel对象下载到本地
        FileOutputStream out = new FileOutputStream(new File("G:\\test2.xlsx"));
        excel.write(out);
        out.flush();
        out.close();
        excel.close();


    }
}
