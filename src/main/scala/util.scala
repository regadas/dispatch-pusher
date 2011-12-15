package dispatch.pusher

object Security {
  import java.security.MessageDigest
  import javax.crypto.Mac
  import javax.crypto.spec.SecretKeySpec
  
  def md5(str: String) = MessageDigest.getInstance("MD5").digest(str.getBytes)
  def hmacSHA256(str: String, key: Array[Byte]) = {
    val algorithm = "HmacSHA256"
    val k = new SecretKeySpec(key, 0, key.length, algorithm); 
    val mac = Mac.getInstance(algorithm)
    mac.init(k)
    mac.doFinal(str.getBytes)
  }

  implicit def ArrayToHexString(data: Array[Byte]) = data.map("%02x" format _).mkString
}