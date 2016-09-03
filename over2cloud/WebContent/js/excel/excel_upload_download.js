function getCurrentColumn(downloadType,mappedTableName,masterTableName,dialogId,dataDiv,excelName,level)
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/excel/currentColumn.action",
	    data : "downloadType="+downloadType+"&mappedTableName="+mappedTableName+"&masterTableName="+masterTableName+"&excelName="+excelName+"&level="+level,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}