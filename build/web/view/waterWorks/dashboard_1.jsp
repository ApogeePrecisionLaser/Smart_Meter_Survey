<%-- 
    Document   : Dashboard
    Created on : Sep 9, 2020, 5:28:18 PM
    Author     : Shailendra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>JSP Page</title>  <title>Bootstrap Website Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="refresh" content="10">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style>
  .fakeimg {
    height: 200px;
    background: #aaa;
  }
  </style>
  <script>
      
       function viewChart2(did,oid,ohname){
          
          var water = document.getElementById("waterid").value;
          var energy = document.getElementById("energyid").value;
        //  var ohnamedata = document.getElementById("ohname").value;
        var queryString = "task=VIEW_GRAPH2&ohlevel_id="+water+"&energy_id="+energy+"&did="+did+"&oid="+oid+"&ohname="+ohname;
        var url = "CanvasJSController1?" + queryString;
        popupwin = openPopUp(url, "Previous History Details", 1000, 700);
    }
      function viewChartnew(did,oid,ohname){
          
     //     var water = document.getElementById("waterid").value;
     //     var energy = document.getElementById("energyid").value;
        //  var ohnamedata = document.getElementById("ohname").value;
        var queryString = "task=VIEW_GRAPH2&did="+did+"&oid="+oid+"&ohname="+ohname;
        var url = "CanvasJSController11?" + queryString;
        popupwin = openPopUp(url, "Previous History Details", 1000, 700);
    }
      function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    
     function goToNewPage()
     {
         
         var relay = document.getElementById("relay_in_int").value;
         var waterid = document.getElementById("waterid").value;
         
           var queryString = "task=Waterrelay&relay="+relay+"&waterid="+waterid;
        
         
        $.ajax({
                    dataType: "json",
                    //                    async: false,
                    url: "DashboardController?"+queryString,
                    //data:{data: vkp_id},

                    success: function (response_data) {
                     
                        var show = response_data.relayupdate;
                        if(show == 1)
                        {
                            
                            alert("Record Updated");
                           
                        }
                        else
                        {
                            
                            alert("record not updated");
                        }
                      
                        //                        if (response_data.success == true) {
                        //                            setTimeout(function () {
                        //                                location.reload();
                        //                            }, 3000);
                        //                        }
                    }

                });
                //
     }
     
     
     
        function goToNewPage1()
     {
         var relay = document.getElementById("relay").value;
         var relay1 = document.getElementById("relay1").value;
         var waterid = document.getElementById("waterid").value;
         
           var queryString = "task=Waterrelay&relay="+relay+"&waterid="+waterid+"&relay1="+relay1;
        
         
        $.ajax({
                    dataType: "json",
                    //                    async: false,
                    url: "DashboardController?"+queryString,
                    //data:{data: vkp_id},

                    success: function (response_data) {
                     
                        var show = response_data.relayupdate;
                        if(show == 1)
                        {
                            
                            alert("Record Updated");
                           
                        }
                        else
                        {
                            
                            alert("record not updated");
                        }
                      
                        //                        if (response_data.success == true) {
                        //                            setTimeout(function () {
                        //                                location.reload();
                        //                            }, 3000);
                        //                        }
                    }

                });
                //
     }
     
     
     
      function datashow()
               
            {//alert("under data show"); debugger;

                //       var fuel_level=document.getElementById("fuel_level").value;
                //        var datetime = document.getElementById("datetime").value;


                $.ajax({
                    dataType: "json",
                    //                    async: false,
                    url: "DashboardController?task=check",
                    //data:{data: vkp_id},

                    success: function (response_data) {
                     
                        var fuel = response_data.data;
                        var tank_height = 1000;
                        var empty_height;
                           debugger;
                        for (var i = 0; i < fuel.length; i++) {
                            var level = fuel[i]["id"];
                             
                            var refresh = fuel[i]["id5"];
                            var fuel_left_litre = fuel[i]["id6"];
                            var date = fuel[i]["id2"];
                            var accuracy = fuel[i]["id3"];
                            var voltage = fuel[i]["id4"];

                            var latitude = fuel[i]["id7"];

                            var longitude = fuel[i]["id8"];

                            var speed = fuel[i]["id9"];
                            var fuel_intensity = fuel[i]["id10"];
                            var door_status = fuel[i]["id11"];
                            var waterlevel = fuel[i]["id12"];
                             var temperature = fuel[i]["id13"];
                             var intensity = fuel[i]["id14"];
                             var datetime = fuel[i]["id15"];
                             var relay = fuel[i]["id16"];
                             
                            var a = level.split(",");
//                                                        document.getElementById("fuel_level").value = level;
//                                                        document.getElementById("datetime").value = date;
//                                                        document.getElementById("accuracy").value = accuracy;
//                                                        document.getElementById("voltage").value = voltage;
                            //$("#XDatetime").text(date);
                         //    alert("date value -"+date);
                            //document.getElementById("XDatetime").value = date;
                            $("#XAccuracy").val(accuracy);

                            $("#XVoltage").val(voltage);
                            $("#XFuelStatus").val(level);
                            $("#refresh").val(refresh);
                            //$("#XFuelLeft").text(level);
                            $("#Date_Time").text("Updated : " + refresh + "(" + date + ")");

                            $("#fuel_level_litre").val(fuel_left_litre);
                            $("#refresh").val(refresh);
                            $("#latitude").val(latitude);
                            $("#longitude").val(longitude);
                            
                            $("#speed").val(speed);
                            
                            $("#fuel_intensity").val(fuel_intensity);
                            $("#door_status").val(door_status);
                            $("#waterlevel").val(waterlevel);
                             $("#water_temperature").val(temperature);
                              $("#water_intensity").val(intensity);
                               $("#watertime").val(datetime);
                               $("#relaystate").val(relay);
                            // for fuel icon
                            empty_height = tank_height - level;
                            $("#XEmptyTank").val(empty_height);
                            $("#XFulltank").val(tank_height);

                        }
                        //                        if (response_data.success == true) {
                        //                            setTimeout(function () {
                        //                                location.reload();
                        //                            }, 3000);
                        //                        }
                    }

                });
                //                setTimeout("datashow()",3000);
            }

            function multiple() {
                 // alert(document.getElementById("ohname").value);
            
                datashow();
               // check();
              
                //drawChart();
                setTimeout("multiple()", 30000);
                //                setTimeout(function () {
                //                                location.reload();
                //                            }, 3000);
            }

      </script>
    </head>
    <body onload="multiple()">
       <div class="jumbotron text-center" style="margin-bottom:0" >
  <h1>OverheadTank DashBoard</h1>
   
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="DashboardController">Dashboard</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="ohLevelCont.do">Home</a></li>
<!--        <li><a href="#">Page 2</a></li>
        <li><a href="#">Page 3</a></li>-->
      </ul>
    </div>
  </div>
