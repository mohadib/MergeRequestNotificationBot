package com.tlc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class JSONConfig
{
   @JsonProperty("botName")
   private String botName;

   @JsonProperty("avatarUrl")
   private String avatarUrl;

   @JsonProperty("apiToken")
   private String apiToken;

   @JsonProperty("channelToBranch")
   private Map< String, List<String>> channelToBranchMap;

   @JsonProperty("leankit")
   private List<LeankitRss> rssFeeds;

   public List<LeankitRss> getRssFeeds()
   {
      return rssFeeds;
   }

   public void setRssFeeds( List<LeankitRss> rssFeeds )
   {
      this.rssFeeds = rssFeeds;
   }

   public String getApiToken()
   {
      return apiToken;
   }

   public void setApiToken( String apiToken )
   {
      this.apiToken = apiToken;
   }

   public Map<String, List<String>> getChannelToBranchMap()
   {
      return channelToBranchMap;
   }

   public void setChannelToBranchMap( Map<String, List<String>> channelToBranchMap )
   {
      this.channelToBranchMap = channelToBranchMap;
   }

   public String getBotName()
   {
      return botName;
   }

   public void setBotName( String botName )
   {
      this.botName = botName;
   }

   public String getAvatarUrl()
   {
      return avatarUrl;
   }

   public void setAvatarUrl( String avatarUrl )
   {
      this.avatarUrl = avatarUrl;
   }
}
