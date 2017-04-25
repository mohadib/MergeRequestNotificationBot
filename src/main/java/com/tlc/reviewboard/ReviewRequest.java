package com.tlc.reviewboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude( JsonInclude.Include.NON_NULL )
public class ReviewRequest
{

	@JsonProperty( "commit_id" )
	private Object commitId;
	@JsonProperty( "last_updated" )
	private String lastUpdated;
	@JsonProperty( "target_people" )
	private List< Object > targetPeople = null;
	@JsonProperty( "depends_on" )
	private List< Object > dependsOn = null;
	@JsonProperty( "description_text_type" )
	private String descriptionTextType;
	@JsonProperty( "issue_resolved_count" )
	private long issueResolvedCount;
	@JsonProperty( "ship_it_count" )
	private long shipItCount;
	@JsonProperty( "close_description_text_type" )
	private String closeDescriptionTextType;
	@JsonProperty( "id" )
	private long id;
	@JsonProperty( "issue_dropped_count" )
	private long issueDroppedCount;
	@JsonProperty( "bugs_closed" )
	private List< Object > bugsClosed = null;
	@JsonProperty( "testing_done" )
	private String testingDone;
	@JsonProperty( "branch" )
	private String branch;
	@JsonProperty( "text_type" )
	private Object textType;
	@JsonProperty( "time_added" )
	private String timeAdded;
	@JsonProperty( "public" )
	private boolean _public;
	@JsonProperty( "status" )
	private String status;
	@JsonProperty( "close_description" )
	private Object closeDescription;
	@JsonProperty( "blocks" )
	private List< Object > blocks = null;
	@JsonProperty( "description" )
	private String description;
	@JsonProperty( "testing_done_text_type" )
	private String testingDoneTextType;
	@JsonProperty( "issue_open_count" )
	private long issueOpenCount;
	@JsonProperty( "approved" )
	private boolean approved;
	@JsonProperty( "url" )
	private String url;
	@JsonProperty( "absolute_url" )
	private String absoluteUrl;
	@JsonProperty( "summary" )
	private String summary;
	@JsonProperty( "changenum" )
	private Object changenum;
	@JsonProperty( "approval_failure" )
	private String approvalFailure;
	@JsonProperty("links")
	private Map< String, Link > links;


	@JsonIgnore
	private Map< String, Object > additionalProperties = new HashMap< String, Object >();

	public Map< String, Link > getLinks()
	{
		return links;
	}

	public void setLinks( Map< String, Link > links )
	{
		this.links = links;
	}

	public void setAdditionalProperties(
		Map< String, Object > additionalProperties )
	{
		this.additionalProperties = additionalProperties;
	}

	@JsonProperty( "target_groups" )
	private List<Group> groups;

	public List< Group > getGroups()
	{
		return groups;
	}

	public void setGroups( List< Group > groups )
	{
		this.groups = groups;
	}

	@JsonProperty( "commit_id" )
	public Object getCommitId()
	{
		return commitId;
	}

	@JsonProperty( "commit_id" )
	public void setCommitId( Object commitId )
	{
		this.commitId = commitId;
	}

	@JsonProperty( "last_updated" )
	public String getLastUpdated()
	{
		return lastUpdated;
	}

	@JsonProperty( "last_updated" )
	public void setLastUpdated( String lastUpdated )
	{
		this.lastUpdated = lastUpdated;
	}

	@JsonProperty( "target_people" )
	public List< Object > getTargetPeople()
	{
		return targetPeople;
	}

	@JsonProperty( "target_people" )
	public void setTargetPeople( List< Object > targetPeople )
	{
		this.targetPeople = targetPeople;
	}

	@JsonProperty( "depends_on" )
	public List< Object > getDependsOn()
	{
		return dependsOn;
	}

	@JsonProperty( "depends_on" )
	public void setDependsOn( List< Object > dependsOn )
	{
		this.dependsOn = dependsOn;
	}

	@JsonProperty( "description_text_type" )
	public String getDescriptionTextType()
	{
		return descriptionTextType;
	}

	@JsonProperty( "description_text_type" )
	public void setDescriptionTextType( String descriptionTextType )
	{
		this.descriptionTextType = descriptionTextType;
	}

	@JsonProperty( "issue_resolved_count" )
	public long getIssueResolvedCount()
	{
		return issueResolvedCount;
	}

	@JsonProperty( "issue_resolved_count" )
	public void setIssueResolvedCount( long issueResolvedCount )
	{
		this.issueResolvedCount = issueResolvedCount;
	}

	@JsonProperty( "ship_it_count" )
	public long getShipItCount()
	{
		return shipItCount;
	}

	@JsonProperty( "ship_it_count" )
	public void setShipItCount( long shipItCount )
	{
		this.shipItCount = shipItCount;
	}

	@JsonProperty( "close_description_text_type" )
	public String getCloseDescriptionTextType()
	{
		return closeDescriptionTextType;
	}

	@JsonProperty( "close_description_text_type" )
	public void setCloseDescriptionTextType( String closeDescriptionTextType )
	{
		this.closeDescriptionTextType = closeDescriptionTextType;
	}

