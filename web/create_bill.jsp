<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: stas-
  Date: 10/31/2016
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Договор депозита</title>
    <script type='text/javascript'
            src='http://code.jquery.com/jquery-3.1.0.js'></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="container">
    <a class="btn icon-btn btn-info" href="/controller?command=allClients">
        <span class="glyphicon btn-glyphicon glyphicon-arrow-left img-circle text-success"></span>
        Назад
    </a>
    <div class="row">
        <div class="col-md-8 col-md-offset-3">
            <h2 class="text-left">Заключение депозитного договора</h2>

            <form class="form-horizontal" action="/controller" method="post">
                <input type="hidden" name="command" value="createDepositForClient">
                <input type="hidden" name="clientId" value="${client.idClient}">

                <div class="form-group">
                    <label class="control-label col-sm-2" for="firstName">ФИО клиента:</label>
                    <div class="col-sm-6">
                        <input required type="text" maxlength="45" class="form-control"
                               name="firstName"
                               pattern="^[A-Za-zА-Яа-яЁё]+$"
                               id="firstName" disabled
                               value="${client.secName} ${client.name} ${client.surName}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="sel1">Вид депозита:</label>
                    <div class="col-sm-6">

                        <select name="depositId" class="form-control" id="sel1">
                            <c:forEach var="deposit" items="${deposits}">
                                <c:choose>
                                    <c:when test="${deposit.type.name eq 'REVOCABLE'}">
                                        <option value="${deposit.id}">${deposit.currency} | Отзывный
                                            | ${deposit.percent}%
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${deposit.id}">${deposit.currency} | Безотзывный
                                            | ${deposit.percent}%
                                        </option>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="startDate">Дата начала:</label>
                    <div class="col-sm-6">
                        <input type="date" required
                               class="form-control"
                               name="startDate"
                               id="startDate" placeholder="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="endDate">Дата окончания:</label>
                    <div class="col-sm-6">
                        <input type="date" required
                               class="form-control"
                               name="endDate"
                               id="endDate" placeholder="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="sum">Сумма вклада:</label>
                    <div class="col-sm-6">
                        <input type="number" min="0" step="1" required class="form-control"
                               name="sum" id="sum"
                               maxlength="7"
                               placeholder="Введите сумму вклада в соответствии с валютой депозита">
                    </div>
                </div>
                <div class="col-md-8 text-center">
                    <button type="submit" class="btn btn-primary">Подтвердить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
