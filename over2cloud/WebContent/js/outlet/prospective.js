//FROM HERE

//for validating to location field in createExsistingoutlet
function validateLocation()
{
	
	//alert("hello");
	if(document.Addprospective_Url.location.value.length>0)
	{//alert("hello");
		var a =document.Addprospective_Url.location.value;
		var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errLoc.innerHTML="<div class='user_form_inputError2'>Invalid Location Name.</div>";
				var len = (document.Addprospective_Url.location.value.length) - 1;
				document.Addprospective_Url.location.value = (document.Addprospective_Url.location.value).substring(0, len);
				document.Addprospective_Url.location.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errLoc.innerHTML="";
			}
		}
	}
}

//outlet name

function validateOutLetName()
{
	if(document.Addprospective_Url.outlet_name.value.length>0)
	{
		var a =document.Addprospective_Url.outlet_name.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errOutName.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.Addprospective_Url.outlet_name.value.length) - 1;
				document.Addprospective_Url.outlet_name.value = (document.Addprospective_Url.outlet_name.value).substring(0, len);
				document.Addprospective_Url.outlet_name.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errOutName.innerHTML="";
			}
		}
	}
}
//for outlet code.

function validateOutLetCode()
{
	if(document.Addprospective_Url.outlet_Code.value.length>0)
	{
		var a =document.Addprospective_Url.outlet_Code.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errOutCode.innerHTML="<div class='user_form_inputError2'>Invalid Outlet Code.</div>";
				var len = (document.Addprospective_Url.outlet_Code.value.length) - 1;
				document.Addprospective_Url.outlet_Code.value = (document.Addprospective_Url.outlet_Code.value).substring(0, len);
				document.Addprospective_Url.outlet_Code.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errOutCode.innerHTML="";
			}
		}
	}
}

//validate salary 
function validateSalary()
{
	if(document.Addprospective_Url.ba_salary.value.length>0)
	{
		var a =document.Addprospective_Url.ba_salary.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errBaSal.innerHTML="<div class='user_form_inputError2'>Invalid Salary.</div>";
				var len = (document.Addprospective_Url.ba_salary.value.length) - 1;
				document.Addprospective_Url.ba_salary.value = (document.Addprospective_Url.ba_salary.value).substring(0, len);
				document.Addprospective_Url.ba_salary.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errBaSal.innerHTML="";
			}
		}
	}
}

//Turn over First.

function validateTurnOver1()
{
	if(document.Addprospective_Url.turnover_Firstmonth.value.length>0)
	{
		var a =document.Addprospective_Url.turnover_Firstmonth.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errTurn1.innerHTML="<div class='user_form_inputError2'>Invalid Salary.</div>";
				var len = (document.Addprospective_Url.turnover_Firstmonth.value.length) - 1;
				document.Addprospective_Url.turnover_Firstmonth.value = (document.Addprospective_Url.turnover_Firstmonth.value).substring(0, len);
				document.Addprospective_Url.turnover_Firstmonth.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errTurn1.innerHTML="";
			}
		}
	}
}


function validateTurnOver2()
{
	if(document.Addprospective_Url.turnover_Secondmonth.value.length>0)
	{
		var a =document.Addprospective_Url.turnover_Secondmonth.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errTurn2.innerHTML="<div class='user_form_inputError2'>Invalid Salary.</div>";
				var len = (document.Addprospective_Url.turnover_Secondmonth.value.length) - 1;
				document.Addprospective_Url.turnover_Secondmonth.value = (document.Addprospective_Url.turnover_Secondmonth.value).substring(0, len);
				document.Addprospective_Url.turnover_Secondmonth.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errTurn2.innerHTML="";
			}
		}
	}
}


function validateTurnOver3()
{
	if(document.Addprospective_Url.turnover_thirddmonth.value.length>0)
	{
		var a =document.Addprospective_Url.turnover_thirddmonth.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errTurn3.innerHTML="<div class='user_form_inputError2'>Invalid Salary.</div>";
				var len = (document.Addprospective_Url.turnover_thirddmonth.value.length) - 1;
				document.Addprospective_Url.turnover_thirddmonth.value = (document.Addprospective_Url.turnover_thirddmonth.value).substring(0, len);
				document.Addprospective_Url.turnover_thirddmonth.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errTurn3.innerHTML="";
			}
		}
	}
}



