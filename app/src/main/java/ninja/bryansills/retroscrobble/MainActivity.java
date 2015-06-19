package ninja.bryansills.retroscrobble;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button authenticateButton = (Button) findViewById(R.id.button_authenticate);
        final Button nowPlayingButton = (Button) findViewById(R.id.button_now_playing);
        final Button scrobbleButton = (Button) findViewById(R.id.button_scrobble);

        authenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPlayingButton.setEnabled(true);
                scrobbleButton.setEnabled(true);

                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://ws.audioscrobbler.com/2.0")
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();

                LastFmApi lastFmApi = restAdapter.create(LastFmApi.class);

                lastFmApi.authenticate("auth.getMobileSession", "json", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(MainActivity.this, "Success?", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Now playing", Toast.LENGTH_LONG).show();
            }
        });

        scrobbleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Scrobble", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
