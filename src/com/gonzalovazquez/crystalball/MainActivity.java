package com.gonzalovazquez.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gonzalovazquez.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

    private CrystalBall mCrystalBall = new CrystalBall();
    private TextView mAnswerLabel;
    //private Button mGetAnswerButton;
    private ImageView mCrystalBallImage;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    
    
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Assign our View variables and assign them the Videos from the layout file
        mAnswerLabel = (TextView)findViewById(R.id.textView1);
        //mGetAnswerButton =(Button)findViewById(R.id.button1);
		//Declare the imageView1 as a ImageView variable
		mCrystalBallImage = (ImageView)findViewById(R.id.imageView1);
		//Gives us the service that managers sensors
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		//We select the accelerometer from the manager
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {		
			@Override
			public void onShake() {
				handleNewAnswer();
				
			}
		});
			
       //Click listener to when the user presses the button, text appears
       /*
		mGetAnswerButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				handleNewAnswer();
			}
		});
		*/
    }
	
	@ Override
	public void onResume(){
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
				SensorManager.SENSOR_DELAY_UI);
		
	}
	@Override
	public void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}
    	
	
	/**
	 * Method to control the animation of the crystal ball
	 */
	private void animateCrystalBall(){
		//Set the image resource of the image view to the animation file resource.
		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		//Instantiates an animation drawable object with the drawable from our image view. 
		AnimationDrawable ballAnimation = (AnimationDrawable)mCrystalBallImage.getDrawable();
		//Start the animation.
		//Encounter a bug that Android thinks the animation is still playing so we solve it 
		//with this if statement.
		if(ballAnimation.isRunning()){
			ballAnimation.stop();
		}
		ballAnimation.start();
		
		
	}
	
	/**
	 * animateAnswer does a tween animation of the text by changing its alpha property
	 */
	private void animateAnswer(){
		//AlphaAnimation we specify on the constructor the start stated with the end state.
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0,1);
		//Specify the duration
		fadeInAnimation.setDuration(1500);
		//Persist the animation after it is completed
		fadeInAnimation.setFillAfter(true);
		
		//Attached the animation to the text view and runs it
		mAnswerLabel.setAnimation(fadeInAnimation);
	}

	/**
	 * Plays the sound when the user clicks
	 */
	private void playSound(){
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		//Listen to when the media player is done playing the file so we can release the player.
		//Here we add the OnComplete method and we attach a function to run when its finish,
		//with the variable mp which is the same as player but for scoping purposes we want to use
		//mp
		player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				
			}
		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private void handleNewAnswer() {
		// Update the label with our dynamic answer
		String answer = mCrystalBall.getAnAnswer();
		mAnswerLabel.setText(answer);
		
		//Call the animate button when application launches.
		animateCrystalBall();
		animateAnswer();
		playSound();
	}
    
}
