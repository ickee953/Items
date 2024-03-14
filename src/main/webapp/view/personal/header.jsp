<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>
  </title>
  <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
  <!-- CSS Files -->
  <link href="<c:url value='/assets/css/material-dashboard.css?v=2.1.2'/>" rel="stylesheet" />
  <link href="<c:url value='/assets/css/material-icons.css'/>" rel="stylesheet" />
  <script src="<c:url value='/assets/js/core/jquery.min.js'/>"></script>
  <!-- JCrop plugin -->
  <link href="<c:url value='/assets/css/jcrop.css'/>" rel="stylesheet">
  <script src="<c:url value='/assets/js/plugins/jcrop.js'/>"></script>
  <!-- Custom CSS Styles -->
  <link href="<c:url value='/assets/css/custom-style.css'/>" rel="stylesheet" />
</head>

<body class="">
  <div class="wrapper ">
    <div class="sidebar" data-color="purple" data-background-color="white">
      <div class="sidebar-wrapper">
        <ul class="nav">
          <c:choose>
            <c:when test="${menu_selected_num == 0}">
              <li class="nav-item active">
            </c:when>
            <c:otherwise>
                <li class="nav-item">
            </c:otherwise>
          </c:choose>
            <a class="nav-link" href="<c:url value='/items'/>">
              <i class="material-icons">list</i>
              <p>Продукция</p>
            </a>
          </li>
          <c:choose>
            <c:when test="${menu_selected_num == 1}">
              <li class="nav-item active">
            </c:when>
            <c:otherwise>
                <li class="nav-item">
            </c:otherwise>
          </c:choose>
            <a class="nav-link" href="${pageContext.request.contextPath}/users">
              <i class="material-icons">supervisor_account</i>
              <p>Пользователи</p>
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top lowercase">
        <div class="container-fluid justify-content-end">
          <ul class="navbar-nav">
            <li class="nav-item dropdown account-nav-item">
              <a class="nav-link lowercase" href="javascript:;" id="navbarDropdownProfile" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="material-icons">account_circle</i>

                <i class="material-icons">arrow_drop_down</i>
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownProfile">
                <a class="dropdown-item" href="#">
                  <i class="material-icons">miscellaneous_services</i>
                  <div style="padding-right: 1.5rem;padding-left: 1.5rem;">Настройки</div>
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="javascript: document.logoutForm.submit();">
                  <i class="material-icons">directions_run</i>
                  <div style="padding-right: 1.5rem;padding-left: 1.5rem;">Выход</div>
                  <form name="logoutForm" action="<c:url value='/logout'/>" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  </form>
                </a>
              </div>
            </li>
          </ul>
        </div>
      </nav>
      <!-- End Navbar -->
