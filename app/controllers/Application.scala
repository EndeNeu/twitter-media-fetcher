package controllers

import com.ebusiello.cats.controller.TwitterController
import com.ebusiello.cats.postgres.schema.twitter.TwitterSchema.TwitterCatsRow
import play.api.mvc._

object Application extends Controller {

  def index(page: Int = 0) = Action {
    val controller = new TwitterController()
    val result: List[TwitterCatsRow] = controller.getAnimals(page)
    Ok(views.html.animals(result))
  }

  def infiniteScroll(page: Int = 0) = Action {
    val controller = new TwitterController()
    val result: List[TwitterCatsRow] = controller.getAnimals(page)
    if (result.isEmpty) Ok("")
    else Ok(views.html.animals_list(result))
  }

}