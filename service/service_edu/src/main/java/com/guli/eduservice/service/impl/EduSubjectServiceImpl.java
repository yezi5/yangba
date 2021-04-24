package com.guli.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.eduservice.pojo.subject.EduSubject;
import com.guli.eduservice.mapper.EduSubjectMapper;
import com.guli.eduservice.pojo.subject.OneSubject;
import com.guli.eduservice.pojo.subject.TwoSubject;
import com.guli.eduservice.pojo.excel.SubjectData;
import com.guli.eduservice.service.EduSubjectService;
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
 * @since 2020-10-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {




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