//For address field
function validateAddress()
{
	if(document.Addprospective_Url.address.value.length>0)
	{
		var a =document.Addprospective_Url.address.value;
		var iChars = "_`@#~!$%^&*()+=[]';{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errAdd.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.Addprospective_Url.address.value.length) - 1;
				document.Addprospective_Url.address.value = (document.Addprospective_Url.address.value).substring(0, len);
				document.Addprospective_Url.address.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errAdd.innerHTML="";
			}
		}
	}
}

//for fax no.

function validateFaxNumber()
{
	if(document.Addprospective_Url.faxNo.value.length>0)
	{
		var a =document.Addprospective_Url.faxNo.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errFax.innerHTML="<div class='user_form_inputError2'>Invalid Fax Number.</div>";
				var len = (document.Addprospective_Url.faxNo.value.length) - 1;
				document.Addprospective_Url.faxNo.value = (document.Addprospective_Url.faxNo.value).substring(0, len);
				document.Addprospective_Url.faxNo.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errFax.innerHTML="";
			}
		}
	}
}


//for web address

function validateWebAddress()
{
	if(document.Addprospective_Url.website.value.length>0)
	{
		var a =document.Addprospective_Url.website.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errWeb.innerHTML="<div class='user_form_inputError2'>Invalid Web Address.</div>";
				var len = (document.Addprospective_Url.website.value.length) - 1;
				document.Addprospective_Url.website.value = (document.Addprospective_Url.website.value).substring(0, len);
				document.Addprospective_Url.website.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errWeb.innerHTML="";
			}
		}
	}
}


//for phone number field.
function validatePhoneNumber()
{
	if(document.Addprospective_Url.phoneNo.value.length>0)
	 {
		 var a =document.Addprospective_Url.phoneNo.value;
		var iChars = "0123456789";
		 
		// var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		 for (var i = 0; i< a.length; i++)
		 {
			 if (iChars.indexOf(a.charAt(i)) == -1)
			 {
				 errPhon.innerHTML="<div class='user_form_inputError2'>Invalid Phone No</div>";
				 var len = (document.Addprospective_Url.phoneNo.value.length) - 1;
				 document.Addprospective_Url.phoneNo.value = (document.Addprospective_Url.phoneNo.value).substring(0, len);
				 document.Addprospective_Url.phoneNo.focus();
			 }
			 else
			 {
				 errPhon.innerHTML="";
			 }
		 }
	 }
}

//for contact number


function validateContactNumber()
{
	if(document.Addprospective_Url.contact_no.value.length>0)
	{
		var a =document.Addprospective_Url.contact_no.value;
		var iChars = "0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				if(a.charAt(0)=='9' || a.charAt(0)=='8' || a.charAt(0)=='7')
				{
					errContNo.innerHTML="";
					document.Addprospective_Url.contact_no.focus();
				}
				else
				{
					errContNo.innerHTML="<div class='user_form_inputError2'>Invalid Contact No</div>";
					var len = (document.Addprospective_Url.contact_no.value.length) - 1;
					document.Addprospective_Url.contact_no.value = (document.Addprospective_Url.contact_no.value).substring(0, len);
					document.Addprospective_Url.contact_no.focus();
				}
			}
			else
			{
				errContNo.innerHTML="<div class='user_form_inputError2'>Contact No Should be Numeric.</div>";
				var lent = (document.Addprospective_Url.contact_no.value.length) - 1;
				document.Addprospective_Url.contact_no.value = (document.Addprospective_Url.contact_no.value).substring(0, lent);
				document.Addprospective_Url.contact_no.focus();
				event.originalEvent.options.submit = false;
			}
		}
	}
}



//for E-mail validation

