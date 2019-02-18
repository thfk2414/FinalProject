package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;

public class Test6 extends AppCompatActivity {

    int w , c, sW, aW, sC, wC;
    TextView preview;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test6);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm", 1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);

        Log.i("test",warm +"입니다.");
        Log.i("test",cool+"입니다.");
        Log.i("test",sw+"입니다.");
        Log.i("test",aw+"입니다.");
        Log.i("test",sc+"입니다.");
        Log.i("test",wc+"입니다.");

        w = warm;
        c = cool;
        sW = sw;
        aW = aw;
        sC = sc;
        wC = wc;
        preview = (TextView)findViewById(R.id.preview);
        next = (TextView)findViewById(R.id.next);

        final RadioGroup rg6 = (RadioGroup)findViewById(R.id.radioGroup6);
        //６. 햇몉에 잘타는 타입인가요?
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);

                if(rb.isChecked()){
                    if(rb.getText().equals("피부가 까맣게타고 오래 지속된다")){
                        w++;
                        sW++;
                        aW++;
                    }else if(rb.getText().equals("피부가 빨갛게타고 금방 돌아온다")) {
                        c++;
                        sC++;
                        wC++;
                    }
                }
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Test7.class);
                        intent.putExtra("warm",w);
                        intent.putExtra("cool",c);
                        intent.putExtra("sw",sW);
                        intent.putExtra("aw",aW);
                        intent.putExtra("sc",sC);
                        intent.putExtra("wc",wC);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onclick(View view){

        Intent intent = null;

        switch (view.getId()){

            case R.id.preview: intent = new Intent(this, Test5.class);
                startActivity(intent);
                break;
            case R.id.next:
                Toast.makeText(getApplicationContext(),"선택해주세요.",Toast.LENGTH_LONG).show();
            break;
        }


    }
}
