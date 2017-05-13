package com.bj58.test;

import java.util.List;

public class TestBean {
	
	public Bean bean;
	
	private int id;
	
	private String name;
	
	private List<String> catList;

	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCatList() {
		return catList;
	}

	public void setCatList(List<String> catList) {
		this.catList = catList;
	}

}
