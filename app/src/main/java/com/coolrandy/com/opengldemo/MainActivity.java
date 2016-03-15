package com.coolrandy.com.opengldemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    private Button button;
//    private ProxyActivity mProxyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
//        int i = 0;
//        ClassLoader classLoader = getClassLoader();
//        if(classLoader != null){
//            Log.e("TAG", "[onCreate] classloader " + i + ": " + classLoader.toString());
//            while (classLoader.getParent() != null){
//                classLoader = classLoader.getParent();
//                i++;
//                Log.e("TAG", "[onCreate] classloader " + i + ": " + classLoader.toString());
//            }
//        }

//        button = (Button)findViewById(R.id.call_apk);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(v.getId() == R.id.call_apk){
//
//                    Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
//                    intent.putExtra(ProxyActivity.EXTRA_DEX_PATH, "/mnt/sdcard/DynamicLoadHost/com.mtime-1.apk");
//                    startActivity(intent);
//                }
//            }
//        });
    }

    private void initView(Bundle savedInstanceState) {
        mProxyActivity.setContentView(generateContentView(mProxyActivity));
    }

    private View generateContentView(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.parseColor("#F79AB5"));
        Button button = new Button(context);
        button.setText("button");
        layout.addView(button, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked button",
                        Toast.LENGTH_SHORT).show();
                startActivityByProxy("com.coolrandy.com.opengldemo.TestActivity");
            }
        });
        return layout;
    }


}
