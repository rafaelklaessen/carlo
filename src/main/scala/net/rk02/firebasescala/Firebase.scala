package net.rk02.firebasescala

import java.io.FileInputStream
import com.google.firebase.{FirebaseApp, FirebaseOptions}
import com.google.firebase.auth.FirebaseCredentials

object Firebase {
  private var dbSecret: String = ""

  def init(serviceAccount: FileInputStream, projectId: String, dbSecret: String): Unit = {
    if (FirebaseApp.getApps.isEmpty) {
      val options = new FirebaseOptions.Builder()
          .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
          .setDatabaseUrl(s"https://$projectId.firebaseio.com")
          .build

      FirebaseApp.initializeApp(options)
    }

    Firebase.dbSecret = dbSecret
  }

  def get = "test"
}
