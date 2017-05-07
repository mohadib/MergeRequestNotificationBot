package com.tlc;

import org.openactive.gitlab.webhook.domain.GitlabEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("comment")
public class CommentEventHandler implements EventHandler
{
   private Map<Long, Map< String, Long>> mrToAuthorCommentMap = new ConcurrentHashMap<>();
   private final long TEN_MINUTES = 1000 * 60 * 10;
   private final String template = ">>> :thinking_face: %s *commented* on merge request : %s → %s\n%s";

   @Autowired
   private JSONConfig config;


   //TODO, write cleanup for mrToAuthorCommentMap?

   private void updateCommentMap( String author, long mrId )
   {
      Map<String, Long> entry = mrToAuthorCommentMap.getOrDefault( mrId, new ConcurrentHashMap<>() );
      entry.put( author, System.currentTimeMillis() );
      mrToAuthorCommentMap.put( mrId, entry );
   }

   @Async
   @Override
   public void handle( GitlabEvent event )
   {

      if( !event.getAttributes().getNoteableType().equalsIgnoreCase( "MergeRequest" ) )
      {
         return;
      }

      // Jason commented on merge request: Snow-123 → Snow
      // http://foo/merge/44

      Map<String, Long> entry = mrToAuthorCommentMap.get( event.getMergeRequest().getId() );
      if( entry != null )
      {
         Long timestamp = entry.get( event.getUser().getName() );
         if( timestamp != null )
         {
            if( timestamp + TEN_MINUTES >= System.currentTimeMillis() )
            {
               //we have received a comment from this user on this MR in the last 10 minutes
               return;
            }
         }
      }

      String author = event.getUser().getName();
      String sourceBranchName = event.getMergeRequest().getSourceBranch();
      String targetBranchName = event.getMergeRequest().getTargetBranch();
      String url = event.getAttributes().getUrl();

      updateCommentMap( author, event.getMergeRequest().getId() );

      String msg = String.format( template, author, sourceBranchName, targetBranchName, url );

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
      api.say( channels( config, sourceBranchName, targetBranchName, null ),  msg);
   }

}
