package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		// Initialize empty list for storing movies that match with query
		List<Movie> movies = new ArrayList<Movie>();
		for(MovieData movieData: moviesResponse){
			// Call method to check match with query then add movie to prepared list
			if(isContain(movieData.getTitle(), queryText)){
				movies.add(new Movie(movieData));
			}
		}
		return movies;
	}

	private boolean isContain(String title, String query){
		// Return false for full movie name and multiple word query
		if(title.equalsIgnoreCase(query) || query.trim().contains(" ")) return false;
		// Add word boundaries regex to prevent matching with partial word
		String regex = "\\b"+query+"\\b";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(title);
		return m.find();
	}
}
