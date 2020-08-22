package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main3Activity extends TabActivity {

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;
    TextView cht;
    PieChart pieChart,pieChart2;
    String date, date3;
    TextView in1,in2,in3,out1,out2,out3,out4,out5,out6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //수입탭, 지출탭 탭호스트 구현
        TabHost tabHost = getTabHost();
        TabSpec tabSpecSong = tabHost.newTabSpec("수입").setIndicator("수입");
        tabSpecSong.setContent(R.id.in);
        tabHost.addTab(tabSpecSong);
        TabSpec tabSpecArtist = tabHost.newTabSpec("지출")
                .setIndicator("지출");
        tabSpecArtist.setContent(R.id.out);
        tabHost.addTab(tabSpecArtist);
        tabHost.setCurrentTab(0);

        pieChart = (PieChart)findViewById(R.id.piechart);
        pieChart2 = (PieChart)findViewById(R.id.piechart2);
        in1=(TextView)findViewById(R.id.in1);
        in2=(TextView)findViewById(R.id.in2);
        in3=(TextView)findViewById(R.id.in3);
        out1=(TextView)findViewById(R.id.out1);
        out2=(TextView)findViewById(R.id.out2);
        out3=(TextView)findViewById(R.id.out3);
        out4=(TextView)findViewById(R.id.out4);
        out5=(TextView)findViewById(R.id.out5);
        out6=(TextView)findViewById(R.id.out6);


        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM d11234567;", null);
        //지출
        float a0=0,b0=0,c0=0,d0=0,e0=0;
        while(cursor.moveToNext()) {
            //ginout가 1일때(데이터가 지출일때)
            if (cursor.getInt(2) == 2) {
                if(cursor.getInt(3)==3){
                    a0=cursor.getInt(4);
                }
                else if(cursor.getInt(3)==4){
                    b0=cursor.getInt(4);
                }
                else if(cursor.getInt(3)==5){
                    c0=cursor.getInt(4);
                }
                else if(cursor.getInt(3)==6){
                    d0=cursor.getInt(4);
                }
                else if(cursor.getInt(3)==7){
                    e0=cursor.getInt(4);
                }
            }
        }


        float ch1=0,ch2=0,ch3=0,ch4=0,ch5=0, allch=0;
        allch=a0+b0+c0+d0+e0;
        ch1=a0/allch*100;
        ch2=b0/allch*100;
        ch3=c0/allch*100;
        ch4=d0/allch*100;
        ch5=e0/allch*100;
        if(a0==0){
            ch1=0; }
        else if(b0==0){
            ch2=0; }
        else if(c0==0){
            ch3=0; }
        else if(d0==0){
            ch4=0; }
        else {
            ch5=0; }

        int ai0, bi0,ci0,di0,ei0,fi0;
        ai0= (int) a0;
        bi0= (int) b0;
        ci0= (int) c0;
        di0= (int) d0;
        ei0= (int) e0;
        fi0= (int) allch;
        String ta0,tb0,tc0,td0,te0,tf0;
        ta0=Integer.toString(ai0);
        tb0=Integer.toString(bi0);
        tc0=Integer.toString(ci0);
        td0=Integer.toString(di0);
        te0=Integer.toString(ei0);
        tf0=Integer.toString(fi0);
        out1.setText(ta0);
        out2.setText(tb0);
        out3.setText(tc0);
        out4.setText(td0);
        out5.setText(te0);
        out6.setText(tf0);

        cursor.close();
        sqlDB.close();
        //수입
        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor2;
        cursor2 = sqlDB.rawQuery("SELECT * FROM d11234567;", null);
        float ao0=0,bo0=0;
        while(cursor2.moveToNext()) {
            //ginout가 1일때(데이터가 수입일때)
            if (cursor2.getInt(2) == 1) {
                if(cursor2.getInt(3)==1){
                    ao0=cursor2.getInt(4);
                }
                else if(cursor2.getInt(3)==2){
                    bo0=cursor2.getInt(4);
                }
            }
        }
        float cho1=0,cho2=0, allcho=0;
        allcho=ao0+bo0;

        cho1=ao0/allcho*100;
        cho2=bo0/allcho*100;

        if(ao0==0){
            cho1=0; }
        else if(bo0==0){
            cho2=0; }

        int aio0,bio0,cio0;
        aio0=(int)ao0;
        bio0=(int)bo0;
        cio0=(int)allcho;

        String tao0,tbo0,tco0;
        tao0=Integer.toString(aio0);
        tbo0=Integer.toString(bio0);
        tco0=Integer.toString(cio0);

        in1.setText(tao0);
        in2.setText(tbo0);
        in3.setText(tco0);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(1f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(ch1, "식비"));
        yValues.add(new PieEntry(ch2, "교통비"));
        yValues.add(new PieEntry(ch3, "통신비"));
        yValues.add(new PieEntry(ch4, "공과금"));
        yValues.add(new PieEntry(ch5, "생필품"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        //수입
        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setExtraOffsets(5, 10, 5, 5);
        pieChart2.setDragDecelerationFrictionCoef(1f);
        pieChart2.setDrawHoleEnabled(false);
        pieChart2.setHoleColor(Color.WHITE);
        pieChart2.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues2 = new ArrayList<PieEntry>();
        yValues2.add(new PieEntry(cho1, "용돈"));
        yValues2.add(new PieEntry(cho2, "월급"));
        pieChart2.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet2 = new PieDataSet(yValues2, "");
        dataSet2.setSliceSpace(3f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data2 = new PieData((dataSet2));
        data2.setValueTextSize(10f);
        data2.setValueTextColor(Color.BLACK);

        pieChart2.setData(data2);

    }
    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "dd11234567", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  d11234567 ( gName TEXT , gtt INTEGER,ginout INTEGER, gbtnn INTEGER,gNumber INTEGER);");
            //gName:내용 gtt:날짜 ginout:수입인지 지출인지(수입:1, 지출:2) gbtnn:항목버튼 gNumber:금액
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS d11234567");
            onCreate(db);
        }
    }
    public void btn1(View v){
        Intent intent002=new Intent(this, MainActivity.class);

        startActivity(intent002);
    }
    public void btn11(View v){

        Intent intent1=new Intent(this, Main4Activity.class);
        startActivity(intent1);

    }
}
