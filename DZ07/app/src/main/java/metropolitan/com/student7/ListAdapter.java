package metropolitan.com.student7;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import metropolitan.com.student7.data.DataContract;

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

        long id = mCursor.getLong(mCursor.getColumnIndex(DataContract.PredmetiEntry._ID));
        int sifra = mCursor.getInt(mCursor.getColumnIndex(DataContract.PredmetiEntry.COLUMN_SIFRA));
        String naziv = mCursor.getString(mCursor.getColumnIndex(DataContract.PredmetiEntry.COLUMN_NAZIV));
        int poeni = mCursor.getInt(mCursor.getColumnIndex(DataContract.PredmetiEntry.COLUMN_POENI));

        holder.sifraTV.setText(String.valueOf(sifra));
        holder.nazivTV.setText(naziv);
        holder.poeniTV.setText(String.valueOf(poeni));
        holder.itemView.setTag(id);
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
        TextView sifraTV;
        TextView nazivTV;
        TextView poeniTV;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            sifraTV = (TextView)itemView.findViewById(R.id.itemSifraTV);
            nazivTV = (TextView)itemView.findViewById(R.id.itemNazivTV);
            poeniTV = (TextView)itemView.findViewById(R.id.itemPoeniTV);
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
