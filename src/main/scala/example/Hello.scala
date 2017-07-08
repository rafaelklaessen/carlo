package example
import net.rk02.carlo.Firebase

import com.google.firebase
import com.google.firebase._
import com.google.firebase.auth._
import com.google.firebase.database._
import java.io.FileInputStream
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import net.rk02.carlo.listeners.Listeners
import net.rk02.carlo.listeners.CustomListeners

object Hello extends App with CustomListeners {
  val s = new FileInputStream("firebase-auth.json")
  Firebase.init(s, "fir-scala", "RejuhkemAHBnTzNDxc926y4v0K4Y1UsBebWl4p1S")
  val g = Firebase.get[String]("kees")

  g onSuccess {
    case data => {
      println(data)
    }
  }

  Firebase.listen("jan") { dataSnapshot: DataSnapshot =>
    println(dataSnapshot.getValue)
  }

  val filtered = Firebase.getWithFilters[Map[String, String]]("jan", "orderBy=\"$key\"&limitToFirst=1")

  filtered onSuccess {
    case data => println(data)
  }

  val shallow = Firebase.getShallow("jan")

  shallow onSuccess {
    case data => {
      println(data)
    }
  }

  Firebase.save("henk/jan/erik", "tipsfedora")

  println(FirebaseDatabase.getInstance.getReference("jan").henk)
}
