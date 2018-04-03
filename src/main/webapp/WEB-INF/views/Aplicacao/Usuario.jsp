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

    <title>Usuário</title>
   
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
    <c:import url="HeaderMenu.jsp"/>
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Turmas <small></small>
                            <sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
	                            <a id="adicionar" href="/turma/cadastro/" class="btn btn-primary">Adicionar</a>
								<!--<a href="/turma/finalizadas/" class="btn btn-primary pull-right">Turmas Finalizadas</a>-->
                            </sec:authorize>
                        </h1>
                        <div class="row">
                        	<c:forEach items="${listaTurmas}" var="item">
				                <div class="col-lg-4 col-md-6">
				                    <div class="panel panel-default panel-turmas">
				                        <div class="panel-heading">
				                            <div class="row">
				                                <div class="col-xs-3">
					                                <div class="huge">${item.codigo }</div>
				                                </div>
				                                <div class="col-xs-9 text-right">
				                                    <div>${item.nomeDisciplina }</div>
				                                    <sec:authorize access="hasRole('ESTUDANTE')">
											             <label style="float: right;" >Faltas ${item.faltas }</label>
										             </sec:authorize>
				                                </div>
				                            </div>
				                        </div>
				                            <div class="panel-footer">
					                               	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i><b class="caret"></b></a>
										             <ul class="dropdown-menu">
				                            			<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
											                 <li>
											                     <a href="/frequencia/listar/${item.idTurma }"><i class="fa fa-fw fa-th-list"></i>Chamada</a>
											                 </li>
											                 <li class="divider"></li>
				                            			</sec:authorize>
											                 <c:if test="${item.calendario.length() > 0}">
												                 <li>
												                     <a href="/turma/calendario/${item.idTurma }"><i class="fa fa-fw fa-calendar"></i>Calendário</a>
												                 </li>
												                 <li class="divider"></li>
											                 </c:if>
											                 <li>
											                     <a href="/atividade/${item.idTurma }"><i class="fa fa-fw fa-book"></i>Atividade</a>
											                 </li>
											                 <li class="divider"></li>
				                            			<sec:authorize access="hasRole('USUARIO')">
											                 <li>
											                     <a href="/conteudo/listar/${item.idTurma }"><i class="fa fa-fw fa-comment"></i>Conteúdo</a>
											                 </li>
											                 <li class="divider"></li>
				                            			</sec:authorize>
				                            			<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
											                 <li>
											                     <a href="/grupo/listar/${item.idTurma }"><i class="fa fa-fw fa-users"></i>Grupo</a>
											                 </li>
											                 <li class="divider"></li>
				                            			</sec:authorize>
				                            			<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
											                 <li>
											                     <a href="/turma/editar/${item.idTurma }"><i class="fa fa-fw fa-pencil-square-o"></i>Editar</a>
											                 </li>
				                            			</sec:authorize>
				                            			<sec:authorize access="hasRole('ESTUDANTE')">
											                 <li>
											                     <a href="/grupo/listarGrupos/${item.idTurma }"><i class="fa fa-fw fa-users"></i>Grupo</a>
											                 </li>
				                            			</sec:authorize>                     			
										             </ul>
				                            </div>
				                   	 </div>
				                </div>
                        	</c:forEach>
			            </div>
						<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
							<a href="/turma/arquivadas/" class="btn btn-primary pull-right">Turmas Desativadas</a>
						</sec:authorize>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<div class="modal fade" id="finalizar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="modalTitulo">Fechar Turma</h4>
				</div>
				<div class="modal-body">
					<div id="alertaCurso" class="alert hide">
						<strong><label id="mensagemCurso"></label></strong>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label id="formTitulo">Código</label>
							<input id="codigoCurso" class="form-control" name="codigoCurso" type="text" required>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="sair" data-dismiss="modal">Sair</button>
					<button type="button" class="btn btn-primary" id="enviarCurso">Continuar</button>
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
