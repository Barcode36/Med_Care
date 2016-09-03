package com.Over2Cloud.ctrl.homepage;

import java.util.List;

public class PojoBeanForHomePosts {

	private int id;
	private String empID;
	private String sharedempName;
	private String textData;
	private String shareWithEmpid;
	private String attachedDocPath=null;
	private String attachDocName=null;
	private String date;
	private String time;
	private String userName;
	private int totalLikes=0;
	private List<CommentPojo>commentData=null;
	private String liked="true";
	private String userMatched="true";
	private String linkPost=null;
	private String pollPost=null;
	private String linkName=null;
	private String linkURL=null;
	private String c1=null;
	private String c2=null;
	private String pollId=null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getSharedempName() {
		return sharedempName;
	}
	public void setSharedempName(String sharedempName) {
		this.sharedempName = sharedempName;
	}
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	public String getShareWithEmpid() {
		return shareWithEmpid;
	}
	public void setShareWithEmpid(String shareWithEmpid) {
		this.shareWithEmpid = shareWithEmpid;
	}
	public String getAttachedDocPath() {
		return attachedDocPath;
	}
	public void setAttachedDocPath(String attachedDocPath) {
		this.attachedDocPath = attachedDocPath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public List<CommentPojo> getCommentData() {
		return commentData;
	}
	public void setCommentData(List<CommentPojo> commentData) {
		this.commentData = commentData;
	}
	public String getLiked() {
		return liked;
	}
	public void setLiked(String liked) {
		this.liked = liked;
	}
	public String getAttachDocName() {
		return attachDocName;
	}
	public void setAttachDocName(String attachDocName) {
		this.attachDocName = attachDocName;
	}
	public String getUserMatched() {
		return userMatched;
	}
	public void setUserMatched(String userMatched) {
		this.userMatched = userMatched;
	}
	public String getLinkPost() {
		return linkPost;
	}
	public void setLinkPost(String linkPost) {
		this.linkPost = linkPost;
	}
	public String getPollPost() {
		return pollPost;
	}
	public void setPollPost(String pollPost) {
		this.pollPost = pollPost;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkURL() {
		return linkURL;
	}
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	public String getC1() {
		return c1;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public String getC2() {
		return c2;
	}
	public void setC2(String c2) {
		this.c2 = c2;
	}
	public String getPollId() {
		return pollId;
	}
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
	
}
