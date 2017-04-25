package com.tlc.reviewboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class Link
{

	@JsonProperty( "title" )
	private String title;

	@JsonProperty( "href" )
	private String href;

	@JsonProperty( "method" )
	private String method;

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	@JsonProperty( "href" )
	public String getHref()
	{
		return href;
	}

	@JsonProperty( "href" )
	public void setHref( String href )
	{
		this.href = href;
	}

	@JsonProperty( "method" )
	public String getMethod()
	{
		return method;
	}

	@JsonProperty( "method" )
	public void setMethod( String method )
	{
		this.method = method;
	}
}
