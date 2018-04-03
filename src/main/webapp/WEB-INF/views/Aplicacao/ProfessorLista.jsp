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

<title>Professores</title>

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
						<h1 class="page-header">Professores</h1>
						<div class="col-lg-10">
							<div class="col-lg-12">
								<div class="form-group col-md-4">
									<a id="adicionar" href="/professor/cadastro/" class="btn btn-primary">Adicionar</a>
								</div>
							</div>			
							<div class="table-responsive col-md-12"
								data-example-id="contextual-table">
								<table class="table table-bordered table-hover table-striped display" id="table">
									<thead>
										<tr>
											<th width="30%">Nome</th>
											<th width="20%">Registro</th>											
											<th width="30%">E-mail</th>
											<th width="5%">Coordenador</th>
											<th width="5%">Editar</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${Professor}" var="professor"
											varStatus="status">
											<tr
												class="<c:if test="${status.index % 2 == 0}">active</c:if>">
												<td id="nome${professor.id}">${professor.nome}</td>
												<td id="registro${professor.id}">${professor.registroProfessor}</td>												
												<td id="email${professor.id}">${professor.email}</td>
												<td>
													<input id="coord${professor.id}" value="${professor.coordenador}" type="hidden" />
													<c:choose>
														<c:when test="${professor.coordenador == true}">
															Sim
														</c:when>
														<c:otherwise>
															Não
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<ul class="nav nav-pills">
														<li><a href="#" id="editarProfessor${professor.id}" title="Editar"><i  class="fa fa-pencil-square-o" aria-hidden="true"></i></a></li>																												
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
	
   	<!-- Modal Edicao -->
	<div class="modal fade" id="edicaoProfessor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="editModalTitulo">Edição de Professor</h4>
	      </div>
	     	 <div class="modal-body">
		      <div id="alertaCurso" class="alert hide">
	              <strong><label id="mensagemProfessor"></label></strong>
	          </div>
		      <div class="modal-body">
		      	<div class="form-group">
					<label id="formTitulo">Nome</label>
		        	<input id="editNomeProfessor" class="form-control" name="nomeProfessor" type="text" required>
					<label id="formTitulo">Registro</label>
		        	<input id="editRegistroProfessor" class="form-control" name="registroProfessor" type="text" required>
					<label id="formTitulo">E-mail</label>
		        	<input id="editEmailProfessor" class="form-control" name="emailProfessor" type="text" required>
		        	<div class="checkbox"><label><input id="editCoord" type="checkbox">Coordenador</label></div>
				</div>
		      </div>
	     	 </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" id="sair" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="editProfessor">Continuar</button>
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
		$("[id^='editarProfessor']").bind('click', function(){
			var editId = $(this).attr('id');
			var registro, email, nome, coord;
			id = editId.match(/\d+/);
			nome = $('#nome'+id).text();
			registro = $('#registro'+id).text();
			email = $('#email'+id).text();
			coord = $('#coord'+id).val();
			$('#editNomeProfessor').val(nome);
			$('#editRegistroProfessor').val(registro);
			$('#editEmailProfessor').val(email);
			if(coord == "true"){
				$('#editCoord').prop('checked', true);
			}else{
				$('#editCoord').prop('checked', false);
			}
			$('#edicaoProfessor').modal();
		    $('#edicaoProfessor').modal('show');
	    });	    
	    $('#editProfessor').click(function(){
			var checado;
	    	if($('#editCoord').prop('checked')){
				checado = true;
			}else{
				checado = false;
			}
	    	$.ajax({
	    		url: "/professor/editar", 
	    		type: 'GET',
	    		data: {nomeProfessor: $('#editNomeProfessor').val(), registroProfessor: $('#editRegistroProfessor').val(), idProfessor: id[0], emailProfessor: $('#editEmailProfessor').val(), coordenador: checado},
	    		success: function(result){
					location.reload();
	        	},
	        	error: function(xhr, status, err){
	    			$('#alertaProfessor').removeClass("alert-success hide").addClass("alert-danger");
	        		$('#mensagemProfessor').text(xhr.responseText);
	    			$('#editNomeProfessor').val("");
	    			$('#editRegistroProfessor').val("");
	    			$('#editEmailProfessor').val("");
	        	}
	    	});
		});	
	});
	</script>
</body>
</html>
