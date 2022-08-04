package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private GenreRepository genreRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = movieRepository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+id+"]"));
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findAllReviewsByMovieId(Long id) {
		/*Optional<Movie> movie=movieRepository.findById(id);
		movie.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+id+"]"));
		
		List<Review> listReview = reviewRepository.findAll();
		
		List<ReviewDTO> listReviewDto = new ArrayList<>();
		
		for (Review reviews : listReview) {
			if(reviews.getMovie().getId() == id) {
				listReviewDto.add(new ReviewDTO(reviews));
			}
		}
		
		return listReviewDto;*/
		
		Optional<Movie> obj = movieRepository.findById(id);
		Movie movie = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+id+"]"));
		
		List<Review> listReview = reviewRepository.findAllReviewByMovie(movie);
		
		List<ReviewDTO> listReviewDto = listReview.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
		
		return listReviewDto;
	}
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findMovieByGenre(Long genreId, Pageable pageable) {
		
		Optional<Genre> genre = genreRepository.findById(genreId);
		Genre genreEntity = genre.orElseThrow(() -> new ResourceNotFoundException("Entity not Found. [id = "+genreId+"]"));
		
		Page<Movie> pageMovie = movieRepository.findAllMovieByGenreOrderByTitle(genreEntity, pageable);
		
		return pageMovie.map(x -> new MovieCardDTO(x));
	}

	/*@Transactional(readOnly = true)
	public List<ReviewDTO> findAllReviewsByMovieId(Long id) {
		List<Review> listReview = reviewRepository.findById();
		List<ReviewDTO> listReviewDTO = listReview.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
		return listReviewDTO;
		return null;
	}*/

	/*@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		List<Genre> list = genreRepository.findAll();
		List<GenreDTO> listDTO = list.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
		return listDTO;
	}*/

}
