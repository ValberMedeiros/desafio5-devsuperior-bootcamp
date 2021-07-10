package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class ReviewDTO {

    private Long id;
    @NotEmpty
    @NotBlank
    private String text;
    private Long movieId;
    private UserDTO user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String name, Long movieId, UserDTO user) {
        this.id = id;
        this.text = name;
        this.movieId = movieId;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        text = entity.getText();
        movieId = entity.getMovie().getId();
        user = new UserDTO(entity.getUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var reviewDTO = (ReviewDTO) o;
        return Objects.equals(id, reviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
