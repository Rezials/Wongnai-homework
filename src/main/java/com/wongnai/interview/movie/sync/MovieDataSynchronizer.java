package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import com.wongnai.interview.movie.search.InvertedIndexMovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private InvertedIndexMovieSearchService invertedIndexMovieSearchService;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		movieRepository.deleteAll();
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		for(MovieData movieData: moviesResponse){
			// Save each movie into database
			Movie movie = new Movie(movieData);
			movieRepository.save(movie);

			// Split movie's name and create/update movie list for each name part
			String[] name = movie.getName().split(" ");
			for(String namePart : name){
				String key = namePart.toLowerCase();
				List<Movie> firstList = invertedIndexMovieSearchService.getInvertedIndex().get(key);
				if(firstList == null){
					firstList = new ArrayList<Movie>();
					firstList.add(movie);
					invertedIndexMovieSearchService.getInvertedIndex().put(key, firstList);
				}
				else {
					firstList.add(movie);
				}
			}
		}
	}
}
