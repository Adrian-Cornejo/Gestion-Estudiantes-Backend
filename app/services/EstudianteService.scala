package services

import javax.inject._
import models.Estudiante
import repositories.EstudianteRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EstudianteService @Inject() (estudianteRepository: EstudianteRepository)(
    implicit ec: ExecutionContext
) {

  def listar(): Future[Seq[Estudiante]] = estudianteRepository.listar()

  def buscarPorId(id: Long): Future[Option[Estudiante]] =
    estudianteRepository.buscarPorId(id)

  def agregar(estudiante: Estudiante): Future[Estudiante] =
    estudianteRepository.agregar(estudiante)

  def actualizar(id: Long, estudiante: Estudiante): Future[Int] =
    estudianteRepository.actualizar(id, estudiante)

  def eliminar(id: Long): Future[Int] = estudianteRepository.eliminar(id)
}
