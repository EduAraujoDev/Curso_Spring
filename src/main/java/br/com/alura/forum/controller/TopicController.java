package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.DesafioOutputDto;
import br.com.alura.forum.dto.NewTopicInputDto;
import br.com.alura.forum.dto.TopicBriefOutputDto;
import br.com.alura.forum.dto.TopicSearchInputDto;
import br.com.alura.forum.exception.ResourceNotFoundException;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.DashboardService;

@RestController
@RequestMapping(TopicController.BASE_URL)
public class TopicController {
	
	public static final String BASE_URL= "/api/topics";
	
	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;
	private CourseRepository courseRepository;
	private DashboardService dashboardService;
	
	public TopicController(TopicRepository topicRepository, CategoryRepository categoryRepository,
			DashboardService dashboardService, CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
		this.dashboardService = dashboardService;
		this.courseRepository = courseRepository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
	@GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DesafioOutputDto> list() {
		return dashboardService.findAllDesafio();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public void abc(NewTopicInputDto newTopicDto) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Course course = courseRepository.findByName(newTopicDto.getCourseName());
		Topic topic = new Topic(newTopicDto.getShortDescription(), newTopicDto.getContent(), user, course);
	}
}