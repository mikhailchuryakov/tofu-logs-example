package com.example

import cats.effect._
import cats.{Functor, Monad}
import derevo.derive
import tofu.logging._
import tofu.logging.derivation.{MaskMode, hidden, loggable, masked, unembed}
import tofu.syntax.logging._
import tofu.syntax.monadic._

object Example extends App {
  @derive(loggable)
  case class UserHidden(name: String, @hidden surname: String)
  @derive(loggable)
  case class UserMask(name: String, @masked(MaskMode.Erase) surname: String)
  @derive(loggable)
  case class UserUnembed(name: String, @unembed surname: String)
  @derive(loggable)
  case class UserUnembedWithMask(name: String, @unembed @masked(MaskMode.Erase) surname: String)

  val userHidden = UserHidden("name", "surname")
  val userMask = UserMask("name", "surname")
  val userUnembed = UserUnembed("name", "surname")
  val userUnembedWithMask = UserUnembedWithMask("name", "surname")

  class MyService[F[_]: Logging: Monad] {
    def doLogging: F[Unit] =
      for {
        _ <- info"hidden: $userHidden"
        _ <- info"mask: $userMask"
        _ <- info"unembed: $userUnembed"
        _ <- info"unembed with mask: $userUnembedWithMask"
      } yield ()
  }

  object MyService {
    def apply[I[_]: Functor, F[_]: Monad](implicit logs: Logs[I, F]): I[MyService[F]] = {
      logs.forService[MyService[F]].map(implicit l => new MyService[F])
    }
  }

  implicit val logs: Logs[IO, IO] = Logs.sync[IO, IO]

  val io: IO[Unit] = for {
    service <- MyService[IO, IO]
    _       <- service.doLogging
  } yield ()

  io.unsafeRunSync()
}
