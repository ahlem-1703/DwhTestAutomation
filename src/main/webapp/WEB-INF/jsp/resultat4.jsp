
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageSize" value="10"/>
<c:set var="totalPages" value="${totalPages}"/>
<c:set var="currentPage" value="${not empty currentPage ? currentPage : 1}"/>

<!DOCTYPE html>
<html lang="en">

<head>

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


    <!-- Bootstrap -->
    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath}/resources/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="<c:url value="/resources/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>"
          rel="stylesheet">

    <!-- bootstrap-daterangepicker -->
    <link href="${pageContext.request.contextPath}/resources/vendors/bootstrap-daterangepicker/daterangepicker.css"
          rel="stylesheet">
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
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">


                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="<c:url value="/resources/images/a.png"/>" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>
                        <h2>Admin</h2>
                    </div>
                </div>
                <!-- /menu profile quick info -->

                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>General</h3>
                        <ul class="nav side-menu" class="toggled">

                            <li>
                                <a><i class="fa fa-line-chart"></i>Manage KPIs/Queries
                                    <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu" style="display: block;">
                                    <li><a href="listRequetes">Queries List</a>
                                    </li>
                                    <li><a href="listKpis">KPIs List</a>
                                    <li><a href="manage_kpi ">Add Kpis & Queries</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a><i class="fa fa-database"></i>Manage Connection
                                    <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu" style="display: block;">
                                    <li><a href="list">Databases List</a>
                                    </li>
                                    <li><a href="manage_connexion">Add Database</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a><i class="fa fa-bolt"></i>Result
                                    <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu" style="display: block;">
                                    <li><a href="resultat">Launch Script</a>
                                    <li><a href="LoadAllData">Global View</a>
                                    <li><a href="resultat4">Detailed View</a>

                                </ul>
                            </li>
                            
                            <li>
                                <a><i class="fa fa-history"></i>Consultation
                                    <span class="fa fa-chevron-down"></span></a>
                             </li>
                        </ul>
                    </div>

                </div>

            </div>
            <!-- /sidebar menu -->


        </div>
    </div>

    <!-- top navigation -->
    <div class="top_nav">
        <div class="nav_menu">
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>
            <nav class="nav navbar-nav">
                <ul class=" navbar-right">
                    <li class="nav-item dropdown open" style="padding-left: 15px;">
                        <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true"
                           id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                            <img src="<c:url value="/resources/images/img.jpg"/>" alt="">Admin
                        </a>
                        <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="javascript:;"> Profile</a>


                            <a class="dropdown-item" href="login.html"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                        </div>
                    </li>


                </ul>
            </nav>
        </div>
    </div>
    <!-- /top navigation -->
    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Manage KPI
                        s</h3>
                </div>

                <div class="title_right">
                    <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                        <div class="input-group">
                            <!--<input type="text" class="form-control" placeholder="Search for...">
                            <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Search</button>
                    </span>  -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="clearfix"></div>
            <div class="row">
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Detailed View</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">


                            <button class="btn btn-primary" onclick="rtn()"><i class="fa fa-angle-double-left"></i>
                            </button>
                            <form id="demo-form2" data-parsley-validate="" method="POST"
                                  class="form-horizontal form-label-left">


                                <h2>Download Data File</h2>
                                <form id="demo-form22" data-parsley-validate="" method="get" action="Vue_detaille"
                                      class="form-horizontal form-label-left">
                                    <%--<table id="datatable1" class="table table-striped table-bordered" style="width:100%">
                                        <thead>
                                        <tr>
                                            <td>Code_requete</td>
                                            <td>Date</td>
                                            <td>Name_kpi</td>
                                            <td>Dimension</td>
                                            <td>Value DB1</td>
                                            <td>Value DB2</td>
                                            <td>GAP</td>
                                            <td> Flag OK</td>
                                            <td> Flag Not OK</td>

                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>--%>

                                    <table id="datatable1" class="table table-striped table-bordered" style="width:100%">
                                        <thead>
                                        <tr>
                                            <td>Query Code</td>
                                            <td>Date</td>
                                            <td>KPI Name</td>
                                            <td>Dimension</td>
                                            <td>Value DB1</td>
                                            <td>Value DB2</td>
                                            <td>GAP</td>
                                            <td> Flag OK</td>
                                            <td> Flag Not OK</td>

                                        </tr>
                                        </thead>
                                        <%--<tbody>
                                        <c:forEach items="${rslt}" var="u">
                                            <tr>
                                                <td>${u["code_requete"]}</td>
                                                <td>${u["date"]}</td>
                                                <td>${u["name_kpi"]}</td>
                                                <td>${u["val_dim"]} </td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                      value="${u['val_kpi1']}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                      value="${u['val_kpi2']}"/></td>
                                                    &lt;%&ndash;                         <td>${u.getVal_kpi1()}</td>  &ndash;%&gt;
                                                    &lt;%&ndash;                          <td>${u.getVal_kpi2()}</td> &ndash;%&gt;

                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${u['gap']}"/></td>
                                                    &lt;%&ndash;                          <td>${u.getGap()} </td>  &ndash;%&gt;
                                                <td>${u['nbreRecordOk']} </td>
                                                <td>${u['nbreRecordNotOk']} </td>

                                                    &lt;%&ndash;                          <td>Value in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%></td> &ndash;%&gt;
                                                    &lt;%&ndash;                          <td>Value in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%></td> &ndash;%&gt;

                                            </tr>

                                        </c:forEach>
                                        </tbody>--%>
                                   </table>
                              <%--      <div class="row">
                                        <div class="col-sm-12">
                                            <div class="dataTables_paginate paging_simple_numbers">
                                                <ul class="pagination">
                                                    <c:choose>
                                                        <c:when test="${currentPage > 1}">
                                                            <li class="page-item"><a class="page-link"
                                                                                     href="?page=${currentPage - 1}">«</a>
                                                            </li>
                                                            <c:forEach begin="${currentPage -1}"
                                                                       end="${currentPage + 4}" var="i">
                                                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                                                    <a class="page-link" href="?page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="${currentPage}" end="${currentPage + 4}"
                                                                       var="i">
                                                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                                                    <a class="page-link" href="?page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <li class="page-item">
                                                        <a href="" class="page-link"><span>...</span></a>
                                                    </li>
                                                    <c:choose>
                                                        <c:when test="${currentPage < totalPages}">
                                                            <c:forEach begin="${totalPages -4}" end="${totalPages}"
                                                                       var="i">
                                                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                                                    <a class="page-link" href="?page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item"><a class="page-link"
                                                                                     href="?page=${currentPage + 1}">»</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="${totalPages -4}" end="${totalPages}"
                                                                       var="i">
                                                                <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
                                                                    <a class="page-link" href="?page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </ul>
                                            </div>
                                        </div>
                                    </div>
