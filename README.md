Our search engine data structure can be built in two ways:

1 - Process all files in directory database/html folder, generate txt files and write them in database/txt folder. Process all text files in database/txt folder and insert words in TST.

or

2 - Load TST directly from database/tst files.


If we want to build our data structure from html files, we need to copy the html files to database/html and instantiate the search engine using the following command:

`SearchEngine se = new SearchEngine(true);`

If we want to build our data structure without processing html files, we can instantiate the search engine using the following command:

`SearchEngine se = new SearchEngine(false);`

Note1: We need to build the search engine using method #1 at least once to generate database/tst files.

Note2: We will submit our project to blackboard with only 100 html files to save space.
