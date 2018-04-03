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

<title>Disciplinas</title>

<c:import url="Header.jsp" />
<link type="text/css" rel="stylesheet"
	href="/assets/lib/datepicker/css/datepicker.css" />
<style type="text/css">
.error {
	color: #e74c3c;
}

.calendario {
	display: table;
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
		<c:import url="HeaderMenu.jsp" />
		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Disciplinas</h1>
						<div class="col-lg-10">
							<div class="col-lg-12">
								<div class="form-group col-md-4">
									<button id="adicionarDisciplina" class="btn btn-primary">Adicionar</button>
								</div>
							</div>

							<div class="table-responsive col-md-12"
								data-example-id="contextual-table">
								<table class="table table-bordered table-hover table-striped display" id="table">
									<thead>
										<tr>
											<th width="10%">Código</th>
											<th width="30%">Disciplina</th>
											<th width="3%">Editar</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${Disciplina}" var="disciplina"
											varStatus="status">
											<tr
												class="<c:if test="${status.index % 2 == 0}">active</c:if>">
												<td id="cod${disciplina.id}">${disciplina.codDisciplina}</td>
												<td id="nome${disciplina.id}">${disciplina.nomeDisciplina}</td>
												<td>
													<ul class="nav nav-pills">
														<li><a href="#" id="editarDisciplina${disciplina.id}" title="Editar"><i  class="fa fa-pencil-square-o" aria-hidden="true"></i></a></li>																												
													</ul>
											    </td>
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
	
    <!-- Modal Criação -->
	<div class="modal fade" id="cadastroDisciplina" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Cadastro de Disciplina</h4>
	      </div>
	     	 <div class="modal-body">
		      <div id="alertaDisciplina" class="alert hide">
	              <strong><label id="mensagemDisciplina"></label></strong>
	          </div>
		      <div class="modal-body">
		      	<div class="form-group">
					<label id="formTitulo">Código</label>
		        	<input id="codigoDisciplina" class="form-control" name="codigoDisciplina" type="text" required>
					<label id="formTitulo">Disciplina</label>
		        	<input id="nomeDisciplina" class="form-control" name="nomeDisciplina" type="text" required>
				</div>
		      </div>
	     	 </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" id="sair" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="enviarDisciplina">Continuar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
    <!-- Modal Edicao -->
	<div class="modal fade" id="edicaoDisciplina" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="editModalTitulo">Edição de Disciplina</h4>
	      </div>
	     	 <div class="modal-body">
		      <div id="alertaDisciplina" class="alert hide">
	              <strong><label id="mensagemDisciplina"></label></strong>
	          </div>
		      <div class="modal-body">
		      	<div class="form-group">
					<label id="formTitulo">Código</label>
		        	<input id="editCodigoDisciplina" class="form-control" name="codigoDisciplina" type="text" required>
					<label id="formTitulo">Disciplina</label>
		        	<input id="editNomeDisciplina" class="form-control" name="nomeDisciplina" type="text" required>
				</div>
		      </div>
	     	 </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" id="sair" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="editDisciplina">Continuar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script
		src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
	<script src="/assets/lib/datepicker/js/bootstrap-datepicker.js"></script>
	<script
		src="/assets/lib/elevatezoom-master/jquery.elevateZoom-3.0.8.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/plugins/morris/raphael.min.js"></script>
	<script src="/assets/js/plugins/morris/morris.min.js"></script>
	<script src="/assets/lib/jquery.dataTables/jquery.dataTables.min.js"></script> 
	<script type="text/javascript">	
	$(document).ready(function() {
		$('#table').DataTable();
		var id;
		$('#adicionarDisciplina').bind('click', function(){
		    $('#cadastroDisciplina').modal();
		    $('#cadastroDisciplina').modal('show');
	    });	    
		$("[id^='editarDisciplina']").bind('click', function(){
			var editId = $(this).attr('id');
			var cod, nome;
			id = editId.match(/\d+/);
			cod = $('#cod'+id).text();
			nome = $('#nome'+id).text();
			$('#editNomeDisciplina').val(nome);
			$('#editCodigoDisciplina').val(cod);
			$('#edicaoDisciplina').modal();
		    $('#edicaoDisciplina').modal('show');
	    });	    
	    $('#enviarDisciplina').click(function(){
	    	$.ajax({
	    		url: "/disciplina/cadastro", 
	    		type: 'POST',
	    		data: {nomeDisciplina: $('#nomeDisciplina').val(), codigoDisciplina: $('#codigoDisciplina').val()},
	    		success: function(result){
					location.reload();
	        	},
	        	error: function(xhr, status, err){
	    			$('#alertaDisciplina').removeClass("alert-success hide").addClass("alert-danger");
	        		$('#mensagemDisciplina').text(xhr.responseText);
	    			$('#nomeDisciplina').val("");
	    			$('#codigoDisciplina').val("");
	        	}
	    	});
		});	    
	    $('#editDisciplina').click(function(){
	    	$.ajax({
	    		url: "/disciplina/editar", 
	    		type: 'POST',
	    		data: {nomeDisciplina: $('#editNomeDisciplina').val(), codigoDisciplina: $('#editCodigoDisciplina').val(), idDisciplina: id[0]},
	    		success: function(result){
					location.reload();
	        	},
	        	error: function(xhr, status, err){
	    			$('#alertaDisciplina').removeClass("alert-success hide").addClass("alert-danger");
	        		$('#mensagemDisciplina').text(xhr.responseText);
	    			$('#nomeDisciplina').val("");
	    			$('#codigoDisciplina').val("");
	        	}
	    	});
		});	
	});
	</script>
</body>
</html>
