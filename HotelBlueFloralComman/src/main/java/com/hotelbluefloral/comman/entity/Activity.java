package com.hotelbluefloral.comman.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name="activity")
public class Activity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long acid;
	
	@Column(name="eid")
	private long eid;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="assignedTo")
	private String assignedTo;
	
	//constructors
	public Activity() {}
	
	public Activity(long acid) {
		this.acid=acid;
	}
	
	public Activity(String assignedTo) {
		this.assignedTo=assignedTo;
	}
	
	public Activity(long acid,String assignedTo) {
		this.acid=acid;
		this.assignedTo=assignedTo;
	}
	
	//getters,setters
	public long getAcid() {
		return acid;
	}

	public void setAcid(long acid) {
		this.acid = acid;
	}
	
	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

}
