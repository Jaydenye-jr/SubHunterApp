package com.gamecodeschool.subhunter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Activity;
import android.view.Window;//this uses the more efficient Android Activity, instead of AppCombatActivity
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.util.Log;
import android.widget.ImageView;
import java.util.Random;


public class SubHunter extends Activity {
    /*
      Android runs this code just before the player sees the app, making it a
    good place to add code for 1 time setup phase
    */
    //variable initiations:
    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = -100;
    int subHorizontalPosition;
    int subVerticalPosition;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;
    boolean debugging = true;
    //Activity is what interacts with the operating system
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//No title bar or action bar
        //setContentView(R.layout.activity_main); this line removed as not using normal user interface
        //get resolution of display:
        Display display = getWindowManager().getDefaultDisplay();//obj display is initialised with the get...() methods
        Point size = new Point();//point object has x and y variables to store width and height of display
        display.getSize(size);//point obj gets sent in as an argument
        //init size based variables based on screed resolution:
        //Note: Variables declared are only accessible within the method; therefore important to init var outside
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels/gridWidth;
        gridHeight = numberVerticalPixels/blockSize;
        Log.d("Debugging","In onCreate");
        newGame();
        draw();
        }

        /*This code will execute when a new game needs to start, when the app first starts or
        after the player wins a game
         */
        public void newGame(){
            Random random = new Random();
            subHorizontalPosition = random.nextInt(gridWidth);
            subVerticalPosition = random.nextInt(gridHeight);

            shotsTaken = 0;
            Log.d("Debugging","In newGame");
        }





    /*
        Here is the drawing code of grid, hud touch indicator
         */
        void draw(){//void methods do no return any specific value
            Log.d("Debugging","In draw");
            printDebuggingText();
        }
        /*
        This part handles tapping of screen
         */
        @Override//overloading is methods with the same name but diff parameter, overriding is
        //replacing a mtd with the same name and parameter list. Original mtd can still be called
        // with super.<mtd>()
        public boolean onTouchEvent(MotionEvent motionEvent){
            Log.d("Debugging","In onTouchEvent");
            takeShot();
            return true;
        }
        /*
        This part executes when player taps the screen, calculate distance from sub and decide
        hit/miss
         */
        void takeShot(){
            Log.d("Debugging","In takeShot");
            draw();
        }
        //this code says Boom
        void boom(){

        }
        //this code prints debug text
        void printDebuggingText(){
            Log.d("numberHorizontalPixels",""+numberHorizontalPixels);
            Log.d("numberVerticalPixels",""+numberVerticalPixels);
            Log.d("blockSize",""+blockSize);
            Log.d("gridWidth",""+gridWidth);
            Log.d("gridHeight",""+gridHeight);
            Log.d("horizontalTouched",""+horizontalTouched);
            Log.d("verticalTouched",""+verticalTouched);
            Log.d("subHorizontalPosition",""+subHorizontalPosition);
            Log.d("subVerticalPosition",""+subVerticalPosition);
            Log.d("hit",""+hit);
            Log.d("shotsTaken",""+shotsTaken);
            Log.d("debugging",""+debugging);
            Log.d("distanceFromSub",""+distanceFromSub);


        }
    }
