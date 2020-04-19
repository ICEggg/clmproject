package org.clm.demo.mvc.secondary.service;

import org.clm.demo.mvc.secondary.dao.TeacherDao;
import org.clm.demo.mvc.secondary.entity.Teacher;
import org.clm.demo.mvc.secondary.impl.TeacherImpl;
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
