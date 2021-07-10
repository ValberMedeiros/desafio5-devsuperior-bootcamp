package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private ReviewRepository repository;
    private MovieRepository movieRepository;
    private AuthService authService;

    public ReviewService(ReviewRepository repository, MovieRepository movieRepository, AuthService authService) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.authService = authService;
    }

    public List<ReviewDTO> findAll() {
        List<Review> reviews = repository.findAll();
        return reviews.stream()
            .map(ReviewDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        var movie = movieRepository.getOne(reviewDTO.getMovieId());
        var user = authService.authenticated();

        var review = new Review();
        review.setText(reviewDTO.getText());
        review.setMovie(movie);
        review.setUser(user);
        reviewDTO = new ReviewDTO(repository.save(review));
        return reviewDTO;
    }
}
