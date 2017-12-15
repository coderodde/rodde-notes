<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>rodde-notes</title>
        <style>
            <%@include file="styles.css" %>
        </style>
        <script
          src="https://code.jquery.com/jquery-3.2.1.min.js"
          integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
          crossorigin="anonymous">
      
        </script>
        
        <script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.2/MathJax.js?config=TeX-MML-AM_CHTML'></script>
        
        <script type="text/x-mathjax-config">
            MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\[','\\]']]}});
        </script>
        
        <script> 
            <%@include file="code.js" %>
        </script>
    </head>
    <body>
        <c:choose>
            <c:when test="${pageType == "viewPage"}">
                <%@include file="viewPage.jspf" %>
            </c:when>
            <c:when test="${pageType == "editPage"}">
                <%@include file="editPage.jspf" %>
            </c:when>
            <c:otherwise>
                Unknown page type!
            </c:otherwise>
        </c:choose>
    </body>
</html>
