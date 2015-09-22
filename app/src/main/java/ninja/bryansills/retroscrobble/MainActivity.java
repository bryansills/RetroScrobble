package ninja.bryansills.retroscrobble;

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
import ninja.bryansills.retroscrobble.model.Session;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        Retrofit restAdapter = new Retrofit.Builder()
//                .baseUrl("https://ws.audioscrobbler.com/2.0")
//                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
//                .setConverter(new GsonConverter(gson))
//                .build();

        mLastFmApi = retrofit.create(LastFmApi.class);
//
        mAuthenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNowPlayingButton.setEnabled(true);
                mScrobbleButton.setEnabled(true);

                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                Call<AuthenticationResponse> call = mLastFmApi.authenticate(
                        LastFmApi.AUTH_CONSTS, username, password,
                        Util.generateLastFmApiSig(LastFmApi.AUTH_CONSTS,
                                                  LastFmApi.USERNAME, username,
                                                  LastFmApi.PASSWORD, password));

                call.enqueue(new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Response<AuthenticationResponse> response) {
                        if (response.body() != null) {
                            mResponseTextView.setText(response.body().getSession().getSessionKey());
                            PreferenceManager.putSession(MainActivity.this, response.body().getSession());
                        } else {
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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

        Session session = PreferenceManager.getSession(this);

        if (session != null) {
            mResponseTextView.setText(session.getSessionKey());
        }
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
