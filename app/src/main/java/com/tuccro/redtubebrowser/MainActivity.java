package com.tuccro.redtubebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tuccro.redtubebrowser.api.RedTubeService;
import com.tuccro.redtubebrowser.model.Categories;

import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Button buttonRefresh;
    ProgressBar progressBar;

    String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);

        loadCategoriesList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, VideosActivity.class);
                intent.putExtra(VideosActivity.ARG_CATEGORY, categories[position].toString());
                startActivity(intent);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonRefresh:
                        loadCategoriesList();
                        break;
                }
            }
        });
    }

    private void loadCategoriesList() {

        progressBar.setVisibility(View.VISIBLE);
        buttonRefresh.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.redtube.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedTubeService git = retrofit.create(RedTubeService.class);
        Call call = git.getCategories();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Response<Categories> response) {

                progressBar.setVisibility(View.GONE);
                final Categories model = response.body();

                if (model == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        Log.e("error", responseBody.toString());
                    }

                    buttonRefresh.setVisibility(View.VISIBLE);
                } else {
                    //200
                    categories = model.getCategoriesAsStringArray();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, categories);
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                progressBar.setVisibility(View.GONE);
                buttonRefresh.setVisibility(View.VISIBLE);

                if (t instanceof UnknownHostException) {
                    Snackbar.make(list, R.string.check_internet, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
