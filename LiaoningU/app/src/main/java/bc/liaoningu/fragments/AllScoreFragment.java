package bc.liaoningu.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import bc.liaoningu.R;
import bc.liaoningu.Utils.SaveData;

public class AllScoreFragment extends Fragment {
    private Map<String,String> cookies=null;
    private GridLayout gd ;
    private int rownum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_all_score, container, false);
        gd=(GridLayout)v.findViewById(R.id.all_Grid);
        v.findViewById(R.id.imgbtn_back_all_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        getAllScore();
        return v;
    }

    private void getAllScore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cookies =(Map<String,String>) SaveData.readObject(getActivity(),"cookies");
                try {
                    Document ascore = Jsoup.connect("http://jwgl.lnu.edu.cn/pls/wwwbks/bkscjcx.yxkc").cookies(cookies).get();
                    Elements es_ascore = ascore.select("table").select("td");
                    byte bytes[] = {(byte) 0xC2,(byte) 0xA0};
                    String UTFSpace = new String(bytes,"utf-8");
                    ArrayList<String> ascorelist = new ArrayList<String>();
                    for (Element e : es_ascore) {
                        ascorelist.add(e.text().replaceAll(UTFSpace, "").replaceAll(" ", ""));
                    }
                    rownum = ascorelist.size()/6;
                    String[][] a = new String[rownum][6];
                    int n=10;
                    for(int i=0;i<rownum-1;i++){
                        for(int j=0;j<6;j++){
                            if(ascorelist.get(n)!=null&&ascorelist.get(n).startsWith(".")){
                                ascorelist.set(n,"0"+ascorelist.get(n));
                            }
                            a[i][j] = ascorelist.get(n);
                            n++;
                        }
                    }
                    Message msg = new Message();
                    msg.obj=a;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            showAllScore((String[][]) msg.obj);
        }
    };

    private void showAllScore(String[][] ascore){
        int onecol =getView().findViewById(R.id.coursename).getWidth();
        int tcol = getView().findViewById(R.id.all_mark).getWidth();
        for(int i=0;i<rownum-1;i++){
            for(int j=0;j<6;j++){
                TextView tv = new TextView(getActivity());
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setHeight(150);
                if(i%2==1){
                    tv.setBackgroundColor(getResources().getColor(R.color.from1));
                }else{
                    tv.setBackgroundColor(getResources().getColor(R.color.from2));
                }
                switch (j){
                    case 1:tv.setText(ascore[i][j]);tv.setWidth(onecol);gd.addView(tv);break;
                    case 3:tv.setText(ascore[i][j]);tv.setWidth(tcol);gd.addView(tv);break;
                    case 5:
                        if(Character.isDigit(ascore[i][j].charAt(0))){if(Integer.parseInt(ascore[i][j])<60){tv.setTextColor(Color.RED);}}
                        else if(ascore[i][j].charAt(0)=='不'){
                            tv.setTextColor(Color.RED);
                        }
                        tv.setText(ascore[i][j]);tv.setWidth(tcol);gd.addView(tv);break;
                    default:break;
                }
            }
            /*for(int k=0;k<3;k++){
                TextView line =new TextView(getActivity());
                line.setWidth(GridLayout.LayoutParams.MATCH_PARENT);
                line.setHeight(3);
                gd.addView(line);
            }*/
        }
    }
}
