package com.tlc;

import org.openactive.gitlab.webhook.domain.GitlabEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MergeRequestEventHandler
{
   private final Set<Long> iids = Collections.synchronizedSet( new HashSet<>() );

   @Autowired
   private JSONConfig config;

   @Async
   public void mergeRequestEvent( GitlabEvent event )
   {
      String projectName = event.getAttributes().getTarget().getName();
      String userName = event.getUser().getName();
      String sourceBranchName = event.getAttributes().getSourceBranch();
      String targetBranchName = event.getAttributes().getTargetBranch();
      String state = event.getAttributes().getState();
      long iid = event.getAttributes().getIid();
      String msg = "";
      System.out.println( state );

      List<String> channelNames = channels( sourceBranchName, targetBranchName, projectName );
      if( channelNames.isEmpty() ) return;

      if( "opened".equalsIgnoreCase( state ) || "reopened".equalsIgnoreCase( state ) )
      {
         String comment = event.getAttributes().getLastCommit().getMessage();
         if( comment.length() > 70 )
         {
            comment = comment.substring( 0, 50 )  + "...";
         }

         if( iids.contains( iid ) )
         {
            msg = updated( projectName, userName, sourceBranchName, targetBranchName );
         }
         else
         {
            iids.add( iid );
            msg = newRequest( projectName, userName, sourceBranchName, targetBranchName );
         }
         msg += String.format("\n```%s```", comment);
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
         msg = String.format( ">>> :tips: %s: *Merge request* accepted by %s : %s → %s ", projectName, userName, sourceBranchName, targetBranchName );
      }

      sendMsgs( msg, channelNames );
   }

   private void sendMsgs( String msg, List<String> channelNames)
   {
      if( !msg.trim().isEmpty() )
      {
         SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
         for( String channelName : channelNames )
         {
            api.say( channelName, msg );
         }
      }
   }

   private String updated( String projectName, String userName, String sourceBranchName, String targetBranchName)
   {
      return String.format(
        ">>> <!here> :mr: %s: *Merge request* (updated) from %s : %s → %s",
        projectName,
        userName,
        sourceBranchName,
        targetBranchName
      );
   }

   private String newRequest( String projectName, String userName, String sourceBranchName, String targetBranchName)
   {
      return String.format(
        ">>> <!here> :mr: %s: *Merge request* from %s : %s → %s",
        projectName,
        userName,
        sourceBranchName,
        targetBranchName
      );
   }

   private List<String> channels( String source, String target, String project )
   {
      return
        config.getChannelToBranchMap().keySet().stream()
          .filter( channelName -> {

             List<String> branches = config.getChannelToBranchMap().get( channelName );
             return branches.stream().anyMatch( branch ->
               source.toLowerCase().contains( branch.toLowerCase() ) ||
               target.toLowerCase().contains( branch.toLowerCase() )
             );

          } )
          .collect( Collectors.toList() );
   }
}
