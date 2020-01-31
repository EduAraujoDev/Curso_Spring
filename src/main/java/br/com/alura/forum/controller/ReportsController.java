package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.forum.repository.OpenTopicsByCategoryRepository;

@Controller
@RequestMapping("/admin/reports")
public class ReportsController {
	
	private OpenTopicsByCategoryRepository openTopicsByCategoryRepository;

	public ReportsController(OpenTopicsByCategoryRepository openTopicsByCategoryRepository) {
		this.openTopicsByCategoryRepository = openTopicsByCategoryRepository;
	}
	
	@GetMapping("/open-topics-by-category")
	public String showOpenTopicsByCategoryReport(Model model) {
		model.addAttribute("openTopics", openTopicsByCategoryRepository.findAllByCurrentMonth());
		return "report";
	}
}