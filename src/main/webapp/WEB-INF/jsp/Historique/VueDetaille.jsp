<%@ page import="com.Springboot.example.service.DbServiceImpl" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                <!--<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">

                            <form id="myForm" class="form-horizontal form-label-left form-group">
                                <div class="form-group">
                                    <label class="col-form-label col-md-2 col-sm-2 label-align" for="dateDeb">Start Date<span class="required">*</span></label>
                                    <div class="col-md-3 col-sm-2">
                                        <input required class="form-control" type="date" id="dateDeb" name="dateDeb">
                                    </div>

                                    <label class="col-form-label col-md-2 col-sm-2 label-align" for="dateFin">End Date<span class="required">*</span></label>
                                    <div class="col-md-3 col-sm-2">
                                        <input required class="form-control" type="date" id="dateFin" name="dateFin">
                                    </div>
                                    <input class="col-md-1 col-sm-1 btn btn-primary" type="submit" value="Find"/>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12 col-sm-12">
                                        <table id="resultat" class="table table-striped table-bordered"
                                               style="width:100%;">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>Query Code</th>
                                                <th>KPI Name</th>
                                                <th>Subject_area</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <% try {
                                                Class.forName("org.postgresql.Driver");
                                                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
                                                Statement stm = conn.createStatement();
                                                ResultSet rslt = stm.executeQuery("SELECT * FROM kpi");
                                                while (rslt.next()) { %>
                                            <tr>
                                                <td class="a-center">
                                                    <input type="checkbox" value="<%= rslt.getInt("id_kpi") %>" name="kpi" id="kpi" class="flat">
                                                </td>
                                                <td><%= rslt.getInt("id_kpi") %></td>
                                                <td><%= rslt.getString("name_kpi") %></td>
                                                <td><%= rslt.getString("subject_area") %></td>
                                            </tr>
                                            <% }
                                                rslt.close();
                                                conn.close();
                                            } catch (Exception e) {
                                            } %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </form>
                            <table id="resultat2" class="table table-striped table-bordered" style="width:100%">
                                <thead>
                                <tr role="row">
                                    <th>Date</th>
                                    <th>Query Code</th>
                                    <th>KPI Name</th>
                                    <th> Dimension Value</th>
                                    <th>KPI 1 Value</th>
                                    <th>KPI 2 Value</th>
                                    <th> GAP</th>
                                    <th> GAP %</th>
                                    <th> Number Records OK</th>
                                    <th> Number Records Not OK</th>
                                    <th> Database 1</th>
                                    <th> Database 2</th>
                                </tr>
                                </thead>
                            </table>


                        </div>
                    </div>


                </div>
            </div>
        </div>

    </div>
</div>


<!-- jQuery -->

<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>">
    document.addEventListener('DOMContentLoaded', function () {
        $('select[multiple]').multiselect();
        $('#langOpt').multiselect({
            columns: 1,
            placeholder: 'Select Languages'
        });
    });
</script>
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
<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>


<script data-config="">


    var filtersConfig = {
        base_path: 'tablefilter/',
        col_0: 'none',
        col_1: 'select',
        col_2: 'select',
        col_3: 'select',
        col_4: 'select',
        col_5: 'select',
        col_6: 'none',
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
    var tf = new TableFilter(document.getElementById('resultat'), filtersConfig);
    tf.destroy();
    tf.init();
    $().ready(function () {
        $('#resultat').DataTable({
            searching: false,
            lengthMenu: [5,10, 20, 50, 100, 200],
        });
    });

    $(document).ready(function() {
        $('#myForm').submit(function(event) {
            event.preventDefault(); // Prevent form submission
            callHistoricalDetailedVueDataTable(event.target);
        });
    });

    function callHistoricalDetailedVueDataTable(form) {
        var formData = new FormData(form);

        var selectedCheckboxes = $('input[name="kpi"]:checked').map(function() {
            return $(this).val();
        }).get();
        formData.append('kpi', selectedCheckboxes);
        var data = Object.fromEntries(formData);
        var url = '/HistoriqueVueDetailleDataTable';
        // Destroy existing DataTable instance
        if ($.fn.DataTable.isDataTable('#resultat2')) {
            $('#resultat2').DataTable().destroy();
        }
        $('#resultat2').DataTable({
            searching: false,
            lengthMenu: [5,10, 20, 50, 100, 200],
            retrieve: true,
            serverSide: true,
            processing: true,

            ajax: {
                url: url,
                type: "GET",
                dataType: "json",
                contentType: "application/json",
                data: function(d) {
                    d.kpi = data.kpi
                    d.dateDeb = data.dateDeb
                    d.dateFin = data.dateFin
                    d.page = $('#resultat2').DataTable().page.info().page;
                    d.size = $('#resultat2').DataTable().page.info().length;
                },
                error: function(error) {
                    console.log('Error:', error);
                }
            },
            columns: [
                { data: "date" },
                { data: "code_requete" },
                { data: "name_kpi" },
                { data: "val_kpi1" },
                { data: "val_kpi2" },
                { data: "val_dim" },
                { data: "gap_par_100" },
                { data: "nbreRecordNotOk" },
                { data: "nbreRecordOk" },
                { data: "val_dim" },
                { data: "database1" },
                { data: "database2" }
            ]
        });
    }




    function generateReportY() {
        document.forms['demo-form2'].action = 'resultat4';
        document.forms['demo-form2'].submit();

    }
</script>
</body>
</html>