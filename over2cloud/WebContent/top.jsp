<%@taglib  prefix="s" uri="/struts-tags" %>
<html>
<head>
</head>
<body>
	<div class="top">
  <div class="logo"><a href="index.html"><img src="images/logo.png" /></a></div>
<div class="search-box" style="display: none;">
<div id="searchbox">
<form id="searchform" action="/search" method="get">
    <input type="text" id="s" name="q" value=""/>
    
    <div id="arrow"><a href="javascript:void(0);" onclick="showDiv('search-form');return false"><img src="images/arrow-down-grey.png" width="8" height="6" /></a></div>
    <input type="image" src="images/blank.gif" id="sbutton" />
</form>
</div>
</div>
<div class="wrap_nav">
          <ul class="nav_links">
          
            <li><a href="javascript:void();"><img src="images/profile-thumb-pic.png" /></a>
              <div class="dropdown profile_dropdown">
                <div class="arrow_dropdown">&nbsp;</div>
                <div class="two_column">
                  <ul>
                    <li><a href="javascript:void();"><img style="border:1px solid #f0f0f2" src="images/profile-pic.png" width="102" height="102" /></a></li>
                   
                  </ul>
                </div>
                <div class="two_column">
                  <ul>
                    <li style="font-weight:bold;"><a href="javascript:void();"><s:property value="#session['empName']" /></a></li>
                    <li style="font-size:11px; padding:0 0 15px 10px; text-align:left;">support@dreamsol.biz</li>
                    <li><a href="javascript:void();" id="myaccountpages">My Account</a></li>
                    <li><a href="<s:url action='logout'/>">Sign Out</a></li>
                  </ul>
                </div>
                <div class="clear"></div>
              </div>
            </li>
 
 
 <li><a href="javascript:void();"><img src="images/setting-icon.png" /></a>
              <div class="dropdown setting_dropdown">
                <div class="arrow_dropdown">&nbsp;</div>
               <div class="two_column">
                  <ul>
                    <li><a href="javascript:void();" id="settingpage">CRM Setting</a></li>
                    <li><a href="javascript:void();">CRM Setting</a></li>
                    <li><a href="javascript:void();">CRM Setting</a></li>
                  </ul>
                </div>
                <div class="two_column">
                  <ul>
                    <li><a href="javascript:void();" id="settingpage">CRM Setting</a></li>
                    <li><a href="javascript:void();">CRM Setting</a></li>
                    <li><a href="javascript:void();">CRM Setting</a></li>
                  </ul>
                </div>
                              
                <div class="clear"></div>
              </div>
            </li>           
 
 <li><a href="javascript:void();"><img src="images/help-icon.png" /></a>
              <div class="dropdown help_dropdown">
                <div class="arrow_dropdown">&nbsp;</div>
                
                  <ul>
                    <li><a href="javascript:void();">Any Help</a></li>
                    <li><a href="javascript:void();">Any Help</a></li>
                    <li><a href="javascript:void();">Any Help</a></li>
                  </ul>
              
                              
                <div class="clear"></div>
              </div>
            </li>
           
          </ul>
       <div id="searchbox-2">
       
       <div style="position: absolute; width: 237px; z-index: 100002; display: none; top: 56px; margin-left:9px; visibility: visible;" id="search-form-2">
<form name="search-form-2" id="search-form-2">
	<table width="100%" cellspacing="0" cellpadding="2" border="0" align="center" class="mailClient mailClientBg">
	<tbody><tr>
		<td>
			<table width="100%" cellspacing="0" cellpadding="2" border="0" class="small">
			<tbody><tr>
					<td  align="right" class="mailSubHeader">
								
					<a  href="javascript:void(0)" onclick="hideDiv('search-form-2');return false"><img border="0" src="images/close.gif"></a>
				</td>
			</tr>
			</tbody></table>
			
			<table width="100%" cellspacing="0" cellpadding="5" border="0" class="small">
												<tbody><tr valign="top">	
									<td class="dvtCellLabel"><input type="checkbox" value="Accounts" class="small" name="search_onlyin"> 
									Organizations</td>
																	
								</tr>
																<tr valign="top">	
									<td class="dvtCellLabel"><input type="checkbox" value="Campaigns" class="small" name="search_onlyin"> 
									Campaigns</td>
																	
								</tr>
																<tr valign="top">	
									<td class="dvtCellLabel"><input type="checkbox" value="CustomerPortal" class="small" name="search_onlyin"> 
									CustomerPortal</td>
																	
								</tr>
																<tr valign="top">	
									<td class="dvtCellLabel"><input type="checkbox" value="HelpDesk" class="small" name="search_onlyin"> 
									Trouble Tickets</td>
																	
								</tr>
																<tr valign="top">	
									
								</tr>
																<tr valign="top">	
									
											</tr></tbody></table>
		</td>
	</tr>
	<tr>
		<td height="30px" align="right" class="mailSubHeader">
			<input type="button" onclick="#" value="Cancel" class="crmbutton small cancel">
			<input type="button" onclick="#" value="Apply" class="crmbutton small create">
		</td>
	</tr>
	</tbody>
	</table>
