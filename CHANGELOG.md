# v8.14

This release is only compatible with Workflow Engine 8.14.0.343 or newer.

## Purge Metadata from the endpoints - User Story [479123](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=479123)
 
All entity able to be published to the metadata enpoints (Reference Endpoints for metadata are Elasticsearch and ActiveMQ) now support purging the published metadata (see purge destinations changes in the corresponding purge wrappers). 
* Elasticsearch the corresponding document just gets deleted from the index.
* ActiveMQ - the publish message with the property `Action=purge` is sent to the publish queue.

## Cancel Human Tasks in the Purge flow - SCR [480554](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=480554)

The `CancelTask` task handler's input properties were changed (in terms of WS-HumanTask and jBPM the tasks are acually skipped and as the result task status changes `Obsolete`):
* property `Context` is now optional (no default value); when not set or empty - task handler will attempt to skip the human task independently of its status (only entity type/id and user Id should match)  
* new property `Force` (optional, default is `false`); when set to `true` the task handler will attempt to exit the task when skipping is not possible due to permission errors 

## Inconsistency in publish renditions between Live and VOD content while Publish to Elasticsearch/ActiveMQ - SCR [478343](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=478343)

The renditions map for the live content (entity types `channel` and `event`) in the JSON Reference Publish documents were changed to match the format of the VOD content (`program`). 

## Purge EPG data from the endpoints - User Story [470921](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=470921)
The EPG Purge functionality (implemented in the workflow `com.irdeto.jumpstart.workflow.purge.EpgPurge`) was extended to perform purging from the endpoints. 
(In the reference implementation the _publishable_ `ChannelDay` entities are currently supported by Elasticsearch and ActiveMQ endpoints).

The Elasticsearch and ActiveMQ Publish workflows (`com.irdeto.jumpstart.workflow.publish.activemq.ActiveMQPublish`, `com.irdeto.jumpstart.workflow.publish.elasticsearch.ElasticsearchPublish`) were adjusted to be able to perform both _publishing_ and _purging_. 
The behaviour is controlled via the (_new_) `wrapper` variable (previously was `publishWrapper`) which is assigned from the parent "Publish to Endpoints"/"Purge from Endpoints" workflows and accepts either a PublishWrapper or a PurgeWrapper (declaration is made generic `com.irdeto.jumpstart.factory.WrapperWithDestinations`).
 
Publish/Purge Endpoint-specific notes:  

* When purging from Elasticsearch the corresponding document is just directly deleted from the index.

* A new message property/header `Action` was added to the ActiveMQ publish/purge message which specifies whether the document is being published (created or updated) or purged. The values are `create`, `update` and `purge` correspondingly. 
When purging via ActiveMQ the message sent to the queue is the same as the publish one with the exception of the `Action` message property/header. 

## Add Method Type to Publish Messages - User Story [478349](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/_workitems#_a=edit&id=478349)
 
When communicating to an external system when publishing or purging Entities, it is beneficial for the external system to know what the action is. So there is a new Action type property which specifies the operation. During publishing of an entity you can get action set to `create` for newly created items, and `update` for updated contents. Likewise for purging the action will be set to `purge`. This will enable the external system to handle the content appropriately.
* ActiveMQ - the publish message with the property `Action=purge` is sent to the publish queue for purge, and `Action=create` or `Action=update` is sent to the publish queue for publishing.

# v8.13

## This version is NOT compatible with **Java 7**. Refer to your operation system documentation for Java upgrade instructions

## Upgrade of the jsonschema2pojo Build Plugin - User Story [389365](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=389365)


### Important Notes!

- The latest official version is now used instead (0.4.23) of Irdeto's custom version –
the generated classes don't produce any compilation errors and irrelevant classes

- You have to add the target package name to the MediaManager's `.ini`
    with the `jumpstartWorkflows.entityTypes.javaTypePackage` property files
    to make sure the generated classes are put to the correct location
    e.g.
    ```
    jumpstartWorkflows.entityTypes.javaTypePackage=com.irdeto.jumpstart.domain
    ```

- Make sure to update/merge the following helper classes:
    - `com.irdeto.jumpstart.workflow.LocaleHelper` (see changes in `getValueForLocale()` and `setValueForLocale()`)
    - `com.irdeto.jumpstart.workflow.WorkflowHelper` (see changes in `getLinkCount()`)

- The classes in package `com.irdeto.jumpstart.domain.lang` were replaced with corresponding HashMap's

- The JSON Schema changed and only Media Manager of version 8.13 or higher
generates the correct and valid shcema

### Details on the domain's JSON schema changes

- The `properties` section was renamed to `definitions`.
- Arrays/Sets should be written like this
  ```
  "arrayToBeUsed": {
        "type": "array",
        "items": {
          "javaType": "com.example.Item"
         }
  }
  ```
