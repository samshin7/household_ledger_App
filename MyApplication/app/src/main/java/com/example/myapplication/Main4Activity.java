package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);

        t5=(TextView)findViewById(R.id.t5);
        t6=(TextView)findViewById(R.id.t6);
        t7=(TextView)findViewById(R.id.t7);
        t8=(TextView)findViewById(R.id.t8);

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM d11234567;", null);


        String strtext = ""  ;  //내용
        String strmoney = "" ;  //금액
        String strbtn = "" ;  //항목
        String strtt=""; //날짜

        String strtext2 = ""  ;  //내용
        String strmoney2 = "" ;  //금액
        String strbtn2 = "" ;  //항목
        String strtt2=""; //날짜

        int ab;
        while(cursor.moveToNext()){
            ab=Integer.parseInt(cursor.getString(2));
            //수입일때
            if(ab==1){
                strtt+=cursor.getString(1)+ "\r\n";
                strtext+=cursor.getString(0)+ "\r\n";

                strmoney+=cursor.getString(4)+ "\r\n";
                if(cursor.getInt(3)==1){
                    strbtn+="용돈"+ "\r\n";
                }
                else if(cursor.getInt(3)==2){
                    strbtn+="월급"+ "\r\n";
                }
            }
            //지출일때
            else{
                strtt2+=cursor.getString(1)+ "\r\n";
                strtext2+=cursor.getString(0)+ "\r\n";
                strmoney2+=cursor.getString(4)+ "\r\n";

                if(cursor.getInt(3)==3){
                    strbtn2+="식비"+ "\r\n";
                }
                else if(cursor.getInt(3)==4){
                    strbtn2+="교통비"+ "\r\n";
                }
                else if(cursor.getInt(3)==5){
                    strbtn2+="통신비"+ "\r\n";
                }
                else if(cursor.getInt(3)==6){
                    strbtn2+="공과금"+ "\r\n";
                }
                else if(cursor.getInt(3)==7){
                    strbtn2+="생활비"+ "\r\n";
                }

            }
        }

        t1.setText(strtt);
        t2.setText(strbtn);
        t3.setText(strtext);
        t4.setText(strmoney);

        t5.setText(strtt2);
        t6.setText(strbtn2);
        t7.setText(strtext2);
        t8.setText(strmoney2);

        cursor.close();
        sqlDB.close();

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
    public void chart(View v){
        Intent intent003=new Intent(this, Main3Activity.class);

        startActivity(intent003);
    }
}
