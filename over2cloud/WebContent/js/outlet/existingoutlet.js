function AjexonBaSalary(BaSalaryDiv,Baid)
{
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSolPages/E0/bAName.action?BaId="+Baid,
	        success : function(Basalary) 
	        {
	    	$("#"+BaSalaryDiv).html(Basalary);},
	        error: function()
	        {alert("error");}
	    }
	  );
}

function AjexonOutletDetils(OutletidDiv,outletId)
{
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSolPages/E0/OutletNameId.action?outletId="+outletId,
	        success : function(OutletList) 
	        {
	    	$("#"+OutletidDiv).html(OutletList);},
	        error: function()
	        {alert("error");}
	    }
	  );
}
function XYZ(CountrzoneDiv,country,AjexonBaNameOutletDiv)
{
   AjexonBaNameOutletFill(AjexonBaNameOutletDiv);
   AjexonCountryZoneFill(CountrzoneDiv,country);
}

function AjexonCountryZoneFill(CountrzoneDiv,country)
{
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSolPages/E0/ContryAction.action?countryvab="+country,
	        success : function(OutletList) 
	        {
	    	$("#"+CountrzoneDiv).html(OutletList);},
	        error: function()
	        {alert("error");}
	    }
	  );
}
function AjexonBaNameOutletFill(AjexonBaNameOutletDiv)
{
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSolPages/E0/OutletandBAname.action?countryvab=Yes",
	        success : function(Outletdetails) 
	        {
	    	$("#"+AjexonBaNameOutletDiv).html(Outletdetails);},
	        error: function()
	        {alert("error");}
	    }
	  );
}
//FROM HERE

//for validating to location field in createExsistingoutlet
function validateLocation()
{
	if(document.formOne.location.value.length>0)
	{
		var a =document.formOne.location.value;
		var iChars = "_`~!$%^&*()@#+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errLoctn.innerHTML="<div class='user_form_inputError2'>Invalid Location Name.</div>";
				var len = (document.formOne.location.value.length) - 1;
				document.formOne.location.value = (document.formOne.location.value).substring(0, len);
				document.formOne.location.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errLoctn.innerHTML="";
			}
		}
	}
}
//For comments validation
function validateComments()
{
	if(document.formOne.Comments.value.length>0)
	{
		var a =document.formOne.Comments.value;
		var iChars = "_`~@#!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errComments.innerHTML="<div class='user_form_inputError2'>Only Alphabets.</div>";
				var len = (document.formOne.Comments.value.length) - 1;
				document.formOne.Comments.value = (document.formOne.Comments.value).substring(0, len);
				document.formOne.Comments.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errComments.innerHTML="";
			}
		}
	}
}

//for E-mail validation

function ValidateEmail() {
		//alert("hello");
    var message;
    var formInput= document.formOne.outletEmail.value;
    if (stringEmpty(document.formOne.outletEmail.value)) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>There is no input value entered.";
    } else if (noAtSign( document.formOne.outletEmail.value )) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>does not contain an '@' character.";
    } else if (nothingBeforeAt(document.formOne.outletEmail.value )) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>at least one character before the '@'";
    } else if (noLeftBracket(document.formOne.outletEmail.value )) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>contains a right square bracket ']'.";
    } else if (noRightBracket(document.formOne.outletEmail.value )) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>contains a left square bracket '['.";
    } else if (noValidPeriod(document.formOne.outletEmail.value )) {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>must contain a period ('.') character.";
    } else if (noValidSuffix(document.formOne.outletEmail.value )) {
       // errOutEmal.innerHTML = "<div class='user_form_inputError2'> must contain a two, three or four character suffix.";
    } else {
    	errOutEmal.innerHTML = "<div class='user_form_inputError2'>The email address is ok.";
        	errOutEmal.innerHTML="" ;
    }

    var objType = typeof(document.formOne.outletEmail.value .focus);
    if (objType == "object" || objType == "function") {
         formInput.focus();
    }
    
    return (false);
}

function checkValid () {
    if ( checkValidation ( document.formOne.outletEmail.value ) == true ) {
    }

    return ( false );
}

function stringEmpty () {
    // CHECK THAT THE STRING IS NOT EMPTY
    if ( document.formOne.outletEmail.value.length < 1 ) {
        return ( true );
    } else {
        return ( false );
    }
}

function noAtSign () {
    // CHECK THAT THERE IS AN '@' CHARACTER IN THE STRING
	var id=document.formOne.outletEmail.value ;
    if (id.indexOf ('@', 0) == -1) {
        return ( true )
    } else {
        return ( false );
    }
}

function nothingBeforeAt () {
    // CHECK THERE IS AT LEAST ONE CHARACTER BEFORE THE '@' CHARACTER
	var id=document.formOne.outletEmail.value ;
    if ( id.indexOf ( '@', 0 ) < 1 ) {
        return ( true )
    } else {
        return ( false );
    }
}

function noLeftBracket () {
	var id=document.formOne.outletEmail.value ;
    // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR LEFT BRACKET
    if ( id.indexOf ( '[', 0 ) == -1 && id.charAt (id.length - 1) == ']') {
        return ( true )
    } else {
        return ( false );
    }
}

function noRightBracket (formField) {
	
    // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN CHECK FOR RIGHT BRACKET
	
	var id=document.formOne.outletEmail.value ;

    if (id.indexOf ( '[', 0 ) > -1 && id.charAt (id.length - 1) != ']') {
        return ( true );
    } else {
        return ( false );
    }
}

