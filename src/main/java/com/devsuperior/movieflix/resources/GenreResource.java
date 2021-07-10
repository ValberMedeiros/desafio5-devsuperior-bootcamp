package com.devsuperior.movieflix.resources;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.services.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreResource {

    private GenreService service;

    public GenreResource(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public List<GenreDTO> findAll() {
        return service.findAll();
    }

}
