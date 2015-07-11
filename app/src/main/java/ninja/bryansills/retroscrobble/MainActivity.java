package ninja.bryansills.retroscrobble;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ninja.bryansills.retroscrobble.model.AuthenticationResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mAuthenticateButton;
    private Button mNowPlayingButton;
    private Button mScrobbleButton;
    private TextView mResponseTextView;

    private LastFmApi mLastFmApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsernameEditText = (EditText) findViewById(R.id.edittext_username);
        mPasswordEditText = (EditText) findViewById(R.id.edittext_password);
        mAuthenticateButton = (Button) findViewById(R.id.button_authenticate);
        mNowPlayingButton = (Button) findViewById(R.id.button_now_playing);
        mScrobbleButton = (Button) findViewById(R.id.button_scrobble);
        mResponseTextView = (TextView) findViewById(R.id.textview_response);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://ws.audioscrobbler.com/2.0")
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(gson))
                .build();

        mLastFmApi = restAdapter.create(LastFmApi.class);

        mAuthenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNowPlayingButton.setEnabled(true);
                mScrobbleButton.setEnabled(true);

                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                mLastFmApi.authenticate(LastFmApi.AUTH_CONSTS, username, password,
                        Util.generateLastFmApiSig(LastFmApi.AUTH_CONSTS,
                                LastFmApi.USERNAME, username,
                                LastFmApi.PASSWORD, password),
                        new Callback<AuthenticationResponse>() {
                    @Override
                    public void success(AuthenticationResponse response, Response response2) {
                        if (response.getSession() != null) {
                            mResponseTextView.setText(response.getSession().getSessionKey());
                        } else {
                            mResponseTextView.setText(String.valueOf(response.getError()));
                        }
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
