<%@ page import="com.Springboot.example.service.DbServiceImpl" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">

</head>

<body class="nav-md">

<div class="container body">

    <%@include file="./SpecificSideBar.jsp" %>
    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
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
                <div class="col-md-12 col-sm-12  ">
                    <div class="x_panel">
                        <div class="x_title">
                            <h2>Global View</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <h2>Download Data File</h2>
                            <form id="demo-form2" data-parsley-validate="" method="get" action="Vue_detaille"
                                  class="form-horizontal form-label-left">

                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="card-box table-responsive">

                                            <table id="datatable-buttons" class="table table-striped table-bordered"
                                                   style="width:100%">
                                                <thead>
                                                <tr role="row">
                                                    <th>KPI Name</th>
                                                    <th>KPI Limit</th>
                                                    <th>GAP Limit</th>
                                                    <th>Data Quality Limit</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${rslt}" var="u">

                                                    <tr>
                                                        <td>${u.getName_kpi()}</td>
                                                        <td>${u.getSeuil()}</td>
                                                        <td>${u.getSeuil_gap()}</td>
                                                        <td>${u.getSeuil_dataQuality()}</td>
                                                    </tr>

                                                </c:forEach>
                                                </tbody>
                                            </table>


                                            <table id="resultat2" class="table table-bordered" style="width:100%">
                                                <thead>
                                                <tr role="row">
                                                    <th></th>
                                                    <th>Start Date</th>
                                                    <th>End Date</th>
                                                    <%--     <th>Date</th>--%>
                                                    <th>Query Code</th>
                                                    <th>KPI Name</th>
                                                    <th>Total Kpi
                                                        in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%>
                                                    </th>
                                                    <th>Total Kpi
                                                        in <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%>
                                                    </th>
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
                                                    <th> Execution Time</th>
                                                    <th> Execution Date</th>

                                                </tr>
                                                </thead>


                                                <tbody>
                                                <c:forEach items="${rslt}" var="u">

                                                    <tr>
                                                        <td class="a-center ">
                                                            <input type="checkbox" value="${u.getId()}" name="kpiss"
                                                                   class="flat">

                                                        </td>
                                                        <td>${u.getDateDeb()}</td>
                                                        <td>${u.getDateFin()}</td>
                                                            <%--  <td>${u.getDate()}</td>--%>
                                                        <td>${u.getCode_requete()}</td>
                                                        <td>${u. getName_kpi()}</td>
                                                        <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                              value="${u.getVal_kpi1()}"/></td>
                                                        <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                              value="${u.getVal_kpi2()}"/></td>
                                                            <%--                        	 <td>${u.getVal_kpi1()}</td> --%>
                                                            <%--                          <td>${u.getVal_kpi2()}</td> --%>
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
                                    </div>


                            </form>


                        </div>
                        <div id="customToastContainer"></div>
                        <div class="ln_solid"></div>
                        <div class="item form-group">
                            <div class="col-md-6 col-sm-6 offset-md-3">
                                <Center>
                                    <button id="openMailToggle" class="btn btn-primary" type="submit"
                                            data-toggle="modal"
                                            data-target="#exampleModalLong" > Send Email
                                    </button>
                                </Center>
                                <!-- <button class="btn btn-primary" type="button" onclick="generateReportB();" >Load DB 1</button>
                                           <button class="btn btn-primary" type="reset"  onclick="generateReportA();" >Load DB 2</button>
                                                  <button class="btn btn-primary" type="reset"  onclick="generateReportY();" >Detailed View</button>  -->
                            </div>
                        </div>
                        <div class="modal fade" id="exampleModalLong" tabindex="-1"
                             role="dialog" aria-labelledby="exampleModalLongTitle"
                             aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Select Email</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" id="bodyModal">
                                        <table id="resultat" class="table table-striped table-bordered"
                                               style="width:100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>Email</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="a-center ">
                                                    <input type="checkbox" value="mohamedzied.sahnoun@wevioo.com"
                                                           name="email" id="email1" class="flat">
                                                </td>
                                                <td>mz.sahnoun@gmail.com
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="a-center ">
                                                    <input type="checkbox" value="houcemsalem@gmail.com"
                                                           name="email" id="email2" class="flat">
                                                </td>
                                                <td>ahlem.kthiri@esprit.tn
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="a-center ">
                                                    <input type="checkbox" value="houcemsalem@gmail.com"
                                                           name="email" id="email3" class="flat">
                                                </td>
                                                <td>houcemsalem@gmail.com
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="modal-footer">
                                        <button id="sendMail" class="btn btn-primary" type="submit"
                                                onclick="sendMail()">
                                            send
                                        </button>
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">Return
                                        </button>
                                    </div>
                                </div>

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
    <script src="<c:url value="/resources/js/tablefilter.js"/>"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            localStorage.setItem("globalVueUrl", window.location.href)
            localStorage.setItem("detailVueUrl", window.location.href.replace('LoadAllData', 'Vue_detaille'))
        });

        function goToDetailVue() {
            const detailVueUrl = localStorage.getItem('detailVueUrl')
            window.location.href = detailVueUrl
        }
        function goToGlobalVue() {
            const globalVue = localStorage.getItem('globalVueUrl');
            window.location.href = globalVue;
        }


    </script>


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
        var tf = new TableFilter('resultat2', filtersConfig);
        tf.init();

        $(document).ready(function () {
            $('#resultat2').DataTable();
        });

        function sendMail(rslt) {
            var selectedEmails = [];
            var checkboxes = document.querySelectorAll('input[name="email"]:checked');
            checkboxes.forEach(function(checkbox) {
                selectedEmails.push(checkbox.value);
            });
            <%
            String[] kpiiiValues = request.getParameterValues("kpiii");
            StringBuilder urlBuilder = new StringBuilder("/sendResultMail?");
            urlBuilder
            .append("&DateDeb=")
            .append(request.getParameter("DateDeb"))
            .append("&DateFin=")
            .append(request.getParameter("DateFin"));

            if (kpiiiValues != null) {
                for (String kpiii : kpiiiValues) {
                    urlBuilder.append("&kpiii=").append(kpiii);
                }
            }

            String url = urlBuilder.toString();
            %>
            var url = '<%= url %>';

            if (selectedEmails.length > 0) {
                selectedEmails.forEach(function(email) {
                    url += "&email=" + email;
                });
            }
            console.log(url)
            const httpRequest = new XMLHttpRequest();
            httpRequest.open("POST", url, true);
            httpRequest.onreadystatechange = function() {
                if (httpRequest.readyState === 4) {
                    $('#exampleModalLong').modal('hide');
                    if (httpRequest.status === 200) {
                        var message = httpRequest.responseText;
                        // Afficher le message de succès à l'utilisateur
                        createToast(message,true);
                    } else {
                        // Afficher le message d'erreur à l'utilisateur
                        var response = JSON.parse(httpRequest.responseText);
                        var message = response.message;
                        createToast(message,false)
                    }
                }
            };
            httpRequest.send();
        }
        function createToast(message,response){
            // Create the toast element
            var toast = document.createElement("div");
            response ? toast.classList.add("custom-toast-success"):toast.classList.add("custom-toast-error");
            toast.innerText = message;

            // Add the toast to the container
            var toastContainer = document.getElementById("customToastContainer");
            toastContainer.appendChild(toast);

            // Show the toast
            setTimeout(function() {
                toast.classList.add("show");
            }, 100);

            // Hide and remove the toast after a certain duration (e.g., 3 seconds)
            setTimeout(function() {
                toast.classList.remove("show");
                setTimeout(function() {
                    toastContainer.removeChild(toast);
                }, 300);
            }, 3000);
        }
    </script>
</body>
</html>