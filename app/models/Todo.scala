package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import java.util.Date

case class Todo(id: Long, priority: Int, name: String, description: String, doneAt: String)

/**
 * Created by LieroLarryfier on 23.02.2016.
 */
object Todo {

  val todo = {
      get[Long]("id") ~
      get[Int]("priority") ~
      get[String]("name") ~
      get[String]("description") ~
      get[Option[String]]("doneAt") map {
      case id~priority~name~description~doneAt
      => { val doneTime =
          doneAt match {
            case Some(value) => value
            case None => "no"
          }
        Todo(id, priority, name, description, doneTime)
      }
    }
  }

  def all(): List[Todo] = DB.withConnection { implicit c =>
    SQL("select * from todo").as(todo *)
  }

  def getById(id: Long): Todo = DB.withConnection { implicit c =>
    SQL("select * from todo where id={id}").on('id -> id)as(todo single)
  }

  def getTodos(id: Long): List[Todo] = DB.withConnection { implicit c =>
    SQL("select * from todo where projectId={id}").on('id -> id).as(todo *)
  }

  def create(priority: String, name: String, description: Int) {}

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from todo where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  def check(id: Long): Unit = {
    DB.withConnection { implicit c =>
      SQL("update todo set doneAt = datetime() where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}
