package com.Over2Cloud.modal.db.signup;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name = "job_area")
public class JobFunctionalArea implements Comparator<JobFunctionalArea>
{
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="jobfunction") private String jobfunction;
	@Column(name="inserttime") private String inserttime;
	
	public JobFunctionalArea(){}
	public JobFunctionalArea(String inserttime, String jobfunction) {
		super();
		this.inserttime = inserttime;
		this.jobfunction = jobfunction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobfunction() {
		return jobfunction;
	}
	public void setJobfunction(String jobfunction) {
		this.jobfunction = jobfunction;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	@Override
	public int compare(JobFunctionalArea o1, JobFunctionalArea o2) {
		// TODO Auto-generated method stub
		return o1.getJobfunction().compareTo(o2.getJobfunction());
	}
	

}
