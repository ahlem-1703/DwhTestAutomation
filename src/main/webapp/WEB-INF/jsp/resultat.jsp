<%@ page import="java.sql.*" %>
<%@ page import="com.Springboot.example.service.DbServiceImpl" %>

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
    <!-- NProgress -->
    <link src="<c:url value="/resources/vendors/nprogress/nprogress.css" />" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link src="<c:url value="/resources/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>"
          rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.css"/>" rel="stylesheet">
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
<style>
    .select-box {
        cursor: pointer;
        position: relative;
        max-width: 20em;
        margin: 5em auto;
        width: 100%;
    }

    .select,
    .label {
        color: #414141;
        display: block;
        font: 400 17px/2em 'Source Sans Pro', sans-serif;
    }

    .select {
        width: 100%;
        position: absolute;
        top: 0;
        padding: 5px 0;
        height: 40px;
        opacity: 0;
        -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
        background: none transparent;
        border: 0 none;
    }

    .select-box1 {
        background: #ececec;
    }

    h5 {
        text-align: left;
    }
</style>
<body class="nav-md">
<div class="container body">
    <%@include file="sideBarGlobal.jsp" %>

    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">

                <div id="a" class="alert alert-success alert-dismissible " hidden="true" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="title_left">
                    <h3>Manage Scripts</h3>
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

                <div class="col-md-12 col-sm-12 ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2><small></small></h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <!--<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>-->

                                <!--<li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <br/>
                            <form id="demo-form2" data-parsley-validate class="form-horizontal form-label-left"
                                  method="get" action="resultat">
                                <p style="color:red;">${message}</p>


                                <a href="deleteAll"> Delete Old Data </a>

                                <h3>${msg }</h3>


                                <div class="form-group row">
                                    <label class="control-label col-md-2 col-sm-2 label-align">Database 1 <span
                                            class="required"> =  </span>
                                    </label>

                                    <div class="col-md-3 col-sm-3 ">

                                        <select required class="browser-default custom-select" id="db1" name="db1">
                                            <% if ((request.getParameter("db1") != null) && (request.getParameter("db2") != null)) {
                                            %>
                                            <option><%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getId()%>
                                            </option>
                                            <%
                                                }
                                            %>
                                            <option value="">--- Select Database ----</option>
                                            <c:forEach items="${databaseList}" var="emp">
                                                <option value="${emp.id}">${emp.name} </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <label class="col-form-label col-md-2 col-sm-2 label-align">Database 2 <span
                                            class="required"> = </span>
                                    </label>
                                    <div class="col-md-3 col-sm-3 ">

                                        <select required class="browser-default custom-select" id="db2" name="db2">
                                            <% if ((request.getParameter("db1") != null) && (request.getParameter("db2") != null)) {
                                            %>
                                            <option><%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getId()%>
                                            </option>
                                            <%
                                                }
                                            %>
                                            <option value="">--- Select Database ----</option>
                                            <c:forEach items="${databaseList}" var="emp">
                                                <option value="${emp.id}">${emp.name} </option>
                                            </c:forEach>

                                        </select>

                                    </div>
                                    <button id="kpiSubmit" class="btn btn-primary" type="submit"> Load Kpi List</button>
                                </div>


                                <% if ((request.getParameter("db1") != null) && (request.getParameter("db2") != null)) {
                                %>
                                <table>
                                    <tr>
                                        <td>
                                            <div><h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Database
                                                1
                                                : <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%>
                                            </h5>
                                            </div>
                                        </td>

                                        <td>
                                            <div><h5> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                Database 2
                                                : <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%>
                                            </h5>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <div class="item form-group">

                                    <label class="col-form-label col-md-2 col-sm-2 label-align">Start Date<span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-3 col-sm-3 ">
                                        <input required class="form-control" type="date" id="DateDeb" name="DateDeb">
                                    </div>

                                    <label class="col-form-label col-md-2 col-sm-2 label-align"> End Date<span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-3 col-sm-3 ">
                                        <input required class="form-control" type="date" id="DateFin" name="DateFin">
                                    </div>
                                </div>


                                <div class="item form-group">

                                    <div class="col-md-12 col-sm-12 ">
                                        <table id="KpiListTable" name="KpiListTable"
                                               class="table table-striped table-bordered"
                                               style="width:100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>Query Code</th>
                                                <th>KPI Name</th>
                                                <th>Subject_area</th>
                                                <th>KPI Limit</th>
                                                <th>GAP Limit</th>
                                                <th>DataQuality Limit</th>


                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${KpisList}" var="u">
                                                <tr>
                                                    <td class="a-center ">
                                                        <input type="checkbox" value="${u.id_kpi}"
                                                               name="kpiii" id="kpiii" class="flat">
                                                    </td>
                                                    <td>${u.id_kpi}
                                                    </td>
                                                    <td>${u.name_kpi}
                                                    </td>
                                                    <td>${u.subject_area}
                                                    </td>
                                                    <td>${u.seuil}
                                                    </td>
                                                    <td>${u.subject_area}
                                                    </td>
                                                    <td>${u.seuil_dataQuality}
                                                    </td>


                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                                <div class="ln_solid"></div>
                                <div class="item form-group">
                                    <div class="col-md-6 col-sm-6 offset-md-3">
                                        <!--                        		 <button id="load12" class="btn btn-primary" type="button" name="load12"  onclick="generateReportX();"> Clean Test Data</button> -->
                                        <!--           			 		<button id="load12" class="btn btn-primary" type="button" name="load12"  onclick="generateReportH();"> Clean Vue Details Data</button>	 -->
                                        <!-- <button class="btn btn-primary" type="button" onclick="generateReportB();">Load
                                            DB 1
                                        </button>
                                        <button class="btn btn-primary" type="reset" onclick="generateReportA();">Load
                                            DB 2
                                        </button>-->


                                        <!-- <button class="btn btn-primary" type="reset" onclick="generateReportY();">Detailed View
                                            
                                        </button> -->
                                        <Center>
                                            <button id="load12" disabled="disabled" class="btn btn-primary"
                                                    type="button" name="load12"
                                                    onclick="generateReportO();"> Launch Test
                                            </button>
                                            <button type="button" class="btn btn-primary" data-toggle="modal" id="plan"
                                                    disabled
                                                    data-target="#scheduleModal"> Plan Task
                                            </button>

                                        </Center>
                                        <%@include file="./PlanTask/scheduleModal.jsp" %>
                                    </div>
                                </div>

                                <%--<label class="col-form-label col-md-2 col-sm-2 label-align"> Choose Task Date <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-3 col-sm-3 ">
                                    <input id="PlanTask" class="form-control" type="date" name="PlanTask">
                                </div>--%>
                                <div class="modal fade" id="exampleModalLong3" tabindex="-1" role="dialog"
                                     aria-labelledby="exampleModalLongTitle2" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLongTitle">Would You Please Mark
                                                    The KPIs For The
                                                    Test </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body" id="bodyModal3">

                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" class="btn btn-secondary">Previous</button>


                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </form>


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

<!-- NProgress -->
<script src="<c:url value="/resources/vendors/nprogress/nprogress.js"/>"></script>
<!-- morris.js -->
<script src="<c:url value="/resources/vendors/raphael/raphael.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/morris.js/morris.min.js"/>"></script>
<!-- gauge.js -->
<script src="<c:url value="/resources/vendors/gauge.js/dist/gauge.min.js"/>"></script>
<!-- bootstrap-progressbar -->
<script src="<c:url value="/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"/>"></script>
<!-- Skycons -->
<script src="<c:url value="/resources/vendors/skycons/skycons.js"/>"></script>
<!-- Flot -->
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.pie.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.time.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.stack.js"/>"></script>
<script src="<c:url value="/resources/vendors/Flot/jquery.flot.resize.js"/>"></script>
<!-- Flot plugins -->
<script src="<c:url value="/resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js"/>"></script>
<script src="<c:url value="/resources/vendors/flot-spline/js/jquery.flot.spline.min.js"/>"></script>
<script src="<c:url value="/resources/vendors/flot.curvedlines/curvedLines.js"/>"></script>
<!-- DateJS -->
<script src="<c:url value="/resources/vendors/DateJS/build/date.js"/>"></script>
<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>


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


<script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
<script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>

<!-- jQuery Tags Input -->
<script src="<c:url value="/resources/vendors/jquery.tagsinput/src/jquery.tagsinput.js"/>"></script>

<!-- Custom Theme Scripts -->

<!-- bootstrap-daterangepicker -->

<script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>
<script>
    function getSecondForm() {
        const selectDateDeb = document.getElementById('DateDeb');
        const selectDateFin = document.getElementById('DateFin');
        const selectKpi = document.getElementById('kpiii');
        const selectKpiListTable = document.getElementById('KpiListTable');
        const checkboxes = selectKpiListTable.querySelectorAll('input[type="checkbox"]');

// Flag to check if at least one checkbox is checked
        let atLeastOneChecked = false;

// Loop through the checkboxes and check if any of them is checked
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                atLeastOneChecked = true;
                // You can break the loop if you only want to know if at least one is checked
                // break;
            }

        });
        let getDateDeb;
        let getDateFin;
        let getKpi;
        if (atLeastOneChecked && selectDateFin.value && selectDateDeb.value) {
            getDateDeb = selectDateDeb.value;
            getDateFin = selectDateFin.value;
            getKpi = atLeastOneChecked;
            return getDateFin != null && getDateDeb != null && getKpi;
        }
        return false;
    }

    document.addEventListener('DOMContentLoaded', function () {
        handleTriggerTypeChange()
        // Disable submit button on page load
        document.getElementById('kpiSubmit').disabled = true;
        document.getElementById('demo-form2').addEventListener('input', function () {
            const select1 = document.getElementById('db1').value;
            const select2 = document.getElementById('db2').value;
            let firstForms = false;

            if (select1 !== '' && select2 !== '') {
                firstForms = true;

                document.getElementById('kpiSubmit').disabled = false;
            } else {
                document.getElementById('kpiSubmit').disabled = true;
            }
            if (firstForms) {
                if (getSecondForm()) {
                    document.getElementById("load12").disabled = false
                    document.getElementById("plan").disabled = false
                }

            }
        });


    //
    });

    // Enable submit button when form is valid

