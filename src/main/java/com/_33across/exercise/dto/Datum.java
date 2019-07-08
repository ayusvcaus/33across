package com._33across.exercise.dto;

import java.util.Objects;

import java.io.Serializable;

public class Datum implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String country;

	private String ua;
	
	private String f1;
	
	private String f2;
	
	private String f3;
	
	public Datum(String id, String country, String ua, String f1, String f2, String f3) {
		this.id=id;
		this.country=country;
		this.ua=ua;
		this.f1=f1;
		this.f2=f2;
		this.f3=f3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country=country;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua=ua;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1=f1;
	}
	
	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2=f2;
	}
	
	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3=f3;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, country, ua, f1, f2, f3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this==obj) {
			return true;
		}
		if (!(obj instanceof Datum)) {
			return false;
		}
		Datum other = (Datum) obj;
		if (id==null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	} 
}
