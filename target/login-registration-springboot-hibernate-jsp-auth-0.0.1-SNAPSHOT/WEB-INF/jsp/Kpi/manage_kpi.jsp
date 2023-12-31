<%@page import="com.Springboot.example.service.KpiServiceImp" %>
<%@page import="com.Springboot.example.repository.KpiRepository" %>
<%@page import="com.Springboot.example.model.Database" %>
<%@page import="com.Springboot.example.service.DbServiceImpl" %>
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
            color: #ff0000;
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
    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
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

    <!-- page content -->
    <div class="right_col" role="main">
        <div class="">
            <div class="page-title">
                <div class="title_left">
                    <h3>Manage KPIs</h3>
                </div>

                <div class="title_right">
                    <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                        <div class="input-group">
                            <!--<input type="text" class="form-control" placeholder="Search for...">
                            <span class="input-group-btn">
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
                            <h2>Add Kpi</h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                </li>

                                <!--<li><a class="close-link"><i class="fa fa-close"></i></a>
                                </li>-->
                            </ul>
                            <div class="clearfix">

                                <% if (request.getAttribute("message") != null) {
                                %>

                                <p style="color:red;"> ${message}</p>


                                <% } %>
                            </div>
                        </div>
                        <div id="wizard" class="form_wizard wizard_horizontal">
                            <ul class="wizard_steps anchor">
                                <li>
                                    <a href="#step-1" class="selected" isdone="1" rel="1">
                                        <span class="step_no">1</span>
                                        <span class="step_descr">
                                              Step 1<br>
                                              <small>Create KPI</small>
                                          </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#step-2" class="disabled" isdone="0" rel="2">
                                        <span class="step_no">2</span>
                                        <span class="step_descr">
                                              Step 2<br>
                                              <small>First Query</small>
                                          </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#step-3" class="disabled" isdone="0" rel="3">
                                        <span class="step_no">3</span>
                                        <span class="step_descr">
                                              Step 3<br>
                                              <small>Second Query</small>
                                          </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#step-4" class="disabled" isdone="0" rel="5">
                                        <span class="step_no">4</span>
                                        <span class="step_descr">
                                              Step 4<br>
                                              <small>Validation</small>
                                          </span>
                                    </a>
                                </li>
                            </ul>


                            <div class="stepContainer">
                                <div class="stepContainer content">
                                    <form id="formkpi" class="form-horizontal form-label-left" method="post"
                                          action="/Kpi/manage_kpi">
                                        <div id="step-1" class="content">


                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Father
                                                    <span class="required">*</span>

                                                </label>
                                                <spring:bind path="kpi.type">
                                                    <input type="radio" NAME="type" value="Father"
                                                           onclick="check1();" checked/>
                                                </spring:bind>
                                            </div>


                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's Id
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.id_kpi">
                                                        <input type="number" id="id_kpi" value="${kpi.id_kpi}"
                                                               name="id_kpi" required="required"
                                                               class="form-control" min="0" max="1000"/>
                                                    </spring:bind>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's
                                                    Name<span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.name_kpi">
                                                        <input type="text"
                                                               value="${kpi.name_kpi}" id="name_kpi" name="name_kpi"
                                                               required="required" class="form-control"/>
                                                    </spring:bind>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">KPI's
                                                    Subject Area <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.subject_area">
                                                        <input id="subject_area" name="subject_area"
                                                               value="${kpi.subject_area}" class="form-control"
                                                               required="required" type="text"/>
                                                    </spring:bind>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">KPI
                                                    Limit<span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.seuil">
                                                        <input id="seuil" class="form-control" name="seuil"
                                                               value="${kpi.seuil}" required="required"
                                                               type="number" min="0" max="100"/>
                                                    </spring:bind>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Acceptance
                                                    GAP Limit <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.seuil_gap">
                                                        <input id="seuil_gap" class="form-control" name="seuil_gap"
                                                               value="${kpi.seuil_gap}" required="required"
                                                               type="number" min="0" max="100"/>
                                                    </spring:bind>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Acceptance
                                                    DataQuality Limit<span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="kpi.seuil_dataQuality">
                                                        <input id="seuil_dataQuality" class="form-control"
                                                               name="seuil_dataQuality"
                                                               value="${kpi.seuil_dataQuality}" required="required"
                                                               type="number" max="100" min="0"/>
                                                    </spring:bind>
                                                </div>
                                            </div>

                                            <div class="col-md-12 col-sm-12">
                                                <div class="x_panel">
                                                    <div class="x_title">
                                                        <h2>SqlServer <small>Databases</small></h2>
                                                        <ul class="nav navbar-right panel_toolbox">
                                                            <li><a class="collapse-link"><i
                                                                    class="fa fa-chevron-up"></i></a>
                                                            </li>
                                                            <li class="dropdown">
                                                                <!--<a href="#" class="dropdown-toggle"
                                                                   data-toggle="dropdown" role="button"
                                                                   aria-expanded="false"><i
                                                                        class="fa fa-wrench"></i></a>-->
                                                                <div class="dropdown-menu"
                                                                     aria-labelledby="dropdownMenuButton">

                                                                </div>
                                                            </li>
                                                            <!--<li><a class="close-link"><i
                                                                    class="fa fa-close"></i></a>
                                                            </li>-->
                                                        </ul>
                                                        <div class="clearfix"></div>
                                                    </div>
                                                    <div class="x_content" style="display: block;">

                                                        <div class="">
                                                            <ul class="to_do">
                                                                <li>
                                                                    <p>

                                                                        <c:forEach items="${SqlServerList}"
                                                                                   var="emp">
                                                                            <label>
                                                                                <input type="checkbox"
                                                                                       value="${emp.id}"
                                                                                       name="conditions">
                                                                                    ${emp.name}
                                                                            </label>
                                                                        </c:forEach>

                                                                    </p>
                                                                </li>


                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-12">
                                                <div class="x_panel">
                                                    <div class="x_title">
                                                        <h2>Oracle <small>Databases</small></h2>
                                                        <ul class="nav navbar-right panel_toolbox">
                                                            <li><a class="collapse-link"><i
                                                                    class="fa fa-chevron-up"></i></a>
                                                            </li>
                                                            <li class="dropdown">
                                                               <!-- <a href="#" class="dropdown-toggle"
                                                                   data-toggle="dropdown" role="button"
                                                                   aria-expanded="false"><i
                                                                        class="fa fa-wrench"></i></a>-->
                                                                <div class="dropdown-menu"
                                                                     aria-labelledby="dropdownMenuButton">

                                                                </div>
                                                            </li>
                                                            <!--<li><a class="close-link"><i
                                                                    class="fa fa-close"></i></a>
                                                            </li>-->
                                                        </ul>
                                                        <div class="clearfix"></div>
                                                    </div>
                                                    <div class="x_content" style="display: block;">

                                                        <div class="">
                                                            <ul class="to_do">
                                                                <li>
                                                                    <p>

                                                                        <c:forEach items="${oracleList}" var="emp">
                                                                            <label>
                                                                                <input type="checkbox"
                                                                                       value="${emp.id}"
                                                                                       name="conditions">
                                                                                    ${emp.name}
                                                                            </label>
                                                                        </c:forEach>

                                                                    </p>
                                                                </li>


                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-12 col-sm-12">
                                                <div class="x_panel">
                                                    <div class="x_title">
                                                        <h2>Postgres <small>Databases</small></h2>
                                                        <ul class="nav navbar-right panel_toolbox">
                                                            <li><a class="collapse-link"><i
                                                                    class="fa fa-chevron-up"></i></a>
                                                            </li>
                                                            <li class="dropdown">
                                                                <!--<a href="#" class="dropdown-toggle"
                                                                   data-toggle="dropdown" role="button"
                                                                   aria-expanded="false"><i
                                                                        class="fa fa-wrench"></i></a>-->
                                                                <div class="dropdown-menu"
                                                                     aria-labelledby="dropdownMenuButton">

                                                                </div>
                                                            </li>
                                                            <!--<li><a class="close-link"><i
                                                                    class="fa fa-close"></i></a>
                                                            </li>-->
                                                        </ul>
                                                        <div class="clearfix"></div>
                                                    </div>
                                                    <div class="x_content" style="display: block;">

                                                        <div class="">
                                                            <ul class="to_do">
                                                                <li>
                                                                    <p>

                                                                        <c:forEach items="${postgresList}"
                                                                                   var="emp">


                                                                            <label>
                                                                                <input type="checkbox"
                                                                                       value="${emp.id}"
                                                                                       name="conditions">
                                                                                    ${emp.name}

                                                                            </label>
                                                                        </c:forEach>

                                                                    </p>
                                                                </li>


                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                        </div>


                                        <div id="step-2" class="content" style="display: none;">

                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Database<span
                                                        class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <select class="form-control form-control-sm" name="db[]"
                                                            id="Control">
                                                        <option value="${Control}"> ${Control}</option>
                                                    </select>

                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Date
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">

                                                    <spring:bind path="requete.date">
                                                        <input id="date" class="form-control" name="date"
                                                               value="${requete.date}" required="required"
                                                               type="text"/>
                                                    </spring:bind>
                                                </div>
                                            </div>


                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Val KPI
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-3 col-sm-12">
                                                    <spring:bind path="requete.val_kpi">
                                                        <input id="valKPI" class="form-control"
                                                               value="${requete.val_kpi}" name="val_kpi"
                                                               required="required" type="text"/>
                                                    </spring:bind>
                                                </div>
                                                As
                                                <div class="col-md-3 col-sm-12">
                                                    <spring:bind path="requete.val_kpi_alias">
                                                        <input id="val_kpi_alias" class="form-control"
                                                               value="${requete.val_kpi_alias}" name="val_kpi_alias"
                                                               required="required" type="text"/>
                                                    </spring:bind>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Body
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="requete.copr">
                                                            <textarea id="copr" name="copr" placeholder="From <selected database> where <condition>"
                                                                      required="required">${requete.copr}</textarea>
                                                    </spring:bind>
                                                </div>
                                            </div>

                                            <div class="field_wrapper">
                                                <div class="form-group row">
                                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Dimensions
                                                        <span class="required">*</span>
                                                    </label>

                                                    <div class="col-md-3 col-sm-12">
                                                        <div id="champsStep2"></div>
                                                        alias
                                                        <input class="form-control" type="text" id="ajouttStep2"
                                                               name="ajouttStep2" size="15"/>
                                                        <br>
                                                        <div class="input-group mb-2">
                                                            <span class="input-group-text"
                                                                  onclick="addFieldStep2(this)">+</span>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>

                                           <!-- <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Date_Image
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="requete.date_image">
                                                        <input id="DateImage" class="form-control" type="text"/>
                                                    </spring:bind>
                                                </div>
                                            </div>-->
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#exampleModalLong" onClick="submitTextStep2()">
                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                See Query
                                            </button>
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#exampleModalLong2" onClick="submitText1()">
                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                Execute Query
                                            </button>


                                            <div class="modal fade" id="exampleModalLong" tabindex="-1"
                                                 role="dialog" aria-labelledby="exampleModalLongTitle"
                                                 aria-hidden="true">
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
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-dismiss="modal">Return
                                                            </button>


                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="modal fade" id="exampleModalLong2" tabindex="-1"
                                                 role="dialog" aria-labelledby="exampleModalLong2"
                                                 aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Query</h5>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">Query<span
                                                                    class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">
          <textarea cols="20" rows="30" hidden="true" class="modal-body" id="bodyModal2" name="requetee">
       
          </textarea>

                                                            </div>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">Start
                                                                Date <span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">
                                                                <input type="date" name="dateDebStep2" id="dateDeb"
                                                                       class="form-control ">
                                                            </div>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">End
                                                                Date<span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">
                                                                <input type="date" id="dateFin" name="dateFinStep2"
                                                                       class="form-control">
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-primary"
                                                                    data-toggle="modal"
                                                                    data-target="#SeeResultQueryModalStep2"
                                                                    data-dismiss="modal"
                                                                    onClick="SeeResultQueryModalFunctionStep2()">
                                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                                See Result
                                                            </button>


                                                        </div>
                                                    </div>

                                                </div>

                                            </div>
                                            <%@include file="../SeeResultModalStep2.jsp" %>
                                        </div>


                                        <!-- Modal -->


                                        <div id="step-3" class="content" style="display: none;">
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Database
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <select name="db[]" id="select">
                                                        <option></option>
                                                    </select>

                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Date
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="requeteStep3.date">
                                                        <input id="date2" class="form-control" name="date2"
                                                               type="text"/>
                                                    </spring:bind>

                                                </div>
                                            </div>


                                            <div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Val KPI
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-3 col-sm-12">
                                                    <spring:bind path="requeteStep3.val_kpi">
                                                        <input id="valKPI2" class="form-control" name="valKPI2"
                                                               type="text"/>
                                                    </spring:bind>
                                                </div>
                                                As
                                                <div class="col-md-3 col-sm-12">
                                                    <spring:bind path="requeteStep3.val_kpi_alias">
                                                        <input id="valKPIAs2" class="form-control" name="valKPIAs2"
                                                               type="text"/>
                                                    </spring:bind>
                                                </div>
                                            </div>
                                            <div class="field item form-group bad">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Body
                                                    <span class="required">*</span>
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <spring:bind path="requeteStep3.copr">
                                                        <textarea id="copr2" name="copr2" placeholder="From <selected database> where <condition>"
                                                                  required="required"></textarea>
                                                    </spring:bind>
                                                </div>
                                            </div>


                                            <div class="field_wrapper">
                                                <div class="form-group row">
                                                    <label class="col-form-label col-md-3 col-sm-3 label-align">Dimensions
                                                        <span class="required">*</span>
                                                    </label>

                                                    <div class="col-md-3 col-sm-12">
                                                        <div id="champsStep3"></div>
                                                        alias
                                                        <input class="form-control" type="text" id="ajouttStep3"
                                                               name="ajouttStep3" size="15"/>
                                                        <br>

                                                        <div class="input-group mb-2">
                                                            <span class="input-group-text"
                                                                  onclick="addFieldStep3(this)">+</span>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>

                                            <!--<div class="form-group row">
                                                <label class="col-form-label col-md-3 col-sm-3 label-align">Date_Image
                                                </label>
                                                <div class="col-md-6 col-sm-6 ">
                                                    <input id="DateImage2" class="form-control" type="text"/>
                                                </div>
                                            </div>-->
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#exampleModalLong3" onClick="submitTextStep3()">
                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                See Query
                                            </button>
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#exampleModalLong4" onClick="submitText3()">
                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                Execute Query
                                            </button>
                                            <!--<button type="button" class="btn btn-primary" onClick="RemplirAlias()">
                                                Fill Alias
                                            </button>-->

                                            <div class="modal fade" id="exampleModalLong3" tabindex="-1"
                                                 role="dialog" aria-labelledby="exampleModalLongTitle2"
                                                 aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Query</h5>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body" id="bodyModal3">

                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-dismiss="modal">Return
                                                            </button>


                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="modal fade" id="exampleModalLong4" tabindex="-1"
                                                 role="dialog" aria-labelledby="exampleModalLong4"
                                                 aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Query </h5>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">Query
                                                                <span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">

                                                              <textarea cols="20" rows="30" class="modal-body" id="bodyModal4" name="requeteee" hidden="hidden">

                                                              </textarea>

                                                            </div>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">Start
                                                                Date<span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">
                                                                <input type="date" name="dateDebStep3" id="dateDebStep3"
                                                                       class="form-control ">
                                                            </div>
                                                        </div>
                                                        <div class="item form-group">
                                                            <label class="col-form-label col-md-3 col-sm-3 label-align">End
                                                                Date<span class="required">*</span>
                                                            </label>
                                                            <div class="col-md-6 col-sm-6 ">
                                                                <input type="date" id="dateFinStep3" name="dateFinStep3"
                                                                       class="form-control">
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-primary"
                                                                    data-toggle="modal"
                                                                    data-target="#SeeResultQueryModalStep3"
                                                                    data-dismiss="modal"
                                                                    onClick="SeeResultQueryModalFunctionStep3()">
                                                                <!-- here was an syntax error. you were calling method by uts name without () sign -->
                                                                See Result

                                                            </button>

                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <%@include file="../SeeResultModalStep3.jsp" %>
                                        </div>


                                        <div id="step-4" class="content" style="display: none;">
                                            <button type="submit" class="btn btn-success">Add</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /page content -->
                </div>
            </div>
        </div>

        <!-- footer content -->

        <!-- /footer content -->
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

