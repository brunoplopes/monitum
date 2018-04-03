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

    <title>Conteúdo</title>
   
    <c:import url="Header.jsp"/>
    <link type="text/css" rel="stylesheet" href="/assets/lib/datepicker/css/datepicker.css" />
    <link type="text/css" rel="stylesheet" href="/assets/css/timeline.css" />
    <style type="text/css">
    .error{
    	    color: #e74c3c;
    }
    .btn-circle{
		width: 50px;
		height: 50px;
		padding: 10px 16px;
		font-size: 18px;
		line-height: 1.33;
		border-radius: 25px;
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
                            Publicações <small></small>
                        </h1>
                        <div class="col-lg-10">
							<div class="form-group">
								 <a id="adicionar" href="/conteudo/criar/${idTurma}" class="btn btn-success">Adicionar</a>
							  </div>
							  <div class="panel panel-default">
		                        <div class="panel-heading">
		                            <i class="fa fa-clock-o fa-fw"></i> Publicações
		                        </div>
		                        <!-- /.panel-heading -->
		                        <div class="panel-body">
		                            <ul class="timeline">
			                            <c:forEach items="${ConteudoList }" var="conteudo" varStatus="status">
			                                <li id="tl${conteudo.idConteudo}" <c:if test="${status.index % 2 == 1 }">class="timeline-inverted"</c:if>>
			                                    <div class="timeline-badge"><img src="${conteudo.urlFoto }" width="50px" height="50px" class="img-circle">
			                                    </div>
			                                    <div class="timeline-panel">
			                                        <div class="timeline-heading">
			                                            <h4 class="timeline-title"><strong>${conteudo.titulo }</strong></h4>
			                                            <p><small class="text-muted">
			                                            	<i class="fa fa-clock-o"></i> <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${conteudo.dataPostagem}" />
			                                            	<c:if test="${conteudo.editado == true}">
			                                            		<i> - Editado em <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${conteudo.dataEdicao}" /></i>
			                                            	</c:if>
			                                            </small></p>
			                                        </div>
			                                        <hr>
			                                        <div class="timeline-body">
			                                            <p>${conteudo.descricao }</p>
														<sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')" var="Professor" />
														<c:choose>
														<c:when test="${idUsuario == conteudo.idUsuario}">
			                                            	<hr />
															<button id="edit" type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
	                 											<i class="fa fa-cog"></i>
	                 											<span class="caret"></span>
	                										</button>
											                <ul id="edit" class="dropdown-menu" role="menu">
	                  											<li><a href="/conteudo/editar/${conteudo.idConteudo}">Editar</a></li>
	                  											<li ><a href="#" id="excluir${conteudo.idConteudo}">Excluir</a></li>
											                </ul>
										                </c:when>
										                <c:when test="${idUsuario != conteudo.idUsuario && Professor}">
										                	<hr />
															<button id="excluir${conteudo.idConteudo}" type="button" class="btn btn-danger btn-sm">
	                 											<i class="fa fa-trash"></i>
	                										</button>	                
										                </c:when>
										                </c:choose>
			                                        </div>
			                                    </div>
			                                </li>
			                            </c:forEach>
		                            </ul>
		                        </div>
		                        <!-- /.panel-body -->
		                    </div>
			            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Modal Exclusão -->
	<div class="modal fade" id="exclusaoConteudo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Exclusão de Publicação</h4>
	      </div>
	     	 <div class="modal-body">
		      	<div class="form-group">
					<label id="lblModal">Tem certeza de que deseja excluir a publicação? Ela não poderá mais ser recuperada.</label>
	        	</div>
	        	<button type="button" class="btn btn-danger" data-dismiss="modal">Não</button>
	        	<a href="#" id="excluirConteudo"><button type="button" class="btn btn-primary" >Sim</button></a>
        		<div class="modal-footer"></div>
	      </div>
	    </div>
	  </div>
	</div>
    
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/plugins/morris/raphael.min.js"></script>
    <script src="/assets/js/plugins/morris/morris.min.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
    		var conteudoId;
    		$('[id^=edit]').each(function(){
        		if($(this).parents('.timeline-inverted').length > 0){
        			$(this).addClass('pull-right');
        		}
    		})
    		$('[id^=excluir]').each(function(){
        		if($(this).parents('.timeline-inverted').length > 0 && $(this).hasClass('btn-danger')){
        			$(this).addClass('pull-right');
        		}
    		})
    		$('[id^=excluir]').bind('click', function(){
    			var excludeId = $(this).attr('id');
    			conteudoId = excludeId.match(/\d+/);
    			$('#exclusaoConteudo').modal();
    		    $('#exclusaoConteudo').modal('show');
    		    $('#excluirConteudo').attr('href', "/conteudo/deletar/" + conteudoId[0]);
    	    });
    	});
    </script>
</body>
</html>
