package com.example.kimseolki.refrigerator_acin.service;

        import com.example.kimseolki.refrigerator_acin.model.Shopping;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.DELETE;
        import retrofit2.http.GET;
        import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-03.
 */

public interface GetShopping {
    @GET("Shopping/")
    Call<List<Shopping>> all();

    @GET("Shopping/{id}/")
    Call<Shopping> select(@Path("shoppingId") int id);

    @DELETE("Shopping/{id}/")
    Call<Void> removeShopping(@Path("id")  Integer id);
}
