package com.devsuperior.movieflix.resources;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private MovieService service;

    public MovieResource(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public Page<MovieDTO> findAll(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
            Pageable pageable) {
        return service.findAll(genreId, pageable);
    }

    @GetMapping("/{id}")
    public MovieDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
