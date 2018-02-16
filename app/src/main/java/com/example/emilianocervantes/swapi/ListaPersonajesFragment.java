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
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
    }

    private CharacterAdapter getAdapter() {
        CharacterAdapter adapter = new CharacterAdapter(
                getActivity(),
                R.layout.character_list_layout,
                new ArrayList<Character>());
        try {
            for (int j= 0; j< 9; j++){
                JSONObject jsonObject = new JSONObject(getSWString("https://swapi.co/api/people/?page="+j+"&format=json"));
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i = 0; i < jsonArray.length();i++){
                    JSONObject character = jsonArray.getJSONObject(i);
                    Character ch = new Character();
                    ch.fecha = character.getString("birth_year");
                    ch.nombre = character.getString("name");
                    adapter.add(ch);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return adapter;
    }

    private void jsonStarWars(String url, final CharacterAdapter adapter){
        //Limpie el adapatador
        adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Character ch = new Character();
                        ch.nombre = jsonObject.getString("name");
                        ch.fecha = jsonObject.getString("birth_year");
                        adapter.add(ch);
                    }
                    adapter.notifyDataSetChanged(); //actualiza la vista
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        });
        mQueue.add(request);
    }

    private String getSWString(String url){
        Uri builtUri;
        builtUri = Uri.parse(url).buildUpon()
                .build();
        return builtUri.toString();
    }
}
