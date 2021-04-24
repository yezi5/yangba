package com.guli.admin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.admin.listener.ExcelListener;
import com.guli.admin.pojo.EduSubject;
import com.guli.admin.mapper.EduSubjectMapper;
import com.guli.admin.pojo.excel.OneSubject;
import com.guli.admin.pojo.excel.SubjectData;
import com.guli.admin.pojo.excel.TwoSubject;
import com.guli.admin.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-03-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public boolean save(MultipartFile file, EduSubjectService service) {
        boolean rs = true;
        if (file == null){
            return false;
        }

        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new ExcelListener(service)).sheet().doRead();
        } catch (IOException e) {
            rs = false;
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public boolean existOneSubject(String subjectName) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("parent_id","0");
        wrapper.eq("title",subjectName);

        EduSubject subject = this.getOne(wrapper);

        if (subject != null){
            return true;
        }

        return false;
    }

    @Override
    public Boolean existTwoSubject(String subjectName) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.ne("parent_id","0");
        wrapper.eq("title",subjectName);

        EduSubject subject = this.getOne(wrapper);
        if (subject != null){
            return true;
        }

        return false;
    }

    @Override
    public String getSubjectId(String subjectName) {
        String id = null;

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);

        EduSubject subject = this.getOne(wrapper);
        if (subject != null){
            id = subject.getId();
        }
        return id;
    }

    @Override
    public List<OneSubject> getOneAndTwoSubjects() {
        List<OneSubject> list = new ArrayList<>();

        QueryWrapper<EduSubject>  wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);

        for (EduSubject eduSubject : eduSubjects) {
            OneSubject oneSubject = new OneSubject();
            oneSubject.setParent_id("0");
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());

            List<TwoSubject> twoSubjects = getTwoSubjects(eduSubject.getId());
            oneSubject.setChildren(twoSubjects);
            list.add(oneSubject);
        }


        return list;
    }

    @Override
    public List<TwoSubject> getTwoSubjects(String id) {
        List<TwoSubject> twoSubjects = new ArrayList<>();

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);

        for (EduSubject eduSubject : eduSubjects) {
            TwoSubject twoSubject = new TwoSubject();
            twoSubject.setId(eduSubject.getId());
            twoSubject.setParent_id(id);
            twoSubject.setTitle(eduSubject.getTitle());
            twoSubjects.add(twoSubject);
        }

        return twoSubjects;
    }
}
