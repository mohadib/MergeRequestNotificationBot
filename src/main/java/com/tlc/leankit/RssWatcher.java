package com.tlc.leankit;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.tlc.JSONConfig;
import com.tlc.LeankitRss;
import com.tlc.SlackMessagePoster;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
   private Map<String, Set<String>> titlesMap = new HashMap<>();

   @Autowired
   private JSONConfig config;

   @Scheduled(fixedDelay = 60000, initialDelay = 10000)
   public void parseAllFeeds()
   {
      for ( LeankitRss rss : config.getRssFeeds() )
      {
         titlesMap.putIfAbsent( rss.getName(), new HashSet<>() );
         parseFeed( rss );
      }
   }

   public void parseFeed( LeankitRss rss )
   {
      try
      {
         URL url = new URL( rss.getUrl() );
         SyndFeed feed = new SyndFeedInput().build( new XmlReader( url ) );
         Iterator<SyndEntry> entries = feed.getEntries().iterator();

         if ( !entries.hasNext() ) return;

         Instant nowMinus10Min = Instant.now().minus( 10, ChronoUnit.DAYS );
         SyndEntry current = entries.next();
         Set<String> titles = titlesMap.get( rss.getName() );
         while ( current != null && !titles.contains( current.getTitle() ) )
         {
            titles.add( current.getTitle() );

            String pubDateString = ((Element) ((List) current.getForeignMarkup()).get( 0 )).getValue();
            Instant pubDate = ZonedDateTime.parse( pubDateString, DateTimeFormatter.ISO_DATE_TIME ).toInstant();

            if
              (
              pubDate.isAfter( nowMinus10Min ) &&
                current.getDescription().getValue().endsWith( ": Ready." )
              )
            {
               newCardInReady( rss.getChannels(), current.getTitle() );
            }

            if ( entries.hasNext() ) current = entries.next();
            else current = null;
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
   }

   private void newCardInReady( List<String> channels, String title )
   {
      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl() );
      api.say( channels, "New card in ready: " + title );
   }
}
