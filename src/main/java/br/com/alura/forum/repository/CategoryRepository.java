package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import br.com.alura.forum.model.Category;

public interface CategoryRepository extends Repository<Category, Long>, JpaSpecificationExecutor<Category> {
	
	List<Category> findAll();
}