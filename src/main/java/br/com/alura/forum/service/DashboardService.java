package br.com.alura.forum.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.alura.forum.dto.DesafioOutputDto;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;

@Service
public class DashboardService {
	
	private static final int DIAS_UMA_SEMANA = 7;
	
	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;
	
	public DashboardService(TopicRepository topicRepository, CategoryRepository categoryRepository) {
		super();
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public List<DesafioOutputDto> findAllDesafio() {
		List<Category> categories = categoryRepository.findAll();
		
		/* List<DesafioOutputDto> desafioOutputDtos = new ArrayList<>();
		
		categories.stream()
			.filter(c -> c.getSubcategoryNames() != null && c.getSubcategoryNames().size() > 0)
			.forEach(category -> {
				desafioOutputDtos.add(
						new DesafioOutputDto(
								category,
								topicRepository.countTopicsByCategory(category), 
								topicRepository.countLastWeekTopicsByCategory(category, Instant.now().minus(7, ChronoUnit.DAYS)), 
								topicRepository.countUnansweredTopicsByCategory(category))
						);	
			}); */
		
		return categories
				.stream()
				.filter(c -> c.getSubcategoryNames() != null && c.getSubcategoryNames().size() > 0)
				.map(c -> new DesafioOutputDto(
						c,
						topicRepository.countTopicsByCategory(c), 
						topicRepository.countLastWeekTopicsByCategory(c, Instant.now().minus(DIAS_UMA_SEMANA, ChronoUnit.DAYS)), 
						topicRepository.countUnansweredTopicsByCategory(c)))
				.collect(Collectors.toList());
	}
}