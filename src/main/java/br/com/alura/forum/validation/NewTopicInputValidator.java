package br.com.alura.forum.validation;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.alura.forum.dto.input.NewTopicInputDto;
import br.com.alura.forum.model.PossibleSpam;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.service.TopicService;

public class NewTopicInputValidator implements Validator {
	
	private TopicService topicService;
	private User user;

	public NewTopicInputValidator(TopicService topicService, User user) {
		this.topicService = topicService;
		this.user = user;
	}

	@Override
	public boolean supports(Class<?> clazz) {	
		return NewTopicInputDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
		List<Topic> topics = topicService.findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(user, oneHourAgo);
		
		/*if (topics.size() >= 4) {
			Instant instantOfTheOldestTopic = topics.get(0).getCreationInstant();
			
			long minuteToNextTopic = Duration.between(oneHourAgo, instantOfTheOldestTopic)
					.getSeconds() / 60;
			
			errors.reject("newTopicInputDto.limit.exceeded", new Object[] { minuteToNextTopic }, 
					"O limite individual de novos tópico por hora foi excedido");
		}*/
		
		PossibleSpam possibleSpam = new PossibleSpam(topics);
		
		if (possibleSpam.hasTopicLimitExceeded()) {
			long minuteToNextTopic = possibleSpam.minutesToNextTopics(oneHourAgo);
			
			errors.reject("newTopicInputDto.limit.exceeded", new Object[] { minuteToNextTopic }, 
					"O limite individual de novos tópico por hora foi excedido");
		}
	}
}