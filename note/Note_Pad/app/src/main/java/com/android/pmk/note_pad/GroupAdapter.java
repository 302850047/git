package com.android.pmk.note_pad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pmk.note_pad.db.Note;
import com.android.pmk.note_pad.db.NoteEntity;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Derrick on 2018/6/30.
 */

public class GroupAdapter extends GroupedRecyclerViewAdapter{


    private List<NoteEntity> mNoteEntities;

    private Context mContext;

    private OnLongItemClickListener mOnLongItemClickListener;
    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public GroupAdapter(Context context,List<NoteEntity> noteEntities,OnLongItemClickListener onLongItemClickListener) {
        super(context);
        this.mContext = context;
        this.mNoteEntities = noteEntities;
        this.mOnLongItemClickListener = onLongItemClickListener;
    }

    public void setNoteEntities(List<NoteEntity> noteEntities) {
        mNoteEntities = noteEntities;
    }

    @Override
    public int getGroupCount() {
        return mNoteEntities == null?0:mNoteEntities.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Note> noteList = mNoteEntities.get(groupPosition).getNote();
        return noteList == null?0:noteList.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.item_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_note;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        NoteEntity entity = mNoteEntities.get(groupPosition);
        Log.i("test","group pos" + groupPosition  + "   type " + entity.getType());

        holder.setText(R.id.header_text_view,entity.getType());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }



    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, final int groupPosition, final int childPosition) {

        holder.get(R.id.linearlayout).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnLongItemClickListener != null){
                    Note note = mNoteEntities.get(groupPosition).getNote().get(childPosition);
                    mOnLongItemClickListener.onLongItemClick(note);
                }
                return false;
            }
        });


        this.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                Log.i("test","click");
                Note note = mNoteEntities.get(groupPosition).getNote().get(childPosition);
                Intent intent = new Intent(mContext,NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",note.getId());
                bundle.putInt("color",note.getColor());
                bundle.putString("title",note.getTitle());
                bundle.putString("content",note.getContent());
                bundle.putLong("time",note.getModifyTime());
                bundle.putString("type",note.getType());
                intent.putExtra("bundle",bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


        Note note = mNoteEntities.get(groupPosition).getNote().get(childPosition);
        holder.setText(R.id.title_text_view,note.getTitle());
        Date date = new Date(note.getModifyTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        holder.setText(R.id.time_text_view,dateString);
        holder.setBackgroundColor(R.id.linearlayout,mContext.getResources().getColor(note.getColor()));

    }




}
