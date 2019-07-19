package com.sahins.were;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyExchangeInterface {
    @GET("latest?access_key=2998bc81a528e007e4599c4b9dc607fe")
    Call<CurrencyExchange> loadCurrencyExchange();
}
