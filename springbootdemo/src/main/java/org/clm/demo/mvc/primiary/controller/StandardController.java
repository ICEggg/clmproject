package org.clm.demo.mvc.primiary.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.clm.demo.entity.BaseResponse;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 这个类 为了测试复合主键
 */

@Slf4j
@RestController
@RequestMapping(value = "/standard")
@Api(value = "StandardController操作api")
public class StandardController {

    @Autowired
    StandardService standardService;

    @PostMapping(value = "/addStandard")
    public void addStandard(@RequestBody Standard standard){
        standardService.addStandard(standard);
    }

    @DeleteMapping(value = "/delStandard/{id}/{version}")
    public void delStandard(@PathVariable String id,@PathVariable String version){
        standardService.delStandard(id,version);
    }

    @PutMapping(value = "/updateStandard")
    public void updateStandard(@RequestBody Standard standard){
        standardService.updateStandard(standard);
    }

    @GetMapping(value = "/findStandardByIdVersion/{id}/{version}")
    public void findStandardByIdVersion(@PathVariable String id,@PathVariable String version){
        Standard standard = standardService.findStandardByIdVersion(id, version);
        log.info(standard.toString());
    }

    @GetMapping(value = "/findStandardByIdName/{id}")
    public void findStandardByIdName(@PathVariable String id,@RequestBody JSONObject obj){
        List<Standard> standardList = standardService.findStandardByIdName(id, obj.getString("name"));
        log.info(standardList.toString());
    }

    @GetMapping(value = "/findStandardByIdNameForm/{id}")
    public void findStandardByIdNameForm(@PathVariable String id,@RequestParam String name){
        List<Standard> standardList = standardService.findStandardByIdName(id, name);
        log.info(standardList.toString());
    }

    @GetMapping(value = "/findAllStandardByPage")
    public BaseResponse<List<Standard>> findAllStandardByPage(){
        BaseResponse<List<Standard>> response = new BaseResponse<>();
        List<Standard> standardList = standardService.findAllStandardByPage();
        response.setData(standardList);
        log.info(standardList.toString());
        return response;
    }

    @PostMapping(value = "/aaa")
    public void aaa(@RequestBody JSONObject paramObj){
        System.out.println("----------------"+paramObj.getString("name"));
        System.out.println("----------------"+paramObj.getString("id"));
        System.out.println("----------------"+paramObj.getJSONArray("location"));
    }


}