	@JsonProperty( "id" )
	public long getId()
	{
		return id;
	}

	@JsonProperty( "id" )
	public void setId( long id )
	{
		this.id = id;
	}

	@JsonProperty( "issue_dropped_count" )
	public long getIssueDroppedCount()
	{
		return issueDroppedCount;
	}

	@JsonProperty( "issue_dropped_count" )
	public void setIssueDroppedCount( long issueDroppedCount )
	{
		this.issueDroppedCount = issueDroppedCount;
	}

	@JsonProperty( "bugs_closed" )
	public List< Object > getBugsClosed()
	{
		return bugsClosed;
	}

	@JsonProperty( "bugs_closed" )
	public void setBugsClosed( List< Object > bugsClosed )
	{
		this.bugsClosed = bugsClosed;
	}

	@JsonProperty( "testing_done" )
	public String getTestingDone()
	{
		return testingDone;
	}

	@JsonProperty( "testing_done" )
	public void setTestingDone( String testingDone )
	{
		this.testingDone = testingDone;
	}

	@JsonProperty( "branch" )
	public String getBranch()
	{
		return branch;
	}

	@JsonProperty( "branch" )
	public void setBranch( String branch )
	{
		this.branch = branch;
	}

	@JsonProperty( "text_type" )
	public Object getTextType()
	{
		return textType;
	}

	@JsonProperty( "text_type" )
	public void setTextType( Object textType )
	{
		this.textType = textType;
	}

	@JsonProperty( "time_added" )
	public String getTimeAdded()
	{
		return timeAdded;
	}

	@JsonProperty( "time_added" )
	public void setTimeAdded( String timeAdded )
	{
		this.timeAdded = timeAdded;
	}

	@JsonProperty( "public" )
	public boolean isPublic()
	{
		return _public;
	}

	@JsonProperty( "public" )
	public void setPublic( boolean _public )
	{
		this._public = _public;
	}

	@JsonProperty( "status" )
	public String getStatus()
	{
		return status;
	}

	@JsonProperty( "status" )
	public void setStatus( String status )
	{
		this.status = status;
	}

	@JsonProperty( "close_description" )
	public Object getCloseDescription()
	{
		return closeDescription;
	}

	@JsonProperty( "close_description" )
	public void setCloseDescription( Object closeDescription )
	{
		this.closeDescription = closeDescription;
	}

	@JsonProperty( "blocks" )
	public List< Object > getBlocks()
	{
		return blocks;
	}

	@JsonProperty( "blocks" )
	public void setBlocks( List< Object > blocks )
	{
		this.blocks = blocks;
	}

	@JsonProperty( "description" )
	public String getDescription()
	{
		return description;
	}

	@JsonProperty( "description" )
	public void setDescription( String description )
	{
		this.description = description;
	}

	@JsonProperty( "testing_done_text_type" )
	public String getTestingDoneTextType()
	{
		return testingDoneTextType;
	}

	@JsonProperty( "testing_done_text_type" )
	public void setTestingDoneTextType( String testingDoneTextType )
	{
		this.testingDoneTextType = testingDoneTextType;
	}

	@JsonProperty( "issue_open_count" )
	public long getIssueOpenCount()
	{
		return issueOpenCount;
	}

	@JsonProperty( "issue_open_count" )
	public void setIssueOpenCount( long issueOpenCount )
	{
		this.issueOpenCount = issueOpenCount;
	}

	@JsonProperty( "approved" )
	public boolean isApproved()
	{
		return approved;
	}

	@JsonProperty( "approved" )
	public void setApproved( boolean approved )
	{
		this.approved = approved;
	}

	@JsonProperty( "url" )
	public String getUrl()
	{
		return url;
	}

	@JsonProperty( "url" )
	public void setUrl( String url )
	{
		this.url = url;
	}

	@JsonProperty( "absolute_url" )
	public String getAbsoluteUrl()
	{
		return absoluteUrl;
	}

	@JsonProperty( "absolute_url" )
	public void setAbsoluteUrl( String absoluteUrl )
	{
		this.absoluteUrl = absoluteUrl;
	}

	@JsonProperty( "summary" )
	public String getSummary()
	{
		return summary;
	}

	@JsonProperty( "summary" )
	public void setSummary( String summary )
	{
		this.summary = summary;
	}

	@JsonProperty( "changenum" )
	public Object getChangenum()
	{
		return changenum;
	}

	@JsonProperty( "changenum" )
	public void setChangenum( Object changenum )
	{
		this.changenum = changenum;
	}

	@JsonProperty( "approval_failure" )
	public String getApprovalFailure()
	{
		return approvalFailure;
	}

	@JsonProperty( "approval_failure" )
	public void setApprovalFailure( String approvalFailure )
	{
		this.approvalFailure = approvalFailure;
	}

	@JsonAnyGetter
	public Map< String, Object > getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty( String name, Object value )
	{
		this.additionalProperties.put( name, value );
	}

}
