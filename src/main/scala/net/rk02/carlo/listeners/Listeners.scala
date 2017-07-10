package net.rk02.carlo.listeners

import com.google.firebase.database._
import scala.collection.mutable.ListBuffer

/**
 * Listeners
 * The Listeners class's supposed to be bound to com.google.firebase.database.Query via
 * the CustomListeners trait.
 */
class Listeners(query: Query) {
  /**
   * Listeners.on
   * Scala wrapper around the standard ValueEventListener and ChildEventListener.
   */
  def on(eventType: Listeners.Value)(onChange: DataSnapshot => Unit,
      onCancel: DatabaseError => Unit = _ => Unit): Unit = {

    if (eventType == Listeners.ValueChanged) {
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

// Companion object to Listeners class that simply contains the valid event types
object Listeners extends Enumeration {
  val ValueChanged, ChildAdded, ChildChanged, ChildRemoved, ChildMoved = Value
}
