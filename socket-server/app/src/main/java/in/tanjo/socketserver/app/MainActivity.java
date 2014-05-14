package in.tanjo.socketserver.app;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements Runnable {

  private Thread mLooper = null;

  LocalServerSocket mLocalServerSocket = null;
  LocalSocket mLocalSocket = null;
  private final String SOCKET_NAME = "org.techbooster.server";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();

    // ServerSocket の作成
    try {
      mLocalServerSocket = new LocalServerSocket(SOCKET_NAME);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Threadの起動
    mLooper = new Thread(this);
    mLooper.start();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLooper = null;
  }

  @Override
  public void run() {
    // Server の起動
    try {
      // 待受
      mLocalSocket = mLocalServerSocket.accept();

      // 受信
      int ret = 0;
      while ((ret = mLocalSocket.getInputStream().read()) != -1) {
        Log.v("Techbooster.LocalServerSocketSample", "ret=" + ret);
      }

      Log.v("Techbooster.LocalServerSocketSample","ret =" + ret);

    } catch (IOException e) {
      e.printStackTrace();
    }

    // 切断
    try {
      mLocalSocket.close();
      mLocalServerSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
