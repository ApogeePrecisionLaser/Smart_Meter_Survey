<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <style type="text/css">
            html, body, #map-canvas {
                height: 100%;
                margin: 0px;
                padding: 0px
            }
        </style>
        <script type="text/javascript">
            var data= [];
            var marker1 = [];
            var label;
            var map;
            window.onload = function()
            {
                $.ajax({url: "meter_detail.do?task=getlatlong",
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    context: document.body,
                    success: function(response_data)
                    {
                        data=response_data.data;
                        traverse();
                    }
                });
            };

            function traverse()
            {
                map = new google.maps.Map(document.getElementById('map-canvas'), {
                    center: new google.maps.LatLng(23.1657228, 79.9505182),
                    zoom: 20,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });
                
                    map1(data);
            }

            function map1(arrayData)
            {
                if(arrayData.length!=0)
                {
                    for (var i = 0; i < arrayData.length; i++)
                    {
                        marker1[i] = new google.maps.Marker({
                            position: new google.maps.LatLng(arrayData[i].latitude, arrayData[i].longitude),
                            map: map,
                            label: ""+(i+1)
                        });
                        
                    }
                }
            }
            function initialize()//lat1,long1,lat2,long2,color1,width1)
            {
//                var flightPlanCoordinates_1 = [
//                    new google.maps.LatLng(lat1,long1),
//                    new google.maps.LatLng(lat2,long2)
//                ];
//                var flightPath_1 = new google.maps.Polyline({
//                    path: flightPlanCoordinates_1,
//                    geodesic: true,
//                    strokeColor: color1,
//                    strokeOpacity: 1.0,
//                    strokeWeight: width1
//                });
//                return flightPath_1;
            }
            google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    </head>
    <body>
        <div id="map-canvas"></div>
        <input class="input" type="text" style="display: none" id="pipe_detail_id" name="pipe_detail_id" value="${pipe_detail_id}">
    </body>
</html>