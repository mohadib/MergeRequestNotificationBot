package com.tlc;

import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class GitlabRawEventLogger extends CommonsRequestLoggingFilter
{
   @Override
   protected String createMessage( HttpServletRequest request, String prefix, String suffix )
   {
      StringBuilder msg = new StringBuilder();
      ContentCachingRequestWrapper wrapper =
        WebUtils.getNativeRequest( request, ContentCachingRequestWrapper.class );

      if ( wrapper != null )
      {
         byte[] buf = wrapper.getContentAsByteArray();
         if ( buf.length > 0 )
         {
            int length = Math.min( buf.length, getMaxPayloadLength() );
            String payload;
            try
            {
               payload = new String( buf, 0, length, wrapper.getCharacterEncoding() );
            }
            catch ( UnsupportedEncodingException ex )
            {
               payload = "[unknown]";
            }
            msg.append( payload );
         }
      }

      return msg.toString();
   }
}
