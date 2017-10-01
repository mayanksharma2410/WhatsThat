package com.mayanksharma.whatsthat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nxp50640 on 01-10-2017.
 */

public class DataList extends ArrayAdapter<Data> {

    private Activity context;
    private List<Data> dataList;

    public DataList(Activity context, List<Data> dataList)
    {   super(context, R.layout.activity_finish, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listItemView = inflater.inflate(R.layout.activity_finish, null, true);

        TextView textView1 = (TextView)listItemView.findViewById(R.id.Course);
        TextView textView2 = (TextView)listItemView.findViewById(R.id.Year);
        TextView textView3 = (TextView)listItemView.findViewById(R.id.Sem);

        Data data = dataList.get(position);

        textView1.setText(data.getCourse());
        textView2.setText(data.getYear());
        textView3.setText(data.getSem());

        return listItemView;
    }
}
