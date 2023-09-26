<%@ page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>DWH Test Automation</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">

    <!-- Datatables -->
    <link href="<c:url value="/resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>"
          rel="stylesheet">
    <link href="<c:url value="/resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"/>"
          rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
</head>

<body class="nav-md">
<div class="container body">
    <%@include file="../sideBarGlobal.jsp"%>

    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Query</h3>
                </div>

                <div class="title_right">
                    <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                        <div class="input-group">
                            <!-- <input type="text" class="form-control" placeholder="Search for...">
                            <span class="input-group-btn">
                              <button class="btn btn-default" type="button">Search</button>
                            </span> -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Queries List</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <!-- <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                 </li>-->
                                 <li class="dropdown">
                                     <!--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-expanded="false"><i class="fa fa-wrench"></i></a>-->
                                     <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">

                                     </div>
                                 </li>
                                <!--<li><a class="close-link"><i class="fa fa-close"></i></a>
                                 </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>

                        <div class="x_content">


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card-box table-responsive">

                                        <table id="listReq" class="table table-striped table-bordered" style="width:100%">

                                            <thead>
                                            <tr>
                                                <th>Query Code</th>
                                                <th>Date</th>
                                                <th>KPI Name</th>
                                                <th>Database Name</th>
                                                <th>Database System</th>
                                                <th>Val KPI</th>
                                                <th>Copr</th>

                                                <th>Group BY</th>
                                                <th>Update</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${ReqList}" var="emp">
                                                <tr>
                                                    <td>${emp.code_requete}</td>
                                                    <td>${emp.date}</td>

                                                    <td> ${emp.kpi.name_kpi}</td>
                                                    <td> ${emp.id_databasee.name}</td>
                                                    <td> ${emp.id_databasee.system}</td>
                                                    <td>${emp.val_kpi}</td>
                                                    <td>${emp.copr}</td>


                                                    <td>
                                                        <c:forEach items="${emp.dims}"
                                                                   var="dim"> ${dim.val_dim}<br> </c:forEach>

                                                    <td>

                                                        <a type="submit" href="<c:url value='/editrequete/${emp.id}' />"
                                                           class="btn btn-dark btn-xs"><i class="fa fa-pencil"></i> </a>
                                                    </td>
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
        </div>
    </div>
</div>


<!-- /page content -->

<!-- footer content -->
<footer>
    <div class="pull-right">

    </div>
    <div class="clearfix"></div>
</footer>
<!-- /footer content -->


<!-- jQuery -->

<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->

<script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js"/>"></script>



<!-- iCheck -->
<script src="<c:url value="/resources/vendors/iCheck/icheck.min.js"/>"></script>
<!-- Datatables -->
<script src="<c:url value="/resources/vendors/datatables.net/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.flash.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.html5.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.print.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"/>"></script>
<script src="<c:url value="/resources/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/jszip/dist/jszip.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>


<script src="<c:url value="/resources/vendors/pdfmake/build/pdfmake.min.js"/>"></script>

<script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
<script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#listReq').DataTable();
    });
</script>
</body>
</html>
