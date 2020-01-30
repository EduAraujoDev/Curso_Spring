package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.dto.input.NewAnswerInputDto;
import br.com.alura.forum.dto.output.AnswerOutputDto;
import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.User;
import br.com.alura.forum.service.AnswerService;
import br.com.alura.forum.service.ForumMailService;

@RestController
@RequestMapping("/api/topics/{topicId}/answers")
public class AnswerController {
	
	private AnswerService answerService;
	private ForumMailService forumMailService;	

	public AnswerController(AnswerService answerService, ForumMailService forumMailService) {
		this.answerService = answerService;
		this.forumMailService = forumMailService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerOutputDto answerTopic(@PathVariable Long topicId, @Valid @RequestBody NewAnswerInputDto newAnswerDto,
            @AuthenticationPrincipal User loggedUser) {
		Answer answer = answerService.create(newAnswerDto, topicId, loggedUser);
		
		this.forumMailService.sendNewReplyMailAsync(answer);

        return new AnswerOutputDto(answer);
    }
}