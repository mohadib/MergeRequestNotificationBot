package com.tlc;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.encoder.EchoEncoder;

import java.io.IOException;

public class GitlabRawEventEncoder extends EchoEncoder
{
   @Override
   public void doEncode( Object event ) throws IOException
   {
      if( event instanceof LoggingEvent )
      {
         LoggingEvent le = ( LoggingEvent ) event;
         if( le.getMessage().startsWith( "{" ) )
         {
            super.doEncode( "\n\n" + le.getMessage() );
         }
      }
   }
}
