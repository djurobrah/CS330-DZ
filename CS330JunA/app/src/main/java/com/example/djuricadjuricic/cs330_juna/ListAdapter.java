package com.example.djuricadjuricic.cs330_juna;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.djuricadjuricic.cs330_juna.data.DatabaseContract;
import com.example.djuricadjuricic.cs330_juna.data.DatabaseHelper;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder>
{
    final private ListItemClickListener mOnClickListener;
    private Context mContext;
    private Cursor mCursor;

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }

    public ListAdapter(Cursor cursor, Context context, ListItemClickListener mOnClickListener)
    {
        this.mContext = context;
        this.mCursor = cursor;
        this.mOnClickListener = mOnClickListener;
    }

    @Override //inflatuje view jednog itema i setupujemo onClick
    public ListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override //setuje podatke za delove itema
    public void onBindViewHolder(ListAdapter.ItemViewHolder holder, int position)
    {
        if (!mCursor.moveToPosition(position))
        {
            return;
        }

        int indeks = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLUMN_INDEKS));
        String ime = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLUMN_IME));
        String prezime = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.DatabaseEntry.COLUMN_PREZIME));

        holder.indeksTV.setText(String.valueOf(indeks));
        holder.imeTV.setText(ime);
        holder.prezimeTV.setText(prezime);
        //setTag() za ness sto se ne vidi ako treba, najcesce ID
    }

    @Override
    public int getItemCount()
    {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) //apdejtuje cursor tj. podatke koji idu u listu
    {
        if (mCursor != null)
        {
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor != null)
        {
            this.notifyDataSetChanged();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener //sam item iz Liste
    {
        TextView indeksTV;
        TextView imeTV;
        TextView prezimeTV;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            indeksTV = (TextView)itemView.findViewById(R.id.itemIndeksTV);
            imeTV = (TextView)itemView.findViewById(R.id.itemImeTV);
            prezimeTV = (TextView)itemView.findViewById(R.id.itemPrezimeTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
