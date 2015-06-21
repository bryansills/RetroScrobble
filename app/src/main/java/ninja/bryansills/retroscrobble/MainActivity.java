package ninja.bryansills.retroscrobble;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private Button mAuthenticateButton;
    private Button mNowPlayingButton;
    private Button mScrobbleButton;
    private TextView mResponseTextView;

    private LastFmApi mLastFmApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthenticateButton = (Button) findViewById(R.id.button_authenticate);
        mNowPlayingButton = (Button) findViewById(R.id.button_now_playing);
        mScrobbleButton = (Button) findViewById(R.id.button_scrobble);
        mResponseTextView = (TextView) findViewById(R.id.textview_response);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://ws.audioscrobbler.com/2.0")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new StringConverter())
                .build();

        mLastFmApi = restAdapter.create(LastFmApi.class);

        mAuthenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNowPlayingButton.setEnabled(true);
                mScrobbleButton.setEnabled(true);

                mLastFmApi.authenticate("auth.getMobileSession", "json", BuildConfig.LAST_FM_API_KEY, new Callback<String>() {
                    @Override
                    public void success(String response, Response response2) {
                        mResponseTextView.setText(response);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mNowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Now playing", Toast.LENGTH_LONG).show();
            }
        });

        mScrobbleButton.setOnClickListener(new View.OnClickListener() {
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
