package org.clm.demo.mvc.primiary.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.clm.demo.entity.BaseResponse;
import org.clm.demo.entity.ResultCode;
import org.clm.demo.mvc.primiary.entity.Standard;
import org.clm.demo.mvc.primiary.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个类 为了测试springboot复合主键
 *
 *
 * 使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/standard")
@Api(value = "StandardController操作api")
public class StandardController {

    @Autowired
    StandardService standardService;

    @GetMapping(value = "/getAllStandard")
    public BaseResponse<List<Standard>> getAllStandard(){
        BaseResponse<List<Standard>> response = new BaseResponse<>();
        List<Standard> allStandard = standardService.getAllStandard();
        response.setData(allStandard);
        return response;
    }


    /**
     * 新增
     * @param standard
     */
    @PostMapping(value = "/addStandard")
    public void addStandard(@RequestBody Standard standard){
        standardService.addStandard(standard);
    }

    /**
     * 删除
     * @param id
     * @param version
     */
    @DeleteMapping(value = "/delStandard/{id}/{version}")
    public void delStandard(@PathVariable String id,@PathVariable String version){
        standardService.delStandard(id,version);
    }

    /**
     * 更新
     * @param standard
     */
    @PutMapping(value = "/updateStandard")
    public BaseResponse<Standard> updateStandard(@RequestBody Standard standard){
        BaseResponse<Standard> response = new BaseResponse<>();
        Standard resultStandard = standardService.updateStandard(standard);
        response.setData(resultStandard);
        response.setResultCode(ResultCode.RESULT_SUCCESS.getCode());
        return response;
    }

    /**
     * 根据两个主键查询
     * @param id
     * @param version
     */
    @GetMapping(value = "/findStandardByIdVersion/{id}/{version}")
    public BaseResponse<Standard> findStandardByIdVersion(@PathVariable String id,@PathVariable String version){
        BaseResponse<Standard> response = new BaseResponse<>();
        Standard standard = standardService.findStandardByIdVersion(id, version);
        response.setData(standard);
        response.setResultCode(ResultCode.RESULT_SUCCESS.getCode());
        log.info(standard.toString());
        return response;
    }

    /**
     * url后参数和json数据请求
     * @param id
     * @param obj
     */
    @GetMapping(value = "/findStandardByIdName/{id}")
    public void findStandardByIdName(@PathVariable String id,@RequestBody JSONObject obj){
        List<Standard> standardList = standardService.findStandardByIdName(id, obj.getString("name"));
        log.info(standardList.toString());
    }

    /**
     * url后参数和表单参数
     * @param id
     * @param name
     */
    @GetMapping(value = "/findStandardByIdNameForm/{id}")
    public void findStandardByIdNameForm(@PathVariable String id,@RequestParam String name){
        List<Standard> standardList = standardService.findStandardByIdName(id, name);
        log.info(standardList.toString());
    }

    /**
     * jpa分页查询
     * @return
     */
    @GetMapping(value = "/findAllStandardByPage")
    public BaseResponse<List<Standard>> findAllStandardByPage(){
        BaseResponse<List<Standard>> response = new BaseResponse<>();
        List<Standard> standardList = standardService.findAllStandardByPage();
        response.setData(standardList);
        log.info(standardList.toString());
        return response;
    }


    /**
     * 文档上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]"+ file.getContentType());
        log.info("[文件名称] - [{}]"+ file.getOriginalFilename());
        log.info("[文件大小] - [{}]"+ file.getSize());
        //保存上传来的文件 到指定目录
        file.transferTo(new File("D:\\project\\aaa.docx"));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }

    /**
     * 文件下载
     *
     * 封装ResponseEntity，将文件流写入body中。这里注意一点，就是文件的格式需要根据具体文件的类型来设置，
     * 一般默认为application/octet-stream。文件头中设置缓存，以及文件的名字。文件的名字写入了，
     * 都可以避免出现文件随机产生名字，而不能识别的问题。
     *
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {
        String filePath = "D:\\" + "standard_file_example.docx";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * 文件下载   原生的方式
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/download2")
    public String downloadFile2( HttpServletResponse response) throws IOException {
        // 获取指定目录下的文件
        String fileName = "D:\\" + "standard_file_example.docx";
        File file = new File(fileName);
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            } catch (Exception e) {
                System.out.println("Download the song failed!");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }



    @GetMapping(value = "/aaa")
    public BaseResponse<String> aaa(@RequestParam String name,@RequestBody JSONObject object){
        BaseResponse<String> response = new BaseResponse<>();
        response.setData(name);

        response.setOtherData(object);
        return response;
    }


}
