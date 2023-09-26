<%@ page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="<c:url value="/resources/fonts/font-awesome-4.7.0/css/pgFont.css"/>" rel="stylesheet">

    <!--===============================================================================================-->
    <link href="<c:url value="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>" rel="stylesheet">


    <!--===============================================================================================-->
    <link href="<c:url value="/resources/vendor/select2/select2.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">

    <!-- Datatables -->

    <link href="../vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">

</head>
<style>
    span.ingredient {
        color: #f00;
    }
    .input-container {
        margin-bottom: 20px;
        display: flex;
        align-items: center;
    }

    .input-label {
        flex: 1;
        margin-right: 30px;
        font-weight: bold;
    }

    .input-select {
        flex: 18;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
    }
</style>
<body class="nav-md">
<div class="container body">
    <%@include file="../sideBarGlobal.jsp" %>

    <!-- page content -->
        <div class="right_col" role="main">
            <div class="">
                <div class="page-title">
                    <div class="title_left">
                        <h3>Manage Database</h3>
                    </div>

                    <div class="title_right">
                        <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                            <div class="input-group">
                                <!-- <input type="text" class="form-control" placeholder="Search for...">
                                <span class="input-group-btn">
                          <button class="btn btn-default" type="button">Search</button>
                        </span>-->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix"></div>

                <div class="row">
                    <div class="col-md-12 col-sm-12  ">
                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Database Connection</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <!--<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
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


            <form:form class="login100-form validate-form p-b-33 p-t-5" method="POST" modelAttribute="database">
                <div id="showAndDelete" class="input-container">
                    <select id="selectDatabase" class="browser-default custom-select" data-validate="Enter System Source" onchange="selectChange()">
                        <option value="">-- Select Database Type --</option>
                        <option value="SQL">SQL</option>
                        <option value="NoSQL">No SQL</option>
                    </select>
                </div>

                <div class="form-group  ${status.error ? 'has-error' : ''}" id="select1">
                    <label class="input100 col-md-4 col-sm-3 ">Source_System Relational database </label>
                    <div class="wrap-input100 validate-input" data-validate="Enter System Source">
                        <form:select class="browser-default custom-select" path="system" id="systemSQL">
                            <form:option value="">--select database--</form:option>
                            <form:option value="sqlserver">SqlServer</form:option>
                            <form:option value="postgresql">Postgres</form:option>
                            <form:option value="oracle">Oracle</form:option>
                            <form:option value="MySql">MySql</form:option>
                        </form:select>
                    </div>
                </div>
                <div class="form-group  ${status.error ? 'has-error' : ''}" id="select2">
                    <label class="input100 col-md-4 col-sm-3 ">Source_System No relational database</label>
                    <div class="wrap-input100 validate-input" data-validate="Enter System Source">
                        <form:select class="browser-default custom-select" path="system" id="systemNoSQL">
                            <form:option value="">--select database--</form:option>
                            <form:option value="MongoDb">MongoDb</form:option>
                        </form:select>
                    </div>
                </div>
                <div id="formConn">
                    <span class="ingredient"><h4>${message}</h4></span>
                    <div class="form-group  ${status.error ? 'has-error' : ''}">

                        <label class="input100 col-md-3 col-sm-3 ">Name Database </label>

                        <div class="wrap-input100 validate-input" data-validate="Enter Database Name">
                            <form:input type="text" class="input100" placeholder="Name" path="name" id="name"
                                        required="required"/>
                            <span class="focus-input100" data-placeholder="&#xe80e;"></span>
                        </div>

                    </div>
                    <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">Port </label>

                        <div class="wrap-input100 validate-input" data-validate="Enter port">
                            <form:input type="text" class="input100" placeholder="Port" path="port" id="port"
                                        required="required"/>
                            <span class="focus-input100" data-placeholder="&#xe87d;"></span>
                        </div>

                    </div>
                    <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">UserName </label>
                        <div class="wrap-input100 validate-input" data-validate="Enter Username">
                            <form:input type="text" class="input100" placeholder="user" path="username" id="username"
                                        required="required"/>
                            <span class="focus-input100" data-placeholder="&#xe82a;"></span>
                        </div>

                    </div>
                    <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">URL </label>
                        <div class="wrap-input100 validate-input" data-validate="Enter URL">
                            <form:input type="text" class="input100" placeholder="url" path="url" id="url"/>
                            <span class="focus-input100" data-placeholder="&#xe82a;"></span>
                        </div>

                    </div>
                    <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">Password </label>
                        <div class="wrap-input100 validate-input" data-validate="Enter password">
                            <form:input type="password" class="input100" placeholder="*****" path="password"
                                        id="password"/>
                            <span class="focus-input100" data-placeholder="&#xe80f;"></span>
                        </div>

                    </div>
                </div>
                <div class="form-group ">
                    <div class="col-md-9 col-sm-9  offset-md-3">
                        <div class="row">
                            <button type="button" id="btn1" class="login100-form-btn">choose database</button>
                            <button type="submit" id="btn2" class="login100-form-btn">add database</button>
                        </div>
                    </div>
                </div>

            </form:form>
        </div>


    </div>
    <!-- /page content -->
