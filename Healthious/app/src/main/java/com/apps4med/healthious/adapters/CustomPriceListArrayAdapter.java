package com.apps4med.healthious.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps4med.healthious.R;
import com.apps4med.healthious.model.HealthPrice;

import java.util.List;

/**
 * Created by iskitsas on 26/10/2014.
 */
public class CustomPriceListArrayAdapter extends ArrayAdapter<HealthPrice> {
    private final LayoutInflater mInflater;
    private ShowListener listener;
    private Context context;

    public CustomPriceListArrayAdapter(Context context, ShowListener listener) {
        super(context, android.R.layout.simple_list_item_2);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<HealthPrice> data) {
        clear();

        if (data != null) {
            for (HealthPrice appEntry : data) {
                add(appEntry);
            }
        }
    }

    /**
     * Populate new items in the list.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }

        HealthPrice item = getItem(position);
        TextView liPriceTextView = (TextView) view.findViewById(R.id.liPrice);
        TextView liDescriptionTextView = (TextView) view.findViewById(R.id.liDescription);
        TextView liHospitalizationTextView = (TextView) view.findViewById(R.id.liHospitalization);
        TextView liIdTextView = (TextView) view.findViewById(R.id.liId);
        ImageView showImageView = (ImageView) view.findViewById(R.id.showImageView);
        showImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.show(position);
            }
        });

        liPriceTextView.setText(item.getPrice()+" &euro;");
        liHospitalizationTextView.setText( item.getHospitalization());
        liIdTextView.setText(item.getId());

        String description = item.getDescription();

        if (description != null && !description.equals("")) {
            liDescriptionTextView.setVisibility(View.VISIBLE);
            liDescriptionTextView.setText(item.getDescription());
        } else {
            liDescriptionTextView.setVisibility(View.GONE);

        }
        return view;
    }
}
