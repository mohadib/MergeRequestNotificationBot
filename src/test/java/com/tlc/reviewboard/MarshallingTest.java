package com.tlc.reviewboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class MarshallingTest
{
	@Test
	public void testMarshalling() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		ReviewRequestWrapper wrapper = mapper.readValue( MarshallingTest.class.getResource( "/ReviewRequest.json" ), ReviewRequestWrapper.class );
		System.out.println("win?");
	}
}
