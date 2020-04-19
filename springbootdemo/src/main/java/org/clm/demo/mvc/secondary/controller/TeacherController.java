package org.clm.demo.mvc.secondary.controller;

import org.clm.demo.mvc.secondary.entity.Teacher;
import org.clm.demo.mvc.secondary.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @RequestMapping(value = "/getallteachermess",method = RequestMethod.POST)
    public List<Teacher> getallteachermess(){
        List<Teacher> allTeacherMess = teacherService.getAllTeacherMess();
        return allTeacherMess;
    }
}
