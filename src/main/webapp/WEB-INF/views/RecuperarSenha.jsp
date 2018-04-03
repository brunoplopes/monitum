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
    <title>Recuperar Senha</title>
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
    </style>
</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="/">Monitum</a>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
    
    <!-- Contact Section -->
    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Recuperar senha</h2>
                    <h3 class="section-subheading text-muted"></h3>
                </div>
            </div>

            <div class="row">
				<form id="formSenha" name='formSenha' action="<c:url value='/login/recuperar-senha/${token}' />" method='POST'>
	                <div class="col-lg-12">
	                	<div class="col-lg-4 "></div>
	                	<div class="col-lg-4">
	                		<c:if test="${not empty mensagem }">
				        		<div>
					              	<div class="alert alert-warning">
								 		<strong>${mensagem}</strong>
									</div>
				          		 </div>
				           </c:if>
	                         <div class="form-group">
	                             <input id="senha" type='password' name='senha' class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Senha *"required/>
	                             <p class="help-block text-danger"></p>
	                         </div>
	                         <div class="form-group">
	                             <input type='password' name='senhaRepetida' data-rule-equalTo="#senha" class="form-control" data-rule-minlength="6" data-rule-maxlength="100" placeholder="Repita a Senha *" required/>
	                             <p class="help-block text-danger"></p>
	                         </div>
	                     </div>
	                     <div class="col-lg-4 "></div>
	                </div>
	                <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button id="entrar" type="submit" class="btn btn-xl">Alterar</button>
                            </div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	             </form>
            </div>
        </div>
    </section>
	<c:import url="footerHome.jsp"/>
    <script src="/assets/js/jquery.js"></script>
	<script src="/assets/lib/jquery_validate/dist/jquery.validate.min.js"></script>
	<script src="/assets/lib/jquery_validate/dist/localization/messages_pt_BR.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="/assets/js/classie.js"></script>
    <script src="/assets/js/cbpAnimatedHeader.js"></script>
    <script src="/assets/js/agency.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
	    $('#formSenha').validate();
    });
    </script>
</body>
</html>