--%>
                                </form>


                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <!-- jQuery -->


    <script src="/resources/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->

    <script src="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <!-- FastClick -->
    <script src="/resources/vendors/fastclick/lib/fastclick.js"></script>

    <!-- NProgress -->
    <script src="${pageContext.request.contextPath}/resources/vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->

    <!-- jQuery Sparklines -->

    <!-- morris.js -->
    <script src="/resources/vendors/raphael/raphael.min.js"></script>
    <script src="/resources/vendors/morris.js/morris.min.js"></script>
    <!-- gauge.js -->
    <script src="/resources/vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- Skycons -->
    <script src="/resources/vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="/resources/vendors/Flot/jquery.flot.js"></script>
    <script src="/resources/vendors/Flot/jquery.flot.pie.js"></script>
    <script src="/resources/vendors/Flot/jquery.flot.time.js"></script>
    <script src="/resources/vendors/Flot/jquery.flot.stack.js"></script>
    <script src="/resources/vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="/resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="/resources/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="/resources/vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="/resources/vendors/DateJS/build/date.js"></script>
    <!-- bootstrap-daterangepicker -->

    <script src="${pageContext.request.contextPath}/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

    <!-- iCheck -->
    <script src="/resources/vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="/resources/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="/resources/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="/resources/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="/resources/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="/resources/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="/resources/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="/resources/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="/resources/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="/resources/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="/resources/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="/resources/vendors/jszip/dist/jszip.min.js"></script>

    <script src="/resources/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="/resources/vendors/pdfmake/build/vfs_fonts.js"></script>

    <!-- jQuery Smart Wizard -->

    <script src="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"></script>

    <!-- jQuery Tags Input -->
    <script src="${pageContext.request.contextPath}/resources/vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>

    <!-- Custom Theme Scripts -->
