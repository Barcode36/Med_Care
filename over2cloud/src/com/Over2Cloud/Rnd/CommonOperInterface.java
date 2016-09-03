package com.Over2Cloud.Rnd;

import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface CommonOperInterface
{
	public boolean insertIntoTableWithSession(String tableName,
			List<InsertDataTable> Tablecolumesaaa, Session session);

	public boolean createTable22(String tableName,
			List<TableColumes> Tablecolumesaaa, SessionFactory connection);

	public boolean checkTableColmnAndAltertable(List<String> colName,
			String tableName, SessionFactory connection);

	public boolean insertIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory connection);

	public boolean insertIntoTableIfNotExists(String tableName, String colName,
			String colVal, List<InsertDataTable> Tablecolumesaaa,
			SessionFactory connection);

	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, Map<String, Object> wherClause,
			SessionFactory connection);

	public List viewAllDataEitherSelectOrAllByOrder(String tableName,
			List<String> colmName, Map<String, Object> wherClause,
			Map<String, Object> order, SessionFactory connection);

	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, SessionFactory connection);

	public List viewAllDataEitherSelectOrAll(String tableName,
			Map<String, String> wherClause, SessionFactory connection);

	public List viewAllDataEitherSelectOrAllWithOPutUnder(String tableName,
			List<String> colmName, SessionFactory connection);

	public int getMaxId(String tableName, SessionFactory connection);

	public boolean updateTableColomn(String tableName,
			Map<String, Object> wherClause, Map<String, Object> condtnBlock,
			SessionFactory connection);

	public List executeAllSelectQuery(String query, SessionFactory connection);

	public List executeAllSelectQueryLogin(String query,
			SessionFactory connection);

	public int getCountByField(String tableName, List<String> fields,
			Map<String, Object> condtnBlock, Map<String, Object> order,
			SessionFactory connection);

	public List viewData(String tableName, List<String> fields,
			Map<String, Object> condtnBlock, Map<String, Object> order,
			SessionFactory connection);

	public boolean checkExitOfTable(String tableName,
			SessionFactory connection, String paramName, String value);

	public int executeAlter(String query, SessionFactory connection);

	public List viewAllDataEitherSelectOrAllWithLocalConnection(
			String tableName, List<String> colmName,
			Map<String, Object> wherClause, Map<String, Object> order,
			String dbName);

	public List viewAllDataEitherSelectOrAllWithOPutUnderWithLocal(
			String tableName, List<String> colmName, String dbName);

	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, Map<String, Object> wherClause);

	public List viewAllDataEitherSelectOrAllWithLocal(String tableName,
			List<String> colmName, Map<String, Object> wherClause);

	public boolean deleteAllRecordForId(String tableName, String colName,
			String condValue, SessionFactory connection);

	public boolean updateTableColomn(SessionFactory connection,
			StringBuilder selectTableData);

	public boolean addDetails(Object objct, SessionFactory connection);

	public int executeAllUpdateQuery(String query, SessionFactory connection);

	public int insertDataReturnId(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory connection);

	public boolean tableExists(String tableName, SessionFactory connection);

	public int altTabSetUnique(String tablename, String columnName,
			SessionFactory connection);

	public boolean insertUniqueDataIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory session);

	public boolean updateIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, String id,
			SessionFactory connection);

	public boolean createTable23(String tableName,
			List<TableColumes> Tablecolumesaaa, SessionFactory connection,
			String... compositeKey);
}
