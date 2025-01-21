package models

import play.api.libs.json._

case class Estudiante(
    id: Option[Long],
    nombre: String,
    edad: Int,
    sexo: String,
    correo: String,
    telefono: String,
    direccion: String,
    fechaNacimiento: String,
    carrera: String
)

object Estudiante {
  implicit val estudianteFormat: OFormat[Estudiante] = Json.format[Estudiante]
}
