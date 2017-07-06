package example
import net.rk02.carlo.Firebase
import net.rk02.carlo.Firebase.Value

import com.google.firebase
import com.google.firebase._
import com.google.firebase.auth._
import com.google.firebase.database._
import java.io.FileInputStream
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

object Hello extends App {
  val s = new FileInputStream("firebase-auth.json")
  Firebase.init(s, "fir-scala", "RejuhkemAHBnTzNDxc926y4v0K4Y1UsBebWl4p1S")
  val g = Firebase.get[String]("kees")

  g onSuccess {
    case data => {
      println(data)
    }
  }

  Firebase.listen("/jan", Value) { dataSnapshot: DataSnapshot =>
    println(dataSnapshot.getValue)
  }

}
