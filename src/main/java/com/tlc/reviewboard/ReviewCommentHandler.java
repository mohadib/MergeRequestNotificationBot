package com.tlc.reviewboard;

import com.tlc.JSONConfig;
import com.tlc.SlackMessagePoster;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ReviewCommentHandler
{
   @Autowired
   private JSONConfig config;

   private final List<String> users = Arrays.asList( "jdavis", "ptourvil", "uly" );
   private final String newCommentTmpl = ">>> :reviewboard: :thinking_face: %s *commented* on review request\nhttps://reviewboard.carl.org/reviewboard/r/%s";
   private final String shipItTmpl = ">>> :reviewboard: *%s* says :shipit:\nhttps://reviewboard.carl.org/reviewboard/r/%s";

   @Async
   public void handle( String data )
   {
      JSONObject jsonObj = new JSONObject( data );

      // get user who posted review comment
      String user = getString( jsonObj, "review.links.user.title" );

      //do we care?
      if( !users.contains( user ) )
      {
         System.out.println("Skipping review comment from " + user);
         return;
      }

      // bug where parent review request is not included?? doc says it should be there?!
      // get the parent review request id
      String selfHref = getString( jsonObj, "review.links.self.href" );
      String[] urlTokens = selfHref.split( "/" );
      String parentReviewId = urlTokens[ urlTokens.length - 3 ];

      // is this a ship it?
      boolean shipIt = getBoolean( jsonObj, "review.ship_it" );

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl() );
      if( shipIt )
      {
         api.say( "iago-dev", String.format( shipItTmpl, user, parentReviewId ) );
      }
      else api.say( "iago-dev", String.format( newCommentTmpl, user, parentReviewId ) );
   }

   private String getString( JSONObject data, String query )
   {
      String[] tokens = query.split( "\\." );
      for( int i = 0; i < (tokens.length - 1); i++)
      {
         data = data.getJSONObject( tokens[i] );
      }
      return data.getString( tokens[ tokens.length - 1 ] );
   }

   private boolean getBoolean( JSONObject data, String query )
   {
      String[] tokens = query.split( "\\." );
      for( int i = 0; i < (tokens.length - 1); i++)
      {
         data = data.getJSONObject( tokens[i] );
      }
      return data.getBoolean( tokens[ tokens.length - 1 ] );
   }
}
