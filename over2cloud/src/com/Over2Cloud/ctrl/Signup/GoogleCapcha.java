package com.Over2Cloud.ctrl.Signup;
/*package com.dreamsol.ctrl.Signup;

import com.opensymphony.xwork2.ActionSupport;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
public class GoogleCapcha extends ActionSupport
{
private String recaptcha_challenge_field;
private String recaptcha_response_field;

	public String CapchaVrification()
	{
		
		String remoteAddr = request.getRemoteAddr();
	    ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
	    reCaptcha.setPrivateKey("<font color=red>6LcOQNsSAAAAAA2Q7wy_7byyIAaMjqHmGyTD8I2q</font>");
	    String challenge = request.getParameter("recaptcha_challenge_field");
	    String uresponse = request.getParameter("recaptcha_response_field");
	    ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
	    if (reCaptchaResponse.isValid()) {
	      System.out.print("Answer was entered correctly!");
	    } else {
	      System.out.print("Answer is wrong");
	    }	
		
		
		
		
		return SUCCESS;
	}
	

	
}
*/