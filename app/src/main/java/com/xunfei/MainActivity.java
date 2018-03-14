package com.xunfei;

import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener,SynthesizerListener{
	private EditText editText;
	private Button button1;
	private Button button2;
	//合成对象
	private SpeechSynthesizer speechSynthesizer;
	//识别窗口
	private RecognizerDialog recognizerDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//appid换成自己申请的
		SpeechUser.getUser().login(MainActivity.this, null, null, "appid=5aa8ce44", listener);
		init();
		setParam();

	}

	public void init()
	{
		editText = (EditText) findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	public void setParam()
	{
		speechSynthesizer = SpeechSynthesizer.createSynthesizer(this);
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
	}

	public void setDialog()
	{
		recognizerDialog = new RecognizerDialog(this);
		recognizerDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		recognizerDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		editText.setText(null);
		//显示Dialog
		recognizerDialog.setListener(dialogListener);
		recognizerDialog.show();
	}

	/**
	 * 识别回调监听器
	 */
	private RecognizerDialogListener dialogListener = new RecognizerDialogListener() {
		//识别结果回调
		@Override
		public void onResult(RecognizerResult arg0, boolean arg1) {
			// TODO Auto-generated method stub
			String text = JsonParser.parseIatResult(arg0.getResultString());
			editText.append(text);
			editText.setSelection(editText.length());
		}

		//识别结束回调
		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.button1:
				String text = editText.getText().toString();
				speechSynthesizer.startSpeaking(text, this);
				break;
			case R.id.button2:
				setDialog();
				break;
			default:
				break;
		}
	}

	//缓冲进度回调通知
	@Override
	public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
		// TODO Auto-generated method stub

	}

	//结束回调
	@Override
	public void onCompleted(SpeechError arg0) {
		// TODO Auto-generated method stub

	}
	//开始播放
	@Override
	public void onSpeakBegin() {
		// TODO Auto-generated method stub

	}
	//暂停播放
	@Override
	public void onSpeakPaused() {
		// TODO Auto-generated method stub

	}
	//播放进度
	@Override
	public void onSpeakProgress(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	//继续播放
	@Override
	public void onSpeakResumed() {
		// TODO Auto-generated method stub

	}

	/**
	 * 通用回调接口
	 */
	private SpeechListener listener = new SpeechListener() {

		//消息回调
		@Override
		public void onEvent(int arg0, Bundle arg1) {
			// TODO Auto-generated method stub

		}

		//数据回调
		@Override
		public void onData(byte[] arg0) {
			// TODO Auto-generated method stub

		}

		//结束回调（没有错误）
		@Override
		public void onCompleted(SpeechError arg0) {
			// TODO Auto-generated method stub

		}
	};
}
