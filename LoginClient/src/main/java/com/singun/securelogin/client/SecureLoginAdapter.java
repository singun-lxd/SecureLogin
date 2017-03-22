package com.singun.securelogin.client;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.singun.securelogin.user.LoginInfo;

import java.util.List;

/**
 * Created by singun on 2017/3/22 0022.
 */

public class SecureLoginAdapter extends RecyclerView.Adapter<SecureLoginAdapter.ViewHolder> {
    private List<LoginInfo> mDataSet;
    private OnItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            textView = (TextView) v.findViewById(android.R.id.text1);
            rootView = v;
        }

        public TextView getTextView() {
            return textView;
        }

        public View getRootView() {
            return rootView;
        }
    }

    public SecureLoginAdapter(List<LoginInfo> dataSet) {
        setData(dataSet);
    }

    public void setData(List<LoginInfo> dataSet) {
        mDataSet = dataSet;
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final LoginInfo loginInfo = mDataSet.get(position);
        viewHolder.getTextView().setText(loginInfo.toString());
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, loginInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, LoginInfo loginInfo);
    }
}

