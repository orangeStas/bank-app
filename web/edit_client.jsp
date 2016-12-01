<%@ page import="by.bsuir.bankapp.bean.Client" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: stas-
  Date: 9/9/2016
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Edit client</title>
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
    <h2 class="text-center">Редактирование данных о клиенте</h2>
    <div class="panel-group">
        <div class="panel panel-primary">
            <div class="panel-heading">${client.secName} ${client.name} ${client.surName}</div>
            <div class="panel-body">
                <c:if test="${not empty param['message']}">
                    <c:choose>
                        <c:when test="${param['message'] == 'fullNameMatch'}">
                            <p class="text-danger">Пользвователь с таким именем уже существует</p>
                        </c:when>
                        <c:when test="${param['message'] == 'passportNoMatch'}">
                            <p class="text-danger">Пользователь с таким номером паспорта уже существует</p>
                        </c:when>
                        <c:when test="${param['message'] == 'passpordIdMatch'}">
                            <p class="text-danger">Пользователь с таким идентификационным номером паспорта уже существует</p>
                        </c:when>
                        <c:when test="${param['message'] == 'invalidData'}">
                            <p class="text-danger">Введены некорректные данные</p>
                        </c:when>
                    </c:choose>

                </c:if>

                <small>Поля, отмеченные *, обязательны к заполнению</small>

                <form class="form-horizontal" action="/controller" method="post">

                    <input type="hidden" name="command" value="updateClient">
                    <input type="hidden" name="clientId" value="${client.idClient}">

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="firstName">* Фамилия:</label>
                        <div class="col-sm-10">
                            <input required type="text" maxlength="45" class="form-control" name="firstName"
                                   pattern="^[A-Za-zА-Яа-яЁё]+$"
                                   id="firstName" placeholder="Введите фамилию"
                                   value="${client.secName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="name">* Имя:</label>
                        <div class="col-sm-10">
                            <input type="text" required maxlength="45"
                                   pattern="^[A-Za-zА-Яа-яЁё]+$"
                                   class="form-control" name="name" id="name"
                                   value="${client.name}"
                                   placeholder="Введите имя">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="surname">* Отчество:</label>
                        <div class="col-sm-10">
                            <input type="text" required
                                   pattern="^[A-Za-zА-Яа-яЁё]+$"
                                   maxlength="45" class="form-control" name="surname" id="surname"
                                   value="${client.surName}"
                                   placeholder="Введите отчество">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="birthDay">* Дата рождения:</label>
                        <div class="col-sm-10">
                            <input type="date" required class="form-control" name="birthDay"
                                   value="${client.birthday}"
                                   id="birthDay" placeholder="Выберите дату рождения">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="sex">* Пол:</label>
                        <div class="col-sm-10" id="sex">
                            <c:if test="${empty client}">
                                <label class="radio-inline"><input type="radio" name="sexRadio"
                                                                   value="m">Мужской</label>
                                <label class="radio-inline"><input type="radio" name="sexRadio"
                                                                   value="w">Женский</label>
                            </c:if>

                            <c:if test="${not empty client}">
                                <c:if test="${client.sex}">
                                    <label class="radio-inline"><input type="radio" checked
                                                                       name="sexRadio"
                                                                       value="m">Мужской</label>
                                    <label class="radio-inline"><input type="radio" name="sexRadio"
                                                                       value="w">Женский</label>
                                </c:if>
                                <c:if test="${not client.sex}">
                                    <label class="radio-inline"><input type="radio" name="sexRadio"
                                                                       value="m">Мужской</label>
                                    <label class="radio-inline"><input type="radio" checked
                                                                       name="sexRadio"
                                                                       value="w">Женский</label>
                                </c:if>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="passportSeries">* Серия
                            паспорта:</label>
                        <div class="col-sm-10">
                            <input type="text" required maxlength="2" class="form-control" name="passportSeries"
                                   id="passportSeries" value="${client.passportSeries}"
                                   placeholder="Введите серию паспорта (2 буквы)">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="passportNo">* Номер паспорта:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="passportNo"
                                   id="passportNo" pattern="[0-9]{8}"
                                   value="${client.passportNumber}"
                                   title="неверный формат номера паспорта"
                                   placeholder="Введите номер паспорта">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="passportPlace">* Кем выдан:</label>
                        <div class="col-sm-10">
                            <input type="text" required maxlength="150" class="form-control" name="passportPlace"
                                   id="passportPlace"
                                   value="${client.passportPlace}"
                                   placeholder="Укажите, каким учреждением был выдан паспорт">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="passportDate">* Дата выдачи:</label>
                        <div class="col-sm-10">
                            <input type="date" required
                                   value="${client.passportDate}" class="form-control"
                                   name="passportDate"
                                   id="passportDate" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="passportIdNo">* Идентификационный
                            номер:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="passportIdNo"
                                   id="passportIdNo"
                                   pattern="[0-9]{7}[A-Z][0-9]{3}[A-Z]{2}[0-9]"
                                   value="${client.passportId}"
                                   title="неверный формат идентификационного номера"
                                   placeholder="Введите идентификационный номер паспорта">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2"  for="birthPlace">* Место рождения:</label>
                        <div class="col-sm-10">
                            <input type="text" required maxlength="150" class="form-control"
                                   value="${client.birthPlace}"
                                   name="birthPlace"
                                   id="birthPlace" placeholder="Введите место рождения">
                        </div>
                    </div>

                    <%
                        if (session.getAttribute("client") != null) {
                            List<String> list = ((Client) session.getAttribute("client")).getLivingCities();
                            String[] strings = list.toArray(new String[list.size()]);
                            request.setAttribute("cities", strings);
                        }
                        else if (request.getAttribute("client") != null) {
                            List<String> list = ((Client) request.getAttribute("client")).getLivingCities();
                            String[] strings = list.toArray(new String[list.size()]);
                            request.setAttribute("cities", strings);
                        }
                    %>

                    <c:if test="${not empty client}">
                        <c:set var="cities" value="${fn:join(cities, ' ,')}"/>
                    </c:if>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="livingCity">* Город фактического
                            проживания:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="livingCity"
                                   id="livingCity"
                                   maxlength="1000"
                                   value="${cities}"
                                   pattern="^[-\w\s\u0400-\u04ff]+(?:, [-\w\s\u0400-\u04ff]*)*$"
                                   placeholder="Введите города в следующем формате: Город, Город,..">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="livingPlace">* Адрес фактического
                            проживания:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="address"
                                   maxlength="150"
                                   id="livingPlace"
                                   value="${client.address}"
                                   placeholder="Введите адрес фактического проживания">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="homePhone">Номер домашнего
                            телефона:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="homePhone" id="homePhone"
                                   pattern="80172[0-9]{6}"
                                   maxlength="45"
                                   value="${client.homePhone}"
                                   placeholder="Введите номер домашнего телефона">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="phone">Номер мобильного
                            телефона:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="phone" id="phone"
                                   pattern="+375[0-9]{9}"
                                   maxlength="45"
                                   value="${client.phone}"
                                   placeholder="Введите номер мобильного телефона">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="email">E-mail:</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" name="email" id="email"
                                   value="${client.email}"
                                   maxlength="45"
                                   placeholder="Введите ваш e-mail">
                        </div>
                    </div>

                    <%
                        if (session.getAttribute("client") != null) {
                            List<String> list2 = ((Client) session.getAttribute("client")).getFamilyMembers();
                            String[] strings2 = list2.toArray(new String[list2.size()]);
                            request.setAttribute("familyMembers", strings2);
                        } else if (request.getAttribute("client") != null) {
                            List<String> list2 = ((Client) request.getAttribute("client")).getFamilyMembers();
                            String[] strings2 = list2.toArray(new String[list2.size()]);
                            request.setAttribute("familyMembers", strings2);
                        }
                    %>

                    <c:if test="${not empty client}">
                        <c:set var="family" value="${fn:join(familyMembers, ' ,')}"/>
                    </c:if>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="sp">* Семейное положение:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control"
                                   name="sp" id="sp"
                                   pattern="^[-\w\s\u0400-\u04ff]+(?:, [-\w\s\u0400-\u04ff]*)*$"
                                   value="${family}"
                                   maxlength="1000"
                                   placeholder="Заполните поле в следующем формате: женат, дочь, сын..">
                        </div>
                    </div>

                    <%
                        if (session.getAttribute("client") != null) {
                            List<String> list3 = ((Client) session.getAttribute("client")).getNationalities();
                            String[] strings3 = list3.toArray(new String[list3.size()]);
                            request.setAttribute("nationalitiesCl", strings3);
                        } else if (request.getAttribute("client") != null) {
                            List<String> list3 = ((Client) request.getAttribute("client")).getNationalities();
                            String[] strings3 = list3.toArray(new String[list3.size()]);
                            request.setAttribute("nationalitiesCl", strings3);
                        }
                    %>
                    <c:if test="${not empty client}">
                        <c:set var="nationalities" value="${fn:join(nationalitiesCl, ' ,')}"/>
                    </c:if>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="nationality">* Гражданство:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="nationality"
                                   id="nationality"
                                   pattern="^[-\w\s\u0400-\u04ff]+(?:, [-\w\s\u0400-\u04ff]*)*$"
                                   maxlength="1000"
                                   value="${nationalities}"
                                   placeholder="Заполните поле в следующем формате: РБ, РФ,..">
                        </div>
                    </div>

                    <%
                        if (session.getAttribute("client") != null) {
                            List<String> list4 = ((Client) session.getAttribute("client")).getIlls();
                            String[] strings4 = list4.toArray(new String[list4.size()]);
                            request.setAttribute("illsCl", strings4);
                        } else if (request.getAttribute("client") != null) {
                            List<String> list4 = ((Client) request.getAttribute("client")).getIlls();
                            String[] strings4 = list4.toArray(new String[list4.size()]);
                            request.setAttribute("illsCl", strings4);
                        }
                    %>
                    <c:if test="${not empty client}">
                        <c:set var="ills" value="${fn:join(illsCl, ' ,')}"/>
                    </c:if>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="ills">* Инвалидность:</label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" name="ills" id="ills"
                                   value="${ills}"
                                   pattern="^[-\w\s\u0400-\u04ff]+(?:, [-\w\s\u0400-\u04ff]*)*$"
                                   maxlength="1000"
                                   placeholder="Введите болезни через запятую">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="pensioner">* Пенсионер:</label>
                        <div class="col-sm-10 checkbox">
                            <c:if test="${client.pensioner}">
                                <label><input type="checkbox" checked name="pensioner" id="pensioner"
                                              value="yes">Да</label>
                            </c:if>
                            <c:if test="${not client.pensioner}">
                                <label><input type="checkbox" name="pensioner" id="pensioner"
                                              value="yes">Да</label>
                            </c:if>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="military">* Военнообязанный:</label>
                        <div class="col-sm-10 checkbox">
                            <c:if test="${client.military}">
                                <label><input type="checkbox" checked name="military" id="military"
                                              value="yes">Да</label>
                            </c:if>
                            <c:if test="${not client.military}">
                                <label><input type="checkbox" name="military" id="military"
                                              value="yes">Да</label>
                            </c:if>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="income">Ежемесячный доход в
                            у.е.:</label>
                        <div class="col-sm-10">
                            <input type="number" min="0" step="1" required class="form-control"
                                   name="income" id="income"
                                   maxlength="7"
                                   value="${client.income}"
                                   placeholder="Ваш ежемесячный доход">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">Сохранить изменения</button>
                        </div>
                    </div>
                </form>
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