function ValidateEmail() {
		//alert("hello");
  var message;
  var formInput= document.Addprospective_Url.contactEmail.value;
  if (stringEmpty(document.Addprospective_Url.contactEmail.value)) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>There is no input value entered.";
  } else if (noAtSign( document.Addprospective_Url.contactEmail.value )) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>does not contain an '@' character.";
  } else if (nothingBeforeAt(document.Addprospective_Url.contactEmail.value )) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>at least one character before the '@'";
  } else if (noLeftBracket(document.Addprospective_Url.contactEmail.value )) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>contains a right square bracket ']'.";
  } else if (noRightBracket(document.Addprospective_Url.contactEmail.value )) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>contains a left square bracket '['.";
  } else if (noValidPeriod(document.Addprospective_Url.contactEmail.value )) {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>must contain a period ('.') character.";
  } else if (noValidSuffix(document.Addprospective_Url.contactEmail.value )) {
     // errOutEmail.innerHTML = "<div class='user_form_inputError2'> must contain a two, three or four character suffix.";
  } else {
  	errOutEmail.innerHTML = "<div class='user_form_inputError2'>The email address is ok.";
      	errOutEmail.innerHTML="" ;
  }

  var objType = typeof(document.Addprospective_Url.contactEmail.value .focus);
  if (objType == "object" || objType == "function") {
       formInput.focus();
  }
  
  return (false);
}

function checkValid () {
  if ( checkValidation ( document.Addprospective_Url.contactEmail.value ) == true ) {
  }

  return ( false );
}

function stringEmpty () {
  // CHECK THAT THE STRING IS NOT EMPTY
  if ( document.Addprospective_Url.contactEmail.value.length < 1 ) {
      return ( true );
  } else {
      return ( false );
  }
}

function noAtSign () {
  // CHECK THAT THERE IS AN '@' CHARACTER IN THE STRING
	var id=document.Addprospective_Url.contactEmail.value ;
  if (id.indexOf ('@', 0) == -1) {
      return ( true )
  } else {
      return ( false );
  }
}

function nothingBeforeAt () {
  // CHECK THERE IS AT LEAST ONE CHARACTER BEFORE THE '@' CHARACTER
	var id=document.Addprospective_Url.contactEmail.value ;
  if ( id.indexOf ( '@', 0 ) < 1 ) {
      return ( true )
  } else {
      return ( false );
  }
}

function noLeftBracket () {
	var id=document.Addprospective_Url.contactEmail.value ;
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR LEFT BRACKET
  if ( id.indexOf ( '[', 0 ) == -1 && id.charAt (id.length - 1) == ']') {
      return ( true )
  } else {
      return ( false );
  }
}

function noRightBracket (formField) {
	
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR RIGHT BRACKET
	
	var id=document.Addprospective_Url.contactEmail.value ;

  if (id.indexOf ( '[', 0 ) > -1 && id.charAt (id.length - 1) != ']') {
      return ( true );
  } else {
      return ( false );
  }
}

function noValidPeriod () {
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.Addprospective_Url.contactEmail.value ;

  if (id.indexOf ( '@', 0 ) > 1 && id.charAt (id.length - 1 ) == ']')
      return ( false );

  // CHECK THAT THERE IS AT LEAST ONE PERIOD IN THE STRING
  if (id.indexOf ( '.', 0 ) == -1)
      return ( true );

  return ( false );
}

function noValidSuffix() {
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.Addprospective_Url.contactEmail.value ;

  if (id.indexOf('@', 0) > 1 && id.charAt(id.length - 1) == ']') {
      return ( false );
  }

  // CHECK THAT THERE IS A TWO OR THREE CHARACTER SUFFIX AFTER THE LAST PERIOD
  var len = id.length;
  var pos = id.lastIndexOf ( '.', len - 1 ) + 1;
  if ( ( len - pos ) < 2 || ( len - pos ) > 4 ) {
      return ( true );
  } else {
      return ( false );
  }
}


