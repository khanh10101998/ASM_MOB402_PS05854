package com.example.mrrs.mob402_asm_ps05854;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrrs.mob402_asm_ps05854.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Han on 29/12/2016.
 */
public class AdapterProduct extends BaseAdapter {

    Context context;
    ArrayList<Product> listProducts;

    public AdapterProduct(Context context, ArrayList<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return listProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder {

        TextView tvTime, tvName, tvPrice;
        ImageView ivCart;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item_cart, null);
            viewHolder.tvTime = view.findViewById(R.id.item_cart_time);
            viewHolder.tvName = view.findViewById(R.id.item_cart_name);
            viewHolder.tvPrice = view.findViewById(R.id.item_cart_price);
            viewHolder.ivCart =  view.findViewById(R.id.item_cart_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = listProducts.get(i);
        viewHolder.tvTime.setText("com soon !");
        viewHolder.tvName.setText(product.getNameProduct());
        viewHolder.tvPrice.setText(product.getPriceProduct());
        Picasso.with(context).load(product.getImageProduct())
                .placeholder(R.drawable.ic_place_holder)
                .resize(250,100)
                .error(R.drawable.ic_error)
                .into(viewHolder.ivCart);
        return view;
    }
}
