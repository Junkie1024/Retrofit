package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {

    Recyadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        ///Starting From Array
        /*Call<List<Pokemon>> call = service.getPokemons();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {

                System.out.println(response.body());
                generateData(response.body());

            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });*/
        //Starting from json object


        Call<PokemonPojo> call = service.getPokemonsObj();
        {
            call.enqueue(new Callback<PokemonPojo>() {
                @Override
                public void onResponse(Call<PokemonPojo> call, Response<PokemonPojo> response) {
                    ArrayList<Pokemon> pokarray = new ArrayList<>();
                    PokemonPojo pokojo = response.body();

                    try
                    {
                        pokarray = new ArrayList<>(pokojo.getPokemon());
                        generateData(pokarray);

                    }catch (NullPointerException e)
                    {
                        System.out.println(e.getMessage());;
                    }
                }

                @Override
                public void onFailure(Call<PokemonPojo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void generateData(ArrayList<Pokemon> pokes)//List<Pokemon> poklist)
    {
        /*ArrayList<Pokemon> pokes = (ArrayList<Pokemon>) poklist;*/
        adapter = new Recyadapter(pokes,getApplicationContext());
        @SuppressLint("WrongConstant") LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), VERTICAL,false);
        RecyclerView recyclerView = findViewById(R.id.recycle_poke);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
