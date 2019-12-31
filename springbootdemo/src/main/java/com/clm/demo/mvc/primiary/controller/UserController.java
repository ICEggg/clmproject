package com.clm.demo.mvc.primiary.controller;

import com.clm.demo.entity.BaseController;
import com.clm.demo.entity.BaseResponse;
import com.clm.demo.entity.ResultCode;
import com.clm.demo.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "/userCon")
@Api(value = "UserController操作api")
public class UserController extends BaseController {
    //@Autowired
   // private UserService service;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录获取token
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="根据用户名密码返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String")
    })
    public BaseResponse<String> login(@RequestParam String username, @RequestParam String password){
        BaseResponse<String> response = new BaseResponse<String>();
        Map<String,Object> map =new HashMap<>();
        map.put("creater","user");
        String token = jwtUtil.createJwt(password, username, map);
        response.setMessage(token);
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        return response;
    }

    /**
     * 登录成功返回用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    @ApiOperation(value="根据token获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "token", value = "token", required = true, dataType = "String"),
    })
    public BaseResponse<String> profile(){
        BaseResponse<String> response = new BaseResponse<String>();
        String token = request.getHeader("claims");
        //String token = request.getHeader("token");

        //Claims claims = jwtUtil.parseJwt(token);
        String id = this.claims.getId();
//        SysUser user = service.findbyid(id);
//        List<SysUser> list = new ArrayList<>();
//        list.add(user);

//        Cookie cookie = new Cookie("token",token);
//        cookie.setMaxAge(60*60*60);
//        cookie.setPath("/");
//        httpServletResponse.addCookie(cookie);

        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage(id);
        return response;
    }


    /**
     * 登录成功返回用户信息
     */
//    @ResponseBody
//    @RequestMapping(value = "/profile",method = RequestMethod.POST)
//    @ApiOperation(value="根据token获取用户信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "token", value = "token", required = true, dataType = "String"),
//    })
//    public BaseResponse<SysUser> profile(@RequestParam String token, HttpServletResponse httpServletResponse){
//        BaseResponse<SysUser> response = new BaseResponse<SysUser>();
//        Claims claims = jwtUtil.parseJwt(token);
//        String id = claims.getId();
//        SysUser user = service.findbyid(id);
//        List<SysUser> list = new ArrayList<>();
//        list.add(user);
//
//        Cookie cookie = service.cookie_token(token);
//        httpServletResponse.addCookie(cookie);
//
//        response.setResultCode(ResultCode.RESULT_SUCCESS);
//        response.setData(list);
//        return response;
//    }
}