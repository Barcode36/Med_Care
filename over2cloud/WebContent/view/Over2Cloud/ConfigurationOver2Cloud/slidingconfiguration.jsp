
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">
#accordion{
	float:left; width:862px!important;
}
.accordion_block{
	float:left; width:100%; padding:0px 0px 1px 0px;
}
#accordion h3.ui-accordion-header{
	float:left; width:99.7%; padding:0.5em 0 0.5em 0!important; text-indent:25px; background:url(images/block_heading.png) repeat;
	border:1px solid #D6D3CF; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px; -o-border-radius:5px; color:#ffffff;
	cursor:pointer; font-weight:normal; font-size:13px;
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{
	background:url(images/tabs_bg_active.png) repeat-x!important;
}
#accordion div.ui-accordion-content{
	float:left; width:97.5%; padding:0; background:url(/images/content_bg.png); padding:1%; border:1px solid #D6D3CF;
}
</style>
<s:hidden id="levelIDName"></s:hidden> <!-- For Setting the selected levelid -->
<div id="accordion">
<div class="accordion_block">
	<h3 class="ui-accordion-header" id="<s:property value="%{basictabe}"/>" onclick="tom(${content1});"><s:property value="%{basictabe}"/></h3>
	<s:if test="%{conf_level}">
	<div style="float:left; width:100%; padding:10px 0px;">
		<s:select 
			id="level"
			name="level" 
			list="putLevel"
			headerKey="-1"
			headerValue="Select Level for Configuration" 
			cssClass="btn createNewBtn"
			
			title="%{conf_table}"
			onchange="SelectConfigurtion(this);"
		>
		</s:select>
	</div>
	
	</s:if>
<!-- For Basic Data -->
<s:if test="basictabe!=''">

<div class="ui-accordion-content" style="display:block; float:left;" id="<s:property value="%{content1}"/>">
	<s:form name="two">
		<div style="float:left; width:100%; display: none;"><span id="maped"><s:property value="%{mapedtable}"/>	</span></div>
		<div class="steps_content" style="padding:10px 2.5%; float:left; width:95%;">
			<div class="form_content" style="max-height:210px; padding-top:0; width:100%; margin:0px 0px 0px 0px;">
				<ul>
					<s:iterator id="ConfigurationUtilBean" value="basicconfigBean" >
						<s:set name="conid" value="%{id}" />
						<li>
							<s:if test="%{mandatory}">
							<s:checkbox name="titles1" title="%{id}-%{queryNames}" id="titles1" value="true" fieldValue="%{conid}"  theme="simple" disabled="true"/>&nbsp;&nbsp;<s:property value="field_value"/>
							</s:if>
							<s:else>
							<s:checkbox name="titles1" title="%{id}-%{queryNames}" id="titles1" value="true" fieldValue="%{conid}"  theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
							</s:else>
						</li>
					</s:iterator>
				</ul>
			</div>
			<div class="fields" style="width:100%; padding:0;">
				<ul>
					<li class="submit">
						<input type="button" value="New Add" id="" title="<s:property value="%{mapedtable}"/>" name="" class="btn createNewBtn" onclick="getFormValue('titles1',this);">
					</li>										
				</ul>
			<div id="abccc"></div>
			</div>
		</div>
	</s:form>
</div>
</s:if>
<s:if test="adresstabe!=''">

<!-- For Address Info -->
<h3 class="ui-accordion-header" id="<s:property value="%{adresstabe}"/>" onclick="tom(${content2});"><s:property value="%{adresstabe}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content2}"/>">
		<s:form name="three">
			<div style="float:left; width:100%; display: none;"><span id="maped"><s:property value="%{mapedtable}"/>	</span></div>
			<div class="steps_content" style="padding:10px 2.5%; float:left; width:95%;">
				<div class="form_content" style="max-height:210px; padding-top:0; width:100%; margin:0px 0px 0px 0px;">
					<ul>
						<s:iterator id="ConfigurationUtilBean" value="addressconfigBean" >
							<s:set name="conid" value="%{id}" />
								<li>
									<s:if test="%{mandatory}">
									<s:checkbox name="titles2" title="%{id}-%{queryNames}" id="titles2" value="true" fieldValue="%{conid}" onclick="OnlickEdite(this);" theme="simple" disabled="true"/>&nbsp;&nbsp;<s:property value="field_value"/>
									</s:if>
									<s:else>
									<s:checkbox name="titles2" title="%{id}-%{queryNames}" id="titles2" value="true" fieldValue="%{conid}" onclick="OnlickEdite(this);" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
									</s:else>
								</li>
								<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
							</li>
						</s:iterator>
					</ul>
				</div>
				<div class="fields" style="width:100%; padding:0;">
					<ul>
						<li class="submit">
							<input type="button" value="New Add" id="" title="<s:property value="%{mapedtable}"/>" name="" class="btn createNewBtn" onclick="getFormValue('titles2',this);">
						</li>										
					</ul>
				<div id="abccc"></div>
				</div>
			</div>	
		</s:form>
	</div>
