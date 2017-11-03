package com.tlc.reviewboard;

import com.tlc.JSONConfig;
import com.tlc.SlackMessagePoster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReviewRequestHandler
{
	@Autowired
	private JSONConfig config;

	@Async
	public void handle( ReviewRequestWrapper reviewRequestWrapper )
	{
		// is in a group we care about 'dev' ?
		ReviewRequest request = reviewRequestWrapper.getReviewRequest();

		boolean forDev = request.getGroups().stream()
								.anyMatch( group -> "Dev".equalsIgnoreCase( group.getTitle() ) );

		if( !forDev ) return;

		// get user and url
		String submitterTitle = request.getLinks().get( "submitter" ).getTitle();

		String description = request.getDescription();
		String url = request.getAbsoluteUrl();

		String msg = String.format( ">>> :reviewboard: New review request from %s : %s\n", submitterTitle, description );
		msg += url;

		SlackMessagePoster api = new SlackMessagePoster( config.getApiToken(), config.getBotName(), config.getAvatarUrl() );
		// find channel to post
		for ( String channel : config.getChannelToUsers().keySet() )
		{
			if ( config.getChannelToUsers().get( channel ).contains( submitterTitle ) )
			{
				api.say( channel, msg );
				break;
			}
		}
	}
}
