package backend

import scala.concurrent.{ ExecutionContext, Future }

import play.api.Play.current
import play.api.libs.json.{ JsObject, Json }

import reactivemongo.bson.BSONDocument
import reactivemongo.api.commands.WriteResult

trait PostRepo {
  def find()(implicit ec: ExecutionContext): Future[List[JsObject]]

  def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
}
