<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css"></style>
<script type='text/javascript' src='js/jquery.min.js'></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style1.css" rel="stylesheet" type="text/css" />
<link href="css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/popbox.css" type="text/css" media='screen' charset='utf-8'></link>
<link href="css/dcmegamenu.css" rel="stylesheet" type="text/css" />
<link href="css/skins/black.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function($){
		$('#mega-menu').dcMegaMenu({
		rowItems: '4',
		speed: 'fast',
		effect: 'slide'
	});
});
</script>

<script type="text/javascript">
function hideDiv(id){
  var obj = document.getElementById(id);
    if (obj){
      obj.style.display='none';
    }
}
function showDiv(id){
  var obj = document.getElementById(id);
    if (obj){
      obj.style.display='block';
    }
}
</script>


<script>
$(function() {
// run the currently selected effect
function runEffect() {
// get effect type from
var selectedEffect = $( "#effectTypes" ).val();
// most effect types need no options passed by default
var options = {};
// some effects have required parameters
if ( selectedEffect === "scale" ) {
options = { percent: 0 };
} else if ( selectedEffect === "size" ) {
options = { to: { width: 200, height: 60 } };
}
// run the effect
$( "#effect" ).toggle( selectedEffect, options, 500 );
};
// set effect from select menu value
$( "#button" ).click(function() {
runEffect();
return false;
});
});
</script>

<script type='text/javascript' charset='utf-8' src='js/popbox.js'></script>
  
<script type='text/javascript' charset='utf-8'>
    $(document).ready(function(){
      $('.popbox').popbox();
    });
  </script>


</head>
<body>
<div class="clear"></div>

<div class="list-icon">
<div class="head"></div>
</div>

<div class="left">
<div class="menu-box">
<div class="headdings">Profile Details</div>
<div class="clear"></div>
<div class="cat-menu">
<ul>
	<li class="selected-setting"><a href="#">Personal Settings</a></li>
	<li><a href="#">Organization Settings</a></li>
	<li><a href="#">Users & Permissions</a></li>
	<li><a href="#">Customization</a></li>
	
</ul>
</div>
</div>
</div>

<div class="right">
<div style="float:left; width:100%; height:auto; margin-bottom:10px;"></div>
<div id="updatemsg" style="display:none;  color: #FFFFFF; background-color: #55B05A; margin-left: 300px; height: 30px; width: 200px; line-height: 40px; top:-42px; text-align:center; padding:2px 15px; border-radius:2px;" class="successmsg"></div><br><table width="97%" cellspacing="0" cellpadding="0"><tbody>
<tr><td width="50"> 
   <div class='collapse'>
      <div class='box'>

          <form method="post" name="" id="" action="" onsubmit="">
<div class="contact-box1">
<div class="headdings-inner" style="margin-top:0px;">Personal Details</div>
<div class="clear"></div>

  <div class="input-main-container1"> 
<div class="input-name1">First Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Last Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Address :</div>
<div class="input-container1">
  <textarea name="comments" rows="2" class="input3" style="height:50px;"></textarea>
</div>
</div>
<div class="input-main-container1" style="margin-bottom:0px"> 
<div class="input-name1">Phone :</div>
<div class="input-container1">
  <div style="float:left; width:79px; margin-right:7px;"><input type="text" style="width:70px;" name="std" class="input3" /></div>
  <div style="float:left; width:130px"><input type="text" style="width:130px;" name="ph" class="input3" /></div>
  <div style="float:left; width:79px; padding-right:5px; font-size:11px;">Area Code.</div>
  <div style="float:left; width:132px; font-size:11px;">Telephone No.</div>
</div>
</div>
<div class="clear"></div>
<div class="input-main-container1"> 
<div class="input-name1">Select :</div>
<div class="input-container1">
  <select name="Interest" class="input3" style="width:228px; height:25px;">
    <option value="" selected="selected"> --Select-- </option>
    <option value="Residential">Abcdef</option>
  </select>
</div>
</div>
<div class="clear"></div>
<div class="input-main-container1"> 
<div class="input-name1"></div>
<div class="input-container1">
<div style="float:left; margin-right:15px;"><a href="#" class="open">Submit</a></div><div style="float:left"><a href="#" class="open close">Cancel</a></div>
</div>

