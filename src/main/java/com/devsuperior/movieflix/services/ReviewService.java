package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthService authService;

	/*@Transactional(readOnly = true)
	public GenreDTO findById(Long id) {
		Optional<Genre> obj = genreRepository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+id+"]"));
		return new GenreDTO(entity);
	}*/

	/*@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		List<Genre> list = genreRepository.findAll();
		List<GenreDTO> listDTO = list.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
		return listDTO;
	}*/
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_MEMBER')")
	public ReviewDTO insert(ReviewDTO dto) {
		
		User user = authService.authenticated();
		
		Optional<Movie> obj = movieRepository.findById(dto.getMovieId());
		Movie movie = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+dto.getId()+"]"));
		
		Review review = new Review();
		
		review.setText(dto.getText());
		review.setUser(user);
		review.setMovie(movie);
		
		review=reviewRepository.save(review);
		
		return new ReviewDTO(review);
	}

}