//For comments validation
function validateComments()
{
	if(document.Addprospective_Url.Comments.value.length>0)
	{
		var a =document.Addprospective_Url.Comments.value;
		var iChars = "_`~@#!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errComments.innerHTML="<div class='user_form_inputError2'>Only Alphabets.</div>";
				var len = (document.Addprospective_Url.Comments.value.length) - 1;
				document.Addprospective_Url.Comments.value = (document.Addprospective_Url.Comments.value).substring(0, len);
				document.Addprospective_Url.Comments.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errComments.innerHTML="";
			}
		}
	}
}

//For comments validation
function validateReasonForReplacing()
{
	if(document.Addprospective_Url.reasonof_ba_placing.value.length>0)
	{
		var a =document.Addprospective_Url.reasonof_ba_placing.value;
		var iChars = "_`~@#!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				reasnForBA.innerHTML="<div class='user_form_inputError2'>Only Alphabets.</div>";
				var len = (document.Addprospective_Url.reasonof_ba_placing.value.length) - 1;
				document.Addprospective_Url.reasonof_ba_placing.value = (document.Addprospective_Url.reasonof_ba_placing.value).substring(0, len);
				document.Addprospective_Url.reasonof_ba_placing.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				reasnForBA.innerHTML="";
			}
		}
	}
}

//for E-mail validation

function ValidateEmailPersonal() {
		//alert("hello");
  var message;
  var formInput= document.Addprospective_Url.eMailPersonal.value;
  if (stringEmpty1(document.Addprospective_Url.eMailPersonal.value)) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>There is no input value entered.";
  } else if (noAtSign1( document.Addprospective_Url.eMailPersonal.value )) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>does not contain an '@' character.";
  } else if (nothingBeforeAt1(document.Addprospective_Url.eMailPersonal.value )) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>at least one character before the '@'";
  } else if (noLeftBracket1(document.Addprospective_Url.eMailPersonal.value )) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>contains a right square bracket ']'.";
  } else if (noRightBracket1(document.Addprospective_Url.eMailPersonal.value )) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>contains a left square bracket '['.";
  } else if (noValidPeriod1(document.Addprospective_Url.eMailPersonal.value )) {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>must contain a period ('.') character.";
  } else if (noValidSuffix1(document.Addprospective_Url.eMailPersonal.value )) {
     // errCompttrEmail.innerHTML = "<div class='user_form_inputError2'> must contain a two, three or four character suffix.";
  } else {
  	errCompttrEmail.innerHTML = "<div class='user_form_inputError2'>The email address is ok.";
      	errCompttrEmail.innerHTML="" ;
  }

  var objType = typeof(document.Addprospective_Url.eMailPersonal.value .focus);
  if (objType == "object" || objType == "function") {
       formInput.focus();
  }
  
  return (false);
}

function checkValid1 () {
  if ( checkValidation ( document.Addprospective_Url.eMailPersonal.value ) == true ) {
  }

  return ( false );
}

function stringEmpty1 () {
  // CHECK THAT THE STRING IS NOT EMPTY
  if ( document.Addprospective_Url.eMailPersonal.value.length < 1 ) {
      return ( true );
  } else {
      return ( false );
  }
}

function noAtSign1 () {
  // CHECK THAT THERE IS AN '@' CHARACTER IN THE STRING
	var id=document.Addprospective_Url.eMailPersonal.value ;
  if (id.indexOf ('@', 0) == -1) {
      return ( true )
  } else {
      return ( false );
  }
}

function nothingBeforeAt1 () {
  // CHECK THERE IS AT LEAST ONE CHARACTER BEFORE THE '@' CHARACTER
	var id=document.Addprospective_Url.eMailPersonal.value ;
  if ( id.indexOf ( '@', 0 ) < 1 ) {
      return ( true )
  } else {
      return ( false );
  }
}

function noLeftBracket1 () {
	var id=document.Addprospective_Url.eMailPersonal.value ;
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR LEFT BRACKET
  if ( id.indexOf ( '[', 0 ) == -1 && id.charAt (id.length - 1) == ']') {
      return ( true )
  } else {
      return ( false );
  }
}

