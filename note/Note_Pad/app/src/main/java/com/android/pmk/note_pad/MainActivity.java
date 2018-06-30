package com.android.pmk.note_pad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.pmk.note_pad.db.Note;
import com.android.pmk.note_pad.db.NoteEntity;
import com.android.pmk.note_pad.db.NoteUtil;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLongItemClickListener,OnColorChangeListener{

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private MaterialSearchView mSearchView;
    private GroupAdapter mGroupAdapter;
    private List<NoteEntity> mNoteEntityList;
    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mNoteEntityList = changeData(NoteUtil.getsNoteUtil(this).getNotes());
    }
    /**
     * 初始化
     */
    private void init(){

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("笔记");

        initSearchView();

        //RecyclerView

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        mGroupAdapter = new GroupAdapter(this,mNoteEntityList,this);

        mRecyclerView.setAdapter(mGroupAdapter);
        registerForContextMenu(mRecyclerView);


    }

    /**
     * 初始化搜索框
     */
    private void initSearchView(){

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<NoteEntity> queryList = changeData(NoteUtil.getsNoteUtil(MainActivity.this).search(newText));
                mGroupAdapter.setNoteEntities(queryList);
                mGroupAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                updateUI();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    /**
     * 更新UI
     */
    private void updateUI(){

        List<NoteEntity> noteList = changeData(NoteUtil.getsNoteUtil(this).getNotes());
        mNoteEntityList = noteList;
        if(mGroupAdapter == null){
            mGroupAdapter = new GroupAdapter(this,noteList,this);
            mRecyclerView.setAdapter(mGroupAdapter);
        }else {
            if(mGroupAdapter.getItemCount() != noteList.size()){
                mGroupAdapter = new GroupAdapter(this,noteList,this);
                mRecyclerView.setAdapter(mGroupAdapter);
            }else {
                mGroupAdapter.setNoteEntities(noteList);
                mGroupAdapter.notifyDataSetChanged();
            }
        }
        mGroupAdapter.notifyDataSetChanged();
    }

    private List<NoteEntity> changeData(List<Note> noteList){

        List<NoteEntity> noteEntityList = new ArrayList<>();

        NoteEntity entity1 = new NoteEntity();
        NoteEntity entity2 = new NoteEntity();
        NoteEntity entity3 = new NoteEntity();
        NoteEntity entity4 = new NoteEntity();
        NoteEntity entity5 = new NoteEntity();
        NoteEntity entity6 = new NoteEntity();

        for (Note note : noteList){
            switch (note.getType()){
                case "默认":
                    entity1.getNote().add(note);
                    entity1.setType(note.getType());
                    break;
                case "生活":
                    entity2.getNote().add(note);
                    entity2.setType(note.getType());
                    break;
                case "情感":
                    entity3.getNote().add(note);
                    entity3.setType(note.getType());
                    break;
                case "家庭":
                    entity4.getNote().add(note);
                    entity4.setType(note.getType());
                    break;
                case "校园":
                    entity5.getNote().add(note);
                    entity5.setType(note.getType());
                    break;
                case "学习":
                    entity6.getNote().add(note);
                    entity6.setType(note.getType());
                    break;
            }
        }
        if(entity1.getNote().size() > 0)
        noteEntityList.add(entity1);
        if(entity2.getNote().size() > 0)
        noteEntityList.add(entity2);
        if(entity3.getNote().size() > 0)
        noteEntityList.add(entity3);
        if(entity4.getNote().size() > 0)
        noteEntityList.add(entity4);
        if(entity5.getNote().size() > 0)
        noteEntityList.add(entity5);
        if(entity6.getNote().size() > 0)
        noteEntityList.add(entity6);

        return noteEntityList;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.long_click_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                NoteUtil.getsNoteUtil(this).deleteNote(mNote.getId());
                Log.i("test","delete");
                updateUI();
                break;
            case R.id.change_bg:
                ColorDialogFragment fragment = ColorDialogFragment.newInstance(mNote);
                fragment.setOnColorChangeListener(this);
                fragment.show(getSupportFragmentManager(),"color");
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }




    @Override
    public void onColorChange() {
        updateUI();
    }

    @Override
    public void onLongItemClick(Note note) {
        this.mNote = note;
    }
}
