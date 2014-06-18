package com.ebusiello.cats.connection

import play.api.Play
import twitter4j.{Twitter, TwitterFactory}
import twitter4j.conf.ConfigurationBuilder

class TwitterConnection {

  def getConnection(): Twitter = {
    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setUseSSL(true)
      .setOAuthConsumerKey(Play.current.configuration.getString("twitter.key").get)
      .setOAuthConsumerSecret(Play.current.configuration.getString("twitter.secret").get)
      .setOAuthAccessToken(Play.current.configuration.getString("twitter.access.token").get)
      .setOAuthAccessTokenSecret(Play.current.configuration.getString("twitter.token.secret").get)
    val tf = new TwitterFactory(cb.build())
    tf.getInstance()
  }

}
