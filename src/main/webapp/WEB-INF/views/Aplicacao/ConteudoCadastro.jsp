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

    <title>Conteúdo</title>
   
    <c:import url="Header.jsp"/>
    <link type="text/css" rel="stylesheet" href="/assets/lib/datepicker/css/datepicker.css" />
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
                            Conteúdo <small>Cadastro de conteúdo</small>                       
                        </h1>
                        <div class="col-lg-12">
							<form:form id="formConteudo" action="/conteudo/criar/${ConteudoDTO.idTurma}" method="POST" commandName="ConteudoDTO" modelAttribute="ConteudoDTO">                         		 
		                        <div class="col-lg-8 well bs-component">
									<div class="form-group">
		                                <div class="form-group col-md-6">
		                                	<label>Título</label>
		                                	<form:input path="titulo" class="form-control" required="true"/>
		                                </div>	
		                                <div class="form-group col-md-6">
											<label>Tipo de conteúdo</label>
											<form:select class="form-control" path="idTipoConteudo" required="true">
												<option value=""></option>
												<form:options items="${tipoConteudoList }" />
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<div class="form-group col-md-12">
											<label>Descrição</label>
											<form:textarea path="descricao" class="form-control" rows="3" required="true"/>
										</div>
									</div>
									<div class="form-group">
										<div class="form-group">
											<button type="submit" class="btn btn-primary pull-right">Criar</button>
										</div>
									</div>
		                       </div>
							</form:form>
			            </div>
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
    		$('#formConteudo').validate();
    	});
    </script>
    
</body>
</html>
