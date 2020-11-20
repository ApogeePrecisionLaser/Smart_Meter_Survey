<%-- 
    Document   : CanvasJs2
    Created on : Feb 19, 2018, 1:43:14 PM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
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
        <title>JSP Page</title>
        <script>
            jQuery(function(){
                $("#level_datetime").datepicker({

//            minDate: -200,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });



            });





            window.onload = function () {
                var dataPoints1 = [];
                var dataPoints2 = [];
                var energyMeterdataPoints3 = [];

                var datetime;
                var ohlevel;
                var dateTime1=[];
                var OhLevel1=[];
                var dateTime2=[];
                var OhLevel2=[];
                //////////////////////////
                var sumpwellDateTime;
                var sumpwellOhLevel;
                var sumpwellDateTime1=[];
                var sumpwellOhLevel1=[];

                var energyMeterDateTime;
                var energyMeterData;
                var energyMeterDateTimeArray=[];
                var energyMeterDataArray=[];

                //var a=[];
                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllDateTime",
                    success: function(response_data) {
                        datetime=response_data.dateTime;

                        for(var i=0;i<datetime.length;i++){
                            var d=datetime[i]["date_time"];
                 
                            var a=d.split(",");


                            dateTime1[i]=new Date(a[0], a[1], a[2], a[3], a[4]);
                            
                       //     alert(dateTime1[i]);
                 
                        }
       
                    }
                });

                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllOhLevel",
                    //data:{data: vkp_id},
                    success: function(response_data1) {
                        ohlevel=response_data1.ohLevel;
                        for(var i=0;i<ohlevel.length;i++){
                            OhLevel1[i]=ohlevel[i]["remark"];
                            //alert(ohlevel[i]["remark"]);
                        }

                    }
                });

//////////////////////////////////
              $.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllSumpwellDateTime",
                    success: function(response_data) {
                        sumpwellDateTime=response_data.dateTime;
                        

                    try{
                        for(var i=0;i<sumpwellDateTime.length;i++){
                            var d=sumpwellDateTime[i]["date_time"];
                            

                            var a=d.split(",");


                            sumpwellDateTime1[i]=new Date(a[0], a[1], a[2], a[3], a[4]);

                        }
                    }catch(err){
                       // alert(err.message);
                    }

                    }
                });

                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllSumpwellOhLevel",
                    //data:{data: vkp_id},
                    success: function(response_data1) {
                        sumpwellOhLevel=response_data1.ohLevel;
                        for(var i=0;i<sumpwellOhLevel.length;i++){
                            sumpwellOhLevel1[i]=sumpwellOhLevel[i]["remark"];
                            //alert(ohlevel[i]["remark"]);
                        }

                    }
                });
//////////////////////////////////////////////////
////energyMeterData///////////////////////////////////////////////////////
$.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllEnergyMeterDateTime",
                    success: function(response_data) {
                        energyMeterDateTime=response_data.dateTime;

                        for(var i=0;i<energyMeterDateTime.length;i++){
                            var d=energyMeterDateTime[i]["date_time"];

                            var a=d.split(",");


                            energyMeterDateTimeArray[i]=new Date(a[0], a[1], a[2], a[3], a[4]);

                        }

                    }
                });

                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "CanvasJSCont.do?task=getAllEnergyMeterData",
                    //data:{data: vkp_id},
                    success: function(response_data1) {
                        energyMeterData=response_data1.ohLevel;
                        for(var i=0;i<energyMeterData.length;i++){
                            energyMeterDataArray[i]=energyMeterData[i]["remark"];
                            //alert(ohlevel[i]["remark"]);
                        }

                    }
                });


