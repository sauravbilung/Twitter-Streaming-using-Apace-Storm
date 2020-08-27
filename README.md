# Twitter-Streaming-using-Apace-Storm
This project streams the tweets from twitter which contains some specific keyword or a term.

### Requirements  
1) JDK 1.8
2) Any JAVA IDE
3) A Twitter developer account with a twitter app created for the task with all the credentials.
Link : ```https://developer.twitter.com/apps```

### Creating a Twitter application

To obtain the required keys, visit https://apps.twitter.com/ and Create a New App. Fill in an application name & description & web site and accept the developer     aggreement. Click on Create my access token and populate a file twitter-source.properties with consumer key & secret and the access token & token secret using       the example file to begin with.

### Steps for running the project  
1) Import the project in any IDE of your choice and run the TopologyMain class.
2) Or, Create the jar and the run TopologyMain class.
3) The Application is configured to be run in a Local Storm Cluster.
4) The output is very verbose so you have to search for it.

