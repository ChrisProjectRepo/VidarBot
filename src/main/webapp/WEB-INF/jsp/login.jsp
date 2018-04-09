<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no">
    <meta name="description" content="Materialize is a Material Design Admin Template,It's modern, responsive and based on Material Design by Google. ">
    <meta name="keywords" content="materialize, admin template, dashboard template, flat admin template, responsive admin template,">
    <title>Login Page | Materialize - Material Design Admin Template</title>

      <!-- CSS  -->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
      <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
      <link href="/css/page-center.css" type="text/css" rel="stylesheet">

      <link href="/css/style-login.css" type="text/css" rel="stylesheet" media="screen,projection"/>

</head>
<body class="blue-grey darken-4">


<div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
        <c:url var="loginUrl" value="" />
        <form action="${loginUrl}" method="post" class="login-form">
            <c:if test="${param.error != null}">
                <div class="alert alert-danger" style="color: red">
                    <p>Invalid username and password.</p>
                </div>
            </c:if>
            <div class="row">
                <div class="input-field col s12 center">
                    <img src="/images/login-logo.png" alt="" class="circle responsive-img valign profile-image-login">
                    <p class="center login-form-text">Material Design Admin Template</p>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="material-icons prefix pt-5">person_outline</i>
                    <input id="username" type="text" name="ssoId" ><span class="highlight"></span><span class="bar"></span>
                    <label for="username" class="center-align">Username</label>
                </div>
            </div>
            <div class="row margin">
                <div class="input-field col s12">
                    <i class="material-icons prefix pt-5">lock_outline</i>
                    <input id="password" type="password" name="password" >
                    <label for="password">Password</label>
                  <!--  <span class="helper-text" data-error="wrong" data-success="right">Helper text</span> -->
                </div>
            </div>
            <div class="row">
                <div class="col s12 m12 l12 ml-2 mt-3">
                    <input type="checkbox" id="remember-me" />
                    <label for="remember-me">Remember me</label>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
            <div class="row">
                <div class="input-field col s12">
                    <button class="btn waves-effect blue-grey darken-3 col s12">Login</button>
                </div>
            </div>

        </form>
    </div>
</div>
<!-- ================================================
Scripts
================================================ -->
<!--  Scripts-->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>
<script src="/js/init.js"></script>
</body>
</html>
