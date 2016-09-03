package com.Over2Cloud.procegure.ProcegureCall;
import com.Over2Cloud.procegure.annotation.SqlQueries;
import com.Over2Cloud.procegure.annotation.SqlQuery1;

@SqlQueries(
	    {
	    	@SqlQuery1(name="UpdateRecord",query="CALL flageUpdate(:BAid,:outletId,:superUser,:User_name)"),
	    	@SqlQuery1(name="EmployeeUserAccountUpdate",query="CALL empAccountUpdate(:useraccountId)"),
	    	@SqlQuery1(name="GetTargetOutletValue",query="CALL targetDetails(:Outletid,:Time_period,:super_user)"),
	    	@SqlQuery1(name="MapoutLetId",query="CALL MapoutLetId(:super_user,:ListMonth1,:ListMonth2,:ListMonth3,:user)"),
	    	@SqlQuery1(name="CategoryOutletId",query="CALL categoryTrgetId(:ListMonth1,:ListMonth2,:ListMonth3)"),
	    	@SqlQuery1(name="ProductOutletId",query="CALL productTargetId(:ListMonth1,:ListMonth2,:ListMonth3,:userlist)"),
	    	@SqlQuery1(name="GetCategoryTargetvalue",query="CALL GetCategoryTargetvalue(:Outletid,:categoryid,:Time_period)"),
	    	@SqlQuery1(name="GetProductTargetvalue",query="CALL GetProductTargetvalue(:outletid,:productid,:Time_period)"),
	    	@SqlQuery1(name="TargetOutletValue",query="CALL targetHorizantal(:Time_period,:super_user)"),
	    	@SqlQuery1(name="UserLevelInfo",query="CALL TotalSalesId(:leveluser,:designation)"),
	    	@SqlQuery1(name="history_ivrs",query="CALL History_ivrs(:startIndex,:endIndex,:SearchField,:SearchString)"),
	    	@SqlQuery1(name="Nested_Insert_Msg_state",query="CALL Insert_msg_stats(:Mobileno,:Msg,:date1,:time1,:status1,:User1,:senderid,:empid)"),
	    	@SqlQuery1(name="productnamesize",query="CALL productsizename(:User,:super_user)"),
	    	@SqlQuery1(name="MapUserOnelevelharari",query="CALL XXX(:Userid)"),
	    	@SqlQuery1(name="MapOutletList",query="CALL targetOutletList(:super_user)")
	    }
	  )
public class NativeSqlQueryCallPro{

}
