package com.devsuperior.movieflix.resources;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewResource {

    private ReviewService service;

    public ReviewResource(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> insert(@RequestBody @Valid ReviewDTO reviewDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(reviewDTO));
    }

}
