package br.com.alura.forum.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.input.NewTopicInputDto;
import br.com.alura.forum.dto.input.TopicSearchInputDto;
import br.com.alura.forum.dto.output.DashboardOutputDto;
import br.com.alura.forum.dto.output.TopicBriefOutputDto;
import br.com.alura.forum.dto.output.TopicDashboardOutputDto;
import br.com.alura.forum.dto.output.TopicOutputDto;
import br.com.alura.forum.exception.ResourceNotFoundException;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.service.DashboardService;
import br.com.alura.forum.service.TopicService;
import br.com.alura.forum.validation.NewTopicInputValidator;

@RestController
@RequestMapping(TopicController.BASE_URL)
public class TopicController {
	
	public static final String BASE_URL= "/api/topics";
	
	private DashboardService dashboardService;
	private TopicService topicService;
	
	public TopicController(DashboardService dashboardService, TopicService topicService) {
		this.dashboardService = dashboardService;
		this.topicService = topicService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TopicBriefOutputDto> listTopics(TopicSearchInputDto topicSearch, 
			@PageableDefault(sort="creationInstant", direction=Sort.Direction.DESC) Pageable pageable) {
		
		Specification<Topic> topicSearchSpecification = topicSearch.build();		
		Page<Topic> topics = topicService.findAll(topicSearchSpecification, pageable);
		
		if (topics == null || topics.getTotalElements() == 0) {
			throw new ResourceNotFoundException();
		}
		
		return TopicBriefOutputDto.listFromTopics(topics);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DashboardOutputDto> list() {
		return dashboardService.findAllDesafio();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TopicDashboardOutputDto searchTopic(@PathVariable long id) {
		return topicService.findById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public TopicOutputDto createTopic(@Valid @RequestBody NewTopicInputDto newTopicDto, @AuthenticationPrincipal User user) {
		return topicService.createTopic(newTopicDto, user);
	}
	
	@InitBinder("newTopicInputDto")
	public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User user) {
		binder.addValidators(new NewTopicInputValidator(topicService, user));
	}
}