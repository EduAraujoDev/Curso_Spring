package br.com.alura.forum.dto.input;

public class AnwserDashboardOutputDto {
	
	private long id;
	private String content;
	private String creationTime;
	private String solution;
	private String ownerName;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getSolution() {
		return solution;
	}
	
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}