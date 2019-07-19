package com.sahins.were;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActualActivity extends AppCompatActivity implements Callback<CurrencyExchange> {

    int count = 0;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual);




        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try{

                        Thread.sleep(2000);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                loadCurrencyExchangeData();
                            }
                        });
                    } catch (InterruptedException exc){
                        exc.printStackTrace();
                    }
                }
            }

        };
        t.start();
        //Handler handler = new Handler();
        /*while(true){
            handler.postDelayed(new Runnable() {
                public void run() {
                    loadCurrencyExchangeData();
                }
            }, 2000);   //2 seconds

        }*/


        //loadCurrencyExchangeData();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this,"girdi",Toast.LENGTH_SHORT).show();
        loadCurrencyExchangeData();
    }

    private void loadCurrencyExchangeData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyExchangeInterface service = retrofit.create(CurrencyExchangeInterface.class);
        Call<CurrencyExchange> call = service.loadCurrencyExchange();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<CurrencyExchange> call, Response<CurrencyExchange> response) {
        count++;
        TextView dollar = (TextView) findViewById(R.id.usd);
        TextView lira = (TextView) findViewById(R.id.lira);
        TextView audollar = (TextView) findViewById(R.id.aud);
        TextView Date =  (TextView) findViewById(R.id.date);
        TextView Base =  (TextView) findViewById(R.id.base);
        if(response.body().isSuccess()) {
            //TextView counter = (TextView) findViewById(R.id.counter);
            //Toast.makeText(this, response.body().getBase(), Toast.LENGTH_LONG).show(); //Base para birimini gosterir yani burada EUR
            List<OneCurrency> listofcurrencies = response.body().getCurrencyList();
            //Toast.makeText(this,Double.toString(listofcurrencies.get(0).getRate()),Toast.LENGTH_LONG).show();
            dollar.setText(Double.toString(listofcurrencies.get(0).getRate()));
            //Toast.makeText(this,Double.toString(listofcurrencies.get(1).getRate()),Toast.LENGTH_LONG).show();
            lira.setText(Double.toString(listofcurrencies.get(1).getRate()));
            //Toast.makeText(this,Double.toString(listofcurrencies.get(2).getRate()),Toast.LENGTH_LONG).show();
            audollar.setText(Double.toString(listofcurrencies.get(2).getRate()));
            //Toast.makeText(this,response.body().getDate(),Toast.LENGTH_SHORT).show(); 
            Date.setText(response.body().getDate());
            //counter.setText(String.valueOf(count));
            //toast.cancel();
            Base.setText(response.body().getBase());
        }
        else{
            Toast.makeText(this,"Couldn't connect to API",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"API limit is reached or API is down",Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onFailure(Call<CurrencyExchange> call, Throwable t) {
        //Toast.makeText(this,"amk",Toast.LENGTH_LONG).show();
        toast.makeText(this, t.getMessage(),Toast.LENGTH_SHORT).show();
        //toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
}
