package com.android.demo.ui.activity.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.demo.R;
import com.android.demo.ui.activity.base.AbstractBaseActivity;

import java.util.ArrayList;


/**
 * Created by 321930 on 8/20/2015.
 */
public class RecyclerViewDemoActivity extends AbstractBaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private ArrayList<String> mDataSet;

    RecyclerViewDemoActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_demo);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataSet = getData();


        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<String> getData() {
        ArrayList<String> mData = new ArrayList<>();
        mData.add("Ten");
        mData.add("Ten");
        mData.add("Ten");
        mData.add("Ten");
        mData.add("Ten");
        mData.add("Ten");

        return mData;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<String> mDataSet;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView txtHeader;
            public TextView txtFooter;

            public ViewHolder(View v) {
                super(v);
                txtHeader = (TextView) v.findViewById(R.id.firstLine);
                txtFooter = (TextView) v.findViewById(R.id.secondLine);
            }
        }

        public void add(int position, String item) {
            mDataSet.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(String item) {
            int position = mDataSet.indexOf(item);
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<String> myDataSet) {
            mDataSet = myDataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final String name = mDataSet.get(position);
            holder.txtHeader.setText(mDataSet.get(position));
            holder.txtHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(name);
                }
            });

            holder.txtFooter.setText("Footer: " + mDataSet.get(position));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

    }
}
