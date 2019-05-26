package com.example.sh.androidregisterandlogin.TotalMusic;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sh.androidregisterandlogin.R;

import java.util.ArrayList;

public class AudioAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    public static ArrayList<Long> audioIds;
    Context context;

    public AudioAdapter(Context context, Cursor cursor) {
        super(cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        AudioItem audioItem = AudioItem.bindCursor(cursor);
        ((AudioViewHolder) viewHolder).setAudioItem(audioItem, cursor.getPosition());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music, viewGroup, false);
//        레이아웃에 관한 것입니다.
        return new AudioViewHolder(view);

    }

    public static class AudioItem {

        public long mId; // 오디오 고유 ID
        public long mAlbumId; // 오디오 앨범아트 ID
        public String mTitle; // 타이틀 정보
        public String mArtist; // 아티스트 정보
        public String mAlbum; // 앨범 정보
        public long mDuration; // 재생시간
        public String mDataPath; // 실제 데이터위치

        public static AudioItem bindCursor(Cursor cursor) {

            AudioItem audioItem = new AudioItem();
            audioItem.mId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));
            audioItem.mAlbumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
            audioItem.mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
            audioItem.mArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
            audioItem.mAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
            audioItem.mDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
            audioItem.mDataPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
            return audioItem;

        }
    }

    public ArrayList<Long> getAudioIds() {

        int count = getItemCount();
        audioIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            audioIds.add(getItemId(i));
        }
        return audioIds;
    }

    private class AudioViewHolder extends RecyclerView.ViewHolder {

        private final Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        private ImageView mImgAlbumArt;
        private TextView mTxtTitle;
        private TextView mTxtSubTitle;
        private TextView mTxtDuration;

        public int mPosition;

        private AudioViewHolder(View view) {

            super(view);
            mImgAlbumArt = view.findViewById(R.id.img_mini);
            mTxtTitle = view.findViewById(R.id.txt_title);
            mTxtSubTitle = view.findViewById(R.id.txt_sub_title);
            mTxtDuration = view.findViewById(R.id.txt_duration);

            view.setOnClickListener(v -> {
                AudioApplication.getInstance().getServiceInterface().setPlayList(getAudioIds()); // 재생목록등록
                AudioApplication.getInstance().getServiceInterface().play(mPosition); // 선택한 오디오재생
                ((TotalMusicActivity) TotalMusicActivity.mContext).updateUI();
                ((TotalMusicActivity) TotalMusicActivity.mContext).updatePlay();
            });
        }

        public void setAudioItem(AudioItem item, int position) {

            mPosition = position;
            mTxtTitle.setText(item.mTitle);
            mTxtSubTitle.setText(item.mArtist + "(" + item.mAlbum + ")");
            mTxtDuration.setText(DateFormat.format("mm:ss", item.mDuration));
            Uri albumArtUri = ContentUris.withAppendedId(artworkUri, item.mAlbumId);
            Log.d("albumArtUri", "setAudioItem: " + albumArtUri);

            RequestOptions circleCrop = new RequestOptions().circleCrop();
            Glide.with(context)
                    .load(albumArtUri).apply(RequestOptions.errorOf(R.drawable.music)).apply(circleCrop)
                    .into(mImgAlbumArt);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}