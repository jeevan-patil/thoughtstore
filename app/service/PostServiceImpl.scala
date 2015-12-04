package service

import scala.concurrent.Future

import backend.PostRepositoryImpl
import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

class PostServiceImpl @Inject() (val postRepo: PostRepositoryImpl) extends PostService {
  
  def find(): Future[List[JsObject]] = postRepo.find()

  def update(selector: BSONDocument, update: BSONDocument): Future[WriteResult] = postRepo.update(selector, update)

  def remove(document: BSONDocument): Future[WriteResult] = postRepo.remove(document)

  def save(document: BSONDocument): Future[WriteResult] = postRepo.save(document)
}