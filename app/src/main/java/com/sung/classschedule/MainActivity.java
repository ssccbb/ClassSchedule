package com.sung.classschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ClassScheduleView mSchedule;
    private String[] times = {"上午\n(8:00-12:00)", "下午\n(12:00-18:00)", "晚上\n(18:00-23:00)"};
    private String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSchedule = findViewById(R.id.csv_schedule);
        ClassScheduleEntity entity = new ClassScheduleEntity(
                8, 4,
                "星期",
                "时间",
                Arrays.asList(weeks),
                Arrays.asList(times));
        mSchedule.setData(entity);
    }
}