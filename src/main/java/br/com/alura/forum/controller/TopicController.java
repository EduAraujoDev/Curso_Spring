package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.alura.forum.dto.TopicBriefOutputDto;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;

@Controller
public class TopicController {
	
	@RequestMapping("/api/topics")
	@ResponseBody
	public TopicBriefOutputDto listTopics() {
		Category category = new Category("Java", new Category("Programação"));
		User user = new User("Eduardo", "r.eduardo00@gmial.com", "123456");
		Course course = new Course("Spring",category);
		Topic topic = new Topic("Problema com o JSF", "Erro ao fazer conversão da data", user, course);
		
		TopicBriefOutputDto topicResponseDto = new TopicBriefOutputDto(topic);
		
		return topicResponseDto;
	}
}