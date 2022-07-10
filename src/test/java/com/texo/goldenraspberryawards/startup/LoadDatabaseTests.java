package com.texo.goldenraspberryawards.startup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import com.texo.goldenraspberryawards.dao.MovieRepository;
import com.texo.goldenraspberryawards.model.Movie;
import com.texo.goldenraspberryawards.model.Producer;

@SpringBootTest
public class LoadDatabaseTests {

	@Autowired
	MovieRepository movieRepository;

	@Test
	public void testMovieLoad() {
		final List<Movie> movies = movieRepository.findAll();
		assertThat(movies).isNotNull();
		assertThat(movies.size()).isEqualTo(206);
	}


	@Test
	public void testProducerLoad() {
		final Optional<Movie> optional = movieRepository.findBy(Example.of(new Movie(1992, "The Bodyguard", false)),
			ffq -> ffq.project("producers").first());
		assertThat(optional.isPresent()).isTrue();

		final Set<Producer> producers = optional.get().getProducers();
		assertThat(producers.contains(new Producer("Jim Wilson"))).isTrue();
		assertThat(producers.contains(new Producer("Lawrence Kasdan"))).isTrue();
		assertThat(producers.contains(new Producer("Kevin Costner"))).isTrue();
	}
}
