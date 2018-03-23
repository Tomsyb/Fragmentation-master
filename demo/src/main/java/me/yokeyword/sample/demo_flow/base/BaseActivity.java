package me.yokeyword.sample.demo_flow.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;




import me.yokeyword.sample.App;

/**
 * Created by yulh on 2016/6/6.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // x.view().inject(this);
        App.mActivity = this;
        App.addActivity(this);
    }





    @Override
    public void finish() {
        super.finish();
        App.removeActivity(this);
    }

    protected void onDestroy() {
        App.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.mActivity = this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
