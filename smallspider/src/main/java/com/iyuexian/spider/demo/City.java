package com.iyuexian.spider.demo;

public class City {

	private String name;
	private String code;

	public City() {
	}

	public City(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "{name:" + name + ", code:" + code + "}";
	}
	
	

}
