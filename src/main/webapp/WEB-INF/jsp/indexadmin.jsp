<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="refresh" content="5">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title>Starter Template - Materialize</title>

  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>


</div>
  <nav class="blue-grey darken-4 white-text nav-extended">
     <div class="nav-wrapper container">
       <a href="#" class="brand-logo">VIDAR</a>
       <ul class="right">
         <li><a> <c:out value="${data.myOnion}"></c:out></a></li>
       </ul>
     </div>
     <div class="nav-content blue-grey darken-3">
       <ul class="tabs tabs-transparent">
         <li class="tab"><a class="active" href="#botconsole">BOT LOG</a></li>
         <li class="tab"><a  href="#botlist">BOT LIST</a></li>
            <li class="tab"><a href="#userpriv">USER PRIVILEGE</a></li>
               <li class="tab"><a href="#userlist">USER LIST</a></li>
       </ul>
     </div>
   </nav>

<div id="main" class="wrapper">


  <!-- Bot Console  -->
  <section id="botconsole">

    <!-- Info Bot  -->
    <div class="row">
        <div class="col s12 m8 l6 offset-l3 offset-m2">
            <div class="card z-depth-5 card-roundCornerInfoBot">
                <div class="card-content">

                    <div class="card-title blue-grey-text center-align">
                        <b>INFO BOT</b>
                    </div>
                    <div class="row blue-grey-text center-align">
                        <div class="col s6">
                            <b>MAC</b>
                        </div>
                        <div class="col s6">
                            <b>OS</b>
                        </div>
                    </div>
                    <div class="row center-align">
                        <div class="col s6">
                            <c:out value="${data.mac}"></c:out>
                        </div>
                        <div class="col s6">
                            <c:out value="${data.os}"></c:out>
                        </div>
                    </div>
                    <div class="row blue-grey-text center-align">
                        <div class="col s6">
                            <b>Architettura</b>
                        </div>
                        <div class="col s6">
                            <b>Versione OS</b>
                        </div>
                    </div>
                    <div class="row center-align">
                        <div class="col s6">
                            <c:out value="${data.archOS}"></c:out>
                        </div>
                        <div class="col s6">
                            <c:out value="${data.versionOS}"></c:out>
                        </div>
                    </div>
                    <div class="row blue-grey-text center-align">
                        <div class="col s6">
                            <b>Computer User</b>
                        </div>
                        <div class="col s6">
                            <b>ID HASH</b>
                        </div>
                    </div>
                    <div class="row center-align">
                        <div class="col s6">
                            <c:out value="${data.usernameOS}"></c:out>
                        </div>
                        <div class="col s6">
                            <c:out value="${data.idHash}"></c:out>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

      <!--  Console-->
      <div class="row">
          <div class="col s12 m6">
              <div id="style-7" class="card z-depth-5 card-roundCorner scale-transition scale-in cardHeight">
                  <div class="card-content text">
                      <span class="card-title">Debug Console</span>
                      <c:forEach items="${debug}" var="deb">

                          <p><c:out value="${deb}"></c:out>
                          </p>

                      </c:forEach>
                  </div>
              </div>
          </div>
          <div class="col s12 m6">
              <div id="style-7" class="card z-depth-5 card-roundCorner scale-transition scale-in cardHeight">
                  <div class="card-content">
                      <span class="card-title">Terminal Console</span>
                          <c:forEach items="${terminal}" var="ter">

                      <p><c:out value="${ter}"></c:out>
                      </p>

                      </c:forEach>

                  </div>
              </div>
          </div>
      </div>

  </section>

  <!-- Bot List  -->
  <section id="botlist">

      <!-- Info Bot  -->
      <div class="container">


        <ul class="collection with-header">

        <li class="collection-header"><h4>Vidar Bot Net</h4></li>

            <c:forEach items="${bots}" var="bot">
                <li class="collection-item avatar">
                    <i class="material-icons circle blue-grey darken-3">computer</i>
                    <span class="title"><c:out value="${bot.onionAddress}"></c:out></span>
                    <p><c:out value="${bot.idBot}"></c:out><br><c:out value="${bot.usernameOS}"></c:out>
                    </p>
                    <a class="secondary-content small waves-effect waves-light btn blue-grey darken-3"><i class="material-icons left">build</i>manage</a>
                </li>
            </c:forEach>

    </ul>

      </div>
    </section>

</div>

  <footer class="page-footer blue-grey darken-4 white-text">
    <div class="footer-copyright">
      <div class="container">
      Made by <a class="orange-text text-lighten-3">Nobody</a>
      </div>
    </div>
  </footer>


  <!--  Scripts-->
  <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <!-- Compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>
  <script src="js/init.js"></script>

  </body>
</html>
