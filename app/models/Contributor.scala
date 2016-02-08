package models

case class Contributor(id: Long, name: String, email: String)

object Contributor {

  def all(): List[Contributor] = Nil

  def create(name: String, email: String) {}

  def delete(id: Long) {}

}