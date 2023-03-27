package com.app.starcom.model;

import jakarta.persistence.*;


@Entity
@Table(name = "student_tb")
public class Student{

	@Id
	@TableGenerator(name = "companyGen", table="ID_GEN", pkColumnName="GEN_NAME", valueColumnName="GEN_VAL", pkColumnValue="CompanyGen", initialValue = 10, allocationSize = 3)
	@GeneratedValue(generator = "companyGen")
	private Integer studentId;
	private String studentname;
	private String pathToImage;
	
	public Student() {
		
	}
	
	public Student(Integer studentId, String studentname, String pathToImage) {
		this.studentId =  studentId;
		this.studentname = studentname;
		this.pathToImage = pathToImage;
	}
		
	public Integer getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	public String getStudentname() {
		return studentname;
	}
	
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	
	public String getPathToImage() {
		return pathToImage;
	}
	
	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}

	@Override
	public String toString() {
		return "Student {studentId:" + studentId + ", studentname:" + studentname + ", pathToImage:"
				+ pathToImage + "}";
	}
}
