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

      //setting pusher credentials
      val pusher = PusherRequest(key, secret)_
      //specify
      val channel = pusher(apiId, "test_channel")
      //specify the event to trigger and the data to be sent ... socket id is optional
      val pushToMyEvent: Request = channel("my_event", "hello world", None)
      val pushToYourEvent: Request = channel("your_event", "hello world again", None)

      val http = new Http
      val requests = List(pushToMyEvent, pushToYourEvent)
      requests.map { req =>
        val status :Int = http(( req >:> identity) {
          case (status, _, _, _) => status
        })
        status must_==202
      }
    }
  }
}