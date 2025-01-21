package controllers

import javax.inject._
import models.Estudiante
import play.api.libs.json._
import play.api.mvc._
import services.EstudianteService
import repositories.EstudianteRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EstudianteController @Inject() (
    cc: ControllerComponents,
    estudianteService: EstudianteService,
    estudianteRepository: EstudianteRepository

)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  // Inicializa la base de datos al iniciar
  estudianteRepository.initSchema()

  def listar = Action.async {
    estudianteService.listar().map { estudiantes =>
      Ok(Json.obj("student" -> Json.toJson(estudiantes)))
    }
  }


  def buscarPorId(id: Long) = Action.async {
    estudianteService.buscarPorId(id).map {
      case Some(estudiante) => Ok(Json.toJson(estudiante))
      case None => NotFound(Json.obj("error" -> "Estudiante no encontrado"))
    }
  }

  def agregar = Action.async(parse.json) { request =>
    request.body
      .validate[Estudiante]
      .map { estudiante =>
        estudianteService
          .agregar(estudiante)
          .map(nuevoEstudiante => Created(Json.toJson(nuevoEstudiante)))
      }
      .recoverTotal { e =>
        Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(e))))
      }
  }
/*
  def actualizar(id: Long) = Action.async(parse.json) { request =>
    request.body
      .validate[Estudiante]
      .map { estudiante =>
        estudianteService.actualizar(id, estudiante).map {
          case 1 => Ok(Json.obj("status" -> "actualizado"))
          case _ => NotFound(Json.obj("error" -> "Estudiante no encontrado"))
        }
      }
      .recoverTotal { e =>
        Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(e))))
      }
  }

  */
def actualizar(id: Long) = Action.async(parse.json) { request =>
  request.body.validate[Estudiante].map { estudiante =>
    estudianteService.actualizar(id, estudiante).flatMap {
      case 1 =>

        estudianteService.buscarPorId(id).map {
          case Some(estudianteActualizado) => Ok(Json.toJson(estudianteActualizado))
          case None => NotFound(Json.obj("error" -> "Estudiante no encontrado después de actualizar"))
        }
      case _ =>
        Future.successful(NotFound(Json.obj("error" -> "Estudiante no encontrado")))
    }
  }.recoverTotal { e =>
    Future.successful(BadRequest(Json.obj("error" -> JsError.toJson(e))))
  }
}


  def eliminar(id: Long) = Action.async {
    estudianteService.eliminar(id).map {
      case 1 => NoContent
      case _ => NotFound(Json.obj("error" -> "Estudiante no encontrado"))
    }
  }
}
