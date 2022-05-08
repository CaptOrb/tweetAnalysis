# 3rdYearProject
COMP30880 Software Engineering Project by Conor Barry, Jane O'Brien and Tom Higgins

Team NAME: ToJaCo

RUNNING FROM JAR

- java -jar tojaco.jar to use the bundled in config file.
- To use your own config file use java -jar tojaco.jar {pathtoconfigfile}

- If using a custom config file, note that a sample configuration file is given in config/default_config.txt. Use that file as a template for your config file
  and put in your API KEYS

If running from source ensure to add the Twitter4J library to the classpath.

# Answers to Sprint 4 questions 

- The tasks were divided evenly between the team members. See commit history for a clearer idea of who carried out which tasks, but pair work was often carried out with just one person committing the finished result.

3c)
Answers obtained by running the jar file and selecting option 4 in the menu

- percentage of users who have stances: 95.3451693% 
- percentage of users who don't have stances: 4.6548307%

- percentage of users who have anti vax stances: 58.892816%
- percentage of users who have pro vax stances: 41.104653%

4) 
- Our stance assignment algorithm correctly assigns stances to 95% of users in the data set 100% of the time, in terms of labelling someone as "pro vax" with a positive number less than 1000, or "anti vax" with a negative number greater than -1000 (which was checked and confirmed in part 4a and 4b, where each of the users who were assigned a stance produced accurate results). The weight of the stance that each user received may not be completely accurate, as certain users who seemed to only retweet pro vax evangelists might only have a stance of 600 or 700, when they deserve to have a stance of 1000, and the same results appeared for anti vaxxers deserving of a stance of -1000 but were actually assigned a stance of around -800.

4a & 4b) 
- see 100Users.txt

4c) 
- Positive Stances: 40/40 = 1 ( There are 41 pro vax users in total, @Lukedutchh was not assigned a stance, this user is retweeted by a few users but does not retweet any other users in the data set)
- Negative Stances: 59/59 = 1


4d) Based on the precision scores given above it doesn't appear that there is an apparent bias in our algorithm. 

# Answers to Sprint 5 questions

- The tasks were divided evenly between the team members. See commit history for a clearer idea of who carried out which tasks, but pair work was often carried out with just one person committing the finished result.

3c)

- percentage of users who have stances: 98.90274%
- percentage of users who don't have stances: 1.0972595%

- percentage of users who have anti vax stances: 59.277313%
- percentage of users who have pro vax stances: 40.722687%

For Sprint 4 the percentage of users who have stances was  95.3451693% so adding stances to hashtags and bootstrapping the results to update the user stances results in a 3.5604107% increase in the coverage of users in the graph.
Interestingly, the ratio of Pro/Anti vax stance assignments changes very little compared to Sprint 4, with the percentages only varying by +- 2%

4a) After calculating the stances for the hashtags, and setting all user stances back to 0/hasStance==false, and by percolating stances through the hashtag graph, the percentage of pro vax and anti vax is almost the same:

Calculating stances using retweeted users only
Percentage positive stances: 41.108273%
Percentage negative stance: 58.89173%

Calculating stances using hashtags only
ercentage positive stances using ONLY hashtags: 37.95751%
Percentage negative stance using ONLY hashtags: 62.04249%


4b) The two perspectives agree with each other for roughly 98% of the stances, and disagree with each other for roughly 2% of the stances based on the results from 4a.

4c) Roughly 16% of users who use 10 or more different hashtags (based solely off of hashtag based stance calculation) are pro vax, whilst roughly 35% of users who retweet 10 or more different users are pro vax. The percentage of pro vax users for hashtags is quite low because there are only 20657 users who use 10 different hashtags or more. 

# Answers to Sprint 6 questions

- All tasks were completed successfully, except we didn't use interfaces as we weren't sure how best to utilise them.
- The tasks were divided evenly between the team members. See commit history for a clearer idea of who carried out which tasks, but pair work was often carried out with just one person committing the finished result.
- Best way to use the graphs: All graphs are outputted to the graph directory e.g. hashTagsToWords.txt contains a mapping from hashtags to the tag splits, hashtagSummaries.txt contains a mapping from hashtag to its summary and lexiconGraph.txt contains the lexicongraph. 
- Also note that if a hashtag contained a word that was not in CamelCase /  not in the labeled tag elements.txt file we did not split up that hashtag because the Sprint description says to split a word "provided each part is in the lexicon"

# Answers to Sprint 7 questions

- The tasks were divided evenly between the team members. See commit history for a clearer idea of who carried out which tasks, but pair work was often carried out with just one person committing the finished result.
- Our code calculates and outputs the significant conditional probabilities that take the form P( pro/anti | ref:x ), and we plan to expand it to also calculate for P( ref:x | pro/anti )
- some significant probabilities we found were:

| Property      |    Probability     |   Z-Score |
| ----------- |:------------------:|----------:|
| pro : rightwing     |      0.419196      |  3.503769 |
| anti : -ref:tony_holohan       |      1.000000      |  6.049106 |
|  anti : -ref:media    |     0.867737       |  3.598639 |
| anti : ref:fauci     | 0.985401  |  6.513596 |
| anti : -ref:pfizer    |  0.976415  |  5.799930 |
| anti : -ref:corona     |  0.832000  |  2.349956 |

