<?php

        //1 offer, 1 brand, 1 series with 2 seasons
        
        include 'xmlFunctions.php';
       
        //3 dimontinal array, 
        //first array (offer set): different offers, 
        //second array (movie set): movies in each offer, 
        //third array (movie attribute): movie attributes for each movie: ([Title], [ID], [repeat])
        
        $offerSet = array (array( array( "Game of Thrones11", "ID", "0"),
                                  array( "Game of Thrones12", "ID", "0"),
                                  array( "Game of Thrones13", "ID", "0"),
                                  array( "Game of Thrones21", "ID", "0"),
                                  array( "Game of Thrones22", "ID", "0")
                                 )
                            );
        
          //seasonSet format 
         //first array (brand sets)
         //second array (season sets)
         //third array (season attributes): ([season Nr], [ID], [total episode Nr])
         
        $seasonSet = array (array (array (1, "ID", 3),
                                   array (2, "ID", 2)
                                   )
                            );
 
 //
//   Script starts running at this point
//

// number of offers, first level array
$offerSetSize = count( $offerSet );
$seasonSetSize = count( $seasonSet );
  
        generateXmlHeader();
        
                
        // build a list of unique content IDs for each asset
        // this must be done in advance so that they can be listed
        // in offer:OfferType as <offer:ContentGroupRef elements
        
        //set counters to keep tracking content and movie uriId when the 2nd offer contains new movies
        $contentCountSofar = 0;
        $movieCountSofar = 0;
        $seriesCountSofar = 0;
        $sCountSofar = 0;
        $seriesCountSofar2=0;
        
        
        for ($iter=0; $iter<$offerSetSize; $iter++)
        {
            
            // number of movies in each offer
            $movieEachOfferSize = count( $offerSet[$iter] );
            
            for ($contentItem=0; $contentItem<$movieEachOfferSize; $contentItem++ )
            {
                
                //if the movie in the offer is not repeat (0), build the contenturiId ref in offer block
                if ( $offerSet[ $iter ][$contentItem][2]=="0")
                {
                    //get contentGroupUriId for each offer
                    $offerSet[ $iter ][$contentItem][1] = generateUniqueID( "ContentGroup", ($contentItem+$contentCountSofar), 0);
                    
                    //print ($offerSet[ $iter ][$contentItem][1]);
                }
                elseif ($offerSet[ $iter ][$contentItem][2]=="1")
                {
                    //check the movie title against the first array Offer, if in the first array offer, use the existing contentId
                    if (in_multiarray ($offerSet[ $iter ][$contentItem][0], $offerSet[0]))
                    {
                         $offerSet[ $iter ][$contentItem][1] = generateUniqueID( "ContentGroup", $contentItem, 0);
 
                    }
                  }
               }
           
            
           $contentCountSofar = $contentCountSofar+$movieEachOfferSize;
            
           //printf ("\ncontentCount %d\n", $contentCountSofar);
           
            //generateXmlOfferOfferType(generateUniqueID( "Offer", $iter, 0 ), generateUniqueID( "Category", $iter, 0 ), generateUniqueID( "Terms", $iter, 0 ), $offerSet, $iter);
            printf( "<!--offer %d start-->\n", $iter );
            generateXmlOfferOfferType(generateUniqueID( "Offer", $iter, 0 ), generateUniqueID( "Category", $iter, 0 ), generateUniqueID( "Terms", $iter, 0 ), $offerSet, $iter );
           // generateXmlOfferOfferType(generateUniqueID( "Offer", $iter, 0 ), generateUniqueID( "Category", $iter, 0 ), generateUniqueID( "Terms", $iter, 0 ), $offerSet, $iter, $contentCountSofar );
            printf( "<!--offer %d end-->\n", $iter );
            generateXmlTermTermsType(generateUniqueID( "Terms", $iter, 0 ));
            
            //2nd level array
            for ($item=0; $item<$movieEachOfferSize; $item++)
            {
                //if the movie in the offer is not repeat (0), build the content
                if ( $offerSet[ $iter ][$item][2]=="0")
                {
                       printf( "<!--movie %d start-->\n", ($item+$movieCountSofar) );
                       generateXmlTitleTitleType( generateUniqueID( "Title", ($item+$movieCountSofar), 0 ), $offerSet[ $iter ][$item][0]);
                       //generateXmlTitleTitleType( generateUniqueID( "Title", ($item), 0 ), $offerSet[ $iter ][$item][0]);
                       
                       generateXmlOfferContentGroupType( $offerSet[ $iter ][$item][1], generateUniqueID( "Title", ($item+$movieCountSofar), 0 ), generateUniqueID( "Movie", ($item+$movieCountSofar), 0 ), generateUniqueID( "Preview", ($item+$movieCountSofar), 0 ), generateUniqueID( "BoxCover", ($item+$movieCountSofar), 0 ) );
                      // generateXmlOfferContentGroupType( $offerSet[ $iter ][$item][1], generateUniqueID( "Title", $item+$contentCountSofar, 0 ), generateUniqueID( "Movie", $item+$contentCountSofar, 0 ), generateUniqueID( "Preview", $item+$contentCountSofar, 0 ), generateUniqueID( "BoxCover", $item+$contentCountSofar, 0 ) );
                         //get the last part of UriId 
                        $lastPartUriIdM = getLastPartUriId(generateUniqueID( "Movie", ($item+$movieCountSofar), 0 ));
                        $lastPartUriIdP = getLastPartUriId(generateUniqueID( "Preview", ($item+$movieCountSofar), 0 ));
                        $lastPartUriIdB = getLastPartUriId(generateUniqueID( "BoxCover", ($item+$movieCountSofar), 0 ));
                                   
                        generateXmlContentMovieType( generateUniqueID("Movie" , ($item+$movieCountSofar), 0 ), assetFileNameConvention($lastPartUriIdM, "movie", "1", "en-US") );
                        generateXmlContentPreviewType( generateUniqueID( "Preview", ($item+$movieCountSofar), 0 ), assetFileNameConvention($lastPartUriIdP, "preview", "1", "en-US"));
                        generateXmlContentBoxCoverType( generateUniqueID( "BoxCover", ($item+$movieCountSofar), 0 ), assetFileNameConvention($lastPartUriIdB, "boxcover", "1", "en-US") );
                        printf( "<!--movie %d end-->\n", ($item+$movieCountSofar) );
                 }

            };
            $movieCountSofar = $movieCountSofar+$movieEachOfferSize;
            
        };
                 
        generateXmlOfferCategoryType(generateUniqueID( "Category", 0, 0 ));
        
  //*******************************************************************************************************************     
  //below is for brand and series
  //There are more than 1 brand, brand type will repeat     
 // seasonSet array is added
  //********************************************************************************************************************
   
       for ($i=0; $i<$seasonSetSize; $i++)
        {
            $totalseasons = count ($seasonSet[$i]);
                                   
           //start to build brand type  
           //seriesRef inside brand type will match the number of seasons. E.g if there are two seasons, there should be two seriesRef 
                  
           for ($seasonCount=0; $seasonCount<$totalseasons; $seasonCount++)
           {
               //get id in seasonSet 
               $seasonSet[$i][$seasonCount] [1] = generateUniqueID( "Series", ($seasonCount+$sCountSofar), 0 );
           }
            
           // the seriesUriId will increment even if it is in different brand 
           $sCountSofar = $sCountSofar+$totalseasons;
            
           generateXmlIrdetoBrandType(generateUniqueID( "Brand", $i, 0 ), generateUniqueID( "Title", $i, 100 ), $seasonSet, $i, $totalseasons); 
           print "<!-- Brand's title --> \n";
           generateXmlTitleTitleType( generateUniqueID( "Title", $i, 100 ), $offerSet[0][0][0]); 
           
            //start to build serie Type
            //contentGroupRef should match the number of episodes in one Season (serie type in this case). E.g. if there are 3 episodes in one season, there should be 3 contentGroupRef
            
           for ($seasonCount=0; $seasonCount<$totalseasons; $seasonCount++)
           {
               //number of episodes in each season
               $episodeEachSeason = $seasonSet[$i][$seasonCount][2];
               
                for ($contentItem=0; $contentItem<$episodeEachSeason; $contentItem++ )
                {
                     //get each contentGroupUriId for each season
                    $offerSet[ $i ][$contentItem][1] = generateUniqueID( "ContentGroup", ($contentItem+$seriesCountSofar), 0); 
                 }
                 
                 //the contentGroupUriId will increment even if it is in different season
                 $seriesCountSofar = $seriesCountSofar+$episodeEachSeason;
                                                    
                 generateXmlIrdetoSeriesType(generateUniqueID( "Series", ($seasonCount+$seriesCountSofar2), 0 ), generateUniqueID( "Title", 0, 200 ), $offerSet, $i, $seasonSet[$i][$seasonCount][2]);  
                 
                 print "<!-- Series' title --> \n";
                 generateXmlTitleTitleType( generateUniqueID( "Title", ($seasonCount+$seriesCountSofar2), 200 ), $offerSet[0][0][0]);
            }
            //for seriesID increment for no matter new brand or new season
            $seriesCountSofar2 = $seriesCountSofar2+$totalseasons;
    
        };
          
        print"<!--End of Irdeto proprietary CableLabs extension--> \n";	
        
        generateXmlFooter();
   
?>
 
  
 
 
 
 
 
 
 
 
 
 
 
 
 
 