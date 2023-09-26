<%@ page import="com.Springboot.example.service.DbServiceImpl" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script type="text/javascript">


        function validerDate() {
            alert(document.getElementById('dateDeb').value);
            var dateDeb = document.getElementById('dateDeb').value;
            document.querySelector('p.bleuu').textContent = document.getElementById('dateFin').value;
//Sélectionne le premier paragraphe du class bleuu du document et modifie son texte

        };

    </script>
    <style>
        h1 {
            color: red;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>DWH Test Automation</title>

    <link rel="stylesheet" href="../../../resources/css/table.css">
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
    <%@include file="../sideBarGlobal.jsp" %>

    <!-- top navigation -->

    <!-- /top navigation -->
    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Consultation</h3>
                </div>
            </div>

            <div class="clearfix"></div>
            <div class="row">
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Global View</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <!--<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <form id="demo-form2" data-parsley-validate="" method="get" action="HistoriqueVueGlobale"
                              class="form-horizontal form-label-left">
                            <div class="x_content">

                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="card-box table-responsive">
                                            <div class="item form-group" style="margin-left: 0">

                                                <label class="col-form-label col-md-2 col-sm-2 label-align">Date<span
                                                        class="required">*</span>
                                                </label>
                                                <div class="col-md-3 col-sm-3 ">
                                                    <input required class="form-control" type="date"
                                                           name="date">
                                                </div>
                                                <div class="col-md-3 col-sm-3 ">
                                                    <input class="btn btn-primary" type="submit" value="Find"/>
                                                </div>
                                            </div>
                                            <div class="item form-group">

                                                <div class="col-md-12 col-sm-12 " style="width:100%">
                                                    <table id="KpiListTable" name="KpiListTable"
                                                           class="table table-striped table-bordered"
                                                           style="width:100%">
                                                        <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Query Code</th>
                                                            <th>KPI Name</th>
                                                            <th>Subject_area</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${KpiList}" var="u">
                                                        <tr>
                                                            <td class="a-center ">
                                                                <input type="checkbox"
                                                                       value="${u.id_kpi}"
                                                                       name="kpi" id="kpi" class="flat">
                                                            </td>
                                                            <td>
                                                                ${u.id_kpi}
                                                            </td>
                                                            <td>
                                                                ${u.name_kpi}
                                                            </td>
                                                            <td>
                                                                ${u.subject_area}
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
                                <table id="GlobalVueDataTable" class="table table-striped table-bordered" style="width:100%">
                                    <thead>
                                    <tr role="row">
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <%--     <th>Date</th>--%>
                                        <th>Query Code</th>
                                        <th>KPI Name</th>
                                        <th>Total Kpi in DB1</th>
                                        <th>Total Kpi in DB2</th>
                                        <th> GAP</th>
                                        <th> GAP %</th>
                                        <th> Data Quality</th>
                                        <th> Acceptance</th>
                                        <%--  <th> Number Records <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%></th>
                                         <th> Number Records <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%></th>--%>
                                        <th> Number Records OK</th>
                                        <th> Number Records Not OK</th>
                                        <%--  <th> Not Found in  <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%></th>
                                         <th> Not Found in  <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%></th>--%>
                                        <th>Execution Time</th>
                                        <th>Execution Date</th>

                                    </tr>
                                    </thead>


                                    <tbody>
                                    <c:forEach items="${rslt}" var="u">

                                        <tr>
                                            <td>${u.getDateDeb()}</td>
                                            <td>${u.getDateFin()}</td>
                                                <%--  <td>${u.getDate()}</td>--%>
                                            <td>${u.getCode_requete()}</td>
                                            <td>${u. getName_kpi()}</td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                  value="${u.getVal_kpi1()}"/></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                  value="${u.getVal_kpi2()}"/></td>
                                            <td>${u.getGap()} </td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                                  value="${u.getGAP_par_100()}"/>%
                                            </td>

                                                <%--                          <td> ${u.getGAP_par_100()}% </td> --%>
                                            <td>${u.getDataQualite()}%</td>
                                            <td>

                                                <c:if test="${u.acceptation=='OK'}">
                                                    <span style='background-color:#39FF14;display:block;width:${u.getAcceptation()}%'>${u.acceptation}</span>
                                                </c:if>
                                                <c:if test="${u.acceptation=='NOT OK'}">

                                                    <span style='background-color:#FF2400;display:block;width:${u.getAcceptation()}%'>${u.acceptation}</span>

                                                </c:if>
                                                <c:if test="${u.acceptation=='Partially OK'}">
                                                    <span style='background-color:#FFEF00;display:block;width:${u.getAcceptation()}%'>${u.acceptation}</span>
                                                </c:if>
                                            </td>

                                            <td>${u.getNbreRecordOk()} </td>
                                            <td>${u.getNbreRecordNotOk()} </td>
                                                <%--         <td>${u.getFoundDB1()} </td>
                                                          <td>${u.getFoundDB2()} </td> --%>
                                            <td>${u.getTempsExec()} Ms</td>
                                            <td>${u.getDateExec()} </td>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>


                            </div>
                        </form>
                    </div>


                </div>
            </div>
        </div>

    </div>
</div>


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

<script src="<c:url value="/resources/vendors/pdfmake/build/pdfmake.min.js"/>"></script>


<script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
<script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>

<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>
<script type="text/javascript">
    var filtersConfig = {
        base_path: 'tablefilter/',
        col_0: 'none',
        col_1: 'id_kpi',
        col_2: 'name_kpi',
        col_3: 'Subject_area',
        popup_filters: true,
        auto_filter: {
            delay: 1100 //milliseconds
        },
        alternate_rows: true,
        rows_counter: true,
        btn_reset: true,
        status_bar: true,
        msg_filter: 'Filtering...',
        col_widths: [
            '125px', '90px', '100px',
            '90px', '90px', '90px',
            '90px', '90px', '90px'
        ]
    };
    var filterGlobalVueDataTableConfig = {
        base_path: 'tablefilter/',
        col_0: 'date_deb',
        col_1: 'date_fin',
        col_2: 'code_requete',
        col_3: 'val_kpi1',
        col_3: 'val_kpi2',
        col_3: 'gap',
        col_3: 'gap_par_100',
        col_3: 'nbre_record_not_ok',
        col_3: 'nbre_record_ok',
        col_3: 'data_qualite',
        col_3: 'acceptation',
        col_3: 'name_kpi',
        col_3: 'seuil_data_quality',
        col_3: 'seuil',
        col_3: 'seuil_gap',
        col_3: 'date_exec',
        col_3: 'temps_exec',
        popup_filters: true,
        auto_filter: {
            delay: 1100 //milliseconds
        },
        alternate_rows: true,
        rows_counter: true,
        btn_reset: true,
        status_bar: true,
        msg_filter: 'Filtering...'

    };
    var tf = new TableFilter(document.getElementById('KpiListTable'), filtersConfig);
    var tGlobalVueDataTablef = new TableFilter(document.getElementById('GlobalVueDataTable'), filtersConfig);
    tf.destroy();
    tf.init();
    tGlobalVueDataTablef.destroy();
    tGlobalVueDataTablef.init();
    $().ready(function () {
        $('#KpiListTable').DataTable({
            lengthMenu: [5,10, 20, 50, 100, 200],
        });
    });
    $().ready(function () {
        $('#GlobalVueDataTable').DataTable();
    });
</script>
<script type="text/javascript">
    function generateReportY() {
        document.forms['demo-form2'].action = 'resultat4';
        document.forms['demo-form2'].submit();

    }
</script>
</body>
</html>