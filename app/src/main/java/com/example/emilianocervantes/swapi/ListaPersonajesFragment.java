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

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=1&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=2&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=3&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=4&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=5&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=6&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=7&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=8&format=json"),adapter);
        jsonStarWars(getSWString("https://swapi.co/api/people/?page=9&format=json"),adapter);
    }

    private CharacterAdapter getAdapter() {
        CharacterAdapter adapter = new CharacterAdapter(
                getActivity(),
                R.layout.character_list_layout,
                new ArrayList<Character>());
        try {
            JSONObject jsonObject = new JSONObject(getSWString());
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject01 = jsonArray.getJSONObject(i);
                JSONArray characters = jsonObject01.getJSONArray("matches");
                JSONObject match = characters.getJSONObject(j);
                Character ch = new Character();
                ch.fecha = match.getString("birth_year");
                ch.nombre = match.getString("name");
                adapter.add(ch);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return adapter;
    }

    private String getSWString(String url){
        Uri builtUri;
        builtUri = Uri.parse(url).buildUpon()
                .build();
        return builtUri.toString();
    }
}