<!-- jQuery Tags Input -->
<script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
<!-- iCheck 1.0.1 -->
<script src="https://almsaeedstudio.com/themes/AdminLTE/plugins/iCheck/icheck.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var maxField = 20; //Input fields increment limitation
        var addButton = $('.add_button'); //Add button selector
        var wrapper = $('.field_wrapper'); //Input field wrapper
        var fieldHTML = '<div class="form-group row"><div class="col-form-label col-md-3 col-sm-3 label-align"></div><div class="col-md-6 col-sm-6"><input type="text" name="field_name[]" value=""/>As <input type="text" name="field_name[]" value=""/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-square"/></a></div></div>'; //New input field html
        var x = 1; //Initial field counter is 1
        $(addButton).click(function () { //Once add button is clicked
            if (x < maxField) { //Check maximum number of input fields
                x++; //Increment field counter
                $(wrapper).append(fieldHTML); // Add field html
            }
        });

        $(wrapper).on('click', '.remove_button', function (e) { //Once remove button is clicked
            e.preventDefault();
            $(this).parent('div').remove();
            $(this).parent('label').remove(); //Remove field html
            x--; //Decrement field counter
        });
    });

    var $IncrementStep2 = 0;
    var $saisiStep2 = [];
    var $IncrementStep3 = 0;
    var $saisiStep3 = [];
    var index = 0;
    var numInputs = 0;

    function addFieldStep2() {
        var div = $("#champsStep2");


        div.append('<div class="input-group mb-2"><input class="form-control" type="text" name="ajoutStep2[]">'
            + '<div class="input-group-append"><span class="input-group-text" onclick="removeFieldStep2(this)">-</span></div></div>');
        $saisiStep2[$IncrementStep2] = $IncrementStep2;
        $IncrementStep2++;
    };

    function addFieldStep3() {
        var div = $("#champsStep3");


        div.append('<div class="input-group mb-2"><input class="form-control" type="text" name="ajoutStep3[]">'
            + '<div class="input-group-append"><span class="input-group-text" onclick="removeFieldStep3(this)">-</span></div></div>');
        $saisiStep3[$IncrementStep3] = $IncrementStep3;
        $IncrementStep3++;
    };

    function removeFieldStep2(minusElement) {
        minusElement.parentElement.parentElement.remove();
    }

    function removeFieldStep3(minusElement) {
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

    var $i = 0
    var $saisi2 = new Array();
    var $conds = $('input[name="conditions"]'),
        $ctrl = $('select[id="Control"]'), $ctrl1 = $('select[id="select"]'),
        $ctrl2 = $('select[id="Controlll"]');
    $conds.change(function () {
        if (this.checked) {
            $('<option />', {
                value: this.value,
                text: this.nextSibling.nodeValue
            }).appendTo($ctrl)
            $('<option />', {
                value: this.value,
                text: this.nextSibling.nodeValue
            }).appendTo($ctrl1)
            $('<option />', {
                value: this.value,
                text: this.nextSibling.nodeValue
            }).appendTo($ctrl2)
            $saisi2[$i] = this.nextSibling.nodeValue;
            $i = $i + 1;
        } else {
            $ctrl.find('option[value="' + this.value + '"]').remove();
            $ctrl1.find('option[value="' + this.value + '"]').remove();
            $ctrl2.find('option[value="' + this.value + '"]').remove();
        }

    });


    function submitTextStep2() {
        var i = 0;
        var $ds = "";
        var $dg = "";
        var notes = [];
        $('input[name="ajoutStep2[]"]').each(function () {
            notes.push($(this).val());
        });
        console.log(notes);
        if ($saisiStep2.length == 0) {
            $ds = " as " + document.getElementById('ajouttStep2').value;
        } else {
            for (let i = 0; i < notes.length; i++) {
                const hasNextNote = i < notes.length - 1;
                var $sg = notes[i] + " ";
                var $ss = notes[i] + " ";
                if (hasNextNote) {
                    $ds = $ds + $ss + "<font color='#FF0000'> ||'|'|| </font> ";
                    $dg = $dg + $sg + "<font color='#FF0000'> ||'|'|| </font> ";
                } else {
                    $ds = $ds + $ss;
                    $dg = $dg + $sg;
                }
            }
            ;
            $ds += " as " + document.getElementById('ajouttStep2').value;
        }

        var html = "<font color='#FF0000'>Select</font> " + $("#date").val() + " as Date,<br>" +
            $("#id_kpi").val() + " As code_requete,<br>" + $ds + ",<br>" +
            $("#valKPI").val() + " as " + $("#val_kpi_alias").val() + "<br>" +
            $("#copr").val() + "<br> And " +
            $("#date").val() + "<font color='#FF0000'> between</font><p class='bleu'> <font color='#008000'>DateDeb</font></p><font color='#FF0000'> and</font><p class='bleuu'> <font color='#008000'>DateFin</font> </p><br> " +
            "<font color='#FF0000'>group by</font> " + $("#date").val() + ",<br> " + $dg + "";
        ;

        $("#bodyModal").html(html);
    }

    function submitTextStep3() {
        var i = 0;
        var $ds = "";
        var $dg = "";
        var notes = [];
        $('input[name="ajoutStep3[]"]').each(function () {
            notes.push($(this).val());
        });
        console.log(notes);
        if ($saisiStep3.length == 0) {
            $ds = " as " + document.getElementById('ajouttStep3').value;
        } else {
            for (let i = 0; i < notes.length; i++) {
                const hasNextNote = i < notes.length - 1;
                var $sg = notes[i] + " ";
                var $ss = notes[i] + " ";
                if (hasNextNote) {
                    $ds = $ds + $ss + "<font color='#FF0000'> ||'|'|| </font> ";
                    $dg = $dg + $sg + "<font color='#FF0000'> ||'|'|| </font> ";
                } else {
                    $ds = $ds + $ss;
                    $dg = $dg + $sg;
                }
            }
            ;
            $ds += " as " + document.getElementById('ajouttStep3').value;
        }

        var html = "<font color='#FF0000'>Select</font> " + $("#date2").val()
            + " as Date <br>" + $("#id_kpi").val() + " As code_requete,<br>   "
            + $ds + ",<br>" +
            $("#valKPI2").val() + " as " + $("#valKPIAs2").val() + "<br>" +
            $("#copr2").val() + "<br> And " +
            $("#date2").val() + "<font color='#FF0000'> between</font><p class='bleu'> <font color='#008000'>DateDeb</font></p><font color='#FF0000'> and</font><p class='bleuu'> <font color='#008000'>DateFin</font> </p><br> " +
            "<font color='#FF0000'>group by</font> " + $("#date2").val() + ",<br> " + $dg + "";
        ;

        $("#bodyModal3").html(html);
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
        $("#bodyModal2").html(html);
    }

    function submitText2() {

        var i = 1;
        var $d = "";
        while (i < $saisi.length) {
            var $s = "<font color='#FF0000'>||'|'|| </font> " + document.getElementById('ajoute[' + i + ']').value + " as " + document.getElementById('ajoutte[' + i + ']').value + " ,<br> ";
            var $d = $d + $s + ' ';
            i++;

        }
        ;
        var html = "<font color='#FF0000'>Select</font> " + $("#date2").val() + " as Date <br>" + $("#id_kpi").val() + " As code_requete,<br>   " + $("#ajoute").val() + " as " + $("#ajoutte").val() + ",<br>" + $d + $("#valKPI2").val() + " as " + $("#valKPIAs2").val() + "<br>   " + $("#copr2").val() + "<br> And " + $("#date2").val() + "<font color='#FF0000'> between</font><p class='bleu'> <font color='#008000'>DateDeb</font></p><font color='#FF0000'> and</font><p class='bleuu'> <font color='#008000'>DateFin</font> </p><br> <font color='#FF0000'>group by</font> " + $("#date2").val() + ",<br> " + $("#ajoute").val() + " as " + $("#ajoutte").val() + ",<br>" + $d + "";
        $("#bodyModal3").html(html);
    }

    function submitText3() {

        var i = 1;
        var $d = "";
        while (i < $saisi.length) {
            var $s = "||'|'|| " + document.getElementById('ajoute[' + i + ']').value + " /* as " + document.getElementById('ajoutte[' + i + ']').value + " */ ";
            var $d = $d + $s + ' ';
            i++;

        }
        ;
        var html = "Select " + $("#date2").val() + " as Date,  " + $("#id_kpi").val() + " as Code_requete, " + $("#ajoute").val() + " /*  as " + $("#ajoutte").val() + " */ " + $d + " as val_dim ," + $("#valKPI2").val() + " as " + $("#valKPIAs2").val() + " " + $("#copr2").val() + " and " + $("#date2").val() + "between DateDeb and DateFin group by " + $("#date2").val() + "," + $("#ajoute").val() + " /* as " + $("#ajoutte").val() + " */ " + $d + "";
        $("#bodyModal4").html(html);
    }

    function RemplirAlias() {


        $('#valKPIAs2').val($('#valKPIAs').val());
        $('#ajoutte').val($('#ajoutt').val());

        var i = 1;
        while (i < $saisi.length) {

            document.getElementById('ajoutte[' + i + ']').value = document.getElementById('ajoutt[' + i + ']').value;

            i++;
        }
        ;
    }

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

    function populateCustomerId() {
        var selectBox = document.getElementById('selectBox');
        var selectedCustomerId = selectBox.options[selectBox.selectedIndex].value.split(',');
        document.getElementById('id_kpi').value = selectedCustomerId[1];
        document.getElementById('name_kpi').value = selectedCustomerId[0];
        document.getElementById('subject_area').value = selectedCustomerId[2];
        document.getElementById('seuil').value = selectedCustomerId[3];

    }

    function check() {

        document.getElementById("div1").style.display = 'block';

    }

    function check1() {

        document.getElementById("div1").style.display = 'none';
        document.getElementById('id_kpi').value = '';
        document.getElementById('name_kpi').value = '';
        document.getElementById('subject_area').value = '';
        document.getElementById('seuil').value = '';
    }

    function testerStep2() {
        document.forms['formkpi'].action = 'LaunchScriptTestResultStep2';
        document.forms['formkpi'].submit();

    }

    function testerStep3() {
        document.forms['formkpi'].action = 'LaunchScriptTestResultStep3';
        document.forms['formkpi'].submit();

    }

    function desactive() {
        document.getElementById('date3').disabled = false;

        document.getElementById('valKPI3').disabled = false;
        document.getElementById('valKPIAs3').disabled = false;
        document.getElementById('copr3').disabled = false;
        document.getElementById('ajoutee').disabled = false;
        document.getElementById('ajouttee').disabled = false;
        document.getElementById('DateImage3').disabled = false;

        var i = 1;
        while (i < $saisi.length) {
            document.getElementById('ajoutee[' + i + ']').disabled = false;
            document.getElementById('ajouttee[' + i + ']').disabled = false;
            i++;
        }
        ;
    }

    function renderHtml(columns, rows) {
        var html = `<div class="modal-header">
                                                                <h5 class="modal-title">Only the first 10 rows</h5>
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