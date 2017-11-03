package com.tlc.reviewboard;

import com.tlc.JSONConfig;
import com.tlc.SlackMessagePoster;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReviewCommentHandler
{
   @Autowired
   private JSONConfig config;

   private final String newCommentTmpl = ">>> :reviewboard: :thinking_face: %s *commented* on review request\nhttps://reviewboard.carl.org/reviewboard/r/%s";
   private final String shipItTmpl = ">>> :reviewboard: *%s* says :shipit:\nhttps://reviewboard.carl.org/reviewboard/r/%s";

   @Async
   public void handle( String data )
   {
      JSONObject jsonObj = new JSONObject( data );

      // get user who posted review comment
      String user = getString( jsonObj, "review.links.user.title" );

      // bug where parent review request is not included?? doc says it should be there?!
      // get the parent review request id
      String selfHref = getString( jsonObj, "review.links.self.href" );
      String[] urlTokens = selfHref.split( "/" );
      String parentReviewId = urlTokens[ urlTokens.length - 3 ];

      // is this a ship it?
      boolean shipIt = getBoolean( jsonObj, "review.ship_it" );

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl() );

      // based on who submitted the request, find the right channel to post it
      for ( String channel : config.getChannelToUsers().keySet() )
      {
         if ( config.getChannelToUsers().get( channel ).contains( user ) )
         {
            if( shipIt )
            {
               api.say( "iago-dev", String.format( shipItTmpl, user, parentReviewId ) );
            }
            else api.say( "iago-dev", String.format( newCommentTmpl, user, parentReviewId ) );
            break;
         }
      }
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
