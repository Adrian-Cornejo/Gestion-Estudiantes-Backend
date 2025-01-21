package repositories

import javax.inject._
import models.Estudiante
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EstudianteRepository @Inject()(implicit ec: ExecutionContext) {

  // Conexión explícita a la base de datos H2 en memoria
  private val db = Database.forURL(
    url = "jdbc:h2:mem:gestion_estudiantes;DB_CLOSE_DELAY=-1",
    driver = "org.h2.Driver",
    user = "sa",
    password = ""
  )

  private val estudiantes = TableQuery[EstudiantesTable]

  // Definición de la tabla
  class EstudiantesTable(tag: Tag) extends Table[Estudiante](tag, "estudiantes") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("nombre")
    def edad = column[Int]("edad")
    def sexo = column[String]("sexo")
    def correo = column[String]("correo")
    def telefono = column[String]("telefono")
    def direccion = column[String]("direccion")
    def fechaNacimiento = column[String]("fecha_nacimiento")
    def carrera = column[String]("carrera")
    def * = (id.?, nombre, edad, sexo, correo, telefono, direccion, fechaNacimiento, carrera) <> ((Estudiante.apply _).tupled, Estudiante.unapply)
  }

  // Crear el esquema al iniciar
  def initSchema(): Future[Unit] = db.run(estudiantes.schema.create)

  // Métodos CRUD
  def listar(): Future[Seq[Estudiante]] = db.run(estudiantes.result)

  def buscarPorId(id: Long): Future[Option[Estudiante]] = {
    db.run(estudiantes.filter(_.id === id).result.headOption)
  }

  def agregar(estudiante: Estudiante): Future[Estudiante] = {
    db.run((estudiantes returning estudiantes.map(_.id)
      into ((estudiante, id) => estudiante.copy(id = Some(id)))) += estudiante)
  }

  def actualizar(id: Long, estudiante: Estudiante): Future[Int] = {
    db.run(estudiantes.filter(_.id === id).update(estudiante.copy(id = Some(id))))
  }

  def eliminar(id: Long): Future[Int] = {
    db.run(estudiantes.filter(_.id === id).delete)
  }
}
