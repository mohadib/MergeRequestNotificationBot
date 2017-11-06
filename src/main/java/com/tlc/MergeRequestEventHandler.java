package com.tlc;

import org.openactive.gitlab.webhook.domain.GitlabEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("merge")
public class MergeRequestEventHandler implements EventHandler
{
   private final Set<Long> iids = Collections.synchronizedSet( new HashSet<>() );

   @Autowired
   private JSONConfig config;

   @Async
   @Override
   public void handle( GitlabEvent event )
   {
      String projectName = event.getAttributes().getTarget().getName();
      String userName = event.getUser().getName();
      String sourceBranchName = event.getAttributes().getSourceBranch();
      String targetBranchName = event.getAttributes().getTargetBranch();
      String state = event.getAttributes().getState();
      long iid = event.getAttributes().getIid();
      String msg = "";
      System.out.println( state );

      List<String> channelNames = channels( config, sourceBranchName, targetBranchName, projectName );
      if( channelNames.isEmpty() ) return;

      if( "opened".equalsIgnoreCase( state ) || "reopened".equalsIgnoreCase( state ) )
      {
         String description = "";
         if( iids.contains( iid ) )
         {
            msg = updated( projectName, userName, sourceBranchName, targetBranchName );
            description = event.getAttributes().getLastCommit().getMessage();
         }
         else
         {
            iids.add( iid );
            msg = newRequest( projectName, userName, sourceBranchName, targetBranchName );
            description = event.getAttributes().getDescription();
         }
         String title = event.getAttributes().getTitle();
         msg += String.format("\n```%s\n\n%s```", title, description);
         msg += "\n" + event.getAttributes().getUrl();
      }
      else if( "closed".equalsIgnoreCase( state ) )
      {
         iids.remove( iid );
         msg = String.format( "%s: *Merge request* from %s *closed*", projectName, userName );
      }
      else if( "merged".equalsIgnoreCase( state ) )
      {
         iids.remove( iid );
         msg = String.format( ">>> :tips: %s: *Merge request* accepted by %s : %s \u2192 %s ", projectName, userName, sourceBranchName, targetBranchName );
      }

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
      api.say( channelNames, msg );
   }

   private String updated( String projectName, String userName, String sourceBranchName, String targetBranchName)
   {
      return String.format(
        ">>> <!here> :mr: %s: *Merge request* (updated) from %s : %s \u2192 %s",
        projectName,
        userName,
        sourceBranchName,
        targetBranchName
      );
   }

   private String newRequest( String projectName, String userName, String sourceBranchName, String targetBranchName)
   {
      return String.format(
        ">>> <!here> :mr: %s: *Merge request* from %s : %s \u2192 %s",
        projectName,
        userName,
        sourceBranchName,
        targetBranchName
      );
   }
}
