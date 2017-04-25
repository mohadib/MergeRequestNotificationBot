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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RssWatcher
{
   private final Logger LOG = LoggerFactory.getLogger( RssWatcher.class );
   private Map<String, Set<String>> titlesMap = new HashMap<>();

   @Autowired
   private JSONConfig config;

   @Scheduled(fixedDelay = 30000, initialDelay = 10000)
   public void parseAllFeeds()
   {
      LOG.info( "Leankit Parser Running for {} feeds.", config.getRssFeeds().size() );
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

         LOG.info( "Found {} entries for {}", feed.getEntries().size(), rss.getName() );

         Iterator<SyndEntry> entries = feed.getEntries().iterator();

         if ( !entries.hasNext() ) return;

         Instant nowMinus10Min = Instant.now().minus( 5, ChronoUnit.MINUTES );
         SyndEntry current = entries.next();
         Set<String> titles = titlesMap.get( rss.getName() );
         while ( current != null && !titles.contains( current.getTitle() ) )
         {
            titles.add( current.getTitle() );

            String pubDateString = ((Element) ((List) current.getForeignMarkup()).get( 0 )).getValue();
            Instant pubDate = ZonedDateTime.parse( pubDateString, DateTimeFormatter.ISO_DATE_TIME ).toInstant();

            if (
                  pubDate.isAfter( nowMinus10Min ) &&
                  current.getDescription().getValue().endsWith( ": Ready." )
              )
            {
               LOG.info( "Found a new entry!: {}", current.getTitle() );
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
      //try to match jira?
      String ticketNumber = "";
      try{
         ticketNumber = title.substring( title.indexOf("(") + 1, title.indexOf(")"));
      }
      catch ( Exception e )
      {
         LOG.info( "Could not parse jira in: " + title );
      }

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl() );
      String msg = ">>> :leankit: New card in ready: " + title;
      if( !ticketNumber.isEmpty() )
      {
         msg += "\n http://jira.tlcdelivers.com/browse/" + ticketNumber;
      }

      api.say( channels, msg );
   }
}
