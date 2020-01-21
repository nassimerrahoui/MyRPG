package com.example.myrpg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LevelsAdapter extends ArrayAdapter<LevelsItem> {

    private final List<LevelsItem> objects;
    private final int layoutResource;

    class ViewHolder {
        int position;
        public TextView title;
        public CheckBox checkbox;
    }

    public LevelsAdapter(@NonNull Context context, int resource, @NonNull List<LevelsItem> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
        this.objects = objects;
    }

    public LevelsItem get(int position){
        return objects.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = createView();
        }

        final LevelsItem level = get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.position = position;
        holder.title.setText(level.title);

        holder.checkbox.setChecked(level.checked);

        return convertView;
    }

    private View createView(){
        View convertView = LayoutInflater.from(getContext()).inflate(this.layoutResource, null);

        final ViewHolder holder = new ViewHolder();
        convertView.setTag(holder);

        holder.title = convertView.findViewById(R.id.title_level);
        holder.checkbox = convertView.findViewById(R.id.checkbox);

        return convertView;
    }
}