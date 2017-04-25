package com.tlc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LeankitRss
{
   @JsonProperty("name")
   private String name;

   @JsonProperty("rss")
   private String url;

   @JsonProperty("channels")
   private List<String> channels;

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl( String url )
   {
      this.url = url;
   }

   public List<String> getChannels()
   {
      return channels;
   }

   public void setChannels( List<String> channels )
   {
      this.channels = channels;
   }
}
