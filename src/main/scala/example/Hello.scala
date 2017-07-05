package example
import net.rk02.firebasescala.Firebase
import java.io.FileInputStream

object Hello extends App {
  val s = new FileInputStream("firebase-auth.json")
  println(Firebase.get)
  Firebase.init(s, "fir-scala", "RejuhkemAHBnTzNDxc926y4v0K4Y1UsBebWl4p1S")
}
