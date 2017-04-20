package com.tlc;

import org.openactive.gitlab.webhook.domain.GitlabEvent;

public interface EventHandler
{
   void handle( GitlabEvent event);
}
