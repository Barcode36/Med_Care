
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
  <head>
   <script src="amcharts/ammap.js"></script>
<script src="amcharts/maps/js/worldLow.js"></script>
<script src="amcharts/themes/light.js">

function showRefMap(div)
{
	$("#"+div).html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/Text2Mail/refMapDataget.action",
		success : function(data) {
		$("#"+div).html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

showRefMap('chartdiv');


</script>
   
    <script type='text/javascript'>
    var map = AmCharts.makeChart("chartdiv", {
        type: "map",
        "theme": "light",
        path: "amcharts",

        imagesSettings: {
            rollOverColor: "#089282",
            rollOverScale: 3,
            selectedScale: 3,
            selectedColor: "#089282",
            color: "#13564e"
        },

        zoomControl: {
            buttonFillColor: "#15A892"
        },

        areasSettings: {
            unlistedAreasColor: "#15A892"
        },

        dataProvider: {
            map: "worldLow",
            images: getJsonData()
        }
    });
   function getJsonData(){
     //   alert("hii");
    
	 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/refMapDataget.action?",
			    success : function(subdeptdata) 
			    {
		   return subdeptdata;
		    },
		   error: function() {
	            alert("error");
	        }
		 });
    }
    
    map.write("chartdiv");

    // add events to recalculate map position when the map is moved or zoomed
    map.addListener("positionChanged", updateCustomMarkers);

    // this function will take current images on the map and create HTML elements for them
    function updateCustomMarkers (event) {
        // get map object
        var map = event.chart;
        
        // go through all of the images
        for( var x in map.dataProvider.images) {
            // get MapImage object
            var image = map.dataProvider.images[x];
            
            // check if it has corresponding HTML element
            if ('undefined' == typeof image.externalElement)
                image.externalElement = createCustomMarker(image);

            // reposition the element accoridng to coordinates
            image.externalElement.style.top = map.latitudeToY(image.latitude) + 'px';
            image.externalElement.style.left = map.longitudeToX(image.longitude) + 'px';
        }
    }

    // this function creates and returns a new marker element
    function createCustomMarker(image) {
        // create holder
        var holder = document.createElement('div');
        holder.className = 'map-marker';
        holder.title = image.title;
        holder.style.position = 'absolute';
        
        // maybe add a link to it?
        if (undefined != image.url) {
            holder.onclick = function() {
                window.location.href = image.url;
            };
            holder.className += ' map-clickable';
        }
        
        // create dot
        var dot = document.createElement('div');
        dot.className = 'dot';
        holder.appendChild(dot);
        
        // create pulse
        var pulse = document.createElement('div');
        pulse.className = 'pulse';
        holder.appendChild(pulse);
        
        // append the marker to the map container
        image.chart.chartDiv.appendChild(holder);
        
        return holder;
    }
    </script>
    <STYLE type="text/css">
    #chartdiv {
	width	: 100%;
	height	: 500px;
}

.map-marker {
    /* adjusting for the marker dimensions 
    so that it is centered on coordinates */
    margin-left: -8px;
    margin-top: -8px;
}
.map-marker.map-clickable {
    cursor: pointer;
}
.pulse {
		width: 10px;
		height: 10px;
		border: 5px solid #f7f14c;
		-webkit-border-radius: 30px;
		-moz-border-radius: 30px;
		border-radius: 30px;
		background-color: #716f42;
		z-index: 10;
		position: absolute;
	}
.map-marker .dot {
		border: 10px solid #fff601;
		background: transparent;
		-webkit-border-radius: 60px;
		-moz-border-radius: 60px;
		border-radius: 60px;
		height: 50px;
		width: 50px;
		-webkit-animation: pulse 3s ease-out;
		-moz-animation: pulse 3s ease-out;
		animation: pulse 3s ease-out;
		-webkit-animation-iteration-count: infinite;
		-moz-animation-iteration-count: infinite;
		animation-iteration-count: infinite;
		position: absolute;
		top: -25px;
		left: -25px;
		z-index: 1;
		opacity: 0;
	}
	@-moz-keyframes pulse {
	 0% {
	   	-moz-transform: scale(0);
	   	opacity: 0.0;
	 }
	 25% {
	   	-moz-transform: scale(0);
	   	opacity: 0.1;
	 }
	 50% {
	   	-moz-transform: scale(0.1);
	   	opacity: 0.3;
	 }
	 75% {
	   	-moz-transform: scale(0.5);
	   	opacity: 0.5;
	 }
	 100% {
	   	-moz-transform: scale(1);
	   	opacity: 0.0;
	 }
	}
	@-webkit-keyframes "pulse" {
	 0% {
	    -webkit-transform: scale(0);
	   	opacity: 0.0;
	 }
	 25% {
	    -webkit-transform: scale(0);
	   	opacity: 0.1;
	 }
	 50% {
	    -webkit-transform: scale(0.1);
	   	opacity: 0.3;
	 }
	 75% {
	    -webkit-transform: scale(0.5);
	   	opacity: 0.5;
	 }
	 100% {
	    -webkit-transform: scale(1);
	   	opacity: 0.0;
	 }
	}		
    </STYLE>

  </head>
  <body>
   <div id="chartdiv"></div>						
  </body>
</html>