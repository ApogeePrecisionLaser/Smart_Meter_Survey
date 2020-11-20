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
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#watertreatmentplant").autocomplete("waterTreatmentPlantCont.do", {
            extraParams: {
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#search_waterPlant").autocomplete("waterTreatmentPlantCont.do", {
            extraParams: {
                action1: function() { return "getWaterTreatmentPlant"}
            }
        });
        $("#overheadtank_name").autocomplete("OverHead_WaterPlant.do", {
            extraParams: {
                action1: function() { return "getTankName" }
            }
        });
        $("#search_overheadtank_name").autocomplete("OverHead_WaterPlant.do", {
            extraParams: {
                action1: function() { return "getTankName" }
            }
        });
    });


    function setStatus(id)
    {
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

    //    function verify() {
    //        var result;
    //        if($("#clickedButton").val() == 'Save' || $("#clickedButton").val() == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
    //
    //            var waterTreatmentPlantName = $("#waterTreatmentPlantName").val();
    //            var overHeadTankName = $("#overHeadTankName").val();
    //            var level = $("#level").val();
    //
    //            if(myLeftTrim(waterTreatmentPlantName).length == 0) {
    //                $("#message").html("<td colspan='5' bgcolor='coral'><b>Water Treatment Plant name is required...</b></td>");
    //                $("#waterTreatmentPlantName").focus();
    //                return false; // code to stop from submitting the form2.
    //            }else if(myLeftTrim(overHeadTankName).length == 0) {
    //                $("#message").html("<td colspan='5' bgcolor='coral'><b>Overhead Tank Name is required...</b></td>");
    //                $("#overHeadTankName").focus();
    //                return false; // code to stop from submitting the form2.
    //            }else if(myLeftTrim(level).length == 0) {
    //                $("#message").html("<td colspan='5' bgcolor='coral'><b>Level is required...</b></td>");
    //                $("#level").focus();
    //                return false; // code to stop from submitting the form2.
    //            }
    //            if($("#clickedButton").val() == 'Save AS New'){
    //                result = confirm("Are you sure you want to save it as New record?")
    //                return result;
    //            }
    //        } else result = confirm("Are you sure you want to delete this record?")
    //        return result;
    //    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns)
    {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id) {
        var noOfRowsTraversed = $("#noOfRowsTraversed").val();
        var noOfColumns = 5;

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
        $("#overHead_WaterPlant_id").val(document.getElementById(t1id + (lowerLimit + 0)).innerHTML);
        $("#watertreatmentplant").val(document.getElementById(t1id + (lowerLimit + 2)).innerHTML);
        $("#overheadtank_name").val(document.getElementById(t1id + (lowerLimit + 3)).innerHTML);
        $("#remark").val(document.getElementById(t1id + (lowerLimit + 4)).innerHTML);

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }

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
        var search_waterPlant = $("#search_waterPlant").val();
        var url = "PipeDetailCont.do?"+queryString+"&search_waterPlant="+search_waterPlant;
        popupwin = openPopUp(url, "Overhead Tank Level", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function makeEditable(id) {
        document.getElementById("overHead_WaterPlant_id").disabled = false;
        document.getElementById("watertreatmentplant").disabled = false;
        document.getElementById("overheadtank_name").disabled = false;
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

    function getSectionName(id, node){
        var task = $("#task").val();
        if(task == "child"){
            opener.document.getElementById("child_node_id").value = id;
            opener.document.getElementById("childHeaderName").value = node;
        }else if(task == "parent") {
            opener.document.getElementById("node_id").value = id;
            opener.document.getElementById("parentHeaderName").value = node;
        }else if(task == "header") {
            opener.document.getElementById("header_node_id").value = id;
            opener.document.getElementById("HeaderName").value = node;
        }
        window.close();
    }

    function getNode(name){
        var url = "GetPipeNameCont.do?task="+name;
        popupwin = openPopUp(url, "Header List", 600, 725);
    }
    function makeEditable1() {
        var parentHeaderName = $("#parentHeaderName").val();
        var childHeaderName = $("#childHeaderName").val();
        //var childHeaderName = $('#'+id).val();
        //alert(parentHeaderName);
        if(myLeftTrim(parentHeaderName).length == 0 ){
            //alert("if block");
            document.getElementById("getSubHeader").disabled = true;
            //document.getElementById("childHeaderName").value = "";
            if(myLeftTrim(childHeaderName).length != 0){
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Please Select Parent Header Name first</b></td>");
                // $('#'+id).val('');
                $("#childHeaderName").val('');
                // alert("Please Select Parent Header Name first");
            }
        } else if(myLeftTrim(parentHeaderName).length != 0 ) {
            //$("#message").val('');

            document.getElementById("getSubHeader").disabled = false;
            //alert("else block");
        }
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
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center">
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b>Node OverHead Tank Mapping</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="OverHead_WaterPlant.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="7" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Water Plant</th>
                                                <td><input class="input" type="text" id="search_waterPlant" name="search_waterPlant" value="${search_waterPlant}" size="30" ></td>
                                                <th>OverHead Tank Name</th>
                                                <td><input class="input" type="text" id="search_overheadtank_name" name="search_overheadtank_name" value="${search_overheadtank_name}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <%--  <c:if test="${isSelectPriv eq 'Y'}">--%>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="OverHead_WaterPlant.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="800"  border="1"  align="center" class="content">
                                            <tr >
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Water Plant Name</th>
                                                <th class="heading">OverHead Tank Name</th>
                                                <th  class="heading">Remark</th>
                                            </tr>
                                            <c:forEach var="ohLevelBean" items="${requestScope['list']}"  varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none;" onclick="fillColumns(id)">${ohLevelBean.overHead_WaterPlant_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${ohLevelBean.watertreatmentplant}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${ohLevelBean.overheadtank_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ohLevelBean.remark}</td>
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
                                            <input  type="hidden" id="search_waterPlant" name="search_waterPlant" value="${search_waterPlant}">
                                            <input  type="hidden" id="search_overheadtank_name" name="search_overheadtank_name" value="${search_overheadtank_name}">
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div >
                                    <form name="form2" method="POST" action="OverHead_WaterPlant.do" >
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Water Plant</th>
                                                <td><input class="input" type="text" id="watertreatmentplant" name="watertreatmentplant" value="" size="40" disabled>
                                                    
                                                    <input class="input" type="text" style="display: none" id="overHead_WaterPlant_id" name="overHead_WaterPlant_id" value="" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">OverHead Tank Name</th>
                                                <td><input class="input" type="text" id="overheadtank_name" name="overheadtank_name" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Remark</th>
                                                <td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
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
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="search_waterPlant" value="${search_waterPlant}" >
                                            <input  type="hidden"  name="search_overheadtank_name" value="${search_overheadtank_name}">

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
