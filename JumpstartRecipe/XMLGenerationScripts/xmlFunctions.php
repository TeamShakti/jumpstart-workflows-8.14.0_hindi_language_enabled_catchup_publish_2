<?php
          
// Constants used in XML

define("providerVersionNum","1");
define("internalVersionNum","0");
define("creationDateTime","2013-03-05T00:00:00Z");
define("startDateTime","2013-03-07T00:00:00Z");
define("endDateTime","2014-03-07T11:59:59Z");


function in_multiarray($elem, $array)
{
   $top = sizeof($array) - 1;
   $bottom = 0;
   while($bottom <= $top)
   {
       if($array[$bottom] == $elem)
           return true;
       else
           if(is_array($array[$bottom]))
               if(in_multiarray($elem, ($array[$bottom])))
                   return true;
              
       $bottom++;
   }       
   return false;
}

//generate unique uriId, the format is irdeto.com/datatype/, the last part comprises with 5 letters and 15 digits, datatypes are

function generateUniqueID( $idType, $idValue, $firstBit)
{
  switch( $idType ) {
    case "Offer":
        $uniqueID=sprintf( 'irdeto.com/Offer/IRDET2013%05d%06d', $firstBit, $idValue );
        break;
    case "Category":
        $uniqueID=sprintf( 'irdeto.com/Category/IRDET2013%05d%06d', $firstBit, $idValue );
        break;
    case "Terms":
        $uniqueID=sprintf( 'irdeto.com/Terms/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "ContentGroup":
        $uniqueID=sprintf( 'irdeto.com/ContentGroup/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Title":
        $uniqueID=sprintf( 'irdeto.com/Title/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Movie":
        $uniqueID=sprintf( 'irdeto.com/Movie/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Preview":
        $uniqueID=sprintf( 'irdeto.com/Preview/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "BoxCover":
        $uniqueID=sprintf( 'irdeto.com/BoxCover/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Poster":
        $uniqueID=sprintf( 'irdeto.com/Poster/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Thumbnail":
        $uniqueID=sprintf( 'irdeto.com/Thumbnail/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Barker":
        $uniqueID=sprintf( 'irdeto.com/Barker/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Brand":
        $uniqueID=sprintf( 'irdeto.com/Brand/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
    case "Series":
        $uniqueID=sprintf( 'irdeto.com/Series/IRDET2013%05d%06d', $firstBit, $idValue);
        break;
 }
  
  return $uniqueID;
};

//2013000000000000_boxcover_1_en-US.bmp  <name>_<sub_asset_type>_<version>_<loc>.

function assetFileNameConvention ($name, $subAssetType, $version, $loc)
{
    $assetFileName = $name."_".$subAssetType."_".$version."_".$loc;
    return $assetFileName;
};

function getLastPartUriId ($uriId)
{
    $lastPartUriId = substr($uriId, -20);
    return $lastPartUriId;
};


//To do categoryUriId2, make it variable <offer:CategoryPath>/Movies/Eighties</offer:CategoryPath>


function generateXmlHeader()
{
    print "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    print "<ADI3 xmlns=\"urn:cablelabs:md:xsd:core:3.0\" xmlns:content=\"urn:cablelabs:md:xsd:content:3.0\"\n";
    print "    xmlns:core=\"urn:cablelabs:md:xsd:core:3.0\" xmlns:offer=\"urn:cablelabs:md:xsd:offer:3.0\"\n";
    print "    xmlns:terms=\"urn:cablelabs:md:xsd:terms:3.0\" xmlns:title=\"urn:cablelabs:md:xsd:title:3.0\"\n";
    print "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:irdeto=\"http://www.irdeto.com/schemas/metadata/1.0\"\n";
    print "    xsi:schemaLocation=\"urn:cablelabs:md:xsd:core:3.0 MD-SP-CORE-I02.xsd\n";
    print "        urn:cablelabs:md:xsd:offer:3.0 MD-SP-OFFER-I02.xsd\n";
    print "        urn:cablelabs:md:xsd:title:3.0 MD-SP-TITLE-I02.xsd\n";
    print "        urn:cablelabs:md:xsd:content:3.0 MD-SP-CONTENT-I02.xsd\n";
    print "        urn:cablelabs:md:xsd:terms:3.0 MD-SP-TERMS-I02.xsd\n";
    print "        http://www.irdeto.com/schemas/metadata/1.0 Irdeto-ADI-3_0-extension.xsd\">\n";
};

function generateXmlFooter()
{
    print "</ADI3>\n";
};

//to make more than one offers, there will be more than one <offer:ContentGroupRef> 

function generateXmlOfferOfferType($offerUriId, $categoryUriId, $termUriId, $movieLibrary, $offerSet )
{
    print "        <Asset xsi:type=\"offer:OfferType\" uriId=\"";
    print $offerUriId;
    print "\"";
    print " providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "            <core:Provider>Universal Pictures</core:Provider>\n";
    print "            <offer:Presentation>\n";
    print "                <offer:CategoryRef uriId=\"$categoryUriId\"/>\n";
    print "                <offer:DisplayAsNew>P7D</offer:DisplayAsNew>\n";
    print "                <offer:DisplayAsLastChance>P7D</offer:DisplayAsLastChance>\n";
    print "            </offer:Presentation>\n";
    print "            <offer:PromotionalContentGroupRef uriId=\"{$movieLibrary[0][0][1]}\"/>\n";
    print "            <offer:BillingId>56789</offer:BillingId>\n";
    print "            <offer:TermsRef uriId=\"$termUriId\"/>\n";
    $movieLibrarySize=count( $movieLibrary[$offerSet], 0 );
    for( $iter=0; $iter<$movieLibrarySize; $iter++ )
    {
        //print "           <offer:ContentGroupRef uriId=\"{$movieLibrary[$offerSet][$iter+$contentIdOffset][1]}\"/>\n";
        print "           <offer:ContentGroupRef uriId=\"{$movieLibrary[$offerSet][$iter][1]}\"/>\n";
    }
    print "        </Asset>\n";
};

function generateXmlTitleTitleType($titleUriId, $titleName)
{
    print "     <Asset xsi:type=\"title:TitleType\" uriId=\"$titleUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "		   <core:AlternateId identifierSystem=\"ISAN\">1881-66C7-3420-000-7-9F3A-02450- U</core:AlternateId>\n";
    print "		   <core:ProviderQAContact>John Doe, JDoe@irdeto.com</core:ProviderQAContact>\n";
	print "           <core:Provider>Universal Pictures</core:Provider>\n";
	print "	       <title:LocalizableTitle>\n";
	print "		      <title:TitleSortName>$titleName</title:TitleSortName>\n";
	print "		      <title:TitleBrief>$titleName</title:TitleBrief>\n";
	print "		      <title:TitleMedium>$titleName</title:TitleMedium>\n";
	print "		      <title:TitleLong>$titleName, Part One</title:TitleLong>\n";
	print "		      <title:SummaryShort>Back to the Future is a 1985 American science fiction adventure comedy film.</title:SummaryShort>\n";
	print "		      <title:SummaryMedium>A teenager is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his friend, Dr. Emmett Brown, and must make sure his high-school-age parents unite in order to save his own existence.</title:SummaryMedium>\n";
	print "		      <title:SummaryLong>Seventeen-year-old Marty McFly lives with his bleak, unambitious family in Hill Valley, California. His father, George McFly, is bullied by his supervisor, Biff Tannen, while his unhappy mother, Lorraine Baines McFly, is an alcoholic.
				
				Marty meets his friend, scientist Dr. Emmett \"Doc\" Brown, late at night in the parking lot of a deserted shopping mall where Doc reveals a time machine made from a modified 1981 DeLorean DMC-12; the vehicle's time displacement is powered by plutonium, which supplies the 1.21 gigawatts of power to a device he calls the \"flux capacitor.\" Doc explains that the car travels to a programmed date upon reaching 88 miles per hour, using the date November 5, 1955, as an example destination. Before Doc can make his first trip, the Libyan terrorists from whom he stole the plutonium shoot him. Marty attempts to escape in the DeLorean and inadvertently activates the time machine. He is transported back to November 5, 1955, and finds himself without the plutonium needed for the return trip.
				
				While exploring Hill Valley, Marty meets his teenaged father, who is being bullied by Biff. As George is about to be hit by Lorraine's father's car, Marty pushes him out of the way and is knocked out by the impact. Consequently, a teenaged Lorraine becomes infatuated with Marty instead of George. Marty is disturbed by her flirtations and leaves to find the younger Doc of 1955. Marty convinces Doc that he is from the future, and asks for help returning to 1985. Doc explains that the only available power source capable of generating 1.21 gigawatts of energy is a bolt of lightning. Discovering the \"Save the Clock Tower\" flyer that Marty received in 1985, indicating that lightning will strike the courthouse clock tower the following Saturday at 10:04 pm, Doc makes plans to harness the lightning strike to power the DeLorean's flux capacitor. When they observe a fading photograph of Marty with his siblings, they realize Marty has prevented his parents from meeting, jeopardizing his family's existence.
				Marty attempts to set George up with Lorraine. To make his parents fall in love, Marty plans to have George \"rescue\" Lorraine from Marty's inappropriate advances on the night of the school dance. A drunk Biff unexpectedly shows up, pulls Marty from the car, and attempts to force himself on Lorraine. George arrives to rescue her from Marty, but instead finds Biff, who humiliates George and pushes Lorraine to the ground. Standing up to him for the first time, George knocks Biff out. A smitten Lorraine follows George to the dance floor, where they kiss for the first time, ensuring Marty's existence.
				Marty arrives at the clock tower where Doc is making final preparations for the lightning strike, and tries to warn Doc of his impending 1985 murder in a letter, but Doc tears it up, fearing it will lead to altering the future. A falling tree branch disconnects Doc's wiring setup, but Doc repairs the connections in time to send Marty and the DeLorean back to 1985. Although Marty arrives too late to prevent him from being shot, Doc is still alive and admits to reading the letter anyway and wearing a bulletproof vest.
				Doc drops Marty off at home and uses the time machine to travel 30 years into the future. Marty awakens the next morning to find his family changed; Lorraine is happy and physically fit, a self-confident George is a successful science fiction author, Dave is an office employee, and Linda no longer has trouble finding boyfriends. George and Lorraine now have a closer relationship than ever, while Biff has become an auto detailer/washer who is on good terms with the McFly family. As Marty reunites with Jennifer, Doc arrives, insisting they accompany him to the future to sort out a problem with their future children. Marty and Jennifer enter the upgraded DeLorean, now a hovercar powered by nuclear fusion, and Doc flies the time machine into the future.</title:SummaryLong>\n";
	print "		      <title:ActorDisplay>Michael J. Fox, Christopher Lloyd, Lea Thompson, Crispin Glover</title:ActorDisplay>\n";
	print "		      <title:Actor fullName=\"Michael J. Fox\" firstName=\"Michael\" lastName=\"Fox\" sortableName=\"Fox, Michael J.\"/>\n";
	print "		      <title:Actor fullName=\"Christopher Lloyd\" firstName=\"Christopher\" lastName=\"Lloyd\" sortableName=\"Lloyd, Christopher\"/>\n";
	print "		      <title:Actor fullName=\"Lea Thompson\" firstName=\"Lea\" lastName=\"Thompson\" sortableName=\"Thompson, Lea\"/>\n";
	print "		      <title:Actor fullName=\"Crispin Glover\" firstName=\"Crispin\" lastName=\"Glover\" sortableName=\"Glover, Crispin\"/>\n";
	print "		      <title:WriterDisplay>Robert Zemeckis and Bob Gale</title:WriterDisplay>\n";
	print "		      <title:Director fullName=\"Robert Zemeckis\" firstName=\"Robert\" lastName=\"Zemeckis\" sortableName=\"Zemeckis, Robert\"/>\n";
	print "		      <title:Director fullName=\"Bob Gale\" firstName=\"Bob\" lastName=\"Gale\" sortableName=\"Gale, Bob\"/>\n";
	print "		      <title:Producer fullName=\"John Snow\" firstName=\"John\" lastName=\"Snow\" sortableName=\"John, Snow\"/>\n";
	print "           <title:StudioDisplay>Universal Pictures</title:StudioDisplay>\n";
	print "		      <title:RecordingArtist>Alan Silvestri</title:RecordingArtist>\n";
	print "		      <title:EpisodeName>Episode 1</title:EpisodeName>\n";
	print "		      <title:EpisodeID>1</title:EpisodeID>\n";
	print "		      <title:Chapter heading=\"Opening Scene\" timeCode=\"00:00:02:00\"/>\n";
	print "		      <title:Chapter heading=\"They Meet\" timeCode=\"00:15:12:00\"/>\n";
	print "		      <title:Chapter heading=\"The Collision\" timeCode=\"02:03:06:00\"/>\n";
	print "		      <title:Chapter heading=\"She Goes Under\" timeCode=\"02:55:15:00\"/>\n";
	print "		      <title:Chapter heading=\"Final Scene\" timeCode=\"03:02:09:00\"/>\n";
	print "	       </title:LocalizableTitle>\n";
	print "	       <title:Rating ratingSystem=\"MPAA\">PG</title:Rating>\n";
	print "	       <title:Advisory>AL</title:Advisory>\n";
	print "	       <title:Advisory>N</title:Advisory>\n";
	print "	       <title:IsClosedCaptioning>false</title:IsClosedCaptioning>\n";
	print "	       <title:DisplayRunTime>02:40:00</title:DisplayRunTime>\n";
	print "	       <title:Year>1985</title:Year>\n";
	print "	       <title:CountryOfOrigin>US</title:CountryOfOrigin>\n";
	print "	       <title:Genre>Action</title:Genre>\n";
	print "	       <title:Genre>Adventure</title:Genre>\n";
	print "	       <title:Genre>Comedy</title:Genre>\n";
	print "	       <title:Genre>Sci-fi</title:Genre>\n";
	print "	       <title:ShowType>Movie</title:ShowType>\n";
	print "	       <title:IsSeasonPremiere>false</title:IsSeasonPremiere>\n";
	print "	       <title:IsSeasonFinale>false</title:IsSeasonFinale>\n";
	print "	       <title:BoxOffice>190000000</title:BoxOffice>\n";
	print "	       <title:ProgrammerCallLetters>BTTF</title:ProgrammerCallLetters>\n";
    print "     </Asset>\n";
};


function generateXmlOfferContentGroupType($contentUriId, $titleUriId, $movieUriId, $previewUriId, $boxCoverUriId)
{
    print "     <Asset xsi:type=\"offer:ContentGroupType\" uriId=\"$contentUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print " 		  <offer:TitleRef uriId=\"$titleUriId\"/>\n";
	print "	          <offer:MovieRef uriId=\"$movieUriId\"/>\n";
	print "	          <offer:PreviewRef uriId=\"$previewUriId\"/>\n";
	print "	          <offer:BoxCoverRef uriId=\"$boxCoverUriId\"/>\n";
	print "	          <!--		<offer:MovieRef uriId=\"irdeto.com/Asset/IRDE2012081701004005\"/>-->\n";
    print "     </Asset>\n";
};

function generateXmlTermTermsType($termUriId)
{
    print "     <Asset xsi:type=\"terms:TermsType\" uriId=\"$termUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "		       <terms:ContractName>Irdeto</terms:ContractName>\n";
	print "	           <terms:BillingGracePeriod>PT300S</terms:BillingGracePeriod>\n";
	print "	           <terms:UsagePeriod>P00DT24H00M</terms:UsagePeriod>\n";
	print "	           <terms:HomeVideoWindow>P1000D</terms:HomeVideoWindow>\n";
	print "	           <terms:SubscriberViewLimit startDateTime=\"2002-02-01T00:00:00Z\" endDateTime=\"2002-02-28T23:59:59Z\" maximumViews=\"5\"/>\n";
	print "	           <terms:SubscriberViewLimit startDateTime=\"2002-03-01T00:00:00Z\" endDateTime=\"2002-03-31T23:59:59Z\" maximumViews=\"5\"/>\n";
	print "	           <terms:SuggestedPrice>5.95</terms:SuggestedPrice>\n";
	print "	           <terms:DistributorRoyaltyInfo>\n";
	print "		          <terms:OrganizationName>Irdeto</terms:OrganizationName>\n";
	print "		          <terms:RoyaltyPercent>52.5</terms:RoyaltyPercent>\n";
	print "		          <terms:RoyaltyMinimum>3.124</terms:RoyaltyMinimum>\n";
	print "		          <terms:RoyaltyFlatRate>3.155</terms:RoyaltyFlatRate>\n";
	print "	           </terms:DistributorRoyaltyInfo>\n";
	print "	           <terms:StudioRoyaltyInfo>\n";
	print "		          <terms:OrganizationName>Paramount</terms:OrganizationName>\n";
	print "		          <terms:OrganizationCode>PAR</terms:OrganizationCode>\n";
	print "		          <terms:RoyaltyPercent>47.5</terms:RoyaltyPercent>\n";
	print "		          <terms:RoyaltyMinimum>2.826</terms:RoyaltyMinimum>\n";
	print "		          <terms:RoyaltyFlatRate>2.795</terms:RoyaltyFlatRate>\n";
	print "	           </terms:StudioRoyaltyInfo>\n";
    print "     </Asset>\n";  
};

function generateXmlOfferCategoryType($categoryUriId1)
{
    print "     <Asset xsi:type=\"offer:CategoryType\" uriId=\"$categoryUriId1\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "	           <offer:CategoryPath>/Movies/Eighties</offer:CategoryPath>\n";
    print "     </Asset>\n";
};

function generateXmlContentMovieType($movieUriId, $sourceUrl)
{
    print "     <Asset xsi:type=\"content:MovieType\" uriId=\"$movieUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "	       <core:Provider>Universal Pictures</core:Provider>\n";
	print "	       <content:SourceUrl>$sourceUrl.mpg</content:SourceUrl>\n";
	print "	       <content:ContentFileSize>3907840625</content:ContentFileSize>\n";
	print "	       <content:ContentCheckSum>12558D3269D25852BD26548DC2654CA2</content:ContentCheckSum>\n";
	print "	       <content:PropagationPriority>1</content:PropagationPriority>\n";
	print "	       <content:AudioType>Dolby Digital</content:AudioType>\n";
	print "	       <content:ScreenFormat>Widescreen</content:ScreenFormat>\n";
	print "	       <content:Duration>PT03H14M00S</content:Duration>\n";
	print "	       <content:Language>en</content:Language>\n";
	print "	       <content:SubtitleLanguage>nl</content:SubtitleLanguage>\n";
	print "	       <content:SubtitleLanguage>fr</content:SubtitleLanguage>\n";
	print "	       <content:DubbedLanguage>nl</content:DubbedLanguage>\n";
	print "	       <content:DubbedLanguage>fr</content:DubbedLanguage>\n";
	print "	       <content:Rating ratingSystem=\"MPAA\">R</content:Rating>\n";
	print "	       <content:CopyControlInfo>\n";
	print "		      <content:IsCopyProtection>false</content:IsCopyProtection>\n";
	print "	       </content:CopyControlInfo>\n";
    print "     </Asset>\n";
};


function generateXmlContentPreviewType($previewUriId, $sourceUrl)
{
    print "     <Asset xsi:type=\"content:PreviewType\" uriId=\"$previewUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "        <core:Provider>Universal Pictures</core:Provider>\n";
	print "	       <content:SourceUrl>$sourceUrl.mpg</content:SourceUrl>\n";
	print "	       <content:ContentFileSize>25284375</content:ContentFileSize>\n";
	print "	       <content:ContentCheckSum>A1258D3269D25852BD26548DC2654C12</content:ContentCheckSum>\n";
	print "	       <content:PropagationPriority>1</content:PropagationPriority>\n";
	print "	       <content:AudioType>Dolby Digital</content:AudioType>\n";
	print "	       <content:ScreenFormat>Widescreen</content:ScreenFormat>\n";
	print "	       <content:Duration>PT00H00M45S</content:Duration>\n";
	print "	       <content:Language>en</content:Language>\n";
	print "	       <content:SubtitleLanguage>nl</content:SubtitleLanguage>\n";
	print "	       <content:SubtitleLanguage>fr</content:SubtitleLanguage>\n";
	print "	       <content:DubbedLanguage>nl</content:DubbedLanguage>\n";
	print "	       <content:DubbedLanguage>fr</content:DubbedLanguage>\n";
	print "	       <content:Rating ratingSystem=\"MPAA\">G</content:Rating>\n";
    print "     </Asset>\n";
};


function generateXmlContentBoxCoverType($boxCoverUriId, $sourceUrl)
{
    print "     <Asset xsi:type=\"content:BoxCoverType\" uriId=\"$boxCoverUriId\" providerVersionNum=\"";
    print providerVersionNum;
    print "\"";
    print " internalVersionNum=\"";
    print internalVersionNum;
    print "\"";
    print " creationDateTime=\"";
    print creationDateTime;
    print "\"";
    print " startDateTime=\"";
    print startDateTime;
    print "\"";
    print " endDateTime=\"";
    print endDateTime;
    print "\">\n";
    print "        <core:Provider>Universal Pictures</core:Provider>\n";
	print "	       <core:Ext/>\n";
	print "	       <content:SourceUrl>$sourceUrl.bmp</content:SourceUrl>\n";
	print "	       <content:ContentFileSize>15235</content:ContentFileSize>\n";
	print "	       <content:ContentCheckSum>B3258D3269D25852BD26548DC2654CD2</content:ContentCheckSum>\n";
	print "	       <content:PropagationPriority>1</content:PropagationPriority>\n";
	print "	       <content:X_Resolution>320</content:X_Resolution>\n";
	print "	       <content:Y_Resolution>240</content:Y_Resolution>\n";
    print "     </Asset>\n";
};

function generateXmlIrdetoBrandType($brandUriId, $brandTitleUriId, $seasonSet, $offerSet, $totalSeason)
{
    print "<!--Here follow Irdeto proprietary extensions to mark up the Brand and Series-->\n";
    print "     <Asset xsi:type=\"irdeto:BrandType\" uriId=\"$brandUriId\" > \n";
    print "		   <!--Link to this Brand's title-->\n";	
	print "	       <irdeto:TitleRef uriId=\"$brandTitleUriId\" />\n";
	//$episodeSize = 	count( $movieLibrary[$offerSet], 0 );
	for ($iter=0; $iter<$totalSeason; $iter++)
	{
	   print "	       <irdeto:SeriesRef uriId=\"{$seasonSet[$offerSet][$iter][1]}\"/> \n";
	}
	print "	       <!--Link to Series -->\n";
	print "     </Asset>\n";
}

function generateXmlIrdetoSeriesType($seriesUriId, $titleUriId, $movieLibrary, $offerSet, $seasonCount)
{
    print "     <Asset xsi:type=\"irdeto:SeriesType\" uriId=\"$seriesUriId\" > \n";
    print "		   <!--Link to this Series' title-->\n";	
	print "	       <irdeto:TitleRef uriId=\"$titleUriId\" />\n";	
	print "	       <!--Links to the programs present in this file. Typically, there would be more episodes.-->\n";	
	//print "	       <irdeto:ContentGroupRef uriId=\"$contentUriId\"/> \n";
	
    for ($i=0; $i<$seasonCount; $i++)
    {
        print "           <irdeto:ContentGroupRef uriId=\"{$movieLibrary[$offerSet][$i][1]}\"/>\n";
    }
   
    print "     </Asset>\n";
}


?>