</form>
</div>

           <form id="searchform-2" action="/search" method="get">
    <input type="text" id="s" name="q" value=""/>
    
    <div id="arrow"><a href="javascript:void(0);" onclick="showDiv('search-form-2');return false"><img src="images/arrow-down-grey.png" width="8" height="6" /></a></div>
    
</form>
           </div>
         
    </div>


</div>
 <div class="clear"></div>
 <div class="black">  
<ul id="mega-menu" class="mega-menu">
	<li><a href="mainFrame.action"><img src="images/home-icon.png" alt="Home" width="16" height="16" title="Home"/></a></li>
	<li><a href="#">My Cloud</a>
    <ul>
				<li><a href="javascript:void();" id="onlineRegistationDetilsView">Registration Details</a></li>
				<li><a href="javascript:void();" id="onlineAssociateDetilsView">Associate Details</a></li>
				<li><a href="javascript:void();" id="onlineOrgnizationalDetilsView">Organizational Details</a></li>
				</ul>
			</li>
            

	            <li><a href="#">My Account</a>
                <ul>
				<li><a href="#" id="accountMgmtView">Account/ Request</a></li>
				<li><a href="#" id="accountMgmtResponce">Response Client</a></li>
				</ul>
                </li>
                <li><a href="#"> Login History</a></li>
               
                
                
	            <li><a href="#">Registration</a>
                 <ul>
	             <li><a href="javascript:void();" id="registation_patner" >Add Registration</a></li>
	             <li><a href="javascript:void();" id="productConfirmation">Product Confirmation</a></li>
	            <li><a href="javascript:void();" id="ourclient">Registered Client</a></li>
	            <li><a href="javascript:void();" id="blockClientAssociate">Block Client</a></li>
                </ul>
                </li>
                <li><a href="#">Settings</a>
                <ul>
				<li><a href="#" id="deleteIndustry">Industry Configuration</a></li>
				<li><a href="#" id="deleteJob">Job Function Area</a></li>
				<li><a href="#" id="MailConfiguration">Mail Configuration</a></li>
        		<li><a href="#" id="viewApplicationRegistration">Application Configuration</a></li>
        		<li><a href="#" id="deletepackRegistration">Pack Configuration</a></li>
        		<li><a href="#" id="clientdbView">Client DB Server</a></li>
        	    <li><a href="#" id="clientsingleSpacereg">Client DB Configuration</a></li>
        	    <li><a href="#" id="configureDemoSpace"><b>Demo Space</b></a></li>
				</ul>
    </li>
	<li><a href="#">Space Configure</a>
    <ul>
				<li><a href="#" id="viewCSC">Chunk Space Server</a></li>
				<li><a href="#" id="RegistrationSpaceConfiguration">Add Chunk Space</a></li>
				<li><a href="#" id="blockChunkSpaceConfig">Block Chunk Space</a></li>
				</ul>
    </li>	
    <li><li><a href="#">Table Configuration</a>
     <ul>
				<li><a href="#" >Module Configuration</a></li>
				<li><a href="#" >Master Configuration</a></li>
				<li><a href="#" >Table Configuration</a></li>
				</ul>
     </li>
	<li><a href="#">More</a>
		<ul>
			<li><a href="#">Sub Head 1</a>
				<ul>
    
				<li><a href="#">Sub Link1</a></li>
				<li><a href="#">Sub Link2</a></li>
				<li><a href="#">Sub Link3</a></li>
        		<li><a href="#">Sub Link4</a></li>
        		<li><a href="#">Sub Link5</a></li>
        
				</ul>
			</li>
            
	<li><a href="#">Sub Head 2</a>
				<ul>
    
				<li><a href="#">Sub Link1</a></li>
				<li><a href="#">Sub Link2</a></li>
				<li><a href="#">Sub Link3</a></li>
        		<li><a href="#">Sub Link4</a></li>
        		<li><a href="#">Sub Link5</a></li>
        
				</ul>
		  </li>
	<li><a href="#">Sub Head 3</a>
				<ul>
    
				<li><a href="#">Sub Link1</a></li>
				<li><a href="#">Sub Link2</a></li>
				<li><a href="#">Sub Link3</a></li>
        		<li><a href="#">Sub Link4</a></li>
        		<li><a href="#">Sub Link5</a></li>
        
				</ul>
		  </li>
    <li><a href="#">Sub Head 4</a>
				<ul>
    
				<li><a href="#">Sub Link1</a></li>
				<li><a href="#">Sub Link2</a></li>
				<li><a href="#">Sub Link3</a></li>
        		<li><a href="#">Sub Link4</a></li>
        		<li><a href="#">Sub Link5</a></li>
        
				</ul>
		  </li>
	
</ul>
</li>

<li><a href="index.html"><img src="images/add.png"/></a>
		<ul>
			<li><a href="#">Sub Link1</a></li>
			<li><a href="#">Sub Link2</a></li>
			<li><a href="#">Sub Link3</a></li>
        	<li><a href="#">Sub Link4</a></li>
        	<li><a href="#">Sub Link5</a></li>
        	</ul>           
</li>

<li><a href="index.html"><img src="images/calendar.png" /></a></li>

</ul>
</div>
<div class="clear"></div>
</body>
</html>
