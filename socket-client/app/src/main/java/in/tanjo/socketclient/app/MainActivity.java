package in.tanjo.socketclient.app;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements Runnable, View.OnClickListener {
  private Thread mLooper = new Thread(this);
  LocalSocket mLocalSocket = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    Button button = (Button)this.findViewById(R.id.button);
    button.setOnClickListener(this);
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
  public void onClick(View v) {
    mLooper.start();
    Toast.makeText(this, "Connect Server!", Toast.LENGTH_LONG).show();
  }

  @Override
  public void run() {
    try {
      // create socket
      mLocalSocket = new LocalSocket();
      String SOCKET_NAME = "org.techbooster.server";
      LocalSocketAddress address = new LocalSocketAddress(SOCKET_NAME);

      // 接続
      mLocalSocket.connect(address);

      // write
      int data1 = 0xff;
      int data2 = 0xaa;
      int data3 = 0x88;

      mLocalSocket.getOutputStream().write(data1);
      mLocalSocket.getOutputStream().write(data2);
      mLocalSocket.getOutputStream().write(data3);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 切断
    try {
      mLocalSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
