package com.sung.classschedule;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by sung at 2020/6/16
 *
 * @desc: 课表
 * @notice:
 */
public class ClassScheduleView extends RecyclerView {
    private Context context;
    private ClassScheduleEntity mClassEntity;
    private ClassScheduleAdapter mClassAdapter;

    public ClassScheduleView(@NonNull Context context) {
        super(context);
        this.context = context.getApplicationContext();
    }

    public ClassScheduleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context.getApplicationContext();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setData(ClassScheduleEntity entity) {
        mClassEntity = entity;
        setLayoutManager();
        setHasFixedSize(true);
        setItemAnimator(new DefaultItemAnimator());
        setAdapter();
    }

    private void setAdapter() {
        if (mClassAdapter == null) {
            mClassAdapter = new ClassScheduleAdapter();
        }
        setAdapter(mClassAdapter);
    }

    private void setLayoutManager() {
        setLayoutManager(new GridLayoutManager(context, mClassEntity.getColumnCounts()));
    }

    /**
     * Create by sung at 2020/6/16
     *
     * @desc: 课程表适配器
     * @notice:
     */
    private class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public ClassScheduleAdapter() {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == Type.Header.ordinal()) {
                return new Header(inflater.inflate(R.layout.layout_table_header, parent, false));
            } else if (viewType == Type.Row.ordinal()) {
                return new Row(inflater.inflate(R.layout.layout_table_row, parent, false));
            } else if (viewType == Type.Column.ordinal()) {
                return new Column(inflater.inflate(R.layout.layout_table_column, parent, false));
            } else {
                return new Element(inflater.inflate(R.layout.layout_table_element, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Header) {
                ((Header) holder).onBind();
            }
            if (holder instanceof Row) {
                ((Row) holder).onBind(position);
            }
            if (holder instanceof Column) {
                ((Column) holder).onBind(position);
            }
            if (holder instanceof Element) {
                ((Element) holder).onBind(position);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return Type.Header.ordinal();
            }
            if (position < mClassEntity.getColumnCounts()) {
                return Type.Column.ordinal();
            }
            int pos = (position) % mClassEntity.getColumnCounts();
            if (pos == 0) {
                return Type.Row.ordinal();
            }
            return Type.Element.ordinal();
        }

        @Override
        public int getItemCount() {
            return mClassEntity.getRowCounts() * mClassEntity.getColumnCounts();
        }
    }

    /**
     * 表头
     */
    private class Header extends RecyclerView.ViewHolder {
        private TextView mRowTag, mColumnTag;

        public Header(@NonNull View itemView) {
            super(itemView);
            mRowTag = itemView.findViewById(R.id.tv_row_tag);
            mColumnTag = itemView.findViewById(R.id.tv_column_tag);
        }

        void onBind() {
            mRowTag.setText(mClassEntity.getRowTag());
            mColumnTag.setText(mClassEntity.getColumnTag());
        }
    }

    /**
     * 表行（首行）
     */
    private class Row extends RecyclerView.ViewHolder {
        private TextView mContent;

        public Row(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_row_content);
        }

        void onBind(int position) {
            try {
                position = position / mClassEntity.getColumnCounts() - 1;
                mContent.setText(mClassEntity.getRowTags().get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 表列（首列）
     */
    private class Column extends RecyclerView.ViewHolder {
        private TextView mContent;

        public Column(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_column_content);
        }

        void onBind(int position) {
            try {
                position--;
                mContent.setText(mClassEntity.getColumnTags().get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据元
     */
    private class Element extends RecyclerView.ViewHolder {
        private CheckBox mContent;

        public Element(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.cb_check);
        }

        void onBind(final int position) {
            mContent.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            mContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int row = position / mClassEntity.getColumnCounts();
                    int column = position % mClassEntity.getColumnCounts();
                    ClassScheduleEntity.ClassScheduleElement element = mClassEntity.getElement(row, column);
                    String log = "row:"+element.getRowPos()+"\tcolumn:"+element.getColumnPos()+"\tcheck:"+element.isCheck();
                    Log.e(ClassScheduleView.class.getSimpleName(),log);
                }
            });
        }
    }

    private enum Type {
        Header(0),
        Row(1),
        Column(2),
        Element(3);

        private int value;

        Type(int value) {
            this.value = value;
        }
    }
}
