package com.tlc;

import org.openactive.gitlab.webhook.domain.Build;
import org.openactive.gitlab.webhook.domain.GitlabEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("pipeline")
public class PipelineEventHandler implements EventHandler
{
   @Autowired
   private JSONConfig config;

   @Async
   @Override
   public void handle( GitlabEvent event )
   {
      if( !"failed".equals( event.getAttributes().getStatus() ) ) return;

      List<String> channelNames = channels( event.getAttributes().getRef(), event.getProject().getName() );
      if( channelNames.isEmpty() ) return;

      String format = ">>> <!here> :hammer: %s: *Pipeline Event* on %s status: *%s*\n";
      format += "https://git.carl.org/connect/connect/builds/" + findFaildBuild( event.getBuilds() );


      String msg = String.format(
        format,
        event.getProject().getName(),
        event.getAttributes().getRef(),
        event.getAttributes().getStatus()
      );

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
      api.say( channelNames, msg );
   }

   private long findFaildBuild( List<Build> builds )
   {
      return
         builds.stream()
           .filter( build -> "failed".equals( build.getStatus() ) )
           .mapToLong( build -> build.getId() )
           .findFirst()
           .orElse( -1 );
   }

   private List<String> channels( String target, String project )
   {
      return
        config.getChannelToBranchMap().keySet().stream()
          .filter( channelName -> {

             List<String> branches = config.getChannelToBranchMap().get( channelName );
             return branches.stream().anyMatch( branch ->
               target.toLowerCase().contains( branch.toLowerCase() )
             );

          } )
          .collect( Collectors.toList() );
   }
}
