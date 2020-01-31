package br.com.alura.forum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.topic.domain.Topic;

@Component
public class ForumMailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForumMailService.class);
	
	private JavaMailSender mailSender;
    private NewReplyMailFactory newReplyMailFactory;

    public ForumMailService(JavaMailSender mailSender, NewReplyMailFactory newReplyMailFactory) {
		this.mailSender = mailSender;
		this.newReplyMailFactory = newReplyMailFactory;
	}
	
	public void sendNewReplyMail(Answer answer) {
		Topic answeredTopic = answer.getTopic();
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(answeredTopic.getOwnerEmail());
            messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());

            String messageContent = this.newReplyMailFactory.generateNewReplyMailContent(answer);
            messageHelper.setText(messageContent, true);
        };
            
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			LOGGER.error("Não foi possível enviar email para " + answer.getTopic().getOwnerEmail(), e.getMessage());
		}
	}
	
	@Async
	public void sendNewReplyMailAsync(Answer answer) {
		Topic answeredTopic = answer.getTopic();
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(answeredTopic.getOwnerEmail());
            messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());

            String messageContent = this.newReplyMailFactory.generateNewReplyMailContent(answer);
            messageHelper.setText(messageContent, true);
        };
            
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			LOGGER.error("Não foi possível enviar email para " + answer.getTopic().getOwnerEmail(), e.getMessage());
		}
	}
}