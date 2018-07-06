package com.example.jeason.swipe.activity;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import com.example.jeason.swipe.Adapter.WordAdapter;
import com.example.jeason.swipe.R;
import com.example.jeason.swipe.SeparatorDecoration;
import com.example.jeason.swipe.services.MediaPlayService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.jeason.swipe.activity.MainActivity.DETAIL_KEY;

public class MantraDetailActivity extends AppCompatActivity {
    private static final int FIRST_ASSEMBLY_CN = 300404;
    private static final int SECOND_ASSEMBLY_CN = 858362;
    private static final int THIRD_ASSEMBLY_CN = 961219;
    private static final int FOURTH_ASSEMBLY_CN = 1272504;
    private static final int FIFTH_ASSEMBLY_CN = 1473297;
    private static final int MANTRA_HEART_CN = 1736345;
    private static final int FIRST_ASSEMBLY_Sanskrit = 4841;
    private static final int SECOND_ASSEMBLY_sanskrit = 354395;// 354395.126984127;
    private static final int THIRD_ASSEMBLY_sanskrit = 457939;//457939.615079365;
    private static final int FOURTH_ASSEMBLY_sanskrit = 692168;//692168.98015873;
    private static final int FIFTH_ASSEMBLY_sanskrit = 880580;// 880580.472222222;
    private static final int MANTRA_HEART_sanskrit = 1090716;
    private static final String TAG = MantraDetailActivity.class.getSimpleName();
    private int mediaCurrentPositionInSecond;
    private SparseIntArray subtitlePosItemMap  = new SparseIntArray();
    private ExoPlayer player;
    private ArrayList<String> mantraLinesSimplifiedCN  = new ArrayList<>();
    private boolean doubleTapToExit = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        boolean isBound = false;
        MediaPlayService mediaPlayService;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaPlayService.MyBinder binder = (MediaPlayService.MyBinder) iBinder;
            mediaPlayService = binder.getService();
            isBound = true;
            mediaPlayService.playMedia(localMediaSource());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mediaPlayService = null;
            isBound = false;
        }
    };
    private WordAdapter mAdapter;
    private RecyclerView mWordList;
    private LinearLayoutManager layoutManager;
    private Handler updateCurrentPostionHandler;
    private Runnable updateMediaCurrentPositionRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_detail);
        preInitialization();
        initializeRecyclerView();
        findLastCompletelyVisibleItemPostionInRecyclerView();

    }

    private void preInitialization() {
        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra(DETAIL_KEY);
        TextView toolBarTitle = findViewById(R.id.toolBarTextView);
        toolBarTitle.setText(itemTitle);
        Toolbar toolbar = findViewById(R.id.MantraDetailActivityToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initializeChapterButtons();
        updateCurrentPostionHandler = new Handler();
        updateMediaCurrentPositionRunnable = new Runnable() {
            @Override
            public void run() {
                long mediaCurrentPosition = player == null ? 0 : player.getCurrentPosition();
                mediaCurrentPositionInSecond = (int) Math.round(mediaCurrentPosition / 1000.0);
                updateHighlightItem();
                updateCurrentPostionHandler.postDelayed(this, 1000);
            }
        };
    }


    private void updateHighlightItem() {
        int indexOfValue = subtitlePosItemMap.indexOfValue(mediaCurrentPositionInSecond);
        if (indexOfValue != -1) {
            mAdapter.setRowHighlightUpdateTracker(indexOfValue);
            mAdapter.notifyDataSetChanged();
            int storedPosition = subtitlePosItemMap.get(indexOfValue);
            mWordList.smoothScrollToPosition(indexOfValue);
        }
    }

    private boolean isForegroundMediaPlayServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void initializeRecyclerView() {
        mWordList = findViewById(R.id.mantra_detail_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(1);
        mAdapter = new WordAdapter(mantraLinesSimplifiedCN, this);
        mWordList.setLayoutManager(layoutManager);
        mWordList.setAdapter(mAdapter);
        mWordList.setHasFixedSize(true);
        SeparatorDecoration itemDecoration = new SeparatorDecoration(
                this, getResources().getColor(R.color.divider), 0.5f);
        mWordList.addItemDecoration(itemDecoration);
        mWordList.setItemAnimator(new DefaultItemAnimator());
    }

    private void initializeChapterButtons() {
        Button chapterOne = findViewById(R.id.button_one);
        chapterOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(FIRST_ASSEMBLY_Sanskrit);
//                mediaCurrentPositionInSecond = (int) Math.round(300404 / 1000.0);
            }
        });
        Button chapterTwo = findViewById(R.id.button_two);
        chapterTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(SECOND_ASSEMBLY_sanskrit);

            }
        });
        Button chapterThree = findViewById(R.id.button_three);
        chapterThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(THIRD_ASSEMBLY_sanskrit);
            }
        });
        Button chapterFour = findViewById(R.id.button_four);
        chapterFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(FOURTH_ASSEMBLY_sanskrit);
            }
        });
        Button chapterFive = findViewById(R.id.button_five);
        chapterFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(FIFTH_ASSEMBLY_sanskrit);
            }
        });
        Button mantraEssence = findViewById(R.id.button_six);
        mantraEssence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(MANTRA_HEART_sanskrit);
            }
        });
    }

    private void initializePlayer() {
        if (player == null) {
            PlaybackControlView playerView = findViewById(R.id.playerView);
            Renderer[] audioRenders = new Renderer[1];
            audioRenders[0] = new MediaCodecAudioRenderer(MediaCodecSelector.DEFAULT);
            TrackSelector audioTrackSelection = new DefaultTrackSelector();
            player = ExoPlayerFactory.newInstance(audioRenders, audioTrackSelection);
            playerView.setPlayer(player);
            playerView.show();
            player.setPlayWhenReady(true);
            MediaSource mediaSource = localMediaSource();//local sanskrit
            player.prepare(mediaSource);
        }
//        MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.shurangma_in_sanskrit)));//Chinese online mentra


    }

    private MediaSource localMediaSource() {
        Uri uri = RawResourceDataSource.buildRawResourceUri(R.raw.shurangama_in_sanskrit_audio);
        DataSpec dataSpec = new DataSpec(uri);
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
        ExtractorMediaSource mediaSource = null;
        try {
            rawResourceDataSource.open(dataSpec);
            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };
            mediaSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
                    factory, new DefaultExtractorsFactory(), null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaSource;
    }

    private void parseJsonFile(String incomingJsonString) {

        try {
            JSONArray jsonArray = new JSONArray(incomingJsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
//                String line = object.getString("line");
                long lineStartPostion = object.getLong("startTime");
                Double postionInSecond = lineStartPostion / 1000.0;
                mantraLinesSimplifiedCN.add(object.getString("content"));
//                mantraLinesSimplifiedCNPinYin.add(object.getString("CHN_PINYIN"));

                //Map position with counter
                subtitlePosItemMap.append(i, (int) Math.round(postionInSecond));
//                Log.v(TAG, "subTitlePosition " + String.valueOf(subTitlePosition));
            }
//            Log.v("Json line ", line);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notificationOnStatusBar() {
        Log.v(TAG, "notificationOnStatusBar Media is playing");
        Intent intent = new Intent(this, MainActivity.class);
        int requestID = (int) System.currentTimeMillis();
        int flag = PendingIntent.FLAG_CANCEL_CURRENT;
        int notificationID = 101;
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, requestID, intent, flag);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Media is playing";
        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(this, channelId);
        mBuilder.setSmallIcon(R.drawable.rounded_button)
                .setContentTitle("Player is playing")
                .setContentText("Tap to back to the MainActivity!")
                .setContentIntent(pendingIntent);
        notificationManager.notify(notificationID, mBuilder.build());
    }

    private String loadJsonFromAsset(InputStream input) {
        String json;
        byte[] data;
        try {
            data = new byte[input.available()];
            if (input.read(data) == -1) {
                throw new EOFException();
            }
            json = new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private void findLastCompletelyVisibleItemPostionInRecyclerView() {
        mWordList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                mWordList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            Log.v(TAG, "initializePlayer onCreate");
        }
        String jSonStringFromAsset = loadJsonFromAsset(getResources().openRawResource(R.raw.shurangama_in_sanskrit));
        parseJsonFile(jSonStringFromAsset);
        updateCurrentPostionHandler.post(updateMediaCurrentPositionRunnable);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Log.v(TAG, "onBackPressed");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
//        removeAllCallBacks();
//        notificationOnStatusBar();
//        checkRunningServiceNumber();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
//        notificationManager.cancel(notificationID);
        removeAllCallBacks();
        Log.v(TAG, "onDestroy");
    }

    private void releasePlayer() {
        if (player != null) {
            long playbackPosition = player.getCurrentPosition();
            int currentWindow = player.getCurrentWindowIndex();
            boolean playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void removeAllCallBacks() {
        updateCurrentPostionHandler.removeCallbacks(updateMediaCurrentPositionRunnable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
