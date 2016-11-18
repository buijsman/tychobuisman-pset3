package tycho.tychobuisman_pset3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get_data(View view) {
        EditText text = (EditText) findViewById(R.id.editText);
        String movie = text.getText().toString();
        movie = movie.replace(" ", "+");
        GetURL geturl = new GetURL();

        geturl.execute(movie);

    }

    public void goToWatchlist(View view) {
        Intent goToWatchlist = new Intent(MainActivity.this.getApplicationContext(), watchlist.class);
        startActivity(goToWatchlist);

    }

    class GetURL extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

            ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
            progress.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            String url1 = "http://www.omdbapi.com/?t=";
            String url2 = params[0];
            String FullUrl = url1 + url2;
            String result = "";
            try {
                URL url = new URL(FullUrl);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");

                httpConnection.connect();

                int responseCode = httpConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    result = sb.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar);
            progress.setVisibility(View.INVISIBLE);

            Intent goToMovieDetails = new Intent(MainActivity.this.getApplicationContext(), MovieDetails.class);
            EditText text = (EditText) findViewById(R.id.editText);
            text.setText("");
            goToMovieDetails.putExtra("result", result);//bundle doorgeven aan intent
            startActivity(goToMovieDetails);
            finish();

        }
    }
}



