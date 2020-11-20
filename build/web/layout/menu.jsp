<%--
    Document   : index
    Created on : Jan 7, 2013, 3:26:07 PM
    Author     : Neha
--%>

<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>

<html>
    <head>
        <title>Menu</title>

        <link type="text/css" href="style/menu.css" rel="stylesheet"/>
    </head>
    <body>
        <div>
            <ul id="menu">
                <li><a href="#">Data Entry</a>
                    <ul>
                        <li><a href="personCount.do">Person's Name</a></li>
                        <li><a href="meter_detail.do">Connection Detail</a></li>
                        <li><a href="meterReadingCont.do">Meter Reading</a></li>
                        <li><a href="MultipleMeterDetailCont.do">Multiple Connection Detail</a></li>
                        <li><a href="reason.do">Reason</a></li>
                    </ul>
                </li>
                <li id="water_menu" style="display: block"><a id="status" href="#">Water</a>
                    <ul>
                        <li id="waterTreatmentPlantCont.do" style="display: block"><a href="waterTreatmentPlantCont.do">Water Treatment Plant</a></li>
                        <li id="overHeadTankCont.do" style="display: block"><a href="overHeadTankCont.do">Overhead Tank</a></li>
                        <li id="ohLevelCont.do" style="display: block"><a href="ohLevelCont.do">Overhead Tank Level</a></li>
                        <li id="OnOffCont.do" style="display: block"><a href="OnOffCont.do">On Off Level</a></li>
                        <li id="rawWaterCont.do" style="display: block"><a href="rawWaterCont.do">Raw Water</a></li>
                        <li id="rawWaterLevelCont.do" style="display: block"><a href="rawWaterLevelCont.do">Raw Water Level</a></li>
                        <li id="sourceWaterCont.do" style="display: block"><a href="sourceWaterCont.do">Source Water</a></li>
                        <li id="sourceWaterLevelCont.do" style="display: block"><a href="sourceWaterLevelCont.do">Source Water Level</a></li>

                        <li id="OverheadTankKeyPersonCont.do" style="display: block"><a href="OverheadTankKeyPersonCont.do">Overhead Tank Person</a></li>

                        <li id="TypeOfErrorCont.do" style="display: block"><a href="TypeOfErrorCont.do">Type Of Error </a></li>
                        <li id="OnOffValueCont.do" style="display: block"><a href="OnOffValueCont.do">OnOffValue</a></li>
                        <li id="OnOffValueCont.do" style="display: block"><a href="SumpwellOverheadtankCont.do">Sump well Overheadtank</a></li>

                        <li id="LevelRangeCont.do" style="display: block"><a href="LevelRangeCont.do">Level Range</a></li>
                         <li id="InsertDistributionCont.do" style="display: block"><a href="InsertDistributionCont.do">insert Dist</a></li>
                    </ul>
                </li>
                <li id="menu1" style="display: block"><a id="status" href="#">Pipe Detail</a>
                    <ul>
                        <li><a href="PurposeHeaderCont.do">Node</a></li>
                        <li> <a href="pipe_type.do">Pipe Type</a></li>
                        <li> <a href="BendCont.do">Bend</a></li>
                        <li> <a href="ValveCont.do">Valve</a></li>
                        <li> <a href="NodeOverHeadTankCont.do">Node OverHead Mapping</a></li>
                        <li> <a href="node_connectionCont.do">Node Connection Detail Mapping</a></li>
                        <li> <a href="OverHead_WaterPlant.do">OverHead WaterPlant Mapping</a></li>
                        <li> <a href="PipeDetailCont.do">Pipe Detail</a></li>
                        <li> <a href="PipeDetailTempCont.do">Pipe Detail Temp</a></li>
                        <li> <a href="date_timeController.do">Date Time</a> </li>
                        <li> <a href="generalCont.do?task=mapRouteView">Map</a></li>
                        <li> <a href="nearestMap.do">Nearest Map</a></li>
                    </ul>
                </li>
                <li><a href="#">MIS</a>
                    <ul>
                        <li><a href="OnOffErrorCont.do">Error Log</a></li>
                        <li><a href="ErrorLogMsgCont.do">Error Log Message</a></li>
                        <li><a href="PurposeHeaderController1">PurposeHeader</a></li>
                    </ul>
                </li>

                <li><a href="#">Add Item</a>
                    <ul>
                        <li><a href="ItemNameController">Item name</a></li>
                        <li><a href="PropertiesController">Properties</a></li>
                        <li><a href="ItemNamePropertiesMapController">ItemName_Properties_map</a></li>
                        <li><a href="PropertiesDetailsController">Properties_Details</a></li>
                        <li><a href="ItemController">Item</a></li>

                    </ul>
                </li>
           <li><a id="mpeb1" href="DashboardController" onclick="">Dashboard</a></li>
                
                <li><a id="mpeb1" href="LoginCont.do?task=logout" onclick="">LogOut</a></li>
            </ul>
        </div>
    </body>
</html>