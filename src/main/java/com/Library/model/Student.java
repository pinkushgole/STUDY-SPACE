package com.Library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String sname;
	String contact;
	String sdate;
	String edate;
	double payment;
	double remain_payment;

	@ManyToOne
	Library library;

	public Student(int id, String sname, String contact, String sdate, String edate, double payment,
			double remain_payment, Library library) {
		super();
		this.id = id;
		this.sname = sname;
		this.contact = contact;
		this.sdate = sdate;
		this.edate = edate;
		this.payment = payment;
		this.remain_payment = remain_payment;
		this.library = library;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getRemain_payment() {
		return remain_payment;
	}

	public void setRemain_payment(double remain_payment) {
		this.remain_payment = remain_payment;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", sname=" + sname + ", contact=" + contact + ", sdate=" + sdate + ", edate="
				+ edate + ", payment=" + payment + ", remain_payment=" + remain_payment + ", library=" + library + "]";
	}

}
