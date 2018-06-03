package edward.com.little_aunt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout periodLinearLayout;
    private LinearLayout circleLinearLayout;
    private LinearLayout LastPeriodLinearLayout;
    private TextView periodTextView;
    private TextView circleTextView;
    private TextView lastPeriodTextView;
    private TextView useBtnView;

    private String periodText = null;
    private String circleText = null;
    private String lastPeriodText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        periodLinearLayout = (LinearLayout) findViewById(R.id.period_container);
        periodLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPeriodText();
            }
        });
        circleLinearLayout = (LinearLayout) findViewById(R.id.circle_container);
        circleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCircleText();
            }
        });
        LastPeriodLinearLayout = (LinearLayout) findViewById(R.id.last_period_container);
        LastPeriodLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLastPeriodText();
            }
        });
        periodTextView = (TextView) findViewById(R.id.period_text);
        circleTextView = (TextView) findViewById(R.id.circle_text);
        lastPeriodTextView = (TextView) findViewById(R.id.last_period_text);
        useBtnView = (TextView) findViewById(R.id.user_btn);
        useBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == periodText) {
                    Snackbar.make(view, "请选择经期长度", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (null == circleText) {
                    Snackbar.make(view, "请选择周期长度", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (null == lastPeriodText) {
                    Snackbar.make(view, "请选择最后一次月经时间", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("base_info_record", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("period_length", periodText);
                editor.putString("circle_length", circleText);
                editor.putString("last_period", lastPeriodText);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setPeriodText() {
        final List<String> list = new ArrayList();
        for (int i = 2; i < 15; i++) {
            list.add(i + "天");
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(MainActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String day = list.get(options1);
                periodText = day.substring(0, day.length() - 1);
                periodTextView.setText(day);
            }
        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleText("选择经期天数")//标题文字
                .setSelectOptions(3)//默认选中项
                .build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    private void setCircleText() {
        final List<String> list = new ArrayList();
        for (int i = 15; i < 101; i++) {
            list.add(i + "天");
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(MainActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String day = list.get(options1);
                circleText = day.substring(0, day.length() - 1);
                circleTextView.setText(day);
            }
        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleText("选择周期天数")//标题文字
                .setSelectOptions(13)//默认选中项
                .build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    private void setLastPeriodText() {
        TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                lastPeriodText = new SimpleDateFormat("yyyy-MM-dd").format(date);
                lastPeriodTextView.setText(lastPeriodText);
            }
        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleText("选择最近月经周期")//标题文字
                .build();
        pvTime.show();
    }
}
