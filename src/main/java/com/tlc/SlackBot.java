package com.tlc;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackBot
{
   private SlackSession session;

   public SlackBot( JSONConfig config ) throws IOException
   {
      session = SlackSessionFactory.createWebSocketSlackSession( config.getApiToken() );
      session.connect();
   }

   public void say( String channelName, String message )
   {
      SlackChannel channel = session.findChannelByName( channelName );
      if( channel != null )
      {
         session.sendMessage( channel, message );
      }
   }
}