</div>

<!-- /footer content -->

<style>
    .bs-example {
        margin: 20px;
    }
</style>


<!-- jQuery -->

<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->

<script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
<!-- FastClick -->
<script src="../vendors/fastclick/lib/fastclick.js"></script>
<!-- NProgress -->
<script src="../vendors/nprogress/nprogress.js"></script>
<!-- Chart.js -->
<script src="../vendors/Chart.js/dist/Chart.min.js"></script>
<!-- jQuery Sparklines -->
<script src="../vendors/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
<!-- morris.js -->
<script src="../vendors/raphael/raphael.min.js"></script>
<script src="../vendors/morris.js/morris.min.js"></script>
<!-- gauge.js -->
<script src="../vendors/gauge.js/dist/gauge.min.js"></script>
<!-- bootstrap-progressbar -->
<script src="../vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- Skycons -->
<script src="../vendors/skycons/skycons.js"></script>
<!-- Flot -->
<script src="../vendors/Flot/jquery.flot.js"></script>
<script src="../vendors/Flot/jquery.flot.pie.js"></script>
<script src="../vendors/Flot/jquery.flot.time.js"></script>
<script src="../vendors/Flot/jquery.flot.stack.js"></script>
<script src="../vendors/Flot/jquery.flot.resize.js"></script>
<!-- Flot plugins -->
<script src="../vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
<script src="../vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
<script src="../vendors/flot.curvedlines/curvedLines.js"></script>
<!-- DateJS -->
<script src="../vendors/DateJS/build/date.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="../vendors/moment/min/moment.min.js"></script>
<script src="../vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

<!-- iCheck -->
<script src="<c:url value="/resources/vendors/iCheck/icheck.min.js"/>"></script>
<!-- Datatables -->
<script src="../vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script src="../vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="../vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script src="../vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
<script src="../vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script src="../vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
<script src="../vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
<script src="../vendors/jszip/dist/jszip.min.js"></script>
<script src="../vendors/pdfmake/build/pdfmake.min.js"></script>
<script src="../vendors/pdfmake/build/vfs_fonts.js"></script>
<!-- jQuery Smart Wizard -->
<script src="<c:url value="/resources/vendor/select2/select2.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>

<!-- jQuery Tags Input -->
<script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>


<script>
    function selectChange(){
        var selectDatabase = document.getElementById("selectDatabase");
        var selectValue = selectDatabase.value;
        if(selectValue == 'SQL'){
            document.getElementById('select1').hidden = false;
            document.getElementById('select2').hidden = true;
        }
        else if(selectValue == 'NoSQL'){
            document.getElementById('select2').hidden = false;
            document.getElementById('select1').hidden = true;
        }
    }
    $(document).ready(function () {
        $("#myTab a").click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });
    });

    document.addEventListener('DOMContentLoaded', function () {

        // Disable submit button on page load
        document.getElementById('btn1').hidden = false;
        document.getElementById('btn2').hidden = true;
        document.getElementById('formConn').hidden = true;
        document.getElementById('select1').hidden = true;
        document.getElementById('select2').hidden = true;

        document.getElementById('btn1').addEventListener('click', function () {
            document.getElementById('formConn').hidden = !document.getElementById('formConn').hidden;
            document.getElementById('select1').hidden = true;
            document.getElementById('select2').hidden = true;
            document.getElementById("showAndDelete").hidden = !document.getElementById("showAndDelete").hidden;

            document.getElementById('btn2').hidden = !document.getElementById('btn2').hidden;
        });
    });
</script>
</body>
</html>
