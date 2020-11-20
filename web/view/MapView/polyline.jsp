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
            var map;
            window.onload = function()
            {
                traverse();
            };
            
            function traverse()
            {
                map = new google.maps.Map(document.getElementById('map-canvas'), {
                    center: new google.maps.LatLng(23.1657228, 79.9505182),
                    zoom: 10,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });
                map1();
            }
            
            function map1()
            {
                var x1 = $.trim(document.getElementById("latti1").value);
                var y1 = $.trim(document.getElementById("longi1").value);
                var x2 = $.trim(document.getElementById("latti2").value);
                var y2 = $.trim(document.getElementById("longi2").value);
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(x1, y1),
                    map: map,
                    label: "CL"
                });
                var markerr = new google.maps.Marker({
                    position: new google.maps.LatLng(x2, y2),
                    map: map,
                    label: "Dest."
                });
                var pl=initialize(x1, y1,x2, y2,'blue','3');
                pl.setMap(map);
                
            }
            function initialize(lat1,long1,lat2,long2,color1,width1)
            {
                var path = new google.maps.MVCArray();
                var service = new google.maps.DirectionsService();
                var poly = new google.maps.Polyline({ map: map, strokeColor: '#4986E7' });

                        var src = new google.maps.LatLng(lat1, long1);
                        var des = new google.maps.LatLng(lat2, long2);
                        path.push(src);
                        poly.setPath(path);
                        service.route({
                            origin: src,
                            destination: des,
                            travelMode: google.maps.DirectionsTravelMode.DRIVING
                        }, function (result, status) {
                            if (status == google.maps.DirectionsStatus.OK) {
                                for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                                    path.push(result.routes[0].overview_path[i]);
                                }
                            }
                        });
                    
                
            }
            google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    </head>
    <body>
        <div id="map-canvas"></div>
        <input type="hidden" id="longi1" value="${longi1}" >
        <input type="hidden" id="latti1" value="${latti1}" >
        <input type="hidden" id="longi2" value="${longi2}" >
        <input type="hidden" id="latti2" value="${latti2}" >
    </body>
</html>