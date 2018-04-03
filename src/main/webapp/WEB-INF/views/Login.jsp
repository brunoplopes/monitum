<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Login</title>
	<c:import url="headerHome.jsp"/>
    <style type="text/css">
	    .error{
	    	    color: #e74c3c;
	    }
	    .errorLogin {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #a94442;
			background-color: #f2dede;
			border-color: #ebccd1;
		}
		
		.msgLogin {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #31708f;
			background-color: #d9edf7;
			border-color: #bce8f1;
		}
		a {
		    color: #FFFFFF;
		    text-decoration: none;
		}
    </style>
</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <a class="navbar-brand page-scroll" href="/"><img src="/assets/img/logo-monitum-amarelo.png" width="100px"></a>
            </div>
        </div>
        <!-- /.container-fluid -->
    </nav>
    
    <!-- Contact Section -->
    <section id="contact">
        <div class="container">
        	<c:if test="${not empty mensagem }">
        		<div class="col-lg-12">
	              <div class="alert alert-warning">
				  <strong>${mensagem}</strong>
				</div>
          		 </div>
			</c:if>
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h1 class="section-heading"><strong>Login</strong></h1>
                    <h3 class="section-subheading text-muted"></h3>
                </div>
            </div>

            <div class="row">
				<form id="loginForm" name='loginForm' action="/login?targetUrl=${targetUrl }" method='POST'>
	                <div class="col-lg-12">
	                	<div class="col-lg-4 "></div>
	                	<div class="col-lg-4">
			                <c:if test="${not empty error}">
								<div class="errorLogin">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msgLogin">${msg}</div>
							</c:if>
							<c:if test="${not empty mensagemSenha }">
				        		<div>
						              <div class="alert alert-info alert-dismissable">
									 	 <strong>${mensagemSenha}</strong>
									</div>
				          		 </div>
							</c:if>
	                         <div class="form-group">
	                             <input type='text' name='username' data-rule-maxlength="150" class="form-control" placeholder="Email *" id="email" required/>
	                             <p class="help-block text-danger"></p>
	                         </div>
	                         <div class="form-group">
	                             <input type='password' name='password' class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Senha *" id="senha" required/>
	                             <label id="senha-error" class="error" for="senha" style="display: none">Este campo é requerido.</label>
	                             <a href="#" id="esqueciSenha" data-toggle="modal" data-target="#recuperarSenhaModal">Esqueci minha senha.</a>
	                             <p class="help-block text-danger"></p>
	                         </div>
	                     </div>
	                     <div class="col-lg-4 "></div>
	                </div>
	                <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                            	<div class="col-lg-4 text-center"></div>
	                            <div class="col-lg-4 text-center">
	                                <div id="success"></div>
	                                <button id="entrar" type="submit" class="btn btn-primary btn-block">Entrar</button>
	                                Ainda não tem sua conta? <a href="/usuario/cadastro">Cadastre-se</a>
	                            </div>
                            	<div class="col-lg-4 text-center"></div>
                            </div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	             </form>
            </div>
        </div>
    </section>
    
	    <!-- Modal -->
	<div class="modal fade" id="recuperarSenhaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Recuperar Senha</h4>
	      </div>
	      <div class="modal-body">
		      <div id="alerta" class="alert hide">
	              <strong><label id="mensagem"></label></strong>
	          </div>
	          <div id="inputRecuperarSenha">
		      	<div class="form-group">
					<label>Email</label>
		        	<input id="emailRecuperar" class="form-control" name="emailRecuperar" type="email" required>
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
    
	<c:import url="footerHome.jsp"/>
    <script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="/assets/js/classie.js"></script>
    <script src="/assets/js/cbpAnimatedHeader.js"></script>
    <script src="/assets/js/agency.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
	    $('#loginForm').validate();
	    $('#recuperar-senha').validate();
	    $('#enviar').click(function(){
		    if($('#emailRecuperar').val() !== ''){
		    	$.ajax({
		    		url: "/login/recuperar-senha", 
		    		type: 'GET',
		    		data: {email: $('#emailRecuperar').val()},
		    		success: function(result){
		    			$('#alerta').removeClass("alert-danger hide").addClass("alert-success");
		    			$('#mensagem').text(result);
		    			$('#emailRecuperar').val("");
		    			$('#inputRecuperarSenha').addClass('hide');
		    			$('#enviar').addClass('hide');
		        	},
		        	error: function(xhr, status, err){
		    			$('#alerta').removeClass("alert-success hide").addClass("alert-danger");
		        		$('#mensagem').text(xhr.responseText);
		    			$('#emailRecuperar').val("");
		        	}
		    	});
	    	}else{
	    		$('#alerta').removeClass("alert-success hide").addClass("alert-danger");
	    		$('#mensagem').text("Email obrigatório");
	    	}
	    });
    });
    </script>

</body>
</html>
