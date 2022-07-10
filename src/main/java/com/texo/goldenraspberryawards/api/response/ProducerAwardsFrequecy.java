package com.texo.goldenraspberryawards.api.response;

import java.util.Collections;
import java.util.List;

public class ProducerAwardsFrequecy {

	private List<AwardInteval> min = Collections.emptyList();

	private List<AwardInteval> max = Collections.emptyList();

	public ProducerAwardsFrequecy() {
	}


	public ProducerAwardsFrequecy(final List<AwardInteval> min, final List<AwardInteval> max) {
		super();
		this.min = min;
		this.max = max;
	}


	public List<AwardInteval> getMin() {
		return min;
	}


	public List<AwardInteval> getMax() {
		return max;
	}

	public static class AwardInteval {

		private final String producer;

		private final int interval;

		private final int previousWin;

		private final int followingWin;

		public AwardInteval(final String producer, final int previousWin, final int followingWin) {
			super();
			this.producer = producer;
			interval = followingWin - previousWin;
			this.previousWin = previousWin;
			this.followingWin = followingWin;
		}


		public String getProducer() {
			return producer;
		}


		public int getInterval() {
			return interval;
		}


		public int getPreviousWin() {
			return previousWin;
		}


		public int getFollowingWin() {
			return followingWin;
		}

	}

}
