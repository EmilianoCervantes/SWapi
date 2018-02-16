package com.example.emilianocervantes.swapi;


import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link ListFragment} subclass.
 */
public class ListaPersonajesFragment extends ListFragment {
    private RequestQueue mQueue;

    public ListaPersonajesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_personajes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CharacterAdapter adapter = getAdapter();
        setListAdapter(adapter);

        mQueue = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        jsonStarWars(getSWString(),adapter);
    }

    private CharacterAdapter getAdapter() {

    }

    private void jsonStarWars(String url, final CharacterAdapter adapter){
        //Limpie el adapatador
        adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Listener positivo
                //Si pudo consultar entra ahi
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("results");
                    for (int i = 0; i<jsonArray.length();i++){
                        //Para el name
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Character character = new Character();
                        character.nombre = jsonObject.getString("name");
                        character.fecha = jsonObject.getLong("date")+"";
                        adapter.add(character);
                    }
                    //Actualizar vista
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /**/
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }

    private String getSWString(){
        Uri builtUri;
        builtUri = Uri.parse("https://swapi.co/api/people/?page=1&format=json").buildUpon()
                .build();
        return builtUri.toString();
    }
}
