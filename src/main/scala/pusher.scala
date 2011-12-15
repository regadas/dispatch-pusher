package dispatch.pusher

import dispatch._
import dispatch.Request._
import scala.collection.mutable.{ OpenHashMap => OHM }

case class PusherRequest(appId: String, channel: String, key: String, secret: String, authVersion: String = "1.0")(extraParams: OHM[String, String] = OHM())(val data: String) {
  import Security._

  lazy val innerParams = List[(String, String)](
    'auth_key.name -> key,
    'auth_timestamp.name -> (System.currentTimeMillis / 1000).toString,
    'auth_version.name -> authVersion,
    'body_md5.name -> Security.md5(data))
  lazy val params = innerParams ++ extraParams

  def asMap()(implicit req: Request) = {
    val toBeHashed = {
      val format = (params: List[(String, String)]) =>
        (params.map { e => "%s=%s" format (e._1, e._2) }).mkString("&")

      List(req.method, req.path, format(params)).mkString("\n")
    }

    val hash: String = Security.hmacSHA256(toBeHashed, secret.getBytes)
    (('auth_signature.name, hash) :: params).toMap
  }
}

object PusherRequest {
  def apply(key: String, secret: String)(appId: String, channel: String)(params: OHM[String, String])(data: String) =
    new PusherRequest(appId, channel, key, secret)(params)(data)
}

case object Pusher {
  val host = :/("api.pusherapp.com")
  val svc = (id: String, channel: String) => host / "apps" / id / "channels" / channel / "events"

  implicit def PusherRequest2Request(pr: PusherRequest) = {
    implicit val req = svc(pr.appId, pr.channel).POST
    req <<? pr.asMap << pr.data
  }
}