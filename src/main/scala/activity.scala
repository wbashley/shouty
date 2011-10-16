package spur.shouty

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import java.io.{File,FileInputStream,BufferedInputStream}

class MainActivity extends Activity {
  lazy val server = unfiltered.netty.Http(8080).plan(Stream)

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    import android.net.wifi.WifiManager
    import android.content.Context.WIFI_SERVICE
    val wifiManager = getBaseContext().getSystemService(
      WIFI_SERVICE).asInstanceOf[WifiManager]
    val wifiInfo = wifiManager.getConnectionInfo()
    val ipAddress = wifiInfo.getIpAddress()
    val ip = "%d.%d.%d.%d".format(
      (ipAddress & 0xff),
      (ipAddress >> 8 & 0xff),
      (ipAddress >> 16 & 0xff),
      (ipAddress >> 24 & 0xff))

    setContentView(new TextView(this) {
      setText("http://%s:%d/ ".format(ip, server.port))
    })

    server.start()
    val encode = Encode(Mic)
    Mic.start(encode(Stream.write))
  }
  override def onDestroy() {
    Mic.stop()
    Stream.stop()
    server.stop()
  }
}
