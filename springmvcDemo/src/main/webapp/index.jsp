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

        <%--对象类型数据提交--%>
       <%-- 用户姓名：<input type="text" name="user.username">
        用户年龄：<input type="text" name="user.age">--%>
        <br>
        <%--集合类型数据 提交--%>
        用户姓名1：<input type="text" name="userList[0].username">
        用户年龄1：<input type="text" name="userList[0].age">

        用户姓名2：<input type="text" name="userMap['one'].username">
        用户年龄2：<input type="text" name="userMap['one'].age">
        <br>
        <%--日期类型  默认可以接收：2000/11/11这种字符串，2000-11-11就会报错，所以要加一个日期类型的转换器  --%>
        日期：<input type="text" name="date">

        <input type="submit" value="提交">

    </form>

    <form action="user/fileuploadServlet" method="post" enctype="multipart/form-data">
        servlet传统文件上传：<input type="file" name="upload">
        <input type="submit" value="提交">
    </form>
    <br>
    <form action="user/fileuploadSpringMvc" method="post" enctype="multipart/form-data">
        springmvc文件上传：<input type="file" name="upload">
        <input type="submit" value="提交">
    </form>

</body>
</html>
