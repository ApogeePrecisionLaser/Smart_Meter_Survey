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
<script type="text/javascript" language="javascript"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <script type="text/javascript" language="javascript">


            jQuery(function(){
                $("#person_name").autocomplete("OverheadTankKeyPersonCont.do", {
                    extraParams: {
                        action1: function() { return "getPersonName"}
                    }
                });
                $("#overhead_tank").autocomplete("OverheadTankKeyPersonCont.do", {
                    extraParams: {
                        action1: function() { return "getOverheadTankName"}
                    }
                });

         $("#search_person_name").autocomplete("OverheadTankKeyPersonCont.do", {
                    extraParams: {
                        action1: function() { return "getPersonName"}
                    }
                });
                $("#search_overheadtank_name").autocomplete("OverheadTankKeyPersonCont.do", {
                    extraParams: {
                        action1: function() { return "getOverheadTankName"}
                    }
                });
            });


             function fillColumns(id)
            {

                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 4;
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

                document.getElementById("overhead_tank_keyperson_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("person_name").value=document.getElementById(t1id +(lowerLimit+2)).innerHTML;
                document.getElementById("overhead_tank").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;

//                var date=document.getElementById(t1id +(lowerLimit+10)).innerHTML;
//                var date_time=date.split(" ")[0];
//                var date_time1=date_time.split("-")[2]+"-"+date_time.split("-")[1]+"-"+date_time.split("-")[0];
//                document.getElementById("date_time").value=date_time1;
//                document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
//
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }

                document.getElementById("edit").disabled = false;

                if(!document.getElementById("save").disabled)
                {
                   // document.getElementById("save_as_new").disabled = true;
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
                document.getElementById("person_name").disabled = false;
                document.getElementById("overhead_tank").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message

                    $("#message").html("");
                    //document.getElementById("client_id").value = "";

                    document.getElementById("person_name").disabled=false;
                    document.getElementById("overhead_tank").disabled=false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("update").disabled = true;
                    //setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("person_name").focus();
                }
                if(id == 'edit') {
                    document.getElementById("update").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }

            function getOverheadTank_KeyPersonList(){
                var searchpersonname = $("#search_person_name").val();
                var searchoverheadtankname = $("#search_overheadtank_name").val();
               // var searchemail = $("#searchemail").val();

                var queryString = "task=generateHSReport&searchpersonname="+searchpersonname+"&searchoverheadtankname="+searchoverheadtankname;
                var url = "OverheadTankKeyPersonCont.do?" + queryString;
                popupwin = openPopUp(url, "NameStatus List", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }





        </script>
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

                         <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="OverheadTankKeyPersonCont.do" onsubmit="return searchCheck()">
                                        <table align="center" class="heading1" >
                                            <tr><td colspan="11" align="center" style="color: red;" id="searchMessage"></td></tr>
                                            <tr>
                                                <th>Person name</th>
                                                <td><input class="input" type="text" id="search_person_name" name="search_person_name" value="${searchPersonName}" size="30" ></td>

                                                <th>Overheadtank name</th>
                                                <td><input class="input" type="text" id="search_overheadtank_name" name="search_overheadtank_name" value="${searchOverheadtankName}" size="30" ></td>


                                                <td><input class="button" type="submit" name="task" id="searchIn" onclick="setStatus(id)" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" onclick="setStatus(id)" value="Show All Records"></td>
                                                <td><input type="button" class="button" id="viewPdf" name="viewPdf" value="PDF File" onclick="getOverheadTank_KeyPersonList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                       


                        <tr>
                            <td>
                        <tr align="center">
                            <td >
                                <form name="form1" method="post" action="OverheadTankKeyPersonCont.do">
                                    <div class="content_div">
                                        <TABLE BORDER="1" align="center" cellpadding="5%" width="40%" height="100%" class="content">


                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <th class="heading">S.no&nbsp;</th>
                                            <TH class="heading">Person Name&nbsp;</TH>
                                            <th class="heading">Overhead Tank&nbsp;</th>
                                            <c:forEach var="item" items="${requestScope['List']}" varStatus="loopCounter">
                                                <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                                <tr>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${item.overheadtank_keyperson_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${lowerLimit + loopCounter.count- noOfRowsTraversed}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.key_person_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${item.name}</td>
                                                </tr>
                                                </TR>
                                            </c:forEach>
                                            <tr> <td align='center' colspan="4">
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
                                    </div>
                                    <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                    <input type="hidden"  id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                </form>
                            </td>
                        </tr>
                        <tr align="center">
                            <td>
                                <form name="form2" method="post" action="OverheadTankKeyPersonCont.do" onsubmit="return verify()">
                                    <table class="content" border="0" id="table2" align="center" width="20%">
                                        <tr id="message">
                                            <c:if test="${not empty message}">
                                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                            </c:if>
                                        </tr>
                                        <tr>
                                            <th class="heading1">Person Name</th><td>
                                                <input class="input new_input" type="hidden" id="overhead_tank_keyperson_id" name="overhead_tank_keyperson_id" value="" >
                                                <input class="input new_input" type="text" id="person_name" name="person_name" size="60" value=""   disabled maxlength="15">
                                            </td>
                                        </tr>
                                        <tr>

                                            <th class="heading1">Overhead Tank</th><td><input class="input new_input" type="text" id="overhead_tank" name="overhead_tank" size="60" value="" disabled></td>
                                        </tr>
                                        <tr>
                                            <td align='center' colspan="2" >
                                                <input type="button" class="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                <input type="submit" class="button" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                <input type="submit" class="button" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                <input type="submit" class="button" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                <input type="reset" class="button" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                            </td>
                                        </tr>

                                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        <input type="hidden" id="clickedButton" value="">

                                    </table>
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
