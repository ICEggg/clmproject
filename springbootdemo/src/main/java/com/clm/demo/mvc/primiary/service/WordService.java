package com.clm.demo.mvc.primiary.service;

import com.clm.demo.mvc.primiary.dao.WordRepository;
import com.clm.demo.mvc.primiary.entity.wc_table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {
    @Autowired
    private WordRepository wordRepository;

    public List<wc_table> wordList(){
        return wordRepository.findAll();
    }

    public Optional<wc_table> wordById(Integer id){
        return wordRepository.findById(id);
    }

    public wc_table addword(String word,Integer count){
        wc_table wc = new wc_table();
        wc.setWord(word);
        wc.setCount(count);
        return wordRepository.save(wc);
    }

    public wc_table updateword(Integer id,String word,Integer count){
        wc_table wc = new wc_table();
        wc.setId(id);
        wc.setWord(word);
        wc.setCount(count);
        return wordRepository.save(wc);
    }

    public void delWord(Integer id){
        wordRepository.deleteById(id);
    }

    public List<wc_table> findByWord(String word){
        return wordRepository.findByWord(word);
    }

    @Transactional
    public void addTwoWord(){
        wc_table wc1 = new wc_table();
        wc1.setWord("a");
        wc1.setCount(1);

        wc_table wc2 = new wc_table();
        wc2.setWord("b");
        wc2.setCount(2);

        wordRepository.save(wc1);
        System.out.println(5/0);
        wordRepository.save(wc2);
    };

}
