package br.com.alura.forum.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.DesafioOutputDto;
import br.com.alura.forum.dto.TopicBriefOutputDto;
import br.com.alura.forum.dto.TopicSearchInputDto;
import br.com.alura.forum.exception.ResourceNotFoundException;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;

@RestController
public class TopicController {
	
	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;
	
	public TopicController(TopicRepository topicRepository, CategoryRepository categoryRepository) {
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TopicBriefOutputDto> listTopics(TopicSearchInputDto topicSearch, 
			@PageableDefault(sort="creationInstant", direction=Sort.Direction.DESC) Pageable pageable) {
		
		Specification<Topic> topipSearchSpecification = topicSearch.build();		
		Page<Topic> topics = topicRepository.findAll(topipSearchSpecification, pageable);
		
		if (topics == null || topics.getTotalElements() == 0) {
			throw new ResourceNotFoundException();
		}
		
		return TopicBriefOutputDto.listFromTopics(topics);
	}
	
	@GetMapping(value = "/api/topics/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DesafioOutputDto> list() {
		List<Category> categories = categoryRepository.findAll();
		/*List<DesafioOutputDto> desafioOutputDtos = new ArrayList<>();
		
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
			});
		
		return desafioOutputDtos;*/
		
		return categories
				.stream()
				.filter(c -> c.getSubcategoryNames() != null && c.getSubcategoryNames().size() > 0)
				.map(c -> new DesafioOutputDto(
						c,
						topicRepository.countTopicsByCategory(c), 
						topicRepository.countLastWeekTopicsByCategory(c, Instant.now().minus(7, ChronoUnit.DAYS)), 
						topicRepository.countUnansweredTopicsByCategory(c)))
				.collect(Collectors.toList());
	}
}