</nav>

<div class="container">
  <div class="row">
<!--    <div class="col-sm-4">
      <h2>About Me</h2>
      <h5>Photo of me:</h5>
      <div class="fakeimg">Fake Image</div>
      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
      <h3>Some Links</h3>
      <p>Lorem ipsum dolor sit ame.</p>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="#">Link 1</a></li>
        <li><a href="#">Link 2</a></li>
        <li><a href="#">Link 3</a></li>
      </ul>
      <hr class="hidden-sm hidden-md hidden-lg">
    </div>-->
<!--    <div class="col-sm-8">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Dec 7, 2017</h5>
      <div class="fakeimg">Fake Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
      <br>
      <h2>TITLE HEADING</h2>
      <h5>Title description, Sep 2, 2017</h5>
      <div class="fakeimg">Fake Image</div>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>-->


<!--<div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Primary card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
<!--<div class="card text-white bg-secondary mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Secondary card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
<!--<div class="card text-white bg-success mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Success card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
<!--<div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Danger card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
<!--<div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Warning card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
     <input type="hidden" id="Date_Time" name="XDate_Time" value="${Date_Time}">
                <input type="hidden" id="refresh" name="refresh" value="${refresh}">
                <input type="hidden" id="waterlevel" name="waterlevel" value="${waterlevel}">
                 <input type="hidden" id="water_temperature" name="water_temperature" value="${water_temperature}">
                  <input type="hidden" id="water_intensity" name="water_intensity" value="${water_intensity}">
                    <input type="hidden" id="watertime" name="watertime" value="${watertime}">
                     <input type="hidden" id="relaystate" name="relaystate" value="${relaystate}">
                      <input type="hidden" id="waterid" name="waterid" value="${waterid}">
                      <input type="hidden" id="relay_in_int" name="relay_in_int" value="${relay_in_int}">
                       <input type="hidden" id="relay_in_int1" name="relay_in_int1" value="${relay_in_int1}">
                      <input type="hidden" id="total_active_power" name="total_active_power" value="${total_active_power}">
  <input type="hidden" id="Cons_energy_mains" name="Cons_energy_mains" value="${Cons_energy_mains}">
  <input type="hidden" id="active_energy_dg" name="active_energy_dg" value="${active_energy_dg}">
  <input type="hidden" id="total_active_energy" name="total_active_energy" value="${total_active_energy}">
  <input type="hidden" id="phase_voltage_R" name="phase_voltage_R" value="${phase_voltage_R}">
  <input type="hidden" id="phase_voltage_Y" name="phase_voltage_Y" value="${phase_voltage_Y}">
  <input type="hidden" id="phase_voltage_B" name="phase_voltage_B" value="${phase_voltage_B}">
  <input type="hidden" id="phase_current_R" name="phase_current_R" value="${phase_current_R}">
  <input type="hidden" id="phase_current_Y" name="phase_voltage_Y" value="${phase_current_Y}">
  <input type="hidden" id="phase_current_B" name="phase_voltage_Y" value="${phase_current_B}">
  <input type="hidden" id="energytime" name="energytime" value="${energytime}">
   <input type="hidden" id="energyid" name="energyid" value="${energyid}">
      <input type="hidden" id="magnetic_sensor_value" name="magnetic_sensor_value" value="${magnetic_sensor_value}">
    <input type="hidden" id="did" name="did1" value="${device_idfromjsp}">
    <input type="hidden" id="ohname" name="ohname1" value="${ohname}">
  

