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
        <title>Connection Detail</title>
    </head>
    <script type="text/javascript">
        jQuery(function(){
            $("#searchMeterNo").autocomplete("meter_detail.do", {
                extraParams: {
                    action1: function() { return "getMeterNo" }
                }
            });
            $("#searchMobileNo").autocomplete("meter_detail.do", {
                extraParams: {
                    action1: function() { return "getMobileNo" }
                }
            });
            $("#node").autocomplete("meter_detail.do", {
                extraParams: {
                    action1: function() { return "getNodeName" }
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
        });
        function fillColumns(id) {
            var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
            var noOfColumns = 10;
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
            var meter_detail_id = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
            document.getElementById("meter_detail_id").value = meter_detail_id;
            document.getElementById("water_service_no").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
            document.getElementById("key_person_name").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
            document.getElementById("meter_no").value=document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
            document.getElementById("meter_reading").value=document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
            document.getElementById("date_time").value=document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
            document.getElementById("remark").value=document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
            
            document.getElementById("property_service_no").value=document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
            document.getElementById("node").value=document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
            document.getElementById("latitude").value=document.getElementById("lat"+meter_detail_id).value;
            document.getElementById("longitude").value=document.getElementById("long"+meter_detail_id).value;

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
        
        function makeEditable(id)
        {
            document.getElementById("key_person_name").disabled=false;
            document.getElementById("meter_no").disabled=false;
            document.getElementById("meter_reading").disabled=false;
            document.getElementById("date_time").disabled=false;
            document.getElementById("latitude").disabled=false;
            document.getElementById("longitude").disabled=false;
            document.getElementById("get_cordinate").disabled=false;
            document.getElementById("remark").disabled = false;
            document.getElementById("water_service_no").disabled = false;
            document.getElementById("property_service_no").disabled = false;
            document.getElementById("node").disabled = false;
            document.getElementById("save").disabled = true;
            document.getElementById("update").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = false;
            if(id == 'new')
            {
                $("#message").html("");
                document.getElementById("update").disabled = true;
                document.getElementById("delete").disabled = true;
                document.getElementById("save_As").disabled = true;
                document.getElementById("save").disabled = false;
                document.getElementById("key_person_name").focus();
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
        
        function verify()
        {
            var result;
            if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                if((document.getElementById("key_person_name").value).trim().length == 0) {
                    //document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Key Person Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Item Name is required...</b></td>");
                    document.getElementById("key_person_name").focus();
                    return false; // code to stop from submitting the form2.
                }
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

        function checkMeter_no()
        {
            var meter_no=document.getElementById("meter_no").value;
            $.ajax({url: "meter_detail.do?task=checkMeter_no&meter_no="+meter_no,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                context: document.body,
                success: function(response_data)
                {
                    var length=response_data;
                    if(length>0){
                        document.getElementById("msg").innerHTML="Meter No Is Exits In Database";
                    }
                    if(length==0){
                        document.getElementById("msg").innerHTML="";
                    }
                }
            });
        }
        
        function openMap(lattitude, longitude)
        {
            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
            var y = longitude;//$.trim(doc.getElementById(logitude).value);
            var url="meter_detail.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
            popupwin = openPopUp(url, "",  580, 620);
        }
        function openPopUp(url, window_name, popup_height, popup_width) {
            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
            return window.open(url, window_name, window_features);
        }
        
        function openMapForCord() {
            var url="generalCont.do?task=GetCordinates1";//"getCordinate";
            popupwin = openPopUp(url, "",  600, 630);
        }
         function openMapForCord1()
    {
        var url="generalCont.do?task=meterMapCordinates";
        popupwin = openPopUp(url, "",  600, 630);
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
                        <form name="form" action="meter_detail.do">
                            <table width="100%" align="center" >
                                <tr>
                                    <td>
                                        <table align="center">
                                            <tr>
                                                <td align="center" class="header_table" width="90%"><b>Connection Detail</b></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr align="center">
                                    <td>
                                        <span class="heading1">Meter No.</span>
                                        <input class="input" type="text" id="searchMeterNo" name="searchMeterNo" value="${searchMeterNo}" size="15">
                                        <span class="heading1">Mobile No.</span>
                                        <input class="input" type="text" id="searchMobileNo" name="searchMobileNo" value="${searchMobileNo}" size="15">
                                        <span class="heading1">Active</span>
                                        <select id="active" name="active" value="${active}">
                                            <option value="" ${active eq ""? 'selected':''}>ALL</option>
                                            <option value="Y" ${active eq "Y"? 'selected':''}>Y</option>
                                            <option value="N" ${active eq "N"? 'selected':''}>N</option>
                                        </select>
                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                        <input class="button" type="button" id="completeMap" value="Map" onclick="openMapForCord1()"/>
                                    </td>
                                </tr>
                            </table>
                        </form>                        
                        <table>
                            <tr>
                                <td align="center">
                                    <div  class="content_div" style="width:990px" >
                                        <form name="form1" action="meter_detail.do">
                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content" >
                                                <TR>
                                                    <th class="heading">S.no.</th>
                                                    <th class="heading">Water Service No</th>
                                                    <th class="heading">Person name</th>
                                                    <th class="heading">Meter No.</th>
                                                    <th class="heading">Meter Reading</th>
                                                    <th class="heading">Date & Time</th>
                                                    <th class="heading">Remark</th>

                                                    <th class="heading">Property Service No</th>
                                                    <th class="heading">Node</th>
                                                    <th class="heading">View Map</th>
                                                    <!--<th class="heading">longitude</th>-->
                                                </TR>
                                                <c:forEach var="key_person_name" items="${requestScope['list']}" varStatus="loopCounter">
                                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${key_person_name.meter_detail_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.water_service_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.key_person_name}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${key_person_name.meter_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.meter_reading}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.date_time}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.remark}</TD>

                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.property_service_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.node}</TD>
                                                        <td>
                                                            <input type="hidden"  id="lat${key_person_name.meter_detail_id}" value="${key_person_name.latitude}">
                                                            <input type="hidden"  id="long${key_person_name.meter_detail_id}" value="${key_person_name.longitude}">
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${key_person_name.latitude}' , '${key_person_name.longitude}');"/>
                                                        </td>
                                                        <%--                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.latitude}</TD>
                                                                                                                <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.longitude}</TD>--%>
                                                    </TR>
                                                </c:forEach>
                                                <tr> <td align='center' colspan="7">
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
                                            <input type="hidden"  id="searchMobileNo" name="searchMeterNo" value="${searchMobileNo}">
                                            <input type="hidden"  id="active" name="active" value="${active}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form>
                                    </div>
                                    <div  style="width:990px" >
                                        <form name="form2" action="meter_detail.do" onsubmit="return verify()">
                                            <TABLE BORDER="1" class="content" align="center" cellpadding="5%" width="52%">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <span id="msg" style="color: red">

                                                </span>

                                                <tr>
                                                    <th class="heading1">Person Name</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="meter_detail_id" name="meter_detail_id" value="" >
                                                        <input type="text" id="key_person_name" name="key_person_name" value="" disabled required>
                                                    </td>
                                                    <th class="heading1">Meter No.</th>
                                                    <td>
                                                        <input type="text" id="meter_no" name="meter_no" value="" onblur="checkMeter_no()" disabled required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Meter Reading</th>
                                                    <td>
                                                        <input type="text" id="meter_reading" name="meter_reading" value="" disabled required>
                                                    </td>
                                                    <th class="heading1">Date</th>
                                                    <td>
                                                        <input type="text" id="date_time" name="date_time" value="" size="10" disabled required>
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
                                                        <input type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled>
                                                        <input type="hidden" id="cordinate_change" value="">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Water Service No</th>
                                                    <td><input type="text" id="water_service_no" name="water_service_no" value="" disabled></td>
                                                    <th class="heading1">Property Service No</th>
                                                    <td><input type="text" id="property_service_no" name="property_service_no" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Node</th>
                                                    <td><input type="text" id="node" name="node" value="" disabled></td>

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
                                                        <input type="hidden"  id="searchkey_person_name" name="searchkey_person_name" value="${searchkey_person_name}">
                                                        <input type="hidden"  id="searchMobileNo" name="searchMobileNo" value="${searchMobileNo}">
                                                        <input type="hidden"  id="active" name="active" value="${active}">
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