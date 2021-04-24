package com.guli.admin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.guli.admin.pojo.EduSubject;
import com.guli.admin.pojo.excel.SubjectData;
import com.guli.admin.service.EduSubjectService;
import com.guli.servicebase.handle.BadRequestException;
import com.mysql.jdbc.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.admin.listener
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/31 星期三 18:38
 */
public class ExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService service;
    private static final int BATCH_COUNT = 5;
    private List<EduSubject> list = new ArrayList<>();

    public ExcelListener() {
    }

    public ExcelListener(EduSubjectService service) {
        this.service = service;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new BadRequestException(20001,"文件数据为空");
        }

        //添加一级分类
        if (!service.existOneSubject(subjectData.getOneSubjectName())){
            EduSubject subject = new EduSubject();
            subject.setParentId("0");
            subject.setTitle(subjectData.getOneSubjectName());
            System.out.println(subject);
            list.add(subject);
        }

        //添加二级分类
        if (!service.existTwoSubject(subjectData.getTwoSubjectName())){
            EduSubject subject = new EduSubject();
            String subjectId = service.getSubjectId(subjectData.getOneSubjectName());
            if (StringUtils.isNullOrEmpty(subjectId)){
                throw new BadRequestException(20001,"一级分类为空");
            }
            subject.setParentId(subjectId);
            subject.setTitle(subjectData.getTwoSubjectName());
            System.out.println(subject);
            list.add(subject);
        }

        if (list.size() > BATCH_COUNT){
            service.saveBatch(list);
        }

    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
