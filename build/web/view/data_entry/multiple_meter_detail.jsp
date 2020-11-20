

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Multiple Connection Detail</title>
        <script type="text/javascript">
            var focused_name = "";
            jQuery(function(){
       
                $("#date_time").datepicker({
                    //minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'yy-mm-dd',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $(document).keypress(function(e) {
                    if(e.which == 13) {
                        var name = document.activeElement.id;
                        focused_name = name;
                        if(name == "key_person_name")
                            $("#mobile_no").focus();
                        else if(name == "mobile_no")
                            $("#meter_no").focus();
                        else if(name == "meter_no"){
                            $("#meter_reading").focus();}
                        else if(name == "meter_reading")
                            $("#date_time").focus();
                        else if(name == "date_time")
                            $("#remark").focus();
                        else if(name == "remark")
                            $("#water_service_no").focus();
                        else if(name == "water_service_no")
                            $("#property_service_no").focus();
                    }
                });

                $(document).ready(function() {
                    makeEditable('new');
                });


            });


            function fillColumns(id)
            {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 10;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1       ). --%>
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

                document.getElementById("meter_detail_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("key_person_name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("emp_code").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("item_name").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
                document.getElementById("issue_weight").value=document.getElementById(t1id +(lowerLimit+5)).innerHTML;
                document.getElementById("return_weight").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
                document.getElementById("less_weight").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
                document.getElementById("quality_missmatch_weight").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
                document.getElementById("date_time").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
          

                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }

                document.getElementById("edit").disabled = false;

                if(!document.getElementById("save").disabled)
                {
                    document.getElementById("save_as_new").disabled = true;
                    document.getElementById("delete").disabled = false;
                    dodument.getElementById("save").disabled=true;

                }

                $("#message").html("");

                function setDefaultColor(noOfRowsTraversed, noOfColumns) {

                    for(var i = 0; i < noOfRowsTraversed; i++) {

                        for(var j = 1; j <= noOfColumns; j++) {

                            document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.

                        }
                    }
                }
            }
            function makeEditable(id) {

                document.getElementById("key_person_name").disabled = false;
                document.getElementById("mobile_no").disabled = false;
                document.getElementById("meter_no").disabled = false;
                document.getElementById("meter_reading").disabled = false;
                document.getElementById("date_time").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("water_service_no").disabled = false;
                document.getElementById("property_service_no").disabled = false;
                document.getElementById("save").disabled = true;

                if(id == 'new') {
                    //$("#message").html("");
                    //                    document.getElementById("edit").disabled = true;
                    //                    document.getElementById("delete").disabled = true;
                    //                    document.getElementById("save_as").disabled =true;
                    document.getElementById("save").disabled =false;
                    document.getElementById("meter_detail_id").value=0;
                    $("#meter_reading").val(0);
                    var key_person_name=document.getElementById("key_person_name").value;
                    if(key_person_name.length==0)
                        document.getElementById("key_person_name").focus();
                }
                if(id == 'edit'){
                    $("#message").html("");
                    document.getElementById("save_as").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = false;

                }

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
            function isNumeric(id) {
                var strValidChars = "0123456789.";
                var strChar;
                var blnResult = true;
                var i;
                var strString = $('#'+id).val();
                for (i = 0; i < strString.length && blnResult == true; i++)
                {
                    strChar = strString.charAt(i);
                    if (strValidChars.indexOf(strChar) == -1)
                    {
                        blnResult = false;
                        $('#'+id).val('');
                        alert("Weight Should Be Numeric");

                    }
                }
                return blnResult;
            }
            function verify() {
                var result = false;

                var clickedButton = document.getElementById("clickedButton").value;
                if(clickedButton == 'save' || clickedButton == 'Save AS New') {
                 
                    var key_person_name=document.getElementById("key_person_name").value;
                    var mobile_no = document.getElementById("mobile_no").value;
                    var meter_no=document.getElementById("meter_no").value;
                    var meter_reading=document.getElementById("meter_reading").value;
                    var date_time=document.getElementById("date_time").value;
                    var remark=document.getElementById("remark").value;
                    var water_service_no=document.getElementById("water_service_no").value;
                    var property_service_no=document.getElementById("property_service_no").value;
                    if(myLeftTrim(key_person_name).length == 0) {
                        $("#message").html("<td colspan='6' bgcolor='coral'><b> key_person_name is required...</b></td>");
                        document.getElementById("key_person_name").focus();
                        return false;
                    }

                    if(focused_name == "property_service_no"){
                        if(document.getElementById("meter_detail_id").value == 0){
                            addRow(key_person_name,mobile_no,meter_no,meter_reading,date_time,remark,water_service_no,property_service_no);
                            document.getElementById('issue_item').reset();
                            $("#date_time").val(date_time);
                            $("#meter_reading").val(0);
                            document.getElementById("key_person_name").focus();
   
                            return false;
                        }
                    }

                    return result;
                }
            }

            function setStatus(id) {

                if(id == 'save'){

                    document.getElementById("clickedButton").value = "save";
                }
                else if(id == 'save_as'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'delete'){
                    document.getElementById("clickedButton").value = "Delete";
                }


                else
                {}
            }

            function addRow(data2, data3,data4,data5, data6,data7,data8,data9) {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "meter_detail_id";
                element1.id = "meter_detail_id"+rowCount;
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
                var element9 = document.createElement("input");
                element9.type = "hidden";
                element9.name = "key_person_name";
                element9.id = "key_person_name"+rowCount;
                element9.size = 20;
                element9.value = data2;
                cell3.appendChild(element9);

                var cell4 = row.insertCell(2);
                cell4.innerHTML = $.trim(data3);
                var element3 = document.createElement("input");
                element3.type = "hidden";
                element3.name = "mobile_no";
                element3.id = "mobile_no"+rowCount;
                element3.size = 20;
                element3.value = data3;
                cell4.appendChild(element3);

                var cell5 = row.insertCell(3);
                cell5.innerHTML = $.trim(data4);
                var element4 = document.createElement("input");
                element4.type = "hidden";
                element4.name = "meter_no";
                element4.id = "meter_no"+rowCount;
                element4.size = 20;
                element4.value = data4;
                cell5.appendChild(element4);


                var cell6 = row.insertCell(4);
                cell6.innerHTML = $.trim(data5);
                var element5 = document.createElement("input");
                element5.type = "hidden";
                element5.name = "meter_reading";
                element5.id = "meter_reading"+rowCount;
                element5.size = 20;
                element5.value = data5;
                cell6.appendChild(element5);

                var cell7 = row.insertCell(5);
                cell7.innerHTML = $.trim(data6);
                var element6 = document.createElement("input");
                element6.type = "hidden";
                element6.name = "date_time";
                element6.id = "date_time"+rowCount;
                element6.size = 20;
                element6.value = data6;
                cell7.appendChild(element6);

                var cell8 = row.insertCell(6);
                cell8.innerHTML = $.trim(data7);
                var element7 = document.createElement("input");
                element7.type = "hidden";
                element7.name = "remark";
                element7.id = "remark"+rowCount;
                element7.size = 20;
                element7.value = data7;
                cell8.appendChild(element7);

                var cell9 = row.insertCell(7);
                cell9.innerHTML = $.trim(data8);
                var element8 = document.createElement("input");
                element8.type = "hidden";
                element8.name = "water_service_no";
                element8.id = "water_service_no"+rowCount;
                element8.size = 20;
                element8.value = data8;
                cell9.appendChild(element8);

                var cell10 = row.insertCell(8);
                cell10.innerHTML = $.trim(data9);
                var element10 = document.createElement("input");
                element10.type = "hidden";
                element10.name = "property_service_no";
                element10.id = "property_service_no"+rowCount;
                element10.size = 20;
                element10.value = data9;
                cell10.appendChild(element10);

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
                    document.getElementById("key_person_name").focus();
                }catch(e) {
                    alert(e);
                }
            }
            function singleCheckUncheck(id){          
                if ($('#check_print'+id).is(':checked')) {
                    $("#meter_detail_id"+id).val("1");
                }else{
                    $("#meter_detail_id"+id).val("0");
                }

                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }
            }

            function checkUncheck(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#meter_detail_id"+i).val("1");
                    }

                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#meter_detail_id"+i).val("2");
                    }

                }
            }

            function isUniqueMob1(){
                var mobile_no1 = document.getElementById("mobile_no").value;
                $.ajax({url: "MultipleMeterDetailCont.do",
                    data: "action1=checkmobile_no1&mobile_no1="+mobile_no1,
                    success: function(response_data) {
                        if(response_data == 0){
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Mobile No. 1st already exists</b></td>");
                            //document.getElementById("SendOTP").disabled = true;
                            document.getElementById("mobile_no").focus();
                            $("#mobile_no").val('');
                            return false;
                        } else{
                            $("#message").html("");
                            // document.getElementById("SendOTP").disabled = false;
                            return true;
                        }
                    }
                });
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
                        }else{
                            document.getElementById("msg").innerHTML="";
                        }

                    }

                });
        }
        </script>

    </head>
    <body >
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv" align="center" >
                        <table width="100%" align="center">
                            <tr><td>
                                    <table align="center">
                                        <tr>
                                            <td align="center" class="header_table" width="90%"><b>Multiple Connection Detail</b></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <tr><td align="center">
                                    <div>
                                        <form  action="" method="post" id="issue_item" onsubmit="return verify();">
                                            <table  align="center" class="content" border="1">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <span id="msg" style="color: red">

                                                </span>
                                                <tr><input class="input" type="hidden" id="meter_detail_id" name="meter_detail_id" value="" ></tr>

                                                <tr>

                                                    <th class="heading1">Person Name </th>
                                                    <td>  <input type="text"  id="key_person_name"  name="key_person_name" value="" disabled required>
                                                    </td>
                                                    <th class="heading1">Mobile No</th>
                                                    <td><input type="text"  id="mobile_no" name="mobile_no" value="" disabled required onblur="isUniqueMob1()"></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Meter No.</th>
                                                    <td>
                                                        <input type="text" id="meter_no" name="meter_no" value="" onblur="checkMeter_no()" disabled required>
                                                    </td>
                                                    <th class="heading1">Meter Reading</th>
                                                    <td>
                                                        <input type="text" id="meter_reading" name="meter_reading" value="" disabled required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Date</th>
                                                    <td><input type="text"  id="date_time" name="date_time" value="${current_date}" disabled required></td>
                                                    <th class="heading1">Address</th>
                                                    <td><input type="text" id="remark" name="remark" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Water Service No</th>
                                                    <td><input type="text" id="water_service_no" name="water_service_no" value="" disabled></td>
                                                    <th class="heading1">Property Service No</th>
                                                    <td><input type="text" id="property_service_no" name="property_service_no" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="4" >
                                                        <!--                                                        <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >-->
                                                        <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
                                                        <!--                                                        <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
                                                                                                                <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>-->
                                                        <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    </td>
                                                </tr>
                                            </table>
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" name="current_date" value="${current_date}" id="current_date">
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv" STYLE="visibility:hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="MultipleMeterDetailCont.do" method="POST" >
                                            <table id="parentTable"  border="1"  align="center" width="500" style="border-collapse: collapse;" class="content">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" border="1" align="center" style="border-collapse: collapse;" width="100%">
                                                            <tr class="heading">
                                                                <th  style="width: 50px">S.no.</th>
                                                                <th>Person Name</th>
                                                                <th>Mobile No</th>
                                                                <th>Meter No</th>
                                                                <th>Meter Reading</th>
                                                                <th>Date</th>
                                                                <th>Remark</th>
                                                                <th class="heading">water_service_no</th>
                                                                <th class="heading">property_service_no</th>

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
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
