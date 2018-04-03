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
    <style type="text/css">
    .error{
    	    color: #e74c3c;
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
    	<c:import url="HeaderMenu.jsp"/>
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Frequência
                        </h1>
                        <div class="col-lg-10">
							<c:if test="${not empty mensagem }">
				              <div class="alert alert-success">
							  		<strong>${mensagem}</strong>
								</div>
							</c:if>
							<div class="">
								<label>Novo registro</label>
								<div class="form-group">
									<a href="/frequencia/turma/${idTurma }" class="btn btn-primary">Criar</a>
								</div>
							</div>
							<div class="col-lg-12">
								<table id="table" class="table table-bordered table-hover table-striped display">
							        <thead>
							            <tr>
							                <th>Data aula</th>
							                <th>Período</th>
							                <th>Aulas</th>
							                <th></th>
							            </tr>
							        </thead>
							        <tbody>
							        	<c:forEach items="${registroList }" var="registro">
								            <tr>
								                <td>${registro.dataAula }</td>
								                <td>${registro.periodo }</td>
								                <td>${registro.quantidadeAula }</td>
								                <td><a href="/frequencia/editar/${registro.idFrequencia }" class="btn btn-warning"> Editar</a></td>
								            </tr>
							        	</c:forEach>
							        </tbody>
								</table>
							</div>
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
	<script src="/assets/lib/jquery.dataTables/jquery.dataTables.min.js"></script> 

    <script type="text/javascript">
    	$(document).ready(function() {
    		$('#table').DataTable();
    	});
    </script>
    
</body>
</html>
