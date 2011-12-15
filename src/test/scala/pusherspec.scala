package dispatch.pusher

import org.specs2.mutable._
import dispatch._
import Pusher._
import scala.collection.mutable.{ OpenHashMap => OHM }

class PusherSpec extends Specification {
  "Pusher request" should {
    "send data" in {
      val key = System.getenv("PUSHER_KEY")
      val secret = System.getenv("PUSHER_SECRET")
      val apiId = System.getenv("PUSHER_APIID")
      
      key must not be empty
      secret must not be empty
      apiId must not be empty
      
      val http = new Http
      val pusher = PusherRequest(key, secret)_
      val channel = pusher(apiId, "test_channel")
      val push: Request = channel(OHM("name" -> "my_event"))("hello world")

      val status: Int = http((push >:> identity) {
        case (status, _, _, _) => status
      })
      status must_== 202
    }
  }
}