</div>
</div>
         
        </form>

      </div>
    </div>

</td>
  </tr>
   </tbody>
      </table>
           <br>
             <br>
             <table width="97%" cellspacing="0" cellpadding="0" class="mt15"><tbody>
          <tr>
           <td width="200" valign="top" bgcolor="#ffffff"> <div style="width:210px;height:210px;overflow:hidden;"><a class="userphotocontainer" href="javascript:;">
			<div style="width:200px; height:200px; overflow:hidden;">
			<img width="200" border="0" src="images/profile-pic.png"></div>
			<div onclick="javascript:showUploadPhoto('Users');" style="overflow:hidden; width:200px\9;" class="userchangephototxt" ><span style="cursor:pointer;"> Change </span></div>
			</a>
</div>
<div class="pT20">
<div class="emailicon mt5">pravendra.saxena@gmail.com</div>
<div onclick="javascript:show('extrainfodv');hide('extrainfodvlnk');" class="pL25 mt7" id="extrainfodvlnk"><img width="1" height="1" title="Show More" class="dottedIcon pointer" src="../images/spacer.gif"></div>
<div style="display:none;" id="extrainfodv"></div><div class="locationIcons pL25 mt15">DELHI
,<br>INDIA.	
</div>
<div class='popbox'><a href="#" class='open'>Edit</a>

   <div class='collapse'>
      <div class='box'>

          <form method="post" name="" id="" action="" onsubmit="">
<div class="contact-box1">
<div class="headdings-inner" style="margin-top:0px;">Personal Details</div>
<div class="clear"></div>

  <div class="input-main-container1"> 
<div class="input-name1">First Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Last Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Address :</div>
<div class="input-container1">

  <textarea name="comments" rows="2" class="input3" style="height:50px;"></textarea>
</div>
</div>

<div class="input-main-container1" style="margin-bottom:0px"> 
<div class="input-name1">Phone :</div>
<div class="input-container1">
  <div style="float:left; width:79px; margin-right:7px;"><input type="text" style="width:70px;" name="std" class="input3" /></div>
  <div style="float:left; width:130px"><input type="text" style="width:130px;" name="ph" class="input3" /></div>
  
  <div style="float:left; width:79px; padding-right:5px; font-size:11px;">Area Code.</div>
  <div style="float:left; width:132px; font-size:11px;">Telephone No.</div>
  
</div>



</div>


<div class="clear"></div>

<div class="input-main-container1"> 
<div class="input-name1">Select :</div>
<div class="input-container1">
  <select name="Interest" class="input3" style="width:228px; height:25px;">
    <option value="" selected="selected"> --Select-- </option>
    <option value="Residential">Abcdef</option>
    
    
  </select>
</div>
</div>





<div class="clear"></div>
<div class="input-main-container1"> 
<div class="input-name1"></div>
<div class="input-container1">
<div style="float:left; margin-right:15px;"><a href="#" class="open">Submit</a></div><div style="float:left"><a href="#" class="open close">Cancel</a></div>
</div>

</div>
</div>
         
        </form>

      </div>
    </div>
 </div> </div></td>
<td valign="top" class="pL45" style="border-left:0px dotted #ddd;"> 

<div class='popbox' style="float:right; right:82px;"><a href="#" class='open' style="float:right">Edit</a>

   <div class='collapse' style="float:right">
      <div class='box' style="float:right">

          <form method="post" name="" id="" action="" onsubmit="">
<div class="contact-box1">
<div class="headdings-inner" style="margin-top:0px;">Personal Details</div>
<div class="clear"></div>

  <div class="input-main-container1"> 
<div class="input-name1">First Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Last Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Address :</div>
<div class="input-container1">

  <textarea name="comments" rows="2" class="input3" style="height:50px;"></textarea>
</div>
</div>

