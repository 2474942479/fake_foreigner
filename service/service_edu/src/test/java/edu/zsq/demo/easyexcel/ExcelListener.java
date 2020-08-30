package edu.zsq.demo.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<EntityDemo>{

//    读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);

    }

    //    一行一行读取excel内容

    @Override
    public void invoke(EntityDemo entityDemo, AnalysisContext analysisContext) {
        System.out.println("内容"+entityDemo);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
