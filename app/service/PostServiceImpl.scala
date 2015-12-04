package service

import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import javax.inject.Inject
import play.api.libs.json.JsObject
import play.modules.reactivemongo.{ ReactiveMongoComponents, ReactiveMongoApi }
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import backend.PostRepositoryImpl

class PostServiceImpl @Inject() (val postRepo: PostRepositoryImpl) extends PostService {
  
  def find()(implicit ec: ExecutionContext): Future[List[JsObject]] = postRepo.find()

  def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.update(selector, update)

  def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.remove(document)

  def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = postRepo.save(document)
}