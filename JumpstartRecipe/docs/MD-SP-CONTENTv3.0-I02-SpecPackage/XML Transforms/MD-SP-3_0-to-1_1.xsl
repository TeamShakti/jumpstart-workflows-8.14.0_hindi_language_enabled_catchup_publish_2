<xsl:stylesheet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:core="urn:cablelabs:md:xsd:core:3.0" xmlns:offer="urn:cablelabs:md:xsd:offer:3.0" xmlns:title="urn:cablelabs:md:xsd:title:3.0" xmlns:terms="urn:cablelabs:md:xsd:terms:3.0" xmlns:content="urn:cablelabs:md:xsd:content:3.0" exclude-result-prefixes="core offer title terms content xsi" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<!--
Translate from CL3 to CL1.1.  
The input CL3 package must follow the conventions used during conversion of CL1.1 to CL3.

Known issues:
-Order is NOT preserved
-Verb is dropped
-All AppData.App (except trial use fields) is lost, hardcoded to $APP
-AMS.Asset_Class and AppData "Type" not preserved, calculated based on element type
-Type added with asset_class value even if not originally present
-Title.Licensing_Window_Start and Licensing_Window_End, use DATETIME_FORMAT and will always have time
-trickModesRestricted with list values like"RW,Pause" are flattened
-Title Display_Run_Time truncated Run_Time value
-Durations are put in equivalent canonical form:  e.g. 00:24:00 -> 01:00:00
-->
	<xsl:output method="xml" indent="yes" doctype-system="ADI.DTD" encoding="ISO-8859-1"/>
	
	<xsl:variable name="Offer" select="//core:Asset[string(@xsi:type)='offer:OfferType'][1]"/>
	<xsl:variable name="ContentGroup" select="//core:Asset[string(@xsi:type)='offer:ContentGroupType'][@uriId=$Offer/offer:ContentGroupRef/@uriId]"/>
	<xsl:variable name="Title" select="//core:Asset[string(@xsi:type)='title:TitleType'][@uriId=$ContentGroup/offer:TitleRef/@uriId]"/>
	<xsl:variable name="Terms" select="//core:Asset[string(@xsi:type)='terms:TermsType'][@uriId=$Offer/offer:TermsRef/@uriId]"/>
	<xsl:variable name="Category" select="//core:Asset[string(@xsi:type)='offer:CategoryType'][@uriId=$Offer/offer:Presentation/offer:CategoryRef/@uriId]"/>
	<xsl:variable name="Movie" select="//core:Asset[string(@xsi:type)='content:MovieType'][@uriId=$ContentGroup/offer:MovieRef/@uriId][1]"/>
	<xsl:variable name="APP">
		<xsl:choose>
			<xsl:when test="$Offer/core:Product = 'SVOD'">SVOD</xsl:when>
			<xsl:otherwise>MOD</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="DATE_FORMAT">[Y,4]-[M,2]-[D,2]</xsl:variable>
	<xsl:variable name="DATETIME_FORMAT">[Y,4]-[M,2]-[D,2]T[H,2]:[m,2]:[s,2]</xsl:variable>
	<xsl:variable name="JUSTTIME_FORMAT">[H,2]:[m,2]:[s,2]</xsl:variable>
	
	<xsl:template match="/">
		<xsl:apply-templates select="$Offer"/>
	</xsl:template>

	<xsl:template match="core:Asset[string(@xsi:type)='offer:OfferType']">
		<ADI>
			<Metadata>
				<xsl:call-template name="AMS"/>
				<xsl:for-each select="offer:ProviderContentTier">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Provider_Content_Tier</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:element name="App_Data">
					<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
					<xsl:attribute name="Name">Metadata_Spec_Version</xsl:attribute>
					<xsl:attribute name="Value">
						<xsl:choose>
							<xsl:when test="offer:SourceMetadataSpecVersion"><xsl:value-of select="offer:SourceMetadataSpecVersion"/></xsl:when>
							<xsl:otherwise>CableLabsVOD1.1</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
				</xsl:element>
			</Metadata>
			<xsl:apply-templates select="$Title"/>
		</ADI>
	</xsl:template>
	
	<xsl:template match="core:Asset[string(@xsi:type)='title:TitleType']">
		<Asset>
			<Metadata>
				<xsl:call-template name="AMS" />
				<xsl:for-each select='title:LocalizableTitle/title:TitleSortName'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Title_Sort_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:SubscriberViewLimit">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Subscriber_View_Limit</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="concat(format-dateTime(@startDateTime,$DATE_FORMAT),',',format-dateTime(@endDateTime,$DATE_FORMAT),',',@maximumViews)"/></xsl:attribute>				
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:TitleBrief'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Title_Brief</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:TitleLong'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Title</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="core:AlternateId[@identifierSystem = 'EIDR']">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">EIDR</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="core:AlternateId[@identifierSystem = 'ISAN']">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">ISAN</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:EpisodeName'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Episode_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:EpisodeID'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Episode_ID</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:SummaryLong'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Summary_Long</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:SummaryMedium'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Summary_Medium</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:SummaryShort'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Summary_Short</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:Rating">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name"><xsl:if test="@ratingSystem[. = 'MSO']">MSO</xsl:if>Rating</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:Advisory">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Advisories</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:Audience">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Audience</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:IsClosedCaptioning">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Closed_Captioning</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Movie/content:Duration">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Run_Time</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="concat(format-number(hours-from-duration(.),'00'),':',format-number(minutes-from-duration(.),'00'),':',format-number(seconds-from-duration(.),'00'))"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:DisplayRunTime">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Display_Run_Time</xsl:attribute>
						<!-- Only show hh:mm -->
						<xsl:attribute name="Value"><xsl:value-of select="substring(.,1,5)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:Year">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Year</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:CountryOfOrigin">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Country_of_Origin</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:Actor'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Actors</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="person"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:LocalizableTitle/title:ActorDisplay">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Actors_Display</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:LocalizableTitle/title:WriterDisplay">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Writer_Display</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:Director'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Director</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="person"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:Producer'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Producers</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="person"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:LocalizableTitle/title:StudioDisplay">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='$Category'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Category</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select='offer:CategoryPath'/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:IsSeasonPremiere">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Season_Premiere</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:IsSeasonFinale">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Season_Finale</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:Genre[. != 'private:UNKNOWN']">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Genre</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:ShowType[. != 'private:UNKNOWN']">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Show_Type</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:Chapter'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Chapter</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="concat(@timeCode,',',@heading)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:BoxOffice">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Box_Office</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>			
				<!--TODO:  first one? -->
				<xsl:if test="$Movie/content:PropagationPriority">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Propagation_Priority</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="$Movie/content:PropagationPriority[1]"/></xsl:attribute>
					</xsl:element>
				</xsl:if>
				<!-- Retain default value if converted from V1.1 to V3.0 "00000" -->
				<xsl:for-each select="$Offer/offer:BillingId">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Billing_ID</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Get these values from the Title (current) element -->
				<xsl:if test='@startDateTime'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Licensing_Window_Start</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="format-dateTime(@startDateTime,$DATETIME_FORMAT)"/></xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:if test='@endDateTime'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Licensing_Window_End</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="format-dateTime(@endDateTime,$DATETIME_FORMAT)"/></xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:for-each select="$Terms/terms:BillingGracePeriod">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Preview_Period</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="60*minutes-from-duration(.) + seconds-from-duration(.)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:HomeVideoWindow">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Home_Video_Window</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="days-from-duration(.)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>	
				<!-- we can only grab DisplayAsNew/DisplayAsLastChance from the first and hope for the best-->		
				<xsl:for-each select="($Offer/offer:Presentation/offer:DisplayAsNew)[1]">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Display_As_New</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="days-from-duration(.)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="($Offer/offer:Presentation/offer:DisplayAsLastChance)[1]">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Display_As_Last_Chance</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="days-from-duration(.)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:UsagePeriod">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Maximum_Viewing_Length</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="concat(format-number(days-from-duration(.),'00'),':',format-number(hours-from-duration(.),'00'),':',format-number(minutes-from-duration(.),'00'))"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="core:ProviderQAContact">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Provider_QA_Contact</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:ContractName">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Contract_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- If this was defaulted to 0.00 on conversion to V3.0 the default value will be retained -->
				<xsl:for-each select="$Terms/terms:SuggestedPrice">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Suggested_Price</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:DistributorRoyaltyInfo/terms:RoyaltyPercent">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Distributor_Royalty_Percent</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:DistributorRoyaltyInfo/terms:RoyaltyMinimum">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Distributor_Royalty_Minimum</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:DistributorRoyaltyInfo/terms:RoyaltyFlatRate">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Distributor_Royalty_Flat_Rate</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:DistributorRoyaltyInfo/terms:OrganizationName">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Distributor_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:StudioRoyaltyInfo/terms:RoyaltyPercent">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio_Royalty_Percent</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:StudioRoyaltyInfo/terms:RoyaltyMinimum">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio_Royalty_Minimum</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:StudioRoyaltyInfo/terms:RoyaltyFlatRate">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio_Royalty_Flat_Rate</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:StudioRoyaltyInfo/terms:OrganizationName">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="$Terms/terms:StudioRoyaltyInfo/terms:OrganizationCode">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Studio_Code</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>			
				<xsl:for-each select="title:ProgrammerCallLetters">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Programmer_Call_Letters</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="title:LocalizableTitle/title:RecordingArtist">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Recording_Artist</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='title:LocalizableTitle/title:SongTitle'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Song_Title</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
			</Metadata>
			
			<xsl:apply-templates select="/core:ADI3/*[@uriId=$ContentGroup/*/@uriId or @uriId=//content:TrickRef/@uriId][starts-with(string(@xsi:type),'content:')]" mode="contentAsset"/>
		</Asset>
	</xsl:template>

	<xsl:template match="core:Asset" mode="contentAsset">
		<xsl:element name="Asset">
			<Metadata>
				<xsl:call-template name="AMS"/>
				<!-- Encrypted Movie -->
				<xsl:if test="content:EncryptionInfo">
					<xsl:for-each select="core:MasterSourceRef">
						<xsl:element name="App_Data">
							<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
							<xsl:attribute name="Name">Asset_Encrypted</xsl:attribute>
							<xsl:attribute name="Value"><xsl:value-of select="substring-after(substring-after(@uriId,'/'),'/')"/></xsl:attribute>
						</xsl:element>	
					</xsl:for-each>	
				</xsl:if>
				<xsl:for-each select="content:EncryptionInfo/content:VendorName">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Vendor_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:ReceiverType">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Receiver_Type</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:ReceiverVersion">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Receiver_Version</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:Encryption">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Type</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:EncryptionAlgorithm">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Algorithm</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:EncryptionDateTime">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Date</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="format-dateTime(.,$DATE_FORMAT)"/></xsl:attribute>
					</xsl:element>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Time</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="format-dateTime(.,$JUSTTIME_FORMAT)"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:EncryptionSystemInfo">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_System_Info</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:EncryptionInfo/content:EncryptionKeyBlock">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Key_Block</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Trick -->
				<xsl:for-each select="content:VendorName">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Vendor_Name</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:VendorProduct">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Vendor_Product</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:ForVersion">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">For_Version</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:TrickMode">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Trick_Mode</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Preview -->
				<xsl:if test="string(@xsi:type)='content:PreviewType'">
					<xsl:for-each select="content:Rating">
						<xsl:element name="App_Data">
							<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
							<xsl:attribute name="Name"><xsl:if test="@ratingSystem[. = 'MSO']">MSO</xsl:if>Rating</xsl:attribute>
							<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="content:Audience">
						<xsl:element name="App_Data">
							<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
							<xsl:attribute name="Name">Audience</xsl:attribute>
							<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each select="content:Duration">
						<xsl:element name="App_Data">
							<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
							<xsl:attribute name="Name">Run_Time</xsl:attribute>
							<xsl:attribute name="Value"><xsl:value-of select="concat(format-number(hours-from-duration(.),'00'),':',format-number(minutes-from-duration(.),'00'),':',format-number(seconds-from-duration(.),'00'))"/></xsl:attribute>
						</xsl:element>
					</xsl:for-each>
				</xsl:if>
				<!-- Movie -->
				<xsl:if test="string(@xsi:type)='content:MovieType' and not(content:EncryptionInfo)">
					<xsl:for-each select="$Title/title:IsEncryptionRequired">
						<xsl:element name="App_Data">
							<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
							<xsl:attribute name="Name">Encryption</xsl:attribute>
							<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
						</xsl:element>
					</xsl:for-each>
				</xsl:if>
				<!-- Movie, Preview, Barker -->
				<xsl:for-each select='content:AudioType'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Audio_Type</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:ScreenFormat">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Screen_Format</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Movie, Preview, Barker, Encrypted Movie -->
				<xsl:for-each select="content:Resolution">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Resolution</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:FrameRate">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Frame_Rate</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:Codec">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Codec</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Movie, Preview, Barker -->
				<xsl:for-each select='content:Language'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Languages</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='content:SubtitleLanguage'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Subtitle_Languages</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select='content:DubbedLanguage'>
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Dubbed_Languages</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Movie -->
				<xsl:for-each select="content:CopyControlInfo/content:IsCopyProtection">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Copy_Protection</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:CopyControlInfo/content:IsCopyProtectionVerbose">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Copy_Protection_Verbose</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:CopyControlInfo/content:AnalogProtectionSystem">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Analog_Protection_System</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:CopyControlInfo/content:EncryptionModeIndicator">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Encryption_Mode_Indicator</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:CopyControlInfo/content:ConstrainedImageTrigger">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Constrained_Image_Trigger</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:CopyControlInfo/content:CGMS_A ">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">CGMS_A </xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:IsResumeEnabled">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Viewing_Can_Be_Resumed</xsl:attribute>
							<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Still Image -->
				<xsl:if test="content:X_Resolution and content:Y_Resolution">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Image_Aspect_Ratio</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="concat(content:X_Resolution,'x',content:Y_Resolution)"/></xsl:attribute>
					</xsl:element>
				</xsl:if>
				<!-- Movie, Preview, Trick, Barker, Still Image -->
				<xsl:for-each select="content:BitRate">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Bit_Rate</xsl:attribute>
						<!-- Convert from V3.0 bps to V1.1 kbps -->
						<xsl:attribute name="Value"><xsl:value-of select="number(.) div 1000"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- All -->
				<xsl:for-each select="content:ContentFileSize">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Content_FileSize</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:ContentCheckSum">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Content_CheckSum</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<!-- Movie, Preview -->
				<xsl:if test="content:TrickModesRestricted/core:TrickModeExclusion">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">trickModesRestricted</xsl:attribute>
						<xsl:attribute name="Value">
							<xsl:for-each select="content:TrickModesRestricted/core:TrickModeExclusion"><xsl:if test="position() != 1">,</xsl:if><xsl:value-of select="substring-after(@type,'private:')"/></xsl:for-each>
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:for-each select="content:CopyControlInfo/content:RequiresOutputControl">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">Selectable_Output_Control</xsl:attribute>
						<xsl:attribute name="Value"><xsl:call-template name="boolean"/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:ThreeDMode">
					<xsl:element name="App_Data">
						<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
						<xsl:attribute name="Name">3D_Mode</xsl:attribute>
						<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
				<xsl:for-each select="content:TrickRef">
					<xsl:variable name="trickUriId" select="@uriId"/>
					<xsl:apply-templates select="//Asset[string(@xsi:type)='content:TrickType'][@uriId=$trickUriId]" mode="contentAsset" />
				</xsl:for-each>
			</Metadata>
			<xsl:for-each select="content:SourceUrl">
				<Content>
					<xsl:attribute name="Value"><xsl:value-of select="."/></xsl:attribute>
				</Content>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	<xsl:template name="AMS">
		<xsl:variable name="assetType" select="substring-before(lower-case(substring-after(string(@xsi:type),':')),'type')"/>
		<xsl:variable name="assetClass">
			<xsl:choose>
				<xsl:when test="$assetType='offer'">package</xsl:when>
				<xsl:when test="$assetType='trick'">trickfile</xsl:when>
				<xsl:when test="$assetType='boxcover'">box cover</xsl:when>
				<xsl:when test="$assetType='movie' and content:EncryptionInfo">encrypted</xsl:when>
				<xsl:otherwise><xsl:value-of select="$assetType"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:element name="AMS">	
			<xsl:attribute name="Provider"><xsl:value-of select="core:Provider"/></xsl:attribute>
			<xsl:attribute name="Product"><xsl:value-of select="core:Product"/></xsl:attribute>
			<xsl:attribute name="Asset_Name"><xsl:value-of select="core:AssetName"/></xsl:attribute>
			<xsl:attribute name="Version_Major"><xsl:value-of select="@providerVersionNum"/></xsl:attribute>
			<xsl:attribute name="Version_Minor"><xsl:value-of select="@internalVersionNum"/></xsl:attribute>
			<xsl:attribute name="Description"><xsl:value-of select="core:Description"/></xsl:attribute>
			<xsl:attribute name="Creation_Date"><xsl:value-of select="format-dateTime(@creationDateTime,$DATE_FORMAT)"/></xsl:attribute>
			<!-- Ok to assume this is always present? -->
			<xsl:for-each select="core:AlternateId[@identifierSystem = 'VOD1.1']">
				<xsl:attribute name="Provider_ID"><xsl:value-of select="substring-before(substring-after(.,'://'),'/')"/></xsl:attribute>
				<xsl:attribute name="Asset_ID"><xsl:value-of select="substring-after(substring-after(.,'://'),'/')"/></xsl:attribute>
			</xsl:for-each>
			<xsl:attribute name="Asset_Class"><xsl:value-of select="$assetClass"/></xsl:attribute>
			<!-- No Verb -->
		</xsl:element>
		<!-- The following are not part of AMS, but common across most Assets -->
		<xsl:if test="$assetClass != 'package' and $assetClass != 'encrypted' and $assetClass != 'trickfile'">
			<xsl:element name="App_Data">
				<xsl:attribute name="App"><xsl:value-of select="$APP"/></xsl:attribute>
				<xsl:attribute name="Name">Type</xsl:attribute>
				<xsl:attribute name="Value"><xsl:value-of select="$assetClass"/></xsl:attribute>
			</xsl:element>
		</xsl:if>
		<xsl:for-each select="core:Ext/App_Data">
			<App_Data>
				<xsl:attribute name="App"><xsl:value-of select="@App"/></xsl:attribute>
				<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
				<xsl:attribute name="Value"><xsl:value-of select="@Value"/></xsl:attribute>
			</App_Data>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="boolean">
		<xsl:choose>
			<xsl:when test="text()='true' or text()='1'">Y</xsl:when>
			<xsl:otherwise>N</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="person">
		<xsl:choose>
			<xsl:when test="@sortableName"><xsl:value-of select="@sortableName"/></xsl:when>
			<xsl:when test="@lastName"><xsl:value-of select="concat(@lastName,',',@firstName)"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="@firstName"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="*" mode="date">
		<xsl:value-of select="substring(., 10)"/>
	</xsl:template>
	<xsl:template match="*" mode="time">
		<xsl:value-of select="substring(substring-after(.,'T'), 6)"/>
	</xsl:template>
</xsl:stylesheet>
