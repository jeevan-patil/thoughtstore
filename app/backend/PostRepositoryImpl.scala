package backend

import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import javax.inject.Inject
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.{ ReactiveMongoApi, ReactiveMongoComponents }
import play.modules.reactivemongo.json.JsObjectDocumentWriter
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

class PostRepositoryImpl @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends PostRepo with ReactiveMongoComponents {
  // BSON-JSON conversions
  import play.modules.reactivemongo.json._, ImplicitBSONHandlers._

  protected def collection =
    reactiveMongoApi.db.collection[JSONCollection]("posts")

  def find()(implicit ec: ExecutionContext): Future[List[JsObject]] =
    collection.find(Json.obj()).cursor[JsObject].collect[List]()

  def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = collection.update(selector, update)

  def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = collection.remove(document)

  def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = collection.save(document)
}