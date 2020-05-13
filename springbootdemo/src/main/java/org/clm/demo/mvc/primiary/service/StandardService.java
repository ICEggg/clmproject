package org.clm.demo.mvc.primiary.service;

import lombok.extern.slf4j.Slf4j;
import org.clm.demo.mvc.primiary.compositekey.StandardPrimiaryKey;
import org.clm.demo.mvc.primiary.dao.StandardRepository;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.serviceinterface.IStandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 注释的都是没测过的
 *
 * @CacheConfig 配置当前类，全局的缓存设置，cacheNames成全局的缓存的名字（就像一个缓存数组，取了个名字）。
 */
@Slf4j
@Service
@CacheConfig(cacheNames="standardServiceCache")
public class StandardService implements IStandService {

    @Autowired
    StandardRepository standardRepository;

    /**
     * @Cacheable 和 @CacheEvict分别在getAllStandard  和addStandard上，意思是：
     * 查找所有std时候从缓存拿，添加一条记录后删除这个缓存。下次查所有std就会查库，并把数据重新放一份到缓存
     * @return
     */
    @Override
    @Cacheable(key="#root.target.getAllStandardCacheName()")
    public List<Standard> getAllStandard() {
        return standardRepository.findAll();
    }

    /**
     * 这个方法，就是为了给getAllStandard的缓存一个key，可以让其他方法拿到这个key
     * @return
     */
    public String getAllStandardCacheName(){
        return "aaa";
    }

    @Override
    @CacheEvict(key="#root.target.getAllStandardCacheName()")
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

    /**
     * @CachePut 先调用目标方法，再将目标方法的结果缓存起来
     * 该方法一定要有结果，要有返回值，不然存到缓存里的就是空的东西了
     *
     * @param standard
     * @return
     */
    @Transactional
    @Override
    @CachePut(key = "#standard.id+'_'+#standard.version")
    public Standard updateStandard(Standard standard) {
        //standardRepository.save(standard);
        standardRepository.updateStandard(standard.getName(), standard.getId());
        return standard;
    }

    @Override
    @Cacheable(key = "#id+'_'+#version")
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

        log.info("【TotalPages】"  + all.get());
        log.info("【totalElements】"  + all.getTotalElements());
        log.info("【Number】"  + all.getNumber());
        log.info("【Size】"  + all.getSize());
        log.info("【NumberOfElements】"  + all.getNumberOfElements());


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
