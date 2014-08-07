package com.ebusiello.twitter.fetcher

import play.api.Play
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

trait TwitterConnection {

  /**
   * Returns a twitter connection.
   *
   * @return Twitter
   */
  def getConnection: Twitter = {
    val cb = new ConfigurationBuilder()

    cb
      .setDebugEnabled(true)
      .setUseSSL(true)
      .setOAuthConsumerKey(Play.current.configuration.getString("twitter.key").get)
      .setOAuthConsumerSecret(Play.current.configuration.getString("twitter.secret").get)
      .setOAuthAccessToken(Play.current.configuration.getString("twitter.access.token").get)
      .setOAuthAccessTokenSecret(Play.current.configuration.getString("twitter.token.secret").get)

    new TwitterFactory(cb.build()).getInstance()
  }

}