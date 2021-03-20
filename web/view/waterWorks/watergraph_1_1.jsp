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
 
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


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
                    url: "MobileGraph?task=getAllDateTime",
                    success: function(response_data) {
                        datetime=response_data.dateTime;

                        for(var i=0;i<datetime.length;i++){
                            var d=datetime[i]["date_time"];
                 
                            var a=d.split(",");


                            dateTime1[i]=new Date(a[0], a[1], a[2], a[3], a[4]);
                 
                        }
       
                    }
                });

                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "MobileGraph?task=getAllOhLevel",
                    //data:{data: vkp_id},
                    success: function(response_data1) {
                        ohlevel=response_data1.ohLevel;
                        for(var i=0;i<ohlevel.length;i++){
                            OhLevel1[i]=ohlevel[i]["remark"];
                            //alert(ohlevel[i]["remark"]);
                        }

                    }
                });
 



                var chart = new CanvasJS.Chart("chartContainer", {
                    zoomEnabled: true,
                    title: {
                        text: "Overhead Tank Level"
                    },
//                    axisX: {
//                        title: "chart updates in every 2 minutes"
//                    },
                    axisY:{
		
                        title: "ohLevel",
                        yValueFormatString: "###",
                        includeZero: false,
                        interval: 50
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
                    data: [ {
                          type: "line",
                            xValueType: "dateTime",
                            yValueFormatString: "###",
                            xValueFormatString: "hh:mm:ss TT",
                            showInLegend: true,
                            name: " OverHeadTank Water Level ",
                            dataPoints: dataPoints1
                        } 
                      ]

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
                    chart.options.data[0].legendText = " Current OverHead Level  " + yValue1 +" cm" ;
                    chart.render();
                }
 

                function updateChart1() {


                    $.ajax({
                        dataType: "json",
                        async : false,
                        url: "CanvasJSController1?task=getNewDateTime",
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
                        url: "CanvasJSController1?task=getNewOhLevel",
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
                        yValue1 = Math.round((OhLevel1[i])*100)/100;
                          alert(yValue1);
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
               // updateSumpwellChart(sumpwellOhLevel1.length);
                
               // updateEnergyMeterChart(energyMeterDataArray.length);
                //setInterval(function(){updateChart1()}, updateInterval);
            }
        </script>
    </head>
    <body>
<div class="container">
    <div class="row"  id="chartContainer" >
        
      </div>   
        
        
      
           <div id="search_div" class="row">
         <div class="col-md-6 col-lg-4 col-xl-3">
            <h2>date :${date} </h2>
            <p>  OverHeadTank :${ohname}  </p>
           </div>
      
                  
       
    </div>
    
</div>
        
     
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    </body>
</html>
