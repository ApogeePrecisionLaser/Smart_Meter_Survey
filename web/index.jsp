<%-- 
    Document   : index
    Created on : Jan 1, 2002, 12:05:23 AM
    Author     : Navitus1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table align="center" class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${designation eq 'Admin'}">
                        <td><%@include file="/layout/menu.jsp" %></td>
                    </c:when>
                        
                    <c:otherwise>
                        <td><%@include file="/layout/customerMenu.jsp" %></td>
                    </c:otherwise>
                        
                      
                        
                </c:choose>

                        
            </tr>
            <tr>
                <td nowrap>
                    <DIV id="div_viewQtList" class="maindiv"  style="height: 650px; width: 900px" >
                        <table width="100%" >
                            <tr>
                                <td align="center">
                                    <h3>  Welcome</h3>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
