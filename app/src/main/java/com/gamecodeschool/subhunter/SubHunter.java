package com.gamecodeschool.subhunter;

import android.media.Image;
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

    //Objects of classes needed to do drawing:
    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;


    //Activity is what interacts with the operating system
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//No title bar or action bar
        //setContentView(R.layout.activity_main); this line removed as not using normal user
        // interface
        //get resolution of display:
        Display display = getWindowManager().getDefaultDisplay();//obj display is initialised with
        // the get...() methods
        Point size = new Point();//point object has x and y variables to store width and height of
        // display
        display.getSize(size);//point obj gets sent in as an argument
        //init size based variables based on screed resolution:
        //Note: Variables declared are only accessible within the method; therefore important to
        // init var outside
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels/gridWidth;
        gridHeight = numberVerticalPixels/blockSize;

        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels,numberVerticalPixels
                ,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();
        // Do drawing here
        // Associate the drawn upon Bitmap with the ImageView

        // Tell Android to set our drawing
        // as the view for this app
        // via the gameView

        setContentView(gameView);

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
            gameView.setImageBitmap(blankBitmap);
            //wipe screen with white color:
            canvas.drawColor(Color.argb(255,255,255,255));
            // Change the paint color to black
            paint.setColor(Color.argb(255, 0, 0, 0));

            for (int i = 0; i<gridWidth; i++) {
                // Draw the vertical lines of the grid
                canvas.drawLine(blockSize * i, 0, blockSize * i, numberVerticalPixels, paint);
                // Draw the horizontal lines of the grid
            }
            for (int i = 0; i<gridHeight; i++) {
                canvas.drawLine(0, blockSize * i, numberHorizontalPixels , blockSize * i, paint);
                //Resize text for score and distance
                paint.setTextSize(blockSize * 2);
                paint.setColor(Color.argb(255, 0, 0, 255));
                canvas.drawText("Shots Taken: " + shotsTaken + "  Distance: " + distanceFromSub, blockSize, blockSize * 1.75f, paint);
                Log.d("Debugging", "In draw");
                printDebuggingText();
            }
        }
        /*
        This part handles tapping of screen
         */
        @Override//overloading is methods with the same name but diff parameter, overriding is
        //replacing a mtd with the same name and parameter list. Original mtd can still be called
        // with super.<mtd>()
        public boolean onTouchEvent(MotionEvent motionEvent){
            Log.d("Debugging","In onTouchEvent");
            if((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
                //Process player shot by passing coordinates of finger to takeShot
                takeShot(motionEvent.getX(), motionEvent.getY());

            }
            return true;
        }
        /*
        This part executes when player taps the screen, calculate distance from sub and decide
        hit/miss
         */
        void takeShot(float touchX, float touchY){
            Log.d("Debugging","In takeShot");
            shotsTaken ++;//add to shots taken
            horizontalTouched = (int)touchX/blockSize;//convert float screen coord into grid
            verticalTouched = (int)touchY/blockSize;
            //check if shot hit sub
            hit = horizontalTouched == subHorizontalPosition && verticalTouched == subVerticalPosition;
            //distancte from sub to original target
            int horizontalGap = (int)horizontalTouched-subHorizontalPosition;
            int verticalGap = (int)verticalTouched-subVerticalPosition;
            distanceFromSub = (int)Math.sqrt((horizontalGap*horizontalGap)+(verticalGap*verticalGap)
            );

            if(hit)
                boom();
            else
                draw();
        }
        //this code says Boom
        void boom(){

        }
        //this code prints debug text
        void printDebuggingText(){
            paint.setTextSize(blockSize);
            canvas.drawText("numberHorizontalPixels = " + numberHorizontalPixels, 50, blockSize * 3, paint);
            canvas.drawText("numberVerticalPixels = " + numberVerticalPixels, 50, blockSize * 4, paint);
            canvas.drawText("blockSize = " + blockSize, 50, blockSize * 5, paint);
            canvas.drawText("gridWidth = " + gridWidth, 50, blockSize * 6, paint);
            canvas.drawText("gridHeight = " + gridHeight, 50, blockSize * 7, paint);
            canvas.drawText("horizontalTouched ="+ horizontalTouched, 50, blockSize * 8, paint);
            canvas.drawText("verticalTouched = " +verticalTouched, 50, blockSize * 9, paint);
            canvas.drawText("subHorizontalPosition = " + subHorizontalPosition, 50, blockSize * 10, paint);
            canvas.drawText("subVerticalPosition = " + subVerticalPosition, 50, blockSize * 11, paint);
            canvas.drawText("hit = " + hit, 50, blockSize * 12, paint);
            canvas.drawText("shotsTaken = " + shotsTaken, 50, blockSize * 13, paint);
            canvas.drawText("debugging = " + debugging, 50, blockSize * 14, paint);

        }


    }
