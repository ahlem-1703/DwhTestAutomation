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
    <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">

    <!-- Datatables -->
    <link href="<c:url value="/resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>"
          rel="stylesheet">
    <link href="<c:url value="/resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"/>"
          rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
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
                    <h3>KPI</h3>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>KPIs List</h2>
                            <ul class="nav navbar-right panel_toolbox">
                               <!-- <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>-->
                                <li class="dropdown">
                                    <!--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                       aria-expanded="false"><i class="fa fa-wrench"></i></a>-->
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">

                                    </div>
                                </li>
                               <!-- <li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>

                        <div class="x_content">


                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card-box table-responsive">

                                        <table id="tableKpi" class="table table-striped table-bordered"
                                               style="width:100%">

                                            <thead>
                                            <tr>
                                                <th>Query Code</th>
                                                <th>KPI Name</th>
                                                <th>Subject Area</th>
                                                <th>Type</th>
                                                <th>Limit</th>
                                                <th> Update</th>
                                                <th> Add Query</th>
                                                <th> Delete KPI</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${listKpis}" var="emp">
                                                <tr>
                                                    <td>${emp.id_kpi}</td>
                                                    <td>${emp.name_kpi}</td>
                                                    <td>${emp.subject_area}</td>
                                                    <td>${emp.type}</td>
                                                    <td> ${emp.seuil}</td>
                                                    <td>

                                                        <a type="submit" href="<c:url value='/editkpi/${emp.id_kpi}' />"
                                                           class="btn btn-dark btn-xs">
                                                            <i class="fa fa-pencil"></i> </a>

                                                        <input type="text" value="${emp.id_kpi}" name="kpiId"
                                                               class="flat" hidden="true"></td>
                                                    <td style="align-items: center">
                                                        <a type="submit"
                                                           href="<c:url value='/addRequete/${emp.id_kpi}' />"
                                                           class="btn btn-success btn-xs">

                                                            <i class="fa fa-plus-square"></i> </a></td>
                                                    <td>
                                                        <a type="submit" href="#" class="btn btn-danger btn-xs" onclick="confirmDelete(${emp.id_kpi}); return false;">
                                                            <i class="fa fa-trash-o"></i>
                                                        </a>
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
          var tf = new TableFilter('tableKpi', filtersConfig);
          tf.init();
    $(document).ready(function () {
        $('#tableKpi').DataTable();
    });
</script>
<script>
    function confirmDelete(employeId) {
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
                window.location.href = `/deletekpi/` + employeId;
            } else {
                // Si l'utilisateur clique sur "Cancel" ou ferme la boîte de dialogue
                // Vous pouvez ajouter un code ici pour gérer l'annulation si nécessaire
            }
        });
    }

</script>
</body>
</html>
