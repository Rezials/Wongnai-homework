package com.wongnai.interview.movie.search;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	private HashMap<String, List<Movie>> invertedIndex = new HashMap<String, List<Movie>>();

	@Override
	public List<Movie> search(String queryText) {

		// Split queryText into words
		String[] queryArray = queryText.toLowerCase().split(" ");
		// Get movie list containing the first word
		List<Movie> firstList = invertedIndex.get(queryArray[0]);
		// If movie list is empty then there're no movie containing such word or it's a partial word, return empty list
		if(firstList == null ){
			return new ArrayList<Movie>();
		}
		// Prepare another list identical to the movie list to prevent removing movie from the list in inverted index
		List<Movie> baseList = new ArrayList<Movie>(firstList);
		for(int index = 1 ; index < queryArray.length ; index++){
			List<Movie> movieList = invertedIndex.get(queryArray[index]);
			// Remove movie from base list that doesn't exist in the new list of this iteration & use HashSet because it's faster(?).
			baseList.retainAll(new HashSet(movieList));
		}
		return baseList;
	}

	public HashMap<String, List<Movie>> getInvertedIndex() {
		return invertedIndex;
	}
}
