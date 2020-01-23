package br.com.alura.forum.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Category;

public class DesafioOutputDto {
	
	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;
	
	public DesafioOutputDto() {
		
	}

	public DesafioOutputDto(Category category) {
		this.categoryName = category.getName();
	}

	public DesafioOutputDto(String categoryName, List<String> subcategories, int allTopics, int lastWeekTopics,
			int unansweredTopics) {
		super();
		this.categoryName = categoryName;
		this.subcategories = subcategories;
		this.allTopics = allTopics;
		this.lastWeekTopics = lastWeekTopics;
		this.unansweredTopics = unansweredTopics;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<String> subcategories) {
		this.subcategories = subcategories;
	}

	public int getAllTopics() {
		return allTopics;
	}

	public void setAllTopics(int allTopics) {
		this.allTopics = allTopics;
	}

	public int getLastWeekTopics() {
		return lastWeekTopics;
	}

	public void setLastWeekTopics(int lastWeelTopics) {
		this.lastWeekTopics = lastWeelTopics;
	}

	public int getUnansweredTopics() {
		return unansweredTopics;
	}

	public void setUnansweredTopics(int unansweredTopcis) {
		this.unansweredTopics = unansweredTopcis;
	}

	public static List<DesafioOutputDto> listFromCategories(List<Category> categories) {
		return categories.stream()
				.map(DesafioOutputDto::new)
				.collect(Collectors.toList());
	}
}