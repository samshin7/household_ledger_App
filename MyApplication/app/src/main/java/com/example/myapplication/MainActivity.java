package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    myDBHelper myHelper;
    TextView edtshow, edtshow2,edtshow3;
    SQLiteDatabase sqlDB;
    String date="";
    String ii1, ii2, ii3, date3;
    TextView txtin, txtout, txtmoney, ttinout;
    Button btnchart;

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtshow=(TextView) findViewById(R.id.edtshow);
        edtshow2=(TextView) findViewById(R.id.edtshow2);
        edtshow3=(TextView) findViewById(R.id.edtshow3);
        txtin=(TextView) findViewById(R.id.txtin);
        txtout=(TextView) findViewById(R.id.txtout);
        txtmoney=(TextView) findViewById(R.id.txtmoney);
        ttinout=(TextView) findViewById(R.id.ttinout);





        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM d11234567;", null);

        //총수입, 총지출, 잔액 계산해서 txtview에 출력하기
        int cc=0,cc2=0, cc3=0;
        String aa = "",aa2="",aa3;
        while(cursor.moveToNext()) {
            //ginout가 1일때(데이터가 수입일때)
            if (cursor.getInt(2) == 1) {
                cc = cc + cursor.getInt(4);
                aa = Integer.toString(cc);
                txtin.setText(aa);

            }
            //나머지(데이터가 지출일때)
            else {
                cc2 = cc2 + cursor.getInt(4);
                aa2 = Integer.toString(cc2);
                txtout.setText(aa2);
            }
        }
        btnchart= (Button)findViewById(R.id.chart);
        final String finalAa = aa;
        final String finalAa2 = aa2;
        btnchart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                intent.putExtra("aa", finalAa);
                intent.putExtra("aa2", finalAa2);

                startActivity(intent);
                finish();
            }
        });


        cc3=cc-cc2;
        aa3=Integer.toString(cc3);
        txtmoney.setText(aa3);



        //캘린더 날짜를 누르면 실행되는 이벤트?
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i + "/" + (i1+1) + "/" + i2;
                ii1=String.valueOf(i);
                ii2=String.valueOf(i1+1);
                ii3=String.valueOf(i2);
                date3=ii1+ii2+ii3;
                Log.d(TAG, "onSelectDayChange: date : "+date);

                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT* FROM d11234567 WHERE gtt = '" + date3 + "';", null);

                String strtext = ""  ;
                String strmoney = "" ;
                String strbtn = "" ;
                String strinout="";

                int a;
                String b;
                while (cursor.moveToNext()) {
                    strtext += cursor.getString(0) + "\r\n";
                    strmoney += cursor.getString(4) + "\r\n";
                    a=Integer.parseInt(cursor.getString(3));
                    if(a==1)
                        b="용돈";
                    else if(a==2)
                        b="월급";
                    else if(a==3)
                        b="식비";
                    else if(a==4)
                        b="교통비";
                    else if(a==5)
                        b="통신비";
                    else if(a==6)
                        b="공과금";
                    else
                        b="생필품";
                    strbtn += b + "\r\n";
                }

                sqlDB = myHelper.getReadableDatabase();
                cursor = sqlDB.rawQuery("SELECT* FROM d11234567 WHERE gtt = '" + date3 + "';", null);
                int ab;
                while(cursor.moveToNext()){
                    ab=Integer.parseInt(cursor.getString(2));
                    if(ab==1){
                        strinout+="수입"+ "\r\n";
                    }
                    else{
                        strinout+="지출"+ "\r\n";
                    }
                }

                edtshow2.setText(strtext);
                edtshow3.setText(strmoney);
                edtshow.setText(strbtn);
                ttinout.setText(strinout);

                cursor.close();
                sqlDB.close();
            }
        });



    }
    public void btn1(View v){
        //달력을 클릭하지 않고 +버튼을 눌러서 다음폼으로 갈때 date에 오늘날짜를 넣어준다.
        if(date==""){
            String today=new SimpleDateFormat("yyyy/M/dd").format(new Date());
            date=today;
            String today2=new SimpleDateFormat("yyyyMdd").format(new Date());
            date3=today2;
        }
        Intent intent001=new Intent(this, Main2Activity.class);
        intent001.putExtra("date", date);
        intent001.putExtra("date3", date3);
        startActivity(intent001);
        finish();
    }
    public void mlist(View v){
        Intent intent2=new Intent(this, Main4Activity.class);
        startActivity(intent2);
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
}