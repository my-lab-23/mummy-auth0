package controllers

import models.MyAuth
import my_utility.MyUtility

import javax.inject._
import play.api._
import play.api.mvc._

import sys.process._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  val redirect_url = "???.auth0.com/authorize?" +
     "response_type=code&" +
     "client_id=???&" +
     "client_secret=???&" +
     "redirect_uri=???/callback&scope=openid email&"

  def index() = Action { implicit request: Request[AnyContent] =>

     val access_token = request.session.get("access_token")

     if (access_token.isDefined) {

        val cmd =
           s"""curl --request GET
              |--url '???.auth0.com/userinfo'
              |--header 'Authorization: Bearer ${access_token.get}'
              |--header 'Content-Type: application/json'""".stripMargin

        val check = cmd.!!
        val ok = check.contains("???@gmail.com")

        if (ok) { Ok(views.html.index()) }
        else { Ok(views.html.forbidden()) }

     } else {

        Redirect(redirect_url)

     }
  }

  def take_access_token(code: String) = Action { implicit request: Request[AnyContent] =>
     val access_token = MyAuth.take_access_token(code)
     Redirect("/").withSession("access_token" -> access_token)
  }

   def android() = Action { implicit request: Request[AnyContent] =>
      try {
         val headers = request.headers
         val access_token = headers("access_token")
         var cmd =
            s"""curl --request GET
               |--url '???.auth0.com/userinfo'
               |--header 'Authorization: Bearer $access_token'
               |--header 'Content-Type: application/json'""".stripMargin
         val check = cmd.!!
         val ok = check.contains("???@gmail.com")
         println(ok)
         if (ok == true) {
            Ok(views.html.index())
         } else {
            Ok(views.html.forbidden())
         }
      } catch {
         case e: RuntimeException => Ok(views.html.forbidden())
      }
   }

  def save = Action { request: Request[AnyContent] =>
    val body = request.body
    val data_path = sys.env("MY_DATA_PATH")
    MyUtility.write_file(body.asText.get, s"$data_path/mummy.txt", true)
    Ok("Saved.")
  }

    def test = Action { request: Request[AnyContent] =>
       Ok("0kill")
    }
}
