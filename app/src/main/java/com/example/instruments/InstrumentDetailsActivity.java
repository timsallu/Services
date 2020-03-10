package com.example.instruments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InstrumentDetailsActivity extends AppCompatActivity {

    TextView tvname;
    TextView tvsymbol;
    TextView tvid;
    TextView tvasserClass;
    TextView tvquantityIncrement;
    TextView tvpriceIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_details);
        loadScreen();
    }

    private void loadScreen()
    {
        tvname = findViewById(R.id.tv_instrumentName);
        tvsymbol = findViewById(R.id.tv_instrumentSymbol);
        tvid = findViewById(R.id.tv_id);
        tvasserClass = findViewById(R.id.tv_assetClass);
        tvquantityIncrement = findViewById(R.id.tv_quantityIncrement);
        tvpriceIncrement = findViewById(R.id.tv_priceIncrement);

        tvname.setText(getIntent().getStringExtra("getInstrumentName"));
        tvsymbol.setText(getIntent().getStringExtra("getInstrumentSymbol"));
        tvid.setText(getIntent().getStringExtra("getId"));
        tvasserClass.setText(getIntent().getStringExtra("getAssetClass"));
        tvquantityIncrement.setText(getIntent().getStringExtra("getQuantityIncrement"));
        tvpriceIncrement.setText(getIntent().getStringExtra("getPriceIncrement"));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
