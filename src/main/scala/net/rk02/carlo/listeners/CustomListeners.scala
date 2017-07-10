package net.rk02.carlo.listeners

import com.google.firebase.database.Query

// Use this trait to enable the custom listener methods
trait CustomListeners {
  // Implicit method that binds the Listeners class to com.google.firebase.database.Query
  implicit def bindListeners(q: Query) = new Listeners(q)
}
