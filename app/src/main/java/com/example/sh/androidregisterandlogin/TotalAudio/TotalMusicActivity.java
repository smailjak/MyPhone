package com.example.sh.androidregisterandlogin.TotalAudio;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.sh.androidregisterandlogin.ToTalHome.CollectActivity;
import com.example.sh.androidregisterandlogin.R;
import com.squareup.picasso.Picasso;


public class TotalMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int LOADER_ID = 0x001;

    private RecyclerView mRecyclerView;
    public static AudioAdapter mAdapter; // 질문 .
    public static Context mContext;

    public int musicCountVoiceResult = 0;
    private int music_count = 0;
    //  앨범사진
    private ImageView mImgAlbumArt;
    //  음악 제목
    private TextView mTxtTitle;
    //   음악의 총개수가 적히는 setText 부분
    TextView music_number;
    private ImageButton mBtnPlayPause;
    LinearLayout lin_miniplayer;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };// 이거는 필요없는 것 같은데 ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_music);

        mContext = this;
        // OS가 Marshmallow 이상일 경우 권한체크를 해야 합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                // READ_EXTERNAL_STORAGE 에 대한 권한이 있음.
                getAudioListFromMediaDatabase();
            }
        }
        // OS가 Marshmallow 이전일 경우 권한체크를 하지 않는다.
        else {
            getAudioListFromMediaDatabase();
        }

        lin_miniplayer = findViewById(R.id.lin_miniplayer);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new AudioAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        music_number = findViewById(R.id.music_number);
        mImgAlbumArt = (ImageView) findViewById(R.id.img_albumart);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mBtnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        findViewById(R.id.lin_miniplayer).setOnClickListener(this);
        findViewById(R.id.btn_rewind).setOnClickListener(this);
        mBtnPlayPause.setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);


        Intent intent = getIntent();
        int start = intent.getIntExtra("music_start", 0);
//        여기부분은 "노래재생" , "음악재생" 이라고 말했을경우에 여기로 들어와서 실행시키게 할려고 만들었습니다.
//        키값으로 받아오는것이 만약에 없다면 defaultValue를 사용하게된다 .==> start ==1 이 아니라서 못들어 오게된다 .
        Log.d("TotalMusicActivity.qwe", "start : " + start);

        if (start == 1) {
            Log.d("TotalMusicActivity.qwer", "1");
            AudioApplication.getInstance().getServiceInterface().voice_togglePlay();
            Log.d("TotalMusicActivity.qwer", "2");
        }

        String music_title = intent.getStringExtra("music_title");
//        여기는 "노래틀어줘" , "음악틀어줘" 라고 말했을경우 노래혹은 음악이 실행되게 하기위해서 만들었습니다.
//        "뭐뭐 틀어줘~" 이것은 원래 제목을 얘기해야하지만 , CollectActivity 에서 저는 "틀어줘"라는 말을 빼버렸습니다. 그렇게해서 제목을 추출하는데
//        "노래틀어줘~" 라고했을경우 노래가 추출되게 됩니다. 그럴경우 그냥 노래가 실행되게 하기위해서 만들었습니다.
//        intent 참조변수를 불러와서 , getStringExtra 라는 함수를 불러오게 됩니다.
//        getStringExtra 안에 매개변수로 키 이름 = > "music_title " 를 넣어줍니다.
//        그리고 music_title 에 넣어줍니다.
//        이부분에서 노래 제목을 추출해 줘야합니다. ==> music_title 에는 노래 제목이 적혀있습니다.
        Log.d("TotalMusicActivity.qwer", "music_title : " + music_title);
