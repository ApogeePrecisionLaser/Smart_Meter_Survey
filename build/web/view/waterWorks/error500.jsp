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
        var url = "ohLevelCont.do?"+queryString+"&searchOverHeadTank="+searchOverHeadTank+"&searchWaterTreatmentPlant="+searchWaterTreatmentPlant;
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
    function viewImage(id){
        var queryString = "overheadtank_id="+id;
        var url = "OHLevelCont1.do?" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }
    function viewImage1(id){
        var queryString = "overheadtank_id="+id;
        var url = "Temp_ohLevelCont.do?" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }

    function setCommand(id,ohlevel_id){debugger;
        var change_command_value = $("#"+id).val();
        $.ajax({
            url:"ohLevelCont.do",
            data:"action1=setCommand&ohlevel_id=" + ohlevel_id + "&change_command_value=" + change_command_value,
            success:function(response){
                if(response == 1)
                    $("#message").html("<td colspan='5' bgcolor='yellow'><b>Command is Set...</b></td>");
                else
                    $("#message").html("<td colspan='5' bgcolor='coral'><b>Command is not Set Some Error!</b></td>");
            }
        });
    }
    function viewChart(id){
        var queryString = "requestprinrt=VIEW_GRAPH&ohlevel_id="+id;
        var url = "jFreeChart.do?" + queryString;
        popupwin = openPopUp(url, "Previous History Details", 600, 600);
    }
        function viewChart2(id){
        var queryString = "task=VIEW_GRAPH2&ohlevel_id="+id;
        var url = "CanvasJSCont.do?" + queryString;
        popupwin = openPopUp(url, "Previous History Details", 1000, 700);
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
           
                    </table>
<table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
    <tr>.</tr>
    <tr>.</tr>
    <tr>.</tr>
     
    <tr>.</tr>
    <tr><marquee><h3>WELCOME   .....TO  .....DASHBOARD</h3></marquee></tr>
            <tr>
            <center><h3>*********Please... Visit... After... SomeTime ...To... View ...DashBoard**********</h3> </center>
            
                        </tr>
                    </table>
                </DIV>
            </td>
           
        </table>
    </body>
</html>