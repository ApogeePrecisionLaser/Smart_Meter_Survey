<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib  prefix="myfn" uri="http://myCustomTagFunctions"%>--%>
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
<script type="text/javascript" language="javascript"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">

            jQuery(function(){

                $("#searchDateFrom").datepicker({
                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#searchDateTo").datepicker({
                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });

                $("#oht_name").autocomplete("OnOffCont.do", {
                    extraParams: {
                        action1: function() { return "getOverHeadTankName"}
                    }
                });
                
                
                 $("#type_name").autocomplete("OnOffCont.do", {
                    extraParams: {
                        action1: function() { return "getTypeName"}
                    }
                });
                
                
                

            });
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }

            function displayMapList(){
                var queryString;
                var searchDateFrom=document.getElementById("searchDateFrom").value;
                var searchDateTo=document.getElementById("searchDateTo").value;
                var type = document.getElementById("type_name").value;
                queryString = "task=viewPdf&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo+"&type="+type;
                var url = "OnOffCont.do?" + queryString;
                popupwin = openPopUp(url, "Division List", 600, 900);
            }

            function insertDistribution(){
                alert("insertDistribution");
                 var url = "InsertDistributionCont.do";

                window.open(url,"target=_self");
            }


        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b>On Off Level</b></td>
                                    </tr>
                                    <tr>
                                        <td> <div align="center">
                                                <form name="form0" method="get" action="OnOffCont.do">
                                                    <table align="center" class="heading1" width="800">
                                                        <tr>
                                                            <td>Date FROM <input class="input" type="text" id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}" size="15"  placeholder="searchDateFrom" >
                                                                To
                                                                <input class="input" type="text" id="searchDateTo" name="searchDateTo" value="${searchDateTo}" size="15" placeholder="searchDateTo">
                                                            </td>

                                                            <td colspan="6" align="center">
                                                                <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                                <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                                <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()">
                                                                <input type="button" class="button" id="insert_distribution" name="insert_distribution" value="InsertDistribution" onclick="insertDistribution()">
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>Overhead Tank Name <input class="input" type="text" id="oht_name" name="oht_name" value="${searchOverheadtankName}" size="35"  placeholder="search by overhead tank name" ></td>
                                                        </tr>
                                                          <tr>
                                                            <td>Type <input class="input" type="text" id="type_name" name="type_name" value="${typeName}" size="35"  placeholder="search by type" ></td>
                                                        </tr>
                                                        <input type="hidden" name="searchDateTo" value="${searchDateTo}">
                                                        <input type="hidden" name="searchDateFrom" value="${searchDateFrom}">
                                                        <input type="hidden" name="oht_name" value="${searchOverheadtankName}">
                                                    </table>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <!--                        <tr>
                                                    <td> <div align="center">
                                                            <form name="form0" method="POST" action="ohLevelCont.do" onsubmit="return searchCheck()">
                                                                <table align="center" class="heading1" >
                                                                    <tr><td colspan="7" align="center" style="color: red;" id="searchMessage"></td></tr>
                                                                    <tr>
                                                                        <th>Water Treatment Plant</th>
                                                                        <td><input class="input" type="text" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" size="30" ></td>
                                                                        <th>Overhead Tank</th>
                                                                        <td><input class="input" type="text" id="searchOverHeadTank" name="searchOverHeadTank" value="${searchOverHeadTank}" size="30" ></td>
                                                                        <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                                        <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>

                                                                    </tr>
                                                                </table>
                                                            </form></div>
                                                    </td>
                                                </tr>-->
                        <%--  <c:if test="${isSelectPriv eq 'Y'}">--%>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="OnOffCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <td class="heading">S.No.</td>
                                                <td class="heading" style="white-space: normal">Water Treatment Plant</td>
                                                <td class="heading" style="white-space: normal">Overhead Tank</td>
                                                <td class="heading" style="white-space: normal">Date Time</td>
                                                <td class="heading" >Level</td>
                                                <td  class="heading" style="white-space: normal">Type</td>
                                                <td  class="heading" style="white-space: normal">Value Of 3 & 4</td>
                                                <td  class="heading" style="white-space: normal">Level In %</td>
                                            </tr>
                                            <c:forEach var="ohLevelBean" items="${requestScope['ohLevelList']}"  varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${ohLevelBean.ohLevelId}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${ohLevelBean.waterTreatmentPlantName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${ohLevelBean.overHeadTankName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ohLevelBean.dateTime}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  align="center"> ${ohLevelBean.level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  align="center"> ${ohLevelBean.type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ohLevelBean.value_of_34}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ohLevelBean.level_of_34}</td>

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
                                            <input type="hidden" name="searchDateTo" value="${searchDateTo}">
                                            <input type="hidden" name="searchDateFrom" value="${searchDateFrom}">
                                          <input  type="hidden" id="searchOverHeadTank" name="oht_name" value="${searchOverheadtankName}">
                                    <!-- <input  type="hidden" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}">-->
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
