package edu.zsq.demo.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    @Test
    public void writeToExcel() {

//        实现excel的写入操作

//        1 设置写入文件夹地址和excel文件名
        String filename = "D:/write.xlsx";

//        2 调用easyexcel的方法实现写入操作
//        参数1  文件路径名称  参数2 实体类class     sheet方法是指定excel中哪个sheet  操作完成后自动关闭流
        EasyExcel.write(filename, EntityDemo.class).sheet("学生").doWrite(getList());

    }


    @Test
    public void readFromExcel() {

        String filename = "D:/write.xlsx";
        EasyExcel.read(filename,EntityDemo.class,new ExcelListener()).sheet("学生").doRead();

    }

    private static List<EntityDemo> getList() {
        List<EntityDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EntityDemo entityDemo = new EntityDemo();
            entityDemo.setSno(i);
            entityDemo.setSname("张三" + i );
            list.add(entityDemo);
        }

        return list;
    }
}
