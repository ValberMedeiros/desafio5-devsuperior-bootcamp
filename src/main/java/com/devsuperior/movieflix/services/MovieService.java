package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository repository;
    private ReviewRepository reviewRepository;

    public MovieService(MovieRepository repository, ReviewRepository reviewRepository) {
        this.repository = repository;
        this.reviewRepository = reviewRepository;
    }

    public MovieDTO findById(Long id) {
        Optional<Movie> entity = repository.findById(id);
        if (entity.isPresent()) {
            return new MovieDTO(entity.get());
        } else  {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    public Page<MovieDTO> findAll(Long genreId, Pageable pageable) {
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title").ascending());
        Page<Movie> movies = null;
        if (genreId > 0) {
            movies = repository.findAllByGenre_Id(genreId, page);
        } else {
            movies = repository.findAll(page);
        }
        return movies
                .map(MovieDTO::new);
    }

    public List<ReviewDTO> getReviews(Long movieId) {
        List<Review> reviews = reviewRepository.findAllByMovieId(movieId);
        return reviews.stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }
}
