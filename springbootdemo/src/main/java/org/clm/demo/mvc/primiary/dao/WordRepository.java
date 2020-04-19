package org.clm.demo.mvc.primiary.dao;

import org.clm.demo.mvc.primiary.entity.wc_table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WordRepository extends JpaRepository<wc_table,Integer> {
    List<wc_table> findByWord(String word);
}
