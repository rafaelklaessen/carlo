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

object Firebase {
  private var projectId = ""
  private var dbSecret = ""
  private var dbUrl = ""

  protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal

  /**
   * Firebase.init
   * Inits a default Firebase app from given serviceAccount and projectId, but only when there
   * is none yet.
   */
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

  /**
   * Firebase.get
   * Gets the JSON from given path and parses it to Some(A). If the JSON is "null", None is
   * returned
   */
  def get[A](path: String)(implicit m: Manifest[A]): Future[Option[A]] = Future {
    val json = Source.fromURL(getUrl(path)).mkString
    if (json == "null") None else Some(parse(json).extract[A])
  }

  /**
   * Firebase.getWithFilters
   * Basically the same as Firebase.get, except that given filters will be added to the JSON URL
   */
  def getWithFilters[A](path: String, filters: String)(implicit m: Manifest[A]):
      Future[Option[A]] = Future {

    val json = Source.fromURL(getUrl(path) + "&" + filters).mkString
    if (json == "null") None else Some(parse(json).extract[A])
  }

  /**
   * Firebase.getShallow
   * The same as Firebase.get, except for that it requests a shallow JSON, which means it'll
   * always return Future[Option[Map[String, Boolean]]]
   */
  def getShallow(url: String): Future[Option[Map[String, Boolean]]] = Future {
    val json = Source.fromURL(getUrl(url) + "&shallow=true").mkString
    if (json == "null") None else Some(parse(json).extract[Map[String, Boolean]])
  }

  /**
   * Firebase.listen
   * Really basic Scala wrapper around a simple ValueEventListener. For more advanced listeners
   * use the net.rk02.carlo.listeners instead.
   */
  def listen(refPath: String)(onChange: DataSnapshot => Unit,
      onCancel: DatabaseError => Unit = _ => Unit): Unit = {

    val ref = FirebaseDatabase.getInstance.getReference(refPath)

    ref.addValueEventListener(new ValueEventListener() {
      override def onDataChange(dataSnapshot: DataSnapshot) = {
        onChange(dataSnapshot)
      }

      override def onCancelled(databaseError: DatabaseError) = {
        onCancel(databaseError)
      }
    });
  }

  /**
   * Firebase.save
   * Saves given item to Firebase using Firebase's .setValue()
   */
  def save(refPath: String, item: Object): Unit = {
    FirebaseDatabase.getInstance.getReference(refPath).setValue(item)
  }

  /**
   * Firebase.getUrl
   * Private method that builds the full URL from a partial URL.
   */
  private def getUrl(url: String) = s"$dbUrl/$url.json?auth=$dbSecret"
}
