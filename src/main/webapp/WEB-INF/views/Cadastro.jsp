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

    <title>Cadastro</title>
	<c:import url="headerHome.jsp"/>
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
    </style>
    <link type="text/css" rel="stylesheet" href="/assets/lib/cropper-2.2.5/cropper.min.css" />
</head>

<body id="page-top" class="index">
<div id="container"> 
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <a class="navbar-brand page-scroll" href="/"><img src="/assets/img/logo-monitum-amarelo.png" width="100px"></a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                	<div class="navbar-header page-scroll">
                        <a class="navbar-brand page-scroll" href="/login">Login</a>
                    </div>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
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
                    <h2 class="section-heading"><strong>Cadastro</strong></h2>
                    <h3 class="section-subheading text-muted"></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                <form:errors path="*" cssClass="error"/>
                    <form:form name="UsuarioDTO" action="/usuario/cadastro" method="POST" modelAttribute="UsuarioDTO" class="formValidar" id="formCadastro">
                        <div class="row">
                        	<div class="col-md-4 text-center">
								<label id="anexarFoto-error" class="error" for="anexarFoto" style="display: none;"></label><br>
	                        	<img id="fotoPreview" src="" style="width: 150; height: 150">
								<form:hidden path="foto" required="true"/>
								<div class="sign-u">
									<div class="fileUpload btn btn-primary">
									    <span><strong>Upload foto</strong></span>
										<input id="anexarFoto" name="anexarFoto" type="file" class="upload" required data-msg="É obrigatório o envio da foto.">
									</div>
								</div>
                        	</div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <form:input path="nome" data-rule-minlength="6" data-rule-maxlength="150" type="text" class="form-control" placeholder="Nome *" id="nome" required="true"/>
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <form:input path="registro" data-rule-minlength="4" data-rule-maxlength="20" type="text" class="form-control" placeholder="Registro *" id="email" required="true"/>
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <form:input  path="telefone" type="tel" data-rule-maxlength="20" class="form-control" placeholder="Telefone *" id="telefone" required="true"/>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <form:input path="email" type="email" data-rule-maxlength="150" class="form-control" placeholder="Email *" id="email" required="true"/>
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input  type="password" class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Senha *" id="senha" required/>
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <form:input path="senha" type="password" class="form-control" placeholder="Repita a senha  *" data-rule-equalTo="#senha" id="confirmacaoSenha" required="true"/>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                         
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button id="cadastrar" type="submit" class="btn btn-primary"><strong>Cadastrar</strong></button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>    
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
	
	<c:import url="footerHome.jsp"/>
	
	<script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
	<script src="/assets/lib/cropper-2.2.5/cropper.min.js"></script>
	<script src="/assets/lib/jQuery-Mask-Plugin/jquery.mask.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="/assets/js/classie.js"></script>
    <script src="/assets/js/cbpAnimatedHeader.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/assets/js/agency.js"></script>
 </body>
</html>
