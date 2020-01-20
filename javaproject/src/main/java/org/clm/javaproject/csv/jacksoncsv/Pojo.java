package org.clm.javaproject.csv.jacksoncsv;

public class Pojo {
	private String id;
	private String name;
	private String score;
	private String tmp;
	
	public Pojo() {
	}
	
	public Pojo(String id, String name, String score, String tmp) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.tmp = tmp;
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTmp() {
		return tmp;
	}
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
	
	
	
}
