package com.sung.classschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by sung at 2020/6/16
 *
 * @desc: 课程表适配器
 * @notice:
 */
public class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ClassScheduleEntity mClassEntity;

    public ClassScheduleAdapter(ClassScheduleEntity entity) throws Exception {
        if (entity == null || entity.getRowCounts() <= 0 || entity.getColumnCounts() <= 0) {
            throw new Exception("unable to initialize!");
        }
        mClassEntity = entity;
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
        int pos = (position + 1) % mClassEntity.getRowCounts();
        if (pos == 1) {
            return Type.Column.ordinal();
        }
        if (pos == 0) {
            return Type.Row.ordinal();
        }
        return Type.Element.ordinal();
    }

    @Override
    public int getItemCount() {
        return mClassEntity.getRowCounts() * mClassEntity.getColumnCounts();
    }

    /**
     * 表头
     */
    public class Header extends RecyclerView.ViewHolder {
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
    public class Row extends RecyclerView.ViewHolder {
        private TextView mContent;

        public Row(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_row_content);
        }

        void onBind(int position) {

        }
    }

    /**
     * 表列（首列）
     */
    public class Column extends RecyclerView.ViewHolder {
        private TextView mContent;

        public Column(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_column_content);
        }

        void onBind(int position) {

        }
    }

    /**
     * 数据元
     */
    public class Element extends RecyclerView.ViewHolder {
        private CheckBox mContent;

        public Element(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.cb_check);
        }

        void onBind(int position) {

        }
    }

    public enum Type {
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
