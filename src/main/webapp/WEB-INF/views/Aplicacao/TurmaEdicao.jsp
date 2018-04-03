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

<title>Edição de Turma</title>
	<style type="text/css">
	    .error{
	    	    color: #e74c3c;
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
						<h1 class="page-header">Edição de Turma</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-8 well bs-component">
					  <div id="alerta" class="alert hide">
			              <strong><label id="mensagem"></label></strong>
			          </div>
						<form:form id="formTurma" action="/turma/editar" method="POST"
							commandName="TurmaDTO" modelAttribute="TurmaDTO" enctype="multipart/form-data">
							<input type="hidden" name="idTurma" id="idTurma" value="${idTurma}" />
							<label>Curso</label>
							<div class="form-group input-group">
								<form:select class="form-control" path="cursoId" required="true">
									<option value=""></option>
									<form:options items="${cursoList }"/>
								</form:select>
								<span class="input-group-addon cursorPointer modalCurso" id="adicionarCurso">+</span>
							</div>
							<label id="cursoId-error" class="error" style="display: none" for="cursoId">Este campo é requerido.;</label>
							<br><label>Disciplina</label>
							<div class="form-group input-group">
								<form:select class="form-control" path="disciplinaId" required="true">
									<option value=""></option>
									<form:options items="${disciplinaList }"/>
								</form:select>
								<span class="input-group-addon cursorPointer modalDisciplina" id="adicionarDisciplina">+</span>
							</div>
							<label id="disciplinaId-error" class="error" style="display: none" for="disciplinaId">Este campo é requerido.</label>
							<div class="form-group">
								<br><label>Código da Turma</label>
								<form:input class="form-control" path="codigoTurma" required="true"/>
								<br><label>Aulas por dia</label>
								<form:input path="aulasDia" type="number" class="form-control bfh-number" data-zeros="true"  min="1" max="20" required="true"/>								
								<br><label>Link do Calendário (compatível apenas com Google Agenda)</label>
								<form:input path="calendario" class="form-control" />
								<span id="calendario-error" class="error" style="display: none;"><strong>Informe um endereço válido.</strong></span>
								<label><small>Caso queira compartilhar sua Agenda Google, informe nesse campo o endereço privado <abbr title="Formato: https://calendar.google.com/calendar/ical/google%40gmail.com/private-1a2b3c4d5e6f7g8h9i10j11k12l13/basic.ics">ICAL</abbr> da mesma. 
								Para isso, basta seguir o passo-a-passo "Ver sua agenda" <a href="https://support.google.com/calendar/answer/37648?hl=pt-BR" target="_blank">neste link</a>.<br>
								Para que todos possam vê-la, é necessário compartilha-la. Veja <a href="https://support.google.com/calendar/answer/37082?rd=1" target="_blank">aqui</a> como compartilhar.</small></label>
							</div>
							<br><label>Lista de Alunos</label>							
							<div class="form-group hidden-xs input_fields" style="margin-bottom: 15px;">
								<fieldset disabled>
									<label>Ex:</label>
									<div class="row aluno" style="margin-bottom: 15px;"> 
									  <div class="col-xs-2">
									    <input type="text" class="form-control" placeholder="01" >
									  </div>
									  <div class="col-xs-4">
									    <input type="text" class="form-control" placeholder="João Souza" >
									  </div>
									  <div class="col-xs-4">
									    <input type="text" class="form-control" placeholder="XXXXXXXXX" >
									  </div>
									</div>
								</fieldset>
								<c:set var="count" value="0" />
								<c:forEach items="${TurmaDTO.alunos}" var="aluno" varStatus="status">
									<div class="row aluno">
										<div class="col-xs-2">
											<form:input type="text" class="form-control" placeholder="Código" path="alunos[${count}].codigo" value="${aluno.codigo}" required="true"/>
										</div>
										<div class="col-xs-4">
											<form:input type="text" class="form-control" placeholder="Nome" path="alunos[${count}].nome" value="${aluno.nome}" required="true" />
										</div>
										<div class="col-xs-4">
											<form:input type="text" class="form-control" placeholder="Prontuário" path="alunos[${count}].prontuario" value="${aluno.prontuario}" required="true" />
										</div>
										<div class="form-group col-xs-2">
											<button id="remover${count}" type="button" class="btn btn-danger remover">Remover</button>
										</div>
									</div>
									<c:set var="count" value="${count+1}" />
								</c:forEach>
							</div>
							<div class="form-group">
							 	<button id="adicionar" type="button" class="btn btn-success">Adicionar</button>
							</div>
							<button type="button" id="fecharTurma" class="btn btn-danger pull-left">Encerrar Turma</button>
							<button type="submit" class="btn btn-primary pull-right">Cadastrar</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
    <!-- Modal Curso-->
	<div class="modal fade" id="cadastroCurso" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Cadastro de Curso</h4>
	      </div>
	     	 <div class="modal-body">
		      <div id="alertaCurso" class="alert hide">
	              <strong><label id="mensagemCurso"></label></strong>
	          </div>
		      <div class="modal-body">
		      	<div class="form-group">
					<label id="formTitulo">Nome do Curso</label>
		        	<input id="curso" class="form-control" name="curso" type="text" required>
				</div>
		      	<div class="form-group">
					<label id="formTitulo">Código do Curso</label>
		        	<input id="codCurso" class="form-control" name="codCurso" type="text" required>
				</div>
		      </div>
	     	 </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="enviarCurso">Continuar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
    <!-- Modal Disciplina-->
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
	        <button type="button" class="btn btn-default" data-dismiss="modal">Sair</button>
	        <button type="button" class="btn btn-primary" id="enviarDisciplina">Continuar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
    <!-- Modal Impossivel remover ultimo aluno -->
	<div class="modal fade" id="aviso" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Aviso!!!</h4>
	      </div>
	     	 <div class="modal-body">
			      <strong><label id="mensagemAviso">Não é possivel remover o aluno. É necessário que haja, ao menos, um aluno na turma.</label></strong>
	     	 </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal Exclusão -->
	<div class="modal fade" id="fecharTurmaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalTitulo">Encerramento de Turma</h4>
	      </div>
	     	 <div class="modal-body">
		      	<div class="form-group">
					<label id="lblModal">Tem certeza de que deseja encerrar a turma? Ela será arquivada em suas turmas encerradas e não poderá mais ser reativada.</label>
	        	</div>
	        	<button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Não</button>
	        	<a href="#" id="encerrarTurma"><button type="button" class="btn btn-primary pull-right" >Sim</button></a>
        		<div class="modal-footer"></div>
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
		$('#formTurma').validate();
		$('#formCurso').validate();
		$('#formTurma').submit(function(){
			var cal = $('#calendario').val();
			if(cal.length > 0){
				if(!cal.contains("google") || !cal.contains("calendar") || !cal.match(/ics$/)){
					$('#calendario-error').css('display', 'block');
					return false;
				}
			}
		});
		$('#calendario').change(function(){
			if(cal.length > 0){
				if(!$(this).val().contains("google") || !$(this).val().contains("calendar") || !$(this).val().match(/ics$/)){
					$('#calendario-error').css('display', 'block');
				}else{
					$('#calendario-error').css('display', 'none');
				}
			}else{
				$('#calendario-error').css('display', 'none');
			}
		});
		
	    var limite      = 50;
	    var campos         = $(".input_fields");
	    var adicionar      = $("#adicionar");
	    
	    var x = ${count};
	    $(adicionar).click(function(e){
	        if(x < limite){
	            $(campos).append('<div class="row aluno"> ' +
	            		'<div class="col-xs-2">' +
	            		'<input type="text" class="form-control" placeholder="Código" name="alunos['+ x +'].codigo" required>' +
	            		'</div>' +
	            		'<div class="col-xs-4">' +
	            		'<input type="text" class="form-control nome'+ x +'" placeholder="Nome" name="alunos['+ x +'].nome" required>' +
	            		'</div>' +
	            		'<div class="col-xs-4">' +
	            		'<input type="text" class="form-control searchAluno" data-nome="nome'+ x +'" placeholder="Prontuário" name="alunos['+ x +'].prontuario" required>' +
	            		'</div>' +
	            		'<div class="form-group col-xs-2">' +
	            		'<button id="remover'+ x +'" type="button" class="btn btn-danger remover">Remover</button>' +
	            		'</div>' +
	            		'</div>');
	            x++;
	        }
	    });
		$(campos).on("focusout",".searchAluno", function(e){
			var $imput = $(this).data('nome');
			$.ajax({
				url: "/turma/buscarAluno/" + $(this).val(),
				type: 'GET',
				success: function(result){
					if(result.nome != undefined)
						$('.' + $imput).val(result.nome)
				},
				error: function(xhr, status, err){
					console.log("error");
				}
			});
		});
	    $(campos).on("click",".remover", function(e){ 
	        if($('.aluno').length > 2){
		    	$(this).parents('div.row.aluno').remove();
		        x--;
	        }else{
			    $('#aviso').modal();
			    $('#aviso').modal('show');
	        }
	    });
	    $('#adicionarCurso').bind('click', function(){
		    $('#cadastroCurso').modal();
		    $('#cadastroCurso').modal('show');
	    });
	    $('#enviarCurso').click(function(){
	    	$.ajax({
	    		url: "/curso/cadastro", 
	    		type: 'GET',
	    		data: {cursoNome: $('#curso').val(), codCurso: $('#codCurso').val()},
	    		success: function(result){
	    			$('#cursoId').append($('<option>', {value:result, text: $('#curso').val()}));
	    			$('#alerta').removeClass("alert-danger hide").addClass("alert-success");
	    			$('#mensagem').text("Curso Cadastrado com Sucesso!");
	    			$('#curso').val("");
	    			$('#cadastroCurso').modal('hide');
	        	},
	        	error: function(xhr, status, err){
	    			$('#alertaCurso').removeClass("alert-success hide").addClass("alert-danger");
	        		$('#mensagemCurso').text(xhr.responseText);
	    			$('#curso').val("");
	        	}
	    	});
		});
	    $('#adicionarDisciplina').bind('click', function(){
		    $('#cadastroDisciplina').modal();
		    $('#cadastroDisciplina').modal('show');
	    });	    
	    $('#enviarDisciplina').click(function(){
	    	$.ajax({
	    		url: "/disciplina/cadastro", 
	    		type: 'GET',
	    		data: {nomeDisciplina: $('#nomeDisciplina').val(), codigoDisciplina: $('#codigoDisciplina').val()},
	    		success: function(result){
	    			$('#disciplinaId').append($('<option>', {value:result, text: $('#codigoDisciplina').val() + ' - ' + $('#nomeDisciplina').val()}));
	    			$('#alerta').removeClass("alert-danger hide").addClass("alert-success");
	    			$('#mensagem').text("Disciplina Cadastrada com Sucesso!");
	    			$('#nomeDisciplina').val("");
	    			$('#codigoDisciplina').val("");
	    			$('#cadastroDisciplina').modal('hide');
	        	},
	        	error: function(xhr, status, err){
	    			$('#alertaDisciplina').removeClass("alert-success hide").addClass("alert-danger");
	        		$('#mensagemDisciplina').text(xhr.responseText);
	    			$('#nomeDisciplina').val("");
	    			$('#codigoDisciplina').val("");
	        	}
	    	});
		});
	    $("#fecharTurma").bind('click',function(){
			$('#fecharTurmaModal').modal();
		    $('#fecharTurmaModal').modal('show');
		    $('#encerrarTurma').attr('href', '/turma/encerrar/' + $("#idTurma").val());
	    });
	});
	</script>
</body>
</html>