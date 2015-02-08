package com.jvj.test.client;

public class Rating {

	private final String label;
	private int count;
	private int ratingSum;
	private Integer userRating = null;

	public Rating(final String label, int count, int ratingSum) {
		this.label = label;
		this.count = count;
		this.ratingSum = ratingSum;
	}

	public int getRating() {
		return Math.round(ratingSum / count);
	}

	public Integer getUserRating() {
		return userRating;
	}

	public void setUserRating(Integer value) {

		userRating = value;
		ratingSum += value;
		count++;
	}

	public String getLabel() {
		return label;
	}
}
