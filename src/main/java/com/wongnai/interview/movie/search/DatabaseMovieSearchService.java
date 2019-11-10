package com.wongnai.interview.movie.search;

import java.util.List;

import com.wongnai.interview.movie.sync.MovieDataSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("databaseMovieSearchService")
public class DatabaseMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> search(String queryText) {
		// Didn't do anything here but amend query in movie repository to use lower() when comparing string
		return movieRepository.findByNameContains(queryText);
	}
}
