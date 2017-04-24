package com.tlc.leankit;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.tlc.JSONConfig;
import com.tlc.SlackMessagePoster;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class RssWatcher
{
	private Set<String> titles = new HashSet<>();
	private String rssFeedUrl;

	@Autowired
	private JSONConfig config;

	public void parseFeed()
	{
		try
		{
			URL url = new URL( rssFeedUrl );
			SyndFeed feed = new SyndFeedInput().build( new XmlReader( url ) );
			Iterator<SyndEntry> entries = feed.getEntries().iterator();

			if( !entries.hasNext() ) return;

			Instant nowMinus10Min = Instant.now().minus( 5, ChronoUnit.DAYS );
			SyndEntry current = entries.next();
			while( current != null && !titles.contains( current.getTitle() ) )
			{
				titles.add( current.getTitle() );

				String pubDateString = ((Element)((List)current.getForeignMarkup()).get( 0 )).getValue();
				Instant pubDate = ZonedDateTime.parse( pubDateString, DateTimeFormatter.ISO_DATE_TIME ).toInstant();

				if
				(
					pubDate.isAfter( nowMinus10Min ) &&
					current.getDescription().getValue().endsWith( ": Ready." )
				)
				{
					newCardInReady( current.getTitle() );
				}

				if( entries.hasNext())  current = entries.next();
				else current = null;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private void newCardInReady( String title )
	{
		SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
		api.say( "bottest", "New card in ready: " + title );
	}
}
