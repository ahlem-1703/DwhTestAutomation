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
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Update Query</h3>
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
                            <h2>Edit Query</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <!--<li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                        <div class="x_content">
                            <form:form id="formkpi" modelAttribute="requete" class="form-horizontal form-label-left"
                                       method="post"
                                       action="/updatee">


                                <form:hidden path="id" id="id" class="form-control" name="id" required="required"/>
                                <form:hidden path="code_requete" id="code_requete" class="form-control"
                                             name="code_requete" required="required"/>


                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Database <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">
                                        <form:input path="id_databasee" id="id_databasee" readonly="true"
                                                    class="form-control" name="id_databasee"
                                                    required="required" type="text"/>
                                            <%--<form:hidden path="id_databasee"></form:hidden>--%>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Query Code <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">
                                        <form:input path="kpi" id="id_kpi" class="form-control" name="id_kpi"
                                                    type="text" readonly="true"/>
                                            <%--<form:hidden path="kpi"></form:hidden>--%>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Date <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">


                                        <form:input path="date" id="date" class="form-control" name="date"
                                                    required="required" type="text"/>

                                    </div>
                                </div>


                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Val KPI <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-3 col-sm-12">

                                        <form:input path="val_kpi" id="valKPI" class="form-control" name="valKPI"
                                                    required="required" type="text"/>

                                    </div>
                                    As
                                    <div class="col-md-3 col-sm-12">

                                        <form:input path="val_kpi_alias" id="val_kpi_alias" class="form-control"
                                                    name="valKPIAs" required="required" type="text"/>

                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">COPR <span
                                            class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <form:textarea path="copr" id="copr" name="copr" col="40" rows="4"
                                                       oninput="updateTextareaHeight(this);"
                                                       required="required"></form:textarea>

                                    </div>
                                </div>

                                <div class="field_wrapper">

                                    <div class="form-group row">
                                        <label class="col-form-label col-md-3 col-sm-3 label-align">Dimension <span
                                                class="required">*</span>
                                        </label>

                                        <div class="col-md-8 col-sm-12">
                                            <c:forEach items="${requete.dims}" var="d">

                                                <input class="form-control" value="${d.val_dim}" type="text" id="ajout"
                                                       name="ajout" size="15"/> as
                                                <input class="form-control" type="text" value="${d.val_dim_alias}"
                                                       id="ajoutt" name="ajoutt" size="15"/>

                                                <input class="form-control" value="${d.id_dim}" type="text" id="ids"
                                                       name="ids" size="15" hidden="hidden"/>

                                            </c:forEach>
                                            <div id="champs"><br></div>
                                            <c:if test="${requete.kpi.type == 'Father'}">
                                                <div class="input-group mb-2">
                                                    <span class="input-group-text" onclick="addField(this)">+</span>
                                                </div>
                                            </c:if>
                                        </div>

                                    </div>


                                </div>


                                <!--<div class="form-group row">
                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Date_Image
                                    </label>
                                    <div class="col-md-6 col-sm-6 ">

                                        <input id="DateImage" class="form-control" type="text"/>

                                    </div>
                                </div>-->
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#exampleModalLong" onClick="submitText()">
                                    <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                    See Query
                                </button>
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#exampleModalLong2" onClick="submitText1()">
                                    <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                    Execute Query
                                </button>

                                <button type="submit" class="btn btn-primary">
                                    Save
                                </button>

                                <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog"
                                     aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Query </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body" id="bodyModal">

                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                    Return
                                                </button>


                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="modal fade" id="exampleModalLong2" tabindex="-1" role="dialog"
                                     aria-labelledby="exampleModalLong2" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLongTitle">Query </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Query <span
                                                        class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <textarea cols="20" rows="30" hidden="true" class="modal-body"
                                                              id="bodyModal2" name="requetee">

                                                    </textarea>

                                                </div>
                                            </div>
                                            <div class="item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Start
                                                    Date<span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <input type="date" name="dateDeb" id="dateDeb"
                                                           class="form-control ">
                                                </div>
                                            </div>
                                            <div class="item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">End
                                                    Date<span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <input type="date" id="dateFin" name="dateFin" class="form-control">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-primary"
                                                        data-toggle="modal"
                                                        data-target="#SeeResultQueryModal"
                                                        data-dismiss="modal"
                                                        onClick="SeeResultQueryModalFunction()">
                                                    <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                    See Result
                                                </button>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%@include file="../SeeResultModalEditRequete.jsp" %>
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