- Extending classes should be done like in this sample:
  ```
  "extends": {
          "javaType": "com.example.ParentClass"
   }
  ```
- The newly created `properties` section contains only references to previously defined classes
- Each definition must have corresponding property:
  ```
  "foo": {
        "$ref": "#/definitions/foo"
  }
  ```

## Performant EPG Ingest - User Stories [463009](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/_workitems#_a=edit&id=463009), [463012](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/_workitems#_a=edit&id=463012), [463016](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/_workitems#_a=edit&id=463016)

EPG-related entity types structure changed - Schedule Slot entities (`scheduleSlot` entity type)
are now grouped under a Daily Schedule Slots entities (`channelDay` entity type) with an embedded relationship.
The relationship chain is now as follows:

`channel` <-- `channelDay` <-- `scheduleSlot` (embedded)

Genre and Rating relationships were removed from the `scheduleSlot` entity type.
Corresponding data is now represented as (localized) text field on the Schedule Slot.

A set of the lightweight workflows was created for the EPG data processing (XMLTV format as a reference implementation):
* ingest - `com.irdeto.jumpstart.workflow.ingest.EpgIngest`
* publish - `com.irdeto.jumpstart.workflow.publish.EpgPublishToEndpoints`
* purge - `com.irdeto.jumpstart.workflow.purge.EpgPurge` (see below)

Ingest handles data in bulk creating/updating the Schedules Slots as part of Daily Schedule Slots entities (`channelDay`).
So is publishing - the whole "channel - day" schedule slots set is published at once.
The only case when the Schedule Slot is eligible for the individual publishing
is when catch up or black out booking has to be made.
Any EPG data (channel - day's / schedule slots) with the date set to the past date are ignored on ingest/publish stages.
(In the reference XMLTV implementation the catch up and black out flags are to be set as `/tv/programme[@catchup]` and `/tv/programme[@blackout]`, accepted values are `true` and `false` (default))

The default Media Manager entity change notification workflow `com.irdeto.jumpstart.workflow.MMEntry`
was adjusted to support EPG lifecycle (separate path for processing `scheduleSlot` and `channelDay`);

The support of the reference EPG format (XMLTV) was removed from the original Data Ingest workflow.

## Purge Schedule Slots - User Story [463011]

Purging schedule slots is done by removing corresponding ChannelDays. You can initiate this procedure by triggering corresponding (EPGPurge) workflow.
This workflow perform API calls to the MediaManager.
The EPRPurge workflow has the following parameters:

* `purgeDays`  used to determine the last day to purge (purgeDays before current day)
* `hardDelete` (default true)  whether "HardDelete" should be performed.

# v5.1.1

## Catchup Blackout

Recipe has been udpated to include catchup and blackout flags on the `scheduleSlot` entity type.
Sample CatchUp publish workflow has been added (BlackOut functionality can be implemented accordingly).
The publish endpoints have been added to the corresponding PublishWrapper.
More info available in [wiki](https://irdeto.atlassian.net/wiki/display/MM/2.+Configuring+a+Publish+Workflow#id-2.ConfiguringaPublishWorkflow-PublishtoCatchUpService).

## Metadata Reference Publish - User stories [449832](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#searchText=%5Bwfs%5D&_a=edit&id=449832), [452806](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#searchText=%5Bwfs%5D&_a=edit&id=452806)

Support for the new endpoint options has been added for publishing metadata.
The corresponding workflows are `com.irdeto.jumpstart.workflow.publish.activemq.ActiveMQPublish` and `com.irdeto.jumpstart.workflow.publish.elasticsearch.ElasticsearchPublish`

For more details refer to wiki – [[1]](https://irdeto.atlassian.net/wiki/display/MM/2.+Configuring+a+Publish+Workflow#id-2.ConfiguringaPublishWorkflow-PublishtoActiveMQ) and [[2]](https://irdeto.atlassian.net/wiki/display/MM/3.+Reference+Elasticsearch+Publish+Format)


# v5.1.0

## Bypass Human Tasks - User Story [449557](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=449557)

Added the `jumpstart.humantask.bypass` property at `jumpstart.config.properties` file.

Property change is effected on "QA.bpmn" workflow.

With this property administrator can easily set `true` or `false` to trigger of human tasks or not to trigger.

By default the property will be set to `false` i.e. reference workflows supports with triggering of QA HT for the required entities.

On setting the `jumpstart.humantask.bypass=true`, then QA HT will be bypassed and at the workflows level automatically the status is set to 'approved' and at Media Manager UI no HT tasks will be trigegred



## Bulk Update/Create in DataIngest workflow - User Story [449834](https://tfs.irdeto.com/tfs/DefaultCollection/Irdeto%20Scrum%20Projects/HDC%20Sparkles/_workItems#_a=edit&id=449834)

The process `com.irdeto.jumpstart.workflow.ingest.DataIngest` now features the Media Manager bulk operations (async ActiveMQ-based create and update).
