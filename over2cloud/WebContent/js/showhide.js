function getObj(id)
{
  return document.getElementById(id);
}



function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
	$("#extraDiv").show();

}


function adddeletediv(divID,divID1)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
}


function plusdiv(divID,buttonId)
{
  document.getElementById(divID).style.display="block";
}


function deletediv(divID)
{
  document.getElementById(divID).style.display="none";
}



function showhidedivblock(divID,divID1)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
}


function deletedivboth(divID,divID1)
{
  document.getElementById(divID).style.display="none";
  document.getElementById(divID1).style.display="block";
}



function showhidedivblock2(divID,divID1,divID2)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
  document.getElementById(divID2).style.display="none";
}



function showhidediv(divID,divID1)
{
  var selectval= mStatus.value;
  
  if(selectval=='Married')
	  {
	    document.getElementById(divID).style.display="block";
	    document.getElementById(divID1).style.display="block";
	  }
  else
	  {
	    document.getElementById(divID).style.display="none";
	    document.getElementById(divID1).style.display="none";
	  }
}



