package com.yeabkalwubshit.watchbible;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public abstract class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {
    private List<GenericTableCellData> items;
    private Context context;
    private int id;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mainText;

        public ViewHolder(View v) {
            super(v);
            mainText = v.findViewById(R.id.main_text);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void add(int position, GenericTableCellData chapter) {
        items.add(position, chapter);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public GenericAdapter(List<GenericTableCellData> myDataset, Context ctxt) {
        items = myDataset;
        context = ctxt;
    }

    @Override
    public GenericAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.generic_table_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GenericTableCellData item = items.get(position);
        holder.mainText.setText(item.toString());
        holder.mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericAdapter.this.onClick(item.toString());
            }
        });

        if (id == Consts.ADAPTER_ID_CHAPTER_DISPLAY) {
            holder.mainText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Press check", Toast.LENGTH_SHORT).show();
                    VerseSaver.saveVerse(item.getVerse());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    abstract public void onClick(String itemClicked);
}