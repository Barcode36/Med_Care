<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<body>

<script type="text/javascript">
function SelectConfigurtion(valuepassed){
var valuepasseds=valuepassed.value;
var conf_table=valuepassed.title;
var conP = "<%=request.getContextPath()%>";
$("#dividsss").load(conP+"/view/CloudApps/GetLevelConfiguration?id="+valuepasseds+"&mapedtable="+conf_table.split(" ").join("%20"));
 $("#dividsss").dialog('open');
  
}
</script>
    <sj:dialog 
      	id="dividsss" 
      	 		buttons="{ 
    		'Save':function() { oksaveButton(); },
    		'Cancel':function() { cancelButton(); } 
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Dialog with Effect"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="900"
		height="600"
    >
    </sj:dialog>
    <s:property value=""/>
    <s:property value="%{adresstabe}"/>
<sj:accordion id="accordion" autoHeight="false">
<s:if test="basictabe!=''">
	<s:form name="formone"  id="AddConfigmgmt_Url" action="addconfiguration"  theme="css_xhtml">
	  <table align="center">
		<tr class="tdcell" style="display: none;">
			<td align="left">
				<label class="lablecell"></label>
			</td>
			<td align="center">
				<label class="lablecell"><span id="tid"><s:property value="id"></s:property></span> </label>
			</td>
	
		</tr>
		</table>
	
		<sj:accordionItem title="%{basictabe}">
			<s:if test="%{conf_level}">
				<s:select 
					id="level"
					name="level" 
					list="putLevel"
					headerKey="-1"
					headerValue="-Select Level For Creation-" 
					cssClass="form_menu_inputselect"
					title="%{conf_table}"
					onchange="SelectConfigurtion(this);"
				>
				</s:select>
			</s:if>
		<div class="steps_content" >
			<div class="form_content">
				<ul>
				<li style="display: none;"><span id="maped"><s:property value="mapedtable"/></span>
					<s:textarea name="mapedtable" ></s:textarea>
				</li>
					<s:iterator id="ConfigurationUtilBean" value="basicconfigBean" >
						<s:set name="conid" value="%{id}" />
							<li>
								<s:checkbox name="titles" title="%{id}-%{queryNames}" id="titles" value="true" fieldValue="%{conid}"  theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
							</li>
							<li style="display: none;">
								<span id="queryNames"><s:property value="queryNames"/>	</span>
								<s:textarea name="queryNames" ></s:textarea>
							</li>
					</s:iterator>
				</ul>
			</div>
			<div class="fields">
			<ul></ul>	
				<ul>
					<li class="submit">
						<div class="type-button">
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
								<sj:submit 
									targets="Result" 
									clearForm="true"
									value="  Register  " 
									href="%{AddConfigmgmt_Url}"
									effect="highlight"
									effectOptions="{ color : '#222222'}"
									effectDuration="5000"
									button="true"
									onCompleteTopics="complete"
									cssClass="submit"
								/>
						</div>
						<sj:div id="foldeffect"  effect="fold">
						<div id="Result"></div>
						</sj:div>
					</li>
				</ul>
			</div>
		</div>
		</sj:accordionItem>
	</s:form>
 </s:if>
 <s:if test="adresstabe!=''">
	<sj:accordionItem title="%{adresstabe}">
		<s:form name="formtwo"  id="AddAdressConfigmgmt_Url" action="addconfiguration"  theme="css_xhtml">
			<table align="center">
				<tr class="tdcell" style="display: none;">
					<td align="left">
						<label class="lablecell"></label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="tid"><s:property value="id"></s:property></span> </label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="mapedtable"><s:property value="mapedtable"></s:property></span> </label>
					</td>
				</tr>
			</table>
			
			<div>
				<div class="steps_content">
					<div class="form_content">
						<ul>
							<s:iterator id="ConfigurationUtilBean" value="addressconfigBean" >
							<s:set name="conid" value="%{id}" />
							<li>
								<s:checkbox name="titles" title="%{id}-%{queryNames}" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite(this);" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
							</li>
							<li style="display: none;">
								<span id="queryNames"><s:property value="queryNames"/>	</span>
								<s:textarea name="queryNames" ></s:textarea>
							</li>
							</s:iterator>
						</ul>
					</div>
					<div class="fields">
						<ul></ul>	
						<ul>
							<li class="submit">
								<div class="type-button">
									<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
										<sj:submit 
											targets="Result" 
											clearForm="true"
											value="  Register  " 
											href="%{AddAdressConfigmgmt_Url}"
											effect="highlight"
											effectOptions="{ color : '#222222'}"
											effectDuration="5000"
											button="true"
											onCompleteTopics="complete"
											cssClass="submit"
										/>
								</div>
								<sj:div id="foldeffect"  effect="fold">
								<div id="Result"></div>
								</sj:div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</s:form>
	</sj:accordionItem>
