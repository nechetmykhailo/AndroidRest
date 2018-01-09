package com.example.mixazp.androidrest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mixazp.androidrest.R;
import com.example.mixazp.androidrest.model.FinalProduct;

import java.util.List;

public class RecyclerAdapterFinalProduct extends RecyclerView.Adapter<RecyclerAdapterFinalProduct.ViewHolderAdapter> {

    private List<FinalProduct> productList;
    private Context context;

    public RecyclerAdapterFinalProduct(Context context, List<FinalProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public RecyclerAdapterFinalProduct.ViewHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_reviews, parent, false);
        return new ViewHolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterFinalProduct.ViewHolderAdapter holder, int position) {

        holder.tv1.setText("Login: " + productList.get(position).getCreatedBy().getUsername());
        holder.tv2.setText("Rate: " + productList.get(position).getRate().toString());
        holder.tv3.setText("Comment: " + productList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {

        private TextView tv1;
        private TextView tv2;
        private TextView tv3;

        public ViewHolderAdapter(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
        }
    }
}
