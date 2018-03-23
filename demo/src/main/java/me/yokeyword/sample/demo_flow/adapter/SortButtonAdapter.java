package me.yokeyword.sample.demo_flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.yokeyword.sample.R;
import me.yokeyword.sample.demo_flow.entity.ButtonModel;


public class SortButtonAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ButtonModel> data;
    public SortButtonAdapter(Context context, List<ButtonModel> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.item_button, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(position).getName());
        holder.icon.setImageResource(data.get(position).getDrawableIcon());
        return view;
    }
    class ViewHolder {
        ImageView icon;
        TextView name;
    }

}
