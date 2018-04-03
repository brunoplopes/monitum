<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <style type="text/css">
        #page-wrapper{
            height: 100%;
        }
        .panel-turmas>.panel-heading {
            color: #585252;
            background-color: #F2F2F2;
            border-color: #000000;
        }
    </style>
    <title>Fechar Turma</title>
    <c:import url="Header.jsp"/>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond./assets/js/1.4.2/respond.min.js"></script>
</head>
<body>
<div id="wrapper">
    <c:import url="HeaderMenu.jsp"/>
    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Turma <small></small>
                    </h1>
                    <div class="row">
                        <div class="col-lg-4">
                            <p>Tem certeza de que deseja encerrar a turma?
                            <p>Ela será fechada e não poderá mais ser reativada.</p>
                            <a href="/turma/fechar/${idTurma}" class="btn btn-danger ">Fechar Turma</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/assets/js/jquery.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/plugins/morris/raphael.min.js"></script>
<script src="/assets/js/plugins/morris/morris.min.js"></script>
</body>
</html>
