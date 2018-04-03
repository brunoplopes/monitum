<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Frequência</title>
   
    <c:import url="Header.jsp"/>
    <style type="text/css">
    .error{
    	    color: #e74c3c;
    }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond./assets/js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                            Atividade Notas <small>Lançamento de nota</small>
                        </h1>
                        <form:form action="/atividade/atividadesTurma" method="POST" modelAttribute="AtividadeAlunoDTO" name="AtividadeAlunoDTO" id="formAtividadeAluno">
	                        <div class="col-lg-12">
								<div class="form-group col-md-8">
					                    <div class="panel panel-default">
					                        <div class="panel-heading">
					                            ${AtividadeAlunoDTO.titulo }
					                        </div>
					                        <form:hidden path="idAtividade"/>
					                        <!-- /.panel-heading -->
					                        <div class="panel-body">
					                            <div class="table-responsive">
					                                <table class="table table-hover">
					                                    <thead>
					                                        <tr>
					                                            <th>Nome</th>
					                                            <th>Nota</th>
					                                        </tr>
					                                    </thead>
					                                    <tbody>
					                                    <c:forEach items="${AtividadeAlunoDTO.alunosNota }" var="aluno" varStatus="status">
					                                        <tr>
					                                        	<input type="hidden" value="${aluno.idAluno}" name="alunosNota[${status.index}].idAluno" />
					                                            <td>${aluno.nome }</td>
					                                            <td><input type="number" name="alunosNota[${status.index}].nota" value="${aluno.nota}" required></td>
					                                        </tr>
					                                    </c:forEach>
					                                    </tbody>
					                                </table>
					                            </div>
					                            <!-- /.table-responsive -->
					                        </div>
					                        <!-- /.panel-body -->
					                    </div>
								</div>
								<div class="form-group col-md-8">
									<div class="form-group">
										<button type="submit" id="salvar" class="btn btn-primary pull-right">Salvar</button>
									</div>
								</div>
				            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/plugins/morris/raphael.min.js"></script>
    <script src="/assets/js/plugins/morris/morris.min.js"></script>

    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#formAtividadeAluno').validate();
    	});
    </script>
    
</body>
</html>
