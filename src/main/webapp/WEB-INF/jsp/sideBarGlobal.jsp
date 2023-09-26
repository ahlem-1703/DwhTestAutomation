
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
                    <h2>${pageContext.request.userPrincipal.name}</h2>
                </div>
            </div>
            <!-- /menu profile quick info -->

            <br/>

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                <div class="menu_section">
                    <h3>General</h3>
                    <ul class="nav side-menu " class="toggled">

                        <li>
                            <a><i class="fa fa-line-chart"></i>Manage KPIs/Queries
                                <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu" style="display: block;">
                                <li><a href="/listRequetes">Queries List</a>
                                </li>
                                <li><a href="/listKpis">KPIs List </a>
                                <li><a href="/manage_kpi">Add KPIs & Queries</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a><i class="fa fa-database"></i>Manage Connection
                                <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu" style="display: block;">

                                <li><a href="/manage_connexion">Add Database</a>
                                </li>
                                <li><a href="/list">Databases List</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a><i class="fa fa-dashboard"></i>Realtime Tests
                                <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu" style="display: block;">
                                <li><a href="/resultat">Launch Script</a>

                            </ul>
                        </li>
                        <li>
                            <a><i class="fa fa-history"></i>Consultation
                                <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu" style="display: block;">
                                <li><a href="/HistoriqueVueGlobale">Global View</a>
                                </li>
                                <li><a href="/HistoriqueVueDetaille">Detailed View</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a><i class="fa fa-calendar"></i>Scheduled Tests
                                <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu" style="display: block;">
                                <li><a href="/tasksList">Tasks List</a>
                                </li>
                            </ul>

                        </li>
                    </ul>
                </div>

            </div>

        </div>
        <!-- /sidebar menu -->


    </div>
</div>
<!-- /sideBar -->
<%@include file="./TopNavigation.jsp" %>

<script type="text/javascript">
    function goToDetailVue() {
        const detailVueUrl = localStorage.getItem('detailVueUrl')
        window.location.href = detailVueUrl
    }
</script>
</body>
</html>
