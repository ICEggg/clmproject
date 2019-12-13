package com.clm.demo.mvc.secondary.dao;

import com.clm.demo.mvc.secondary.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TeacherDao extends JpaRepository<Teacher,Integer> {

    @Query(value = "select * from teacher",nativeQuery = true)
    List<Teacher> getAllTeacherMess();
}
