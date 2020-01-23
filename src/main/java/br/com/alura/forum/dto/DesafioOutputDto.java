package br.com.alura.forum.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Category;

public class DesafioOutputDto {
	
	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeelTopics;
	private int unansweredTopcis;
	
	public DesafioOutputDto() {
		
	}

	public DesafioOutputDto(Category category) {
		this.categoryName = category.getName();
	}

	public DesafioOutputDto(String categoryName, List<String> subcategories, int allTopics, int lastWeelTopics,
			int unansweredTopcis) {
		super();
		this.categoryName = categoryName;
		this.subcategories = subcategories;
		this.allTopics = allTopics;
		this.lastWeelTopics = lastWeelTopics;
		this.unansweredTopcis = unansweredTopcis;
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

	public int getLastWeelTopics() {
		return lastWeelTopics;
	}

	public void setLastWeelTopics(int lastWeelTopics) {
		this.lastWeelTopics = lastWeelTopics;
	}

	public int getUnansweredTopcis() {
		return unansweredTopcis;
	}

	public void setUnansweredTopcis(int unansweredTopcis) {
		this.unansweredTopcis = unansweredTopcis;
	}

	public static List<DesafioOutputDto> listFromCategories(List<Category> categories) {
		return categories.stream()
				.map(DesafioOutputDto::new)
				.collect(Collectors.toList());
	}
}