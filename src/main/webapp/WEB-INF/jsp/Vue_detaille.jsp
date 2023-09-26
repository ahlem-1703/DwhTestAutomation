<%@ page import="com.Springboot.example.service.DbServiceImpl" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="static jdk.nashorn.internal.runtime.GlobalFunctions.encodeURIComponent" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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


    <!-- Bootstrap -->
    <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="<c:url value="/resources/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>"
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
    <%@include file="./SpecificSideBar.jsp" %>
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




                                <form id="demo-form2" data-parsley-validate="" method="get" action="Vue_detaille"
                                      class="form-horizontal form-label-left">


                                    <table id="resultat2" class="table table-striped table-bordered" style="width:100%">
                                        <thead>

                                        <tr>
                                            <td>Query Code</td>
                                            <td>Date</td>
                                            <td>Val KPI 1</td>
                                            <td>Val KPI 2</td>
                                            <td>KPI Name</td>
                                            <td>Dimension</td>
                                            <td>GAP</td>
                                            <td>GAP%</td>
                                            <td>Flag NOT OK</td>
                                        </tr>


                                        </thead>


                                        <%--<tbody>
                                        <c:forEach items="${rslt}" var="u">
                                            <tr>
                                                <td>${u.getCode_requete()}</td>
                                                <td>${u.getDate()}</td>
                                                <td>${u. getName_kpi()}</td>
                                                <td>${u.getVal_dim()} </td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                      value="${u.getVal_kpi1()}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="0"
                                                                      value="${u.getVal_kpi2()}"/></td>
                                                    &lt;%&ndash;                         <td>${u.getVal_kpi1()}</td>  &ndash;%&gt;
                                                    &lt;%&ndash;                          <td>${u.getVal_kpi2()}</td> &ndash;%&gt;

                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${u.getGap()}"/></td>
                                                    &lt;%&ndash;                          <td>${u.getGap()} </td>  &ndash;%&gt;
                                                <td>${u.getNbreRecordOk()} </td>
                                                <td>${u.getNbreRecordNotOk()} </td>
                                                <td>${u.getDatabase1()} </td>
                                                <td>${u.getDatabase2()} </td>
                                                    &lt;%&ndash;                          <td>Value in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%></td> &ndash;%&gt;
                                                    &lt;%&ndash;                          <td>Value in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%></td> &ndash;%&gt;

                                            </tr>

                                        </c:forEach>
                                        </tbody>--%>
                                    </table>


                                    <!--<center>
                                        <button id="load12" class="btn btn-primary" type="button" name="load12"  onclick="generateReportF();">Global View</button>
                            </center> -->


                                </form>
                        </div>
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
<script src="<c:url value="/resources/vendors/pdfmake/build/vfs_fonts.js"/>"></script>

<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>


<script>
    function goToGlobalVue() {
        const globalVueUrl = localStorage.getItem('globalVueUrl')
        window.location.href = globalVueUrl
    }

</script>


<script type="text/javascript">
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


<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>


<script data-config="">

    var filtersConfig = {
        base_path: 'tablefilter/',

        col_4: 'none',
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
    /*var tf = new TableFilter('resultat2', filtersConfig);
    tf.init();

    function rtn() {
        window.history.back();
    }*/

    var tf = new TableFilter('resultat2', filtersConfig);
    tf.init();

    // Join the values with a delimiter
    <%
        String[] kpiiiValues = request.getParameterValues("kpiii");
        StringBuilder urlBuilder = new StringBuilder("/VueDetailleDataTable?");
        urlBuilder.append("db1=").append(request.getParameter("db1"));
        urlBuilder.append("&db2=").append(request.getParameter("db2"));
        urlBuilder.append("&DateDeb=").append(request.getParameter("DateDeb"));
        urlBuilder.append("&DateFin=").append(request.getParameter("DateFin"));

        if (kpiiiValues != null) {
            for (String kpiii : kpiiiValues) {
                urlBuilder.append("&kpiii=").append(kpiii);
            }
        }

        String url = urlBuilder.toString();
        %>
    $(document).ready(function () {
        var url = '<%= url %>';
        console.log(url)
        $('#resultat2').DataTable({
            serverSide: true,
            processing:true,
            ajax: {
                url: url,
                type:"GET",
                dataType: "json",
                contentType: "application/json",
                data: function(d) {
                    d.page = $('#resultat2').DataTable().page.info().page;
                    d.size = $('#resultat2').DataTable().page.info().length;
                }
            },
            columns: [
                {data: "code_requete"},
                {data: "date"},
                {data: "val_kpi1"},
                {data: "val_kpi2"},
                {data: "name_kpi"},
                {data: "val_dim"},
                {data: "gap"},
                {data: "GAP_par_100"},
                {data: "nbreRecordNotOk"}
            ]
        });
    });

</script>
</body>
</html>