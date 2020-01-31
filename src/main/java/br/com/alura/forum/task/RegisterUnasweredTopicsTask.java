package br.com.alura.forum.task;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.alura.forum.model.OpenTopicsByCategory;
import br.com.alura.forum.repository.OpenTopicsByCategoryRepository;
import br.com.alura.forum.repository.TopicRepository;

@Component
public class RegisterUnasweredTopicsTask {
	
	private TopicRepository topicRepository;
	private OpenTopicsByCategoryRepository openTopicsByCategoryRepository;

	public RegisterUnasweredTopicsTask(TopicRepository topicRepository, 
			OpenTopicsByCategoryRepository openTopicsByCategoryRepository) {
		
		this.topicRepository = topicRepository;
		this.openTopicsByCategoryRepository = openTopicsByCategoryRepository;
	}
	
	@Scheduled(fixedDelay = 30000)
	public void execute() {
		List<OpenTopicsByCategory> topicsByCategories = topicRepository.findOpenTopicsByCategory();
		this.openTopicsByCategoryRepository.saveAll(topicsByCategories);
	}
}