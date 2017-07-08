package net.rk02.carlo

import com.google.firebase.database.Query

class Listeners(q: Query) {
  def henk = {
    "henk"
  }
}

// Use this trait to enable the custom listener methods
trait CustomListeners {
  implicit def bindListeners(q: Query) = new Listeners(q)
}
