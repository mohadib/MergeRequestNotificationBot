package com.tlc;

import org.openactive.gitlab.webhook.domain.GitlabEvent;

import java.util.List;
import java.util.stream.Collectors;

public interface EventHandler
{
   void handle( GitlabEvent event);


   default List<String> channels( JSONConfig config, String target, String project )
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

   default List<String> channels( JSONConfig config, String source, String target, String project )
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
