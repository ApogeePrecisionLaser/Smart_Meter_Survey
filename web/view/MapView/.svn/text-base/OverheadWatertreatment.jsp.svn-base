<%--
    Document   : mapWindow
    Created on : Sep 29, 2014, 11:36:35 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <script type="text/javascript" language="javascript">
            var markers = [];
            var map;
            var data= [];
            function polyline(lat1,long1,lat2,long2,color,width) {
                var flightPlanCoordinates_1 = [
                    new google.maps.LatLng(lat1,long1),
                    new google.maps.LatLng(lat2,long2)
                ];
                var flightPath_1 = new google.maps.Polyline({
                    path: flightPlanCoordinates_1,
                    geodesic: true,
                    strokeColor: color,
                    strokeOpacity: 1.0,
                    strokeWeight: width
                });
                return flightPath_1;
            }
            function initialize() {
                var mapOptions = {
                    zoom: 12,
                    center: new google.maps.LatLng(23.1657228, 79.9505182),
                    travelMode: google.maps.TravelMode.DRIVING
                };
                map = new google.maps.Map(document.getElementById('googleMap'),mapOptions);
                var coordinateCount = $("#count").val();
                var array = new Array();
                for(var i = 1; i <= coordinateCount; i++){
                    array[i - 1] = new google.maps.LatLng($("#lati"+i).val(),$("#longi"+i).val());
                }
                var locations = array;
                for (var i=0; i < locations.length; i++) {
                    markers[i] = new google.maps.Marker({
                        position: locations[i],
                        map: map,
                        icon:"images/map_image/waterTreatmentPlant.png",
                        title: locations[i].lat()+", "+locations[i].lng()
                    });
                }


                var tankLegth = $("#count1").val();

                var array1 = new Array();
                for(var i = 1; i <= tankLegth; i++){
                    array1[i - 1] = new google.maps.LatLng($("#latitude"+i).val(),$("#longitude"+i).val());
                }
                var locations1 = array1;
                for (var i=0; i < locations1.length; i++) {
                    markers[i] = new google.maps.Marker({
                        position: locations1[i],
                        map: map,
                        icon:"images/map_image/overheadTank.png",
                        title: locations1[i].lat()+", "+locations1[i].lng()
                    });
                }
                getLatLng();
            }

            function getLatLng(){
                $.ajax({url: "overHeadTankCont.do?task=getAllLatLng",
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json',
                    context: document.body,
                    success: function(response_data)
                    {  
                        data=response_data.data;
                        for (i = 0; i < data.length; i++)
                        {
                     var pl=polyline(data[i].wlatitude,data[i].wlongitude,data[i].olatitude,data[i].olongitude,"RED",2);
                       pl.setMap(map);
                        }       
                    }
                });
            }

            google.maps.event.addDomListener(window, 'load', initialize);

        </script>
    </head>
    <body>
        <div id="googleMap" style="width:1450px;height:650px;text-align: center"></div><!--width:1500px;height:650px;-->
        <c:forEach var="Coordinates" items="${requestScope['CoordinatesList']}" varStatus="loopCounter">
            <c:set var="cordinateLength"  value="${loopCounter.count}"></c:set>
            <input type="hidden" id="lati${loopCounter.count}" value="${Coordinates.latitude}">
            <input type="hidden" id="longi${loopCounter.count}" value="${Coordinates.longitude}">
        </c:forEach>
        <input type="hidden" id="count" value="${cordinateLength}">
        <c:forEach var="tank" items="${requestScope['TankList']}" varStatus="loopCounter">
            <c:set var="tankLegth"  value="${loopCounter.count}"></c:set>
            <input type="hidden" id="latitude${loopCounter.count}" value="${tank.latitude}">
            <input type="hidden" id="longitude${loopCounter.count}" value="${tank.longitude}">
        </c:forEach>
        <input type="hidden" id="count1" value="${tankLegth}">
    </body>
</html>
