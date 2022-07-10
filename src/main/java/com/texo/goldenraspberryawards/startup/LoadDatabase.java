package com.texo.goldenraspberryawards.startup;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.texo.goldenraspberryawards.dao.MovieRepository;
import com.texo.goldenraspberryawards.model.Movie;
import com.texo.goldenraspberryawards.model.Producer;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	private final int yearIndex = 0;

	private final int titleIndex = 1;

	private final int producersIndex = 3;

	private final int winnerIndex = 4;

	private final Map<String, Producer> producerCache = new HashMap<String, Producer>();

	@Value("${csv.movielist.file}")
	private String movielist;

	@Bean
	CommandLineRunner initDatabase(final MovieRepository movieRepo) {

		final List<Movie> movies = readLines().stream()//
			.map(line -> {
				final Movie movie = new Movie(parseInt(line[yearIndex]), line[titleIndex], extractWinner(line));
				extractProducers(movie, line);
				return movie;
			})//
			.collect(toList());

		return args -> {
			log.info("Preloading " + movieRepo.saveAll(movies));
		};
	}


	private boolean extractWinner(final String[] line) {
		return line.length > 4 ? "yes".equals(line[winnerIndex]) : false;
	}


	private String toKey(final String name) {
		return Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}|\\s]", "");
	}


	private void extractProducers(final Movie movie, final String[] line) {

		final String[] producerStr = line[producersIndex].split(",");
		for (int i = 0; i < producerStr.length - 1; i++) {
			movie.addProducer(getUniqueRef(producerStr[i].trim()));
		}
		for (final String name : producerStr[producerStr.length - 1].split("and")) {
			movie.addProducer(getUniqueRef(name.trim()));
		}
	}


	private Producer getUniqueRef(final String producerName) {
		return producerCache.computeIfAbsent(toKey(producerName), (key) -> new Producer(producerName));
	}


	private List<String[]> readLines() {
		final Pattern pattern = Pattern.compile(";");
		try {
			return Files.readAllLines(getPath())//
				.stream().map(pattern::split)//
				.skip(1)//
				.collect(toList());
		} catch (final Exception e) {
			log.error("Error on reading csv");
		}
		return Collections.emptyList();
	}


	private Path getPath() {
		final ClassLoader classLoader = getClass().getClassLoader();
		final URL resource = classLoader.getResource(movielist);
		if (resource != null) {
			return Paths.get(resource.getFile());
		}
		return Paths.get(movielist);
	}
}
