package metropolitan.com.student6;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import metropolitan.com.student6.data.DatabaseContract;
import metropolitan.com.student6.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener
{
    private EditText mIndexET;
    private EditText mImeET;
    private EditText mPoeniET;
    private Button btn;

    private ListAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndexET = (EditText) findViewById(R.id.indeksET);
        mImeET = (EditText) findViewById(R.id.imeET);
        mPoeniET = (EditText) findViewById(R.id.poeniET);
        btn = (Button) findViewById(R.id.dodajBtn);

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
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
                viewHolder = (ListAdapter.ItemViewHolder)viewHolder;
                int id = Integer.parseInt(((ListAdapter.ItemViewHolder) viewHolder).indeksTV.getText().toString());
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
        int indeks = Integer.parseInt(viewHolder.indeksTV.getText().toString());
        String ime = viewHolder.imeTV.getText().toString();
        int poeni = Integer.parseInt(viewHolder.poeniTV.getText().toString());

        mIndexET.setText(String.valueOf(indeks));
        mImeET.setText(ime);
        mPoeniET.setText(String.valueOf(poeni));

        mIndexET.setFocusable(false);
        mIndexET.setClickable(false);
        btn.setText("Izmeni");
    }

    public void btnClick(View v)
    {
        Button b = (Button)v;
        if(b.getText().toString().equals("Dodaj"))
        {
            addToList();
        }
        else if(b.getText().toString().equals("Izmeni"))
        {
            updateItem();
        }
    }

    public void addToList()
    {
        if(mIndexET.length() == 0 || mImeET.length() == 0 || mPoeniET.length() == 0)
        {
            return;
        }

        int indeks = 1;
        int poeni = 1;
        try
        {
            indeks = Integer.parseInt(mIndexET.getText().toString());
            poeni = Integer.parseInt(mPoeniET.getText().toString());
        }
        catch (NumberFormatException ex) {
            Log.e("NE VALJA DODAVANJE!", "Failed to parse party size text to number: " + ex.getMessage());
        }

        dbAddRow(indeks, mImeET.getText().toString(), poeni);
        mAdapter.swapCursor(dbSelectAllBy());

        mIndexET.getText().clear();
        mIndexET.clearFocus();
        mImeET.getText().clear();
        mImeET.clearFocus();
        mPoeniET.getText().clear();
        mPoeniET.clearFocus();
    }

    public void updateItem()
    {
        if(mIndexET.length() == 0 || mImeET.length() == 0 || mPoeniET.length() == 0)
        {
            return;
        }

        int indeks = Integer.parseInt(mIndexET.getText().toString());
        String ime = mImeET.getText().toString();
        int poeni = 1;
        try
        {
            poeni = Integer.parseInt(mPoeniET.getText().toString());
        }
        catch (NumberFormatException ex) {
            Log.e("NE VALJA UPDATE!", "Failed to parse party size text to number: " + ex.getMessage());
        }

        dbUpdateRow(indeks, ime, poeni);
        mAdapter.swapCursor(dbSelectAllBy());

        mIndexET.setFocusableInTouchMode(true);
        mIndexET.setFocusable(true);
        mIndexET.setClickable(true);

        btn.setText("Dodaj");

        mIndexET.getText().clear();
        mIndexET.clearFocus();
        mImeET.getText().clear();
        mImeET.clearFocus();
        mPoeniET.getText().clear();
        mPoeniET.clearFocus();
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
                DatabaseContract.DatabaseEntry.COLUMN_POENI + " DESC"
        );
    }

    private long dbAddRow(int indeks, String ime, int poeni)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.COLUMN_INDEKS, indeks);
        values.put(DatabaseContract.DatabaseEntry.COLUMN_IME, ime);
        values.put(DatabaseContract.DatabaseEntry.COLUMN_POENI, poeni);
        return mDatabase.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
    }

    private boolean dbRemoveRow(int id)
    {
        return (mDatabase.delete(DatabaseContract.DatabaseEntry.TABLE_NAME,
        DatabaseContract.DatabaseEntry.COLUMN_INDEKS + "=" + id, null)) > 0; //vece od nule jer delete vraca broj izmenjenih redova
    }

    private boolean dbUpdateRow(int indeks, String ime, int poeni)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.DatabaseEntry.COLUMN_IME, ime);
        cv.put(DatabaseContract.DatabaseEntry.COLUMN_POENI, poeni);
        return mDatabase.update(DatabaseContract.DatabaseEntry.TABLE_NAME, cv,DatabaseContract.DatabaseEntry.COLUMN_INDEKS+"=" + indeks, null) > 0;
    }


}
