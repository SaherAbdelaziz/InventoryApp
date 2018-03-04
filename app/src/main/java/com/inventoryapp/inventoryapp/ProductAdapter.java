package com.inventoryapp.inventoryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SaherOs on 3/1/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {
    private Context context = null;
    private ArrayList<MyProduct> productArrayList = null;
    private static RecyclerViewClickListener itemClickListener = null;

    public ProductAdapter(Context context, ArrayList<MyProduct> productArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.itemClickListener = listener;
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewItemClick(int position);
        public void itemButtonClick(int position);
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        MyProduct product = productArrayList.get(position);
        holder.txtName.setText(product.getName());
        holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
        holder.txtPrice.setText(String.valueOf(product.getPrice()));
    }



    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        TextView txtQuantity;
        TextView txtPrice;
        Button sell;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.textName);
            txtQuantity = (TextView) itemView.findViewById(R.id.textQuantity);
            txtPrice = (TextView) itemView.findViewById(R.id.textPrice);
            sell = (Button) itemView.findViewById(R.id.sellButton);
            itemView.setOnClickListener(this);
            sell.setOnClickListener(this);
            txtName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == sell.getId()) {
                itemClickListener.itemButtonClick(getLayoutPosition());
            } else {
                itemClickListener.recyclerViewItemClick(getLayoutPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

}