<script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
<script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
<!-- jQuery Smart Wizard -->

<script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
<script type="text/javascript">
    var $Increment = 0;
    var $saisi = [];
    var index = 0;
    var numInputs = 0;

    function addField() {
        var div = $("#champs");


        div.append('<div class="input-group mb-2"><input class="form-control" type="text" name="ajout[]">'
            + '<div class="input-group-append"><span class="input-group-text" onclick="removeField(this)">-</span></div></div>');
        $saisi[$Increment] = $Increment;
        $Increment++;
    };

    function removeField(minusElement) {
        minusElement.parentElement.parentElement.remove();
    }

    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green'
    });

    function submitText() {
        var i = 0;
        var $ds = "";
        var $dg = "";
        var notes = [];
        $('input[name="ajout[]"]').each(function () {
            notes.push($(this).val());
        });
        console.log(notes);
        if ($saisi.length == 0) {
            $ds = " as " + document.getElementById('ajoutt').value;
        } else {
            for (let note of notes) {

                var $sg = "<font color='#FF0000'> ||'|'|| </font> " + note;
                var $ss = "<font color='#FF0000'> ||'|'|| </font> " + note;
                $ds = $ds + $ss + ' ';
                $dg = $dg + $sg + ' ';
                i++;
            }
            ;
            $ds += " as " + document.getElementById('ajoutt').value;
        }
        console.log($saisi)

        var html = "<font color='#FF0000'>Select</font> " + $("#date").val() + " as Date,<br>" +
            $("#id_kpi").val() + " As code_requete,<br>" +
            document.getElementById('ajout').value + $ds + ",<br>" +
            $("#valKPI").val() + " as " + $("#val_kpi_alias").val() + "<br>" +
            $("#copr").val() + "<br> And " +
            $("#date").val() + "<font color='#FF0000'> between</font><p class='bleu'> <font color='#008000'>DateDeb</font></p><font color='#FF0000'> and</font><p class='bleuu'> <font color='#008000'>DateFin</font> </p><br> " +
            "<font color='#FF0000'>group by</font> " + $("#date").val() + ",<br> " + $("#ajout").val() + $dg + "";
        ;

        $("#bodyModal").html(html);
    }


    function submitText1() {

        var i = 1;
        var $d = "";

        while (i < $saisi.length) {
            var $s = "+'|'+ " + document.getElementById('ajout[' + i + ']').value + " /* as " + document.getElementById('ajoutt[' + i + ']').value + " */ ";
            var $d = $d + $s + ' ';
            i++;

        }
        ;
        var html = "Select " + $("#date").val() + " as Date,  " + $("#id_kpi").val() + " as Code_requete, " + $("#ajout").val() + " /*  as " + $("#ajoutt").val() + " */ " + $d + " as val_dim ," + $("#valKPI").val() + " as " + $("#valKPIAs").val() + " " + $("#copr").val() + " and " + $("#date").val() + " between DateDeb and DateFin group by " + $("#date").val() + "," + $("#ajout").val() + " /* as " + $("#ajoutt").val() + " */ " + $d + "";
        $("#bodyModal").html(html);
    }

    function renderHtml(columns, rows) {
        var html = `<div class="modal-header">
                                                                <h5 class="modal-title">Only the fist 10 rows</h5>
                                                                <button type="button" class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
<table class="table table-striped table-bordered">
    <thead>
        <tr>`;
        columns.forEach(function (column) {
            html += `<th>` + column + `</th>`;
        });
        html += `</tr>
    </thead>
    <tbody>`;
        rows.forEach(function (row) {
            html += `<tr>`;
            columns.forEach(function (column) {
                html += `<td>` + row[column] + `</td>`;
            });
            html += `</tr>`;
        });
        html += `</tbody>
</table>`;
        return html;
    }


</script>
</body>
</html>