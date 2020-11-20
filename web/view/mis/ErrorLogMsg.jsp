<%-- 
    Document   : ErrorLogMsg
    Created on : Oct 5, 2017, 10:07:32 AM
    Author     : Com7_2
--%>

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

        <script type="text/javascript" language="javascript">

            jQuery(function(){
                $("#search_key_person").autocomplete("ErrorLogMsgCont.do", {
                    extraParams: {
                        action1: function() { return "getKeyPerson"}
                    }
                });
                $("#search_overhead_tank").autocomplete("ErrorLogMsgCont.do", {
                    extraParams: {
                        action1: function() { return "getOverheadTank"}
                    }
                });


            });


   function setErrorMsgStatus(id){
        $.ajax({
            url:"ViewErrorCont.do",
            data:"action1=setErrorMsg&id=" +id,
            success:function(response){
                location.reload();
            }
        });
    }

    function getViewErrorList(){
                var searchKeyPerson = $("#search_key_person").val();
                var searchoverheadTank = $("#search_overhead_tank").val();


                var queryString = "task=generateHSReport&search_key_person="+searchKeyPerson+"&search_overhead_tank="+searchoverheadTank;
                var url = "ErrorLogMsgCont.do?" + queryString;
                popupwin = openPopUp(url, "NameStatus List", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }



     </script>

    </head>
    <body>
       <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">

                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="ErrorLogMsgCont.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="11" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Key Person</th>
                                                <td><input class="input" type="text" id="search_key_person" name="search_key_person" value="${searchKeyPerson}" size="30" ></td>

                                                <th>Overhead Tank</th>
                                                <td><input class="input" type="text" id="search_overhead_tank" name="search_overhead_tank" value="${searchOverheadTank}" size="30" ></td>


                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="getViewErrorList()"></td>

                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>

                        <tr>
                            <td>
                        <tr align="center">
                            <td >
                                <form name="form1" method="post" action="ErrorLogMsgCont.do">
                                    <div class="content_div">
                                        <TABLE BORDER="1" align="center" cellpadding="5%" width="40%" height="100%" class="content">


                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <th class="heading">S.no&nbsp;</th>
                                            <TH class="heading" style="white-space:normal">Water Treatment Plant&nbsp;</TH>
                                            <th class="heading">Overhead Tank&nbsp;</th>
                                            <th class="heading">Key Person&nbsp;</th>
                                            <th class="heading">Error Type&nbsp;</th>
                                            <th class="heading">Error Message&nbsp;</th>
                                            <th class="heading">Error Status&nbsp;</th>
                                            <th class="heading">Message Status&nbsp;</th>

                                            <c:forEach var="item" items="${requestScope['List']}" varStatus="loopCounter">
                                                <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                <tr>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none">${item.error_log_msg_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.wtpName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.key_person_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.error_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.error_status_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}">${item.message_status}</td>
                                                    

                                                </tr>
                                                </TR>
                                            </c:forEach>
                                            <tr> <td align='center' colspan="8">
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
                                                            <input class="button" type='submit' name='buttonAction' value='Last' >
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </TABLE>
                                    </div>
                                    <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                    <input type="hidden"  id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
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
