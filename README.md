# Config Lab

Proyecto en donde se creara multiples ramas para distintas configuracione.

## feature/fcm

La Rama develop tendrá la primera configuración.

## Configuration

1. Ir a Firebase y crear un nuevo proyecto.
2. registrar con el nombre del paquete, que se encuentra en gradle a nivel de app
   en mi caso --> com.rafdev.configlab
3. descargar el archivo que te genrará firebase en nivel de app
   en mi caso google-services.json

4. agregar sdk de fierbase en el gradle a nivel de proyecto
   id("com.google.gms.google-services") version "4.4.2" apply false

5. agregar sdk en el gradle a nivel de app
   plugin {
   id("com.google.gms.google-services")
   }

dependencies{
implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")
}

6. Agregar en el manifest
```xml
<manifest>

 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>

 <application>
     <!-- Configuración del servicio -->
     <service
         android:name=".java.MyFirebaseMessagingService"
         android:exported="false">
         <intent-filter>
             <action android:name="com.google.firebase.MESSAGING_EVENT" />
         </intent-filter>
     </service>

     <!-- Metadatos de configuración -->
     <meta-data
         android:name="com.google.firebase.messaging.default_notification_icon"
         android:resource="@drawable/icono" />
     <meta-data
         android:name="com.google.firebase.messaging.default_notification_channel_id"
         android:value="@string/default_notification_channel_id" />
     <meta-data
         android:name="com.google.firebase.messaging.default_notification_color"
         android:resource="@color/transparent" />
 </application>

 <!-- Opciones avanzadas -->
 <meta-data
     android:name="firebase_messaging_auto_init_enabled"
     android:value="false" />
 <meta-data
     android:name="firebase_analytics_collection_enabled"
     android:value="false" />
</manifest> 
```


7. creamos un canal en un
   string  <string name="default_notification_channel_id" translatable="false">
   fcm_default_channel</string>
8. Crea un archivo
   class MyFirebaseMessagingService: FirebaseMessagingService() {

   override fun onMessageReceived(message: RemoteMessage) {
   super.onMessageReceived(message)
   }

   override fun onNewToken(token: String) {
   super.onNewToken(token)
   }

}

9. Permisos para Recibir Notificaciones en la actividad principal
   private fun askNotificationPermission() {
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
   if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
   PackageManager.PERMISSION_GRANTED
   ) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
   }

   private val requestPermissionLauncher = registerForActivityResult(
   ActivityResultContracts.RequestPermission(),
   ) { isGranted: Boolean ->
   if (isGranted) {

        } else {

        }
   }
   y en el archivo principal creamos un canal
   @HiltAndroidApp
   class ConfigLabApp : Application() {
   companion object {
   const val FCM_CHANNEL_ID = "FCM_CHANNEL_ID"
   }

   override fun onCreate() {
   super.onCreate()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val fcmChannel = NotificationChannel(
                FCM_CHANNEL_ID,
                "FCM_Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(fcmChannel)
        }
   }

}

10. Creamos la notificación

en el archivo de MyFirebaseMessaginService
  


