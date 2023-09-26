<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html>
<html lang="en">

<head>
    <style>
        h1 {
            color: red;
        }

        .hide {
            display: none;
        }
    </style>
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
    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
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
    <%@include file="../sideBarGlobal.jsp" %>
    <!-- /top navigation -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Manage KPIs</h3>
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
                            <h2>Update KPI</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <!--<li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <c:url var="action" value="/update"> </c:url>

                            <form:form modelAttribute="kpi" class="form-horizontal form-label-left" method="post"
                                       action="${action}">


                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Father
                                        <span class="required">*</span>

                                    </label>

                                    <form:radiobutton path="type" NAME="type" value="Father"/>

                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Child
                                        <span class="required">*</span>

                                    </label>
                                    <form:radiobutton path="type" NAME="type" value="Child"/>
                                </div>


                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's Id <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="id_kpi" type="number" id="id_kpi" name="id_kpi"
                                                    required="required" class="form-control"/>

                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's Name<span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="name_kpi" type="text" placeholder="Code_requete" id="name_kpi"
                                                    name="name_kpi" required="required" class="form-control"/>

                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's Subject Area <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="subject_area" id="subject_area" name="subject_area"
                                                    class="form-control" required="required" type="text"/>

                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Dimensional rounding
                                        Limit <span class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="seuil" id="seuil" class="form-control" name="seuil"
                                                    required="required" type="number" step="0.01" min="0"/>

                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">GAP limit<span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="seuil_gap" id="seuil_gap" class="form-control"
                                                    name="seuil_gap" required="required" type="number" step="0.01"
                                                    min="0"/>

                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">DataQuality Limit <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:input path="seuil_dataQuality" id="seuil_dataQuality" class="form-control"
                                                    name="seuil_dataQuality" required="required" type="number"
                                                    step="0.01" min="0"/>

                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">
                                    Update
                                </button>

                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->

<script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js"/>"></script>

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

<script src="<c:url value="/resources/vendors/pdfmake/build/pdfmake.min.js"/>"></script>


<script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
<script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>

<!-- jQuery Tags Input -->
<script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
<!-- iCheck 1.0.1 -->
<script src="https://almsaeedstudio.com/themes/AdminLTE/plugins/iCheck/icheck.min.js"></script>

</script>
</body>
</html>