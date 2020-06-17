package com.sung.classschedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by sung at 2020/6/16
 *
 * @desc: 课表
 * @notice:
 */
public class ClassScheduleView extends RecyclerView {
    private ClassScheduleEntity mClassEntity;

    public ClassScheduleView(@NonNull Context context) {
        super(context);
    }

    public ClassScheduleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setData(int row, int column){
//        mClassEntity = new ClassScheduleEntity(row,column,)
    }
}
