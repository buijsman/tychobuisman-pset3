package tycho.tychobuisman_pset3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class watchlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        fetchAllPreference();
    }

    public void fetchAllPreference() {
        ListView lv = (ListView) findViewById(R.id.list);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String key = "movie";
        String memory = sharedpreferences.getString(key, "");
        String[] movies = memory.split(",");
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < movies.length; i++){
            list.add(movies[i]);
        }

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        lv.setAdapter(adapter);
    }
    public void goback(View view){
        Intent goToMain = new Intent(this, MainActivity.class);
        startActivity(goToMain);
        finish();
    }

}





