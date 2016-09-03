package com.Over2Cloud.action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;
import com.opensymphony.xwork2.ActionContext;
import com.Over2Cloud.servlet.*;

@SuppressWarnings("serial")
public  class CaptchaAction  extends ActionSupport {
	private Map<String, String> capchavalue;
	public Map<String, String> getCapchavalue() {
		return capchavalue;
	}
	public void setCapchavalue(Map<String, String> capchavalue) {
		this.capchavalue = capchavalue;
	}
	public String execute() throws Exception {
			HttpServletRequest request  = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
			Boolean isResponseCorrect = Boolean.FALSE;
			javax.servlet.http.HttpSession session = request.getSession();
			String parm = request.getParameter("j_captcha_response");
			String c= (String)session.getAttribute(RoseIndiaCaptcha.CAPTCHA_KEY) ;
			if(!parm.equals(c) ){
				capchavalue=new HashMap<String, String>();
				capchavalue.put("msg","Invalid Code! Please try again!");
				capchavalue.put("status","false");
			return SUCCESS;
		}else{
			capchavalue=new HashMap<String, String>();
			capchavalue.put("msg","Code Matched!");
			capchavalue.put("status","true");
		return SUCCESS;
		}
	}
}