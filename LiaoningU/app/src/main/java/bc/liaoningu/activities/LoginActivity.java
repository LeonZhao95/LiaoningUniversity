package bc.liaoningu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bc.liaoningu.R;
import bc.liaoningu.Utils.SaveData;

public class LoginActivity extends AppCompatActivity {

    private EditText etxt_stuid;
    private EditText etxt_password;
    private TextView tv_login;
    private String user;
    private String password;
    private String filename;
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int TIMEOUT= -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etxt_stuid = (EditText)findViewById(R.id.etxt_stuid);
        etxt_password = (EditText)findViewById(R.id.etxt_password);
        tv_login =(TextView)findViewById(R.id.login_report);
        Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window=LoginActivity.this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_login);
        if (btn_confirm != null) {
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user = etxt_stuid.getText().toString();
                    password = etxt_password.getText().toString();
                    sendResquest();
                }
            });
        }
    }

    private  Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(etxt_stuid.getText().toString().isEmpty()||etxt_password.getText().toString().isEmpty()){
                tv_login.setText("请输入学号或密码");
            }else{
                switch (msg.what){
                    case SUCCESS:
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        startActivity(intent);break;
                    case FAIL:
                        tv_login.setText("学号或密码有误");break;
                    case TIMEOUT:
                        tv_login.setText("登录超时，请重新尝试");break;
                }
            }
        }
    };
    private void sendResquest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> datas=new HashMap<>();
                datas.put("stuid",user);
                datas.put("pwd", password);
                Connection con= Jsoup.connect("http://jwgl.lnu.edu.cn/pls/wwwbks/bks_login2.login");//获取连接
                try {
                    con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");//配置模拟浏览器
                    Connection.Response response  = con.timeout(2000).method(Connection.Method.POST).data(datas).execute();
                    char c = response.parse().text().charAt(0);
                    Message msg = new Message();
                    if(c=='登'){
                        msg.what = SUCCESS;
                        SaveData.saveObject(LoginActivity.this,"cookies",response.cookies());
                    }else{
                        msg.what = FAIL;
                    }
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = TIMEOUT;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}
