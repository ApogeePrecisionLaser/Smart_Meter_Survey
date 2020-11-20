<%--
    Document   : pipe_type
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
        <title>JSP Page</title>
    </head>
    <script type="text/javascript">
        jQuery(function(){
            $("#type_ofBend").autocomplete("typeofvalve.do", {
                extraParams: {
                    action1: function() { return "getTypeofvalve"}
                }
            });
        });
        
        function fillColumns(id)
        {
            var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
            var noOfColumns = 6;
            var columnId = id;                              
            columnId = columnId.substring(3, id.length);    
            var lowerLimit, higherLimit;
            for(var i = 0; i < noOfRowsTraversed; i++) {
                lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

                if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
            }
            setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
            var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

            document.getElementById("type_of_bendId").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
            document.getElementById("type_ofBend").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
      
            document.getElementById("remark1").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
            document.getElementById("latitude").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
            document.getElementById("longitude").value=document.getElementById(t1id +(lowerLimit+5)).innerHTML;

            for(var i = 0; i < noOfColumns; i++) {
                document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
            }

            $("#message").html("");
            makeEditable('');
        }
        function setDefaultColor(noOfRowsTraversed, noOfColumns) {
            for(var i = 0; i < noOfRowsTraversed; i++) {
                for(var j = 1; j <= noOfColumns; j++) {
                    document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "pink";
                }
            }
        }

        function makeEditable(id) {
            document.getElementById("type_ofBend").disabled = false;
            document.getElementById("bendOrder").disabled = false;
            document.getElementById("remark1").disabled = false;
            document.getElementById("longitude").disabled = false;
            document.getElementById("latitude").disabled = false;

            document.getElementById("save").disabled = true;
            document.getElementById("update").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = false;
            document.getElementById("get_cordinate").disabled = false;

            if(id == 'new')
            {
                $("#message").html("");
                document.getElementById("update").disabled = true;
                document.getElementById("delete").disabled = true;
                document.getElementById("save_As").disabled = true;
                document.getElementById("save").disabled = false;
                document.getElementById("type_of_bendId").value = '';

                //                setDefaultColor(document.getElementById("noOfRowsTraversed").value,5);
                document.getElementById("type_ofBend").disabled=false;
                document.getElementById("type_ofBend").focus();


            }
            //        if(id == 'edit'){
            //            $("#message").html("");
            //            document.getElementById("save_As").disabled = false;
            //
            //                document.getElementById("delete").disabled = false;
            //                document.getElementById("save").disabled = false;
            //            }

        }



        function setStatus(id) {

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
            var result = true;
            if(document.getElementById("clickedButton").value=="Save" ||document.getElementById("clickedButton").value == 'Save AS New' ||document.getElementById("clickedButton").value == 'Update')
            {
                var type_ofBend=document.getElementById("type_ofBend").value;
                var bendOrder=document.getElementById("bendOrder").value;
                var remark1=document.getElementById("remark1").value;
                var latitude=document.getElementById("latitude").value;
                var longitude=document.getElementById("longitude").value;
                
                if(type_ofBend==''|| type_ofBend==null)
                {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>cityname is required...</b></td>");
                    document.getElementById("type_ofBend").focus();
                    result = false;
                }
                if(bendOrder==''|| bendOrder==null)
                {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>bendOrder is required...</b></td>");
                    document.getElementById("bendOrder").focus();
                    result = false;
                }
//                if(remark1=='')
//                {
//                    $("#message").html("<td colspan='8' bgcolor='coral'><b>remark1 is required...</b></td>");
//                    document.getElementById("bendOrder").focus();
//                    result = false;
//                }
                if(result == false)    // if result has value false do nothing, so result will remain contain value false.
                {                 }
                else{ result = true;
                }
                if(document.getElementById("clickedButton").value == 'Save AS New')
                {
                    if(type_ofBend==''|| type_ofBend==null)
                    {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>name is required...</b></td>");
                        document.getElementById("type_ofBend").focus();
                        result = false;
                    }else if(bendOrder==''|| bendOrder==null){
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>bendOrder is required...</b></td>");
                        document.getElementById("bendOrder").focus();
                        result = false;
                    }
                    else if(remark1==''){
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>remark1 is required...</b></td>");
                        document.getElementById("bendOrder").focus();
                        result = false;
                    }
                    else if(latitude==''){
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>remark1 is required...</b></td>");
                        document.getElementById("bendOrder").focus();
                        result = false;
                    }else if(longitude==''){
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>remark1 is required...</b></td>");
                        document.getElementById("bendOrder").focus();
                        result = false;
                    }else{
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }}

            } else result = confirm("Are you sure you want to delete this record?");
            if(document.getElementById("type_of_bendId").value == 0)
            {
                addRow( type_ofBend,bendOrder,remark1,latitude,longitude);
                document.getElementById('form2').reset();
                document.getElementById("type_ofBend").focus();
                return false;
            }

            return result;

        }
        function addRow(data2,data3,data4,data5,data6)
        {
            var table = document.getElementById('insertTable');
            var rowCount = table.rows.length;                // alert(rowCount);
            var row = table.insertRow(rowCount);
            var cell1 = row.insertCell(0);
            table.className="content"
            cell1.innerHTML = rowCount;
            var element1 = document.createElement("input");
            element1.type = "hidden";
            element1.name = "type_of_bendId";
            element1.id = "type_of_bendId"+rowCount;
            element1.size = 1;
            element1.value = 1;
            element1.readOnly = false;
            cell1.appendChild(element1);

            var element1 = document.createElement("input");
            element1.type = "checkbox";
            element1.name = "check_print";
            element1.id = "check_print"+rowCount;                //element1.size = 1;
            element1.value = "Yes";
            element1.checked = true;
            element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
            cell1.appendChild(element1);

            var cell3 = row.insertCell(1);
            cell3.innerHTML = $.trim(data2);
            var element3 = document.createElement("input");
            element3.type = "hidden";
            element3.name = "type_ofBend";
            element3.id = "type_ofBend"+rowCount;
            element3.size = 20;
            element3.value = data2;
            cell3.appendChild(element3);

            var cell4 = row.insertCell(2);
            cell4.innerHTML = $.trim(data3);
            var element4 = document.createElement("input");
            element4.type = "hidden";
            element4.name = "bendOrder";
            element4.id = "bendOrder"+rowCount;
            element4.size = 20;
            element4.value = data3;
            cell4.appendChild(element4);


            var cell5 = row.insertCell(3);
            cell5.innerHTML = $.trim(data4);
            var element5 = document.createElement("input");
            element5.type = "hidden";
            element5.name = "remark1";
            element5.id = "remark1"+rowCount;
            element5.size = 20;
            element5.value = data4;
            cell5.appendChild(element5);

            var cell6 = row.insertCell(4);
            cell6.innerHTML = $.trim(data5);
            var element6 = document.createElement("input");
            element6.type = "hidden";
            element6.name = "latitude";
            element6.id = "latitude"+rowCount;
            element6.size = 20;
            element6.value = data5;
            cell6.appendChild(element6);

            var cell7 = row.insertCell(5);
            cell7.innerHTML = $.trim(data6);
            var element7 = document.createElement("input");
            element7.type = "hidden";
            element7.name = "longitude";
            element7.id = "longitude"+rowCount;
            element7.size = 20;
            element7.value = data6;
            cell7.appendChild(element7);

            var height = (rowCount * 25)+ 60;
            document.getElementById("autoCreateTableDiv").style.visibility = "visible";
            document.getElementById("autoCreateTableDiv").style.height = height+'px';
            $("#autoCreateTableDiv").show();
            if(rowCount == 1){
                $('#checkUncheckAll').attr('hidden', true);
            }else{
                $('#checkUncheckAll').attr('hidden', false);
            }
        }

        function deleteRow() {
            try {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                for(var i=1; i<rowCount; i++) {
                    table.deleteRow(1);
                }

                $("#autoCreateTableDiv").hide();
                document.getElementById("type_ofBend").focus();
            }catch(e) {
                alert(e);
            }
        }
        function singleCheckUncheck(id){
            // alert(document.getElementById('insertTable').rows.length);
            if ($('#check_print'+id).is(':checked')) {
                $("#type_of_bendId"+id).val("1");
            }else{
                $("#type_of_bendId"+id).val("0");
            }
            if($('form input[type=checkbox]:checked').size() == 0){
                $("#addAllRecords").attr("disabled", "disabled");
            }else{
                $("#addAllRecords").removeAttr("disabled");
            }
        }

        function checkUncheck(id)
        {
            var table = document.getElementById('insertTable');
            var rowCount = table.rows.length;
            if(id == 'Check All'){
                $('input[name=check_print]').attr("checked", true);
                for(var i=1;i<rowCount;i++){
                    $("#type_of_bendId"+i).val("1");
                }
                $('#checkUncheckAll').val('Uncheck All');
                $("#addAllRecords").removeAttr("disabled");
            }else{
                $('input[name=check_print]').attr("checked", false);
                $('#checkUncheckAll').val('Check All');
                $("#addAllRecords").attr("disabled", "disabled");
                for(var i=1;i<rowCount;i++){
                    $("#type_of_bendId"+i).val("2");
                }
            }
        }
        function openMapForCord() {
            var url="generalCont.do?task=GetCordinates";//"getCordinate";
            popupwin = openPopUp(url, "",  600, 630);
        }
        function viewMap(longitude, lattitude)
        {
            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
            var y = longitude;//$.trim(doc.getElementById(logitude).value);
            var url="generalCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
            popupwin = openPopUp(url, "",  580, 620);
        }
        function openPopUp(url, window_name, popup_height, popup_width) {
            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

            return window.open(url, window_name, window_features);
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
                    <div class="maindiv" id="body"  align="center">
                        <table>
                               <tr>
                                    <td>
                                        <table align="center">
                                            <tr>
                                                <td align="center" class="header_table" width="90%"><b> Type Of Valve</b></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            <tr>
                                <td align="center">
                                    <div class="content_div" style="width:990px">
                                        <form name="form1" action="typeofbend.do">
                                            <TABLE BORDER="1" align="center" cellpadding="5" class="content">
                                                <TR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <th class="heading">S.no&nbsp;</th>
                                                    <TH class="heading">Type Of Valve&nbsp;</TH>
                             
                                                    <th class="heading">Remark&nbsp;</th>
                                                    <th class="heading">Latitude&nbsp;</th>
                                                    <th class="heading">Longitude&nbsp;</th>
                                                    <th class="heading">Map&nbsp;</th>
                                                </TR>
                                                <c:forEach var="CitystatusBean" items="${requestScope['list']}" varStatus="loopCounter">
                                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                        <TD id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${CitystatusBean.type_of_bendId}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed }</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${CitystatusBean.type_ofBend}</TD>
                                                  
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${CitystatusBean.remark1}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${CitystatusBean.latitude}</TD>
                                                        <TD id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${CitystatusBean.longitude}</TD>
                                                        <td><input type="button" value="View Map" onclick="viewMap(${CitystatusBean.longitude}, ${CitystatusBean.latitude})"></td>
                                                    </TR>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="7">
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
                                            <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <%--                                            <input type="hidden"  name="searchcity" value="${searchcity}" >--%>
                                        </form>
                                    </div>
                                    <DIV id="autoCreateTableDiv" STYLE="visibility:hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="typeofbend.do" method="POST" >
                                            <table id="parentTable"  border="1"  align="center" cellpadding="5" class="content">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" border="1" align="center" width="100%">
                                                            <tr>
                                                                <TH class="heading" style="width: 50px">S.no.</TH>
                                                                <th class="heading">Type Of Bend</th>
                                                             
                                                                <th class="heading">Remark</th>
                                                                <th class="heading">Latitude&nbsp;</th>
                                                                <th class="heading">Longitude&nbsp;</th>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                        <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </DIV>
                                    <div  style="width:990px" >
                                        <form  id="form2" action="typeofbend.do" onsubmit="return verify()">

                                            <table BORDER="1" align="center" cellpadding="5" class="content" >
                                                <tr id="message">

                                                </tr>
                                                <tr>
                                                    <td>Type of Valve</td>
                                                    <td>
                                                        <input class="input" type="hidden" id="type_of_bendId" name="type_of_bendId" value="" >
                                                        <input type="text" id="type_ofBend" name="type_ofBend" disabled> </td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Latitude</th><td><input  type="text" id="latitude" name="latitude" value="" size="20" disabled/></td>
                                                    <th class="heading1">Longitude</th>
                                                    <td>
                                                        <input type="text" id="longitude" name="longitude" value="" size="20" disabled/>
                                                        <input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled>
                                                        <input type="hidden" id="cordinate_change" value="">
                                                    </td>
                                                </tr>
                                                <tr>

                                                </tr>
                                                <tr>
                                                    <td>Remark</td>
                                                    <td><input type="text" id="remark1" name="remark1" disabled></td>
                                                </tr>
                                                <tr align="center"><td colspan="5" >
                                                        <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    </td></tr>
                                            </table>
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
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