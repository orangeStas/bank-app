<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: stas-
  Date: 12/2/2016
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type='text/javascript'
            src='http://code.jquery.com/jquery-3.1.0.js'></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="styles.css">
</head>
<body class="login">
<div class="container">
    <div class="row">
        <div class="Absolute-Center is-Responsive">
            <div class="col-sm-12 col-md-10 col-md-offset-1">
                <form action="/controller" id="loginForm" method="post">
                    <input type="hidden" name="command" value="login">
                    <div class="form-group input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input class="form-control" type="text" name='number' placeholder="Номер карты"/>
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input class="form-control" type="text" name='pin' placeholder="ПИН"/>
                    </div>
                    <c:if test="${not empty param['message']}">
                        <div>
                            <p class="bg-primary">Неверный PIN или номер карты</p>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <button type="submit" class="btn btn-def btn-block">Войти</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
