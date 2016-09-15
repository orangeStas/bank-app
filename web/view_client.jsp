<%--
  Created by IntelliJ IDEA.
  User: stas-
  Date: 9/9/2016
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>View client</title>
    <script type='text/javascript'
            src='http://code.jquery.com/jquery-3.1.0.js'></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="styles.css">


    <script type='text/javascript'>//<![CDATA[
    $(window).load(function () {<!--   w  w  w . j  a v a2  s. co m-->
//save the selector so you don't have to do the lookup everytime
        $dropdown = $("#contextMenu");
        $(".actionButton").click(function () {
            //get row ID
            var id = $($(this).closest("tr").children()[5]).html();
            //move dropdown menu
            $(this).after($dropdown);
            //update links
            $dropdown.find(".openLink").attr("href", "/transaction/pay?id=" + id);
            $dropdown.find(".removeLink").attr("href", "/transaction/delete?id=" + id);
            //show dropdown
            $(this).dropdown();
        });
    });//]]>
    </script>
</head>
<body>
<div class="container">
    <h2 class="text-center">Информация о клиенте</h2>
    <div class="panel-group">
        <div class="panel panel-primary">
            <div class="panel-heading">${client.secName} ${client.name} ${client.surName}</div>
            <div class="panel-body">
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-sm-4">
                            <h4>Дата рождения</h4>
                            <h5>${client.birthday}</h5>
                        </div>
                        <div class="col-sm-2">
                            <h4>Пол</h4>
                            <c:if test="${client.sex}">
                                <h5>Мужской</h5>
                            </c:if>
                            <c:if test="${not client.sex}">
                                <h5>Женский</h5>
                            </c:if>
                        </div>
                        <div class="col-sm-4">
                            <div class="dropdown" style="margin-top: 7px">
                                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">Гражданство
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <c:forEach var="nationality" items="${client.nationalities}">
                                        <li><a href="#">${nationality}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2">
                            <h4>Серия паспорта</h4>
                            <h5>${client.passportSeries}</h5>
                        </div>
                        <div class="col-sm-2">
                            <h4>Номер паспорта</h4>
                            <h5>${client.passportNumber}</h5>
                        </div>
                        <div class="col-sm-2">
                            <h4>Кем выдан</h4>
                            <h5>${client.passportPlace}</h5>
                        </div>
                        <div class="col-sm-2">
                            <h4>Дата выдачи</h4>
                            <h5>${client.passportDate}</h5>
                        </div>

                        <div class="col-sm-2">
                            <h4>Идентификационный номер</h4>
                            <h5>${client.passportId}</h5>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-4">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">Семейное положение
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <c:forEach var="member" items="${client.familyMembers}">
                                        <li><a href="#">${member}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">Город прописки
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <c:forEach var="city" items="${client.livingCities}">
                                        <li><a href="#">${city}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="row">

                        <div class="col-sm-4">
                            <h4>Место рождения</h4>
                            <h5>${client.birthPlace}</h5>
                        </div>
                        <div class="col-sm-4">
                            <h4>Адрес</h4>
                            <h5>${client.address}</h5>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-sm-4">
                            <h4>Домашний телефон</h4>
                            <h5>${client.homePhone}</h5>
                        </div>
                        <div class="col-sm-4">
                            <h4>Мобильный телефон</h4>
                            <h5>${client.phone}</h5>
                        </div>
                        <div class="col-sm-4">
                            <h4>E-mail</h4>
                            <h5>${client.email}</h5>
                        </div>
                    </div>

                    <div class="row">

                        <div class="col-sm-4">
                            <div class="dropdown" style="margin-top: 7px">
                                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">Инвалидность
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <c:forEach var="ill" items="${client.ills}">
                                        <li><a href="#">${ill}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <h4>Пенсионер</h4>
                            <c:if test="${client.pensioner}">
                                <h5>Да</h5>
                            </c:if>
                            <c:if test="${not client.pensioner}">
                                <h5>Нет</h5>
                            </c:if>
                        </div>
                        <div class="col-sm-4">
                            <h4>Военнообязанный</h4>
                            <c:if test="${client.military}">
                                <h5>Да</h5>
                            </c:if>
                            <c:if test="${not client.military}">
                                <h5>Нет</h5>
                            </c:if>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-8">
                            <h4>Ежемесячный доход в у.е.</h4>
                            <h5>${client.income}</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a class="btn icon-btn btn-info" href="/controller?command=allClients">
        <span class="glyphicon btn-glyphicon glyphicon-arrow-left img-circle text-success"></span>
        К списку клиентов
    </a>
</div>
</body>
</html>