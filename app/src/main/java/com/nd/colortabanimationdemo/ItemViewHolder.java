package com.nd.colortabanimationdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text)
    TextView text;

    public ItemViewHolder(Context context, ViewGroup viewGroup) {
        super(LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

}
