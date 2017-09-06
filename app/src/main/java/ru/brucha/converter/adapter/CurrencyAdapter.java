package ru.brucha.converter.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import ru.brucha.converter.R;
import ru.brucha.converter.entity.Valute;

/**
 * Created by prog on 06.09.2017.
 */

public class CurrencyAdapter extends BaseAdapter {
    List<Valute> valutes;

    public CurrencyAdapter(List<Valute> valutes) {
        this.valutes = valutes;
    }

    @Override
    public int getCount() {
        return valutes.size();
    }

    @Override
    public Object getItem(int position) {
        return valutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.spiner_item, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.text);
            holder.description = convertView.findViewById(R.id.description);
            holder.rate = convertView.findViewById(R.id.rate);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(valutes.get(position).getCharCode());
        holder.description.setText(valutes.get(position).getName());
        //holder.rate.setText(valutes.get(position).getValue());
        convertView.setTag(holder);
        return convertView;
    }

    private class ViewHolder{
        TextView text;
        TextView description;
        TextView rate;
    }
}
