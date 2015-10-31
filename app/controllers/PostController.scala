package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{ Action, BodyParsers, Call, Controller, Result }

import reactivemongo.bson.{ BSONObjectID, BSONDocument }
import reactivemongo.core.actors.Exceptions.PrimaryUnavailableException
import reactivemongo.api.commands.WriteResult

import play.modules.reactivemongo.{
  MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

class PostController @Inject() (val reactiveMongoApi: ReactiveMongoApi) 
      extends Controller with MongoController with ReactiveMongoComponents {

  import controllers.PostFields._

  var postService = new service.PostServiceImpl(reactiveMongoApi)

  def list = Action.async {implicit request =>
    postService.find()
      .map(posts => Ok(Json.toJson(posts.reverse)))
      .recover {case PrimaryUnavailableException => InternalServerError("Please install MongoDB")}
  }

  def like(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val value = (request.body \ Favorite).as[Boolean]
    postService.update(BSONDocument(Id -> BSONObjectID(id)), BSONDocument("$set" -> BSONDocument(Favorite -> value)))
      .map(le => Ok(Json.obj("success" -> le.ok)))
  }

  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val value = (request.body \ Text).as[String]
    postService.update(BSONDocument(Id -> BSONObjectID(id)), BSONDocument("$set" -> BSONDocument(Text -> value)))
      .map(le => Ok(Json.obj("success" -> le.ok)))
  }

  def delete(id: String) = Action.async {
    postService.remove(BSONDocument(Id -> BSONObjectID(id)))
      .map(le => RedirectAfterPost(le))
  }

  def add = Action.async(BodyParsers.parse.json) { implicit request =>
    val username = (request.body \ Username).as[String]
    val text = (request.body \ Text).as[String]
    val avatar = (request.body \ Avatar).as[String]
    postService.save(BSONDocument(
      Text -> text,
      Username -> username,
      Avatar -> avatar,
      Favorite -> false
    )).map(le => Redirect("/api/posts", 200))
  }

  private def RedirectAfterPost(result: WriteResult): Result =
    if (result.inError) InternalServerError(result.toString)
    else Redirect("/api/posts", 200)

}

object PostFields {
  val Id = "_id"
  val Text = "text"
  val Username = "username"
  val Avatar = "avatar"
  val Favorite = "favorite"
}
