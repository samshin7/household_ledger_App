package com.example.myapplication;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends TabActivity {
    myDBHelper myHelper;
    EditText edtmoney, edttext, edtmoney2, edttext2;
    Button btnInsert, btnInsert2;
    SQLiteDatabase sqlDB;
    TextView day, day2;
    int date4, btnnum, inout;  //날짜, 항목, 수입인지 지출인지
    String aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

        edtmoney = (EditText) findViewById(R.id.edtmoney);
        edttext=(EditText)findViewById(R.id.edttext);
        edtmoney2 = (EditText) findViewById(R.id.edtmoney2);
        edttext2=(EditText)findViewById(R.id.edttext2);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert2 = (Button) findViewById(R.id.btnInsert2);
        day=(TextView)findViewById(R.id.day);
        day2=(TextView)findViewById(R.id.day2);

        //캘린더에서 클릭한 날짜 인텐트로 가져오기
        Intent comingIntent = getIntent();
        String date = comingIntent.getStringExtra("date");
        String date3 = comingIntent.getStringExtra("date3");
        date4=Integer.parseInt(date3);
        day.setText(date);
        day2.setText(date);

        //수입탭 항목버튼 (용돈, 월급) 색상바꾸기
        final Button btnIn1=(Button)findViewById(R.id.btnIn1);
        final Button btnIn2=(Button)findViewById(R.id.btnIn2);
        btnIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIn1.setBackgroundColor(Color.rgb(212,244,250));
                btnIn2.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=1;
            }
        });
        btnIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIn2.setBackgroundColor(Color.rgb(212,244,250));
                btnIn1.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=2;
            }
        });

        //지출탭 항목버튼 (식비, 교통비, 통신비, 공과금, 생필품) 색상바꾸기
        final Button btnOut1=(Button)findViewById(R.id.btnOut1);
        final Button btnOut2=(Button)findViewById(R.id.btnOut2);
        final Button btnOut3=(Button)findViewById(R.id.btnOut3);
        final Button btnOut4=(Button)findViewById(R.id.btnOut4);
        final Button btnOut5=(Button)findViewById(R.id.btnOut5);
        btnOut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOut1.setBackgroundColor(Color.rgb(212,244,250));
                btnOut2.setBackgroundColor(Color.rgb(234,234,234));
                btnOut3.setBackgroundColor(Color.rgb(234,234,234));
                btnOut4.setBackgroundColor(Color.rgb(234,234,234));
                btnOut5.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=3;
            }
        });
        btnOut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOut2.setBackgroundColor(Color.rgb(212,244,250));
                btnOut1.setBackgroundColor(Color.rgb(234,234,234));
                btnOut3.setBackgroundColor(Color.rgb(234,234,234));
                btnOut4.setBackgroundColor(Color.rgb(234,234,234));
                btnOut5.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=4;
            }
        });
        btnOut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOut3.setBackgroundColor(Color.rgb(212,244,250));
                btnOut1.setBackgroundColor(Color.rgb(234,234,234));
                btnOut2.setBackgroundColor(Color.rgb(234,234,234));
                btnOut4.setBackgroundColor(Color.rgb(234,234,234));
                btnOut5.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=5;
            }
        });
        btnOut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOut4.setBackgroundColor(Color.rgb(212,244,250));
                btnOut2.setBackgroundColor(Color.rgb(234,234,234));
                btnOut3.setBackgroundColor(Color.rgb(234,234,234));
                btnOut1.setBackgroundColor(Color.rgb(234,234,234));
                btnOut5.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=6;
            }
        });
        btnOut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOut5.setBackgroundColor(Color.rgb(212,244,250));
                btnOut2.setBackgroundColor(Color.rgb(234,234,234));
                btnOut3.setBackgroundColor(Color.rgb(234,234,234));
                btnOut4.setBackgroundColor(Color.rgb(234,234,234));
                btnOut1.setBackgroundColor(Color.rgb(234,234,234));
                btnnum=7;
            }
        });

        myHelper = new myDBHelper(this);

        //수입탭 입력버튼
        btnInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inout=1;
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO d11234567 VALUES ( '"
                        + edttext.getText().toString() + "' , "  //내용
                        + date4 + " , "  //날짜
                        + inout + " , "  //수입인지 지출인지
                        + btnnum + " , "  //항목
                        + edtmoney.getText().toString() + ");");  //금액
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨",
                        Toast.LENGTH_SHORT).show();
            }

        });

        //지출탭 입력버튼
        btnInsert2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inout=2;
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO d11234567 VALUES ( '"
                        + edttext2.getText().toString() + "' , "  //내용
                        + date4 + " , "  //날짜
                        + inout + " , "  //수입인지 지출인지
                        + btnnum + " , "  //항목
                        + edtmoney2.getText().toString() + ");");  //금액
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    //홈버튼
    public void btn2(View v){
        Intent intent002=new Intent(this, MainActivity.class);

        startActivity(intent002);
    }

    public void chart(View v){
        Intent intent003=new Intent(this, Main3Activity.class);

        startActivity(intent003);
    }

    public class myDBHelper extends SQLiteOpenHelper {
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

    //입력버튼
    public void btn3(View v){
        Intent intent003=new Intent(this, MainActivity.class);
        startActivity(intent003);
    }
}