<table align="center">
  
    
    
    <tr><td><h3>
               OverHead Device Id :${device_idfromjsp}
</h3></td>
    <td><h3>
                OverHead Tank  :${ohname}
</h3></td>
 
    
    
    
    </tr>
    
    <tr>
        <td><div class="card text-white bg-warning mb-3" style="width: 400px; height: 300px">
    <div class="card-header"><h3 align="center">OverHead Tank Level DATA</h3></div>
  <div class="card-body">
<!--    <h5 class="card-title">Info card title</h5>-->
 

    <p class="card-text">  Overhead Water Level : <strong style="color: black;" id="waterlevel">${waterleveltemp} cm</strong></p>
    
                                            <p style="display: none">Water Intensity : <strong style="color: black;" id="water_intensity">${water_intensity}</strong></p>
                                             <p>Date Time : <strong style="color: black;" id="watertime">${datetimetemp}</strong></p>
                                             
   
 
                                          
                   <input type="button" class="btn btn-primary" id="graph" value="GRAPH" name="view_pdf2" onclick="viewChartnew('${device_idfromjsp}','${oht_idfromjsp}','${ohname}');">
                       
                                  
  </div>
</div></td>
 
<!--<td>    
<div class="card text-white bg-info mb-3" style="width: 430px; height: 400px">
    <div class="card-header"><h3 align="center">SumpWellWater Data</h3></div>
  <div class="card-body">
    <h5 class="card-title">Info card title</h5>
 

    <p class="card-text">  Water Level : <strong style="color: black;" id="waterlevel">${waterlevel} cm</strong></p>
    <p style="display: none">Water Temperature : <strong style="color: black;" id="water_temperature">${water_temperature}</strong></p>
                                            <p style="display: none">Water Intensity : <strong style="color: black;" id="water_intensity">${water_intensity}</strong></p>
                                             <p>Date Time : <strong style="color: black;" id="watertime">${watertime}</strong></p>
                                              <p>Magnetic Sensor Value : <strong style="color: black;" id="magnetic_sensor_value">${magnetic_sensor_value}</strong></p>
                                             <p>Relay State1 : <strong style="color: black;" id="relaystate">${relaystate}</strong></p>
                                              <p>Relay State2 : <strong style="color: black;" id="relay_in_int1">${relay_in_int1}</strong></p>
                                          <label for="relay">Relay 1:</label>
  <select name="relay" id="relay">
       <option value="0" class="btn btn-info" >OFF</option>
     <option value="1" class="btn btn-success">ONN</option>
  </select>
                                          
                                           <input type=button value="OK" class="btn btn-success" onclick="goToNewPage()" />
                                           <br>
                                           <label for="relay1">Relay 2:</label>
  <select name="relay1" id="relay1">
       <option value="0" class="btn btn-info" >OFF</option>
     <option value="1" class="btn btn-success">ONN</option>
  </select>
 
                                          
                                          <input type=button value="OK" class="btn btn-success" onclick="goToNewPage1()" />
                                          <br>