</script>
<script type="text/javascript">
    /*function disableUsedOptions($table) {
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
    });*/

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

    function generateReportO() {
        document.forms['demo-form2'].action = 'LoadAllData';
        document.forms['demo-form2'].submit();
        /* dateDeb = document.getElementById('DateDeb').value
        var dateFin = document.getElementById('DateFin').value
        var select1 = document.getElementById('db1').value;
        var select2 = document.getElementById('db2').value;
        var kpi = document.getElementById('kpiii').value;

        console.log(kpi)

        const formValues = {
                db1: select1,
                db2: select2,
                dateDeb,
                dateFin
        }

        if (select1 && select2 && dateDeb && dateFin) {
            localStorage.setItem("formValues", JSON.stringify(formValues))
        } */

    }
</script>

<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>

<script type="text/javascript">

    var filtersConfig = {
        base_path: 'tablefilter/',
        col_0: 'select',
        col_1: 'id_kpi',
        col_2: 'name_kpi',
        col_3: 'subject_area',
        col_4: 'seuil',
        col_5: 'seuil_gap',
        col_6: 'seuil_data_quality',
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
            '90px', '90px', '90px',
            '90px', '90px', '90px',
            '90px', '90px', '90px'
        ]
    };

    var tf = new TableFilter(document.getElementById('KpiListTable'), filtersConfig);
    tf.destroy();
    tf.init();
    $().ready(function () {
        $('#KpiListTable').DataTable({
            aLengthMenu: [5, 10, 20, 50, 100]
        });
    });

    function handleTriggerTypeChange() {
        const triggerType = document.getElementById('triggerType').value;
        console.log(triggerType);

        const dailySchedule = document.getElementById('dailySchedule');
        const monthlySchedule = document.getElementById('monthlySchedule');
        const weeklySchedule = document.getElementById('weeklySchedule');

        dailySchedule.style.display = triggerType === 'DAILY' ? 'block' : 'none';
        weeklySchedule.style.display = triggerType === 'WEEKLY' ? 'block' : 'none';
        monthlySchedule.style.display = triggerType === 'MONTHLY' ? 'block' : 'none';
    }

    function scheduleAPI() {

        var html = '';

        var form = document.getElementById('demo-form2'); // Replace 'yourFormId' with the actual ID of your form
        var formData = new FormData(form);
        for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }
        // Create a new URLSearchParams object
        var params = new URLSearchParams();

        // Add each form field to the URLSearchParams object
        formData.forEach((value, key) => {
            params.append(key, value);
        });

        // Get the query string from the URLSearchParams object
        var queryString = params.toString();

        // Append the query string to the URL
        var url = '/schedule?' + queryString;
        console.log(url)
        fetch(url)
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Error: ' + response.statusText);
                }
            })
            .then(function (data) {
                // Process the response data
                var modal = document.getElementById('scheduleModal');
                var modalInstance = new bootstrap.Modal(modal);
                modalInstance.hide();
            })
    }
</script>
</body>
</html>
