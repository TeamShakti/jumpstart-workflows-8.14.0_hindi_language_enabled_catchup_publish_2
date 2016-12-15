<xsl:stylesheet xmlns="urn:cablelabs:md:xsd:core:3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:core="urn:cablelabs:md:xsd:core:3.0" xmlns:offer="urn:cablelabs:md:xsd:offer:3.0" xmlns:title="urn:cablelabs:md:xsd:title:3.0" xmlns:terms="urn:cablelabs:md:xsd:terms:3.0" xmlns:content="urn:cablelabs:md:xsd:content:3.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0">
	<!-- Translate from CL1.1 (ADI C01) to CL3. -->
	<xsl:output method="xml" indent="yes"/>
	<xsl:variable name="Title" select="/ADI/Asset/Metadata/AMS[@Asset_Class='title'][1]"/>
	<xsl:variable name="TermsUriId" select="concat($Title/@Provider_ID, '/Terms/', $Title/@Asset_ID )"/>
	<xsl:variable name="TitleUriId" select="concat($Title/@Provider_ID, '/Title/', $Title/@Asset_ID )"/>
	<xsl:variable name="ContentGroupUriId" select="concat($Title/@Provider_ID, '/ContentGroup/', $Title/@Asset_ID )"/>

	<xsl:template match="/ADI">
		<ADI3 xsi:schemaLocation="urn:cablelabs:md:xsd:core:3.0 MD-SP-CORE-I02.xsd urn:cablelabs:md:xsd:offer:3.0 MD-SP-OFFER-I02.xsd urn:cablelabs:md:xsd:title:3.0 MD-SP-TITLE-I02.xsd urn:cablelabs:md:xsd:content:3.0 MD-SP-CONTENT-I02.xsd urn:cablelabs:md:xsd:terms:3.0 MD-SP-TERMS-I02.xsd">
			<xsl:if test="not(Metadata/AMS/@Verb) or Metadata/AMS[@Verb != 'DELETE']">
				<xsl:call-template name="Offer"/>
			</xsl:if>
			<xsl:apply-templates select="//Asset[not(Metadata/AMS/@Verb) or Metadata/AMS/@Verb != 'DELETE']"/>
		</ADI3>
	</xsl:template>

	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='title']">
		<!-- Every ADI1.1 title maps to a Title, ContentGroup, Terms and one or more Categories -->
		<xsl:call-template name="Title"/>
		<xsl:call-template name="ContentGroup"/>
		<xsl:call-template name="Terms"/>
		<xsl:call-template name="Category"/>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='box cover']">
		<core:Asset xsi:type="content:BoxCoverType">
			<xsl:call-template name="StillImageAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='poster']">
		<core:Asset xsi:type="content:PosterType">
			<xsl:call-template name="StillImageAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='movie']">
		<core:Asset xsi:type="content:MovieType">
			<xsl:call-template name="AudioVideoAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='encrypted']">
		<core:Asset xsi:type="content:MovieType">
			<xsl:call-template name="AudioVideoAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='preview']">
		<core:Asset xsi:type="content:PreviewType">
			<xsl:call-template name="AudioVideoAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='trickfile']">
		<core:Asset xsi:type="content:TrickType">
			<xsl:call-template name="ContentAssetType"><xsl:with-param name="masterSource" select="//Asset/Metadata/AMS[@Asset_Class='movie']"/></xsl:call-template>
			<xsl:for-each select="Metadata/App_Data[@Name='Bit_Rate']">
				<content:BitRate><xsl:value-of select="number(@Value) * 1000"/></content:BitRate>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Vendor_Name']">
				<content:VendorName><xsl:value-of select="@Value"/></content:VendorName>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Vendor_Product']">
				<content:VendorProduct><xsl:value-of select="@Value"/></content:VendorProduct>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='For_Version']">
				<content:ForVersion><xsl:value-of select="@Value"/></content:ForVersion>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Trick_Mode']">
				<content:TrickMode><xsl:value-of select="@Value"/></content:TrickMode>
			</xsl:for-each>
		</core:Asset>
	</xsl:template>

	<xsl:template match="Asset[Metadata/AMS/@Asset_Class='barker']">
		<core:Asset xsi:type="content:BarkerType">
			<xsl:call-template name="AudioVideoAssetType"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template name="Offer">
		<core:Asset xsi:type="offer:OfferType">
			<xsl:attribute name="uriId"><xsl:value-of select="concat(Metadata/AMS/@Provider_ID, '/Offer/', Metadata/AMS/@Asset_ID)"/></xsl:attribute>
			<xsl:call-template name="AMS_1"/>
			<xsl:call-template name="AMS_2"/>
			<xsl:for-each select="//App_Data[@Name='Category'] ">
				<offer:Presentation>
					<offer:CategoryRef>
						<xsl:attribute name="uriId"><xsl:value-of select="concat($Title/@Provider_ID, '/Category/',@Value )"/></xsl:attribute>
					</offer:CategoryRef>
					<xsl:for-each select="//App_Data[@Name='Display_As_New']">
						<offer:DisplayAsNew><xsl:value-of select="concat('P',@Value,'D')"/></offer:DisplayAsNew>
					</xsl:for-each>
					<xsl:for-each select="//App_Data[@Name='Display_As_Last_Chance']">
						<offer:DisplayAsLastChance><xsl:value-of select="concat('P',@Value,'D')"/></offer:DisplayAsLastChance>
					</xsl:for-each>
				</offer:Presentation>
			</xsl:for-each>
			<offer:PromotionalContentGroupRef uriId="{$ContentGroupUriId}"/>
			<xsl:for-each select="Metadata/App_Data[@Name='Provider_Content_Tier'] ">
				<offer:ProviderContentTier><xsl:value-of select="@Value"/></offer:ProviderContentTier>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Metadata_Spec_Version'] ">
				<offer:SourceMetadataSpecVersion deprecated="true"><xsl:value-of select="@Value"/></offer:SourceMetadataSpecVersion>
			</xsl:for-each>
			<offer:BillingId>
				<xsl:choose>
					<xsl:when test="//App_Data[@Name='Billing_ID']">
						<xsl:value-of select="//App_Data[@Name='Billing_ID']/@Value"/>
					</xsl:when>
					<!--TODO  what is default value? -->
					<xsl:otherwise>00000</xsl:otherwise>
				</xsl:choose>
			</offer:BillingId>
			<offer:TermsRef uriId="{$TermsUriId}"/>
			<offer:ContentGroupRef uriId="{$ContentGroupUriId}"/>
		</core:Asset>
	</xsl:template>
	
	<xsl:template name="Title">
		<core:Asset xsi:type="title:TitleType">
			<xsl:attribute name="uriId"><xsl:value-of select="$TitleUriId"/></xsl:attribute>
			<xsl:call-template name="AMS_1"/>
			<xsl:call-template name="AMS_2"/>
			<title:LocalizableTitle>
				<xsl:for-each select="Metadata/App_Data[@Name='Title_Sort_Name']">
					<title:TitleSortName><xsl:value-of select="@Value"/></title:TitleSortName>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Title_Brief']">
					<title:TitleBrief><xsl:value-of select="@Value"/></title:TitleBrief>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Title']">
					<title:TitleMedium>
						<xsl:choose>
							<xsl:when test="string-length(@Value) &lt;= 35"><xsl:value-of select="@Value"/></xsl:when>
							<xsl:otherwise><xsl:value-of select="../App_Data[@Name='Title_Brief']/@Value"/></xsl:otherwise>
						</xsl:choose>
					</title:TitleMedium>
					<title:TitleLong><xsl:value-of select="@Value"/></title:TitleLong>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Summary_Short']">
					<title:SummaryShort><xsl:value-of select="@Value"/></title:SummaryShort>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Summary_Medium']">
					<title:SummaryMedium><xsl:value-of select="@Value"/></title:SummaryMedium>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Summary_Long']">
					<title:SummaryLong><xsl:value-of select="@Value"/></title:SummaryLong>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Actors_Display']">
					<title:ActorDisplay><xsl:value-of select="@Value"/></title:ActorDisplay>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Actors']">
					<title:Actor><xsl:call-template name="person"/></title:Actor>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Writer_Display']">
					<title:WriterDisplay><xsl:value-of select="@Value"/></title:WriterDisplay>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Director_Display']">
					<title:DirectorDisplay><xsl:value-of select="@Value"/></title:DirectorDisplay>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Director']">
					<title:Director><xsl:call-template name="person"/></title:Director>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Producer_Display']">
					<title:ProducerDisplay><xsl:value-of select="@Value"/></title:ProducerDisplay>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Producers']">
					<title:Producer><xsl:call-template name="person"/></title:Producer>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Studio']">
					<title:StudioDisplay><xsl:value-of select="@Value"/></title:StudioDisplay>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Recording_Artist']">
					<title:RecordingArtist><xsl:value-of select="@Value"/></title:RecordingArtist>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Song_Title']">
					<title:SongTitle><xsl:value-of select="@Value"/></title:SongTitle>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Episode_Name']">
					<title:EpisodeName><xsl:value-of select="@Value"/></title:EpisodeName>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Episode_ID']">
					<title:EpisodeID><xsl:value-of select="@Value"/></title:EpisodeID>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Chapter']">
					<title:Chapter>
						<xsl:attribute name="heading"><xsl:value-of select="substring-after(@Value, ',')"/></xsl:attribute>
						<xsl:attribute name="timeCode"><xsl:value-of select="substring-before(@Value, ',')"/></xsl:attribute>
					</title:Chapter>
				</xsl:for-each>
			</title:LocalizableTitle>
			<xsl:for-each select="Metadata/App_Data[@Name='Rating']">
				<title:Rating><xsl:call-template name="rating"/></title:Rating>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='MSORating']">
				<title:Rating ratingSystem="MSO"><xsl:value-of select="@Value"/></title:Rating>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Audience']">
				<title:Audience><xsl:value-of select="@Value"/></title:Audience>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Advisories']">
				<title:Advisory><xsl:value-of select="@Value"/></title:Advisory>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Closed_Captioning']">
				<title:IsClosedCaptioning><xsl:call-template name="boolean"/></title:IsClosedCaptioning>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Display_Run_Time']">
				<title:DisplayRunTime><xsl:value-of select="@Value"/></title:DisplayRunTime>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Year']">
				<title:Year><xsl:value-of select="@Value"/></title:Year>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Country_of_Origin']">
				<title:CountryOfOrigin>
					<xsl:choose>
						<xsl:when test="@Value = 'United States'">US</xsl:when>
						<xsl:otherwise><xsl:value-of select="@Value"/></xsl:otherwise>
					</xsl:choose>
				</title:CountryOfOrigin>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Genre']">
				<title:Genre><xsl:value-of select="@Value"/></title:Genre>
			</xsl:for-each>
			<xsl:if test="count(Metadata/App_Data[@Name='Genre'])=0">
				<title:Genre>private:UNKNOWN</title:Genre>
			</xsl:if>
			<title:ShowType>
				<xsl:choose>
					<xsl:when test="Metadata/App_Data[@Name='Show_Type']">
						<xsl:value-of select="Metadata/App_Data[@Name='Show_Type']/@Value"/>
					</xsl:when>
					<xsl:otherwise>private:UNKNOWN</xsl:otherwise>
				</xsl:choose>
			</title:ShowType>
			<xsl:for-each select="Metadata/App_Data[@Name='Season_Premiere']">
				<title:IsSeasonPremiere><xsl:call-template name="boolean"/></title:IsSeasonPremiere>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Season_Finale']">
				<title:IsSeasonFinale><xsl:call-template name="boolean"/></title:IsSeasonFinale>
			</xsl:for-each>
			<!-- TODO:  a bit weird as this is per movie in ADI1.1, should be deprecated? we are lazy and just find it any Encryption="Y" -->
			<xsl:for-each select="//App_Data[@Name='Encryption']">
				<title:IsEncryptionRequired><xsl:call-template name="boolean"/></title:IsEncryptionRequired>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Box_Office']">
				<title:BoxOffice><xsl:value-of select="@Value"/></title:BoxOffice>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Programmer_Call_Letters']">
				<title:ProgrammerCallLetters><xsl:value-of select="@Value"/></title:ProgrammerCallLetters>
			</xsl:for-each>
		</core:Asset>
	</xsl:template>

	<xsl:template name="ContentGroup">
		<core:Asset xsi:type="offer:ContentGroupType">
			<xsl:attribute name="uriId"><xsl:value-of select="$ContentGroupUriId"/></xsl:attribute>
			<xsl:call-template name="AMS_1"/>
			<offer:TitleRef>
				<xsl:attribute name="uriId"><xsl:value-of select="$TitleUriId"/></xsl:attribute>
			</offer:TitleRef>
			<xsl:for-each select="Asset/Metadata/AMS">
				<xsl:variable name="uriId">
					<xsl:value-of select="concat( @Provider_ID, '/Asset/', @Asset_ID)"/>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="@Asset_Class='movie' or @Asset_Class='encrypted'">
						<offer:MovieRef uriId="{$uriId}"/>
					</xsl:when>
					<xsl:when test="@Asset_Class='barker'">
						<offer:BarkerRef uriId="{$uriId}"/>
					</xsl:when>
					<xsl:when test="@Asset_Class='preview'">
						<offer:PreviewRef uriId="{$uriId}"/>
					</xsl:when>
					<xsl:when test="@Asset_Class='box cover'">
						<offer:BoxCoverRef uriId="{$uriId}"/>
					</xsl:when>
					<xsl:when test="@Asset_Class='poster'">
						<offer:PosterRef uriId="{$uriId}"/>
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
		</core:Asset>
	</xsl:template>
	
	<xsl:template name="Terms">
		<core:Asset xsi:type="terms:TermsType">
			<xsl:attribute name="uriId"><xsl:value-of select="$TermsUriId"/></xsl:attribute>
			<xsl:call-template name="AMS_1"/>
			<xsl:for-each select="Metadata/App_Data[@Name='Contract_Name']">
				<terms:ContractName><xsl:value-of select="@Value"/></terms:ContractName>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Preview_Period']">
				<terms:BillingGracePeriod><xsl:value-of select="concat('PT',@Value,'S')"/></terms:BillingGracePeriod>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Maximum_Viewing_Length']">
				<xsl:variable name="days" select="concat('P',substring-before(@Value, ':'),'DT')"/>
				<xsl:variable name="remaining" select="substring-after(@Value, ':')"/>
				<xsl:variable name="hours" select="concat(substring-before($remaining, ':'),'H')"/>
				<xsl:variable name="minutes" select="concat(substring-after($remaining, ':'),'M')"/>
				<terms:UsagePeriod><xsl:value-of select="concat($days,$hours,$minutes)"/></terms:UsagePeriod>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Home_Video_Window']">
				<terms:HomeVideoWindow><xsl:value-of select="concat('P',@Value,'D')"/></terms:HomeVideoWindow>
			</xsl:for-each>
			<xsl:for-each select="//App_Data[@Name='Subscriber_View_Limit']">
				<xsl:variable name="start" select="concat(normalize-space(substring-before(@Value, ',')),'T00:00:00Z')"/>
				<xsl:variable name="remaining" select="substring-after(@Value, ',')"/>
				<xsl:variable name="end" select="concat(normalize-space(substring-before($remaining, ',')),'T23:59:59Z')"/>
				<xsl:variable name="maxviews" select="normalize-space(substring-after($remaining, ','))"/>
				<terms:SubscriberViewLimit>
					<xsl:attribute name="startDateTime"><xsl:value-of select="$start"/></xsl:attribute>
					<xsl:attribute name="endDateTime"><xsl:value-of select="$end"/></xsl:attribute>
					<xsl:attribute name="maximumViews"><xsl:value-of select="$maxviews"/></xsl:attribute>
				</terms:SubscriberViewLimit>
			</xsl:for-each>
			<terms:SuggestedPrice>
				<xsl:choose>
					<xsl:when test="Metadata/App_Data[@Name='Suggested_Price']">
						<xsl:value-of select="Metadata/App_Data[@Name='Suggested_Price']/@Value"/>
					</xsl:when>
					<!-- TODO:  no price, must be free but this introduces default Suggested_Price after round trip -->
					<xsl:otherwise>0.00</xsl:otherwise>
				</xsl:choose>
			</terms:SuggestedPrice>
			<xsl:if test="Metadata/App_Data[starts-with(@Name,'Distributor_')]">
				<terms:DistributorRoyaltyInfo>
					<xsl:for-each select="Metadata/App_Data[@Name='Distributor_Name']">
						<terms:OrganizationName><xsl:value-of select="@Value"/></terms:OrganizationName>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Distributor_Code']">
						<terms:OrganizationCode><xsl:value-of select="@Value"/></terms:OrganizationCode>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Distributor_Royalty_Percent']">
						<terms:RoyaltyPercent><xsl:value-of select="@Value"/></terms:RoyaltyPercent>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Distributor_Royalty_Minimum']">
						<terms:RoyaltyMinimum><xsl:value-of select="@Value"/></terms:RoyaltyMinimum>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Distributor_Royalty_Flat_Rate']">
						<terms:RoyaltyFlatRate><xsl:value-of select="@Value"/></terms:RoyaltyFlatRate>
					</xsl:for-each>
				</terms:DistributorRoyaltyInfo>
			</xsl:if>
			<xsl:if test="Metadata/App_Data[starts-with(@Name,'Studio_')]">
				<terms:StudioRoyaltyInfo>
					<xsl:for-each select="Metadata/App_Data[@Name='Studio_Name']">
						<terms:OrganizationName><xsl:value-of select="@Value"/></terms:OrganizationName>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Studio_Code']">
						<terms:OrganizationCode><xsl:value-of select="@Value"/></terms:OrganizationCode>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Studio_Royalty_Percent']">
						<terms:RoyaltyPercent><xsl:value-of select="@Value"/></terms:RoyaltyPercent>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Studio_Royalty_Minimum']">
						<terms:RoyaltyMinimum><xsl:value-of select="@Value"/></terms:RoyaltyMinimum>
					</xsl:for-each>
					<xsl:for-each select="Metadata/App_Data[@Name='Studio_Royalty_Flat_Rate']">
						<terms:RoyaltyFlatRate><xsl:value-of select="@Value"/></terms:RoyaltyFlatRate>
					</xsl:for-each>
				</terms:StudioRoyaltyInfo>
			</xsl:if>
			<!-- TODO: Does TrickModesRestricted and IsResumeEnabled belong here too? -->
		</core:Asset>
	</xsl:template>

	<xsl:template name="Category">
		<xsl:for-each select="Metadata/App_Data[@Name='Category']">
			<core:Asset xsi:type="offer:CategoryType">
				<xsl:attribute name="uriId"><xsl:value-of select="concat(../AMS/@Provider_ID, '/Category/',@Value )"/></xsl:attribute>
				<xsl:for-each select="../..">
					<xsl:call-template name="AMS_1"/>
				</xsl:for-each>
				<offer:CategoryPath><xsl:value-of select="@Value"/></offer:CategoryPath>
			</core:Asset>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="ContentAssetType">
		<xsl:param name="masterSource"/>
		<xsl:attribute name="uriId"><xsl:value-of select="concat( Metadata/AMS/@Provider_ID, '/Asset/', Metadata/AMS/@Asset_ID )"/></xsl:attribute>
		<xsl:call-template name="AMS_1"/>
		<xsl:call-template name="AMS_2"/>
		<xsl:if test="$masterSource">
			<core:MasterSourceRef><xsl:attribute name="uriId"><xsl:value-of select="concat($masterSource/@Provider_ID,'/Asset/',$masterSource/@Asset_ID)"/></xsl:attribute></core:MasterSourceRef>
		</xsl:if>
		<xsl:if test="Content/@Value">
			<content:SourceUrl><xsl:value-of select="Content/@Value"/></content:SourceUrl>
		</xsl:if>
		<xsl:for-each select="Metadata/App_Data[@Name='Content_FileSize']">
			<content:ContentFileSize><xsl:value-of select="@Value"/></content:ContentFileSize>
		</xsl:for-each>
		<xsl:for-each select="Metadata/App_Data[@Name='Content_CheckSum']">
			<content:ContentCheckSum><xsl:value-of select="@Value"/></content:ContentCheckSum>
		</xsl:for-each>
		<!-- Presumably the propogation priority applies only to content assets -->
		<xsl:for-each select="//Asset/Metadata/App_Data[@Name='Propagation_Priority']">
			<content:PropagationPriority><xsl:value-of select="@Value"/></content:PropagationPriority>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="StillImageAssetType">
		<xsl:call-template name="ContentAssetType"/>
		<xsl:for-each select="Metadata/App_Data[@Name='Image_Aspect_Ratio']">
			<xsl:variable name="x" select="substring-before(@Value, 'x')"/>
			<xsl:variable name="y" select="substring-after(@Value, 'x')"/>
			<xsl:if test="$x">
				<content:X_Resolution><xsl:value-of select="$x"/></content:X_Resolution>
			</xsl:if>
			<xsl:if test="$y">
				<content:Y_Resolution><xsl:value-of select="$y"/></content:Y_Resolution>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="AudioVideoAssetType">
		<!-- For an asset with asset_class="encrypted" the values on the "movie" with Asset_ID of the Asset_Encrypted are copied over -->
		<xsl:variable name="assetEncrypted" select="Metadata/App_Data[@Name='Asset_Encrypted']/@Value"/>
		<xsl:variable name="sourceNode" select="//Asset[Metadata/AMS/@Asset_ID=$assetEncrypted] | current()"/>
		<xsl:call-template name="ContentAssetType"><xsl:with-param name="masterSource" select="//AMS[@Asset_ID=$assetEncrypted]"/></xsl:call-template>
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Audio_Type']">
			<content:AudioType><xsl:value-of select="@Value"/></content:AudioType>
		</xsl:for-each>
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Screen_Format']">
			<content:ScreenFormat><xsl:value-of select="@Value"/></content:ScreenFormat>
		</xsl:for-each>
		<xsl:for-each select="Metadata/App_Data[@Name='Resolution']">
			<content:Resolution><xsl:value-of select="@Value"/></content:Resolution>
		</xsl:for-each>
		<xsl:for-each select="Metadata/App_Data[@Name='Frame_Rate']">
			<content:FrameRate><xsl:value-of select="@Value"/></content:FrameRate>
		</xsl:for-each>
		<xsl:for-each select="Metadata/App_Data[@Name='Codec']">
			<content:Codec><xsl:value-of select="@Value"/></content:Codec>
		</xsl:for-each>
		<xsl:for-each select="Metadata/App_Data[@Name='Bit_Rate']">
			<content:BitRate><xsl:value-of select="concat(@Value,'000')"/></content:BitRate>
		</xsl:for-each>
		<xsl:choose>
			<xsl:when test="Metadata/AMS[@Asset_Class='preview']"><xsl:apply-templates select="." mode="duration"/></xsl:when>
			<xsl:otherwise><xsl:apply-templates select="/ADI/Asset[Metadata/AMS/@Asset_Class='title']" mode="duration"/></xsl:otherwise>
		</xsl:choose>
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Languages'] ">
			<content:Language><xsl:value-of select="@Value"/></content:Language>
		</xsl:for-each>
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Subtitle_Languages'] ">
			<content:SubtitleLanguage><xsl:value-of select="@Value"/></content:SubtitleLanguage>
		</xsl:for-each>
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Dubbed_Languages'] ">
			<content:DubbedLanguage><xsl:value-of select="@Value"/></content:DubbedLanguage>
		</xsl:for-each>
		<xsl:if test="Metadata/AMS[@Asset_Class='preview']">
			<xsl:for-each select="Metadata/App_Data[@Name='Rating']">
				<content:Rating><xsl:call-template name="rating"/></content:Rating>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='MSORating']">
				<content:Rating ratingSystem="MSO"><xsl:value-of select="@Value"/></content:Rating>
			</xsl:for-each>
			<xsl:for-each select="Metadata/App_Data[@Name='Audience']">
				<content:Audience><xsl:value-of select="@Value"/></content:Audience>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="Metadata/App_Data[@Name='Vendor_Name' or @Name='Receiver_Type' or @Name='Receiver_Version' or @Name='Encryption_Type' or @Name='Encryption_Algorithm' or @Name='Encryption_Date' or @Name='Encryption_Time'  or @Name='Encryption_System_Info'  or @Name='Encryption_Key_Block' ]">
			<content:EncryptionInfo>
				<xsl:for-each select="Metadata/App_Data[@Name='Vendor_Name']">
					<content:VendorName><xsl:value-of select="@Value"/></content:VendorName>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Receiver_Type']">
					<content:ReceiverType><xsl:value-of select="@Value"/></content:ReceiverType>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Receiver_Version']">
					<content:ReceiverVersion><xsl:value-of select="@Value"/></content:ReceiverVersion>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Encryption_Type']">
					<content:Encryption><xsl:value-of select="@Value"/></content:Encryption>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Encryption_Algorithm']">
					<content:EncryptionAlgorithm><xsl:value-of select="@Value"/></content:EncryptionAlgorithm>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Encryption_Date']">
					<content:EncryptionDateTime><xsl:value-of select="concat(@Value,'T',../App_Data[@Name='Encryption_Time']/@Value,'Z')"/></content:EncryptionDateTime>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Encryption_System_Info']">
					<content:EncryptionSystemInfo><xsl:value-of select="@Value"/></content:EncryptionSystemInfo>
				</xsl:for-each>
				<xsl:for-each select="Metadata/App_Data[@Name='Encryption_Key_Block']">
					<content:EncryptionKeyBlock><xsl:value-of select="@Value"/></content:EncryptionKeyBlock>
				</xsl:for-each>
			</content:EncryptionInfo>
		</xsl:if>
		<xsl:if test="$sourceNode/Metadata/App_Data[@Name='Copy_Protection' or @Name='Copy_Protection_Verbose' or @Name='Analog_Protection_System' or @Name='Encryption_Mode_Indicator' or @Name='Constrained_Image_Trigger' or @Name='CGMS_A' ]">
			<content:CopyControlInfo>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Copy_Protection']">
					<content:IsCopyProtection><xsl:call-template name="boolean"/></content:IsCopyProtection>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Copy_Protection_Verbose']">
					<content:IsCopyProtectionVerbose><xsl:call-template name="boolean"/></content:IsCopyProtectionVerbose>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Analog_Protection_System']">
					<content:AnalogProtectionSystem><xsl:value-of select="@Value"/></content:AnalogProtectionSystem>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Encryption_Mode_Indicator']">
					<content:EncryptionModeIndicator><xsl:value-of select="@Value"/></content:EncryptionModeIndicator>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Constrained_Image_Trigger']">
					<content:ConstrainedImageTrigger><xsl:value-of select="@Value"/></content:ConstrainedImageTrigger>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='CGMS_A']">
					<content:CGMS_A><xsl:value-of select="@Value"/></content:CGMS_A>
				</xsl:for-each>
				<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Selectable_Output_Control']">
					<content:RequiresOutputControl><xsl:call-template name="boolean"/></content:RequiresOutputControl>
				</xsl:for-each>
			</content:CopyControlInfo>
		</xsl:if>
		<!-- TODO: Should this also be included in Terms? -->
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='Viewing_Can_Be_Resumed']">
			<content:IsResumeEnabled><xsl:call-template name="boolean"/></content:IsResumeEnabled>
		</xsl:for-each>
		<!-- TODO: Should this also be included in Terms? -->
		<xsl:for-each select="$sourceNode/Metadata/App_Data[@Name='trickModesRestricted']">
				<content:TrickModesRestricted>
					<xsl:call-template name="trickModesRestricted">
						<xsl:with-param name="value" select="@Value"/>
					</xsl:call-template>
				</content:TrickModesRestricted>
		</xsl:for-each>
		<!-- Can we assume that all V1.1 trickfiles relate to all V1.1 movies? -->
		<xsl:if test="Metadata/AMS/@Asset_Class='movie'">
			<xsl:for-each select="//Asset/Metadata/AMS[@Asset_Class='trickfile']">
				<content:TrickRef>
					<xsl:attribute name="uriId"><xsl:value-of select="concat( @Provider_ID, '/Asset/', @Asset_ID)"/></xsl:attribute>
				</content:TrickRef>
			</xsl:for-each>
		</xsl:if>
		<xsl:for-each select="Metadata/App_Data[@Name='3D_Mode']">
			<content:ThreeDMode><xsl:value-of select="@Value"/></content:ThreeDMode>
		</xsl:for-each>
	</xsl:template>
	
	<!-- Reusable Templates -->
	<xsl:template name="AMS_1">
		<xsl:for-each select="Metadata/AMS">
			<xsl:attribute name="providerVersionNum"><xsl:value-of select="@Version_Major"/></xsl:attribute>
			<xsl:attribute name="internalVersionNum"><xsl:value-of select="@Version_Minor"/></xsl:attribute>
			<xsl:attribute name="creationDateTime"><xsl:value-of select="concat(@Creation_Date,'T00:00:00')"/></xsl:attribute>
		</xsl:for-each>
		<!-- TODO: Should this only be provided for Offer? -->
		<xsl:for-each select="//App_Data[@Name='Licensing_Window_Start']">
			<xsl:attribute name="startDateTime">
				<xsl:choose>
					<xsl:when test="string-length(@Value) = 10">
						<xsl:value-of select="concat(@Value,'T00:00:00')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@Value"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:for-each>
		<xsl:for-each select="//App_Data[@Name='Licensing_Window_End']">
			<xsl:attribute name="endDateTime">
				<xsl:choose>
					<xsl:when test="string-length(@Value) = 10">
						<xsl:value-of select="concat(@Value,'T23:59:59')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@Value"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="AMS_2">
		<xsl:for-each select="Metadata/AMS">
			<core:AlternateId identifierSystem="VOD1.1"><xsl:value-of select="concat('vod://',@Provider_ID,'/',@Asset_ID)"/></core:AlternateId>
		</xsl:for-each>
		<!-- EIDR would only be expected for title-level Metadata -->
		<xsl:for-each select="Metadata/App_Data[@Name='EIDR']">
			<core:AlternateId identifierSystem="EIDR"><xsl:value-of select="@Value"/></core:AlternateId>
		</xsl:for-each>
		<!-- ISAN would only be expected for title-level Metadata -->
		<xsl:for-each select="Metadata/App_Data[@Name='ISAN']">
			<core:AlternateId identifierSystem="ISAN"><xsl:value-of select="@Value"/></core:AlternateId>
		</xsl:for-each>
		<!-- Currently Provider QA Contact would only be mapped at title-level; should this be offer and content level also? -->
		<xsl:for-each select="Metadata/App_Data[@Name='Provider_QA_Contact']">
			<core:ProviderQAContact><xsl:value-of select="@Value"/></core:ProviderQAContact>
		</xsl:for-each>
		<xsl:for-each select="Metadata/AMS">
			<core:AssetName deprecated="true"><xsl:value-of select="@Asset_Name"/></core:AssetName>
			<core:Product deprecated="true"><xsl:value-of select="@Product"/></core:Product>
			<core:Provider><xsl:value-of select="@Provider"/></core:Provider>
			<core:Description deprecated="true"><xsl:value-of select="@Description"/></core:Description>
		</xsl:for-each>
		<core:Ext>
			<xsl:apply-templates select="Metadata/App_Data"/>
		</core:Ext>
	</xsl:template>

	<xsl:template name="person">
		<xsl:choose>
			<xsl:when test="contains(@Value,',')">
				<xsl:variable name="firstname" select="normalize-space(substring-after(@Value,','))"/>
				<xsl:variable name="lastname" select="normalize-space(substring-before(@Value,','))"/>
				<xsl:attribute name="fullName"><xsl:value-of select="concat($firstname,' ',$lastname)"/></xsl:attribute>
				<xsl:attribute name="firstName"><xsl:value-of select="$firstname"/></xsl:attribute>
				<xsl:attribute name="lastName"><xsl:value-of select="$lastname"/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="fullName"><xsl:value-of select="@Value"/></xsl:attribute>
				<xsl:attribute name="firstName"><xsl:value-of select="@Value"/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:attribute name="sortableName"><xsl:value-of select="@Value"/></xsl:attribute>
	</xsl:template>
	
	<xsl:template name="boolean">
		<xsl:choose>
			<xsl:when test="@Value='Y'">true</xsl:when>
			<xsl:when test="@Value='N'">false</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="rating">
		<xsl:choose>
			<xsl:when test="@Value='G' or @Value='PG' or @Value='PG-13' or @Value='R' or @Value='NC-17'">
				<xsl:attribute name="ratingSystem">MPAA</xsl:attribute>
			</xsl:when>
			<xsl:when test="starts-with(@Value,'TV')">
				<xsl:attribute name="ratingSystem">TV</xsl:attribute>
			</xsl:when>
		</xsl:choose>
		<xsl:value-of select="@Value"/>
	</xsl:template>
	
	<xsl:template match="*" mode="duration">
		<xsl:for-each select="Metadata/App_Data[@Name='Run_Time']">
			<xsl:variable name="hours" select="concat('PT',substring-before(@Value, ':'),'H')"/>
			<xsl:variable name="remaining" select="substring-after(@Value, ':')"/>
			<xsl:variable name="minutes" select="concat(substring-before($remaining, ':'),'M')"/>
			<xsl:variable name="seconds" select="concat(substring-after($remaining, ':'),'S')"/>
			<content:Duration><xsl:value-of select="concat($hours,$minutes,$seconds)"/></content:Duration>
		</xsl:for-each>
	</xsl:template>
	
	<!-- TODO: This template needs to be modified to correctly express Trick mode restrictions (currently using private:) -->
	<xsl:template name="trickModesRestricted">
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($value,',')">
				<xsl:call-template name="trickModesRestricted">
					<xsl:with-param name="value" select="substring-before($value,',')"/>
				</xsl:call-template>
				<xsl:call-template name="trickModesRestricted">
					<xsl:with-param name="value" select="substring-after($value,',')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<core:TrickModeExclusion>
					<xsl:attribute name="type"><xsl:value-of select="concat('private:',normalize-space($value))"/></xsl:attribute>
				</core:TrickModeExclusion>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Templates to convert trial use fields to Ext elements - ignoring all known fields -->
	<xsl:template match="App_Data[../AMS/@Asset_Class='package']">
		<xsl:choose>
			<xsl:when test="@Name='Provider_Content_Tier'"/>
			<xsl:when test="@Name='Metadata_Spec_Version'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='title']">
		<xsl:variable name="barker" select="//AMS[@Asset_Class='barker']"/>
		<xsl:choose>
			<xsl:when test="@Name='Type'"/>
			<xsl:when test="@Name='Title'"/>
			<xsl:when test="@Name='Summary_Short'"/>
			<xsl:when test="@Name='Rating'"/>
			<xsl:when test="@Name='Display_Run_Time'"/>
			<xsl:when test="@Name='Run_Time'"/>
			<xsl:when test="@Name='Category'"/>
			<xsl:when test="@Name='Licensing_Window_Start'"/>
			<xsl:when test="@Name='Licensing_Window_End'"/>
			<xsl:when test="@Name='Year'"/>
			<xsl:when test="@Name='Studio'"/>
			<xsl:when test="@Name='Closed_Captioning'"/>
			<xsl:when test="@Name='Title_Sort_Name' and not($barker)"/>
			<xsl:when test="@Name='Subscriber_View_Limit' and not($barker)"/>
			<xsl:when test="@Name='Title_Brief' and not($barker)"/>
			<xsl:when test="@Name='EIDR' and not($barker)"/>
			<xsl:when test="@Name='ISAN' and not($barker)"/>
			<xsl:when test="@Name='Episode_Name' and not($barker)"/>
			<xsl:when test="@Name='Episode_ID' and not($barker)"/>
			<xsl:when test="@Name='Summary_Long' and not($barker)"/>
			<xsl:when test="@Name='Summary_Medium' and not($barker)"/>
			<xsl:when test="@Name='MSORating' and not($barker)"/>
			<xsl:when test="@Name='Advisories' and not($barker)"/>
			<xsl:when test="@Name='Audience' and not($barker)"/>
			<xsl:when test="@Name='Country_of_Origin' and not($barker)"/>
			<xsl:when test="@Name='Actors' and not($barker)"/>
			<xsl:when test="@Name='Actors_Display' and not($barker)"/>
			<xsl:when test="@Name='Writer_Display' and not($barker)"/>
			<xsl:when test="@Name='Director' and not($barker)"/>
			<xsl:when test="@Name='Producers' and not($barker)"/>
			<xsl:when test="@Name='Season_Premiere' and not($barker)"/>
			<xsl:when test="@Name='Season_Finale' and not($barker)"/>
			<xsl:when test="@Name='Genre' and not($barker)"/>
			<xsl:when test="@Name='Show_Type' and not($barker)"/>
			<xsl:when test="@Name='Chapter' and not($barker)"/>
			<xsl:when test="@Name='Box_Office' and not($barker)"/>
			<xsl:when test="@Name='Propagation_Priority' and not($barker)"/>
			<xsl:when test="@Name='Billing_ID' and not($barker)"/>
			<xsl:when test="@Name='Preview_Period' and not($barker)"/>
			<xsl:when test="@Name='Home_Video_Window' and not($barker)"/>
			<xsl:when test="@Name='Display_As_New' and not($barker)"/>
			<xsl:when test="@Name='Display_As_Last_Chance' and not($barker)"/>
			<xsl:when test="@Name='Maximum_Viewing_Length' and not($barker)"/>
			<xsl:when test="@Name='Provider_QA_Contact' and not($barker)"/>
			<xsl:when test="@Name='Contract_Name' and not($barker)"/>
			<xsl:when test="@Name='Suggested_Price' and not($barker)"/>
			<xsl:when test="@Name='Distributor_Royalty_Percent' and not($barker)"/>
			<xsl:when test="@Name='Distributor_Royalty_Minimum' and not($barker)"/>
			<xsl:when test="@Name='Distributor_Royalty_Flat_Rate' and not($barker)"/>
			<xsl:when test="@Name='Distributor_Name' and not($barker)"/>
			<xsl:when test="@Name='Studio_Royalty_Percent' and not($barker)"/>
			<xsl:when test="@Name='Studio_Royalty_Minimum' and not($barker)"/>
			<xsl:when test="@Name='Studio_Royalty_Flat_Rate' and not($barker)"/>
			<xsl:when test="@Name='Studio_Name' and not($barker)"/>
			<xsl:when test="@Name='Studio_Code' and not($barker)"/>
			<xsl:when test="@Name='Programmer_Call_Letters' and not($barker)"/>
			<xsl:when test="@Name='Recording_Artist' and not($barker)"/>
			<xsl:when test="@Name='Song_Title' and not($barker)"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='movie']">
		<xsl:choose>
			<xsl:when test="@Name='Encryption'"/>
			<xsl:when test="@Name='Type'"/>
			<xsl:when test="@Name='Audio_Type'"/>
			<xsl:when test="@Name='Screen_Format'"/>
			<xsl:when test="@Name='Resolution'"/>
			<xsl:when test="@Name='Frame_Rate'"/>
			<xsl:when test="@Name='Codec'"/>
			<xsl:when test="@Name='Languages'"/>
			<xsl:when test="@Name='Subtitle_Languages'"/>
			<xsl:when test="@Name='Dubbed_Languages'"/>
			<xsl:when test="@Name='Copy_Protection'"/>
			<xsl:when test="@Name='Copy_Protection_Verbose'"/>
			<xsl:when test="@Name='Analog_Protection_System'"/>
			<xsl:when test="@Name='Encryption_Mode_Indicator'"/>
			<xsl:when test="@Name='Constrained_Image_Trigger'"/>
			<xsl:when test="@Name='CGMS_A'"/>
			<xsl:when test="@Name='Viewing_Can_Be_Resumed'"/>
			<xsl:when test="@Name='Bit_Rate'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:when test="@Name='trickModesRestricted'"/>
			<xsl:when test="@Name='Selectable_Output_Control'"/>
			<xsl:when test="@Name='3D_Mode'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='box cover' or ../AMS/@Asset_Class='poster']">
		<xsl:choose>
			<xsl:when test="@Name='Type'"/>
			<xsl:when test="@Name='Image_Aspect_Ratio'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='preview']">
		<xsl:choose>
			<xsl:when test="@Name='Rating'"/>
			<xsl:when test="@Name='MSORating'"/>
			<xsl:when test="@Name='Audience'"/>
			<xsl:when test="@Name='Run_Time'"/>
			<xsl:when test="@Name='Type'"/>
			<xsl:when test="@Name='Audio_Type'"/>
			<xsl:when test="@Name='Screen_Format'"/>
			<xsl:when test="@Name='Resolution'"/>
			<xsl:when test="@Name='Frame_Rate'"/>
			<xsl:when test="@Name='Codec'"/>
			<xsl:when test="@Name='Languages'"/>
			<xsl:when test="@Name='Subtitle_Languages'"/>
			<xsl:when test="@Name='Dubbed_Languages'"/>
			<xsl:when test="@Name='Bit_Rate'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:when test="@Name='trickModesRestricted'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='trickfile']">
		<xsl:choose>
			<xsl:when test="@Name='Vendor_Name'"/>
			<xsl:when test="@Name='Vendor_Product'"/>
			<xsl:when test="@Name='For_Version'"/>
			<xsl:when test="@Name='Trick_Mode'"/>
			<xsl:when test="@Name='Bit_Rate'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='encrypted']">
		<xsl:choose>
			<xsl:when test="@Name='Asset_Encrypted'"/>
			<xsl:when test="@Name='Vendor_Name'"/>
			<xsl:when test="@Name='Receiver_Type'"/>
			<xsl:when test="@Name='Receiver_Version'"/>
			<xsl:when test="@Name='Encryption_Type'"/>
			<xsl:when test="@Name='Encryption_Algorithm'"/>
			<xsl:when test="@Name='Encryption_Date'"/>
			<xsl:when test="@Name='Encryption_Time'"/>
			<xsl:when test="@Name='Encryption_System_Info'"/>
			<xsl:when test="@Name='Encryption_Key_Block'"/>
			<xsl:when test="@Name='Resolution'"/>
			<xsl:when test="@Name='Frame_Rate'"/>
			<xsl:when test="@Name='Codec'"/>
			<xsl:when test="@Name='Bit_Rate'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="App_Data[../AMS/@Asset_Class='barker']">
		<xsl:choose>
			<xsl:when test="@Name='Type'"/>
			<xsl:when test="@Name='Audio_Type'"/>
			<xsl:when test="@Name='Resolution'"/>
			<xsl:when test="@Name='Frame_Rate'"/>
			<xsl:when test="@Name='Codec'"/>
			<xsl:when test="@Name='Languages'"/>
			<xsl:when test="@Name='Subtitle_Languages'"/>
			<xsl:when test="@Name='Dubbed_Languages'"/>
			<xsl:when test="@Name='Bit_Rate'"/>
			<xsl:when test="@Name='Content_FileSize'"/>
			<xsl:when test="@Name='Content_CheckSum'"/>
			<xsl:when test="@Name='trickModesRestricted'"/>
			<xsl:otherwise><xsl:call-template name="appData"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="appData">
		<App_Data>
			<xsl:attribute name="App"><xsl:value-of select="@App"/></xsl:attribute>
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="Value"><xsl:value-of select="@Value"/></xsl:attribute>
		</App_Data>
	</xsl:template>
</xsl:stylesheet>