function noRightBracket1 (formField){
	
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR RIGHT BRACKET
	
	var id=document.Addprospective_Url.eMailPersonal.value ;

  if (id.indexOf ( '[', 0 ) > -1 && id.charAt (id.length - 1) != ']') {
      return ( true );
  } else {
      return ( false );
  }
}

function noValidPeriod1() {
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.Addprospective_Url.eMailPersonal.value ;

  if (id.indexOf ( '@', 0 ) > 1 && id.charAt (id.length - 1 ) == ']')
      return ( false );

  // CHECK THAT THERE IS AT LEAST ONE PERIOD IN THE STRING
  if (id.indexOf ( '.', 0 ) == -1)
      return ( true );

  return ( false );
}

function noValidSuffix1() {
  // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.Addprospective_Url.eMailPersonal.value ;

  if (id.indexOf('@', 0) > 1 && id.charAt(id.length - 1) == ']') {
      return ( false );
  }

  // CHECK THAT THERE IS A TWO OR THREE CHARACTER SUFFIX AFTER THE LAST PERIOD
  var len = id.length;
  var pos = id.lastIndexOf ( '.', len - 1 ) + 1;
  if ( ( len - pos ) < 2 || ( len - pos ) > 4 ) {
      return ( true );
  } else {
      return ( false );
  }
}

function validateCompanyName(){
		//alert("hello");
		if(document.Addprospective_Url.company_name.value.length>0)
		{//alert("hello");
			var a =document.Addprospective_Url.company_name.value;
			var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errCompNam.innerHTML="<div class='user_form_inputError2'>Invalid Company Name.</div>";
					var len = (document.Addprospective_Url.company_name.value.length) - 1;
					document.Addprospective_Url.company_name.value = (document.Addprospective_Url.company_name.value).substring(0, len);
					document.Addprospective_Url.company_name.focus();
					event.originalEvent.options.submit = false;
				}
				else
				{
					errCompNam.innerHTML="";
				}
			}
		}
	}	
	
	
function validateBrandName(){
	//alert("hello");
	if(document.Addprospective_Url.brand.value.length>0)
	{//alert("hello");
		var a =document.Addprospective_Url.brand.value;
		var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errCompBrand.innerHTML="<div class='user_form_inputError2'>Invalid Brand Name.</div>";
				var len = (document.Addprospective_Url.brand.value.length) - 1;
				document.Addprospective_Url.brand.value = (document.Addprospective_Url.brand.value).substring(0, len);
				document.Addprospective_Url.brand.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errCompBrand.innerHTML="";
			}
		}
	}
}	

//validate salary BA
function validateBASalary()
{
	if(document.Addprospective_Url.salary.value.length>0)
	{
		var a =document.Addprospective_Url.salary.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errCompBASal.innerHTML="<div class='user_form_inputError2'>Invalid Salary.</div>";
				var len = (document.Addprospective_Url.salary.value.length) - 1;
				document.Addprospective_Url.salary.value = (document.Addprospective_Url.salary.value).substring(0, len);
				document.Addprospective_Url.salary.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errCompBASal.innerHTML="";
			}
		}
	}
}


//validate Sales 
function validateSales()
{
	if(document.Addprospective_Url.sales.value.length>0)
	{
		var a =document.Addprospective_Url.sales.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errCompSales.innerHTML="<div class='user_form_inputError2'>Invalid Sales.</div>";
				var len = (document.Addprospective_Url.sales.value.length) - 1;
				document.Addprospective_Url.sales.value = (document.Addprospective_Url.sales.value).substring(0, len);
				document.Addprospective_Url.sales.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errCompSales.innerHTML="";
			}
		}
	}
}

