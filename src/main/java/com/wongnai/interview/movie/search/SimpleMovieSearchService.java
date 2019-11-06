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
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<Movie> movies = new ArrayList<Movie>();
		for(MovieData movieData: moviesResponse){
			if(isContain(movieData.getTitle(), queryText)){
				movies.add(new Movie(movieData));
			}
		}
		return movies;
	}

	private boolean isContain(String title, String query){
		if(title.equalsIgnoreCase(query) || query.trim().contains(" ")) return false;
		String regex = "\\b"+query+"\\b";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(title);
		return m.find();
	}
}
