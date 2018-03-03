package com.inventoryapp.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SaherOs on 3/1/2018.
 */

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView my_img = null;
    private TextView my_name = null, my_price = null, my_amount_of_items = null, my_supplier = null;
    private Button increase= null, order = null,decrease = null,  delete = null;
    private MyProduct product = null;
    private MyDatabase data_base = null;
    private Intent myIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        product = (MyProduct) intent.getSerializableExtra("selectedProduct");

        data_base = new MyDatabase(this);

        my_img= (ImageView) findViewById(R.id.productImgView);
        my_name = (TextView) findViewById(R.id.productNameTxtView);
        my_amount_of_items = (TextView) findViewById(R.id.productQuantityTxtView);
        my_price = (TextView) findViewById(R.id.productPriceTxtView);
        my_supplier = (TextView) findViewById(R.id.supplierTxtView);


        my_img.setImageBitmap(BitmapFactory.decodeFile(product.getImage()));
        my_name.setText(product.getName());
        my_amount_of_items.setText(String.valueOf(product.getQuantity()));
        my_price.setText(String.valueOf(product.getPrice()));
        my_supplier.append(product.getSupplierMail());

        increase = (Button) findViewById(R.id.buttonIncrease);
        increase.setOnClickListener(this);

        decrease= (Button) findViewById(R.id.buttonDecrease);
        decrease.setOnClickListener(this);

        order = (Button) findViewById(R.id.orderButton);
        order.setOnClickListener(this);

        delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonIncrease:
                product.setQuantity(product.getQuantity() + 1);
                my_amount_of_items.setText(String.valueOf(product.getQuantity()));
                data_base.modify_product(product.getMyId(), product.getQuantity());
                break;
            case R.id.buttonDecrease:
                if (product.getQuantity() > 0) {
                    product.setQuantity(product.getQuantity() - 1);
                    my_amount_of_items.setText(String.valueOf(product.getQuantity()));
                    data_base.modify_product(product.getMyId(), product.getQuantity());
                }
                break;
            case R.id.orderButton:
                myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setData(Uri.parse("mail to"));
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{product.getSupplierMail()});
                myIntent.putExtra(Intent.EXTRA_SUBJECT, "Order ");
                myIntent.putExtra(Intent.EXTRA_TEXT, "Product Name is " + product.getName() + "\n" +
                        "amount of items are " + 50 + "\n");
                startActivity(Intent.createChooser(myIntent, "select app"));
                break;
            case R.id.deleteButton:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete ?\n\n"+ product.getName())
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                data_base.deleteProduct(product.getMyId());
                                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
    }

}
