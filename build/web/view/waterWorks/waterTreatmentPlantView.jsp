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
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#searchWaterTreatmentPlant").autocomplete("waterTreatmentPlantCont.do", {
            extraParams: {                
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#cityName").autocomplete("waterTreatmentPlantCont.do", {
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
        if($("#clickedButton").val() == 'Save' || $("#clickedButton").val() == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {

            var waterTreatmentPlantName = $("#waterTreatmentPlantName").val();
            var capacityMLD = $("#capacityMLD").val();
            var city = $("#cityName").val();
            var address1 = $("#address1").val();
            
            if(myLeftTrim(waterTreatmentPlantName).length == 0) {                
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Water Treatment Plant Name is required...</b></td>");
                $("#waterTreatmentPlantName").focus();                
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(capacityMLD).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Capacity MLD is required...</b></td>");
                $("#capacityMLD").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(city).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>City is required...</b></td>");
                $("#cityName").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(address1).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Address is required...</b></td>");
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
        var noOfRowsTraversed = $("#noOfRowsTraversed").val();
        var noOfColumns = 11;

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
        $("#waterTreatmentPlantId").val(document.getElementById(t1id + (lowerLimit + 0)).innerHTML);        
        $("#waterTreatmentPlantName").val(document.getElementById(t1id + (lowerLimit + 2)).innerHTML);
        $("#capacityMLD").val(document.getElementById(t1id + (lowerLimit + 3)).innerHTML);
        $("#cityName").val(document.getElementById(t1id + (lowerLimit + 4)).innerHTML);
        $("#address1").val(document.getElementById(t1id + (lowerLimit + 5)).innerHTML);
        $("#address2").val(document.getElementById(t1id + (lowerLimit + 6)).innerHTML);
        $("#latitude").val(document.getElementById(t1id + (lowerLimit + 8)).innerHTML);
        $("#longitude").val(document.getElementById(t1id + (lowerLimit + 9)).innerHTML);
        $("#remark").val(document.getElementById(t1id + (lowerLimit + 10)).innerHTML);
       
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //        document.getElementById("edit").disabled = false;
        //        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        //        {
        //            document.getElementById("save_As").disabled = true;
        //            document.getElementById("delete").disabled = false;
        //
        //       }
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
        var url = "waterTreatmentPlantCont.do?"+queryString+"&searchWaterTreatmentPlant="+searchWaterTreatmentPlant;
        popupwin = openPopUp(url, "Water Treatment Plant Details", 500, 1000);

    }

//    function openPopUp(url, window_name, popup_height, popup_width) {
//        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
//        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
//        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
//
//        return window.open(url, window_name, window_features);
//    }



    function makeEditable(id) {
        document.getElementById("waterTreatmentPlantName").disabled = false;
        document.getElementById("capacityMLD").disabled = false;
        document.getElementById("cityName").disabled = false;
        document.getElementById("address1").disabled = false;
        document.getElementById("address2").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save_As").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = true;
        document.getElementById("get_cordinate").disabled = false;
        if(id == 'new') {
            $("#message").html("");
            $("#waterTreatmentPlantName").focus();
            document.getElementById("update").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            document.getElementById("waterTreatmentPlantId").value='';
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 11);
        }
            
           
        
    }

    function searchCheck()
    {
        var button = $("#buttonClick").val();
        if(button=="searchIn")
        {
            var searchWTP = $("#searchWaterTreatmentPlant").val();
            if(searchWTP=="")
            {
                $("#searchMessage").html("Please enter Water Treatment Plant name");
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
        var url="waterTreatmentPlantCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
        popupwin = openPopUp(url, "",  580, 620);
    }
    function openCurrentMap(longitude, lattitude) {
        var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
        var y = longitude;//$.trim(doc.getElementById(logitude).value);
        var url="waterTreatmentPlantCont.do?task=showAllOverheadTank&logitude="+y+"&lattitude="+x;
        window.open(url);
        //popupwin = openPopUp(url, "",  580, 620);
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Water Treatment Plant Page</title>
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
                                        <td align="center" class="header_table" width="100%"><b>Water Treatment Plant</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="waterTreatmentPlantCont.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="10" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th colspan="6">Water Treatment Plant Name</th>
                                                <td><input class="input" type="text" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" size="30" ></td>                                                
                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList()"></td>
                                                <td><input class="button" type="button" value="View Map" onclick="openCurrentMap()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <%--  <c:if test="${isSelectPriv eq 'Y'}">--%>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="waterTreatmentPlantCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <th class="heading">S.No.</th>
                                                <th class="heading" style="white-space: normal">Water Treatment Plant Name</th>
                                                <th  class="heading" style="white-space: normal">Capacity MLD</th>
                                                <th  class="heading">City</th>
                                                <th  class="heading">Address1</th>
                                                <th  class="heading">Address2</th>
                                                <th  class="heading">Date & Time</th>
                                                <th  class="heading">Latitude</th>
                                                <th  class="heading">Longitude</th>
                                                <th  class="heading">Remark</th>
                                                <th  class="heading"></th>

                                            </tr>
                                            <c:forEach var="waterTreatmentPlantBean" items="${requestScope['waterTreatmentPlantList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${waterTreatmentPlantBean.waterTreatmentPlantId}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${waterTreatmentPlantBean.waterTreatmentPlantName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="right" style="padding-right: 8px;">${waterTreatmentPlantBean.capacityMLD}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.cityName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.address1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.address2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="">${waterTreatmentPlantBean.dateTime}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.latitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.longitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${waterTreatmentPlantBean.remark}</td>
                                                    <td>
                                                        <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${waterTreatmentPlantBean.longitude}' , '${waterTreatmentPlantBean.latitude}');"/>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="11">
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
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div >
                                    <form name="form2" method="POST" action="waterTreatmentPlantCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>                                            
                                            <tr>
                                                <th class="heading1 " >Water Treatment Plant Name</th>
                                                <td>
                                                    <input class="input" type="text" style="display:none;" id="waterTreatmentPlantId" name="waterTreatmentPlantId" value="" >
                                                    <input class="input" type="text" id="waterTreatmentPlantName" name="waterTreatmentPlantName" value="" size="30" disabled>
                                                </td>                                            
                                                <th class="heading1">Capacity (Million Ltr. Per Day)</th><td><input class="input" type="text" id="capacityMLD" name="capacityMLD" value="" size="30" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">City</th><td><input class="input" type="text" id="cityName" name="cityName" value="" size="30" disabled></td>
                                                <th class="heading1">Address1</th><td><input class="input" type="text" id="address1" name="address1" value="" size="30" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Address2</th><td><input class="input" type="text" id="address2" name="address2" value="" size="30" disabled></td>
                                                <th class="heading1">Latitude</th><td><input class="input" type="text" id="latitude" name="latitude" value="" size="30" disabled><input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                            </tr>                                            
                                            <tr>
                                                <th class="heading1">Longitude</th><td><input class="input" type="text" id="longitude" name="longitude" value="" size="30" disabled></td>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="30" disabled></td>
                                            </tr>
                                            <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input type="button" class="button"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap(document.getElementById('longitude').value , document.getElementById('latitude').value);"/>
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)"disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)"  disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)"  disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" >
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