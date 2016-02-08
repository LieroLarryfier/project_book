package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Project
import models.WorkStep

class Application extends Controller {
  
  def index = Action {
    Redirect(routes.Application.projects)
  }

  val projectForm = Form (
  mapping (
    "label" -> nonEmptyText,
    "categories" -> text,
    "description" -> text,
    "contributors" -> text,
    "tags" -> text
  ) ((label, categories, description , contributors, tags) => Project(0,label,categories,description,contributors,tags))
    ((project: Project) => Some(project.label, project.categories, project.description, project.contributors, project.tags))
  )

  def projects = Action {
    Ok(views.html.index(Project.all(), projectForm))
  }

  def manageProjects = Action {
    Ok(views.html.manageProjects(Project.all(), projectForm))
  }

  def projectDetails(id: Long) = Action {
    Ok(views.html.projectDetails(Project.getById(id), workStepForm))
  }

  def addProject = Action { implicit request =>
    projectForm.bindFromRequest.fold(
      errors => BadRequest(views.html.manageProjects(Project.all(), errors)),
      project => {
        Project.create(project.label, project.categories, project.description, project.contributors, project.tags)
        Redirect(routes.Application.manageProjects)
      }
    )
  }

  def deleteProject(id: Long) = Action {
    Project.delete(id)
    Redirect(routes.Application.manageProjects)
  }

  val workStepForm = Form (
    mapping (
      "header" -> nonEmptyText,
      "description" -> nonEmptyText,
      "progress" -> number,
      "pictures" -> text
    ) ((header, description, progress, pictures) => WorkStep(0,header,description,progress,pictures))
      ((workStep: WorkStep) => Some(workStep.header, workStep.description, workStep.progress, workStep.pictures))
  )

  def addWorkStep(id: Long) = Action {
    Ok(views.html.addWorkStep(Project.getById(id), workStepForm))
  }

  def addWorkStepToProject(id: Long) = Action { implicit request =>
    workStepForm.bindFromRequest.fold(
      errors => BadRequest(views.html.addWorkStep(Project.getById(id), errors)),
      workStep => {
        Project.addWorkStep(id, workStep)
        Redirect(routes.Application.manageProjects)
      }
    )
  }

  def deleteWorkStep(id: Long) = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    val workStepId: Long = toLong(request.body.get("workStepId").map(_.head).getOrElse("")).getOrElse(0)
    WorkStep.delete(workStepId)
    Redirect(routes.Application.projectDetails(id))
  }

  def toLong(s: String): Option[Long] = {
    try {
      Some(s.toLong)
    } catch {
      case e: Exception => None
    }
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      picture.ref.moveTo(new File("E:\\Projekte\\project_book\\public\\images\\" + filename))
      Redirect(routes.Application.manageProjects)
    }.getOrElse {
      Redirect(routes.Application.projects).flashing(
        "error" -> "Missing file"
      )
    }
  }

}