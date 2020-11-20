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
                directionsDisplay = new google.maps.DirectionsRenderer();
                directionsDisplay.setMap(map);
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
    </body>
</html>