</s:if>
<s:if test="customtabe!=''">

	<sj:accordionItem title="%{customtabe}">
		<s:form name="formthree"  id="AddCustmConfigmgmt_Url" action="addconfiguration"  theme="css_xhtml">
			<table align="center">
				<tr class="tdcell" style="display: none;">
					<td align="left">
						<label class="lablecell"></label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="tid"><s:property value="id"></s:property></span> </label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="mapedtable"><s:property value="mapedtable"></s:property></span> </label>
					</td>
				</tr>
			</table>
			
			<div>
				<div class="steps_content">
					<div class="form_content">
						<ul>
							<s:iterator id="ConfigurationUtilBean" value="customconfigBean" >
								<s:set name="conid" value="%{id}" />
									<li><s:checkbox name="titles" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</li>
								<li style="display: none;">
									<span id="queryNames"><s:property value="queryNames"/>	</span>
									<s:textarea name="queryNames" ></s:textarea>
								</li>	
							</s:iterator>
						</ul>
					</div>
					<div class="fields">
						<ul></ul>	
						<ul>
							<li class="submit">
								<div class="type-button">
									<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
										<sj:submit 
											targets="Result" 
											clearForm="true"
											value="  Register  " 
											href="%{AddAdressConfigmgmt_Url}"
											effect="highlight"
											effectOptions="{ color : '#222222'}"
											effectDuration="5000"
											button="true"
											onCompleteTopics="complete"
											cssClass="submit"
										/>
								</div>
								<sj:div id="foldeffect"  effect="fold">
								<div id="Result"></div>
								</sj:div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</s:form>
	</sj:accordionItem>
</s:if>
<s:if test="desictabe!=''">	

	<sj:accordionItem title="%{desictabe}">
		<s:form name="formtfour"  id="AddDesiConfigmgmt_Url" action="addconfiguration"  theme="css_xhtml">
			<table align="center">
				<tr class="tdcell" style="display: none;">
					<td align="left">
						<label class="lablecell"></label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="tid"><s:property value="id"></s:property></span> </label>
					</td>
					<td align="center">
						<label class="lablecell"><span id="mapedtable"><s:property value="mapedtable"></s:property></span> </label>
					</td>
				</tr>
			</table>
		
			<div>
				<div class="steps_content">
					<div class="form_content">
						<ul>
							<s:iterator id="ConfigurationUtilBean" value="customconfigBean" >
								<s:set name="conid" value="%{id}" />
									<li><s:checkbox name="titles" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
								</li>
								<li style="display: none;">
									<span id="queryNames"><s:property value="queryNames"/>	</span>
									<s:textarea name="queryNames" ></s:textarea>
								</li>	
							</s:iterator>
						</ul>
					</div>
					<div class="fields">
						<ul></ul>	
						<ul>
							<li class="submit">
								<div class="type-button">
									<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
										<sj:submit 
											targets="Result" 
											clearForm="true"
											value="  Register  " 
											href="%{AddAdressConfigmgmt_Url}"
											effect="highlight"
											effectOptions="{ color : '#222222'}"
											effectDuration="5000"
											button="true"
											onCompleteTopics="complete"
											cssClass="submit"
										/>
								</div>
								<sj:div id="foldeffect"  effect="fold">
								<div id="Result"></div>
								</sj:div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</s:form>
	</sj:accordionItem>
	</s:if>	
</sj:accordion>
</body>
