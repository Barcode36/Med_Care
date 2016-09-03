function addinstantmsg()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeschdulemessageAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}

function changeFrequency(val,div1,div2,div3,div4)
{
 var p=document.getElementById(div1);
var q=document.getElementById(div2);
var r=document.getElementById(div3);
var s=document.getElementById(div4)
if(val=="One-Time")
{
	p.style.display='block';
	q.style.display='none';
	r.style.display='block';
	s.style.display='none';
	}
else if(val=="Daily")
{
	p.style.display='none';
    q.style.display='none';
    r.style.display='block';
    s.style.display='none';
	}
else if(val=="Weekly")
{
	p.style.display='none';
    q.style.display='block';
    r.style.display='block';
    s.style.display='none';
	}
else if(val=="Monthly")
{
	p.style.display='block';
	q.style.display='none';
	r.style.display='block';
	s.style.display='none';
	}
else if(val=="Yearly")
{
	p.style.display='none';
	q.style.display='none';
	r.style.display='block';
	s.style.display='block';
	 }
else if(val=="None")
{
	p.style.display='none';
	q.style.display='none';
	r.style.display='none';
	s.style.display='none';
	}
	}
