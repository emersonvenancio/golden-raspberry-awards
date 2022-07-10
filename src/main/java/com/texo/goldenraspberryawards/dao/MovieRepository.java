package com.texo.goldenraspberryawards.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.texo.goldenraspberryawards.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
