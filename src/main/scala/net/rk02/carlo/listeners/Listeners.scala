package net.rk02.carlo.listeners

import com.google.firebase.database._
import scala.collection.mutable.ListBuffer

class Listeners(query: Query) {
  def on(eventType: String)(onChange: DataSnapshot => Unit,
      onCancel: DatabaseError => Unit = _ => Unit): Unit = {

    if (eventType == Listeners.Value) {
      query.addValueEventListener(new ValueEventListener() {
        override def onDataChange(dataSnapshot: DataSnapshot) = {
          onChange(dataSnapshot)
        }

        override def onCancelled(databaseError: DatabaseError) = {
          onCancel(databaseError)
        }
      })
    } else {
      query.addChildEventListener(new ChildEventListener() {
        override def onChildAdded(dataSnapshot: DataSnapshot, p: String) = {
          if (eventType == Listeners.ChildAdded) onChange(dataSnapshot)
        }

        override def onChildChanged(dataSnapshot: DataSnapshot, p: String) = {
          if (eventType == Listeners.ChildChanged) onChange(dataSnapshot)
        }

        override def onChildRemoved(dataSnapshot: DataSnapshot) = {
          if (eventType == Listeners.ChildRemoved) onChange(dataSnapshot)
        }

        override def onChildMoved(dataSnapshot: DataSnapshot, p: String) = {
          if (eventType == Listeners.ChildMoved) onChange(dataSnapshot)
        }

        override def onCancelled(databaseError: DatabaseError) = {
          onCancel(databaseError)
        }
      })
    }
  }
}

object Listeners {
  val Value = "value"
  val ChildAdded = "childAdded"
  val ChildChanged = "childChanged"
  val ChildRemoved = "childRemoved"
  val ChildMoved = "childMoved"
}
