package com.Over2Cloud.CommonInterface;

import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;



public interface CommonforClassdata {
    public boolean Prosessrequest(String userName, String password,Class cCls);
	public boolean addDetails(Object objct);
	public boolean UpdateDetails(Object UpdateObject);
	public boolean DeleteRecord(Object ObjectOfRecord,Class cCls );
	public List getNonUniqueDataFromSqlProcedure(String query, Map paramMap);
	public List getDataFromProcedure(String query, Map paramMap);
	public Object getDataFromSqlProcedure(String query, Map paramMap);
    public boolean Createtabledata(String tableName, List<TableColumes> Tablecolumesaaa,SessionFactory connection);
    public boolean batchinsertIntoTable(String tableName, String createTableQuery,Session connection);
    public boolean batchinsertIntoTable(String tableName, String createTableQuery,SessionFactory connectSession);
    public boolean insertIntoTable(String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory connection);
    public boolean insertIntoTableifNotExit(String tableName, List<InsertDataTable> Tablecolumesaaa, SessionFactory connection);
    public List insertIntoTableReturnId(String tableName, List<InsertDataTable> Tablecolumesaaa,SessionFactory connectionSpace);
    public boolean altertabledata(String tablename,List<TableColumes> Tablecolumesaaa);
    public List getSelectTabledata(String tablename, Map whereClose,SessionFactory connection);
    public List getSelectdataFromSelectedFields(String tablename,List<String> selectList, Map whereClose,SessionFactory connection);
    public boolean updateIntoTable(String tableName,Map SetMap,Map WhereClouse,SessionFactory connectionSpace);
    public boolean updateIntomultipleTable(List multipletable,Map SetMap,Map WhereClouse,Map subWhereClouse,SessionFactory connectionSpace);
	public  Map sortByComparator(Map unsortMap) ;
	public List viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,SessionFactory connection);
	public List viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, Object>wherClause,SessionFactory connection);
	public boolean selectAllRecordForId(String tableName,String collmnId,String colmidValue,SessionFactory connection);
	public boolean selectRecordForId(String tableName,List<String> ColumnName, String collmnId,String colmidValue,SessionFactory connection);
	public List executeAllSelectQuery(String query,SessionFactory connection);
	public List executeAllupdateQuery(String query,SessionFactory connection);
	public void updateMsgStatus(String slno, String type,String tablename, SessionFactory connection);
	public void updateMail(String sms_id, String type,String tablename ,SessionFactory connection) ;
	public boolean deleteAllRecordForId(String tableName,String colName,String condValue,SessionFactory connection);
	public boolean deleteAllRecordForId(String tableName,Map<String, Object>wherClause,SessionFactory connection);
	public boolean selectIfdataExist(String tableName,Map WhereClouse,SessionFactory connectionSpace);
}
