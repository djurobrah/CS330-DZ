package com.example.djuricadjuricic.cs330_juna;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.djuricadjuricic.cs330_juna.data.DatabaseContract;
import com.example.djuricadjuricic.cs330_juna.data.DatabaseHelper;

public class DrugiFragment extends Fragment implements View.OnClickListener, ListAdapter.ListItemClickListener{

    LinearLayout ll;

    private EditText mIndexET;
    private EditText mImeET;
    private EditText mPrezimeET;
    private Button btn;
    private Button nextButton;

    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private RecyclerView recyclerView;

    public DrugiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drugi, container, false);
        ll = (LinearLayout) rootView.findViewById(R.id.ll2);

        mIndexET = (EditText)  rootView.findViewById(R.id.indeksET);
        mImeET = (EditText)  rootView.findViewById(R.id.imeET);
        mPrezimeET = (EditText)  rootView.findViewById(R.id.prezimeET);
        btn = (Button)  rootView.findViewById(R.id.dodajBtn);
        btn.setOnClickListener(this);
        nextButton = (Button)  rootView.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DatabaseHelper databaseHelper = new DatabaseHelper(this.getContext());
        mDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = dbSelectAllBy();
        mAdapter = new ListAdapter(cursor, this.getContext(), this);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private Cursor dbSelectAllBy()
    {
        return mDatabase.query(
                DatabaseContract.DatabaseEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.DatabaseEntry.COLUMN_INDEKS + " DESC"
        );
    }

    private long dbAddRow(int indeks, String ime, String prezime)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.COLUMN_INDEKS, indeks);
        values.put(DatabaseContract.DatabaseEntry.COLUMN_IME, ime);
        values.put(DatabaseContract.DatabaseEntry.COLUMN_PREZIME, prezime);
        return mDatabase.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
    }

    public void changeBackground()
    {
        ll.setBackgroundColor(Color.RED);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.dodajBtn)
        {
            addToList();
        }
        if(view.getId() == R.id.nextBtn)
        {
            Intent i = new Intent(this.getContext(), DrugiActivity.class);
            startActivity(i);
        }

    }

    public void addToList()
    {
        if(mIndexET.length() == 0 || mImeET.length() == 0 || mPrezimeET.length() == 0)
        {
            return;
        }

        int indeks = 1;
        try
        {
            indeks = Integer.parseInt(mIndexET.getText().toString());
        }
        catch (NumberFormatException ex) {
            Log.e("NE VALJA DODAVANJE!", "Failed to parse party size text to number: " + ex.getMessage());
        }

        if(!checkAlreadyExist(indeks))
        {
            Toast.makeText(this.getContext(), "POSTOJI", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dbAddRow(indeks, mImeET.getText().toString(), mPrezimeET.getText().toString());
            mAdapter.swapCursor(dbSelectAllBy());

            mIndexET.getText().clear();
            mIndexET.clearFocus();
            mImeET.getText().clear();
            mImeET.clearFocus();
            mPrezimeET.getText().clear();
            mPrezimeET.clearFocus();
        }
    }

    public boolean checkAlreadyExist(int indeks)
    {
        String query = "SELECT " + "INDEKS FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.DatabaseEntry.COLUMN_INDEKS + " =?";
        Cursor cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(indeks)});
        if (cursor.getCount() > 0)
        {
            return false;
        }
        else
            return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
