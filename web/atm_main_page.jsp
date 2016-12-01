<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: stas-
  Date: 12/2/2016
  Time: 1:02 AM
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
<body>
<div class="jumbotron vertical-center">
    <div class="container">
        <a class="btn icon-btn btn-info" href="/controller?command=allClients">
            <span class="glyphicon btn-glyphicon glyphicon-arrow-left img-circle text-success"></span>
            ВЫХОД
        </a>
        <p></p>
        <div class="panel-group">
            <div class="panel panel-primary">
                <div class="panel-heading">БАНКОМАТ</div>
                <div class="panel-body">
                    <h2 class="text-center">Здравствуйте, ${card.client.name} </h2>
                    <a href="/controller?command=billStatus" class="btn btn-large btn-block btn-info" role="button">Текущее состояние счёта</a>
                    <a href="/controller?command=openWithdrawCash" class="btn btn-large btn-block btn-primary" role="button">Снятие наличных</a>
                    <a href="/controller?command=openMobilePayment" class="btn btn-large btn-block btn-success" role="button">Оплата услуг мобильной связи
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty param['subCommand']}">
            <c:choose>
                <c:when test="${param['subCommand'] == 'billStatus'}">
                    <div class="panel-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Состояние счёта</div>
                            <div class="panel-body">
                                <h2 class="text-center">На счету: ${card.creditBill.moneySum} ${card.creditBill.credit.currency} </h2>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:when test="${param['subCommand'] == 'openWithdrawCash'}">
                    <div class="panel-group">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Снятие наличных</div>
                            <div class="panel-body">
                                <form class="form-horizontal" action="/controller" method="post">
                                    <input type="hidden" name="command" value="withDrawCash">
                                    <div class="form-group">
                                        <label class="control-label col-sm-2" for="cash">Сумма:</label>
                                        <div class="col-sm-4">
                                            <input required type="number" maxlength="5" class="form-control"
                                                   name="sum"
                                                   id="cash" placeholder="Введите сумму">
                                        </div>
                                    </div>
                                    <c:if test="${not empty param['subCommandError']}">
                                        <p class="text-danger col-sm-offset-2 ">Недостаточно средств на счету</p>
                                    </c:if>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default">Подтвердить</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:when test="${param['subCommand'] == 'openMobilePayment'}">
                    <div class="panel-group">
                        <div class="panel panel-success">
                            <div class="panel-heading">Оплата услуг мобильной связи</div>
                            <div class="panel-body">
                                <form class="form-horizontal" action="/controller" method="post">
                                    <input type="hidden" name="command" value="mobilePayment">
                                    <div class="form-group">
                                        <label class="control-label col-sm-2" for="phone">Номер мобильного
                                            телефона:</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" name="phone" id="phone"
                                                   pattern="+375[0-9]{9}"
                                                   maxlength="45"
                                                   placeholder="Введите номер мобильного телефона">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label col-sm-2" for="cash">Сумма:</label>
                                        <div class="col-sm-4">
                                            <input required type="number" maxlength="5" class="form-control"
                                                   name="sum"
                                                   id="cash" placeholder="Введите сумму">
                                        </div>
                                    </div>
                                    <c:if test="${not empty param['subCommandError']}">
                                        <p class="text-danger col-sm-offset-2 ">Недостаточно средств на счету</p>
                                    </c:if>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default">Подтвердить</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:if>
    </div>
</div>
</body>
</html>
