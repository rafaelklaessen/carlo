package net.rk02.carlo

import java.io.FileInputStream
import com.google.firebase
import com.google.firebase._
import com.google.firebase.auth._
import com.google.firebase.database._
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.reflect._

class FirebaseReadException(message: String = "", cause: Throwable = null) extends RuntimeException(message, cause)

object Firebase {
  private var projectId = ""
  private var dbSecret = ""
  private var dbUrl = ""

  protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal

  def init(serviceAccount: FileInputStream, projectId: String, dbSecret: String): Unit = {
    Firebase.projectId = projectId
    Firebase.dbUrl = s"https://$projectId.firebaseio.com"

    if (FirebaseApp.getApps.isEmpty) {
      val options = new FirebaseOptions.Builder()
          .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
          .setDatabaseUrl(Firebase.dbUrl)
          .build

      FirebaseApp.initializeApp(options)
    }

    Firebase.dbSecret = dbSecret
  }

  def get[A](url: String)(implicit m: Manifest[A]): Future[Option[A]] = Future {
    val json = Source.fromURL(getUrl(url)).mkString

    if (json == "null") None
    else Some(parse(json).extract[A])
  }

  def listen(refPath: String)(onChange: DataSnapshot => Unit): Unit = {
    val ref = FirebaseDatabase.getInstance.getReference(refPath)

    ref.addValueEventListener(new ValueEventListener() {
      override def onDataChange(dataSnapshot: DataSnapshot) = {
        onChange(dataSnapshot)
      }

      override def onCancelled(databaseError: DatabaseError) = {
        throw databaseError.toException
      }
    });
  }

  private def getUrl(url: String) = s"$dbUrl/$url.json?auth=$dbSecret"
}
