package com.android.pmk.note_pad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.pmk.note_pad.db.Note;
import com.android.pmk.note_pad.db.NoteUtil;

import java.util.Random;

public class NoteActivity extends AppCompatActivity implements OnColorChangeListener{

    private Toolbar mToolbar;
    private EditText mTitleET;
    private EditText mContentET;
    private Button mOkButton;
    private Button mDeleteButton;
    private Button mColorButton;
    private Note mNote;
    private Spinner mSpinner;


    private int[] colors = new int[]{
            R.color.green,R.color.blue,R.color.red,R.color.yellow,R.color.pink,R.color.purple
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initData();
        initView();
    }

    private void initData(){
        mToolbar = (Toolbar) findViewById(R.id.note_toolbar);
        mTitleET = (EditText) findViewById(R.id.title_edit_view);
        mContentET = (EditText) findViewById(R.id.content_edit_view);
        mOkButton = (Button) findViewById(R.id.ok_button);
        mDeleteButton = (Button) findViewById(R.id.delete_button);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mColorButton = (Button) findViewById(R.id.color_button);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null){

            int id = bundle.getInt("id");
            int color = bundle.getInt("color");
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            long time = bundle.getLong("time");
            String type = bundle.getString("type");

            mNote = new Note(id,title,content,time,type);
            mNote.setColor(color);
            mTitleET.setText(mNote.getTitle());
            mContentET.setText(mNote.getContent());

            mSpinner.setSelection(typeCheck(mNote.getType()));
            mSpinner.post(new Runnable() {
                @Override
                public void run() {
                    mSpinner.setSelection(typeCheck(mNote.getType()));
                }
            });

        }else {

            mNote = new Note();
            mTitleET.setText("");
            mContentET.setText("");
            mNote.setColor(colors[new Random().nextInt(6)]);
            mSpinner.setSelection(0);

        }

    }

    private void initView(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){
                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(NoteActivity.this).updateNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(NoteActivity.this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }

                }else {

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){

                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(NoteActivity.this).addNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(NoteActivity.this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){
                    NoteUtil.getsNoteUtil(NoteActivity.this).deleteNote(mNote.getId());
                    finish();
                }else {
                    mTitleET.setText("");
                    mContentET.setText("");
                }
            }
        });

        mColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDialogFragment fragment = ColorDialogFragment.newInstance(mNote);
                fragment.setOnColorChangeListener(NoteActivity.this);
                fragment.show(getSupportFragmentManager(),"color");
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //将选择的元素显示出来
                String selected = parent.getItemAtPosition(position).toString();
                if(mNote != null){
                    mNote.setType(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private int typeCheck(String type){
        Log.i("test","type " + type);
        if(type == null)
            return 0;
        String[] types = getResources().getStringArray(R.array.type);
        for (int i = 0;i<types.length; i++){
            if(types[i].equals(type)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onColorChange() {
        if(mNote != null)
        {
           Toast.makeText(this,"修改背景颜色成功",Toast.LENGTH_SHORT).show();
        }
    }
}
