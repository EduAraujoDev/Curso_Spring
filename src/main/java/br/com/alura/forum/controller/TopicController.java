package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.DesafioOutputDto;
import br.com.alura.forum.dto.TopicBriefOutputDto;
import br.com.alura.forum.dto.TopicSearchInputDto;
import br.com.alura.forum.exception.ResourceNotFoundException;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.DashboardService;

@RestController
public class TopicController {
	
	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;
	private DashboardService dashboardService;
	
	public TopicController(TopicRepository topicRepository, CategoryRepository categoryRepository,
			DashboardService dashboardService) {
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
		this.dashboardService = dashboardService;
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
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/api/topics/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DesafioOutputDto> list() {
		return dashboardService.findAllDesafio();
	}
}