package models

case class Category(id: Long, label: String, description: String)

object Category {

  def all(): List[Category] = Nil

  def create(label: String, description: String) {}

  def delete(id: Long) {}

}