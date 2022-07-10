package com.texo.goldenraspberryawards.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
		final List<AwardInteval> intervals = mapToIntervals(repository.findAllWithWinnerMovies());
		if (intervals.isEmpty()) {
			return new ProducerAwardsFrequecy();
		}
		sortByInterval(intervals);

		final Map<Integer, List<AwardInteval>> map = groupingByInterval(intervals);

		return new ProducerAwardsFrequecy(map.get(intervals.get(0).getInterval()),
			map.get(intervals.get(intervals.size() - 1).getInterval()));
	}


	private void sortByInterval(final List<AwardInteval> intervals) {
		Collections.sort(intervals, (a, b) -> Integer.compare(a.getInterval(), b.getInterval()));
	}


	private Map<Integer, List<AwardInteval>> groupingByInterval(final List<AwardInteval> intervals) {
		return intervals.stream().collect(Collectors.groupingBy(AwardInteval::getInterval, toList()));
	}


	private List<AwardInteval> mapToIntervals(final List<Producer> producers) {
		final List<AwardInteval> intervals = new ArrayList<>();
		for (final Producer producer : producers) {
			Movie lastMovie = null;
			for (final Movie movie : producer.getMovies()) {
				if (lastMovie != null) {
					intervals.add(new AwardInteval(producer.getName(), lastMovie.getYear(), movie.getYear()));
				}
				lastMovie = movie;
			}
		}
		return intervals;
	}
}
