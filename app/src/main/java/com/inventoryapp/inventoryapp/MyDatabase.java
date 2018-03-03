package com.inventoryapp.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SaherOs on 3/1/2018.
 */

class MyDatabase extends SQLiteOpenHelper {
    private Context context = null;

    public MyDatabase(Context context) {
        super(context, Contract.DB_NAME, null, Contract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Contract.Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL(Contract.Table.DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public void addProduct(MyProduct product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Table.COLUMN_NAME, product.getName());
        values.put(Contract.Table.COLUMN_QUANTITY, product.getQuantity());
        values.put(Contract.Table.COLUMN_PRICE, product.getPrice());
        values.put(Contract.Table.COLUMN_IMAGE, product.getImage());
        values.put(Contract.Table.COLUMN_SUPPLIER_MAIL, product.getSupplierMail());

        sqLiteDatabase.insert(Contract.Table.TABLE_PRODUCTS, null, values);
        sqLiteDatabase.close();
    }

    private Cursor read() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {Contract.Table.COLUMN_ID, Contract.Table.COLUMN_NAME, Contract.Table.COLUMN_QUANTITY,
                Contract.Table.COLUMN_PRICE, Contract.Table.COLUMN_IMAGE, Contract.Table.COLUMN_SUPPLIER_MAIL};

        Cursor cursor = sqLiteDatabase.query(Contract.Table.TABLE_PRODUCTS, columns, null, null, null, null, null);

        return cursor;
    }

    public ArrayList<MyProduct> readAllProducts() {
        ArrayList<MyProduct> productsList = new ArrayList<MyProduct>();

        Cursor cursor = read();
        if (cursor.moveToFirst()) {
            do {
                MyProduct product = new MyProduct();
                product.setMyId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setQuantity(cursor.getInt(2));
                product.setPrice(cursor.getInt(3));
                product.setImage(cursor.getString(4));
                product.setSupplierMail(cursor.getString(5));
                productsList.add(product);
            } while (cursor.moveToNext());
        }

        return productsList;
    }

    public void modify_product(int id, int quantity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Table.COLUMN_QUANTITY, quantity);
        sqLiteDatabase.update(Contract.Table.TABLE_PRODUCTS, contentValues, Contract.Table.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(Contract.Table.TABLE_PRODUCTS, Contract.Table.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }

}
