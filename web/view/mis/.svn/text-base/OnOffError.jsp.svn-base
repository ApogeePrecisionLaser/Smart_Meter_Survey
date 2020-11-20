<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" language="javascript">
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    function viewImage(error_id,error_name){
        var queryString = "error_id="+error_id+"&error_name="+error_name;
        var url = "ErrorType1Cont.do?" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Overhead Tank Level</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
<!--                <td><%@include file="/layout/menu.jsp" %> </td>  -->
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b> Error Log</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                         <td><%@include file="/layout/menu.jsp" %> </td> 
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="OnOffErrorCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <td class="heading">S.No.</td>
                                                <td class="heading" style="white-space: normal">Water Treatment Plant</td>
                                                <td class="heading" style="white-space: normal">Overhead Tank</td>
                                                <td class="heading" style="white-space: normal">Error Type</td>
                                                <td class="heading" style="white-space: normal"></td>
                       

                                            </tr>
                                            <c:forEach var="ohLevelBean" items="${requestScope['ohLevelList']}"  varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${ohLevelBean.error_log_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   >${ohLevelBean.waterTreatmentPlantName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   >${ohLevelBean.overHeadTankName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   >${ohLevelBean.error_name}</td>                                       
                                                    <td id="t1c${IDGenerator.uniqueID}"  align="center"><input type="button" class="btn"  value ="View" id="View{loopCounter.count}" onclick="viewImage('${ohLevelBean.error_log_id}','${ohLevelBean.error_name}');"/></td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="10">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">

                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>