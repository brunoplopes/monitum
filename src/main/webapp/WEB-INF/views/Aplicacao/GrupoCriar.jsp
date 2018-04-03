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

<title>Cadastro de Grupos</title>
	<style type="text/css">
	    .error{
	    	    color: #e74c3c;
	    }
	    .fileUpload {
	    position: relative;
	    overflow: hidden;
	    margin: 10px;
		}
		.fileUpload input.upload {
		    position: absolute;
		    top: 0;
		    right: 0;
		    margin: 0;
		    padding: 0;
		    font-size: 20px;
		    cursor: pointer;
		    opacity: 0;
		    filter: alpha(opacity=0);
		}
		.span-or {
		    display: block;
		    position: absolute;
		    left: 50%;
		    top: -2px;
		    margin-left: -25px;
		    background-color: #fff;
		    width: 50px;
		    text-align: center;
		}
		.hr-or {
		    background-color: #cdcdcd;
		    height: 1px;
		    margin-top: 0px !important;
		    margin-bottom: 0px !important;
		}
		.login-or {
		    position: relative;
		    font-size: 18px;
		    color: #aaa;
		    margin-top: 10px;
		    margin-bottom: 10px;
		    padding-top: 10px;
		    padding-bottom: 10px;
		}
    </style>
	<c:import url="Header.jsp"/>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond./assets/js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<div id="wrapper">
		<c:import url="HeaderMenu.jsp" />
		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Grupos</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-8">
					  <div id="alerta" class="alert hide">
			              <strong><label id="mensagem"></label></strong>
			          </div>
						<form:form id="formGrupo" action="/grupo/criar/${GrupoDTO.idAtividade}" method="POST" commandName="GrupoDTO" modelAttribute="GrupoDTO">
							<form:hidden path="idAtividade"/>
							<div class="well bs-component">
								<div class="form-group input_fields" style="margin-bottom: 15px;">
									<fieldset disabled>
										<label>Ex:</label>
										<div class="row grupo" style="margin-bottom: 15px;"> 
										  <div class="form-group col-md-4">
										    <input type="number" class="form-control" placeholder="Número de integrantes" >
										  </div>
										  <div class="form-group col-md-6">
										    <input type="text" class="form-control" placeholder="Nome do grupo" >
										  </div>
										</div>
									</fieldset>
								</div>
							</div>
							  <div class="form-group">
								 <button id="adicionar" type="button" class="btn btn-success">Adicionar</button>
							  </div>
							<button type="submit" class="btn btn-primary pull-right">Cadastrar</button>
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
		$('#formGrupo').validate();
		
		var limite      = 50;
	    var campos         = $(".input_fields");
	    var adicionar      = $("#adicionar");
	    
	    var x = 0;
	    $(adicionar).click(function(e){
	        if(x < limite){
	            $(campos).append('<div class="row grupo"> ' +
	            		'<div class="form-group col-md-4">' +
	            		'<input type="number" min="1" class="form-control" placeholder="Número de integrantes" name="grupos['+ x +'].numeroIntegrantes" required>' +
	            		'</div>' +
	            		'<div class="form-group col-md-6">' +
	            		'<input type="text" class="form-control" placeholder="Nome do grupo" name="grupos['+ x +'].nome" required>' +
	            		'</div>' +
	            		'<div class="form-group col-xs-2">' +
	            		'<button id="remover'+ x +'" type="button" class="btn btn-danger remover">Remover</button>' +
	            		'</div>' +
	            		'</div>');
	            x++;
	        }
	    });
	    $(campos).on("click",".remover", function(e){ 
	        $(this).parents('div.row.grupo').remove();
	        x--;
	    });
	});
	</script>
</body>
</html>