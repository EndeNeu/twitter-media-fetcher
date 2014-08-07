# Twitter Media Fetcher #

Simple play application that uses [twitter4j](http://twitter4j.org/en/) and [Slick](http://slick.typesafe.com/) to download media form some tweets.

### Setup: ###

Your application.conf file should contain this parameters:

* twitter.key = "XXXX"
* twitter.secret = "XXXX"
* twitter.access.token = "XXXX"
* twitter.token.secret = "XXXX"
* twitter.query = "#cat+OR+#cats"

Where the query parameter is the request executed on the twitter API.

### Usage: ###

To run the import go to the project root folder with, start sbt and run "runMain com.ebusiello.twitter.fetcher.TwitterJob ."

### Note: ###

The app is setted to save only picture urls, to change this behaviour you will have to change the Slick schema and the TwitterModel class.