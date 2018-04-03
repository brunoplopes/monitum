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
                            Atividade <small>Lançar nota</small>
                        </h1>
                        <form action="/atividade/atividadesTurma" method="GET">
	                        <div class="col-lg-12">
	                        		<c:if test="${not empty mensagem }">
						        		<div class="col-lg-8">
							              <div class="alert alert-success">
										  		<strong>${mensagem}</strong>
											</div>
						           		</div>
									</c:if>
									<div class="form-group col-md-8 well bs-component">
		                                <div class="form-group col-md-6">
											<label>Turma</label>
											<select class="form-control" id="turma" required>
												<option value=""></option>
												<c:forEach items="${turmaList }" var="turma">
													<option value="${turma.key }">${turma.value }</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group col-md-6">
											<label>Atividade</label>
											<select class="form-control" id="atividade" name="idAtividade" required>
												<option value=""></option>
												<c:forEach items="${atividadeList }" var="atividade">
													<option value="${atividade.key }">${atividade.value }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group col-md-8">
										<div class="form-group">
											<button type="submit" id="buscar" class="btn btn-primary pull-right">Buscar</button>
										</div>
									</div>
				            </div>
                        </form>
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
    		$('#formAtividade').validate();
    		$('#turma').change(function(){
    			$.ajax({
		    		url: "/atividade/atividadesTurma/" + $(this).val(), 
		    		type: 'GET',
		    		success: function(data){
		    			$("#atividade").empty();
		    			$("#atividade").append(new Option("", ""));
		    			$.each(data, function(index, item) {
		    				$("#atividade").append(new Option(item.value, item.key));
		    			});
		        	},
		        	error: function(xhr, status, err){
						console.log(xhr.responseText);
		        	}
		    	});
    		});
    	});
    </script>
    
</body>
</html>
