package com.clm.demo.mvc.primiary.controller;

import com.clm.demo.entity.BaseController;
import com.clm.demo.mvc.primiary.entity.wc_table;
import com.clm.demo.mvc.primiary.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/wordCon")
@Api(value = "WordController操作api")
public class WordController extends BaseController {
    @Autowired
    private WordService wordService;

    @GetMapping(value="/words")
    @ApiOperation(value="获取word列表")
    public List<wc_table> wordList(){
        return wordService.wordList();
    }

    @GetMapping(value="/getwords/{id}")
    @ApiOperation(value="根据id获取word")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "String")
    })
    public Optional<wc_table> wordById(@PathVariable("id") Integer id){
        return wordService.wordById(id);
    }

    @PostMapping(value = "/addword")
    @ApiOperation(value="添加word")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "word", value = "单词", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "count", value = "id", required = true, dataType = "String")
    })
    public wc_table addword(@RequestParam("word") String word,@RequestParam("count") Integer count){
        return wordService.addword(word,count);
    }

    @PutMapping(value = "/updateword/{id}")
    @ApiOperation(value="更新word")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "word", value = "单词", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "count", value = "数量", required = true, dataType = "String")
    })
    public wc_table updateword(@PathVariable("id") Integer id, @RequestParam("word") String word,@RequestParam("count") Integer count){
        return wordService.updateword(id,word,count);
    }

    @DeleteMapping(value = "/delword/{id}")
    @ApiOperation(value="删除word")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = true, dataType = "String"),
    })
    public void delWord(@PathVariable("id") Integer id){
        wordService.delWord(id);
    }

    @PostMapping(value = "/findByWord/{word}")
    @ApiOperation(value="删除word")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "word", value = "单词", required = true, dataType = "String"),
    })
    public List<wc_table> findByWord(@PathVariable("word") String word){
        return wordService.findByWord(word);
    }

    @PostMapping(value = "/addTwoWord")
    @ApiOperation(value="添加word")
    public void addTwoWord(){
        wordService.addTwoWord();
    }

}
