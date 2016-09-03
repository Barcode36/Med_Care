<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
        });

</script>
<div class="page_title"><h1>Server Configuration Chunk Mapping</h1></div>
<div class="container_block">
						<div class="form_inner">
							<div class="page_form">
							
							<s:form name="ServerConfiguration"  id="ServerConfiguration_url" action="ServerSpaceConfiguration"  theme="css_xhtml" theme="simple"  method="post">
							<div class="cssn_form">
									<h2>Please fill up the following details</h2>
									<div class="fields">
										<div class="fields_block">
											<label>Slab from</label>
											<s:textfield name="slabfromAccount" id="slabfromAccount" cssClass="form_menu_inputtext" maxlength="20" placeholder="From Account Id"></s:textfield>
										</div>
										<div class="fields_block">
											<label>Slab to</label>
											<s:textfield name="slabtoAccount" id="slabtoAccount" cssClass="form_menu_inputtext" maxlength="20" placeholder="To Account Id"></s:textfield>
										</div>
									</div>
									<div class="fields">
										<label>Domain Address/IP</label>
										<s:textfield name="domainIpName" id="domainIpName" cssClass="form_menu_inputtext" maxlength="20" placeholder="Enter Ip/Domain Name"></s:textfield>
									</div>
									<div class="fields">
										<label>Country</label>
										<s:select 
						                              id="country"
						                              name="country" 
						                              list="countryList"
						                              listKey="iso_code"
						                              listValue="contryName"
						                              headerKey="-1"
						                              headerValue="Country" 
						                              cssClass="form_menu_inputselect"
						                              >
						                  			</s:select>
									</div>
									
									
									<div class="fields">
									<div class="sub_field_main" style="text-align:center;"><img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/></div>
									</div>
									
									
									
									
									<div class="fields">
									   <sj:div id="foldeffect"  effect="fold">
                    				   <div id="Result"></div>
          						       </sj:div>
									</div>
									
									<div class="fields">
										<ul>
											<li class="submit">
												 <sj:submit 
								                              targets="Result" 
								                              clearForm="true"
								                              id="submit"
								                              value="Save" 
								                              href="%{ServerConfiguration_url}"
								                              effect="foldeffect"
								                              cssClass="submit"
								                              effectOptions="{float:'right'}"
								                              effectDuration="5000"
								                              button="true"
								                              onCompleteTopics="complete;"
								                              indicator="indicator"
								                            />
												</li>
											<li class="cancel">
												<input type="submit" class="submit" name="" id="" value="Cancel" /></li>
										</ul>
									</div>
								</div>
								</s:form>
								
							</div>
						</div>
					</div>
