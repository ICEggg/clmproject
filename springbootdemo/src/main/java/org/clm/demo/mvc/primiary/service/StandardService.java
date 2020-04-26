package org.clm.demo.mvc.primiary.service;

import lombok.extern.slf4j.Slf4j;
import org.clm.demo.mvc.primiary.compositekey.StandardPrimiaryKey;
import org.clm.demo.mvc.primiary.dao.StandardRepository;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.serviceinterface.IStandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 注释的都是没测过的
 */
@Slf4j
@Service
public class StandardService implements IStandService {

    @Autowired
    StandardRepository standardRepository;

    @Override
    public void addStandard(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public void delStandard(String id, String version) {
        StandardPrimiaryKey key = new StandardPrimiaryKey();
        key.setId(id);
        key.setVersion(version);
        standardRepository.deleteById(key);
    }

    @Transactional
    @Override
    public void updateStandard(Standard standard) {
        //standardRepository.save(standard);
        standardRepository.updateStandard(standard.getName(),standard.getId());
    }

    @Override
    public Standard findStandardByIdVersion(String id, String version) {
        StandardPrimiaryKey key = new StandardPrimiaryKey();
        key.setId(id);
        key.setVersion(version);
        Optional<Standard> optStandard = standardRepository.findById(key);
        return optStandard.get();

    }

    @Override
    public List<Standard> findStandardByIdName(String id, String name) {
        return standardRepository.findStandardByIdName(id,name);
    }

    /**
     * jpa 分页查询
     */
    public List<Standard> findAllStandardByPage(){
        //分页查询+排序（要么升序，要么降序）
        //Page<Student> studentPage = studentRepository.findAll(PageRequest.of(0,2,Sort.Direction.ASC,"age"));

        PageRequest page = PageRequest.of(0, 2);
        Page<Standard> all = standardRepository.findAll(page);
        List<Standard> standardList = all.getContent();

        all.stream().forEach(s -> System.out.println(s.toString()));

        System.out.println("【TotalPages】"  + all.get());
        System.out.println("【totalElements】"  + all.getTotalElements());
        System.out.println("【Number】"  + all.getNumber());
        System.out.println("【Size】"  + all.getSize());
        System.out.println("【NumberOfElements】"  + all.getNumberOfElements());


        return standardList;
    }




    // JPA动态查询
    /*public void test1() {
        Student student = new Student();
        student.setCityName("南宁");
        student.setClassName("计科152");
        Example<Student> example = Example.of(student);
        // SQL语句 select * from student where city_name = '南宁' and class_name = '计科152'
        List<Student> studentList = studentRepository.findAll(example);
        studentList.forEach(s -> System.out.println(s.toString()));
    }
    @Test
    public void test2() {
        Student student = new Student();
        student.setCityName("南宁");
        student.setClassName("计科152");

        Example<Student> example = Example.of(student);
        // SQL语句 select * from student where city_name = '南宁' and class_name = '计科152' order by age desc limit 0,2
        Page<Student> studentPage = studentRepository.findAll(example, PageRequest.of(0,2, Sort.Direction.DESC,"age"));

        List<Student> studentList = studentPage.getContent();
        studentList.stream().forEach(s -> System.out.println(s.toString()));

        System.out.println("【TotalPages】"  + studentPage.getTotalPages());
        System.out.println("【totalElements】"  + studentPage.getTotalElements());
        System.out.println("【Number】"  + studentPage.getNumber());
        System.out.println("【Size】"  + studentPage.getSize());
        System.out.println("【NumberOfElements】"  + studentPage.getNumberOfElements());
    }
    @Test
    public void test3() {
        Student student = new Student();
        student.setCityName("南宁");
        student.setClassName("计科");

        // 设置属性的查询规则，
        // 有ignoreCase()，caseSensitive()，contains()，endsWith()，startsWith()，exact()，storeDefaultMatching()，regex()
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("className",startsWith());

        Example<Student> example = Example.of(student, matcher);

        List<Student> studentList = studentRepository.findAll(example);
        studentList.forEach(s -> System.out.println(s.toString()));
    }*/

    /*
    * 创建时间、更新时间自动赋值
        在实体类上添加 @EntityListeners(AuditingEntityListener.class)注解，
        * 在创建时间字段上添加 @CreatedDate注解，在更新时间字段上添加 @LastModifiedDate，
        * 时间类型可以为：DateTime、Date、Long、long、JDK8日期和时间类型。
        * 然后还需要在启动类加上@EnableJpaAuditing注解。

    * */














}
