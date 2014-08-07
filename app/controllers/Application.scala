package controllers

import com.ebusiello.twitter.fetcher.TwitterController
import com.ebusiello.twitter.fetcher.postgres.schema.twitter.TwitterSchema.TweetsRow
import play.api.mvc._

object Application extends Controller {

  def index(page: Int = 0) = Action {
    val controller = new TwitterController()
    val result: List[TweetsRow] = controller.getAnimals(page)
    Ok(views.html.animals(result))
  }

  def infiniteScroll(page: Int = 0) = Action {
    val controller = new TwitterController()
    val result: List[TweetsRow] = controller.getAnimals(page)
    if (result.isEmpty) Ok("")
    else Ok(views.html.animals_list(result))
  }

}