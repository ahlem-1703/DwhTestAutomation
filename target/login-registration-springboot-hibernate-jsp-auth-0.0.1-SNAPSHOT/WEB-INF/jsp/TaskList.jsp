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
    <%@include file="./sideBarGlobal.jsp" %>

    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Tasks List</h3>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">

                        <div class="x_content">


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card-box table-responsive">

                                        <table id="taskListTable" class="table table-striped table-bordered"
                                               style="width:100%">

                                            <thead>
                                            <tr>
                                                <th>Schedule Name</th>
                                                <th>Trigger Type</th>
                                                <th>Creation Date</th>
                                                <th>End Date</th>

                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${listTasks}" var="task">
                                                <tr>
                                                    <td data-toggle="modal"
                                                        data-target="#SeeDetailedExecutions"
                                                        data-dismiss="modal"
                                                        onClick="SeeDetailedExecutionsFunction(${task.id})"onmouseover="this.style.color='blue'; this.style.cursor='pointer'"
                                                        onmouseout="this.style.color='black'; this.style.cursor='default'" >${task.scheduleName}</td>
                                                    <td>${task.triggerType}</td>
                                                    <td>${task.creationDate}</td>
                                                    <td>${task.endScheduler}</td>

                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        <%@include file="./SeeDetailedExecutions.jsp" %>
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


<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>
<script type="text/javascript">
    var filtersConfig = {
        base_path: 'tablefilter/',
        col_0:'Query Code',
        col_1:'KPI Name',
        col_2:'Subject Area',
        col_3: 'Type',
        col_4: 'Limit',
        col_5: 'none',
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
        col_widths:[
            '125px', '90px', '100px',
            '90px', '90px', '90px',
            '90px', '90px', '90px'
        ]
    };
    var tf = new TableFilter('taskListTable', filtersConfig);
    tf.init();
    $(document).ready(function () {
        $('#taskListTable').DataTable();
    });
</script>
</body>
</html>
