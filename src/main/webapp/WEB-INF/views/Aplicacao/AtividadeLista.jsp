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

<title>Atividades</title>

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
		<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')" var="Professor" />
		<sec:authorize access="hasRole('ESTUDANTE')" var="Aluno" />
		<c:import url="HeaderMenu.jsp" />
		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Atividades <small>${turma} - ${disciplina}</small>
						</h1>
						<div class="col-lg-10">
							<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
								<div class="col-lg-12">
									<div class="form-group col-md-4">
										<a id="adicionar" href="/atividade/criar/${turmaId}" class="btn btn-primary">Adicionar</a>
									</div>
								</div>
							</sec:authorize>

							<div class="table-responsive col-md-12"
								data-example-id="contextual-table">
								<table class="table table-bordered table-hover table-striped display" id="table">
									<thead>
										<tr>
											<th width="30%">Atividade</th>
											<th class="hidden-xs" width="5%">Data de entrega</th>
											<th class="hidden-xs" class="hidden-xs" width="10%">Tipo</th>
											<c:if test="${Aluno}">
												<th width="5%">Detalhes</th>	
											</c:if>
											<c:if test="${Professor}">
												<th width="20%">Ações</th>											
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${AtividadeDTO}" var="atividade" varStatus="status">
											<tr
												class="<c:if test="${status.index % 2 == 0}">active</c:if>">
												<td>${atividade.titulo}</td>
												<td class="hidden-xs"><fmt:formatDate value="${atividade.dataEntrega}" pattern="dd/MM/yyyy" /></td>
												<td class="hidden-xs">${atividade.tipoAtividade}</td>
												<td>
													<ul class="nav nav-pills">
														<c:if test="${Professor}">
															<li><a href="/atividade/editar/${atividade.id}" title="Editar"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></li>
														</c:if>
														<li><a href="/atividade/detalhes/${atividade.id}" title="Detalhes"><i class="fa fa-bars" aria-hidden="true"></i> </a></li>
														<c:if test="${Professor}">
															<li><a href="/atividade/atividadesTurma?idAtividade=${atividade.id}" title="Editar Notas"><i class="fa fa-book" aria-hidden="true"></i> </a></li>
															<li><a href="#" id="deletarAtividade${atividade.id}" title="Remover" onclick="chamaModal(this)"><i class="fa fa-trash-o" aria-hidden="true"></i></a></li>
														</c:if>
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
	
    <!-- Modal Exclusão -->
	<div class="modal fade" id="exclusaoAtividade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Exclusão de Atividade</h4>
	      </div>
	     	 <div class="modal-body">
		      	<div class="form-group">
					<label id="lblModal">Tem certeza de que quer excluir a atividade? Todos os dados relacionados a ela, como notas, serão perdidos.</label>
	        	</div>
	        	<button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Não</button>
	        	<a href="#" id="excluirAtividade"><button type="button" class="btn btn-primary pull-right" >Sim</button></a>
        		<div class="modal-footer"></div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
	<script src="/assets/lib/datepicker/js/bootstrap-datepicker.js"></script>
	<script src="/assets/lib/elevatezoom-master/jquery.elevateZoom-3.0.8.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/js/plugins/morris/raphael.min.js"></script>
	<script src="/assets/js/plugins/morris/morris.min.js"></script>
	<script src="/assets/lib/jquery.dataTables/jquery.dataTables.min.js"></script> 
	<script type="text/javascript">	
	$(document).ready(function() {
		$('#table').DataTable();
		$("[id^='deletarAtividade']").bind('click', function(){
			var excludeId = $(this).attr('id');
			atividadeId = excludeId.match(/\d+/);
			$('#exclusaoAtividade').modal();
		    $('#exclusaoAtividade').modal('show');
		    $('#excluirAtividade').attr('href', '/atividade/deletar/' + atividadeId);
	    });	  	    
	});
	</script>
</body>
</html>
