package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.acetumor.R;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;

import java.util.List;

public class TakePhotoActivity extends AppCompatActivity implements MediaAdapter.OnAddMediaListener
        , View.OnClickListener{

    private int REQUEST_CODE = 0x000111;
    private MediaAdapter mMediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getWindow() != null){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_take_photo);
        findViewById(R.id.btn_compress_picture).setOnClickListener(this);
        findViewById(R.id.btn_compress_video).setOnClickListener(this);
        findViewById(R.id.btn_take_picture).setOnClickListener(this);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(TakePhotoActivity.this, 4, GridLayoutManager.VERTICAL, false));
        mMediaAdapter = new MediaAdapter(this);
        recyclerView.setAdapter(mMediaAdapter);
        mMediaAdapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mMediaAdapter.getData().size() > 0) {
                    //预览
                    Phoenix.with()
                            .pickedMediaList(mMediaAdapter.getData())
                            .start(TakePhotoActivity.this, PhoenixOption.TYPE_BROWSER_PICTURE, 0);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_compress_picture:
                startActivity(new Intent(TakePhotoActivity.this, PictureActivity.class));
                break;
            case R.id.btn_take_picture:
                startActivity(new Intent(TakePhotoActivity.this, CameraActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            mMediaAdapter.setData(result);
        }
    }

    @Override
    public void onaddMedia() {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(10)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(true)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(30)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(TakePhotoActivity.this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);
    }
}