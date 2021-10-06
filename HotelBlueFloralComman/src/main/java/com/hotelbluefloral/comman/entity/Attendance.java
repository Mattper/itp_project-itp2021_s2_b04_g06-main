package com.hotelbluefloral.comman.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name="attendance")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aid")
	private long aid;
	
	@Column(name="eid")
	private long eid;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="date")
	private LocalDate date;
	
	@DateTimeFormat(iso = ISO.TIME)
	@Column(name="intime")
	private LocalTime intime;
	
	@DateTimeFormat(iso = ISO.TIME)
	@Column(name="outtime")
	private LocalTime outtime;

	//Constructors
	public Attendance() {
		super();
	}
	
	//getters,setters
	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
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

	public LocalTime getIntime() {
		return intime;
	}

	public void setIntime(LocalTime intime) {
		this.intime = intime;
	}

	public LocalTime getOuttime() {
		return outtime;
	}

	public void setOuttime(LocalTime outtime) {
		this.outtime = outtime;
	}

}
