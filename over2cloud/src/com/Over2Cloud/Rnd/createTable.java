package com.Over2Cloud.Rnd;

import hibernate.common.HibernateSessionFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class createTable implements CommonOperInterface
{

	static final Log log = LogFactory.getLog(createTable.class);

	public boolean createTable22(String tableName,
			List<TableColumes> Tablecolumesaaa, SessionFactory connection)
	{
		boolean flage = true;
		List<String> colNames = new ArrayList<String>();
		StringBuilder createTableQuery = new StringBuilder(
				"CREATE TABLE IF NOT EXISTS " + tableName + " (");
		createTableQuery.append("`id`");
		createTableQuery.append("int(10) ");
		createTableQuery.append("unsigned NOT NULL auto_increment,");
		if (Tablecolumesaaa != null && Tablecolumesaaa.size() > 0)
		{
			for (TableColumes h : Tablecolumesaaa)
			{
				createTableQuery.append("`" + h.getColumnname() + "` ");
				createTableQuery.append(h.getDatatype() + " ");
				createTableQuery.append(h.getConstraint() + ", ");
				colNames.add(h.getColumnname());
			}
		}
		createTableQuery.append(" PRIMARY KEY  (`id`))");
		createTableQuery
				.append("ENGINE=MyISAM AUTO_INCREMENT=01 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			int count = session.createSQLQuery(createTableQuery.toString())
					.executeUpdate();
			transaction.commit();
			checkTableColmnAndAltertable(colNames, tableName, connection);
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method createTable22 of class "
					+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}

		return flage;
	}

	public boolean checkTableColmnAndAltertable(List<String> colName,
			String tableName, SessionFactory connection)
	{
		boolean status = false;
		StringBuilder checkQuery = new StringBuilder("desc " + tableName);
		List descTable = executeAllSelectQuery(checkQuery.toString(),
				connection);
		List<String> descTableColname = new ArrayList<String>();
		List<String> returnResult = new ArrayList<String>();
		if (descTable != null)
		{
			String names = null;
			for (Iterator it = descTable.iterator(); it.hasNext();)
			{
				Object[] obdata = (Object[]) it.next();
				if (obdata[0] != null)
				{
					descTableColname.add(obdata[0].toString());
				}
			}
			if (colName != null && colName.size() > 0)
			{
				for (int i = 0; i < colName.size(); i++)
				{
					if (descTableColname.contains(colName.get(i)))
					{

					}
					else
						returnResult.add(colName.get(i));
				}
			}
			if (returnResult.size() > 0)
			{
				// alter table work here
				StringBuilder alterTable = new StringBuilder("alter table "
						+ tableName + " add (");
				int k = 1;
				for (int i = 0; i < returnResult.size(); i++)
				{
					if (k < returnResult.size())
						alterTable.append(returnResult.get(i)
								+ " varchar(255) default NULL, ");
					else
						alterTable.append(returnResult.get(i)
								+ " varchar(255) default NULL");
					k++;
				}
				alterTable.append(")");
				executeAlter(alterTable.toString(), connection);
			}
		}
		return status;
	}

	public boolean insertIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "
				+ tableName + " (");
		int i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}
		// System.out.println("Insert Query "+createTableQuery.toString());
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(createTableQuery.toString());
			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
			query.executeUpdate();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method insertIntoTable of class "
					+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}

		return flage;

	}

	public boolean insertIntoTableIfNotExists(String tableName, String colName,
			String colVal, List<InsertDataTable> Tablecolumesaaa,
			SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "
				+ tableName + " (");
		int i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();

			createTableQuery.append(" where Not EXISTS(select " + colName
					+ " from " + tableName + " where " + colName + "='"
					+ colVal + "'");
			Query query = session.createSQLQuery(createTableQuery.toString());

			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
		//	System.out.println(query.toString());
			query.executeUpdate();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method insertIntoTable of class "
					+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}

		return flage;

	}

	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, Map<String, Object> wherClause,
			SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName);
		}
		if (wherClause != null)
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
	//	System.out.println("CreateTa ble ki querry fro Org Level >>>>> "+selectTableData);
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, Object>wherClause,SessionFactory connection) of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public List viewAllDataEitherSelectOrAllByOrder(String tableName,
			List<String> colmName, Map<String, Object> wherClause,
			Map<String, Object> order, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName);
		}
		if (wherClause != null)
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " "
						+ me.getValue() + "");
			}
		}
		selectTableData.append(";");
		// System.out.println("Data in Order  "+selectTableData.toString());
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,Map<String, Object>wherClause,SessionFactory connection) of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	// over ride method
	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName);
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName, List<String> colmName,SessionFactory connection) of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public List viewAllDataEitherSelectOrAll(String tableName,
			Map<String, String> wherClause, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select * from " + tableName);
		if (wherClause != null && wherClause.size() > 0)
		{
			selectTableData.append(" where ");
		}
		int size = 1;
		Set set = wherClause.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			if (size < wherClause.size())
				selectTableData.append((String) me.getKey() + " = "
						+ me.getValue() + " and ");
			else
				selectTableData.append((String) me.getKey() + " = '"
						+ me.getValue() + "'");
			size++;
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAll(String tableName,Map<String, String>wherClause,SessionFactory connection) of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public List viewAllDataEitherSelectOrAllWithOPutUnder(String tableName,
			List<String> colmName, SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName + ";");
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName + ";");
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAllWithOPutUnder of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public int getMaxId(String tableName, SessionFactory connection)
	{
		int id = 0;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("SELECT MAX(id) FROM " + tableName + ";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			List Data = session.createSQLQuery(selectTableData.toString())
					.list();
			transaction.commit();
			for (Object c : Data)
			{
				Object dataC = (Object) c;
				id = (Integer) dataC;
			}
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method getMaxId() of class "
					+ getClass(), ex);
			transaction.rollback();
		}

		return id;
	}

	public boolean updateTableColomn(String tableName,
			Map<String, Object> wherClause, Map<String, Object> condtnBlock,
			SessionFactory connection)
	{
		boolean Data = false;
		StringBuilder selectTableData = new StringBuilder("");

		selectTableData.append("update " + tableName + " set ");
		int size = 1;
		Set set = wherClause.entrySet();
		Iterator i = set.iterator();
		if (wherClause != null && wherClause.size() > 0)
		{
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append(me.getKey() + " = :" + me.getKey()
							+ " , ");
				else
					selectTableData.append(me.getKey() + " = :" + me.getKey()
							+ "");
				size++;
			}
		}
		if (condtnBlock != null && condtnBlock.size() > 0)
		{
			selectTableData.append(" where ");
			size = 1;
			set = condtnBlock.entrySet();
			i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < condtnBlock.size())
					selectTableData.append(me.getKey() + " = " + me.getValue()
							+ " and ");
				else
					selectTableData.append(me.getKey() + " = " + me.getValue()
							+ "");
				size++;
			}
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(selectTableData.toString());
			set = wherClause.entrySet();
			i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				query.setParameter(me.getKey().toString(), me.getValue()
						.toString());
			}
			int count = query.executeUpdate();
			transaction.commit();
			if (count > 0)
				Data = true;

		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method updateTableColomn() of class "
							+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return Data;
	}

	/**
	 * Execute all select customize select query and return a list
	 * 
	 * @param args
	 */

	public List executeAllSelectQuery(String query, SessionFactory connection)
	{

		List Data = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(query).list();
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			ex.printStackTrace();
			transaction.rollback();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method executeAllSelectQuery() of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public List executeAllSelectQueryLogin(String query,
			SessionFactory connection)
	{
		List Data = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(query).list();
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			// ex.printStackTrace();
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method executeAllSelectQuery() of class "
							+ getClass(), ex);
			transaction.rollback();
		}
		return Data;
	}

	public int getCountByField(String tableName, List<String> fields,
			Map<String, Object> condtnBlock, Map<String, Object> order,
			SessionFactory connection)
	{
		int count = 0;
		List Data = new ArrayList();
		StringBuilder selectTableData = new StringBuilder("");
		int k = 1;
		selectTableData.append("select count(*)");
		if (fields != null && fields.size() > 1)
		{

			selectTableData.append(""
					+ fields.toString().replace("[", "").replace("]", "") + "");
		}
		else
		{
			selectTableData.append(" " + fields.toString() + " ,");
		}
		selectTableData.append(" from " + tableName + "");
		if (condtnBlock.size() > 0)
		{
			selectTableData.append(" where ");
		}
		int size = 1;
		Set set = condtnBlock.entrySet();
		Iterator i = set.iterator();
		i = set.iterator();
		while (i.hasNext())
		{
			Map.Entry me = (Map.Entry) i.next();
			if (size < condtnBlock.size())
				selectTableData.append(me.getKey() + " = '" + me.getValue()
						+ "' and ");
			else
				selectTableData.append(me.getKey() + " = '" + me.getValue()
						+ "' ");
			size++;
		}

		Set set1 = order.entrySet();
		Iterator i1 = set.iterator();
		i1 = set1.iterator();
		while (i1.hasNext())
		{
			Map.Entry me = (Map.Entry) i1.next();
			selectTableData.append("ORDER BY " + me.getKey() + " "
					+ me.getValue() + ";");
		}

		Session session = null;
		Transaction transaction = null;
		try
		{

			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(selectTableData.toString());
			Data = query.list();
			transaction.commit();
			if (Data.size() > 0)
			{
				count = Integer.parseInt(Data.get(0).toString());
			}
			return count;

		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method getCountByField() of class "
							+ getClass(), ex);
			transaction.rollback();
		}
		return count;
	}

	public List viewData(String tableName, List<String> fields,
			Map<String, Object> condtnBlock, Map<String, Object> order,
			SessionFactory connection)
	{
		List Data = new ArrayList();
		StringBuilder selectTableData = new StringBuilder("");
		int k = 1;
		selectTableData.append("select ");
		if (fields != null && fields.size() > 1)
		{

			selectTableData.append(""
					+ fields.toString().replace("[", "").replace("]", "") + "");
		}
		else
		{
			selectTableData.append(" " + fields.toString() + " ,");
		}
		selectTableData.append(" from " + tableName + "");
		if (condtnBlock.size() > 0)
		{
			selectTableData.append(" where ");
		}
		int size = 1;
		if (condtnBlock != null)
		{
			Set set = condtnBlock.entrySet();
			Iterator i = set.iterator();
			i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < condtnBlock.size())
					selectTableData.append(me.getKey() + " = '" + me.getValue()
							+ "' and ");
				else
					selectTableData.append(me.getKey() + " = '" + me.getValue()
							+ "' ");
				size++;
			}

		}

		Set set1 = order.entrySet();
		Iterator i1 = set1.iterator();
		i1 = set1.iterator();
		while (i1.hasNext())
		{
			Map.Entry me = (Map.Entry) i1.next();
			selectTableData.append("ORDER BY " + me.getKey() + " "
					+ me.getValue() + ";");
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(selectTableData.toString());
			Data = query.list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method viewData() of class "
					+ getClass(), ex);
			transaction.rollback();
		}
		return Data;
	}

	public boolean checkExitOfTable(String tableName,
			SessionFactory connection, String paramName, String value)
	{
		boolean status = false;
		try
		{
			StringBuilder query = new StringBuilder("");
			query.append("select count(*) from " + tableName);
			if (paramName != null && value != null)
			{
				query.append(" where " + paramName + "='" + value + "'");
			}
			List data = new createTable().executeAllSelectQuery(
					query.toString(), connection);
			BigInteger count = new BigInteger("3");
			for (Iterator it = data.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				count = (BigInteger) obdata;
			}
			if (count.intValue() > 0)
				return true;

		}
		catch (Exception e)
		{

		}
		return status;
	}

	public int executeAlter(String query, SessionFactory connection)
	{
		int size = 0;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			size = session.createSQLQuery(query).executeUpdate();
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			// ex.printStackTrace();
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method executeAlter() of class "
					+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return size;
	}

	public List viewAllDataEitherSelectOrAllWithLocalConnection(
			String tableName, List<String> colmName,
			Map<String, Object> wherClause, Map<String, Object> order,
			String dbName)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + dbName + "."
							+ tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + dbName + "." + tableName);
		}
		if (wherClause != null)
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " "
						+ me.getValue() + "");
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{

			session = HibernateSessionFactory.getSessionFactory()
					.getCurrentSession();
			if (!session.isOpen())
			{
				session = HibernateSessionFactory.getSession();
			}
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAllWithLocalConnection() of class "
							+ getClass(), ex);
			transaction.rollback();
		}

		return Data;
	}

	public List viewAllDataEitherSelectOrAllWithOPutUnderWithLocal(
			String tableName, List<String> colmName, String dbName)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + dbName + "."
							+ tableName + ";");
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + dbName + "." + tableName
					+ ";");
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateSessionFactory.getSessionFactory()
					.getCurrentSession();
			if (!session.isOpen())
			{
				session = HibernateSessionFactory.getSession();
			}
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAllWithOPutUnderWithLocal() of class "
							+ getClass(), ex);
			transaction.rollback();
		}
		return Data;
	}

	public List viewAllDataEitherSelectOrAll(String tableName,
			List<String> colmName, Map<String, Object> wherClause)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName);
		}
		if (wherClause != null)
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateSessionFactory.getSessionFactory()
					.getCurrentSession();
			if (!session.isOpen())
			{
				session = HibernateSessionFactory.getSession();
			}
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAll() of class "
							+ getClass(), ex);
			transaction.rollback();
		}
		return Data;
	}

	public List viewAllDataEitherSelectOrAllWithLocal(String tableName,
			List<String> colmName, Map<String, Object> wherClause)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		if (colmName != null && colmName.size() > 0)
		{
			selectTableData.append("select ");
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size())
					selectTableData.append(H + " ,");
				else
					selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " + tableName);
		}
		if (wherClause != null)
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size())
					selectTableData.append((String) me.getKey() + " = "
							+ me.getValue() + " and ");
				else
					selectTableData.append((String) me.getKey() + " = '"
							+ me.getValue() + "'");
				size++;
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateSessionFactory.getSessionFactory()
					.getCurrentSession();
			if (!session.isOpen())
			{
				session = HibernateSessionFactory.getSession();
			}
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method viewAllDataEitherSelectOrAllWithLocal() of class "
							+ getClass(), ex);
			transaction.rollback();
		}
		return Data;
	}

	public boolean deleteAllRecordForId(String tableName, String colName,
			String condValue, SessionFactory connection)
	{
		boolean status = false;

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			StringBuilder query = new StringBuilder();
			query.append("delete from " + tableName + " where " + colName
					+ " in(" + condValue + ")");
			int size = session.createSQLQuery(query.toString()).executeUpdate();
			if (size != 0)
				status = true;
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			// ex.printStackTrace();
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method deleteAllRecordForId() of class "
							+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return status;
	}

	public boolean updateTableColomn(SessionFactory connection,
			StringBuilder selectTableData)
	{
		boolean Data = false;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(selectTableData.toString());
			int count = query.executeUpdate();
			if (count > 0)
				Data = true;
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method updateTableColomn() of class "
							+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return Data;
	}

	public boolean addDetails(Object objct, SessionFactory connection)
	{
		Session session = null;
		boolean flag = true;
		try
		{
			session = connection.getCurrentSession();
			Transaction tx = session.beginTransaction();
			session.save(objct);
			tx.commit();
		}
		catch (Exception e)
		{
			flag = false;
			log.error(
					"@ERP::>> ActionDAO::AbstractCommonOperation && Method:addDetails()",
					e);
		}
		return flag;
	}

	// Added By Khushal Sir
	public boolean insertIntoTableWithSession(String tableName,
			List<InsertDataTable> Tablecolumesaaa, Session session)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "
				+ tableName + " (");
		int i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}
		Transaction transaction = null;
		try
		{
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(createTableQuery.toString());
			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
			query.executeUpdate();
			transaction.commit();

		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method insertIntoTable of class "
					+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}
		return flage;
	}

	@Override
	public int executeAllUpdateQuery(String query, SessionFactory connection)
	{
		Session session = null;
		Transaction transaction = null;
		int update = 0;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery(query);
			update = sqlQuery.executeUpdate();
			transaction.commit();
		}
		catch (SQLGrammarException ex)
		{
			 ex.printStackTrace();
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method executeAllSelectQuery() of class "
							+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
		}
		return update;
	}

	public int insertDataReturnId(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder("INSERT INTO "
				+ tableName + " (");
		int i = 1;
		int maxId = 0;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}
		Session session = null;
		Transaction transaction = null;
		try
		{
			System.out.println("createTableQuery "+createTableQuery);
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(createTableQuery.toString());
			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
			query.executeUpdate();
			List id = session
					.createSQLQuery("SELECT MAX(id) FROM " + tableName).list();
			if (id != null && id.size() > 0)
			{
				maxId = Integer.valueOf(id.get(0).toString());
			}
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method insertDataReturnId of class "
							+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}
		return maxId;

	}

	public boolean tableExists(String tableName, SessionFactory connection)
	{
		boolean exists = false;
		StringBuffer buffer = new StringBuffer("desc '" + tableName + "'");
		List dataList = executeAllSelectQuery(buffer.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					if (object.toString().equalsIgnoreCase(tableName))
					{
						exists = true;
					}
				}
			}
		}
		return exists;
	}

	public int altTabSetUnique(String tablename, String columnName,
			SessionFactory connection)
	{
		int size = 0;
		Session session = null;
		Transaction transaction = null;
		try
		{
			String query = "ALTER TABLE " + tablename + " ADD UNIQUE ("
					+ columnName + ")";
			session = connection.openSession();
			transaction = session.beginTransaction();
			size = session.createSQLQuery(query).executeUpdate();
			transaction.commit();
			session.close();
		}
		catch (SQLGrammarException ex)
		{
			transaction.rollback();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method altTabSetUnique() of class "
							+ getClass(), ex);
			ex.printStackTrace();
			transaction.rollback();
			session.close();
		}
		return size;
	}

	public boolean insertUniqueDataIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder createTableQuery = new StringBuilder(
				"INSERT IGNORE INTO " + tableName + " (");
		int i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append(h.getColName() + ", ");
			else
				createTableQuery.append(h.getColName() + ")");
			i++;
		}
		createTableQuery.append(" VALUES (");
		i = 1;
		for (InsertDataTable h : Tablecolumesaaa)
		{
			if (i < Tablecolumesaaa.size())
				createTableQuery.append("?, ");
			else
				createTableQuery.append("?)");
			i++;
		}
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(createTableQuery.toString());
			i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}
			query.executeUpdate();
			transaction.commit();
			session.close();
		}
		catch (Exception ex)
		{
			log.error(
					"Date : "
							+ DateUtil.getCurrentDateIndianFormat()
							+ " Time: "
							+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method insertUniqueDataIntoTable of class "
							+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
			session.close();
		}
		return flage;
	}

	public boolean updateIntoTable(String tableName,
			List<InsertDataTable> Tablecolumesaaa, String id,
			SessionFactory connection)
	{

		boolean flage = true;
		StringBuilder queryStart = new StringBuilder("UPDATE " + tableName
				+ " SET ");
		StringBuilder updateTableQuery = new StringBuilder();
		for (InsertDataTable h : Tablecolumesaaa)
		{
			queryStart.append(h.getColName());
			queryStart.append(" = ? ");
			queryStart.append(", ");
		}
		updateTableQuery.append(queryStart.toString().substring(0,
				queryStart.toString().lastIndexOf(",")));
		updateTableQuery.append(" WHERE id = ");
		updateTableQuery.append(id);

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(updateTableQuery.toString());
			int i = 0;
			for (InsertDataTable h : Tablecolumesaaa)
			{
				query.setParameter(i, h.getDataName());
				i++;
			}

			query.executeUpdate();
			transaction.commit();
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method insertIntoTable of class "
					+ getClass(), ex);
			// ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}

		return flage;

	}

	public boolean createTable23(String tableName,
			List<TableColumes> Tablecolumesaaa, SessionFactory connection,
			String... compositeKey)
	{
		boolean flage = true;
		List<String> colNames = new ArrayList<String>();
		StringBuilder createTableQuery = new StringBuilder(
				"CREATE TABLE IF NOT EXISTS " + tableName + " (");
		createTableQuery.append("`id`");
		createTableQuery.append("int(10) ");
		createTableQuery.append("unsigned NOT NULL auto_increment,");
		if (Tablecolumesaaa != null && Tablecolumesaaa.size() > 0)
		{
			for (TableColumes h : Tablecolumesaaa)
			{
				createTableQuery.append("`" + h.getColumnname() + "` ");
				createTableQuery.append(h.getDatatype() + " ");
				createTableQuery.append(h.getConstraint() + ", ");
				colNames.add(h.getColumnname());
			}
		}
		createTableQuery.append(" PRIMARY KEY  (`id`");
		if (compositeKey != null && compositeKey.length > 0)
		{
			for (String key : compositeKey)
			{
				createTableQuery.append(",`");
				createTableQuery.append(key);
				createTableQuery.append("`");
			}
		}
		createTableQuery.append("))");
		createTableQuery
				.append("ENGINE=MyISAM AUTO_INCREMENT=01 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC");

		// //System.out.println("111:" + createTableQuery);
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.getCurrentSession();
			transaction = session.beginTransaction();
			int count = session.createSQLQuery(createTableQuery.toString())
					.executeUpdate();
			transaction.commit();
			checkTableColmnAndAltertable(colNames, tableName, connection);
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method createTable22 of class "
					+ getClass(), ex);
			ex.printStackTrace();
			flage = false;
			transaction.rollback();
		}

		return flage;
	}

	public static void main(String[] args)
	{
		String dbDetals = new IPAddress().getDBDetails();
		String details[] = dbDetals.split(",");
		AllConnection Conn = new ConnectionFactory("1_clouddb", details[0],
				details[1], details[2], details[3]);
		AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
		SessionFactory configurDBSession = Ob1.getSession();
		Map<String, Object> wherClause = new HashMap<String, Object>();
		Map<String, Object> condtnBlock = new HashMap<String, Object>();
		wherClause.put("readFlag", 1);
		new createTable().updateTableColomn("takeaction", wherClause,
				condtnBlock, configurDBSession);
	}
}