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

    <title>Grupo</title>
   
    <c:import url="Header.jsp"/>
    <style type="text/css">
    .error{
    	    color: #e74c3c;
    }
    </style>
	<link type="text/css" rel="stylesheet" href="/assets/lib/jquery.dataTables/jquery.dataTables.min.css" />
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
                            Grupos
                        </h1>
	                    <div class="col-lg-10">
	                        <form id="formGrupo" action="/grupo/criar/" method="GET">
	                       		<c:if test="${not empty mensagem }">
					        		<div class="col-lg-8">
						              <div class="alert alert-success">
									  		<strong>${mensagem}</strong>
										</div>
					           		</div>
								</c:if>
								<div class="form-group col-md-8 well bs-component">
									<div class="form-group col-md-12">
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
										<button type="submit" id="buscar" class="btn btn-success pull-right">Criar</button>
									</div>
								</div>
	                        </form>
	                        
	                        <div class="col-lg-8">
			                    <div class="panel panel-default">
			                        <div class="panel-heading">
			                            Atividades/Grupos
			                        </div>
			                        <!-- /.panel-heading -->
			                        <div class="panel-body">
			                            <div class="table-responsive table-bordered">
			                                <table class="table display" id="table">
			                                    <thead>
			                                        <tr>
			                                            <th>Atividade</th>
			                                            <th>Grupos</th>
			                                            <th width="20%"></th>
			                                        </tr>
			                                    </thead>
			                                    <tbody>
		                                        	<c:forEach items="${GrupoDTO}" var="grupo">
				                                        <tr>
				                                            <td>${grupo.atividadeTitulo }</td>
				                                            <td>
					                                            <c:forEach items="${grupo.nomegrupos }" var="nome">
					                                            	${nome }
					                                            	<br>
					                                            </c:forEach>
				                                            </td>
				                                            <td><button id="editarGrupo" type="button" data-titulo="${grupo.atividadeTitulo }" data-url="/grupo/grupos/${grupo.idAtividade}" class="btn btn-warning editarGrupo">Editar</button></td>
				                                        </tr>
		                                        	</c:forEach>
			                                    </tbody>
			                                </table>
			                            </div>
			                        </div>
			                    </div>
		                 </div>
				       </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   	<!-- Modal -->
	<div class="modal fade" id="modalEditarGrupo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Editar grupo</h4>
	      </div>
	      <div class="modal-body">
		      <div id="alerta" class="alert hide">
	              <strong><label id="mensagem"></label></strong>
	          </div>
	          <div>
		      	<div class="form-group">
					<div class="well bs-component">
						<fieldset>
                  			<legend id="titulo"></legend>
								<div class="form-group col-md-12">
									<label>Grupos</label>
									<select class="form-control" id="grupo" name="idGrupo" required>
										<option value=""></option>
										<c:forEach items="${gruposList }" var="grupo">
											<option value="${grupo.key }">${grupo.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group col-md-12">
									<div id="alunos">
									</div>
								</div>
						</fieldset>
					</div>
				</div>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="enviar">Continuar</button>
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
	<script src="/assets/lib/jquery.dataTables/jquery.dataTables.min.js"></script> 

    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#table').DataTable();
    		$('#formGrupo').validate();
    		$('.editarGrupo').click(function(){
    			$.ajax({
		    		url: $(this).data('url'), 
		    		type: 'GET',
		    		success: function(data){
		    			$("#grupo").empty();
		    			$("#grupo").append(new Option("", ""));
		    			$.each(data, function(index, item) {
		    				$("#grupo").append(new Option(item.value, item.key));
		    			});
		        	},
		        	error: function(xhr, status, err){
						console.log(xhr.responseText);
		        	}
		    	});
    			modal($(this).data('titulo'));
    		});
    		$('#grupo').change(function(){
    			$.ajax({
		    		url: '/grupo/integrantes/' + $(this).val(), 
		    		type: 'GET',
		    		success: function(data){
		    			$(".aluno").remove();
		    			$.each(data, function(index, item) {
		    				$("#alunos").append(
		    						'<div class="aluno">' +
		    						'<div class="form-group col-md-8">' +
		    						'<h4>' + item.value +'</h4>' +
		    						'</div>' +
		    						'<div class="form-group col-md-4">' +
		    						'<button type="button" data-grupo = ' + item.key +' class="btn btn-danger remover">Remover</button>' +
		    						'</div>' +
		    						'</div>'
		    				);
		    			});
		        	},
		        	error: function(xhr, status, err){
		        		console.log(xhr.responseText);
		        	}
		    	});
    		});
    		$("#alunos").on("click",".remover", function(){ 
    			var $remover = $(this);
    			console.log($remover);
    			$.ajax({
		    		url: '/grupo/remover/' + $remover.data('grupo'), 
		    		type: 'GET',
		    		success: function(data){
		    			$remover.parents('.aluno').remove();
		        	},
		        	error: function(xhr, status, err){
		        		console.log(xhr.responseText);
		        	}
		    	});
    			
    	    });
    		var modal = function(titulo){
    			$('#titulo').text(titulo);
    			modalPropriedades();
        	};
        	modalPropriedades = function(){
        		$("#modalEditarGrupo").on("show", function() {
        	        $("#modalEditarGrupo a.btn").on("click", function(e) {
        	            $("#modalEditarGrupo").modal('hide');
        	        });
        	    });
        		$("#modalEditarGrupo").on("hide", function() {
        	        $("#modalEditarGrupo a.btn").off("click");
        	    });
        	    
        	    $("#modalEditarGrupo").on("hidden", function() {
        	    	$("#modalEditarGrupo").remove();
        	    });
        	    $(".custom-close").on('click', function() {
        	        $("#modalFoto").modal('hide');
        	    });
        	    $("#modalEditarGrupo").modal({                   
        	      "backdrop"  : "static",
        	      "keyboard"  : true,
        	      "show"      : true  
        	    });
        	};
    	});
    </script>
    
</body>
</html>
