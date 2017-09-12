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

      List<String> channelNames = channels( config,  event.getAttributes().getRef(), event.getProject().getName() );
      if( channelNames.isEmpty() ) return;

      String format = ">>> <!here> :hammer: %s: *Pipeline Event* on %s status: *%s*\n";

      long badBuildId = findFaildBuild( event.getBuilds() );

      format += event.getProject().getWebUrl();
      if( !format.endsWith( "/" )) format += "/";
      format += "%s/";

      // if badBuildId == -1 most likely a failure due to bad gitlab ci yml, fall back to pipeline link
      String msg = String.format(
        format,
        event.getProject().getName(),
        event.getAttributes().getRef(),
        event.getAttributes().getStatus(),
        badBuildId == -1 ? "pipelines" : "builds",
        badBuildId == -1 ? event.getAttributes().getId() : badBuildId
      );

      SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl());
      api.say( channelNames, msg );
   }

   private long findFaildBuild( List<Build> builds )
   {
      return
         builds.stream()
           .filter( build -> "failed".equals( build.getStatus() ) )
           .mapToLong( Build::getId )
           .findFirst()
           .orElse( -1 );
   }
}
