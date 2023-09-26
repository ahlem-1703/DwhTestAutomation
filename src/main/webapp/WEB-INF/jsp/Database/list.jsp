<%@ page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

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

    <!-- Bootstrap -->
    <link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
    <!-- Datatables -->

    <link href="<c:url value="/resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>"
          rel="stylesheet">
    <link href="<c:url value="/resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"/>"
          rel="stylesheet">

    <link href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    <style>
        /* Styles CSS pour réduire la taille des composants SweetAlert */

        .custom-icon-class {
            font-size: 10px; /* Taille de police personnalisée pour l'icône */
        }

        .custom-title-class {
            font-size: 15px; /* Taille de police personnalisée pour le titre */
        }



        .custom-actions-class {
            font-size: 13px; /* Taille de police personnalisée pour les boutons */
        }

        .custom-confirm-button-class {
            font-size: 13px; /* Taille de police personnalisée pour le bouton de confirmation */
        }

        .custom-cancel-button-class {
            font-size: 13px; /* Taille de police personnalisée pour le bouton "Annuler" */
        }
    </style>

</head>

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
                            <h2>Databases List</h2>
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

                        <div class="x_content" style="display: block;">


                            <div class="table-responsive">
                                <span class="ingredient"><h4>${message}</h4></span>
                                <table class="table table-striped jambo_table bulk_action">
                                    <thead>
                                    <tr class="headings">


                                        <th class="column-title">Database Name</th>
                                        <th class="column-title">Username</th>
                                        <th class="column-title">Password</th>
                                        <th class="column-title">Port</th>

                                        <th class="column-title">System</th>


                                        <th class="column-title no-link last"><span class="nobr">Delete Database</span>
                                        <th class="column-title"> Verify Connection</th>

                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty DataBases}">
                                            <c:forEach items="${DataBases}" var="emp">
                                                <tr>
                                                    <td>${emp.name}</td>
                                                    <td>${emp.username}</td>
                                                    <td>${emp.password}</td>
                                                    <td>${emp.port}</td>

                                                    <td>${emp.system}</td>


                                                    <td>
                                                        <a type="submit" href="<c:url value='/delete/${emp.id}' />"
                                                           class="btn btn-danger btn-xs">
                                                            <i class="fa fa-trash-o"></i>
                                                        </a>

                                                    </td>
                                                    <td>
                                                        <a type="submit" href="<c:url value='/verifyConnexion/${emp.id}' />"
                                                           class="btn btn-success btn-xs">
                                                            <i class="fa fa-check-circle-o"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach items="${empList}" var="emp">
                                                <tr>
                                                    <td>${emp.name}</td>
                                                    <td>${emp.username}</td>
                                                    <td>${emp.password}</td>
                                                    <td>${emp.port}</td>

                                                    <td>${emp.system}</td>


                                                    <td>
                                                        <a type="submit" href="#" class="btn btn-danger btn-xs" onclick="confirmDelete(${emp.id}); return false;">
                                                            <i class="fa fa-trash-o"></i>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a type="submit" href="<c:url value='/verifyConnexion/${emp.id}' />"
                                                           class="btn btn-success btn-xs">
                                                            <i class="fa fa-check-circle-o"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>


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
<!-- /page content -->

<!-- footer content -->
<footer>
    <div class="pull-right">

    </div>
    <div class="clearfix"></div>
</footer>
<!-- /footer content -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<!-- Bootstrap -->
<script src="../static/vendors/jquery/dist/jquery.min.js"></script>
<script src="../vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
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

<!-- Custom Theme Scripts -->

<script src="../build/js/custom.min.js"></script>
<script>
    function confirmDelete(employeeId) {
        Swal.fire({
            title: 'Are you sure ?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, Delete !',
            cancelButtonText: 'Cancel',
            customClass: {
                icon: 'custom-icon-class', // Classe CSS personnalisée pour l'icône
                title: 'custom-title-class', // Classe CSS personnalisée pour le titre
                actions: 'custom-actions-class', // Classe CSS personnalisée pour les boutons
                confirmButton: 'custom-confirm-button-class', // Classe CSS personnalisée pour le bouton de confirmation
                cancelButton: 'custom-cancel-button-class' // Classe CSS personnalisée pour le bouton "Annuler"
            },
            width: '280px', // Largeur de la boîte de dialogue
            maxHeight: '100px' // Hauteur maximale de la boîte de dialogue
        }).then((result) => {
            if (result.isConfirmed) {
                // Si l'utilisateur clique sur "Yes, Delete !"
                window.location.href = `/delete/` + employeeId;
            } else {
                // Si l'utilisateur clique sur "Cancel" ou ferme la boîte de dialogue
                // Vous pouvez ajouter un code ici pour gérer l'annulation si nécessaire
            }
        });
    }

</script>



</body>
</html>
