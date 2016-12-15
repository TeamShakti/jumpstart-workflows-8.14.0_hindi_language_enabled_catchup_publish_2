<?php

        //3 offers, the last 2 offers use the existing content group ref
        
        include 'xmlFunctions.php';
       
        //3 dimontinal array, 
        //first array (offer set): different offers, 
        //second array (movie set): movies in each offer, 
        //third array (movie attribute): movie attributes for each movie. Title, ID, 0 means origial, 1 means repeat.
        $offerSet = array (array( array( "Game of Thrones1", "ID", "0" ),
                                  array( "Game of Thrones2", "ID", "0" ),
                                  array( "Game of Thrones3", "ID", "0" ),
                                  array( "Game of Thrones4", "ID", "0" )
                                 ),
                            array(array( "Game of Thrones1", "ID", "1" ),
                                  array( "Game of Thrones2", "ID", "1" )                     
                                  ),
                            array(array( "Game of Thrones3", "ID", "1" )
                                  )
                            );
 //
//      Script starts running at this point
//      
        // number of offers, first level array
        $offerSetSize=count( $offerSet );
 
        generateXmlHeader();
        
                
        // build a list of unique content IDs for each asset
        // this must be done in advance so that they can be listed
        // in offer:OfferType as <offer:ContentGroupRef elements
        
       // $contentCountSofar = 0;
        
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
                    $offerSet[ $iter ][$contentItem][1] = generateUniqueID( "ContentGroup", $contentItem, 0);
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
           
            //$contentCountSofar = $contentCountSofar+$movieEachOfferSize;   
                    
            printf( "<!--offer %d start-->\n", $iter );
            generateXmlOfferOfferType(generateUniqueID( "Offer", $iter, 0 ), generateUniqueID( "Category", $iter, 0 ), generateUniqueID( "Terms", $iter, 0 ), $offerSet, $iter);
            printf( "<!--offer %d end-->\n", $iter );
            generateXmlTermTermsType(generateUniqueID( "Terms", $iter, 0 ));
            
            //2nd level array
            for ($item=0; $item<$movieEachOfferSize; $item++)
            {
                //if the movie in the offer is not repeat (0), build the content
                if ( $offerSet[ $iter ][$item][2]=="0")
                {
                    printf( "<!--movie %d start-->\n", $item );
                    generateXmlTitleTitleType( generateUniqueID( "Title", $item, 0 ), $offerSet[ $iter ][$item][0]);
                    
                    generateXmlOfferContentGroupType( $offerSet[ $iter ][$item][1], generateUniqueID( "Title", $item, 0 ), generateUniqueID( "Movie", $item, 0 ), generateUniqueID( "Preview", $item, 0 ), generateUniqueID( "BoxCover", $item, 0 ) );
            
                    //get the last part of UriId 
                    $lastPartUriIdM = getLastPartUriId(generateUniqueID( "Movie", $item, 0 ));
                    $lastPartUriIdP = getLastPartUriId(generateUniqueID( "Preview", $item, 0 ));
                    $lastPartUriIdB = getLastPartUriId(generateUniqueID( "BoxCover", $item, 0 ));
                               
                    generateXmlContentMovieType( generateUniqueID("Movie" , $item, 0 ), assetFileNameConvention($lastPartUriIdM, "movie", "1", "en-US") );
                    generateXmlContentPreviewType( generateUniqueID( "Preview", $item, 0 ), assetFileNameConvention($lastPartUriIdP, "preview", "1", "en-US"));
                    generateXmlContentBoxCoverType( generateUniqueID( "BoxCover", $item, 0 ), assetFileNameConvention($lastPartUriIdB, "boxcover", "1", "en-US") );
                    printf( "<!--movie %d end-->\n", $item );
               
                 }
            }
         
        };
                 
        generateXmlOfferCategoryType(generateUniqueID( "Category", 0, 0 ));
        
        print"<!--Here follow Irdeto proprietary extensions to mark up the Brand and Series--> \n";			
	
	    //title inside irdeto proprietary tags, start from IRDET201300100000000
        generateXmlIrdetoSeriesType(generateUniqueID( "Brand", 0, 0 ), generateUniqueID( "Title", 0, 100 ), $offerSet[0][0][1]);  
        
        //new title for the series, the titleUriId should be the same as TitleRef uriId in Series
        generateXmlTitleTitleType( generateUniqueID( "Title", 0, 100 ), $offerSet[0][0][0]); 
        
        print"<!--End of Irdeto proprietary CableLabs extension--> \n";	
        
        generateXmlFooter();
   
?>