function noValidPeriod () {
    // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.formOne.outletEmail.value ;

    if (id.indexOf ( '@', 0 ) > 1 && id.charAt (id.length - 1 ) == ']')
        return ( false );

    // CHECK THAT THERE IS AT LEAST ONE PERIOD IN THE STRING
    if (id.indexOf ( '.', 0 ) == -1)
        return ( true );

    return ( false );
}

function noValidSuffix() {
    // IF EMAIL ADDRESS IN FORM 'user@[255,255,255,0]', THEN WE ARE NOT INTERESTED
	var id=document.formOne.outletEmail.value ;

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

//For OutLet Name

function validateOutLetName()
{
	if(document.formOne.outlet_name.value.length>0)
	{
		var a =document.formOne.outlet_name.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errOutNam.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.formOne.outlet_name.value.length) - 1;
				document.formOne.outlet_name.value = (document.formOne.outlet_name.value).substring(0, len);
				document.formOne.outlet_name.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errOutNam.innerHTML="";
			}
		}
	}
}

//For address field
function validateAddress()
{
	if(document.formOne.address.value.length>0)
	{
		var a =document.formOne.address.value;
		var iChars = "_`@#~!$%^&*()+=[]';{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errAddrs.innerHTML="<div class='user_form_inputError2'>Invalid Name.</div>";
				var len = (document.formOne.address.value.length) - 1;
				document.formOne.address.value = (document.formOne.address.value).substring(0, len);
				document.formOne.address.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errAddrs.innerHTML="";
			}
		}
	}
}
//for web address

function validateWebAddress()
{
	if(document.formOne.website.value.length>0)
	{
		var a =document.formOne.website.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errWeb.innerHTML="<div class='user_form_inputError2'>Invalid Web Address.</div>";
				var len = (document.formOne.website.value.length) - 1;
				document.formOne.website.value = (document.formOne.website.value).substring(0, len);
				document.formOne.website.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errWeb.innerHTML="";
			}
		}
	}
}
//for fax no.

function validateFaxNumber()
{
	if(document.formOne.faxNo.value.length>0)
	{
		var a =document.formOne.faxNo.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errFax.innerHTML="<div class='user_form_inputError2'>Invalid Fax Number.</div>";
				var len = (document.formOne.faxNo.value.length) - 1;
				document.formOne.faxNo.value = (document.formOne.faxNo.value).substring(0, len);
				document.formOne.faxNo.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errFax.innerHTML="";
			}
		}
	}
}

//for fax no.

function validateOutLetCode()
{
	if(document.formOne.outlet_Code.value.length>0)
	{
		var a =document.formOne.outlet_Code.value;
		var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errOutCod.innerHTML="<div class='user_form_inputError2'>Invalid Outlet Code.</div>";
				var len = (document.formOne.outlet_Code.value.length) - 1;
				document.formOne.outlet_Code.value = (document.formOne.outlet_Code.value).substring(0, len);
				document.formOne.outlet_Code.focus();
				event.originalEvent.options.submit = false;
			}
			else
			{
				errOutCod.innerHTML="";
			}
		}
	}
}

//for phone number field.
function validatePhoneNumber()
{
	if(document.formOne.phoneNo.value.length>0)
	 {
		 var a =document.formOne.phoneNo.value;
		var iChars = "0123456789";
		 
		// var iChars = "_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		 for (var i = 0; i< a.length; i++)
		 {
			 if (iChars.indexOf(a.charAt(i)) == -1)
			 {
				 errPhon.innerHTML="<div class='user_form_inputError2'>Invalid Phone No</div>";
				 var len = (document.formOne.phoneNo.value.length) - 1;
				 document.formOne.phoneNo.value = (document.formOne.phoneNo.value).substring(0, len);
				 document.formOne.phoneNo.focus();
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
	if(document.formOne.contact_no.value.length>0)
	{
		var a =document.formOne.contact_no.value;
		var iChars = "0123456789";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				if(a.charAt(0)=='9' || a.charAt(0)=='8' || a.charAt(0)=='7')
				{
					errCont.innerHTML="";
					document.formOne.contact_no.focus();
				}
				else
				{
					errCont.innerHTML="<div class='user_form_inputError2'>Invalid Contact No</div>";
					var len = (document.formOne.contact_no.value.length) - 1;
					document.formOne.contact_no.value = (document.formOne.contact_no.value).substring(0, len);
					document.formOne.contact_no.focus();
				}
			}
			else
			{
				errCont.innerHTML="<div class='user_form_inputError2'>Contact No Should be Numeric.</div>";
				var lent = (document.formOne.contact_no.value.length) - 1;
				document.formOne.contact_no.value = (document.formOne.contact_no.value).substring(0, lent);
				document.formOne.contact_no.focus();
				event.originalEvent.options.submit = false;
			}
		}
	}
}


function validateBASalary()
{
	if(document.formOne.outletBasicSalary.value.length>0)
	{
		var a =document.formOne.outletBasicSalary.value;
		var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
		for(var i=0;i<a.length;i++)
		{
			if (iChars.indexOf(a.charAt(i)) != -1)
			{
				errBasicSalary.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values.</div>";
				var len = (document.formOne.outletBasicSalary.value.length) - 1;
				document.formOne.outletBasicSalary.value = (document.formOne.outletBasicSalary.value).substring(0, len);
				document.formOne.outletBasicSalary.focus();
				event.originalEvent.formOne.submit = false;
			}
			else
			{
				errBasicSalary.innerHTML="";
			}
		}
	}
}











