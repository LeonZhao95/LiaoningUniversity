package bc.liaoningu.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import bc.liaoningu.R;
import bc.liaoningu.fragments.MainPageFragment;
import bc.liaoningu.fragments.MessageFragment;
import bc.liaoningu.fragments.PersonalFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private ArrayList<Fragment> fragments;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            Window window = MainActivity.this.getWindow();
            window.setFlags(flag, flag);
            setContentView(R.layout.activity_main);
            BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
            bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
            bottomNavigationBar
                    .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                    );
            bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home, "Home").setActiveColorResource(R.color.white))
                    .addItem(new BottomNavigationItem(R.mipmap.bubbles, "Message").setActiveColorResource(R.color.white))
                    .addItem(new BottomNavigationItem(R.mipmap.male_user, "Personal").setActiveColorResource(R.color.white))
                    .setFirstSelectedPosition(0)
                    .initialise();

            fragments = getFragments();
            setDefaultFragment();
            bottomNavigationBar.setTabSelectedListener(this);
        }

        /** * 设置默认的 */
        private void setDefaultFragment() {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.layFrame, MainPageFragment.newInstance("Home"));
            transaction.commit();
        }

        private ArrayList<Fragment> getFragments() {
            ArrayList<Fragment> fragments = new ArrayList<>();
            fragments.add(MainPageFragment.newInstance("Home"));
            fragments.add(MessageFragment.newInstance("Message"));
            fragments.add(PersonalFragment.newInstance("Personal"));
            return fragments;
        }

        @Override
        public void onTabSelected(int position) {
            if (fragments != null) {
                if (position < fragments.size()) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = fragments.get(position);
                    if (!fragment.isAdded()) {
                        ft.replace(R.id.layFrame, fragment);
                    } else {
                        ft.add(R.id.layFrame, fragment);
                    }
                    ft.commitAllowingStateLoss();
                }
            }

        }

        @Override
        public void onTabUnselected(int position) {
            if (fragments != null) {
                if (position < fragments.size()) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = fragments.get(position);
                    ft.remove(fragment);
                    ft.commitAllowingStateLoss();
                }
            }
        }

        @Override
        public void onTabReselected(int position) {


            /*Log.d(null, "onTabSelected() called with: " + "position = [" + position + "]");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (fragments.get(position) == null) {
                transaction.replace(R.id.layFrame, fragments.get(position));
            }
            transaction.commit();*/
            /*switch (position) {
                case 0:
                    if (fragments.get(position) == null) {
                        transaction.replace(R.id.layFrame, fragments.get(position));
                    }
                    break;
                case 1:
                    if (fragments.get(position) == null) {
                        mFindFragment = FindFragment.newInstance("发现");
                    }
                    transaction.replace(R.id.tb, mFindFragment);
                    break;
                case 2:
                    if (fragments.get(position) == null) {
                        mFavoritesFragment = FavoritesFragment.newInstance("爱好");
                    }
                    transaction.replace(R.id.tb, mFavoritesFragment);
                    break;
                default:
                    break;
            }*/


        }
}