//        노래 틀어줘~~ 라고 말했을때 , 위에 start==1 로 들어가는것이 아니라 여기 로그로 music_title 이 "노래" 찍히게 된다 .
//        그래서 여기다가 조건문을 걸어서 String title = "노래 " 해서 if 문 같으면 노래가 실행되게 해야겠습니다 .
        String title = "노래";
        String title2 = "음악";
        if (title.equals(music_title) || title2.equals(music_title)) {
            Log.d("TotalMusicActivity.qwer", "title.equals(music_title) 로 들어오는 곳입니다. ");
            AudioApplication.getInstance().getServiceInterface().voice_togglePlay();
        }

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        updateUI();

    }

    public void updateUI() {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
//            재생중이면 pause 라는 그림을 보여주게됩니다.
            mBtnPlayPause.setImageResource(R.drawable.pause);
        } else {
//            그게 아니라면 play 그림을 보여줍니다.
            mBtnPlayPause.setImageResource(R.drawable.play);
        }
        AudioAdapter.AudioItem audioItem = AudioApplication.getInstance().getServiceInterface().getAudioItem();
        if (audioItem != null) {
//           null 이 아니라면
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), audioItem.mAlbumId);
            Picasso.get().load(albumArtUri).error(R.drawable.music).into(mImgAlbumArt);
            mTxtTitle.setText(audioItem.mTitle);
        } else {
            mImgAlbumArt.setImageResource(R.drawable.music);
            mTxtTitle.setText("재생중인 음악이 없습니다.");
        }
    }

    //        AudioAdapter 에서 position 을 선택했을때 재생버튼 을 설정하는 부분
    public void updatePlay() {
        mBtnPlayPause.setImageResource(R.drawable.pause);
//               AudioAdapter 에서 사용 ((TotalMusicActivity) TotalMusicActivity.mContext).updatePlay();
    }

//      AudioAdapter 에서 position 을 선택하고 , 그 음악이 끝나면 다음 음악을 실행하게 되는 부분
//      ==> AudioAdapter 에서 onClick ==> 해당하는 postion을 클릭해서 클릭된 음악을 실행시킨다.
//      ==> 음악을 실행하면서 updateUI() 라는 함수를 실행시키고 UI를 update 한다 그리고 변하지 않은 재생 버튼 그림은
//      public void updatePlay() 라는 함수를 호출시켜서 실행 시킨다 .
//      음악 이 끝나게 되면 public void forward 라는 함수를 호출시켜서 다음 음악을 실행하게 한다 .


    public void updateForward() {
//         이함수 안에는 어댑터에서 해당 position 을 클릭하게 되면 ,
//         실행한 후에 다음 음악이 재생되게 하는 코드를 작성 해야 합니다.
        AudioApplication.getInstance().getServiceInterface().forward();
    }

    private void getAudioListFromMediaDatabase() {
//      여기는 데이터베이스처럼 칸을 만들어 주는것인가 ??
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA
                };
                Log.d("onCreateLoader.qwe", "MediaStore.Audio.Media.TITLE : " + MediaStore.Audio.Media.TITLE);
                String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
                String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";
                Log.d("onCreateLoader.qwe", "sortOrder : " + sortOrder);
                return new CursorLoader(getApplicationContext(), uri, projection, selection, null, sortOrder);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

                mAdapter.swapCursor(data);
                music_count = 1;
                data.moveToFirst(); // 만약에 cursor 라는 것이 아무것도 밑에 내려갈것이 없을때는 다시 맨위로 올려버린다.
                if (data != null && data.getCount() > 0) {
                    while (data.moveToNext()) {
//                       음악 총 개수를 구하기 위해 더합니다.

                        music_count++;
//                        여기로그는 음악의 제목을 찍어주는 Log 입니다.
                        Log.d("qwe", "Title:" + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
//                        Log.d("Total.qweqwe", "music_title 3 : " + music_title);
                        String change = data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)).replaceAll(" ", "");
                        Log.d("change.music_title1", "change : " + change);
//                        Log.d("change.music_title2", "change : " + music_title);
//                        전체 한바퀴 돌다가 change.music_title1 하고 change.music_title2 하고 제목이 같아져도 if 문으로 안들어오게 됩니다.
//                        어떻게 된거지 ??
//                        String 은 변수가 아닙니다 String 은 클래스 입니다
//                        따라서 ~ 연산자 == 이것을 사용하면 안됩니다. equals 를 사용해야합니다.
                        Intent intent = getIntent();
                        String music_title = intent.getStringExtra("music_title");
//                       getIntent 를 사용해서 music_title 에 music_title 키값을 넣게된다. 이것은 CollectActivity 에서 전달 해준겁니다.
                        Log.d("change.music_title2", "change : " + music_title);
                        if (music_title != null) {
//                            만약에 제목이 null 이 아니라면 ==> 여기 if 문이 있는 이유는 만약에 음성인식이 아니라 그냥 클릭했을 경우
//                            music_title 이 null 값으로 잡히게 된다. 그럴 경우 에러가 생긴다 .그래서 만약에 음악이라는 버튼을 클릭했을때는
//                            여기 if문으로 안 들어오게 된다. 반면 음성인식으로 노래를 틀게되면 여기 if 문으로 들어오게된다.
                            if (music_title.equals(change)) {
//                                제목이 있어도 내 폰에 저장되어있는 제목이 없을 수도 있다
//                                그래서 if문을 걸어주고 내폰에 포함되어있는 제목인지 확인해주기위해 여기 if문이 존재한다.
                                Log.d("Total.들어옴1", "title 들어옴 : " + data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                                String music_date_confirm = data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)).replaceAll(" ", "");
//                               내폰에 저장되어있는 음악 제목들을 보면 띄어쓰기가 있는것이 있다 .그것들을 전부 띄어쓰기를 붙여주기위해서 music_date_confirm 이 존재한다
                                Log.d("Total.들어옴2", "내폰에 저장되어있는 제목 띄어쓰기 줄이기 : " + music_date_confirm);
                                Log.d("Total.들어옴3", "title 들어옴 music_title : " + music_title);
                                Log.d("Total.들어옴4", "music_count 포지션은 몇일까 그럼  : " + music_count);
                                AudioApplication.getInstance().getServiceInterface().setPlayList(mAdapter.getAudioIds()); // 재생목록등록
//                              어댑터안에있는  public ArrayList<Long> getAudioIds() 를 불러옵니다. 재생목록에 색칠해 주기 위해서 불러왔습니다.
                                AudioApplication.getInstance().getServiceInterface().play(music_count - 1); // 선택한 오디오재생
//                                재생목록에 색칠을 해줬으면 이제 그것을 실행을 해줘야합니다.
//                                index 값에 -1 을 해줘야 합니다. music_count 를 초기화 1 로해줬기때문입니다. music_count=1;
//                                왜냐면 원래 index 는 0 부터 시작하다 음악개수를 세야하기때문에 1 부터 시작을 해야 적절하게 개수가 나오게 됩니다.
                                updateUI();
//                                updateUI() 라는 함수를 불러와서 재생목록에 있는 UI를 바꿔주게 됩니다. 이것이 없으면 재생목록 UI가 바꿔지거나 그려지지앖습니다
                                updatePlay();
//                                updatePlay() 라는 함수를 불러오는 이유는 updateUI로 그려줄때 , updateUI 로는 재생과 일시정지 UI가 제대로 바껴지지않아서
//                                updatePlay() 라는 함수를 만들어서 , 재생버튼이 바뀌도록 만들었습니다.
                            }
                        }
                    }
                }
