package br.com.alura.forum.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.User;
import br.com.alura.forum.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> possibleUser = userRepository.findByEmail(username);
		
		return possibleUser
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar usuário com email: " + username));
	}
	
	public User loadUserById(Long userId) {
		Optional<User> possibleUser = userRepository.findById(userId);
		
		return possibleUser
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar usuário com o id: " + userId));
	}
}