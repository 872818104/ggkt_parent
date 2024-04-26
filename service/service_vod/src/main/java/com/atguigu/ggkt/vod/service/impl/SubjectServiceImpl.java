package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.listener.SubjectListener;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import com.atguigu.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目 服务实现类
 *
 * @author cc
 * @since 2024-03-27
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    //课程分类列表
    @Override
    public List<Subject> selectSubjectList(Long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            boolean isChild = this.isChildSubject(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }


    private boolean isChildSubject(Long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    //课程分类导出
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            //防止中文乱码
            String fileName = URLEncoder.encode("课程分类", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //查询课程列表所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            // List<Subject> ==> List<SubjectEeVo>
            List<SubjectEeVo> subjectEeVoList = new ArrayList<>();
            for (Subject subject : subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVoList.add(subjectEeVo);
            }

            //EasyExcel写操作
            EasyExcel.write(response.getOutputStream(),
                            SubjectEeVo.class)
                    .sheet()
                    .doWrite(subjectEeVoList);
        } catch (Exception e) {
            throw new GgktException(20001, "导出失败");
        }
    }

    //课程分类导入
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),
                            SubjectEeVo.class,
                            subjectListener)
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new GgktException(20001, "导入失败");
        }
    }
}