//////////////////////////////////////////////////////////




                var chart = new CanvasJS.Chart("chartContainer", {
                    zoomEnabled: true,
                    title: {
                        text: "Overhead Tank & Energy Meter Level"
                    },
//                    axisX: {
//                        title: "chart updates in every 2 minutes"
//                    },
                    axisY:{
		
                        title: "ohLevel & Energy Meter",
                        yValueFormatString: "###",
                        includeZero: false,
                        interval: 100
                    },
                    toolTip: {
                        shared: true
                    },
                    legend: {
                        cursor:"pointer",
                        verticalAlign: "top",
                        fontSize: 22,
                        fontColor: "dimGrey",
                        itemclick : toggleDataSeries
                    },
                    data: [{
                            type: "line",
                            xValueType: "dateTime",
                            yValueFormatString: "###",
                            xValueFormatString: "hh:mm:ss TT",
                            showInLegend: true,
                            name: " Overhead Tank Water Level ",
                            dataPoints: dataPoints1
                        },
                        {
                          type: "line",
                            xValueType: "dateTime",
                            yValueFormatString: "###",
                            xValueFormatString: "hh:mm:ss TT",
                            showInLegend: true,
                            name: " Sump Well Water Level ",
                            dataPoints: dataPoints2
                        },{
                          type: "line",
                            xValueType: "dateTime",
                            yValueFormatString: "###",
                            xValueFormatString: "hh:mm:ss TT",
                            showInLegend: true,
                            name: " Energy Meter Data ",
                            dataPoints: energyMeterdataPoints3
                        }]

                });
                function toggleDataSeries(e) {
                    if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                        e.dataSeries.visible = false;
                    }
                    else {
                        e.dataSeries.visible = true;
                    }
                    chart.render();
                }
                var updateInterval = 120000;

                var yValue1 = 600;
                var time =dateTime1[0];


                function updateChart(count) {
                    count = count || 1;
                    try{
                    for (var i = 0; i < count; i++) {
                       
                        time.setTime(dateTime1[i].getTime());
                        yValue1 = Math.round((OhLevel1[i])*100)/100;
                        
                        dataPoints1.push({
                            x: time.getTime(),
                            y: yValue1
                        });
                    }
                }
                catch(err){
                    
                }
                    // updating legend text with  updated with y Value
                    chart.options.data[0].legendText = " Current Overhead Tank Level  " + yValue1;
                    chart.render();
                }
                function updateSumpwellChart(count) {
                    count = count || 1;
                    try{
                    for (var i = 0; i < count; i++) {
                        time.setTime(sumpwellDateTime1[i].getTime());
                        yValue1 = Math.round((sumpwellOhLevel1[i])*100)/100;
                        dataPoints2.push({
                            x: time.getTime(),
                            y: yValue1
                        });
                    }
                }
                catch(err){
                    //alert(err.message);
                }
                    // updating legend text with  updated with y Value
                    chart.options.data[1].legendText = " Current Sump Well Level  " + yValue1;
                    chart.render();
                }
                //////////////energyMeter/////////////////////
                function updateEnergyMeterChart(count) {
                    count = count || 1;
                    try{
                    for (var i = 0; i < count; i++) {
                        time.setTime(energyMeterDateTimeArray[i].getTime());
                        yValue1 = Math.round((energyMeterDataArray[i])*100)/100;
                        energyMeterdataPoints3.push({
                            x: time.getTime(),
                            y: yValue1
                        });
                    }
                }
                    catch(err){
                        
                    }
                    // updating legend text with  updated with y Value
                    chart.options.data[2].legendText = " Current Energy  Level  " + yValue1;
                    chart.render();
                }

                ////////////////////////////////////////////////////////////////////////////////////////

                function updateChart1() {


                    $.ajax({
                        dataType: "json",
                        async : false,
                        url: "CanvasJSCont.do?task=getNewDateTime",
                        success: function(response_data) {
                            datetime=response_data.dateTime;

                            for(var i=0;i<datetime.length;i++){
                                var d=datetime[i]["date_time"];

                                var a=d.split(",");


                                dateTime2[i]=new Date(a[0], a[1], a[2], a[3], a[4]);

                            }
                            //alert(datetime.length);
                            //alert(dateTime2);
                        }
                    });

                    $.ajax({
                        dataType: "json",
                        async : false,
                        url: "CanvasJSCont.do?task=getNewOhLevel",
                        //data:{data: vkp_id},
                        success: function(response_data1) {
                            ohlevel=response_data1.ohLevel;
                            for(var i=0;i<ohlevel.length;i++){
                                OhLevel2[i]=ohlevel[i]["remark"];
                                //alert(ohlevel[i]["remark"]);
                            }

                        }
                    });



                    var deltaY1, deltaY2;
                    for (var i = 0; i < datetime.length; i++) {
                        //		time.setTime(time.getTime()+ updateInterval);
                        time.setTime(dateTime2[i].getTime()+updateInterval);
                        yValue1 = Math.round((OhLevel2[i])*100)/100;
                        dataPoints1.push({
                            x: time.getTime(),
                            y: yValue1
                        });
                    }
                    // updating legend text with  updated with y Value
                    chart.options.data[0].legendText = " Current Water Level  " + yValue1;
                    chart.render();
                }
                // generates first set of dataPoints
                updateChart(OhLevel1.length);
                updateSumpwellChart(sumpwellOhLevel1.length);
                updateEnergyMeterChart(energyMeterDataArray.length);
                //setInterval(function(){updateChart1()}, updateInterval);
            }
        </script>
    </head>
    <body>

        <div id="chartContainer" style="height: 500px; width: 100%;"></div>
        <div id="search_div" style="margin-top:35px; margin-left:60px;">
            <form action="CanvasJSCont.do" method="post">
         <table>
             <tr>
         <td>Select Date</td>
         <td><input class="input" type="text" data-format="yyyy-MM-dd" id="level_datetime" name="level_datetime" value="" size="12"></td>
<!--                                                <th>Overhead Tank</th>
                                                <td><input class="input" type="text" id="searchOverHeadTank" name="searchOverHeadTank" value="" size="20" ></td>-->
                                                <td><input class="button" type="submit" name="task" id="searchIn"  value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showCurrentRecords"  value="Show Current Records"></td>
             </tr>
         </table>
            </form>
        </div>
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    </body>
</html>
