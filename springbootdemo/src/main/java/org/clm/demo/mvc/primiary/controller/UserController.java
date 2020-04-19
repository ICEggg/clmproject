package org.clm.demo.mvc.primiary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.clm.demo.entity.BaseResponse;
import org.clm.demo.entity.ResultCode;
import org.clm.demo.exception.CommonException;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.mvc.primiary.service.UserService;
import org.clm.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *  Cookie cookie = new Cookie("token",token);
 *  cookie.setMaxAge(60*60*60);
 *  cookie.setPath("/");
 *  httpServletResponse.addCookie(cookie);
 *
 */

@CrossOrigin
@Controller
@RequestMapping(value = "/userCon")
@Api(value = "UserController操作api")
public class UserController{
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${saveTokenLocation}")
    private String saveTokenLocation;

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
    public BaseResponse<String> login(@RequestParam String username, @RequestParam String password,
                                      HttpSession session) throws CommonException {
        BaseResponse<String> response = new BaseResponse<String>();

        //从数据库查是否有该用户
        User user = userService.getUserByNameAndPwd(username, password);
        if(user != null){
            Map<String,Object> map =new HashMap<>();
            map.put("creater","user");
            String token = jwtUtil.createJwt(password, username, map);

            session.setAttribute("user",user);
            //session.setAttribute("token",token);
            session.setMaxInactiveInterval(-1);//设置单位为秒，设置为-1永不过期

            response.setMessage(token);
            response.setResultCode(ResultCode.RESULT_SUCCESS);
        }else{
            throw new CommonException("请输入正确的用户名密码");
        }

        return response;
    }

}
