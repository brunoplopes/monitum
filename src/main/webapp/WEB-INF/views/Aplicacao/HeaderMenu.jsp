<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<style type="text/css">
		.perfil {
		    padding-top: 0px !important;
		    padding-bottom: 0px !important;
		    line-height: 0px !important;
		    padding: 5px 20px !important;
		}
	</style>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
     <!-- Brand and toggle get grouped for better mobile display -->
     <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
             <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="/usuario"><img src="/assets/img/logo-monitum.png" width="100px" /></a>
     </div>
     <!-- Top Menu Items -->
     <ul class="nav navbar-right top-nav">
     	 
         <li class="dropdown">
             <a href="#" class="dropdown-toggle perfil" data-toggle="dropdown"><img src="/usuario/foto/<sec:authentication property="principal.foto" />" width="40px" height="40px" class="img-circle"></img> <sec:authentication property="principal.nome" />  <b class="caret"></b></a>
             <ul class="dropdown-menu">
                 <li>
                     <a href="/usuario/perfil"><i class="fa fa-fw fa-user"></i> Perfil</a>
                 </li>
                 <li class="divider"></li>
                 <li>
                     <a href="/logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                 </li>
             </ul>
         </li>
     </ul>
     <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
     <div class="collapse navbar-collapse navbar-ex1-collapse">
         <ul class="nav navbar-nav side-nav">
             <li>
                 <a href="/usuario"><i class="fa fa-fw fa-square-o"></i> Home</a>
             </li>
             <sec:authorize access="hasRole('COORDENADOR')">
	             <li>
	                 <a href="/professor/"><i class="fa fa-fw fa-university"></i> Professores</a>
	             </li>
   	             <li>
	                 <a href="/aluno/"><i class="fa fa-fw fa-graduation-cap"></i> Alunos</a>
	             </li>
	             <li>
	                 <a href="/curso/"><i class="fa fa-fw fa-th"></i> Cursos</a>
	             </li>	
	             <li>
	                 <a href="/disciplina/"><i class="fa fa-fw fa-th-large"></i> Disciplinas</a>
	             </li>	          
	         </sec:authorize>
             <sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENADOR')">
	             <li>
	                 <a href="/usuario"><i class="fa fa-fw fa-list-alt"></i> Turma</a>
	             </li>
             </sec:authorize>
         </ul>
     </div>
     <!-- /.navbar-collapse -->
 </nav>
