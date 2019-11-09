package com.wongnai.interview.movie;

import com.wongnai.interview.movie.external.MovieData;
import org.mockito.internal.matchers.InstanceOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> actors = new ArrayList<>();

	/**
	 * Required by JPA.
	 */
	protected Movie() {
	}

	public Movie(String name) {
		this.name = name;
	}

	public Movie(MovieData movieData){
		this.name = movieData.getTitle();
		this.actors = movieData.getCast();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getActors() {
		return actors;
	}

	@Override
	public int hashCode(){
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object comparingObject){
		if(this == comparingObject) return true;
		else if(comparingObject instanceof Movie){
			Movie comparingMovie = (Movie) comparingObject;
			return Objects.equals(this.id, comparingMovie.id);
		}
    else return false;
	}
}
