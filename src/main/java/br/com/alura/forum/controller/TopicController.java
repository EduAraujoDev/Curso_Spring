package br.com.alura.forum.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
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
		List<DesafioOutputDto> desafioOutputDtos = new ArrayList<>();
		List<Category> categories = categoryRepository.findAll();
		
		categories.stream()
			.filter(c -> c.getSubcategoryNames() != null && c.getSubcategoryNames().size() > 0)
			.forEach(ca -> {
				desafioOutputDtos.add(
						new DesafioOutputDto(
								ca.getName(), 
								ca.getSubcategoryNames(), 
								topicRepository.countTopicsByCategory(ca), 
								topicRepository.countLastWeekTopicsByCategory(ca, Instant.now().minus(5, ChronoUnit.DAYS)), 
								topicRepository.countUnansweredTopicsByCategory(ca))
						);	
			});
		
		return desafioOutputDtos;
	}
}