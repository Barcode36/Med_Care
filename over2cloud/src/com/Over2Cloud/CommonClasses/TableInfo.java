package com.Over2Cloud.CommonClasses;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class TableInfo 
{
	public boolean createTable(String tableName, List<TableColumes> Tablecolumesaaa,int autoicrement,SessionFactory Connection) {
		boolean flage=true;
		 StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + tableName + "` (");  
		 				createTableQuery.append("`id`");  
		 				createTableQuery.append("int(10) "); 
		 				createTableQuery.append("unsigned NOT NULL auto_increment,");
		 				for(TableColumes h:Tablecolumesaaa)
		 					{
		 						createTableQuery.append("`"+h.getColumnname()+"` ");  
		 						createTableQuery.append(h.getDatatype()+" ");  
		 						createTableQuery.append(h.getConstraint()+", ");  
		 					}
		 				createTableQuery.append(" PRIMARY KEY  (`id`))");
		 				createTableQuery.append("ENGINE=MyISAM AUTO_INCREMENT="+autoicrement+" DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC");
		 				Session session = null;  
		 				Transaction transaction = null;  
		 				try {  
		 						session=Connection.openSession();
		 						transaction = session.beginTransaction();  
		 						//System.out.println("query"+createTableQuery.toString());
								int count = session.createSQLQuery(createTableQuery.toString()).executeUpdate();     
		 						transaction.commit(); 
		 					}catch (Exception ex) {flage=false;transaction.rollback();}  
                   return flage;
    }  
	
	
	public static void main(String[] args) {
		
	}
	public boolean createTable(String Db,String tableName, List<TableColumes> Tablecolumesaaa,int autoicrement,SessionFactory Connection) {
		boolean flage=true;
		 StringBuilder createTableQuery = new StringBuilder("USE "+Db+"; CREATE TABLE IF NOT EXISTS `" + tableName + "` (");  
		 				createTableQuery.append("`id`");  
		 				createTableQuery.append("int(10) "); 
		 				createTableQuery.append("unsigned NOT NULL auto_increment,");
		 				for(TableColumes h:Tablecolumesaaa)
		 					{
		 						createTableQuery.append("`"+h.getColumnname()+"` ");  
		 						createTableQuery.append(h.getDatatype()+" ");  
		 						createTableQuery.append(h.getConstraint()+", ");  
		 					}
		 				createTableQuery.append(" PRIMARY KEY  (`id`))");
		 				createTableQuery.append("ENGINE=MyISAM AUTO_INCREMENT="+autoicrement+" DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC");
		 				Session session = null;  
		 				Transaction transaction = null;  
		 				try {  
		 						session=Connection.openSession();
		 						transaction = session.beginTransaction();  
		 						@SuppressWarnings("unused")
								int count = session.createSQLQuery(createTableQuery.toString()).executeUpdate();     
		 						transaction.commit(); 
		 					}catch (Exception ex) {flage=false;transaction.rollback();}  
                   return flage;
    }
	
	
	
	
	
	
	
	public boolean insertIntoTable(String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory Connection )
	{
		boolean flage=true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO `"+tableName+"` (");  
		int i=1;
		for(InsertDataTable h:Tablecolumesaaa)
			{
				if(i<Tablecolumesaaa.size())
					createTableQuery.append(h.getColName()+", ");
				else
					createTableQuery.append(h.getColName()+")");
				i++;
			}
		createTableQuery.append(" VALUES (");
		i=1;
		for(InsertDataTable h:Tablecolumesaaa)
		{
			if(i<Tablecolumesaaa.size())
				createTableQuery.append("'"+h.getDataName()+"', ");
			else
				createTableQuery.append("'"+h.getDataName()+"')");
			i++;
		}
			createTableQuery.append(" ;");
			Session session = null;  
			Transaction transaction = null;  
			try {  
					session=Connection.openSession(); 
					transaction = session.beginTransaction();  
					session.createSQLQuery(createTableQuery.toString()).executeUpdate();  
					transaction.commit(); 
				}catch (Exception ex) {flage=false;transaction.rollback();}  
		return flage;
	}
	
	@SuppressWarnings("unchecked")
	public List viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, String>wherClause,SessionFactory Connection)
	{
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from  "+tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from "+tableName);
		}
		if(wherClause.size()>0)
		{
			selectTableData.append(" where ");
		}
		int size=1;
		Set set =wherClause.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<wherClause.size())
				selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"' and ");
			else
				selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
				session=Connection.getCurrentSession();
				transaction = session.beginTransaction(); 
				System.out.println(":::::::helloDude:::::"+selectTableData);
				Data=session.createSQLQuery(selectTableData.toString()).list();  
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		System.out.println(">>>hello dude "+selectTableData.toString()+"Data"+Data.size());
		return Data;
	}
	@SuppressWarnings("unchecked")
	public List viewAllDataEitherSelectOrAllWithOPutUnder(String tableName, List<String> colmName,SessionFactory Connection)
	{
		List Data=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from "+tableName+";");
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from `"+tableName+";");
		}
		
		Session session = null;  
		Transaction transaction = null;  
		try {  
				session=Connection.openSession();
				transaction = session.beginTransaction();  
				Data=session.createSQLQuery(selectTableData.toString()).list();  
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		//System.out.println(">>>"+selectTableData.toString()+"Data"+Data.size());
		return Data;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColumn(String tableName,Map<String, Object>updateSetParameter,Map<String, Object>condtnBlockToUpdateparameter,SessionFactory Connection)
	{
		boolean data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		
		selectTableData.append("update "+tableName+" set ");
		int size=1;
		Set set =updateSetParameter.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<updateSetParameter.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		
		if(condtnBlockToUpdateparameter.size()>0)
		{
			selectTableData.append(" where ");
		}
		size=1;
		set =condtnBlockToUpdateparameter.entrySet(); 
		i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<condtnBlockToUpdateparameter.size())
				selectTableData.append(me.getKey()+" ='"+me.getValue()+"' and ");
			else
				selectTableData.append(me.getKey()+" ='"+me.getValue()+"'");
			size++;
		} 
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
				session=Connection.openSession(); 
				transaction = session.beginTransaction();  
				int value=session.createSQLQuery(selectTableData.toString()).executeUpdate(); 
				if(value>0)
				{
					data=true;
				}
				else
				{
					data=false;
				}
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		return data;
	}
	public boolean deleteRecord(String tableName,String conditionname,int conditionvalue,SessionFactory Connection)
	{

		boolean data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		//delete from industry where id=1
		selectTableData.append("delete From "+tableName+" where "+conditionname+"="+conditionvalue);
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
				session=Connection.openSession(); 
				transaction = session.beginTransaction();  
				int value=session.createSQLQuery(selectTableData.toString()).executeUpdate(); 
				if(value>0)
				{
					data=true;
				}
				else
				{
					data=false;
				}
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
		return data;
	
	}
	
}
