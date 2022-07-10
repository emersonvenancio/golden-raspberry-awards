package com.texo.goldenraspberryawards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.texo.goldenraspberryawards.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

	@Query("SELECT DISTINCT p FROM Producer p JOIN FETCH p.movies m WHERE m.winner = true")
	List<Producer> findAllWithWinnerMovies();

}
