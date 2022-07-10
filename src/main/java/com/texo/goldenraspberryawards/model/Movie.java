package com.texo.goldenraspberryawards.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Movie {

	private Long id;

	private int year;

	private String title;

	private Set<Producer> producers = new HashSet<>();

	private boolean winner;

	public Movie() {
	}


	public Movie(final int year, final String title, final boolean winner) {
		super();
		this.year = year;
		this.title = title;
		this.winner = winner;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "movie_id")
	public Long getId() {
		return id;
	}


	protected void setId(final Long id) {
		this.id = id;
	}


	@Column(name = "`year`")
	public int getYear() {
		return year;
	}


	public void setYear(final int year) {
		this.year = year;
	}


	public boolean isWinner() {
		return winner;
	}


	public void setWinner(final boolean winner) {
		this.winner = winner;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(final String title) {
		this.title = title;
	}


	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "movie_producer", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "producer_id"))
	public Set<Producer> getProducers() {
		return producers;
	}


	public void setProducers(final Set<Producer> producers) {
		this.producers = producers;
	}


	public void addProducer(final Producer producers) {
		this.producers.add(producers);
		producers.getMovies().add(this);
	}


	@Override
	public int hashCode() {
		return Objects.hash(title, year);
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
		final Movie other = (Movie) obj;
		return Objects.equals(title, other.title) && year == other.year;
	}

}
