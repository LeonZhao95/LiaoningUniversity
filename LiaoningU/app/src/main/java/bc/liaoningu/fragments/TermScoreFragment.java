package bc.liaoningu.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TermScoreFragment extends Fragment {
    private Map<String,String> cookies=null;
    private int rownum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_term_score, container, false);
        v.findViewById(R.id.imgbtn_back_term_score).setOnClickListener(new View.OnClickListener() {
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
                    Document ascore = Jsoup.connect("http://jwgl.lnu.edu.cn/pls/wwwbks/bkscjcx.curscopre").cookies(cookies).get();
                    Elements es_ascore = ascore.select("table").select("td");
                    byte bytes[] = {(byte) 0xC2,(byte) 0xA0};
                    String UTFSpace = new String(bytes,"utf-8");
                    ArrayList<String> tscorelist = new ArrayList<String>();
                    for (Element e : es_ascore) {
                        tscorelist.add(e.text().replaceAll(UTFSpace, "").replaceAll(" ", ""));
                    }
                    rownum = tscorelist.size()/8;
                    String[][] a = new String[rownum][8];
                    int n=12;
                    for(int i=0;i<rownum-1;i++){
                        for(int j=0;j<8;j++){
                            if(tscorelist.get(n)!=null&&tscorelist.get(n).startsWith(".")){
                                tscorelist.set(n,"0"+tscorelist.get(n));
                            }
                            a[i][j] = tscorelist.get(n);
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
            showTermScore((String[][]) msg.obj);
        }
    };

    private void showTermScore(String[][] tscore){
        GridLayout gd =(GridLayout) getView().findViewById(R.id.term_Grid);
        int bcol = getView().findViewById(R.id.rank).getWidth();
        int brow = getView().findViewById(R.id.rank).getHeight();
        int onecol =getView().findViewById(R.id.term_name).getWidth();
        int tcol = getView().findViewById(R.id.term_mark).getWidth();
        for(int i=0;i<rownum-1;i++){
            for(int j=0;j<8;j++){
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
                    case 0:if(tscore[i][j]==null){
                        CheckBox cb = new CheckBox(getActivity());
                        cb.setWidth(bcol);
                        gd.addView(cb);
                        break;
                    }else {
                        tv.setText(tscore[i][j]);
                        tv.setWidth(bcol);
                        gd.addView(tv);
                        break;
                    }
                    case 2:tv.setText(tscore[i][j]);tv.setWidth(onecol); gd.addView(tv);break;
                    case 4:tv.setText(tscore[i][j]);tv.setWidth(tcol); gd.addView(tv);break;
                    case 6:tv.setText(tscore[i][j]);tv.setWidth(tcol);gd.addView(tv);break;
                    default:break;
                }
            }
            /*for(int k=0;k<4;k++){
                TextView line =new TextView(getActivity());
                line.setWidth(GridLayout.LayoutParams.MATCH_PARENT);
                line.setHeight(3);
                gd.addView(line);
            }*/
        }
    }
}
