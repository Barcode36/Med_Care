package com.Over2Cloud.ctrl.helpdesk.feeddraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;

@SuppressWarnings("serial")
public class FeedbackExcelUpload extends GridPropertyBean
{
	private String moduleName;
	private String dataFor;
	private String viewType;

	private File feedbackExcel;
	private String feedbackExcelContentType;
	private String feedbackExcelFileName;

	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;

	// private String selectedId;
	private String fbType;
	private String categoryName;
	private String subdeptname;
	private String subdept;
	private String deptname;
	private String deptId;

	List<FeedbackDraftPojo> excelData = null;
	private List<FeedbackDraftPojo> gridFbDraftExcelModel;

	@SuppressWarnings("unchecked")
	public String uploadExcel() throws Exception
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					String categoryId = "", feed_Id = "";
					HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
					if (feedbackExcel != null)
					{
						InputStream inputStream = new FileInputStream(feedbackExcel);
						if (feedbackExcelFileName.contains(".xlsx"))
						{
							GenericReadExcel7 GRE7 = new GenericReadExcel7();
							XSSFSheet excelSheet = GRE7.readExcel(inputStream);
							excelData = new ArrayList<FeedbackDraftPojo>();
							for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
								boolean feed_type = HUH.check_createTable("feedback_type", connectionSpace);
								if (feed_type)
								{
									if (viewType != null && !viewType.equals("") && viewType.equals("SD"))
									{
										feed_Id = HUH.getValueId("feedback_type", "dept_subdept", subdept, "fbType", GRE7.readExcelSheet(row, 0), userName, moduleName, connectionSpace);
									}
									else if (viewType != null && !viewType.equals("") && viewType.equals("D"))
									{
										/*if (moduleName != null && !moduleName.equals("") && !moduleName.equalsIgnoreCase("ASTM"))
										{
											feed_Id = HUH.getValueId("feedback_type", "dept_subdept", deptname, "fbType", GRE7.readExcelSheet(row, 0), userName, moduleName, connectionSpace);
										}
										else
										{*/
											feed_Id = HUH.getValueId("feedback_type", "dept_subdept", deptname, "fbType", GRE7.readExcelSheet(row, 0), userName, moduleName, connectionSpace);
										//}
									}
									boolean catg_flag = HUH.check_createTable("feedback_category", connectionSpace);
									if (catg_flag)
									{
										categoryId = HUH.getValueId("feedback_category", "fbType", feed_Id, "categoryName", GRE7.readExcelSheet(row, 1), userName, "", connectionSpace);
										if (categoryId != null && !categoryId.equals(""))
										{
											boolean subcatg_flag = HUH.check_createTable("feedback_subcategory", connectionSpace);
											if (subcatg_flag)
											{
												int cellNo = row.getLastCellNum();
												if (cellNo > 0)
												{
													FeedbackDraftPojo FDP = new FeedbackDraftPojo();
													FDP.setFeedtype_id(categoryId);
													FDP.setId(rowIndex);
													for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
													{
														switch (cellIndex)
														{
														case 0:
															FDP.setFeedtype(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 1:
															FDP.setCategory(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 2:
															FDP.setSub_category(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 3:
															FDP.setFeed_msg(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 4:
															FDP.setAddressing_time(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 5:
															FDP.setResolution_time(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 6:
															FDP.setEscDuration(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 7:
															FDP.setEsclevel(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 8:
															FDP.setNeedesc(GRE7.readExcelSheet(row, cellIndex));
															break;

														case 9:
															if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equals(""))
															{
																FDP.setSeverity(GRE7.readExcelSheet(row, cellIndex));
															}
															else
															{
																FDP.setSeverity("Severity 1");
															}
															break;
														}
													}
													String esctime = "24:00";
													if (FDP.getAddressing_time() != null && !FDP.getAddressing_time().equals("") && FDP.getResolution_time() != null
															&& !FDP.getResolution_time().equals("") && FDP.getEscDuration() != null && !FDP.getEscDuration().equals(""))
													{
														esctime = DateUtil.getResolutionTime(FDP.getAddressing_time(), FDP.getResolution_time(), FDP.getEscDuration());
													}
													FDP.setEscalation_time(esctime);
													excelData.add(FDP);
												}
											}
										}
									}
								}
							}
						}
						else if (feedbackExcelFileName.contains(".xls"))
						{
							GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
							HSSFSheet excelSheet = GRBE.readExcel(inputStream);
							excelData = new ArrayList<FeedbackDraftPojo>();
							for (int rowIndex = 1; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								HSSFRow row = excelSheet.getRow(rowIndex);
								boolean feed_type = HUH.check_createTable("feedback_type", connectionSpace);
								if (feed_type)
								{
									if (viewType != null && !viewType.equals("") && viewType.equals("SD"))
									{
										feed_Id = HUH.getValueId("feedback_type", "dept_subdept", subdept, "fbType", GRBE.readExcelSheet(row, 0), userName, moduleName, connectionSpace);
									}
									else if (viewType != null && !viewType.equals("") && viewType.equals("D"))
									{
										feed_Id = HUH.getValueId("feedback_type", "dept_subdept", deptname, "fbType", GRBE.readExcelSheet(row, 0), userName, moduleName, connectionSpace);
									}
									boolean catg_flag = HUH.check_createTable("feedback_category", connectionSpace);
									if (catg_flag)
									{
										categoryId = HUH.getValueId("feedback_category", "fbType", feed_Id, "categoryName", GRBE.readExcelSheet(row, 1), userName, "", connectionSpace);
										if (categoryId != null && !categoryId.equals(""))
										{
											boolean subcatg_flag = HUH.check_createTable("feedback_subcategory", connectionSpace);
											if (subcatg_flag)
											{
												int cellNo = row.getLastCellNum();
												if (cellNo > 0)
												{
													FeedbackDraftPojo FDP = new FeedbackDraftPojo();
													FDP.setFeedtype_id(categoryId);
													FDP.setId(rowIndex);
													for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
													{
														switch (cellIndex)
														{
														case 0:
															FDP.setFeedtype(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 1:
															FDP.setCategory(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 2:
															FDP.setSub_category(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 3:
															FDP.setFeed_msg(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 4:
															FDP.setAddressing_time(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 5:
															FDP.setResolution_time(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 6:
															FDP.setEscDuration(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 7:
															FDP.setEsclevel(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 8:
															FDP.setNeedesc(GRBE.readExcelSheet(row, cellIndex));
															break;

														case 9:
															if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equals(""))
															{
																FDP.setSeverity(GRBE.readExcelSheet(row, cellIndex));
															}
															else
															{
																FDP.setSeverity("Severity 1");
															}
														}
													}
													excelData.add(FDP);
												}
											}
										}
									}
								}
							}
						}
						else
						{
						}
						if (excelData.size() > 0)
						{
							session.put("fbDraftList", excelData);
						}
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String showUploadFbDraft()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FeedbackDraftPojo>) session.get("fbDraftList");
					if (excelData != null && excelData.size() > 0)
					{
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();

						setGridFbDraftExcelModel(excelData.subList(from, to));

						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String saveExcelData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FeedbackDraftPojo>) session.get("fbDraftList");
					session.remove("fbDraftList");
					boolean flag = false;
					InsertDataTable ob = null;
					for (Iterator<FeedbackDraftPojo> empItr = excelData.iterator(); empItr.hasNext();)
					{
						FeedbackDraftPojo FDP = empItr.next();
						flag = new HelpdeskUniversalHelper().isExist("feedback_subcategory", "categoryName", FDP.getFeedtype_id(), "subCategoryName", FDP.getSub_category(), "feedBreif",
								FDP.getFeed_msg(), connectionSpace);
						// System.out.println("Flag Value  "+flag);
						if (!flag)
						{
							String escalationTime = "";
							if (FDP.getAddressing_time() != null && !FDP.getAddressing_time().equals("") && FDP.getResolution_time() != null && !FDP.getResolution_time().equals(""))
							{
								escalationTime = new HelpdeskUniversalAction().addTwoTimes(FDP.getAddressing_time().substring(0, 5), FDP.getResolution_time().substring(0, 5));
							}
							List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

							ob = new InsertDataTable();
							ob.setColName("categoryName");
							ob.setDataName(FDP.getFeedtype_id());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("subCategoryName");
							ob.setDataName(FDP.getSub_category());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feedBreif");
							ob.setDataName(FDP.getFeed_msg());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("addressingTime");
							ob.setDataName(FDP.getAddressing_time().substring(0, 5));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("resolutionTime");
							ob.setDataName(FDP.getResolution_time().substring(0, 5));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("escalateTime");
							ob.setDataName(escalationTime);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("escalationDuration");
							ob.setDataName(FDP.getEscDuration());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("escalationLevel");
							ob.setDataName(FDP.getEsclevel());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("needEsc");
							ob.setDataName(FDP.getNeedesc());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("severity");
							ob.setDataName(FDP.getSeverity());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("hide_show");
							ob.setDataName("Active");
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);
							boolean status = cot.insertIntoTable("feedback_subcategory", insertData, connectionSpace);
							if (status)
							{
								flag = false;
							}
						}
					}
				}
				if (true)
					addActionMessage("Excel Uploaded Successfully.");
				else
					addActionMessage("!!!May be Some or Full Data belongss to Other Sub Department. Otherwise Data is Saved.");
				returnResult = SUCCESS;
			}

			catch (Exception e)
			{
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}

	public String downloadFbDraftPage()
	{
		// System.out.println("Sub Dept Id  "+subdeptname
		// +" Feed Type Id  ::  "+feedTypeId+"    Category Id   :::  "+categoryId);
		// System.out.println("Dept Id  "+deptname +" Data For ::  "+dataFor);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getData4Download()
	{
		// System.out.println("Inside Method    ");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					List list = new HelpdeskUniversalHelper().getFeedbackList(deptId, subdeptname, fbType, categoryName, viewType, connectionSpace);
					if (list != null && list.size() > 0)
					{
						excelData = setFedDraftData(list);
					}
					if (excelData != null && excelData.size() > 0)
					{
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();

						setGridFbDraftExcelModel(excelData.subList(from, to));
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
					if (excelData.size() > 0)
					{
						session.put("feedDraftDownloadList", excelData);
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
			}
			return returnResult;
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String downloadFeedbackDraft()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					excelData = (List<FeedbackDraftPojo>) session.get("feedDraftDownloadList");
					session.remove("feedDraftDownloadList");

					List<FeedbackDraftPojo> feedDraftData = new ArrayList<FeedbackDraftPojo>();
					FeedbackDraftPojo FP = null;
					for (Iterator<FeedbackDraftPojo> empItr = excelData.iterator(); empItr.hasNext();)
					{
						FeedbackDraftPojo PDF = empItr.next();
						// if (selectedId.contains(String.valueOf(PDF.getId())))
						// {
						FP = new FeedbackDraftPojo();
						FP.setFeedtype(PDF.getFeedtype());
						FP.setCategory(PDF.getCategory());
						FP.setSub_category(PDF.getSub_category());
						FP.setFeed_msg(PDF.getFeed_msg());
						FP.setAddressing_time(PDF.getAddressing_time());
						FP.setResolution_time(PDF.getResolution_time());
						FP.setEscDuration(PDF.getEscDuration());
						FP.setEsclevel(PDF.getEsclevel());
						FP.setNeedesc(PDF.getNeedesc());
						FP.setSeverity(PDF.getSeverity());
						// }
						feedDraftData.add(FP);
					}
					String filePath = new FeedbackExcelDownload().writeDataInExcel(feedDraftData);
					if (filePath != null)
					{
						excelStream = new FileInputStream(filePath);
					}
					excelFileName = filePath.substring(4, filePath.length());
				}
				addActionMessage("Feedback Draft Download Successfully !!!!");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public List<FeedbackDraftPojo> setFedDraftData(List list)
	{
		List<FeedbackDraftPojo> feedDraftList = new ArrayList<FeedbackDraftPojo>();
		int i = 1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			Object[] object = (Object[]) iterator.next();
			FeedbackDraftPojo FDP = new FeedbackDraftPojo();
			FDP.setId(i);
			if (object[0] != null)
			{
				FDP.setFeedtype(object[0].toString());
			}

			if (object[1] != null)
			{
				FDP.setCategory(object[1].toString());
			}

			if (object[2] != null)
			{
				FDP.setSub_category(object[2].toString());
			}

			if (object[3] != null)
			{
				FDP.setFeed_msg(object[3].toString());
			}

			if (object[4] != null)
			{
				FDP.setAddressing_time(object[4].toString());
			}

			if (object[5] != null)
			{
				FDP.setResolution_time(object[5].toString());
			}

			if (object[6] != null)
			{
				FDP.setEscDuration(object[6].toString());
			}

			if (object[7] != null)
			{
				FDP.setEsclevel(object[7].toString());
			}

			if (object[8] != null)
			{
				FDP.setNeedesc(object[8].toString());
			}

			if (object[9] != null && !object[9].toString().equals(""))
			{
				FDP.setSeverity(object[9].toString());
			}
			else
			{
				FDP.setSeverity("NA");
			}

			feedDraftList.add(FDP);
			i++;
		}
		return feedDraftList;
	}

	public File getFeedbackExcel()
	{
		return feedbackExcel;
	}

	public void setFeedbackExcel(File feedbackExcel)
	{
		this.feedbackExcel = feedbackExcel;
	}

	public String getFeedbackExcelContentType()
	{
		return feedbackExcelContentType;
	}

	public void setFeedbackExcelContentType(String feedbackExcelContentType)
	{
		this.feedbackExcelContentType = feedbackExcelContentType;
	}

	public String getFeedbackExcelFileName()
	{
		return feedbackExcelFileName;
	}

	public void setFeedbackExcelFileName(String feedbackExcelFileName)
	{
		this.feedbackExcelFileName = feedbackExcelFileName;
	}

	public List<FeedbackDraftPojo> getExcelData()
	{
		return excelData;
	}

	public void setExcelData(List<FeedbackDraftPojo> excelData)
	{
		this.excelData = excelData;
	}

	public List<FeedbackDraftPojo> getGridFbDraftExcelModel()
	{
		return gridFbDraftExcelModel;
	}

	public void setGridFbDraftExcelModel(List<FeedbackDraftPojo> gridFbDraftExcelModel)
	{
		this.gridFbDraftExcelModel = gridFbDraftExcelModel;
	}

	public String getSubdeptname()
	{
		return subdeptname;
	}

	public void setSubdeptname(String subdeptname)
	{
		this.subdeptname = subdeptname;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getSubdept()
	{
		return subdept;
	}

	public void setSubdept(String subdept)
	{
		this.subdept = subdept;
	}

	public String getViewType()
	{
		return viewType;
	}

	public void setViewType(String viewType)
	{
		this.viewType = viewType;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getFbType()
	{
		return fbType;
	}

	public void setFbType(String fbType)
	{
		this.fbType = fbType;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
}