//               개수를 구하기 위한 Log 와 String music = String.valuOf(music_count) 입니다 .
//                music_number.setText(mAdapter.getAudioIds().size() + "");
                music_number.setText(Integer.toString(mAdapter.getAudioIds().size()));
                Log.d("musicCountVoiceResult", "musicCountVoiceResult : " + musicCountVoiceResult);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // READ_EXTERNAL_STORAGE 에 대한 권한 획득.
            getAudioListFromMediaDatabase();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_miniplayer:
                // 플레이어 화면으로 이동할 코드가 들어갈 예정
                Intent intent = new Intent(TotalMusicActivity.this, MusicPlayer.class);
                updateUI();
//                updateUI 라는 함수가 있는 이유는 플레이하고나서 UI를 바꿔주기위해 이 함수가 필요합니다.
                startActivity(intent);
                break;
            case R.id.btn_rewind:
                // 이전곡으로 이동
                AudioApplication.getInstance().getServiceInterface().rewind();
                Toast.makeText(TotalMusicActivity.this, "이전곡", Toast.LENGTH_SHORT).show();
                updateUI();
                mBtnPlayPause.setImageResource(R.drawable.pause);
                break;
            case R.id.btn_play_pause:
                // 재생 또는 일시정지
                AudioApplication.getInstance().getServiceInterface().togglePlay();
                updateUI();
                break;
            case R.id.btn_forward:
                // 다음곡으로 이동
                AudioApplication.getInstance().getServiceInterface().forward();
                Toast.makeText(TotalMusicActivity.this, "다음곡", Toast.LENGTH_SHORT).show();
                updateUI();
                mBtnPlayPause.setImageResource(R.drawable.pause);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioApplication.getInstance().getServiceInterface().real_pause();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TotalMusicActivity.this, CollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