</s:if>
<s:if test="customtabe!=''">
					
<!-- For Address Info -->						
<h3 class="ui-accordion-header" id="%{customtabe}" onclick="tom(${content3});"><s:property value="%{customtabe}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content3}"/>">
		<s:form name="fourtff">
			<div style="float:left; width:100%; display: none;"><span id="maped"><s:property value="%{mapedtable}"/></span></div>
			<div class="steps_content" style="padding:10px 2.5%; float:left; width:95%;">
				<div class="form_content" style="max-height:210px; padding-top:0; width:100%; margin:0px 0px 0px 0px;">
					<ul>
						<s:iterator id="ConfigurationUtilBean" value="customconfigBean" >
							<s:set name="conid" value="%{id}" />
							<li>
							    <s:if test="%{mandatory}">
								<s:checkbox name="titles3" id="titles3" title="%{id}-%{queryNames}" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple" disabled="true"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</s:if>
								<s:else>
								<s:checkbox name="titles3" id="titles3" title="%{id}-%{queryNames}" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</s:else>
							</li>
					</s:iterator>
						<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
					</li>
					</ul>
				</div>
				<div class="fields" style="width:100%; padding:0;">
					<ul>
						<li class="submit">
							<input type="button" value="New Add" id="" title="<s:property value="%{mapedtable}"/>" name="" class="btn createNewBtn" onclick="getFormValue('titles3',this);">
						</li>										
					</ul>
				<div id="abccc"></div>
				</div>
			</div>
		</s:form>
	</div>
</s:if>
<s:if test="desictabe!=''">

<!-- For Descriptive Info -->
<h3 class="ui-accordion-header"  id="%{desictabe}" onclick="tom(${content4});"><s:property value="%{desictabe}"/></h3>
	<div><div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content4}"/>">
		<s:form name="fourtff">
		<div style="float:left; width:100%; display: none;"><span id="maped"><s:property value="%{mapedtable}"/></span></div>
			<div class="steps_content" style="padding:10px 2.5%; float:left; width:95%;">
				<div class="form_content" style="max-height:210px; padding-top:0; width:100%; margin:0px 0px 0px 0px;">
					<ul>
						<s:iterator id="ConfigurationUtilBean" value="descriptiveBean" >
							<s:set name="conid" value="%{id}" />
							<li>
							    <s:if test="%{mandatory}">
								<s:checkbox name="titles4" id="titles4" title="%{id}-%{queryNames}" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple" disabled="true"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</s:if>
								<s:else>
								<s:checkbox name="titles4" id="titles4" title="%{id}-%{queryNames}" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</s:else>
							</li>
						</s:iterator>
					<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
					</li>
				</ul>
				</div>
				<div class="fields" style="width:100%; padding:0;">
					<ul>
						<li class="submit">
							<input type="button" value="New Add" id="" title="<s:property value="%{mapedtable}"/>" name="" class="btn createNewBtn" onclick="getFormValue('titles4',this);">
						</li>										
					</ul>
				<div id="abccc"></div>
				</div>
			</div>
		</s:form>
	</div>
	</s:if>	
<!-- For Descriptive Info -->
<center><b>*Note: For any configuration take in effect kindly re-login & make an entry in the respected module.</b></center>
<center><font color="red"><b>Disabled check box for mandatory properties.</b></font> </center>
</div>
</div>
								
