package org.clm.demo;

import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.entity.wc_table;
import org.clm.demo.mvc.primiary.service.StandardService;
import org.clm.demo.mvc.primiary.service.WordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * SpringJUnit4ClassRunner 就是让spring和junit整合
 *
 * @SpringBootTest(classes = SpringbootdemoApplication.class)
 * 加载springboot的启动类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
class SpringbootdemoApplicationTests {

    @Autowired
    private WordService wordService;

    @Autowired
    private StandardService standardService;

    /**
     * 返回值一定要是void才行，其他返回值会报错 no tests were found
     */
    @Test
    void wordList() {
        List<wc_table> wc_tables = wordService.wordList();
        System.out.println(wc_tables.toString());

    }

    @Test
    void getAllStandard(){
        List<Standard> allStandard = standardService.getAllStandard();
        System.out.println(allStandard.toString());
    }

}
