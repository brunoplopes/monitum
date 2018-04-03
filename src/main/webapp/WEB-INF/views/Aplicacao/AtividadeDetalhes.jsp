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
		<c:import url="HeaderMenu.jsp" />
		<div id="page-wrapper">
			<div class="container-fluid">
				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Atividades <small>${turma} - ${disciplina}</small>
						</h1>
						<h3>Atividade <i><b>${Atividade.titulo}</b></i></h3>
						<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
							<div class="col-lg-12">
								<div class="form-group col-md-4">
									<a id="adicionar" href="/atividade/editar/${Atividade.id}" class="btn btn-primary">Editar Atividade</a>
								</div>
							</div>
						</sec:authorize>
						<div>
							<hr />
							<dl class="dl-horizontal">
								<dt>Título</dt>
								<dd>${Atividade.titulo}</dd><br />

								<dt>Descrição</dt>
								<dd>${Atividade.descricaoAtividade}</dd><br />

								<dt>Tipo</dt>
								<dd>${Atividade.tipoAtividade}</dd><br />

								<dt>Data de Entrega</dt>
								<dd>${Atividade.dataEntrega}</dd><br />
								
								<sec:authorize access="hasRole('ESTUDANTE')">
									<dt>Nota</dt>
									<dd>${nota.nota}</dd><br />
								</sec:authorize>
							</dl>
						</div>
						<hr />
						<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
							<div class="col-lg-10">
								<h3>Notas</h3>
								<div class="form-group col-md-12">
									<a id="adicionar" href="/atividade/atividadesTurma?idAtividade=${Atividade.id}" class="btn btn-primary">Editar Notas</a>
								</div>
								<div class="col-md-6 table-responsive">
									<table class="table table-bordered table-hover table-striped display" id="table">
										<thead>
											<tr>
												<th>Aluno</th>
												<th>Nota</th>
											</tr>
										</thead>							
										<tbody>
											<c:forEach items="${alunoList}" var="aluno"
												varStatus="status">
												<tr
													class="<c:if test="${status.index % 2 == 0}">active</c:if>">
													<td>${aluno.nome}</td>
													<td>
														<c:choose>
														<c:when test="${not empty aluno.nota}">
															${aluno.nota}
														</c:when>
														<c:otherwise>
															-
														</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</div>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
    <script src="/assets/lib/datepicker/js/bootstrap-datepicker.js"></script>
    <script src="/assets/lib/elevatezoom-master/jquery.elevateZoom-3.0.8.min.js"></script>
    <script src="/assets/js/plugins/morris/raphael.min.js"></script>
    <script src="/assets/js/plugins/morris/morris.min.js"></script>
	<script src="/assets/lib/jquery.dataTables/jquery.dataTables.min.js"></script> 

    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#table').DataTable();
    	});
    </script>
</body>
</html>