function validateComment()
{
	if(document.Addprospective_Url.com_comment.value.length>0)
	{
		var a =document.Addprospective_Url.com_comment.value;
		var iChars = "_`~@#!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errComments.innerHTML="<div class='user_form_inputError2'>Only Alphabets.</div>";
				var len = (document.Addprospective_Url.com_comment.value.length) - 1;
				document.Addprospective_Url.com_comment.value = (document.Addprospective_Url.com_comment.value).substring(0, len);
				document.Addprospective_Url.com_comment.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errComments.innerHTML="";
			}
		}
	}
}
//
function validateSirName(){
		//alert("hello");
		if(document.Addprospective_Url.sirname.value.length>0)
		{//alert("hello");
			var a =document.Addprospective_Url.sirname.value;
			var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errCompSirNam.innerHTML="<div class='user_form_inputError2'>Invalid Sir Name.</div>";
					var len = (document.Addprospective_Url.sirname.value.length) - 1;
					document.Addprospective_Url.sirname.value = (document.Addprospective_Url.sirname.value).substring(0, len);
					document.Addprospective_Url.sirname.focus();
					event.originalEvent.options.submit = false;
				}
				else
				{
					errCompSirNam.innerHTML="";
				}
			}
		}
	}	


function validateName(){
		//alert("hello");
		if(document.Addprospective_Url.name.value.length>0)
		{//alert("hello");
			var a =document.Addprospective_Url.name.value;
			var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errCompttrNam.innerHTML="<div class='user_form_inputError2'>Invalid  Name.</div>";
					var len = (document.Addprospective_Url.name.value.length) - 1;
					document.Addprospective_Url.name.value = (document.Addprospective_Url.name.value).substring(0, len);
					document.Addprospective_Url.name.focus();
					event.originalEvent.options.submit = false;
				}
				else
				{
					errCompttrNam.innerHTML="";
				}
			}
		}
	}	
//

function validateDesignation(){
		//alert("hello");
		if(document.Addprospective_Url.designation.value.length>0)
		{//alert("hello");
			var a =document.Addprospective_Url.designation.value;
			var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errCompttrDesg.innerHTML="<div class='user_form_inputError2'>Invalid Designation Name.</div>";
					var len = (document.Addprospective_Url.designation.value.length) - 1;
					document.Addprospective_Url.designation.value = (document.Addprospective_Url.designation.value).substring(0, len);
					document.Addprospective_Url.designation.focus();
					event.originalEvent.options.submit = false;
				}
				else
				{
					errCompttrDesg.innerHTML="";
				}
			}
		}
	}	
//

function validateDepartments(){
		//alert("hello");
		if(document.Addprospective_Url.department.value.length>0)
		{//alert("hello");
			var a =document.Addprospective_Url.department.value;
			var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errCompttrDept.innerHTML="<div class='user_form_inputError2'>Invalid Company Name.</div>";
					var len = (document.Addprospective_Url.department.value.length) - 1;
					document.Addprospective_Url.department.value = (document.Addprospective_Url.department.value).substring(0, len);
					document.Addprospective_Url.department.focus();
					event.originalEvent.options.submit = false;
				}
				else
				{
					errCompttrDept.innerHTML="";
				}
			}
		}
	}	




function validateContactNum()
{
	if(document.Addprospective_Url.contactNo.value.length>0)
	{
		var a =document.Addprospective_Url.contactNo.value;
		var iChars = "0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				if(a.charAt(0)=='9' || a.charAt(0)=='8' || a.charAt(0)=='7')
				{
					errContNo.innerHTML="";
					document.Addprospective_Url.contactNo.focus();
				}
				else
				{
					errContNo.innerHTML="<div class='user_form_inputError2'>Invalid Contact No</div>";
					var len = (document.Addprospective_Url.contactNo.value.length) - 1;
					document.Addprospective_Url.contactNo.value = (document.Addprospective_Url.contactNo.value).substring(0, len);
					document.Addprospective_Url.contactNo.focus();
				}
			}
			else
			{
				errContNo.innerHTML="<div class='user_form_inputError2'>Contact No Should be Numeric.</div>";
				var lent = (document.Addprospective_Url.contactNo.value.length) - 1;
				document.Addprospective_Url.contactNo.value = (document.Addprospective_Url.contactNo.value).substring(0, lent);
				document.Addprospective_Url.contactNo.focus();
				event.originalEvent.options.submit = false;
			}
		}
	}
}


























