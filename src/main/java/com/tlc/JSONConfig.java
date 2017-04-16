package com.tlc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class JSONConfig
{
   @JsonProperty("apiToken")
   private String apiToken;

   @JsonProperty("channelToBranch")
   private Map< String, List<String>> channelToBranchMap;

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
}
