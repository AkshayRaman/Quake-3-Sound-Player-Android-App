package com.example.Q3SoundPlayer;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{
	
	private  SoundPool mSoundPool;
	private  AudioManager  mAudioManager;
	private  HashMap<Integer, Integer> mSoundPoolMap;
	private  HashMap<Integer, Integer> mButtonMap;
	private int mStream[];
	
	final static int LOOP_ONCE = 0;
	final static int MAX_SOUND_COUNT = 50;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //set up our audio player
        mSoundPool = new SoundPool(MAX_SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mSoundPoolMap = new HashMap();
        
        int sounds[] = {
        		 mSoundPool.load(this, R.raw.accuracy, 1),
        		 mSoundPool.load(this, R.raw.denied, 1),
        		 mSoundPool.load(this, R.raw.excellent, 1),
        		 mSoundPool.load(this, R.raw.humiliation, 1),
        		 mSoundPool.load(this, R.raw.impressive, 1),
        		 mSoundPool.load(this, R.raw.perfect, 1),
        		 mSoundPool.load(this, R.raw.haste, 1),
        		 mSoundPool.load(this, R.raw.invisibility, 1),
        		 mSoundPool.load(this, R.raw.quaddamage, 1),
        		 mSoundPool.load(this, R.raw.regeneration, 1),
        		 mSoundPool.load(this, R.raw.wearoff, 1),};
        
        mStream = new int[sounds.length];
    
        for(int i=0;i<sounds.length;i++)
        	mSoundPoolMap.put(i+1,sounds[i]);
        
        
        mButtonMap = new HashMap();
        mButtonMap.put(1, R.id.fx01);
        mButtonMap.put(2, R.id.fx02);
        mButtonMap.put(3, R.id.fx03);
        mButtonMap.put(4, R.id.fx04);
        mButtonMap.put(5, R.id.fx05);
        mButtonMap.put(6, R.id.fx06);
        mButtonMap.put(7, R.id.fx07);
        mButtonMap.put(8, R.id.fx08);
        mButtonMap.put(9, R.id.fx09);
        mButtonMap.put(10, R.id.fx10);
        mButtonMap.put(11, R.id.fx11);
        
        Button b = (Button)findViewById(R.id.stop);
        b.setOnClickListener(this);
        
        for(int i=1;i<=mButtonMap.size();i++){
        	b = (Button)findViewById(mButtonMap.get(i));
        	b.setOnClickListener(this);
        }
    }
	
	public int getKeyValue(int x)
	{
		for(int i=1;i<=mButtonMap.size();i++)
		{
			if(mButtonMap.get(i)==x)
				return i;
		}
		return -1;
	}

	@Override
	public void onClick(View v) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		int val = getKeyValue(v.getId());
		System.out.println(val);
		
		if(val>0)
		{
			mSoundPool.stop(mStream[val-1]);
			mStream[val-1] = mSoundPool.play(mSoundPoolMap.get(val), streamVolume, streamVolume, 1, LOOP_ONCE, 1f);
		
		} 
		
		else //stopping all music
		{
			for(int i=0;i<mStream.length;i++)
				mSoundPool.stop(mStream[i]);
		}
	}
	
	@Override
	public void onDestroy()
	{
		mSoundPool.release();

	    super.onDestroy();
	}
}
	
