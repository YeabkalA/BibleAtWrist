package com.yeabkalwubshit.watchbible;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


public abstract class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {
    private List<GenericTableCellData> items;
    private Context applicationContext;
    private int id;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mainText;

        public ViewHolder(View v) {
            super(v);
            int mainTextId = 0;
            switch (id) {
                case Consts.ADAPTER_ID_RANDOM_VERSE_DISPLAY: {
                    mainTextId = R.id.main_text_random_verse_display;
                    break;
                }
                default: {
                    mainTextId = R.id.main_text;
                    break;
                }
            }
            mainText = v.findViewById(mainTextId);
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
        applicationContext = ctxt;
    }

    @Override
    public GenericAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        int viewId = 0;

        switch (id) {
            case Consts.ADAPTER_ID_RANDOM_VERSE_DISPLAY: {
                viewId = R.layout.random_verse_display_cell;
                break;
            }
            default: {
                viewId = R.layout.generic_table_entry;
                break;
            }
        }

        View v = inflater.inflate(viewId, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GenericTableCellData item = items.get(position);
        holder.mainText.setText(item.toString());
//        if (id == Consts.ADAPTER_ID_CHAPTER_DISPLAY &&
//                VerseSaver.isVerseSaved(applicationContext, item.getVerse())) {
//            holder.mainText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//        }
        holder.mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericAdapter.this.onClick(item.toString());
            }
        });

//        if (id == Consts.ADAPTER_ID_CHAPTER_DISPLAY) {
//            holder.mainText.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    VerseSaver.saveVerse(applicationContext, item.getVerse());
//                    Toast.makeText(v.getContext(), "Saved " + item.getVerse().serialize(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    abstract public void onClick(String itemClicked);
}