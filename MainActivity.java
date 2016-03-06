package com.example.jay.cardmovedemo;

import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private Card card1;
    private Card card2;

    private ImageView img7;
    private ImageView img8;

    private Pile p1;
    private Pile p2;
    private Pile p3;
    private Pile p4;
    private Pile p5;
    private Pile p6;

    private Pile[] pileList= new Pile[6];

    private Rect a1;
    private Rect a2;
    private Rect a3;
    private Rect a4;
    private Rect a5;
    private Rect a6;

    Point origin;
    Pile lastPile;

    private int numOfMovingCard = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    public boolean onTouchEvent(MotionEvent event) {


        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {

            case (MotionEvent.ACTION_DOWN):

                Log.d("Message:","pile1:"+String.valueOf(p1.cardList.size())+
                                 "pile2:"+String.valueOf(p2.cardList.size())+
                                 "pile3:"+String.valueOf(p3.cardList.size())+
                                 "pile4:"+String.valueOf(p4.cardList.size())+
                                 "pile5:"+String.valueOf(p5.cardList.size())+
                                 "pile6:"+String.valueOf(p6.cardList.size()));

                if (event.getRawX()>card1.img.getX()&&event.getRawX()<card1.img.getX()+100&&event.getRawY()-150>card1.img.getY()&&event.getRawY()-150<card1.img.getY()+100) {

                    numOfMovingCard = 1;

                    if(card1.currentPile!=null) {
                        card1.currentPile.cardList.remove(card1.img);
                    }

                    card1.img.setX((int) event.getRawX() - 50);
                    card1.img.setY((int) event.getRawY() - 200);
                }
                else if(event.getRawX()> card2.img.getX()&&event.getRawX()<card2.img.getX()+100&&event.getRawY()-150>card2.img.getY()&&event.getRawY()-150<card2.img.getY()+100){

                    numOfMovingCard = 2;

                    if(card2.currentPile!=null) {
                        card2.currentPile.cardList.remove(card2.img);
                    }

                    card2.img.setX((int) event.getRawX() - 50);
                    card2.img.setY((int) event.getRawY() - 200);
                }
                return true;

            case (MotionEvent.ACTION_MOVE):
                if (numOfMovingCard==1) {
                    card1.img.setX((int) event.getRawX() - 50);
                    card1.img.setY((int) event.getRawY() - 200);
                }
                else if(numOfMovingCard==2){
                    card2.img.setX((int) event.getRawX() - 50);
                    card2.img.setY((int) event.getRawY() - 200);
                }
                return true;

            case (MotionEvent.ACTION_UP):
                if (numOfMovingCard==1) {

                    for(int i = 0;i<6;i++){
                        if(pileList[i].area.contains((int)event.getRawX(),(int)event.getRawY()-150)){
                            card1.img.setX(pileList[i].area.left);
                            card1.img.setY(pileList[i].area.top + 40 * pileList[i].cardList.size());

                            card1.lastPosition.x = pileList[i].area.left;
                            card1.lastPosition.y = pileList[i].area.top;
                            card1.currentPile = pileList[i];

                            pileList[i].cardList.add(card1.img);
                            break;
                        }
                        else if(i==5){
                            card1.img.setX(card1.lastPosition.x);
                            card1.img.setY(card1.lastPosition.y);
                            card1.currentPile.cardList.add(card1.img);
                        }

                    }

                }
                else if(numOfMovingCard==2){
                    for(int i = 0;i<6;i++){
                        if(pileList[i].area.contains((int)event.getRawX(),(int)event.getRawY()-150)){
                            card2.img.setX(pileList[i].area.left);
                            card2.img.setY(pileList[i].area.top + 40 * pileList[i].cardList.size());

                            card2.lastPosition.x = pileList[i].area.left;
                            card2.lastPosition.y = pileList[i].area.top;
                            card2.currentPile = pileList[i];

                            pileList[i].cardList.add(card2.img);
                            break;
                        }
                        else if(i==5){
                            card2.img.setX(card2.lastPosition.x);
                            card2.img.setY(card2.lastPosition.y);
                            card2.currentPile.cardList.add(card2.img);
                        }

                    }

                }
                numOfMovingCard=0;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        final ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        final ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        final ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        final ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        final ImageView img6 = (ImageView) findViewById(R.id.imageView6);
        img7 = (ImageView) findViewById(R.id.imageView7);
        img8 = (ImageView) findViewById(R.id.imageView8);

        lastPile = new Pile(a1);

        ViewTreeObserver vto1 = img1.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a1 = new Rect(img1.getLeft(), img1.getTop(), img1.getRight(), img1.getBottom());
                p1 = new Pile(a1);

                pileList[0]=p1;
            }
        });

        ViewTreeObserver vto2 = img2.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a2 = new Rect(img2.getLeft(), img2.getTop(), img2.getRight(), img2.getBottom());
                p2 = new Pile(a2);

                pileList[1]=p2;
            }
        });

        ViewTreeObserver vto3 = img3.getViewTreeObserver();
        vto3.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a3 = new Rect(img3.getLeft(), img3.getTop(), img3.getRight(), img3.getBottom());
                p3 = new Pile(a3);

                pileList[2]=p3;
            }
        });

        ViewTreeObserver vto4 = img4.getViewTreeObserver();
        vto4.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img4.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a4 = new Rect(img4.getLeft(), img4.getTop(), img4.getRight(), img4.getBottom());
                p4 = new Pile(a4);

                pileList[3]=p4;
            }
        });

        ViewTreeObserver vto5 = img5.getViewTreeObserver();
        vto5.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img5.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a5 = new Rect(img5.getLeft(), img5.getTop(), img5.getRight(), img5.getBottom());
                p5 = new Pile(a5);

                pileList[4]=p5;
            }
        });

        ViewTreeObserver vto6 = img6.getViewTreeObserver();
        vto6.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img6.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a6 = new Rect(img6.getLeft(), img6.getTop(), img6.getRight(), img6.getBottom());
                p6 = new Pile(a6);
                pileList[5]=p6;
            }
        });

        ViewTreeObserver vto7 = img7.getViewTreeObserver();
        vto7.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img7.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                card1 = new Card(img7,new Point((int)img7.getX(),(int)img7.getY()));
            }
        });

        ViewTreeObserver vto8 = img8.getViewTreeObserver();
        vto8.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img8.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                card2 = new Card(img8,new Point((int)img8.getX(),(int)img8.getY()));
            }
        });

    }


}
