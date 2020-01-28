package br.com.alura.forum.service;

import org.springframework.stereotype.Service;

import br.com.alura.forum.repository.TopicRepository;

@Service
public class TopicService {

	private TopicRepository repository;

	public TopicService(TopicRepository repository) {
		this.repository = repository;
	}
	
	
}