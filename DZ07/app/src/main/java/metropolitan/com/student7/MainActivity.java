package metropolitan.com.student7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import metropolitan.com.student7.data.DataContract;
import metropolitan.com.student7.data.DataHelper;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener
{
    private EditText mSifraET;
    private EditText mNazivET;
    private EditText mPoeniET;
    private Button btn;

    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private RecyclerView recyclerView;

    private long selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSifraET = (EditText) findViewById(R.id.sifraET);
        mNazivET = (EditText) findViewById(R.id.nazivET);
        mPoeniET = (EditText) findViewById(R.id.poeniET);
        btn = (Button) findViewById(R.id.dodajBtn);

        selectedID = -1;

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DataHelper databaseHelper = new DataHelper(this);
        mDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = dbSelectAllBy();
        mAdapter = new ListAdapter(cursor, this, this);
        recyclerView.setAdapter(mAdapter);

        //resolvovanje svajpovanja u desno i levo
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                long id = (long)viewHolder.itemView.getTag();
                dbRemoveRow(id);
                mAdapter.swapCursor(dbSelectAllBy());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        View v = recyclerView.getLayoutManager().findViewByPosition(clickedItemIndex);
        ListAdapter.ItemViewHolder viewHolder = (ListAdapter.ItemViewHolder) recyclerView.getChildViewHolder(v);
        selectedID = (long)viewHolder.itemView.getTag();
        int sifra = Integer.parseInt(viewHolder.sifraTV.getText().toString());
        String naziv = viewHolder.nazivTV.getText().toString();
        int poeni = Integer.parseInt(viewHolder.poeniTV.getText().toString());

        mSifraET.setText(String.valueOf(sifra));
        mNazivET.setText(naziv);
        mPoeniET.setText(String.valueOf(poeni));
        btn.setText("Izmeni predmet.");
    }

    public void btnClick(View v)
    {
        Button b = (Button)v;
        if(b.getText().toString().equals("Dodaj predmet."))
        {
            addToList();
        }
        else if(b.getText().toString().equals("Izmeni predmet."))
        {
            updateItem();
        }
    }

    public void addToList()
    {
        if(mSifraET.length() == 0 || mNazivET.length() == 0 || mPoeniET.length() == 0)
        {
            return;
        }

        int sifra = 1;
        int poeni = 1;
        try
        {
            sifra = Integer.parseInt(mSifraET.getText().toString());
            poeni = Integer.parseInt(mPoeniET.getText().toString());
        }
        catch (NumberFormatException ex)
        {
            Log.e("NE VALJA DODAVANJE!", "Failed to parse party size text to number: " + ex.getMessage());
        }

        dbAddRow(sifra, mNazivET.getText().toString(), poeni);
        mAdapter.swapCursor(dbSelectAllBy());

        mSifraET.getText().clear();
        mSifraET.clearFocus();
        mNazivET.getText().clear();
        mNazivET.clearFocus();
        mPoeniET.getText().clear();
        mPoeniET.clearFocus();
    }

    public void updateItem()
    {
        if(mSifraET.length() == 0 || mNazivET.length() == 0 || mPoeniET.length() == 0)
        {
            return;
        }

        int sifra = 1;
        String naziv = mNazivET.getText().toString();
        int poeni = 1;
        try
        {
            sifra = Integer.parseInt(mSifraET.getText().toString());
            poeni = Integer.parseInt(mPoeniET.getText().toString());
        }
        catch (NumberFormatException ex)
        {
            Log.e("NE VALJA UPDATE!", "Failed to parse party size text to number: " + ex.getMessage());
        }

        dbUpdateRow(selectedID, sifra, naziv, poeni);
        mAdapter.swapCursor(dbSelectAllBy());

        mSifraET.setFocusableInTouchMode(true);
        mSifraET.setFocusable(true);
        mSifraET.setClickable(true);

        btn.setText("Dodaj predmet.");

        mSifraET.getText().clear();
        mSifraET.clearFocus();
        mNazivET.getText().clear();
        mNazivET.clearFocus();
        mPoeniET.getText().clear();
        mPoeniET.clearFocus();
        selectedID = -1;
    }

    private Cursor dbSelectAllBy()
    {
        return getContentResolver().query(DataContract.PredmetiEntry.CONTENT_URI, null, null, null, DataContract.PredmetiEntry.COLUMN_SIFRA);
    }

    private void dbAddRow(int sifra, String naziv, int poeni)
    {
        ContentValues values = new ContentValues();
        values.put(DataContract.PredmetiEntry.COLUMN_SIFRA, sifra);
        values.put(DataContract.PredmetiEntry.COLUMN_NAZIV, naziv);
        values.put(DataContract.PredmetiEntry.COLUMN_POENI, poeni);
        Uri uri = getContentResolver().insert(DataContract.PredmetiEntry.CONTENT_URI, values);

        if(uri != null)
        {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean dbRemoveRow(long id)
    {
        String idString = Long.toString(id);
        Uri uri = DataContract.PredmetiEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        return getContentResolver().delete(uri, null, null) > 0;
    }

    private boolean dbUpdateRow(long id, int sifra, String naziv, int poeni)
    {
        String idString = Long.toString(id);
        Uri uri = DataContract.PredmetiEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        ContentValues values = new ContentValues();
        values.put(DataContract.PredmetiEntry.COLUMN_SIFRA, sifra);
        values.put(DataContract.PredmetiEntry.COLUMN_NAZIV, naziv);
        values.put(DataContract.PredmetiEntry.COLUMN_POENI, poeni);

        return getContentResolver().update(uri, values, null, null) > 0;
    }


}
