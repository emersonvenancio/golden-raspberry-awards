package com.texo.goldenraspberryawards.service;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.texo.goldenraspberryawards.api.response.ProducerAwardsFrequecy;
import com.texo.goldenraspberryawards.api.response.ProducerAwardsFrequecy.AwardInteval;
import com.texo.goldenraspberryawards.dao.ProducerRepository;
import com.texo.goldenraspberryawards.model.Movie;
import com.texo.goldenraspberryawards.model.Producer;

@Service
public class ProducerService {

	@Autowired
	private ProducerRepository repository;

	public ProducerAwardsFrequecy getAwardsFrequecy() {
		final LinkedList<AwardInteval> intervals = mapToIntervals(repository.findAllWithWinnerMovies());
		if (intervals.isEmpty()) {
			return new ProducerAwardsFrequecy();
		}

		final Map<Integer, List<AwardInteval>> intervalsMap = groupingByInterval(intervals);

		final List<AwardInteval> min = intervalsMap.get(intervals.getFirst().getInterval());
		final List<AwardInteval> max = intervalsMap.get(intervals.getLast().getInterval());

		return new ProducerAwardsFrequecy(min, max);
	}


	private Map<Integer, List<AwardInteval>> groupingByInterval(final List<AwardInteval> intervals) {
		return intervals.stream().collect(Collectors.groupingBy(AwardInteval::getInterval, toList()));
	}


	private LinkedList<AwardInteval> mapToIntervals(final List<Producer> producers) {
		return producers.stream()//
			.filter(p -> p.getMovies().size() > 1)//
			.map(this::mapToIntervals)//
			.flatMap(List::stream)//
			.sorted((a, b) -> Integer.compare(a.getInterval(), b.getInterval())).collect(toCollection(LinkedList::new));
	}


	private List<AwardInteval> mapToIntervals(final Producer producer) {
		final List<Movie> movies = sortByYear(producer.getMovies());
		final List<AwardInteval> result = new ArrayList<>(movies.size() - 1);

		Movie lastMovie = null;
		for (final Movie movie : movies) {
			if (lastMovie != null) {
				result.add(new AwardInteval(producer.getName(), lastMovie.getYear(), movie.getYear()));
			}
			lastMovie = movie;
		}
		return result;
	}


	private List<Movie> sortByYear(final Set<Movie> movies) {
		return movies.stream()//
			.sorted((a, b) -> Integer.compare(a.getYear(), b.getYear()))//
			.collect(Collectors.toList());
	}
}
