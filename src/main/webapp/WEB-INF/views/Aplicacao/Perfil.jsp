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

<title>Perfil</title>
	<style type="text/css">
	    .error{
	    	    color: #e74c3c;
	    }
	    .fileUpload {
	    position: relative;
	    overflow: hidden;
	    margin: 10px;
		}
		.fileUpload input.upload {
		    position: absolute;
		    top: 0;
		    right: 0;
		    margin: 0;
		    padding: 0;
		    font-size: 20px;
		    cursor: pointer;
		    opacity: 0;
		    filter: alpha(opacity=0);
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
    <link type="text/css" rel="stylesheet" href="/assets/lib/cropper-2.2.5/cropper.min.css" />
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
						<h1 class="page-header">Perfil</h1>
					</div>
				</div>
				<div class="row">
					<c:if test="${not empty mensagem }">
		        		<div class="col-lg-8">
			              <div class="alert alert-success">
						  		<strong>${mensagem}</strong>
							</div>
		           		</div>
					</c:if>
					<c:if test="${not empty mensagemError }">
		        		<div class="col-lg-8">
			              <div class="alert alert-danger">
						  		<strong>${mensagemError}</strong>
							</div>
		           		</div>
					</c:if>
					<div class="col-lg-8 well bs-component">
						<fieldset>
	                    	<legend id="titulo">Dados</legend>
							<form:form name="UsuarioDTO" action="/usuario/perfil" method="POST" modelAttribute="UsuarioDTO" id="formCadastro">
		                        <div class="row">
		                        	<div class="col-md-4 text-center">
										<br>
			                        	<img id="fotoPreview" src="/usuario/foto/<sec:authentication property="principal.foto" />" style="width: 150; height: 150">
										<form:hidden path="foto"/>
										<div class="sign-u">
											<div class="fileUpload btn btn-primary">
											    <span>Upload foto</span>
												<input id="anexarFoto" name="anexarFoto" type="file" class="upload">
											</div>
										</div>
		                        	</div>
		                            <div class="col-md-6">
		                                <div class="form-group">
		                                	<label>Nome</label>
		                                    <form:input path="nome" data-rule-minlength="6" data-rule-maxlength="150" type="text" class="form-control" placeholder="Nome *" id="nome" required="true"/>
		                                </div>
		                                <div class="form-group">
		                                	<label>Telefone</label>
		                                    <form:input  path="telefone" type="tel" data-rule-maxlength="20" class="form-control" placeholder="Telefone *" id="telefone" required="true"/>
		                                </div>
		                                <div class="form-group">
		                                	<label>Email</label>
		                                    <form:input path="email" type="email" data-rule-maxlength="150" class="form-control" placeholder="Email *" id="email" required="true"/>
		                                </div>
		                            </div>
		                            <div class="clearfix"></div>
		                            <div class="col-lg-12 ">
		                                <div id="success"></div>
		                                <button id="cadastrar" type="submit" class="btn btn-primary pull-right">Salvar</button>
		                            </div>
		                        </div>
	                    	</form:form>
		                </fieldset>
					</div>
                    	<form:form class="formValidar" action="/usuario/alterarSenha" modelAttribute="SenhaDTO" method="POST" name='formSenha'>
	                    	<div class="col-lg-8 well bs-component">
	                    		<fieldset>
	                    		<legend id="titulo">Alterar senha</legend>
		                    		<div class="col-md-6">
			               			    <div class="form-group col-lg-12">
			                                <form:input type="password" class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Senha *" id="senha" path="senha" required="true"/>
			                            </div>
			               			    <div class="form-group col-lg-12">
			                                <form:input  type="password" class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Nova senha *" id="novaSenha" path="novaSenha" required="true"/>
			                            </div>
			                            <div class="form-group col-lg-12">
			                                <form:input type="password" class="form-control" placeholder="Repita a senha  *" data-rule-equalTo="#novaSenha" id="senhaRepetida" path="senhaRepetida" required="true"/>
			                            </div>
			                            <div class="form-group col-lg-12 pull-rigth">
			                            	<button type="submit" class="btn btn-primary pull-right">Alterar</button>
			                            </div>
		                    		</div>
	                    		</fieldset>
	                    	</div>
                    	</form:form>
				</div>
			</div>
		</div>
	</div>
	
	<div id="modalFoto" class="modal fade" >
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <!-- dialog body -->
	      <div class="modal-body">
	        <button type="button" class="close custom-close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Corte sua foto</h4>
	        	<div class="modal-footer">
	        	<div style="width: 500px; height: 300px;">
	        		<img id="fotoOriginal" class="cropper-hidden" src="">
	        		</div>
		        </div>
		      <div class="modal-footer">
		      	<button type="button" class="btn btn-default custom-close" data-dismiss="modal">Cancelar</button>
		      	<button type="button" class="btn btn-primary" id="crop-foto">Continuar</button>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
	
	<script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/assets/lib/jQuery-Mask-Plugin/jquery.mask.min.js"></script>
	<script src="/assets/lib/cropper-2.2.5/cropper.min.js"></script>
	<script src="/assets/js/main.js"></script>
	<script src="/assets/js/plugins/morris/raphael.min.js"></script>
	<script src="/assets/js/plugins/morris/morris.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$('#formCadastro').validate();
	});
	</script>
</body>
</html>