<%--
    Document   : key_person_name
    Created on : Jan 1, 2002, 12:34:10 AM
    Author     : Navitus1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Meter Reading</title>
    </head>
    <script type="text/javascript">
        jQuery(function(){
            $("#meter_no").autocomplete("meterReadingCont.do", {
                extraParams: {
                    action1: function() { return "getMeterNo" }
                }
            });
            $("#searchMeterNo").autocomplete("meterReadingCont.do", {
                extraParams: {
                    action1: function() { return "getMeterNo" }
                }
            });
            $("#searchMeterNoTo").autocomplete("meterReadingCont.do", {
                extraParams: {
                    action1: function() { return "getMeterNo" }
                }
            });
            $("#date_time").datepicker({
                minDate: -100,
                showOn: "both",
                buttonImage: "images/calender.png",
                dateFormat: 'yy-mm-dd',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
            $("#searchDateFrom").datepicker({
                minDate: -100,
                showOn: "both",
                buttonImage: "images/calender.png",
                dateFormat: 'yy-mm-dd',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
            $("#searchDateTo").datepicker({
                minDate: -100,
                showOn: "both",
                buttonImage: "images/calender.png",
                dateFormat: 'yy-mm-dd',
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true
            });
        });
        function fillColumns(id) {
            var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
            var noOfColumns = 8;
            var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
            columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
            var lowerLimit, higherLimit;
            for(var i = 0; i < noOfRowsTraversed; i++)
            {
                lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
            }

            setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
            var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
            var meter_reading_id = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
            document.getElementById("meter_reading_id").value = meter_reading_id;
            document.getElementById("meter_no").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
            document.getElementById("meter_reading").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
            var date_time=(document.getElementById(t1id + (lowerLimit + 5)).innerHTML).split(" ");
            document.getElementById("date_time").value = date_time[0];
            document.getElementById("time_h").value = date_time[1].split(":")[0];
            document.getElementById("time_m").value = date_time[1].split(":")[1];;
            document.getElementById("no_of_occupants").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
            document.getElementById("remark").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
            document.getElementById("latitude").value=document.getElementById("lat"+meter_reading_id).value;
            document.getElementById("longitude").value=document.getElementById("long"+meter_reading_id).value;

            for(var i = 0; i < noOfColumns; i++)
            {
                document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
            }

            $("#message").html("");
            makeEditable('');
        }
        function setDefaultColor(noOfRowsTraversed, noOfColumns) {
            for(var i = 0; i < noOfRowsTraversed; i++) {
                for(var j = 1; j <= noOfColumns; j++) {
                    document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                }
            }
        }
        function makeEditable(id) {
            document.getElementById("no_of_occupants").disabled=false;
            document.getElementById("meter_no").disabled=false;
            document.getElementById("meter_reading").disabled=false;
            document.getElementById("date_time").disabled=false;
            document.getElementById("time_h").disabled=false;
            document.getElementById("time_m").disabled=false;
            document.getElementById("latitude").disabled=false;
            document.getElementById("longitude").disabled=false;
            document.getElementById("remark").disabled = false;
            document.getElementById("save").disabled = true;
            document.getElementById("update").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = false;
            if(id == 'new') {
                $("#message").html("");
                setDefaultColor($("#noOfRowsTraversed").val(), 8);
                document.getElementById("update").disabled = true;
                document.getElementById("delete").disabled = true;
                document.getElementById("save_As").disabled = true;
                document.getElementById("save").disabled = false;
                document.getElementById("meter_no").focus();
            }
        }
        
        function setStatus(id){
            if(id == 'save'){
                document.getElementById("clickedButton").value = "Save";
            }else if(id == 'update'){
                document.getElementById("clickedButton").value = "Update";
            } else if(id == 'save_As'){
                document.getElementById("clickedButton").value = "Save AS New";
            }
            else document.getElementById("clickedButton").value = "Delete";
        }
        
        function verify(){
            var result;
            if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                /*if((document.getElementById("key_person_name").value).trim().length == 0) {
                    //document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Key Person Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Item Name is required...</b></td>");
                    document.getElementById("key_person_name").focus();
                    return false; // code to stop from submitting the form2.
                }*/
                if(result == false)
                {
                    // if result has value false do nothing, so result will remain contain value false.
                }
                else{

                    result = true;
                }
                if(document.getElementById("clickedButton").value == 'Save AS New'){
                    result = confirm("Are you sure you want to save it as New record?")
                    return result;
                }
            } else result = confirm("Are you sure you want to delete this record?")
            return result;
        }
        
        function openMap(lattitude, longitude) {
            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
            var y = longitude;//$.trim(doc.getElementById(logitude).value);
            var url="meterReadingCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
            popupwin = openPopUp(url, "",  580, 620);
        }

        function openPopUp(url, window_name, popup_height, popup_width) {
            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
            return window.open(url, window_name, window_features);
        }

        function viewImage(id){
            var queryString = "task=viewImage&meter_reading_id=" +id;
            var url = "meterReadingCont.do?" + queryString;
            popupwin = openPopUp(url, "Show Image", 600, 900);
        }

        function displayList(){
            var queryString = "task=generateReport" ;
            var searchMeterNo = $("#searchMeterNo").val();
            var searchMeterNoTo = $("#searchMeterNoTo").val();
            var searchDateFrom = $("#searchDateFrom").val();
            var searchDateTo = $("#searchDateTo").val();
            var url = "meterReadingCont.do?"+queryString+"&searchMeterNo="+searchMeterNo+"&searchMeterNoTo="+searchMeterNoTo+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
            popupwin = openPopUp(url, "Overhead Tank Level", 500, 1000);
        }

        function displayCompleteList(){
            var queryString = "task=displayCompleteList" ;
            var searchMeterNo = $("#searchMeterNo").val();
            var searchMeterNoTo = $("#searchMeterNoTo").val();
            var searchDateFrom = $("#searchDateFrom").val();
            var searchDateTo = $("#searchDateTo").val();
            var url = "meterReadingCont.do?"+queryString+"&searchMeterNo="+searchMeterNo+"&searchMeterNoTo="+searchMeterNoTo+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
            popupwin = openPopUp(url, "Overhead Tank Level", 500, 1000);
        }
        
        function getExcel(){
            var queryString= "task=generateReport1" ;
            var searchMeterNo = $("#searchMeterNo").val();
            var searchMeterNoTo = $("#searchMeterNoTo").val();
            var searchDateFrom = $("#searchDateFrom").val();
            var searchDateTo = $("#searchDateTo").val();
            var url = "meterReadingCont.do?"+queryString+"&searchMeterNo="+searchMeterNo+"&searchMeterNoTo="+searchMeterNoTo+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
            popupwin = openPopUp(url, "Division List", 600, 900);
        }

        function getZipFile(){
            var queryString= "task=generateReportZipFile" ;
            var searchMeterNo = $("#searchMeterNo").val();
            var searchMeterNoTo = $("#searchMeterNoTo").val();
            var searchDateFrom = "";//$("#searchDateFrom").val();
            var searchDateTo = "";//$("#searchDateTo").val();
            var url = "downloadZipFilesCont.do?"+queryString+"&searchMeterNo="+searchMeterNo+"&searchMeterNoTo="+searchMeterNoTo+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
            popupwin = openPopUp(url, "Division List", 600, 900);
        }
    </script>
    <body>
        <table align="center" class="main" cellpadding="0" cellspacing="0" border="1" width="1000px">

            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>

            <tr>
                <td>
                    <div class="maindiv" id="body">
                        <form name="form" action="meterReadingCont.do">
                            <table width="100%" align="center" >
                                <tr>
                                    <td>
                                        <table align="center">
                                            <tr>
                                                <td align="center" class="header_table" width="90%"><b>Meter Reading</b></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr align="center">
                                    <td>
                                        <span class="heading1">Meter No. From</span>
                                        <input class="input" type="text" id="searchMeterNo" name="searchMeterNo" value="${searchMeterNo}" size="15">
                                        <span class="heading1">Meter No To.</span>
                                        <input class="input" type="text" id="searchMeterNoTo" name="searchMeterNoTo" value="${searchMeterNoTo}" size="15">
                                        <span class="heading1">Date from</span>
                                        <input class="input" type="text" id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}" size="15">
                                        <span class="heading1">Date To.</span>
                                        <input class="input" type="text" id="searchDateTo" name="searchDateTo" value="${searchDateTo}" size="15">
                                    </td>
                                </tr>
                                <tr align="center">
                                    <td>
                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                        <input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList()">
                                        <input type="button" class="button"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()">
                                        <input type="button" class="button"  id="viewZip" name="viewZip" value="Zip" onclick="getZipFile()">
                                        <input type="button" class="button" id="displayCompleteList" name="displayCompleteList" value="Complete PDF File" onclick="displayCompleteList()">
                                    </td>
                                </tr>
                            </table>
                        </form>                        
                        <table>
                            <tr>
                                <td align="center">
                                    <div  class="content_div" style="width:990px" >
                                        <form name="form1" action="meterReadingCont.do">
                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content" >
                                                <TR>
                                                    <th class="heading">S.no.</th>
                                                    <th class="heading">Meter No.</th>
                                                    <th class="heading">Survey Meter No.</th>
                                                    <th class="heading">Meter Reading</th>
                                                    <th class="heading">Date & Time</th>
                                                    <th class="heading">No. Of Occupants</th>
                                                    <th class="heading">Remark</th>
                                                    <th class="heading">View Map</th>
                                                    <th class="heading">Image</th>
                                                    <!--<th class="heading">longitude</th>-->
                                                </TR>
                                                <c:forEach var="key_person_name" items="${requestScope['list']}" varStatus="loopCounter">
                                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${key_person_name.meter_reading_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${key_person_name.meter_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${key_person_name.survey_meter_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.meter_reading}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.date_time}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.number_of_occupants}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.remark}</TD>
                                                        <td>
                                                            <input type="hidden"  id="lat${key_person_name.meter_reading_id}" value="${key_person_name.latitude}">
                                                            <input type="hidden"  id="long${key_person_name.meter_reading_id}" value="${key_person_name.longitude}">
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${key_person_name.latitude}' , '${key_person_name.longitude}');"/>
                                                        </td>
                                                        <td>
                                                            <input type="button" class="btn"  value ="View" id="image{loopCounter.count}" onclick="viewImage('${key_person_name.meter_reading_id}');"/>
                                                        </td>
                                                        <%--                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.latitude}</TD>
                                                                                                                <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.longitude}</TD>--%>
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
                                            <input type="hidden"  id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden"  id="searchMeterNo" name="searchMeterNo" value="${searchMeterNo}">
                                            <input type="hidden"  id="searchMeterNoTo" name="searchMeterNoTo" value="${searchMeterNoTo}">
                                            <input type="hidden"  id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}">
                                            <input type="hidden"  id="searchDateTo" name="searchDateTo" value="${searchDateTo}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form>
                                    </div>
                                    <div  style="width:990px" >
                                        <form name="form2" action="meterReadingCont.do" method="post" onsubmit="return verify()">
                                            <TABLE BORDER="1" class="content" align="center" cellpadding="5%" width="52%">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Meter No.</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="meter_reading_id" name="meter_reading_id" value="" >
                                                        <input type="text" id="meter_no" name="meter_no" value="" disabled required>
                                                    </td>
                                                    <th class="heading1">Meter Reading</th>
                                                    <td>
                                                        <input type="text" id="meter_reading" name="meter_reading" value="" disabled required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Date & Time</th>
                                                    <td>
                                                        <input type="text" id="date_time" name="date_time" value="" size="10" disabled required>
                                                        <b>Time</b>
                                                        <input class="input " type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="time_h" name="time_h" value="" maxlength="2" size="2" onkeyup="" disabled required>
                                                        <input class="input " type="numeric" pattern="[0-5]{1}[0-9]{1}" id="time_m" name="time_m" value="" maxlength="2" size="2" onkeyup="" disabled required>
                                                    </td>
                                                    <th class="heading1">No. Of Occupants</th>
                                                    <td>
                                                        <input type="text" id="no_of_occupants" name="no_of_occupants" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Latitude</th>
                                                    <td>
                                                        <input type="text" id="latitude" name="latitude" value="" disabled>
                                                    </td>
                                                    <th class="heading1">Longitude</th>
                                                    <td>
                                                        <input type="text" id="longitude" name="longitude" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">remark</th>
                                                    <td><input type="text" id="remark" name="remark" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="4" align="center">
                                                        <input class="button"  type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                        <input class="button"  type="submit" name="task" id="save" value="Save" onclick="setStatus(id)"  disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)"  disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                                                        <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                        <%--                                                        <input type="hidden"  id="searchkey_person_name" name="searchkey_person_name" value="${searchkey_person_name}">--%>
                                                        <input type="hidden" id="clickedButton" name="clickedButton" value="">
                                                    </td>
                                                </tr>
                                            </TABLE>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>