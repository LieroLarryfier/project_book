package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class WorkStep(id: Long, header: String, description: String, progress: Int, pictures: String)

object WorkStep {

  val workStep = {
      get[Long]("id") ~
      get[String]("header") ~
      get[String]("description") ~
      get[Int]("progress") ~
      get[String]("pictures") map {
      case id~header~description~progress~pictures
      => WorkStep(id, header, description, progress, pictures)
    }
  }

  def all(): List[WorkStep] = DB.withConnection { implicit c =>
    SQL("select * from workStep").as(workStep *)
  }

  def getById(id: Long): WorkStep = DB.withConnection { implicit c =>
    SQL("select * from workStep where id={id}").on('id -> id)as(workStep single)
  }

  def getWorkSteps(id: Long): List[WorkStep] = DB.withConnection { implicit c =>
    SQL("select * from workStep where projectId={id}").on('id -> id).as(workStep *)
  }

  def create(header: String, description: String, progress: Int, pictures: String) {}

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from workStep where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}