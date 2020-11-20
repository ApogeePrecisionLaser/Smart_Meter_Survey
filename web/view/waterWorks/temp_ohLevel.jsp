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
    jQuery(function(){
        $("#searchWaterTreatmentPlant").autocomplete("ohLevelCont.do", {
            extraParams: {
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#searchOverHeadTank").autocomplete("ohLevelCont.do", {
            extraParams: {
                waterTreatmentPlantName: function(){return $("#searchWaterTreatmentPlant").val();},
                action1: function() { return "getOverHeadTank"}
            }
        });
        $("#waterTreatmentPlantName").autocomplete("ohLevelCont.do", {
            extraParams: {
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#overHeadTankName").autocomplete("ohLevelCont.do", {
            extraParams: {
                waterTreatmentPlantName: function(){return $("#waterTreatmentPlantName").val();},
                action1: function() { return "getOverHeadTank"}
            }
        });

        $("#level_datetime").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
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
    });


    function setStatus(id) {
        if(id == 'save'){
            $("#clickedButton").val("Save");
        }else if(id == 'save_As'){
            $("#clickedButton").val("Save AS New");
        }else if(id == 'delete'){
            $("#clickedButton").val("Delete");
        } else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }else if(id=="searchIn"){
            $("#buttonClick").val("searchIn");
        }else if(id=="showAllRecords")
            $("#buttonClick").val("showAllRecords");
    }

    function verify() {
        var result;
        if($("#clickedButton").val() == 'Save' || $("#clickedButton").val() == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {

            var waterTreatmentPlantName = $("#waterTreatmentPlantName").val();
            var overHeadTankName = $("#overHeadTankName").val();
            var level = $("#level").val();

            if(myLeftTrim(waterTreatmentPlantName).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Water Treatment Plant name is required...</b></td>");
                $("#waterTreatmentPlantName").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(overHeadTankName).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Overhead Tank Name is required...</b></td>");
                $("#overHeadTankName").focus();
                return false; // code to stop from submitting the form2.
            }else if(myLeftTrim(level).length == 0) {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Level is required...</b></td>");
                $("#level").focus();
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
        var noOfColumns = 9;

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
        $("#ohLevelId").val(document.getElementById(t1id + (lowerLimit + 0)).innerHTML);
        $("#waterTreatmentPlantName").val(document.getElementById(t1id + (lowerLimit + 2)).innerHTML);
        $("#overHeadTankName").val(document.getElementById(t1id + (lowerLimit + 3)).innerHTML);
        $("#level_a").val(document.getElementById(t1id + (lowerLimit + 4)).innerHTML);
        $("#level_b").val(document.getElementById(t1id + (lowerLimit + 5)).innerHTML);
        var date_time = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        var date = date_time.split(" ");
        $("#level_datetime").val(date[0]);
        var time = date[1].split(":");
        $("#level_time_hour").val(time[0]);
        $("#level_time_min").val(time[1]);
        $("#remark").val(document.getElementById(t1id + (lowerLimit + 8)).innerHTML);

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
        var searchOverHeadTank = $("#searchOverHeadTank").val();
        var searchWaterTreatmentPlant = $("#searchWaterTreatmentPlant").val();
        var searchDateFrom = $("#searchDateFrom").val();
        var searchDateTo = $("#searchDateTo").val();
        var url = "OHLevelCont1.do?"+queryString+"&searchOverHeadTank="+searchOverHeadTank+"&searchWaterTreatmentPlant="+searchWaterTreatmentPlant+"&searchDateFrom="+searchDateFrom+"&searchDateTo="+searchDateTo;
        popupwin = openPopUp(url, "Overhead Tank Level", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function makeEditable(id) {
        document.getElementById("waterTreatmentPlantName").disabled = false;
        document.getElementById("overHeadTankName").disabled = false;
        document.getElementById("level_a").disabled = false;
        document.getElementById("level_b").disabled = false;
        document.getElementById("level_datetime").disabled = false;
        document.getElementById("level_time_hour").disabled = false;
        document.getElementById("level_time_min").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save_As").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = true;
        if(id == 'new') {
            $("#message").html("");
            $("#waterTreatmentPlantName").focus();
            document.getElementById("update").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            setDefaultColor($("#noOfRowsTraversed").val(), 12);
            document.getElementById("ohLevelId").value='';
        }


    }

    function searchCheck()
    {
        var button = $("#buttonClick").val();
        if(button=="searchIn")
        {
            var searchOHT = $("#searchOverHeadTank").val();
            var searchWaterTreatmentPlant = $("#searchWaterTreatmentPlant").val();
            if(searchOHT=="" && searchWaterTreatmentPlant=="")
            {
                $("#searchMessage").html("<center>Please enter Water Treatment Plant or Overhead Tank name</center>");
                $("#searchWaterTreatmentPlant").focus();
                return false;
            }
        }
    }
    function viewChart(id){
        var searchDateFrom = $("#searchDateFrom").val();
        var from_hr = $("#from_hr").val();
        var from_min = $("#from_min").val();
        var searchDateTo = $("#searchDateTo").val();
        var to_hr = $("#to_hr").val();
        var to_min = $("#to_min").val();
        var queryString = "requestprinrt=VIEW_GRAPH1&ohlevel_id="+id+"&searchDateFrom="+searchDateFrom+"&from_hr="+from_hr+"&from_min="+from_min+"&searchDateTo="+searchDateTo+"&to_hr="+to_hr+"&to_min="+to_min;
        var url = "jFreeChart.do?" + queryString;
        popupwin = openPopUp(url, "Previous History Details", 600, 600);
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
                                        <td align="center" class="header_table" width="100%"><b>Overhead Tank Level</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="OHLevelCont1.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="7" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Water Treatment Plant</th>
                                                <td><input class="input" type="text" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" size="15" ></td>
                                                <th>Overhead Tank</th>
                                                <td><input class="input" type="text" id="searchOverHeadTank" name="searchOverHeadTank" value="${searchOverHeadTank}" size="15" ></td>
                                            </tr>
                                            <tr>
                                                <th>Date FROM </th>
                                                <td><input class="input" type="text" id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}" size="15"  placeholder="searchDateFrom" ></td>
                                                <th>  hour</th>   <td><input class="input" type="text" id="from_hr" name="from_hr" value="${from_hr}" size="5"  placeholder="from_hr" ></td>
                                                <th>  min</th>  <td><input class="input" type="text" id="from_min" name="from_min" value="${from_min}" size="5"  placeholder="from_min" ></td>

  </tr>  <tr>
                                                <th>Date To </th>
                                              <td>  <input class="input" type="text" id="searchDateTo" name="searchDateTo" value="${searchDateTo}" size="15" placeholder="searchDateTo"></td>
                                                <th> hour</th>  <td> <input class="input" type="text" id="to_hr" name="to_hr" value="${to_hr}" size="5" placeholder="to_hr"></td>
                                                <th> min </th><td>  <input class="input" type="text" id="to_min" name="to_min" value="${to_min}" size="5" placeholder="to_min"></td>

                                            </tr>
                                            <tr  align="center">
                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td> <input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td> <input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList();"></td>
                                                 <td>
                                                        <input type="button" class="btn"  value ="GRAPH" id="graph" onclick="viewChart('${overheadtank_id}');"/>
                                                    </td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <%--  <c:if test="${isSelectPriv eq 'Y'}">--%>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="OHLevelCont1.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <td class="heading">S.No.</td>
                                                <td class="heading" style="white-space: normal">Water Treatment Plant</td>
                                                <td class="heading" style="white-space: normal">Overhead Tank</td>
                                                <td class="heading" colspan="2">Level</td>
                                                <td  class="heading" style="white-space: normal">Value Of Level</td>
                                                <td  class="heading">Date & Time</td>
                                                <td  class="heading">Remark</td>
                                                <td  class="heading">Step</td>
                                                <td  class="heading">Level 1</td>
                                                <td  class="heading">Level 2</td>
                                                <td  class="heading">Level 3</td>
                                                <td  class="heading">Level 4</td>
                                                <td  class="heading" style="white-space: normal">Value Of 3 & 4</td>
                                                <td  class="heading" style="white-space: normal">Level In %</td>
                                            </tr>
                                            <c:forEach var="ohLevelBean" items="${requestScope['ohLevelList']}"  varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${ohLevelBean.ohLevelId}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   >${ohLevelBean.waterTreatmentPlantName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   >${ohLevelBean.overHeadTankName}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"    align="center"> ${ohLevelBean.level_a}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   align="center" > ${ohLevelBean.level_b}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"   align="center" > ${ohLevelBean.value_of_ab}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level_datetime}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.step}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level4}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.value_of_34}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  >${ohLevelBean.level_of_34}</td>
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
                                            <input  type="hidden" id="searchOverHeadTank" name="searchOverHeadTank" value="${searchOverHeadTank}">
                                            <input  type="hidden" id="searchWaterTreatmentPlant" name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}">
                                            <input  type="hidden" id="overheadtank_id" name="overheadtank_id" value="${overheadtank_id}">
                                            <input type="hidden" name="searchDateTo" value="${searchDateTo}">
                                            <input type="hidden" name="searchDateFrom" value="${searchDateFrom}">
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <!--                        <tr>
                                                    <td align="center">
                                                        <div >
                                                            <form name="form2" method="POST" action="ohLevelCont.do" onsubmit="return verify()">
                                                                <table id="table2"  class="content" border="0"  align="center" width="600">
                                                                    <tr id="message">
                        <c:if test="${not empty message}">
                            <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                        </c:if>
                    </tr>
                    <tr>
                        <th class="heading1">Water Treatment Plant</th>
                        <td><input class="input" type="text" id="waterTreatmentPlantName" name="waterTreatmentPlantName" value="" size="40" disabled></td>
                        <th class="heading1">Overhead Tank</th>
                        <td>
                            <input class="input" type="text" style="display:none;" id="ohLevelId" name="ohLevelId" value="" >
                            <input class="input" type="text" id="overHeadTankName" name="overHeadTankName" value="" size="40" disabled>
                        </td>
                    </tr>
                    <tr>
                        <th class="heading1">Level</th><td>
                            <b class="heading1">A</b><input class="input" type="text"  id="level_a" name="level_a" value="${level_a}" maxlength="4" size="3" disabled>
                            <b class="heading1">B</b><input class="input" type="text"  id="level_b" name="level_b" value="${level_b}" maxlength="4" size="3" disabled>
                        </td>

                        <th class="heading1">Date & Time</th>
                        <td>
                            <input class="input" type="text" data-format="dd-MM-yyyy hh:mm:ss" id="level_datetime" name="level_datetime" value="${level_datetime}" size="12" disabled>
                            <b class="heading1">Hours</b><input class="input" type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="level_time_hour" name="level_time_hour" value="${level_time_hour}" maxlength="2" size="3" disabled>
                            <b class="heading1">Min.</b><input class="input" type="numeric" pattern="[0-5]{1}[0-9]{1}" id="level_time_min" name="level_time_min" value="${level_time_min}" maxlength="2" size="3" disabled>
                        </td>
                    </tr>
                    <tr>
                        <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                    </tr>
                    <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                    <tr>
                        <td align='center' colspan="4">
                            <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                            <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                            <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)"  disabled>
                            <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                            <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                        </td>
                    </tr>
                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                        <input type="hidden" id="clickedButton" value="">
                        <input type="hidden"  name="searchOverHeadTank" value="${searchOverHeadTank}" >
                        <input type="hidden"  name="searchWaterTreatmentPlant" value="${searchWaterTreatmentPlant}" >
                    </table>
                </form>
            </div>
        </td>
    </tr>-->
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>