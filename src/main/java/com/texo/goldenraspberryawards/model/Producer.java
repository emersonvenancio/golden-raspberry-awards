package com.texo.goldenraspberryawards.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Producer {

	private Integer id;

	private String name;

	private Set<Movie> movies = new HashSet<Movie>();

	public Producer() {
	}


	public Producer(final String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(final String name) {
		this.name = name;
	}


	@Id
	@Column(name = "producer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}


	protected void setId(final Integer id) {
		this.id = id;
	}


	@ManyToMany(mappedBy = "producers")
	public Set<Movie> getMovies() {
		return movies;
	}


	public void setMovies(final Set<Movie> movies) {
		this.movies = movies;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Producer other = (Producer) obj;
		return Objects.equals(name, other.name);
	}

}
