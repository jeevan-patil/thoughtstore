package service

import javax.inject.Inject
import play.api.libs.json.{ JsObject, Json }
import scala.concurrent.{ ExecutionContext, Future }
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{ BSONObjectID, BSONDocument }
import play.modules.reactivemongo.{
  MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

class PostServiceImpl @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends PostService with ReactiveMongoComponents {
  
  var postRepo = new backend.PostRepositoryImpl(reactiveMongoApi)
  
  def find()(implicit ec: ExecutionContext): Future[List[JsObject]] = postRepo.find()

  def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.update(selector, update)

  def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.remove(document)

  def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.save(document)
}