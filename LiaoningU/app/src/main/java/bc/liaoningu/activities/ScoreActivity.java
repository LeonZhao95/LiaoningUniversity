package bc.liaoningu.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import bc.liaoningu.R;
import bc.liaoningu.fragments.AllScoreFragment;
import bc.liaoningu.fragments.TermScoreFragment;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{
    private AllScoreFragment asf;
    private TermScoreFragment tsf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window=ScoreActivity.this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_score);
        Button btn_alls = (Button) findViewById(R.id.btn_alls);
        Button btn_terms = (Button) findViewById(R.id.btn_terms);
        assert btn_alls != null;
        btn_alls.setOnClickListener(this);
        assert btn_terms != null;
        btn_terms.setOnClickListener(this);
        setDefaultFragment();
    }
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        tsf = new TermScoreFragment();
        transaction.replace(R.id.id_content, tsf);
        transaction.commit();
    }
    @Override
    public void onClick(View v)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId())
        {
            case R.id.btn_terms:
                if (tsf == null) {
                    tsf = new TermScoreFragment();
                }
                transaction = fm.beginTransaction();
                transaction.replace(R.id.id_content, tsf);
                break;
            case R.id.btn_alls:
                if ( asf== null) {
                    asf = new AllScoreFragment();
                }
                transaction = fm.beginTransaction();
                transaction.replace(R.id.id_content, asf);
                break;
        }
        transaction.commit();
    }

}
