package bc.liaoningu.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import bc.liaoningu.R;
import bc.liaoningu.Utils.SaveData;

public class CourseActivity extends AppCompatActivity {
    private Map<String,String> cookies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window=CourseActivity.this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_course);
        final ImageButton ibtn =(ImageButton) findViewById(R.id.imgbtn_back);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ibtn.setImageResource(R.drawable.back_click);
                finish();
            }
        });
        getCourse();
    }

    public void showCourse(ArrayList<String> clist){
        GridLayout Gd = (GridLayout)findViewById(R.id.courseGrid);
        int n=0;
        TextView[] tv =null;
        Integer[] view = {R.id.tv11,R.id.tv12,R.id.tv13,R.id.tv14,R.id.tv15,R.id.tv16,R.id.tv17,
                        R.id.tv21,R.id.tv22,R.id.tv23,R.id.tv24,R.id.tv25,R.id.tv26,R.id.tv27,
                        R.id.tv31,R.id.tv32,R.id.tv33,R.id.tv34,R.id.tv35,R.id.tv36,R.id.tv37,
                        R.id.tv41,R.id.tv42,R.id.tv43,R.id.tv44,R.id.tv45,R.id.tv46,R.id.tv47,
                        R.id.tv51,R.id.tv52,R.id.tv53,R.id.tv54,R.id.tv55,R.id.tv56,R.id.tv57,
                        R.id.tv61,R.id.tv62,R.id.tv63,R.id.tv64,R.id.tv65,R.id.tv66,R.id.tv67,
                        R.id.tv71,R.id.tv72,R.id.tv73,R.id.tv74,R.id.tv75,R.id.tv76,R.id.tv77,};
        int[] colors ={R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6,R.color.color7};
        Random random = new Random();
        tv = new TextView[49];
        for(String s:clist){
            tv[n]=(TextView)findViewById(view[n]);
            tv[n].setText(s);
            tv[n].setTextColor(Color.WHITE);
            tv[n].setGravity(Gravity.CENTER);
            if(!s.isEmpty())
            tv[n].setBackgroundColor(this.getResources().getColor(colors[random.nextInt(7)]));
            n++;
            if(n==49){
                break;
            }
        }
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            showCourse((ArrayList<String>) msg.obj);
        }
    };

    private void getCourse(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cookies =(Map<String,String>) SaveData.readObject(CourseActivity.this,"cookies");
                try {
                    Document course = Jsoup.connect("http://jwgl.lnu.edu.cn/pls/wwwbks/xk.CourseView").cookies(cookies).get();
                    Elements es_course = course.select("td[bgcolor=\"#EAE2F3\"]").select("p");
                    byte bytes[] = {(byte) 0xC2,(byte) 0xA0};
                    String UTFSpace = new String(bytes,"utf-8");
                    ArrayList<String> courselist=new ArrayList<String>();
                    for (Element e : es_course) {
                        courselist.add(e.text().replaceAll(UTFSpace, "").replaceAll(" ", ""));
                    }
                    Message msg = new Message();
                    msg.obj = courselist;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }






}
