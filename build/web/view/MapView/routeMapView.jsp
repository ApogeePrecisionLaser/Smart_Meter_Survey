<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Map View</title>
        <title>Simple Polylines</title>
        <style>
            html, body, #map-canvas {
                height: 100%;
                margin: 0px;
                padding: 0px
            }
        </style>
        <script>
            var data= [];
            var pipe_detail_id="<%=request.getAttribute("pipe_detail_id")%>";
            var head_latitude="<%=request.getAttribute("head_latitude")%>";
            var head_longitude="<%=request.getAttribute("head_longitude")%>";
            var tail_latitude="<%=request.getAttribute("tail_latitude")%>";
            var tail_longitude="<%=request.getAttribute("tail_longitude")%>";
            window.onload = function()
            {
                $.ajax({url: "PipeDetailCont.do?task=GetPipeBend&pipe_detail_id="+pipe_detail_id,
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    context: document.body,
                    success: function(response_data)
                    {
                        data=response_data.data;
                        map();
                    }
                });
//                map();
            };
            function map()
            {
                var lat = ${latti};
                if(lat == "")
                    lat = "23.1657228";
                var lng = ${longi};
                if(lng == "")
                    lng = "79.9505182";
                var latlng = new google.maps.LatLng(lat, lng);//(23.1657228,79.9505182);
                var map = new google.maps.Map(document.getElementById('map-canvas'), {
                    center: latlng,
                    zoom: 8,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });

                var marker1 = [];
                var color;
                var width;
                var label;
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(head_latitude, head_longitude),
                    map: map,
                    label: "H"
                });
                var markerr = new google.maps.Marker({
                    position: new google.maps.LatLng(tail_latitude, tail_longitude),
                    map: map,
                    label: "T"
                });
                if(data.length!=0){
                for (i = 0; i < data.length; i++)
                {
                    color=data[i].color;
                    width=data[i].width;
                    marker1[i] = new google.maps.Marker({
                        position: new google.maps.LatLng(data[i].latitude, data[i].longitude),
                        map: map,
                        label: ""+(i+1)
                    });
                    if(i==0)
                    {
                        var pl=initialize(head_latitude, head_longitude,data[i].latitude, data[i].longitude,color,width);
                        pl.setMap(map);
                    }
                    else
                    {
                        var pl=initialize(data[i-1].latitude, data[i-1].longitude,data[i].latitude, data[i].longitude,color,width);
                        pl.setMap(map);
                    }
                    if(i == data.length-1)
                    {
                        var pl=initialize(data[i].latitude, data[i].longitude,tail_latitude, tail_longitude,color,width);
                        pl.setMap(map);
                    }
                }
                }else
                {
                    var pl=initialize(head_latitude, head_longitude,tail_latitude, tail_longitude,color,width);
                        pl.setMap(map);
                }
            }

            function initialize(lat1,long1,lat2,long2,color,width) {
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

            google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    </head>
    <body>
        <div id="map-canvas"></div>
        <input class="input" type="text" style="display: none" id="pipe_detail_id" name="pipe_detail_id" value="${pipe_detail_id}">
    </body>
</html>