<%--    <script src="/resources/build/js/custom.min.js"></script>--%>
    <!-- iCheck 1.0.1 -->


    <script type="text/javascript">


        //
        // DataTables initialisation
        //
        $(document).ready(function () {
            $('#datatable1').DataTable({
                serverSide: true,
                processing:true,
                ajax: {
                    url: "/resultat4Table",
                    type:"GET",
                    dataType: "json",
                    contentType: "application/json",
                    data: function(d) {
                        d.page = $('#datatable1').DataTable().page.info().page;
                        d.size = $('#datatable1').DataTable().page.info().length;
                    }
                },
                columns: [
                    {data: "code_requete"},
                    {data: "date"},
                    {data: "name_kpi"},
                    {data: "val_dim"},
                    {data: "val_kpi1"},
                    {data: "val_kpi2"},
                    {data: "gap"},
                    {data: "nbreRecordOk"},
                    {data: "nbreRecordNotOk"}
                ]
            });
        });

        function disableUsedOptions($table) {
            $selects = $table.find("select");
            $selects.on("change", function () {
                $selects = $table.find("select");

                console.log("In table:");
                console.log($table);
                console.log("there are " + $selects.length + " selects");
                if ($selects.length <= 1) return;
                let selected = [];

                $selects.each(function (index, select) {
                    if (select.value !== "") {
                        selected.push(select.value);
                    }
                });

                console.log("option values, that are being deactivated: " + selected);

                $table.find("option").prop("disabled", false);
                for (var index in selected) {
                    $table
                        .find('option[value="' + selected[index] + '"]:not(:selected)')
                        .prop("disabled", true);
                }
            });
            $selects.trigger("change");
        }

        $tables = $("body");
        $tables.each(function (index) {
            $table = $(this);
            disableUsedOptions($table);
        });

        function afficher_pop() {
            document.getElementById('a').hidden = false;

        }

        function generateReportD() {
            document.forms['demo-form2'].action = 'resultat2';
            document.forms['demo-form2'].submit();

        }

        function generateReportA() {
            document.forms['demo-form2'].action = ' resultat200';
            document.forms['demo-form2'].submit();

        }

        function generateReportB() {
            document.forms['demo-form2'].action = ' resultat20';
            document.forms['demo-form2'].submit();

        }

        function generateReportX() {
            document.forms['demo-form2'].action = ' deletekpi';
            document.forms['demo-form2'].submit();

        }

        function generateReportZ() {
            document.forms['demo-form2'].action = ' Vue_Global';
            document.forms['demo-form2'].submit();

        }

        function generateReportY() {
            document.forms['demo-form2'].action = ' Vue_detaille';
            document.forms['demo-form2'].submit();

        }

        function generateReportH() {
            document.forms['demo-form2'].action = ' deletevd';
            document.forms['demo-form2'].submit();

        }

        function generateReportF() {
            document.forms['demo-form2'].action = ' Vue_Globale';
            document.forms['demo-form2'].submit();

        }
    </script>

    <%--<script>
        $(document).ready(function() {
            $('#datatable').DataTable({
                "paging": true,
                "pageLength": 10, // number of rows per page
                "searching": true, // hide search bar
                "lengthChange": true // hide rows per page selection
            });
        });
    </script>--%>
    <script src="/resources/js/tablefilter.js"></script>

    <script type="text/javascript">


        function validerDate() {
            alert(document.getElementById('dateDeb').value);
            var dateDeb = document.getElementById('dateDeb').value;
            document.querySelector('p.bleuu').textContent = document.getElementById('dateFin').value;
//Sélectionne le premier paragraphe du class bleuu du document et modifie son texte

        };

    </script>
    <script>

        // var filtersConfig = {
        //     base_path: 'tablefilter/',
        //
        //     col_4: 'none',
        //     popup_filters: true,
        //     auto_filter: {
        //         delay: 1100 //milliseconds
        //     },
        //     alternate_rows: true,
        //     rows_counter: true,
        //     btn_reset: true,
        //     status_bar: true,
        //     msg_filter: 'Filtering...',
        //     col_widths: [
        //         '125px', '90px', '100px',
        //         '90px', '90px', '90px',
        //         '90px', '90px', '90px'
        //     ]
        // };
        // var tf = new TableFilter('datatable', filtersConfig);
        // tf.init();

        // function rtn() {
        //     window.history.back();
        // }

        // var tf = new TableFilter('resultat2', filtersConfig);
        // tf.init();

        // $(document).ready(function () {
        //     $('#resultat2').DataTable({
        //         dom: 'Bfrtip',
        //         buttons: [
        //             'excelHtml5', PDF,
        //         ]
        //     });
        // });




        <%--$(document).ready(function () {--%>
        <%--    $('#datatable').DataTable({--%>
        <%--        "pageLength": 10, // number of rows per page--%>
        <%--        "searching": true, // hide search bar--%>
        <%--        "lengthChange": true,--%>
        <%--        "paging": true,--%>
        <%--        "processing": true,--%>
        <%--        "serverSide": true,--%>
        <%--        "ajax": {--%>
        <%--            "url": "resultat4",--%>
        <%--            "data": function (d) {--%>
        <%--                d.page = ${page};--%>
        <%--                d.size = ${size};--%>
        <%--            }--%>
        <%--        },--%>

        <%--        "columns": [--%>
        <%--            {"data": "code_requete"},--%>
        <%--            {"data": "date"},--%>
        <%--            {"data": "name_kpi"},--%>
        <%--            {"data": "val_dim"},--%>
        <%--            {"data": "val_kpi1"},--%>
        <%--            {"data": "val_kpi2"},--%>
        <%--            {"data": "gap"},--%>
        <%--            {"data": "nbreRecordOk"},--%>
        <%--            {"data": "nbreRecordNotOk"}--%>
        <%--        ]--%>
        <%--    });--%>
        <%--});--%>
    </script>
</body>
</html>