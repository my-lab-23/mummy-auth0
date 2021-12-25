package models

import argonaut._, Argonaut._
import sys.process._

//

case class MyType(val access_token: String,
                  val id_token: String,
                  val scope: String,
                  val expires_in: Int,
                  val token_type: String)

object MyType {
   implicit val createCodecJson: CodecJson[MyType] = CodecJson.casecodec5(apply, unapply)(
      "access_token",
      "id_token",
      "scope",
      "expires_in",
      "token_type")
}

//

object MyAuth {
   def take_access_token(code: String): String = {

      var cmd = s"""curl --request POST
                   |--url '???.auth0.com/oauth/token'
                   |--header 'content-type: application/x-www-form-urlencoded'
                   |--data grant_type=authorization_code
                   |--data client_id=???
                   |--data client_secret=???
                   |--data code=$code
                   |--data redirect_uri=???/callback""".stripMargin

      val json = cmd.!!
      val x = Parse.decode[MyType](json)
      var access_token = ""
      for(y <- x) { access_token = y.access_token }
      return access_token
   }
}
