package com.tlc.reviewboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewRequestWrapper
{
	@JsonProperty("review_request")
	private ReviewRequest reviewRequest;

	@JsonProperty("event")
	private String event;

	@JsonProperty("is_new")
	private boolean isNew;

	public ReviewRequest getReviewRequest()
	{
		return reviewRequest;
	}

	public void setReviewRequest( ReviewRequest reviewRequest )
	{
		this.reviewRequest = reviewRequest;
	}

	public String getEvent()
	{
		return event;
	}

	public void setEvent( String event )
	{
		this.event = event;
	}

	public boolean isNew()
	{
		return isNew;
	}

	public void setNew( boolean aNew )
	{
		isNew = aNew;
	}
}
