

function show_this_content(id,_co,total){
		var i=1;
		while(total>=i){
			document.getElementById("show_this"+i).style.display="none";
			i++;
		}
		document.getElementById(id).style.display="block";
	}

	function hide_feed(id){
		document.getElementById(id).style.display="none";
	}
	function show_feed(id){
		document.getElementById(id).style.display="block";
	}
	function show_content_this(id){
		if(document.getElementById(id).style.display=="none"){
			document.getElementById(id).style.display="block";
		}else{
			document.getElementById(id).style.display="none";
		}
	}
	function hide_block(id){
		document.getElementById(id).style.display="none";
	}
	
	function show_sub_menu(id1, id2, count, total){
		var z=1;
		while(total>=z){
			document.getElementById(id2+z).style.display="none";
			document.getElementById(id1+z).className+="";
			z++;
		}
		if(document.getElementById(id2+count).style.display=="none"){
			document.getElementById(id2+count).style.display="block";
			/*document.getElementById(id1+count).className+=" this_active";*/
		}else{
			document.getElementById(id2+count).style.display="none";
			document.getElementById(id1+count).className="";
		}
	}

	
	function drawURLData(URL,divIdImg,divIdData,divShow,mainLink)
	{
		if(URL=="" || URL==null)
			return false;

		var urlRegex = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
		var url= URL.match(urlRegex);
		try
		{
			if(url.length>0)
			{
					if(divShow!="" && divShow!=null)
					{
						//document.getElementById("linkPost").value="";
						$("#"+divIdImg).html("");
						$("#"+divIdData).html("");
					}
					if(mainLink!="" && mainLink!=null)
					{
						document.getElementById("processImg").style.display="block";
					}
				
					 var dataObj = {"URL": URL};
				$.ajax({
						type : "post",
					    url : "/cloudapp/view/Over2Cloud/homePage/urlScrapper.action",
					    data: dataObj,
					    success: function(response) {
								var data=response.split("#$#>");
								var logo=null,title=null;
								title=data[0];
								logo=data[2];
						        $("#"+divIdImg).html("<img src='"+logo+"' class='img' width='135' height='80' />");
						        $("#"+divIdData).html('<h1>'+title+'</h1><p>'+data[1]+'</p><p><a>'+URL+'</a></p>');

						        if(divShow!="" && divShow!=null)
								{
						        	document.getElementById(divShow).style.display="block";
									document.getElementById("processImg").style.display="none";
								}
						        if(mainLink!="" && mainLink!=null)
								{
						        	//document.getElementById("linkPost").value=data[1];
								}
					    } 
				 });
			}
		}
		catch(err)
		{
			if(divShow!="" && divShow!=null)
			{
				document.getElementById(divShow).style.display="none";
				document.getElementById("processImg").style.display="none";
			}
		}
	}

	
	function viewAllLikesOfPost(divId,postId)
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/totalLikesView.action?postId="+postId,
		    success : function(subdeptdata) {
	       $("#"+divId).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function deletPost(postDivId,postId,tableName,moduleFlag)
	{
		document.getElementById(postDivId).style.display="none";
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/deletePost.action?postId="+postId+"&tableName="+tableName+"&moduleFlag="+moduleFlag,
		    success : function(subdeptdata) {
		    	document.getElementById(postDivId).style.display="none";
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	

	function likePost(postId,blockDivID,likeID,toalLike,counterDiv)
	{
		var likenbame = null;
		    likenbame =  $("#"+likeID).text();
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/submitLike.action?postId="+postId,
		    success : function(subdeptdata) {
		    	if(likenbame=='Like')
		    	{
			    	$("#"+likeID).text("Unlike");
		    	}
			    else if(likenbame=='Unlike')
			    {
			    	$("#"+likeID).text("Like");
			    } 
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	//pagination in comments
	function moreCommentShow(to,showDivData,HidePaginationDiv,postID)
	{
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/commentpagination.action?commentTo="+to+"&postId="+postID,
		    success : function(subdeptdata) {
			$("#"+showDivData).html(subdeptdata);
			document.getElementById(HidePaginationDiv).style.display="none";
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}
	function openAlreadyUploadedFiles()
	{
		$("#documentView").dialog('open');
		$("#documentView").load("<%=request.getContextPath()%>/view/Over2Cloud/homePage/uploadedFilesViewOfUser.action");
	}
	function selectedFiles()
	{
		var selectedFiles=document.getElementById('filePath').value;
		alert(selectedFiles);
		$("#documentView").dialog('close');
	}
