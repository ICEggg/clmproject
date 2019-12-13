package com.clm.demo.mvc.secondary.service;

import com.clm.demo.mvc.secondary.dao.TeacherDao;
import com.clm.demo.mvc.secondary.entity.Teacher;
import com.clm.demo.mvc.secondary.impl.TeacherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements TeacherImpl {

    @Autowired
    TeacherDao teacherDao;

    @Override
    public List<Teacher> getAllTeacherMess() {
        return teacherDao.getAllTeacherMess();
    }
}
