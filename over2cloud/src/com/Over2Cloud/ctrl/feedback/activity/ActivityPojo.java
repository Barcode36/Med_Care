package com.Over2Cloud.ctrl.feedback.activity;

import java.util.List;
import java.util.Map;

public class ActivityPojo
{
	private String feedDataId = "NA";
	private String feedStatId = "NA";
	private int id;
	private String clientId = "NA";
	private String patientId = "NA";
	private String clientName = "NA";
	private String centerCode = "NA";
	private String mode = "NA";
	private String dateTime = "NA";
	private String targetOn = "NA";
	private String compType = "NA";
	private String dept = "NA";
	private String deptId = "NA";
	private String cat = "NA";
	private String catId = "NA";
	private String subCat = "NA";
	private String subCatId = "NA";
	private String take_Action = "Take Action";
	private String history = "History";
	private String full_view = "Full View";
	private String catRating = "NA";
	private String brief = "NA";
	private String mobNo = "NA";
	private String emailId = "NA";
	private String addressingTime = "NA";
	private String comments = "NA";
	private String responseToQueries = "NA";
	private String counselling = "NA";
	private String admission = "NA";
	private String courtesybehaviour = "NA", attentivePrCaring = "NA", responseCommDoctor = "NA", diagnosis = "NA", treatment = "NA", attentiveprompt = "NA", responseCommunication = "NA", procedures = "NA", medication, careAtHome, qualityUpkeep, behivourResponseTime = "NA", functioningMainenance = "NA", qualityofFoods = "NA", timelineService = "NA", behivourResponse = "NA", assessmentCounseling = "NA", responsetoQuery = "NA", efficiencyBillingdesk = "NA", dischargetime = "NA", pharmacyservices = "NA", cafeteriaDyning = "NA", overallsecurity = "NA", overallservices = "NA", recommend = "NA",
			choseHospital = "NA", front_ease = "NA";
	private String ease_Contact = "NA", res_queries = "NA", polndCourt = "NA", regndBill = "NA", wait4Consltnt = "NA", sampleColl = "NA", labServices = "NA", radiologyServices = "NA", neurology = "NA", urology = "NA", preHealth = "NA", anyOther = "NA", cleanWashroom = "NA", unkeepFacility = "NA", locndDirSignages = "NA", chemistShop = "NA", waitingAreas = "NA", cafeteria = "NA", parking = "NA", security = "NA", overallService = "NA", cardiology = "NA", endoscopy = "NA";
	List<String> allSubCats;
	private String catRatingVal = "NA";
	private String docName = "NA";
	private String handledBy = "NA";
	private String overAll = "NA";
	private String companytype = "NA";
	private List<Map<String, String>> categoryList;
	private boolean catExists = false;
	private String fstatus = "NA";
	private String ticket_no = "NA";
	private String allot_to = "NA";
	private String feed_by = "NA";
	private String reopenstatus = "NA";
	private String ropen_tat = "NA";
	private String level = "NA";
	private int feedId;
	private String escDateTime = "NA";
	private String tat = "NA";
	private String feedType = "NA";
	private String feedRegBy = "NA";
	private String reopenedon = "NA";
	private String reOpenLevel = "NA";
	private String centreName = "NA";
	private String snoozeAt = "NA";
	private String snoozeUpto = "NA";
	private String duration = "NA";
	private String snoozeBy = "NA";
	private String snoozeReason = "NA";
	private String highPriorityAt = "NA";
	private String highPriorityBy = "NA";
	private String highPriorityReason = "NA";
	private String highPriorityActionMode = "NA";
	private String ignoreAt = "NA";
	private String ignoreBy = "NA";
	private String ignoreReason = "NA";
	private String ignoreActionMode = "NA";
	private String resolveAt = "NA";
	private String resolveBy = "NA";
	private String capa = "NA";
	private String rca = "NA";
	private String reassignAt = "NA";
	private String reassignBy = "NA";
	private String reassignReason = "NA";
	private String addmissionDate = "NA";
	private String stage = "1";
	private String dischargeDateTime = "NA";
	private String ratingFlag = "1";
	private String reason="NA";
	private String feed_by_mob="NA";

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getPatientId()
	{
		return patientId;
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getCenterCode()
	{
		return centerCode;
	}

	public void setCenterCode(String centerCode)
	{
		this.centerCode = centerCode;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
	}

	public String getTargetOn()
	{
		return targetOn;
	}

	public void setTargetOn(String targetOn)
	{
		this.targetOn = targetOn;
	}

	public String getCompType()
	{
		return compType;
	}

	public void setCompType(String compType)
	{
		this.compType = compType;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getCat()
	{
		return cat;
	}

	public void setCat(String cat)
	{
		this.cat = cat;
	}

	public String getSubCat()
	{
		return subCat;
	}

	public void setSubCat(String subCat)
	{
		this.subCat = subCat;
	}

	public String getTake_Action()
	{
		return take_Action;
	}

	public void setTake_Action(String take_Action)
	{
		this.take_Action = take_Action;
	}

	public String getHistory()
	{
		return history;
	}

	public void setHistory(String history)
	{
		this.history = history;
	}

	public String getFull_view()
	{
		return full_view;
	}

	public void setFull_view(String full_view)
	{
		this.full_view = full_view;
	}

	public String getCatRating()
	{
		return catRating;
	}

	public void setCatRating(String catRating)
	{
		this.catRating = catRating;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getSubCatId()
	{
		return subCatId;
	}

	public void setSubCatId(String subCatId)
	{
		this.subCatId = subCatId;
	}

	public String getMobNo()
	{
		return mobNo;
	}

	public void setMobNo(String mobNo)
	{
		this.mobNo = mobNo;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getCatId()
	{
		return catId;
	}

	public void setCatId(String catId)
	{
		this.catId = catId;
	}

	public String getAddressingTime()
	{
		return addressingTime;
	}

	public void setAddressingTime(String addressingTime)
	{
		this.addressingTime = addressingTime;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getResponseToQueries()
	{
		return responseToQueries;
	}

	public String getCounselling()
	{
		return counselling;
	}

	public String getAdmission()
	{
		return admission;
	}

	public String getCourtesybehaviour()
	{
		return courtesybehaviour;
	}

	public String getAttentivePrCaring()
	{
		return attentivePrCaring;
	}

	public String getResponseCommDoctor()
	{
		return responseCommDoctor;
	}

	public String getDiagnosis()
	{
		return diagnosis;
	}

	public String getTreatment()
	{
		return treatment;
	}

	public String getAttentiveprompt()
	{
		return attentiveprompt;
	}

	public String getResponseCommunication()
	{
		return responseCommunication;
	}

	public String getProcedures()
	{
		return procedures;
	}

	public String getMedication()
	{
		return medication;
	}

	public String getCareAtHome()
	{
		return careAtHome;
	}

	public String getQualityUpkeep()
	{
		return qualityUpkeep;
	}

	public String getBehivourResponseTime()
	{
		return behivourResponseTime;
	}

	public String getFunctioningMainenance()
	{
		return functioningMainenance;
	}

	public String getQualityofFoods()
	{
		return qualityofFoods;
	}

	public String getTimelineService()
	{
		return timelineService;
	}

	public String getBehivourResponse()
	{
		return behivourResponse;
	}

	public String getAssessmentCounseling()
	{
		return assessmentCounseling;
	}

	public String getResponsetoQuery()
	{
		return responsetoQuery;
	}

	public String getEfficiencyBillingdesk()
	{
		return efficiencyBillingdesk;
	}

	public String getDischargetime()
	{
		return dischargetime;
	}

	public String getPharmacyservices()
	{
		return pharmacyservices;
	}

	public String getCafeteriaDyning()
	{
		return cafeteriaDyning;
	}

	public String getOverallsecurity()
	{
		return overallsecurity;
	}

	public String getOverallservices()
	{
		return overallservices;
	}

	public String getRecommend()
	{
		return recommend;
	}

	public String getChoseHospital()
	{
		return choseHospital;
	}

	public String getFront_ease()
	{
		return front_ease;
	}

	public String getEase_Contact()
	{
		return ease_Contact;
	}

	public String getRes_queries()
	{
		return res_queries;
	}

	public String getPolndCourt()
	{
		return polndCourt;
	}

	public String getRegndBill()
	{
		return regndBill;
	}

	public String getWait4Consltnt()
	{
		return wait4Consltnt;
	}

	public String getSampleColl()
	{
		return sampleColl;
	}

	public String getLabServices()
	{
		return labServices;
	}

	public String getRadiologyServices()
	{
		return radiologyServices;
	}

	public String getNeurology()
	{
		return neurology;
	}

	public String getUrology()
	{
		return urology;
	}

	public String getPreHealth()
	{
		return preHealth;
	}

	public String getAnyOther()
	{
		return anyOther;
	}

	public String getCleanWashroom()
	{
		return cleanWashroom;
	}

	public String getUnkeepFacility()
	{
		return unkeepFacility;
	}

	public String getLocndDirSignages()
	{
		return locndDirSignages;
	}

	public String getChemistShop()
	{
		return chemistShop;
	}

	public String getWaitingAreas()
	{
		return waitingAreas;
	}

	public String getCafeteria()
	{
		return cafeteria;
	}

	public String getParking()
	{
		return parking;
	}

	public String getSecurity()
	{
		return security;
	}

	public String getOverallService()
	{
		return overallService;
	}

	public String getCardiology()
	{
		return cardiology;
	}

	public String getEndoscopy()
	{
		return endoscopy;
	}

	public void setResponseToQueries(String responseToQueries)
	{
		this.responseToQueries = responseToQueries;
	}

	public void setCounselling(String counselling)
	{
		this.counselling = counselling;
	}

	public void setAdmission(String admission)
	{
		this.admission = admission;
	}

	public void setCourtesybehaviour(String courtesybehaviour)
	{
		this.courtesybehaviour = courtesybehaviour;
	}

	public void setAttentivePrCaring(String attentivePrCaring)
	{
		this.attentivePrCaring = attentivePrCaring;
	}

	public void setResponseCommDoctor(String responseCommDoctor)
	{
		this.responseCommDoctor = responseCommDoctor;
	}

	public void setDiagnosis(String diagnosis)
	{
		this.diagnosis = diagnosis;
	}

	public void setTreatment(String treatment)
	{
		this.treatment = treatment;
	}

	public void setAttentiveprompt(String attentiveprompt)
	{
		this.attentiveprompt = attentiveprompt;
	}

	public void setResponseCommunication(String responseCommunication)
	{
		this.responseCommunication = responseCommunication;
	}

	public void setProcedures(String procedures)
	{
		this.procedures = procedures;
	}

	public void setMedication(String medication)
	{
		this.medication = medication;
	}

	public void setCareAtHome(String careAtHome)
	{
		this.careAtHome = careAtHome;
	}

	public void setQualityUpkeep(String qualityUpkeep)
	{
		this.qualityUpkeep = qualityUpkeep;
	}

	public void setBehivourResponseTime(String behivourResponseTime)
	{
		this.behivourResponseTime = behivourResponseTime;
	}

	public void setFunctioningMainenance(String functioningMainenance)
	{
		this.functioningMainenance = functioningMainenance;
	}

	public void setQualityofFoods(String qualityofFoods)
	{
		this.qualityofFoods = qualityofFoods;
	}

	public void setTimelineService(String timelineService)
	{
		this.timelineService = timelineService;
	}

	public void setBehivourResponse(String behivourResponse)
	{
		this.behivourResponse = behivourResponse;
	}

	public void setAssessmentCounseling(String assessmentCounseling)
	{
		this.assessmentCounseling = assessmentCounseling;
	}

	public void setResponsetoQuery(String responsetoQuery)
	{
		this.responsetoQuery = responsetoQuery;
	}

	public void setEfficiencyBillingdesk(String efficiencyBillingdesk)
	{
		this.efficiencyBillingdesk = efficiencyBillingdesk;
	}

	public void setDischargetime(String dischargetime)
	{
		this.dischargetime = dischargetime;
	}

	public void setPharmacyservices(String pharmacyservices)
	{
		this.pharmacyservices = pharmacyservices;
	}

	public void setCafeteriaDyning(String cafeteriaDyning)
	{
		this.cafeteriaDyning = cafeteriaDyning;
	}

	public void setOverallsecurity(String overallsecurity)
	{
		this.overallsecurity = overallsecurity;
	}

	public void setOverallservices(String overallservices)
	{
		this.overallservices = overallservices;
	}

	public void setRecommend(String recommend)
	{
		this.recommend = recommend;
	}

	public void setChoseHospital(String choseHospital)
	{
		this.choseHospital = choseHospital;
	}

	public void setFront_ease(String front_ease)
	{
		this.front_ease = front_ease;
	}

	public void setEase_Contact(String ease_Contact)
	{
		this.ease_Contact = ease_Contact;
	}

	public void setRes_queries(String res_queries)
	{
		this.res_queries = res_queries;
	}

	public void setPolndCourt(String polndCourt)
	{
		this.polndCourt = polndCourt;
	}

	public void setRegndBill(String regndBill)
	{
		this.regndBill = regndBill;
	}

	public void setWait4Consltnt(String wait4Consltnt)
	{
		this.wait4Consltnt = wait4Consltnt;
	}

	public void setSampleColl(String sampleColl)
	{
		this.sampleColl = sampleColl;
	}

	public void setLabServices(String labServices)
	{
		this.labServices = labServices;
	}

	public void setRadiologyServices(String radiologyServices)
	{
		this.radiologyServices = radiologyServices;
	}

	public void setNeurology(String neurology)
	{
		this.neurology = neurology;
	}

	public void setUrology(String urology)
	{
		this.urology = urology;
	}

	public void setPreHealth(String preHealth)
	{
		this.preHealth = preHealth;
	}

	public void setAnyOther(String anyOther)
	{
		this.anyOther = anyOther;
	}

	public void setCleanWashroom(String cleanWashroom)
	{
		this.cleanWashroom = cleanWashroom;
	}

	public void setUnkeepFacility(String unkeepFacility)
	{
		this.unkeepFacility = unkeepFacility;
	}

	public void setLocndDirSignages(String locndDirSignages)
	{
		this.locndDirSignages = locndDirSignages;
	}

	public void setChemistShop(String chemistShop)
	{
		this.chemistShop = chemistShop;
	}

	public void setWaitingAreas(String waitingAreas)
	{
		this.waitingAreas = waitingAreas;
	}

	public void setCafeteria(String cafeteria)
	{
		this.cafeteria = cafeteria;
	}

	public void setParking(String parking)
	{
		this.parking = parking;
	}

	public void setSecurity(String security)
	{
		this.security = security;
	}

	public void setOverallService(String overallService)
	{
		this.overallService = overallService;
	}

	public void setCardiology(String cardiology)
	{
		this.cardiology = cardiology;
	}

	public void setEndoscopy(String endoscopy)
	{
		this.endoscopy = endoscopy;
	}

	public List<String> getAllSubCats()
	{
		return allSubCats;
	}

	public void setAllSubCats(List<String> allSubCats)
	{
		this.allSubCats = allSubCats;
	}

	public String getCatRatingVal()
	{
		return catRatingVal;
	}

	public void setCatRatingVal(String catRatingVal)
	{
		this.catRatingVal = catRatingVal;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public String getHandledBy()
	{
		return handledBy;
	}

	public void setHandledBy(String handledBy)
	{
		this.handledBy = handledBy;
	}

	public String getOverAll()
	{
		return overAll;
	}

	public void setOverAll(String overAll)
	{
		this.overAll = overAll;
	}

	public String getCompanytype()
	{
		return companytype;
	}

	public void setCompanytype(String companytype)
	{
		this.companytype = companytype;
	}

	public List<Map<String, String>> getCategoryList()
	{
		return categoryList;
	}

	public void setCategoryList(List<Map<String, String>> categoryList)
	{
		this.categoryList = categoryList;
	}

	public boolean isCatExists()
	{
		return catExists;
	}

	public void setCatExists(boolean catExists)
	{
		this.catExists = catExists;
	}

	public String getFstatus()
	{
		return fstatus;
	}

	public void setFstatus(String fstatus)
	{
		this.fstatus = fstatus;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getAllot_to()
	{
		return allot_to;
	}

	public void setAllot_to(String allot_to)
	{
		this.allot_to = allot_to;
	}

	public String getFeed_by()
	{
		return feed_by;
	}

	public void setFeed_by(String feed_by)
	{
		this.feed_by = feed_by;
	}

	public String getReopenstatus()
	{
		return reopenstatus;
	}

	public void setReopenstatus(String reopenstatus)
	{
		this.reopenstatus = reopenstatus;
	}

	public String getRopen_tat()
	{
		return ropen_tat;
	}

	public void setRopen_tat(String ropen_tat)
	{
		this.ropen_tat = ropen_tat;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public int getFeedId()
	{
		return feedId;
	}

	public void setFeedId(int feedId)
	{
		this.feedId = feedId;
	}

	public String getEscDateTime()
	{
		return escDateTime;
	}

	public void setEscDateTime(String escDateTime)
	{
		this.escDateTime = escDateTime;
	}

	public String getTat()
	{
		return tat;
	}

	public void setTat(String tat)
	{
		this.tat = tat;
	}

	public String getFeedType()
	{
		return feedType;
	}

	public void setFeedType(String feedType)
	{
		this.feedType = feedType;
	}

	public String getFeedRegBy()
	{
		return feedRegBy;
	}

	public void setFeedRegBy(String feedRegBy)
	{
		this.feedRegBy = feedRegBy;
	}

	public String getReopenedon()
	{
		return reopenedon;
	}

	public void setReopenedon(String reopenedon)
	{
		this.reopenedon = reopenedon;
	}

	public String getReOpenLevel()
	{
		return reOpenLevel;
	}

	public void setReOpenLevel(String reOpenLevel)
	{
		this.reOpenLevel = reOpenLevel;
	}

	public String getFeedDataId()
	{
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId)
	{
		this.feedDataId = feedDataId;
	}

	public String getFeedStatId()
	{
		return feedStatId;
	}

	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}

	public String getCentreName()
	{
		return centreName;
	}

	public void setCentreName(String centreName)
	{
		this.centreName = centreName;
	}

	public String getSnoozeAt()
	{
		return snoozeAt;
	}

	public void setSnoozeAt(String snoozeAt)
	{
		this.snoozeAt = snoozeAt;
	}

	public String getSnoozeUpto()
	{
		return snoozeUpto;
	}

	public void setSnoozeUpto(String snoozeUpto)
	{
		this.snoozeUpto = snoozeUpto;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getSnoozeBy()
	{
		return snoozeBy;
	}

	public void setSnoozeBy(String snoozeBy)
	{
		this.snoozeBy = snoozeBy;
	}

	public String getIgnoreAt()
	{
		return ignoreAt;
	}

	public void setIgnoreAt(String ignoreAt)
	{
		this.ignoreAt = ignoreAt;
	}

	public String getIgnoreBy()
	{
		return ignoreBy;
	}

	public void setIgnoreBy(String ignoreBy)
	{
		this.ignoreBy = ignoreBy;
	}

	public String getIgnoreReason()
	{
		return ignoreReason;
	}

	public void setIgnoreReason(String ignoreReason)
	{
		this.ignoreReason = ignoreReason;
	}

	public String getIgnoreActionMode()
	{
		return ignoreActionMode;
	}

	public void setIgnoreActionMode(String ignoreActionMode)
	{
		this.ignoreActionMode = ignoreActionMode;
	}

	public String getSnoozeReason()
	{
		return snoozeReason;
	}

	public void setSnoozeReason(String snoozeReason)
	{
		this.snoozeReason = snoozeReason;
	}

	public String getHighPriorityAt()
	{
		return highPriorityAt;
	}

	public void setHighPriorityAt(String highPriorityAt)
	{
		this.highPriorityAt = highPriorityAt;
	}

	public String getHighPriorityBy()
	{
		return highPriorityBy;
	}

	public void setHighPriorityBy(String highPriorityBy)
	{
		this.highPriorityBy = highPriorityBy;
	}

	public String getHighPriorityReason()
	{
		return highPriorityReason;
	}

	public void setHighPriorityReason(String highPriorityReason)
	{
		this.highPriorityReason = highPriorityReason;
	}

	public String getHighPriorityActionMode()
	{
		return highPriorityActionMode;
	}

	public void setHighPriorityActionMode(String highPriorityActionMode)
	{
		this.highPriorityActionMode = highPriorityActionMode;
	}

	public String getResolveAt()
	{
		return resolveAt;
	}

	public void setResolveAt(String resolveAt)
	{
		this.resolveAt = resolveAt;
	}

	public String getResolveBy()
	{
		return resolveBy;
	}

	public void setResolveBy(String resolveBy)
	{
		this.resolveBy = resolveBy;
	}

	public String getCapa()
	{
		return capa;
	}

	public void setCapa(String capa)
	{
		this.capa = capa;
	}

	public String getRca()
	{
		return rca;
	}

	public void setRca(String rca)
	{
		this.rca = rca;
	}

	public String getReassignAt()
	{
		return reassignAt;
	}

	public void setReassignAt(String reassignAt)
	{
		this.reassignAt = reassignAt;
	}

	public String getReassignBy()
	{
		return reassignBy;
	}

	public void setReassignBy(String reassignBy)
	{
		this.reassignBy = reassignBy;
	}

	public String getReassignReason()
	{
		return reassignReason;
	}

	public void setReassignReason(String reassignReason)
	{
		this.reassignReason = reassignReason;
	}

	public String getAddmissionDate()
	{
		return addmissionDate;
	}

	public void setAddmissionDate(String addmissionDate)
	{
		this.addmissionDate = addmissionDate;
	}

	public String getStage()
	{
		return stage;
	}

	public void setStage(String stage)
	{
		this.stage = stage;
	}

	public void setDischargeDateTime(String dischargeDateTime)
	{
		this.dischargeDateTime = dischargeDateTime;
	}

	public String getDischargeDateTime()
	{
		return dischargeDateTime;
	}

	public String getRatingFlag()
	{
		return ratingFlag;
	}

	public void setRatingFlag(String ratingFlag)
	{
		this.ratingFlag = ratingFlag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFeed_by_mob() {
		return feed_by_mob;
	}

	public void setFeed_by_mob(String feed_by_mob) {
		this.feed_by_mob = feed_by_mob;
	}
}