<div class="input-main-container1" style="margin-bottom:0px"> 
<div class="input-name1">Phone :</div>
<div class="input-container1">
  <div style="float:left; width:79px; margin-right:7px;"><input type="text" style="width:70px;" name="std" class="input3" /></div>
  <div style="float:left; width:130px"><input type="text" style="width:130px;" name="ph" class="input3" /></div>
  
  <div style="float:left; width:79px; padding-right:5px; font-size:11px;">Area Code.</div>
  <div style="float:left; width:132px; font-size:11px;">Telephone No.</div>
  
</div>



</div>


<div class="clear"></div>

<div class="input-main-container1"> 
<div class="input-name1">Select :</div>
<div class="input-container1">
  <select name="Interest" class="input3" style="width:228px; height:25px;">
    <option value="" selected="selected"> --Select-- </option>
    <option value="Residential">Abcdef</option>
    
    
  </select>
</div>
</div>





<div class="clear"></div>
<div class="input-main-container1"> 
<div class="input-name1"></div>
<div class="input-container1">
<div style="float:left; margin-right:15px;"><a href="#" class="open">Submit</a></div><div style="float:left"><a href="#" class="open close">Cancel</a></div>
</div>

</div>
</div>
         
        </form>

      </div>
    </div>
 </div>

<div class="fh2">Process Information</div>
<div class="clear"></div>
<br>
 <table width="100%" cellspacing="0" cellpadding="0"><tbody>
 <tr>
  <td class="cell">Language</td> 
   <td class="cell"><strong>English (United States)</strong></td>
    </tr><tr><td class="cell">Country Locale</td> <td class="cell">
     <strong>United States</strong></td></tr><tr><td class="cell">Time Zone</td> 
      <td class="cell"><strong>Pacific Standard Time</strong></td>
       </tr><tr><td class="cell">Currency Locale</td> 
       <td class="cell"><strong>United States</strong>
       </td>
       </tr>
</tbody></table><br><br><br>

<div class='popbox' style="float:right; right:82px;"><a href="#" class='open' style="float:right">Edit</a>

   <div class='collapse' style="float:right">
      <div class='box' style="float:right">

          <form method="post" name="" id="" action="" onsubmit="">
<div class="contact-box1">
<div class="headdings-inner" style="margin-top:0px;">Personal Details</div>
<div class="clear"></div>

  <div class="input-main-container1"> 
<div class="input-name1">First Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Last Name* : </div>
<div class="input-container1">
  <input type="text" name="name" class="input3" />
</div>
</div>

<div class="input-main-container1"> 
<div class="input-name1">Address :</div>
<div class="input-container1">

  <textarea name="comments" rows="2" class="input3" style="height:50px;"></textarea>
</div>
</div>

<div class="input-main-container1" style="margin-bottom:0px"> 
<div class="input-name1">Phone :</div>
<div class="input-container1">
  <div style="float:left; width:79px; margin-right:7px;"><input type="text" style="width:70px;" name="std" class="input3" /></div>
  <div style="float:left; width:130px"><input type="text" style="width:130px;" name="ph" class="input3" /></div>
  
  <div style="float:left; width:79px; padding-right:5px; font-size:11px;">Area Code.</div>
  <div style="float:left; width:132px; font-size:11px;">Telephone No.</div>
  
</div>



</div>


<div class="clear"></div>

<div class="input-main-container1"> 
<div class="input-name1">Select :</div>
<div class="input-container1">
  <select name="Interest" class="input3" style="width:228px; height:25px;">
    <option value="" selected="selected"> --Select-- </option>
    <option value="Residential">Abcdef</option>
    
    
  </select>
</div>
</div>





<div class="clear"></div>
<div class="input-main-container1"> 
<div class="input-name1"></div>
<div class="input-container1">
<div style="float:left; margin-right:15px;"><a href="#" class="open">Submit</a></div><div style="float:left"><a href="#" class="open close">Cancel</a></div>
</div>

</div>
</div>
         
        </form>

      </div>
    </div>
 </div> 
 
 <div class="fh2">Signature</div><br><pre style="box-shadow:0 1px 4px rgba(0, 0, 0, 0.2); padding:20px 20px 25px;display:none" id="currentUsrSignature" class="graytxt"></pre><br><br><br> </td></tr></tbody></table>

</div>
</body>
</html>
