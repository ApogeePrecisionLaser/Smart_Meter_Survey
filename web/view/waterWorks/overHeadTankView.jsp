<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#searchWaterTreatmentPlant").autocomplete("overHeadTankCont.do", {
            extraParams: {                
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#waterTreatmentPlantName").autocomplete("overHeadTankCont.do", {
            extraParams: {                
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#searchOverHeadTank").autocomplete("overHeadTankCont.do", {
            extraParams: {
                waterTreatmentPlant: function(){ return $("#searchWaterTreatmentPlant").val();},
                action1: function() { return "getOverHeadTank"}
            }
        });
        $("#cityName").autocomplete("overHeadTankCont.do", {
            extraParams: {
                action1: function() { return "getCityName"}
            }
        });
    });
  
    
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }else if(id == 'delete'){
            document.getElementById("clickedButton").value = "Delete";
        }else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
        else if(id=="searchIn"){
            document.getElementById('buttonClick').value="searchIn";
        }else if(id=="showAllRecords")
            document.getElementById('buttonClick').value="showAllRecords";
    }

    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
            var waterTreatmentPlantName = $("#waterTreatmentPlantName").val();
            var overHeadTankName = $("#overHeadTankName").val();
            var capacityHeight = $("#capacityHeight").val();
            var capacityLTR = $("#capacityLTR").val();
            var height = $("#height").val();
            var city = $("#cityName").val();
            var address1 = $("#address1").val();
            
            if(myLeftTrim(waterTreatmentPlantName).length == 0) {                
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Water Treatment Plant Name is required...</b></td>");
                $("#waterTreatmentPlantName").focus();                
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(overHeadTankName).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Overhead Tank Name is required...</b></td>");
                $("#overHeadTankName").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(capacityHeight).length == 0 && myLeftTrim(capacityLTR).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Capacity height or Capacity Ltr. is required...</b></td>");
                $("#capacityHeight").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(height).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Height is required...</b></td>");
                $("#height").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(city).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>City is required...</b></td>");
                $("#city").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(address1).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Address1 is required...</b></td>");
                $("#address1").focus();
                return false; // code to stop from submitting the form2.
            }
           
            if($("#clickedButton").val() == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to delete this record?")
        return result;
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 15;

        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        $("#overHeadTankId").val(document.getElementById(t1id + (lowerLimit + 0)).innerHTML);
        $("#waterTreatmentPlantName").val(document.getElementById(t1id + (lowerLimit + 2)).innerHTML);
        $("#overHeadTankName").val(document.getElementById(t1id + (lowerLimit + 3)).innerHTML);
        $("#capacityHeight").val(document.getElementById(t1id + (lowerLimit + 4)).innerHTML);
        $("#capacityLTR").val(document.getElementById(t1id + (lowerLimit + 5)).innerHTML);
        $("#height").val(document.getElementById(t1id + (lowerLimit + 6)).innerHTML);
        $("#cityName").val(document.getElementById(t1id + (lowerLimit + 7)).innerHTML);
        $("#address1").val(document.getElementById(t1id + (lowerLimit + 8)).innerHTML);
        $("#address2").val(document.getElementById(t1id + (lowerLimit + 9)).innerHTML);
        $("#latitude").val(document.getElementById(t1id + (lowerLimit + 11)).innerHTML);
        $("#longitude").val(document.getElementById(t1id + (lowerLimit + 12)).innerHTML);
        $("#remark").val(document.getElementById(t1id + (lowerLimit + 13)).innerHTML);
        $("#select").val(document.getElementById(t1id + (lowerLimit + 14)).innerHTML);
        
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //        document.getElementById("edit").disabled = false;
        //        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        //        {
        //            document.getElementById("save_As").disabled = true;
        //            document.getElementById("delete").disabled = false;
        //
        //        }
        makeEditable('');
        $("#message").html("");
    }

    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }
    var popupwin = null;
    function displayList(){
        var queryString = "task=generateReport" ;
        var searchWaterTreatmentPlant = $("#searchWaterTreatmentPlant").val();
        var searchOverHeadTank = $("#searchOverHeadTank").val();
        var url = "overHeadTankCont.do?"+queryString+"&searchWaterTreatmentPlant="+searchWaterTreatmentPlant+"&searchOverHeadTank="+searchOverHeadTank;
        popupwin = openPopUp(url, "Library Details", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function makeEditable(id)
    {
        document.getElementById("waterTreatmentPlantName").disabled = false;
        document.getElementById("overHeadTankName").disabled = false;
        document.getElementById("capacityHeight").disabled = false;
        document.getElementById("capacityLTR").disabled = false;
        document.getElementById("height").disabled = false;
        document.getElementById("cityName").disabled = false;
        document.getElementById("address1").disabled = false;
        document.getElementById("address2").disabled = false;
        document.getElementById("latitude").disabled = false
        document.getElementById("longitude").disabled = false
        document.getElementById("remark").disabled = false;
        document.getElementById("save_As").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = true;
        document.getElementById("get_cordinate").disabled = false;
        document.getElementById("select").disabled = false;
        if(id == 'new') {
            $("#message").html("");
            $("#waterTreatmentPlantName").focus();
            document.getElementById("update").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 14);
            document.getElementById("overHeadTankId").value='';
        }
       
        
    }

    function searchCheck()
    {
        var button = $("#buttonClick").val();
        //var button = document.getElementById("buttonClick").value;
        if(button=="searchIn")
        {
            var searchWTP = $("#searchWaterTreatmentPlant").val();
            var searchOHT = $("#searchOverHeadTank").val();
            if(searchWTP=="" & searchOHT=="")
            {
                $("#searchMessage").html("Please enter Water Treatment Plant or Overhead Tank name");
                return false;
            }
        }

    }
    function openMapForCord() {
        var url="generalCont.do?task=GetCordinates1";//"getCordinate";
        popupwin = openPopUp(url, "",  600, 630);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
    function openMap(longitude, lattitude) {
        var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
        var y = longitude;//$.trim(doc.getElementById(logitude).value);

        var url="overHeadTankCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
        popupwin = openPopUp(url, "",  580, 620);
    }
    function openCurrentMap(longitude, lattitude) {
        var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
        var y = longitude;//$.trim(doc.getElementById(logitude).value);
        var url="overHeadTankCont.do?task=showAllOverheadTank&logitude="+y+"&lattitude="+x;
        window.open(url);
        //popupwin = openPopUp(url, "",  580, 620);
    }
    function openCurrentMap1(longitude, lattitude) {
        var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
        var y = longitude;//$.trim(doc.getElementById(logitude).value);
        var url="overHeadTankCont.do?task=showAllView&logitude="+y+"&lattitude="+x;
        window.open(url);
        //popupwin = openPopUp(url, "",  580, 620);
    }
    function send_url() {

        var url="SendPDFSchedularCont1.do";
        //window.open(url);
        popupwin = openPopUp(url, " ", 500, 1000);

    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Overhead Tank Page</title>
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
                                        <td align="center" class="header_table" width="100%"><b>Overhead Tank</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="overHeadTankCont.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="12" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Water Treatment Plant</th>
                                                <td><input class="input" type="text" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" size="30" ></td>
                                                <th>Overhead Tank</th>
                                                <td><input class="input" type="text" id="searchOverHeadTank" name="searchOverHeadTank" value="${searchOverHeadTank}" size="30" ></td>
                                            </tr>
                                            <tr align="center">
                                                <td colspan="2"><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search">
                                                    <input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td colspan="2">  <input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList()">
                                               <input type="button" value="OverHeadTank Map" onclick="openCurrentMap()">
                                             <input type="button" value="All View" onclick="openCurrentMap1()">
                                                <input type="button" value="SendPDESchedular" onclick="send_url()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <%--   <c:if test="${isSelectPriv eq 'Y'}">--%>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="overHeadTankCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Water Treatment Plant</th>
                                                <th class="heading">Overhead Tank</th>
                                                <th  class="heading">Capacity Height</th>
                                                <th  class="heading">Capacity Ltr.</th>
                                                <th  class="heading">Height</th>
                                                <th  class="heading">City</th>
                                                <th  class="heading">Address1</th>
                                                <th  class="heading">Address2</th>
                                                <th  class="heading">Date & Time</th>
                                                <th  class="heading">Latitude</th>
                                                <th  class="heading">Longitude</th>
                                                <th  class="heading">Remark</th>
                                                <th  class="heading">Type</th>
                                                <th  class="heading"></th>
                                            </tr>
                                            <c:forEach var="overHeadTankBean" items="${requestScope['overHeadTankList']}"  varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${overHeadTankBean.overHeadTankId}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${overHeadTankBean.waterTreatmentPlantName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${overHeadTankBean.overHeadTankName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="text-align: right;padding-right: 8px;">${overHeadTankBean.capacityHeight}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="text-align: right;padding-right: 8px;">${overHeadTankBean.capacityLTR}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="text-align: right;padding-right: 8px;">${overHeadTankBean.height}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.cityName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.address1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.address2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.dateTime}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.latitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.longitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${overHeadTankBean.select}</td>
                                                    <td>
                                                        <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${overHeadTankBean.longitude}' , '${overHeadTankBean.latitude}');"/>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="14">
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
                                            <input  type="hidden" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" >
                                            <input  type="hidden" id="searchOverHeadTank" name="searchOverHeadTank" value="${searchOverHeadTank}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div >
                                    <form name="form2" method="POST" action="overHeadTankCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>                                            
                                            <tr>
                                                <th class="heading1" style="padding-left: 15px;">Water Treatment Plant</th>
                                                <td>
                                                    <input class="input" type="text" id="waterTreatmentPlantName" name="waterTreatmentPlantName" value="" size="40" disabled>
                                                </td>
                                                <th class="heading1" style="padding-left: 15px;">Overhead Tank</th>
                                                <td>
                                                    <input class="input" type="text" style="display:none;" id="overHeadTankId" name="overHeadTankId" value="" >
                                                    <input class="input" type="text" id="overHeadTankName" name="overHeadTankName" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1" style="padding-left: 15px;">Capacity Height</th><td><input class="input" type="text" id="capacityHeight" name="capacityHeight" value="" size="40" disabled></td>
                                                <th class="heading1" style="padding-left: 15px;">Capacity Ltr.</th><td><input class="input" type="text" id="capacityLTR" name="capacityLTR" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1" style="padding-left: 15px;">Height</th><td><input class="input" type="text" id="height" name="height" value="" size="40" disabled></td>
                                                <th class="heading1" style="padding-left: 15px;">City</th><td><input class="input" type="text" id="cityName" name="cityName" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1" style="padding-left: 15px;">Address1</th><td><input class="input" type="text" id="address1" name="address1" value="" size="40" disabled></td>                                            
                                                <th class="heading1" style="padding-left: 15px;">Address2</th><td><input class="input" type="text" id="address2" name="address2" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Latitude</th><td><input class="input" type="text" id="latitude" name="latitude" value="" size="40" disabled></td>
                                                <th class="heading1">Longitude</th><td><input class="input" type="text" id="longitude" name="longitude" value="" size="40" disabled><input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1" style="padding-left: 15px;">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                                <th class="heading1" style="padding-left: 15px;">Select</th><td><select class="input" id="select" name="select" disabled>
                                                                                <option value="">---Select---</option>
                                                                                <option value="YES">OverheadTank</option>
                                                                                <option value="NO">Sump Well</option>
                                                                                </select></td>
                                            </tr>
                                            <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input type="button" class="button"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap(document.getElementById('longitude').value , document.getElementById('latitude').value);"/>
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)"  disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)"  disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" >
                                            <input type="hidden"  name="searchOverHeadTank" value="${searchOverHeadTank}" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>