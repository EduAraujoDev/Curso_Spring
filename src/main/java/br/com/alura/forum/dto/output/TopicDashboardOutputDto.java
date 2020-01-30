package br.com.alura.forum.dto.output;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;

public class TopicDashboardOutputDto {
	
	private long id;
	private String shortDescription;
	private String content;
	private TopicStatus status;
	private Instant creationInstant;
	private Instant lastUpdate;
	
	private String courseName;
	private String subcategoryName;
	private String categoryName;
	private String ownerName;
	
	private long numberOfResponses;
	
	private List<AnswerOutputDto> answers = new ArrayList<>();

	public TopicDashboardOutputDto() {
	}

	public TopicDashboardOutputDto(Topic topic) {
		this.id = topic.getId();
		this.shortDescription = topic.getShortDescription();
		this.content = topic.getContent();
		this.status = topic.getStatus();
		this.creationInstant = topic.getCreationInstant();
		this.lastUpdate = topic.getLastUpdate();
		this.courseName = topic.getCourse().getName();
		this.subcategoryName = topic.getCourse().getSubcategoryName();
		this.categoryName = topic.getCourse().getCategoryName();
		this.ownerName = topic.getOwnerName();
		this.numberOfResponses = topic.getAnswers().size();
		
		List<AnswerOutputDto> answers = AnswerOutputDto.listFromAnswers(topic.getAnswers());
		this.answers.addAll(answers);
	}

	public long getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getContent() {
		return content;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public Instant getCreationInstant() {
		return creationInstant;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public long getNumberOfResponses() {
		return numberOfResponses;
	}

	public List<AnswerOutputDto> getAnswers() {
		return answers;
	}
}