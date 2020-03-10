package com.example.instruments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instruments.adapter.RecyclerInstrumentAdapter;
import com.example.instruments.model.InstrumentModel;
import com.example.instruments.request.ServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerInstrumentAdapter.ClickListener{

    private static final String TAG = "Instrument";
    //ui
    private RecyclerView recyclerView;
    ProgressBar progressBar;

    RecyclerInstrumentAdapter adapter;
    private List<InstrumentModel> modelinstList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar =  findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        initRecyclerView();
        if(isNetworkAvailable())
            loadDatFromApi();
        else
            Toast.makeText(MainActivity.this, R.string.nonetwork, Toast.LENGTH_SHORT).show();

    }

    private void initRecyclerView(){

        adapter = new RecyclerInstrumentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadDatFromApi()
    {
        Call<String> call= ServiceGenerator.getRequestApi().getRess();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d(TAG, "Response1:"+response.body());

                if(response.body()!=null)
                {
                    parseResponse(response.body());
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.invalidresponse, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure:"+t.toString());
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseResponse(String response)
    {
        JSONObject jsonObject= null;
        modelinstList.clear();

        try {
            jsonObject = new JSONObject(response);

            JSONObject resObj=jsonObject.getJSONObject("instruments");

            Iterator<String> keys= resObj.keys();

            while (keys.hasNext())
            {
                String key= keys.next();

                JSONObject instrumentsObj=resObj.getJSONObject(key);

                Log.d(TAG, "\n.......................................................");
                Log.d(TAG, "instruments:assetClass:"+instrumentsObj.getString("assetClass"));
                Log.d(TAG, "instruments:name:"+instrumentsObj.getString("name"));

                JSONObject resObj1=jsonObject.getJSONObject("prices");
                JSONObject priceresObj=resObj1.getJSONObject(key);

                Log.d(TAG, "prices:assetClass:"+priceresObj.getString("bid"));
                Log.d(TAG, "prices:name:"+priceresObj.getString("ask"));


                modelinstList.add(new InstrumentModel(instrumentsObj.getString("name"),
                        instrumentsObj.getString("canonical_symbol"),
                        instrumentsObj.getString("id"),
                        instrumentsObj.getString("assetClass"),
                        instrumentsObj.getString("quantityIncrement"),
                        instrumentsObj.getString("priceIncrement"),
                        priceresObj.getString("bid"),
                        priceresObj.getString("ask")));
            }


            progressBar.setVisibility(View.GONE);
            adapter.setInstrument(modelinstList,MainActivity.this,this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void OnGridClic(int pos) {

        Log.d(TAG, "position:"+pos);
        Intent i = new Intent(this, InstrumentDetailsActivity.class);
        i.putExtra("getInstrumentName",modelinstList.get(pos).getInstrumentName());
        i.putExtra("getInstrumentSymbol",modelinstList.get(pos).getInstrumentSymbol());
        i.putExtra("getId",modelinstList.get(pos).getId());
        i.putExtra("getAssetClass",modelinstList.get(pos).getAssetClass());
        i.putExtra("getPriceIncrement",modelinstList.get(pos).getPriceIncrement());
        i.putExtra("getQuantityIncrement",modelinstList.get(pos).getQuantityIncrement());
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
