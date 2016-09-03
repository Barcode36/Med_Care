function download(divId){
	
	if (maxDivIdLoc1!="1stTable"){
		//alert("2");
		$('#'+divId).jqxChart('saveAsJPEG', 'Ticket Counter Status Department Wise From '+$('#sdate').val()+' to '+$('#sdate').val(), getExportServer());
		
	}else 
		{
		//alert("3");
		html2canvas($("#"+divId), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);
                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                //  var data = canvas.toDataURL("image/png").replace( '/^data:image\/png;base64,/', 'data:application/octet-stream');
                // window.location.href = data;
                var dt = canvas.toDataURL('image/png');
               // window.open(dt);
               this.href = dt.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
                $("#img-out").append(canvas);
                // Clean up 
                document.body.removeChild(canvas);
            }
        });
         
   
		}
}



$(function() { 
	
    $("#btnSave").click(function() { 
	//alert(maxDivId1);
	if (maxDivId1!="1stTable"){
		//alert("2");
		$('#jqxChart').jqxChart('saveAsJPEG', 'Ticket Counter Status Department Wise From '+$('#sdate').val()+' to '+$('#sdate').val(), getExportServer());
		
	}else 
		{
		//alert("3");
		html2canvas($("#jqxChart"), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);
                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                //  var data = canvas.toDataURL("image/png").replace( '/^data:image\/png;base64,/', 'data:application/octet-stream');
                // window.location.href = data;
                var dt = canvas.toDataURL('image/png');
               // window.open(dt);
               this.href = dt.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
                $("#img-out").append(canvas);
                // Clean up 
                document.body.removeChild(canvas);
            }
        });
         
   
		}
    });
});

$(function() { 
	
    $("#btnSave1").click(function() { 
	//alert(maxDivId1);
	if (maxDivId2!="2ndTable"){
	//	alert("2");
		$('#levelChart').jqxChart('saveAsJPEG', 'Ticket Counter Status Level Wise From '+$('#sdate').val()+' to '+$('#sdate').val(), getExportServer());
		
	}else 
		{
		//alert("3");
		html2canvas($("#levelChart"), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);
                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                //  var data = canvas.toDataURL("image/png").replace( '/^data:image\/png;base64,/', 'data:application/octet-stream');
                // window.location.href = data;
                var dt = canvas.toDataURL('image/png');
               // window.open(dt);
               this.href = dt.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
                $("#img-out").append(canvas);
                // Clean up 
                document.body.removeChild(canvas);
            }
        });
         
   
		}
    });
});

$(function() { 
	
    $("#btnSave2").click(function() { 
	//alert(maxDivId1);
	if (maxDivId3!="3rdTable"){
		//alert("2");
		$('#CategChart').jqxChart('saveAsJPEG', 'Current Day Analysis Deparmtnet Wise', getExportServer());
		
	}else 
		{
		//alert("3");
		html2canvas($("#CategChart"), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);
                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                //  var data = canvas.toDataURL("image/png").replace( '/^data:image\/png;base64,/', 'data:application/octet-stream');
                // window.location.href = data;
                var dt = canvas.toDataURL('image/png');
               // window.open(dt);
               this.href = dt.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
                $("#img-out").append(canvas);
                // Clean up 
                document.body.removeChild(canvas);
            }
        });
         
   
		}
    });
});
	

/*$(function() { 
    $("#btnSave").click(function() { 
    	alert("saving");
        html2canvas($("#jqxChart"), {
            onrendered: function(canvas) {
                theCanvas = canvas;
                document.body.appendChild(canvas);

                // Convert and download as image 
                Canvas2Image.saveAsPNG(canvas); 
                
                $("#img-out").append(canvas);
                // Clean up 
                //document.body.removeChild(canvas);
            }
        });
    });
});*/



/*$('#btnSave').click(function () {
alert("hi");
$('#jqxChart').jqxChart('saveAsJPEG', 'myChart.jpeg', getExportServer());
});*/


function getExportServer() {
    return 'http://www.jqwidgets.com/export_server/export.php';
}

/*$("#jpegButton").click(function () {
    // call the export server to create a JPEG image
    $('#chartContainer').jqxChart('saveAsJPEG', 'myChart.jpeg', getExportServer());
});*/