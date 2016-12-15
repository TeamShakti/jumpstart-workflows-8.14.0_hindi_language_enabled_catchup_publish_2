Movie Meta Data XML Generation PHP Scripts

It has 3 php scripts. It has the structure to build a meta data xml file with multiple offers. It is flexible and light weighed. It can be extended to support different scenarios. 

1. xmlFunctions.php contains all the functions used to generate each data types. 
   A XML file is broken down into blocks of data types, e.g. Offer Type, Title Type, Content Type. There is a function for each data type generation.
   
2. multiOffers_differContent.php

It will generate multiple offers, each offer has different movies (no duplicates)

E.g. Offer 1 contains : "Game of Thrones episode 1",
                        "Game of Thrones episode 2", 
                      
     Offer 2 contains:  "Kungfu Panda", 
                        "Finding Nemo", 
	
3. 	multiOffers_refContent.php

It will generate multiple offers, the 2nd and 3rd offer have duplicate movies.

E.g. Offer 1 contains : "Game of Thrones episode 1",
                        "Game of Thrones episode 2", 
						"Game of Thrones episode 3",
						"Game of Thrones episode 4",
                      
     Offer 2 contains:  "Game of Thrones episode 1",
                        "Game of Thrones episode 2",
						
	 Offer 2 contains:  "Game of Thrones episode 3",
						
For the duplicates, there is no new content will be generated, it only has contentUriId ref to point to the existing contents.

						
   