<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
		  });
function back()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/BeforeViewFloorSetting.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{headingName}"/></div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div> 
				
	          	 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:300px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:if test="flag=='CreateFloor'">
<s:form id="formone" name="formone" action="addFloor"  theme="simple"  method="post" enctype="multipart/form-data">
	<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
    <s:iterator value="pageFieldsColumns" status="status">
    <s:if test="#status.odd">
    <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
         </div>
    </div>
    </s:if>    
    <s:else>
     <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
         </div>
    </div>
    </s:else>      
   </s:iterator>     
   
   
   <div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: 44%;margin-top: 9%;">+</sj:a></div>
    <s:iterator begin="100" end="110" var="levelIndx">
	      <div id="<s:property value="%{#levelIndx}"/>" style="display: none">
		    <div class="clear"></div>  
		 <s:iterator value="pageFieldsColumns" status="status">
		    <s:if test="#status.odd">
		    <div class="newColumn">
			     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
				 <div class="rightColumn">
					 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
		         </div>
		    </div>
		    </s:if>    
		    <s:else>
		     <div class="newColumn">
			     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
				 <div class="rightColumn">
					 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
		         </div>
		    </div>
		    </s:else>      
   		</s:iterator> 
			
			<div style="float: left;margin-left: 85%;margin-top: -18px;width: 44%">	
					<s:if test="#levelIndx==110">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#levelIndx+1}')" button="true" cssStyle="margin-left: 107px;margin-top: -16px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" cssStyle="margin-left: 117px;margin-top: -16px;" button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		 </s:iterator>
   
       
	<div class="clear"></div>
	 <div class="fields" align="center">
		<ul>
		<li class="submit" style="background: none;">
			<div class="type-button">
			<div id="ButtonDiv">
               <sj:submit 
                       id="onlineSubmitId"
                       targets="resultTarget" 
                       clearForm="true"
                       value="Save" 
                       button="true"
                       disabled="false"
                       onCompleteTopics="makeEffect"
                       cssStyle="margin-left: 53px;"
		            />
		             <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="back();"
						>
						Back
					</sj:a>
		            
		    </div>
		       <div id="resultTarget"></div>
		      </div>
           </li>
		</ul>
	</div>

</s:form>
</s:if>

<!-- ************************************************************** Code For Wings Mapping ************************************************************** -->
<s:if test="flag=='CreateWings'">

<s:form id="formthree" name="formthree" action="addWings"  theme="simple"  method="post" enctype="multipart/form-data">
	<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
    <s:iterator value="pageFieldsColumns" status="status">
    <s:if test="#status.odd">
    <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:select  
	                          id="%{key}"
	                          name="%{key}"
	                          list="floorMap"
	                          headerKey="-1"
	                          headerValue="--Select Floor--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
                          >
                </s:select>
         </div>
    </div>
    </s:if>    
    <s:else>
     <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Wings" onchange="Reset('.pIds');"/>
         </div>
    </div>
    </s:else>      
   </s:iterator> 
   
    <div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: 44%;margin-top: 9%;">+</sj:a></div>
    <s:iterator begin="100" end="110" var="levelIndx">
	      <div id="<s:property value="%{#levelIndx}"/>" style="display: none">
		    <div class="clear"></div>  
		 <s:iterator value="pageFieldsColumns" status="status" begin="1">
		    <div class="newColumn">
			     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
				 <div class="rightColumn">
					 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
		         </div>
		    </div>
   		</s:iterator> 
			
			<div style="float: left;margin-left: 85%;margin-top: -18px;width: 44%">	
					<s:if test="#levelIndx==110">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#levelIndx+1}')" button="true" cssStyle="margin-left: 107px;margin-top: -16px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" cssStyle="margin-left: 117px;margin-top: -16px;" button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		 </s:iterator>
       
   
	<div class="clear"></div>
	 <div class="fields" align="center">
		<ul>
		<li class="submit" style="background: none;">
			<div class="type-button">
			<div id="ButtonDiv">
               <sj:submit 
                       id="onlineSubmitId"
                       targets="resultTarget" 
                       clearForm="true"
                       value="Save" 
                       button="true"
                       disabled="false"
                       onCompleteTopics="makeEffect"
                       cssStyle="margin-left: 53px;"
		            />
		              <sj:a 
						button="true" href="#"
						onclick="resetForm('formthree');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="back();"
						>
						Back
					</sj:a>
		    </div>
		       <div id="resultTarget"></div>
		      </div>
           </li>
		</ul>
	</div>

</s:form>


</s:if>



<!-- ************************************************************** Code For Room Mapping ************************************************************** -->
<s:if test="flag=='CreateRoom'">

<s:form id="formtwo" name="formtwo" action="addRoom"  theme="simple"  method="post" enctype="multipart/form-data">
	<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
    <s:iterator value="pageFieldsColumns" status="status" begin="0" end="0">
    <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:select  
	                          id="%{key}"
	                          name="%{key}"
	                          list="floorMap"
	                          headerKey="-1"
	                          headerValue="--Select Floor--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="commonSelectAjaxCall2('wings_detail','id','wingsname','floorname',this.value,'','','wingsname','ASC','wingsname','Select Wings');Reset('.pIds');">
                </s:select>
         </div>
    </div>
    </s:iterator>     
     <s:iterator value="pageFieldsColumns" status="status" begin="2" end="2">
    <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:select  
	                          id="%{key}"
	                          name="%{key}"
	                          list="{'No Data'}"
	                          headerKey="-1"
	                          headerValue="--Select Wings--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
                          >
                </s:select>
         </div>
    </div>
    </s:iterator>     
     
     <s:iterator value="pageFieldsColumns" status="status" begin="1" end="1">
     <div class="newColumn">
	     <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Room no & Ext. i.e 309#304," onchange="Reset('.pIds');"/>
         </div>
    </div>
   </s:iterator>     
   
	<div class="clear"></div>
	 <div class="fields" align="center">
		<ul>
		<li class="submit" style="background: none;">
			<div class="type-button">
			<div id="ButtonDiv">
               <sj:submit 
                       id="onlineSubmitId"
                       targets="resultTarget" 
                       clearForm="true"
                       value="Save" 
                       button="true"
                       disabled="false"
                       onCompleteTopics="makeEffect"
                       cssStyle="margin-left: 53px;"
		            />
		              <sj:a 
						button="true" href="#"
						onclick="resetForm('formtwo');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="back();"
						>
						Back
					</sj:a>
		    </div>
		       <div id="resultTarget"></div>
		      </div>
           </li>
		</ul>
	</div>

</s:form>


</s:if>


	
</div>
</div>
</body>
</html>