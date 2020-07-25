package com.zcc.nowatermark.pickvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.utils.StaticFinalValues;
import com.zcc.nowatermark.pickvideo.beans.AudioFile;
import com.zcc.nowatermark.pickvideo.beans.Directory;
import com.zcc.nowatermark.pickvideo.callback.FilterResultCallback;
import com.zcc.nowatermark.pickvideo.itemDecoration.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class AudioPickActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AudioListAdapter mAdapter;
    private ArrayList<AudioFile> audioList = new ArrayList<>();
    private TextView tvback;


    @Override
    void permissionGranted() {
        loadData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏半透明
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_audio_pick);
        initView();
    }

    private void initView() {
        tvback = (TextView) findViewById(R.id.tv_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_video_pick);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        loadData();

    }

    private void loadData() {
        FileFilter.getAudios(this, new FilterResultCallback<AudioFile>() {
            @Override
            public void onResult(List<Directory<AudioFile>> directories) {
                for(Directory<AudioFile> file:directories){
                    for(AudioFile audioFile:file.getFiles()){
                        if(audioFile.getDuration()>0){
                            audioList.add(audioFile);
                        }
                    }

                }
                mAdapter = new AudioListAdapter(AudioPickActivity.this, audioList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setListener(new AudioListAdapter.AudioListListener() {
                    @Override
                    public void onTryClick(AudioFile directory) {

                    }

                    @Override
                    public void onUseClick(AudioFile directory) {
                        Intent intent = new Intent();
                        intent.putExtra(StaticFinalValues.RESULT_PICK_AUDIO,
                                directory);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });
    }



}