<input type="button" class="btn btn-primary" id="graph" value="GRAPH" name="view_pdf2" onclick="viewChart2('${device_idfromjsp}','${oht_idfromjsp}','${ohname}');">
                                  
  </div>
</div>
      
   </td> 
    
   <td> 
<div class="card text-white bg-warning mb-3" style="width: 300px; height: 400px">
    <div class="card-header"><h3 align="center">ENERGY DATA</h3></div>
  <div class="card-body">
    <h5 class="card-title">Light card title</h5>
    <p class="card-text">   Total Active Power : <strong style="color: black;" id="total_active_power">${total_active_power}</strong></p>
                                                 <p>Consumed Energy Mains : <strong style="color: black;" id="Cons_energy_mains">${Cons_energy_mains} kwh</strong></p>
                                            <p>Active Energy DG : <strong style="color: black;" id="active_energy_dg">${active_energy_dg}</strong></p>
                                            <p style="display: none">Total Active Energy : <strong style="color: black;" id="total_active_energy">${total_active_energy}</strong></p>
                                            <p>Phase Voltage R: <strong style="color: black;" id="phase_voltage_R">${phase_voltage_R} Volt</strong></p>
                                            <p>Phase Voltage Y: <strong style="color: black;" id="phase_voltage_Y">${phase_voltage_Y} Volt</strong></p>
                                             <p>Phase Voltage B: <strong style="color: black;" id="phase_voltage_B">${phase_voltage_B} Volt</strong></p>
                                           <p>Phase Current R: <strong style="color: black;" id="phase_current_R">${phase_current_R} A</strong></p>
                                              <p>Phase Current Y: <strong style="color: black;" id="phase_current_Y">${phase_current_Y} A</strong></p>
                                               <p>Phase Current B: <strong style="color: black;" id="phase_current_B">${phase_current_B} A</strong></p>
                                             <p>Date Time : <strong style="color: black;" id="energytime">${energytime}</strong></p>
                                             <p>Relay State : <strong style="color: black;" id="relaystate">${relaystate}</strong></p>
  </div>
</div>
        </td>-->
</tr>
</table>
<!--<div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
  <div class="card-header">Header</div>
  <div class="card-body">
    <h5 class="card-title">Dark card title</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>-->
</div>

<div class="jumbotron text-center" style="margin-bottom:0">
  <p>Footer</p>
</div>
    </body>
</html>
