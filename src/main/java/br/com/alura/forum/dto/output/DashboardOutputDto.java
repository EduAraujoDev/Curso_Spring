package br.com.alura.forum.dto.output;

import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Category;

public class DashboardOutputDto {
	
	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;
	
	public DashboardOutputDto() {
		
	}

	public DashboardOutputDto(Category category) {
		this.categoryName = category.getName();
	}

	public DashboardOutputDto(Category category, int allTopics, int lastWeekTopics,
			int unansweredTopics) {
		super();
		this.categoryName = category.getName();
		this.subcategories = category.getSubcategoryNames();
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

	public static List<DashboardOutputDto> listFromCategories(List<Category> categories) {
		return categories.stream()
				.map(DashboardOutputDto::new)
				.collect(Collectors.toList());
	}
}