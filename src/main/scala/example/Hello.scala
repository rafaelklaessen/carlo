package example
import net.rk02.firebasescala.Firebase
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

}
