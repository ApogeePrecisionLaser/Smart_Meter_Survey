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
        //        $("#search").autocomplete("PipeDetailCont.do", {
        //            extraParams: {
        //                action1: function() { return "getPipeType" }
        //            }
        //        });
    });


    /*    function setStatus(id) {
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


    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id)
    {
        var noOfRowsTraversed = $("#noOfRowsTraversed").val();
        var noOfColumns = 14;
        var columnId = id;                              
        columnId = columnId.substring(3, id.length);   
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        $("#pipe_detail_id").val(document.getElementById(t1id + (lowerLimit + 0)).innerHTML);
        $("#node_name").val(document.getElementById(t1id + (lowerLimit + 2)).innerHTML);
        $("#head_latitude").val(document.getElementById(t1id + (lowerLimit + 3)).innerHTML);
        $("#head_longitude").val(document.getElementById(t1id + (lowerLimit + 4)).innerHTML);
        $("#tail_latitude").val(document.getElementById(t1id + (lowerLimit + 5)).innerHTML);
        $("#tail_longitude").val(document.getElementById(t1id + (lowerLimit + 6)).innerHTML);
        $("#diameter").val(document.getElementById(t1id + (lowerLimit + 7)).innerHTML);
        $("#diameter_unit").val(document.getElementById(t1id + (lowerLimit + 8)).innerHTML);
        $("#length").val(document.getElementById(t1id + (lowerLimit + 9)).innerHTML);
        $("#length_unit").val(document.getElementById(t1id + (lowerLimit + 10)).innerHTML);
        $("#remark").val(document.getElementById(t1id + (lowerLimit + 11)).innerHTML);
        $("#pipe_type").val(document.getElementById(t1id + (lowerLimit + 12)).innerHTML);
        $("#pipe_name").val(document.getElementById(t1id + (lowerLimit + 13)).innerHTML);

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
        var search_range = $("#search_range").val();
        var url = "PipeDetailCont.do?"+queryString+"&search_range="+search_range;
        popupwin = openPopUp(url, "Overhead Tank Level", 500, 1000);
    }
     */
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    /* function makeEditable(id)
    {
        document.getElementById("pipe_detail_id").disabled = false;
        document.getElementById("node_name").disabled = false;
        document.getElementById("pipe_type").disabled = false;
        document.getElementById("head_latitude").disabled = false;
        document.getElementById("head_longitude").disabled = false;
        document.getElementById("tail_latitude").disabled = false;
        document.getElementById("tail_longitude").disabled = false;
        document.getElementById("diameter").disabled = false;
        document.getElementById("diameter_unit").disabled = false;
        document.getElementById("pipe_name").disabled = false;
        document.getElementById("length").disabled = false;
        document.getElementById("length_unit").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save_As").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = true;
        if(id == 'new')
        {
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
     */
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
    /*    function openMapForCord(pipe_detail_id,head_latitude,head_longitude,tail_latitude,tail_longitude)
    {
        var url="generalCont.do?task=GetBendCordinates&pipe_detail_id="+pipe_detail_id+"&head_latitude="+head_latitude+"&head_longitude="+head_longitude+"&tail_latitude="+tail_latitude+"&tail_longitude="+tail_longitude;
        popupwin = openPopUp(url, "",  600, 630);
    }*/
    function openMapForCord1(latitude1,longitude1,latitude2,longitude2)
    {
        var url="generalCont.do?task=polyline&latitude1="+latitude1+"&longitude1="+longitude1+"&latitude2="+latitude2+"&longitude2="+longitude2;
        popupwin = openPopUp(url, "",  600, 630);
    } 
    function openMapForCord()
    {
        var url="generalCont.do?task=currentCordinate";
        popupwin = openPopUp(url, "",  580, 620);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nearest Search</title>
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
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b>Nearest Search</b></td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr><td>
                                <div align="center">
                                    <form name="form0" method="POST" action="nearestMap.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="7" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <td>
                                                    Latitude<input  type="text" id="latitude" name="latitude" value="${latitude}" size="20" />
                                                </td>
                                                <td>
                                                    Longitude<input type="text" id="longitude" name="longitude" value="${longitude}" size="20" />
                                                </td>
                                                <td>
                                                    <input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Range&nbsp;&nbsp;
                                                    <input class="input" type="text" id="search_range" name="search_range" value="${search_range}" size="10" >KM</td>
                                                <td>
                                                    Search By
                                                    <select name="search" id="search" value="${search}">
                                                        <option value="">Select</option>
                                                        <option value="OverheadTank">Overhead Tank</option>
                                                        <option value="WaterPlant">Water Treatment Plant</option>
                                                        <option value="ConnectionDetail">Connection Detail</option>
                                                    </select>
                                                </td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                    <%--                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                                                                    <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="displayList()"></td>
                                                                                                    <td><input class="button" type="button" id="completeMap" value="Map" onclick="openMapForCord1()"/></td>--%>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="nearestMap.do">
                                    <DIV class="content_div">
                                        <c:if test="${search eq 'OverheadTank'}">
                                            <table id="table1" width="800"  border="1"  align="center" class="content">
                                                <tr>
                                                    <th  class="heading">S.No.</th>
                                                    <th  class="heading">Water Treatment Plant</th>
                                                    <th  class="heading">Overhead Tank</th>
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
                                                        <td>
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMapForCord1('${latitude}','${longitude}','${overHeadTankBean.latitude}' , '${overHeadTankBean.longitude}');"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:if>
                                        <c:if test="${search eq 'ConnectionDetail'}">
                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content" >
                                                <TR>
                                                    <th class="heading">S.no.</th>
                                                    <th class="heading">Person name</th>
                                                    <th class="heading">Meter No.</th>
                                                    <th class="heading">Meter Reading</th>
                                                    <th class="heading">Date & Time</th>
                                                    <th class="heading">Remark</th>
                                                    <th class="heading">Water Service No</th>
                                                    <th class="heading">Property Service No</th>
                                                    <th class="heading">Node</th>
                                                    <th class="heading">View Map</th>
                                                </TR>
                                                <c:forEach var="key_person_name" items="${requestScope['list']}" varStatus="loopCounter">
                                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${key_person_name.meter_detail_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.key_person_name}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${key_person_name.meter_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.meter_reading}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.date_time}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.remark}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.water_service_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.property_service_no}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${key_person_name.node}</TD>
                                                        <td>
                                                            <input type="hidden"  id="lat${key_person_name.meter_detail_id}" value="${key_person_name.latitude}">
                                                            <input type="hidden"  id="long${key_person_name.meter_detail_id}" value="${key_person_name.longitude}">
                                                            
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMapForCord1('${latitude}','${longitude}','${key_person_name.latitude}' , '${key_person_name.longitude}');"/>

                                                        </td>
                                                </c:forEach>
                                            </TABLE>
                                        </c:if>
                                        <c:if test="${search eq 'WaterPlant'}">
                                            <table id="table1" width="800"  border="1"  align="center" class="content">
                                                <tr >
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Water Treatment Plant Name</th>
                                                    <th  class="heading">Capacity MLD</th>
                                                    <th  class="heading">City</th>
                                                    <th  class="heading">Address1</th>
                                                    <th  class="heading">Address2</th>
                                                    <th  class="heading">Date & Time</th>
                                                    <th  class="heading">Latitude</th>
                                                    <th  class="heading">Longitude</th>
                                                    <th  class="heading">Remark</th>
                                                    <th  class="heading"></th>

                                                </tr>
                                                <c:forEach var="waterTreatmentPlantBean" items="${requestScope['list']}"  varStatus="loopCounter">
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
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMapForCord1('${latitude}','${longitude}','${waterTreatmentPlantBean.latitude}' , '${waterTreatmentPlantBean.longitude}');"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:if>
                                    </DIV>
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
