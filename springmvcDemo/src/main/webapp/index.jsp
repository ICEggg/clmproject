<%--
  Created by IntelliJ IDEA.
  User: dev
  Date: 2020/3/24
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
</head>
<body>
    <a href="user/hello">hello</a>
    <br>
    <a href="user/param?username=aaa">get传参数</a>
    <br>
    <form action="user/saveAccount" method="post">
        姓名：<input type="text" name="username">
        密码：<input type="text" name="password">
        金额：<input type="text" name="money">

        用户姓名：<input type="text" name="user.username">
        用户年龄：<input type="text" name="user.age">
        <input type="submit" value="提交">
    </form>
</body>
</html>
