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
            var data1= [];
            var head_latitude;
            var head_longitude;
            var tail_latitude;
            var tail_longitude;

            var water_plant_latitude;
            var water_plant_longitude;
            var overheadtank_latitude;
            var water_plant_latitude;
            var marker1 = [];
            var color1;
            var width1;
            var label;
            var map;
            window.onload = function()
            {
                $.ajax({url: "PipeDetailCont.do?task=GetCompleteData",
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    context: document.body,
                    success: function(response_data)
                    {
                        data1=response_data.data;
                        traverse1();
                    }
                });
            };
            
            function traverse1()
            {
                map = new google.maps.Map(document.getElementById('map-canvas'), {
                    center: new google.maps.LatLng(23.1657228, 79.9505182),
                    zoom: 8,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });
                var marker1 = new google.maps.Marker({
                    position: new google.maps.LatLng(water_plant_latitude, water_plant_longitude),
                    map: map,
                    label: "P"
                });
                var tempVal="";
                for (var ii = 0; ii < data1.length; ii++)
                {
                    water_plant_latitude=data1[ii].water_plant_latitude;
                    water_plant_longitude=data1[ii].water_plant_longitude;
                    overheadtank_latitude=data1[ii].overheadtank_latitude;
                    overheadtank_longitude=data1[ii].overheadtank_longitude;
                    if(tempVal.localeCompare(water_plant_latitude)){
                        tempVal=water_plant_latitude;
                        var pl=initialize(water_plant_latitude, water_plant_longitude,overheadtank_latitude, overheadtank_longitude,'RED','4');
                        pl.setMap(map);
                    }
                    data=data1[ii].dataArray1;
                    var status=traverse();
                }
                
            }
            function traverse()
            {
                var markerr2 = new google.maps.Marker({
                    position: new google.maps.LatLng(overheadtank_latitude, overheadtank_longitude),
                    map: map,
                    label: "O"
                });
                for (var ii = 0; ii < data.length; ii++)
                {
                    head_latitude=data[ii].head_latitude;
                    head_longitude=data[ii].head_longitude;
                    tail_latitude=data[ii].tail_latitude;
                    tail_longitude=data[ii].tail_longitude;
                    map1(data[ii].dataArray2);
                }
                return true;
            }
            
            function map1(arrayData)
            {
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
                if(arrayData.length!=0)
                {
                    for (var i = 0; i < arrayData.length; i++)
                    {
                        color1=arrayData[i].color;
                        width1=arrayData[i].width;
                        marker1[i] = new google.maps.Marker({
                            position: new google.maps.LatLng(arrayData[i].latitude, arrayData[i].longitude),
                            map: map,
                            label: ""+(i+1)
                        });
                        if(i==0)
                        {
                            var pl=initialize(head_latitude, head_longitude,arrayData[i].latitude, arrayData[i].longitude,color1,width1);
                            pl.setMap(map);
                        }
                        if(i!=0)
                        {
                            var pl=initialize(arrayData[i-1].latitude, arrayData[i-1].longitude,arrayData[i].latitude, arrayData[i].longitude,color1,width1);
                            pl.setMap(map);
                        }
                        if(i == arrayData.length-1)
                        {
                            var pl=initialize(arrayData[i].latitude, arrayData[i].longitude,tail_latitude, tail_longitude,color1,width1);
                            pl.setMap(map);
                        }
                    }
                } else
                {
                    var pl=initialize(head_latitude, head_longitude,tail_latitude, tail_longitude,color1,width1);
                    pl.setMap(map);
                }
            }
            function initialize(lat1,long1,lat2,long2,color1,width1)
            {
                var flightPlanCoordinates_1 = [
                    new google.maps.LatLng(lat1,long1),
                    new google.maps.LatLng(lat2,long2)
                ];
                var flightPath_1 = new google.maps.Polyline({
                    path: flightPlanCoordinates_1,
                    geodesic: true,
                    strokeColor: color1,
                    strokeOpacity: 1.0,
                    strokeWeight: width1
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