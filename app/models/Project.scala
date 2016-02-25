package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Project(id: Long, label: String, categories: String, description: String, contributors: String, tags: String)

object Project {

  val project = {
      get[Long]("id") ~
      get[String]("label") ~
      get[String]("categories") ~
      get[String]("description") ~
      get[String]("contributors") ~
      get[String]("tags") map {
      case id~label~categories~description~contributors~tags
        => Project(id, label, categories, description, contributors, tags)
    }
  }

  def all(): List[Project] = DB.withConnection { implicit c =>
    SQL("select * from project").as(project *)
  }

  def getById(id: Long): Project = DB.withConnection { implicit c =>
    SQL("select * from project where id={id}").on('id -> id).as(project single)
  }

  def create(label: String, categories: String, description: String, contributors: String, tags: String) {
    DB.withConnection { implicit c =>
      SQL("insert into project (label, categories, description, contributors, tags) values ({label}, {categories}, {description}, {contributors}, {tags})").on(
        'label -> label, 'categories -> categories, 'description -> description, 'contributors -> contributors, 'tags -> tags
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from project where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  def addTodo(id: Long, todo: Todo) {
    DB.withConnection { implicit c =>
      SQL("insert into todo (priority, name, description, projectId) values ({priority}, {name}, {description}, {projectId})").on(
        'priority -> todo.priority, 'name -> todo.name, 'description -> todo.description, 'projectId -> id
      ).executeUpdate()
    }
  }

  def addWorkStep(id: Long, workStep: WorkStep) {
    DB.withConnection { implicit c =>
      SQL("insert into workstep (header, description, progress, pictures, projectId) values ({header}, {description}, {progress}, {pictures}, {projectId})").on(
        'header -> workStep.header, 'description -> workStep.description, 'progress -> workStep.progress, 'pictures -> workStep.pictures, 'projectId -> id
      ).executeUpdate()
    }
  }

  def getProgressSum(id: Long): Int = DB.withConnection { implicit c =>
    SQL("select COALESCE(SUM(progress),0) from workStep where projectId={id}").on('id -> id).as(scalar[Int] single)
  }

  def getTodoCount(id: Long): Int = DB.withConnection { implicit c =>
    SQL("select COUNT() from todo where projectId={id}").on('id -> id).as(scalar[Int] single)
  }

  def getWorkSteps(id: Long): List[WorkStep] = {
    WorkStep.getWorkSteps(id)
  }

  def getTodos(id: Long): List[Todo] = {
    Todo.getTodos(id)
  }

}