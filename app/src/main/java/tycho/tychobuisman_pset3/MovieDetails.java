package tycho.tychobuisman_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        displayMovie();
    }

    public void displayMovie() {
        String result = getIntent().getExtras().getString("result");

        JSONObject jObject = null;
        try {
            jObject = new JSONObject(result);

            String title = jObject.getString("Title");
            String year = jObject.getString("Year");
            String director = jObject.getString("Director");
            String actors = jObject.getString("Actors");
            String plot = jObject.getString("Plot");
            String poster = jObject.getString("Poster");

            new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
                    .execute(poster);
            TextView Title = (TextView) findViewById(R.id.title);
            TextView Year = (TextView) findViewById(R.id.year);
            TextView Director = (TextView) findViewById(R.id.director);
            TextView Actors = (TextView) findViewById(R.id.actors);
            TextView Plot = (TextView) findViewById(R.id.plot);

            Title.setText(title);
            Year.setText(year);
            Director.setText(director);
            Actors.setText(actors);
            Plot.setText(plot);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void saveToWatchlist(View view) {
        String result = getIntent().getExtras().getString("result");
        String memory = "";
        String list = "";
        String[] movies = new String[10];
        try {
            JSONObject jObject = new JSONObject(result);
            String title = jObject.getString("Title");

            SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            memory = sharedpreferences.getString("movie", "");

            if (memory != null) {
                movies = memory.split(",");

                int size = movies.length;

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < movies.length; i++) {
                    sb.append(movies[i]).append(",");
                }
                sb.append(title);
                list = sb.toString();

                editor.putString("movie", list);
                editor.commit();
            } else {
                editor.putString("movie", title);
                editor.commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void goback(View view) {
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
        finish();
    }
}
