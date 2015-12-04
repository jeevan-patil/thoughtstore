package service

import scala.concurrent.Future

import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

trait PostService {
  def find(): Future[List[JsObject]]

  def update(selector: BSONDocument, update: BSONDocument): Future[WriteResult]

  def remove(document: BSONDocument): Future[WriteResult]

  def save(document: BSONDocument): Future[WriteResult]
}