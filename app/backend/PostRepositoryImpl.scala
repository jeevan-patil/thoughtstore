package backend

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{
  MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

import play.api.libs.json.{ JsObject, Json }
import reactivemongo.bson.BSONDocument
import reactivemongo.api.commands.WriteResult

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