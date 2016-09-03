package com.Over2Cloud.modal.dao.impl.common;


import com.Over2Cloud.annotation.SqlQueries;
import com.Over2Cloud.annotation.SqlQuery;

@SqlQueries(
		    {
		    	@SqlQuery(name="getAllDataOfTable",query="CALL getAllDataOfTable(:tableaname)"),
		    	@SqlQuery(name="getAllDataWithGridSearch",query="CALL getAllDataWithGridSearch(:tableaname,:dataToBeSearch,:dataSearchColomn,:startIndex,:endIndex)"),
		    	@SqlQuery(name="getAllDataWithGridSearchCount",query="CALL getAllDataWithGridSearchCount(:tableaname)"),
		    }
		  )

public class NativeSqlQuery {

}
