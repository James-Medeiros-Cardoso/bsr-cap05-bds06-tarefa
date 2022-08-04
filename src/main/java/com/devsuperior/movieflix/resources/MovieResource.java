package com.devsuperior.movieflix.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

	@Autowired
	private MovieService movieService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		MovieDetailsDTO dto = movieService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	

	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findMovieByGenre(
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId,
			Pageable pageable
		) {
		Page<MovieCardDTO> movieCardDTO = movieService.findMovieByGenre(genreId, pageable);
		return ResponseEntity.ok(movieCardDTO);
		//return ResponseEntity.ok().body(movieCardDTO);
	}

	@GetMapping(value = "/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> reviewsByMovieId(@PathVariable Long id) {
		List<ReviewDTO> listReviewsDTO = movieService.findAllReviewsByMovieId(id);
		return ResponseEntity.ok().body(listReviewsDTO);
	}

}