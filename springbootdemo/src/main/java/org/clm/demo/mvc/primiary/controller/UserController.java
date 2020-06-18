package org.clm.demo.mvc.primiary.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.clm.demo.entity.BaseResponse;
import org.clm.demo.entity.ResultCode;
import org.clm.demo.exception.CommonException;
import org.clm.demo.mvc.primiary.entity.Menu;
import org.clm.demo.mvc.primiary.entity.Role;
import org.clm.demo.mvc.primiary.entity.User;
import org.clm.demo.mvc.primiary.service.MenuService;
import org.clm.demo.mvc.primiary.service.RoleService;
import org.clm.demo.mvc.primiary.service.UserService;
import org.clm.demo.util.JwtUtil;
import org.clm.demo.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 *  Cookie cookie = new Cookie("token",token);
 *  cookie.setMaxAge(60*60*60);
 *  cookie.setPath("/");
 *  httpServletResponse.addCookie(cookie);
 *
 *
 *
 * 不需要设置session，因为有token了
 *   request.setAttribute(token,user);
 *   session.setAttribute(token,user);
 *   session.setAttribute("token",token);
 *   session.setMaxInactiveInterval(-1);//设置单位为秒，设置为-1永不过期
 */


@Controller
@RequestMapping(value = "/userCon")
@Api(value = "UserController操作api")
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${saveTokenLocation}")
    private String saveTokenLocation;

    /**
     * 登录获取token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="根据用户名密码返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String")
    })
    public BaseResponse<JSONObject> login(@RequestBody Map<String,String> paramMap,
                                      HttpSession session, HttpServletRequest request) throws CommonException {
        BaseResponse<JSONObject> response = new BaseResponse<JSONObject>();

        JSONObject resultObj = new JSONObject();
        String username = paramMap.get("username");
        String password = paramMap.get("password");
        //从数据库查是否有该用户
        User user = userService.getUserByNameAndPwd(username, password);
        if(user != null){
            //查看该用户的角色
            String userId = user.getId();
            List<Role> roleList = roleService.getRoleIdByUserId(userId);
            user.setRoleList(roleList);

            //查看角色下对应的菜单
            Set<Menu> menuSet = new HashSet<>();
            for (Role role : roleList) {
                int roleId = role.getId();
                Set<Menu> menuList = menuService.getMenuListByRoleId(roleId);
                menuSet.addAll(menuList);
            }
            user.setMenuList(menuSet);

            //这个map，是创建token的时候，附加的信息
            Map<String,Object> map =new HashMap<>();
            map.put("creater", UserContextUtil.getUser());
            String token = jwtUtil.createJwt(user, map);

            resultObj.put("userMess",user);
            resultObj.put("token",token);

            response.setData(resultObj);
            response.setMessage("登录成功");
            response.setResultCode(ResultCode.RESULT_SUCCESS.getCode());
        }else{
            throw new CommonException("请输入正确的用户名密码");
        }

        return response;
    }

    @ResponseBody
    @GetMapping(value = "/getAllUser")
    public BaseResponse<List<User>> getAllUser(){
        BaseResponse<List<User>> response = new BaseResponse<>();
        List<User> allUser = userService.getAllUser();
        response.setData(allUser);
        return response;
    }

}
