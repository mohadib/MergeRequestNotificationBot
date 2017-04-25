package com.tlc.reviewboard;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class Group
{

	@JsonProperty( "href" )
	private String href;

	@JsonProperty( "method" )
	private String method;

	@JsonProperty( "title" )
	private String title;

	@JsonIgnore
	private Map< String, Object > additionalProperties = new HashMap< String, Object >();

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

	@JsonProperty( "title" )
	public String getTitle()
	{
		return title;
	}

	@JsonProperty( "title" )
	public void setTitle( String title )
	{
		this.title = title;
	}

	@JsonAnyGetter
	public Map< String, Object > getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty( String name, Object value )
	{
		this.additionalProperties.put( name, value );
	}

}
