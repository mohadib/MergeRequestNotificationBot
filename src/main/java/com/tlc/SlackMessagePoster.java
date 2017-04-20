package com.tlc;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class SlackMessagePoster
{
   private String token, fromName, avatarUrl;
   private final String urlFormat = "https://slack.com/api/chat.postMessage?token=%s&channel=%s&text=%s&username=%s&icon_url=%s";

   public SlackMessagePoster( String token, String fromName, String avatarUrl )
   {
      this.token = token;
      this.fromName = fromName;
      this.avatarUrl = avatarUrl;
   }

   public void say( List<String> channels, String text )
   {
      channels.forEach( it -> say( it, text ) );
   }

   public void say( String channel, String text)
   {

      HttpURLConnection con = null;
      InputStream is = null;

      try
      {
         URL url = new URL( formatMsg( channel, text ) );
         con = (HttpURLConnection) url.openConnection();
         con.setRequestMethod( "GET" );
         con.setConnectTimeout( 5000 );
         con.setReadTimeout( 5000 );
         is = con.getInputStream();
         String response = IOUtils.toString( is );
         System.out.println(response);
      }
      catch ( IOException ioe )
      {
         ioe.printStackTrace();
      }
      finally
      {
         IOUtils.closeQuietly( is );
         if( con != null ) con.disconnect();
      }
   }

   private String formatMsg( String channel, String text ) throws UnsupportedEncodingException
   {
      return
        String.format(
          urlFormat,
          token,
          URLEncoder.encode( channel, "UTF-8"),
          URLEncoder.encode( text, "UTF-8"),
          URLEncoder.encode( fromName, "UTF-8"),
          URLEncoder.encode( avatarUrl, "UTF-8" )
        );
   }
}
