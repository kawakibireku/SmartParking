package iottelkom.smartparking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kawakibireku on 10/26/17.
 */

public class MyAdapter extends ArrayAdapter {

    ArrayList<Device> devlist = new ArrayList<>();

    public MyAdapter(Context context,int textViewRecourceId, ArrayList objects){
        super(context, textViewRecourceId, objects);
        devlist = objects;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.devicelist, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(devlist.get(position).getdeviceName());
        imageView.setImageResource(devlist.get(position).getdeviceImage());
        return v;
    }



}
