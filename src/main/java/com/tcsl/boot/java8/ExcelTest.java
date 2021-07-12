package com.tcsl.boot.java8;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    public static void main(String[] args) {

        long begin = System.currentTimeMillis();
        File file1 = new File("C:\\Users\\qinhan\\Desktop\\a.xlsx");
        File file2 = new File("C:\\Users\\qinhan\\Desktop\\b.xlsx");
        File file3 = new File("C:\\Users\\qinhan\\Desktop\\d.xlsx");
        ExcelReader reader1 = ExcelUtil.getReader(file1);
        ExcelReader reader2 = ExcelUtil.getReader(file2);
        ExcelReader reader3 = ExcelUtil.getReader(file3);
        // 店铺数据
        List<ExcelObj> aList = reader1.readAll(ExcelObj.class);
        // 库存数据
        List<ExcelObj> bLis = reader2.readAll(ExcelObj.class);
        // 档案数据
        List<ExcelObj> dLis = reader3.readAll(ExcelObj.class);


        List<ExcelObj> excelObjs = new ArrayList<>();
        for (ExcelObj a : aList) {
            for (ExcelObj b : bLis) {
                if (a.getShopId().equals(b.getShopId())) {
                    for (ExcelObj dLi : dLis) {
                        if (b.getItemId().equals(dLi.getItemId())) {
                            ExcelObj excelObj = new ExcelObj();
                            excelObj.setItemId(b.getItemId());
                            excelObj.setQty(b.getQty());
                            excelObj.setShopName(a.getShopName());
                            excelObj.setShopId(a.getShopId());
                            excelObj.setItemName(dLi.getItemName());
                            excelObjs.add(excelObj);
                        }
                    }

                }
            }
        }

//        System.out.println(excelObjs);

        ExcelWriter bigWriter = ExcelUtil.getBigWriter();
        bigWriter.write(excelObjs);
        bigWriter.flush(new File("C:\\Users\\qinhan\\Desktop\\江西.xlsx"));

        long end = System.currentTimeMillis();

        System.out.println(String.format("操作耗时为:%s 毫秒,", (end - begin)));

    }


}
