package com.hogehoge.milltimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MillTimerActivity extends Activity implements OnClickListener {
	// View宣言
	private TextView textViewTime;
	private Button startButton;
	private Button stopButton;
	private Button closeButton;

	// 変数宣言
	private long startTime;
	private long currentTime;
	private long milliSec;
	private long seconds;
	private long minutes;
	private long hours;
	private Handler handler;
	private Runnable runnable;
	
	// 定数宣言
	private final int INTERVAL = 10;
	private final String INIT_TIME = "00:00:00.000";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // View初期化
        textViewTime = (TextView)findViewById(R.id.TextViewTime);
        textViewTime.setText(INIT_TIME);
        startButton = (Button)findViewById(R.id.StartButton);
        startButton.setOnClickListener(this);
        stopButton = (Button)findViewById(R.id.StopButton);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);
        closeButton = (Button)findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.StartButton:
		        // 開始時間取得
		        startTime = SystemClock.elapsedRealtime();
		        
		        // ボタン表示ON/OFF
		        startButton.setEnabled(false);
		        stopButton.setEnabled(true);
		        
		        handler = new Handler();
		        runnable = new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						currentTime = SystemClock.elapsedRealtime() - startTime;
						milliSec = currentTime % 1000;
						currentTime = currentTime / 1000;
						seconds = currentTime % 60;
						currentTime = currentTime / 60;
						minutes = currentTime % 60;
						hours = currentTime / 60;
						
						textViewTime.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + "." + String.format("%03d", milliSec) );
						// 次処理(INTERVALミリ秒毎にrun()を繰り返す)
						handler.postDelayed(this, INTERVAL);						
					}		        	
		        };
		        
		        // 初回処理
		        handler.postDelayed(runnable, INTERVAL);
		        break;
			case R.id.StopButton:
				// ボタン表示ON/OFF
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				
				// Handlerからコールバック(runnable)を削除して、処理を止める
				handler.removeCallbacks(runnable);
				break;
			case R.id.CloseButton:
				finish();
				break;
		}
	}
	
}