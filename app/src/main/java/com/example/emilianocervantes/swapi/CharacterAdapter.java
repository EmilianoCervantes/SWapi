package com.example.emilianocervantes.swapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ovman on 16/02/2018.
 */

public class CharacterAdapter extends ArrayAdapter<Character> {
    private Context context;
    public CharacterAdapter(@NonNull Context context, int resource, @NonNull List<Character> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.character_list_layout, parent, false);
        }
        Character character = getItem(position);

        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);

        fecha.setText(character.fecha);
        nombre.setText(character.nombre);
        convertView.setBackgroundResource(R.color.colorPrimary);

        return convertView;
    }
}
