import java.io.File

import com.ebusiello.cats.model.TwitterModel
import play.api.{Play, Mode, DefaultApplication}
import play.api.db.slick._
import play.api.Play.current

object Main {

  def main(args: Array[String]) {
    val application = new DefaultApplication(new File(args(0)), this.getClass.getClassLoader, null, Mode.Prod)
    Play.start(application)
    DB.withSession {
      implicit s: Session => {
        println(new TwitterModel().deleteOldTweets(10))
      }
    }
    Play.stop()
  }

}
