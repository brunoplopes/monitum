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
    <link type="text/css" rel="stylesheet" href="/assets/lib/datepicker/css/datepicker.css" />
    <style type="text/css">
    .error{
    	    color: #e74c3c;
    }
    .calendario {
	    display: table;
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
                            Frequência <small>${FrequenciaDTO.codigoTurma} - ${FrequenciaDTO.codigoDisciplina}</small>
                        </h1>
                        <div class="col-lg-10">
							<form:form id="formFrequencia" action="/frequencia/turma/${FrequenciaDTO.idTurma}" method="POST" commandName="FrequenciaDTO" modelAttribute="FrequenciaDTO">                         		 
								<div class="col-lg-12">
									<div class="col-lg-4">
										<label>Data</label>
										<div class="input-append date form-group calendario" data-date-format="dd/mm/yyyy">
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											<form:input class="form-control" path="dataAula" id="dataAula" size="16" type="text" readonly="true" required="true"/>
									    	<label id="dataAula-error" class="error" for="dataAula" style="display: none;">Este campo é requerido.</label>
									    </div>
									</div>
									<div class="form-group col-md-4">
										<label>Período</label>
										<form:select class="form-control" path="periodo" required="true">
											<form:option value=""></form:option>
											<form:options items="${periodoList }" />
										</form:select>
									</div>
									<div class="form-group col-md-4">
										<label>Numero de aulas</label>
										<form:select class="form-control" path="quantidadeAula" required="true" id="quantidadeAula">
											<c:forEach var="i" begin="1" end="${FrequenciaDTO.quantidadeAula}">
												<form:option value="${i }">${i }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<c:if test="${ not empty atividadeList && atividadeList.size() > 1}">
									<div class="form-group col-md-12">
										<div class="form-group col-md-10"></div>
										<div class="form-group col-md-2 pull-rigth">
											<label>Atividades</label>
											<select class="form-control" name="idAtividade" id="atividade">
												<option value=""></option>
												<c:forEach items="${atividadeList }" var="atividade" varStatus="status">
													<option value="${atividade.key}">${atividade.value}</option>
			                                    </c:forEach>
											</select>
										</div>
									</div>
								</c:if>
								<input class="hide" name="idFrequencia" value="${FrequenciaDTO.idFrequencia}">
								<div class="col-md-12 table-responsive">
									<table id="frequenciaTable" class="table table-striped tablesorter">
										<thead>
											<tr>
												<th class="hidden-xs" width="5%"># <i class="fa fa-fw fa-sort"></i></th>
												<th width="30%">Nome <i class="fa fa-fw fa-sort"></i></th>
												<th class="hidden-xs" width="20%">Grupo<i class="fa fa-fw fa-sort"></i></th>
												<th class="hidden-xs" width="10%">Foto</th>
												<th width="40%">Chamada</th>
												<th width="10%">Marcar Todos</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${FrequenciaDTO.alunosFrequencia}" var="aluno" varStatus="status">
												<input class="hide" name="alunosFrequencia[${status.index}].idAluno" value="${aluno.idAluno}">
												<tr class="<c:if test="${status.index % 2 == 0 }">active</c:if>">
													<th class="hidden-xs" scope="row">${aluno.codigo}</th>
													<td>${aluno.nome }</td>
													<td class="hidden-xs idAluno-${aluno.idAluno}">${aluno.grupo}</td>
													<td class="hidden-xs"><img src="${aluno.urlFoto}" data-zoom-image="${aluno.urlFoto}" width="40px" height="40px" class="img-circle imagem"/></td>
													<td>
														<div class="form-group">
															<c:forEach var="i" begin="0" end="${FrequenciaDTO.quantidadeAula <= 0 ? 0 :FrequenciaDTO.quantidadeAula -1}">
								                                <label class="checkbox-inline">
								                                    <input class="check${status.index} checkbox" name="alunosFrequencia[${status.index}].frequencia[${i }]" type="checkbox" <c:if test="${aluno.frequencia.get(i)}">checked</c:if>>
								                                </label>
															</c:forEach>
							                            </div>
													</td>
													<td>
														<div class="form-group">
							                                <div class="checkbox">
							                                    <label>
							                                        <input class="checkAll" data-check="check${status.index}" type="checkbox" value="">
							                                    </label>
							                                </div>
						                            	</div>
													</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary pull-right">Continuar</button>
								</div>
							</form:form>
			            </div>
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
	<script src="/assets/lib/jquery.tablesorter/jquery.tablesorter.js"></script> 

    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#formFrequencia').validate();
    		$('#dataAula').datepicker({
    		    format: 'dd/mm/yyyy',
    		    language: 'pt-BR'
    		});
    		$(".checkAll").click(function () {
    			console.log($(this).data('check'));
    		    $('.' + $(this).data('check')).prop('checked', $(this).prop('checked'));
    		});
    		$(".imagem").elevateZoom({zoomWindowPosition: 10, zoomWindowHeight: 200, zoomWindowWidth:200, borderSize: 0, easing:true});
    		$("#frequenciaTable").tablesorter(); 
    		$('#atividade').change(function(){
    			$.ajax({
		    		url: '/atividade/gruposAlunos/' + $(this).val(), 
		    		type: 'GET',
		    		success: function(data){
		    			$.each(data, function(index, item) {
		    				$('.idAluno-' + item.key).text('');
		    				$('.idAluno-' + item.key).text(item.value);
		    			});
		        	},
		        	error: function(xhr, status, err){
						console.log(xhr.responseText);
		        	}
		    	});
    		});
    		var quantidadeAulas;
    		$("#quantidadeAula").change(function(){
    			var ate = $(this).val();
    			var quantidade = ${FrequenciaDTO.quantidadeAula -1};
    			var i = 0;
    			$.each($('.checkAll'),function(){
    				for (var j = quantidade; j >= ate; j--) {
        				console.log("input[name='alunosFrequencia[" + i + "].frequencia[" + j + "]']");
        				$("input[name='alunosFrequencia[" + i + "].frequencia[" + j + "]']").hide();
    				}
    				i++;
    			});
    		});
    	});
    </script>
    
</body>
</html>
