package com.example.jeason.swipe.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.jeason.swipe.Adapter.RecyclerViewCustomAdapter;
import com.example.jeason.swipe.Interface.Fragment2ActivityCommunicator;
import com.example.jeason.swipe.R;
import com.example.jeason.swipe.SeparatorDecoration;

/**
 * Created by Jeason on 2018/3/6.
 */

public class BaseListFragment extends Fragment {
    static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected static final String DATASET = "data";
    private static final String TAG = BaseListFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    protected RecyclerView mRecyclerView;
    protected RecyclerViewCustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected SeparatorDecoration itemDecoration;
    private String[] mDataset;
    public BaseListFragment() {
//        setArguments(new Bundle());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDecoration = new SeparatorDecoration(getContext(), getResources().getColor(R.color.divider), 0.5f);
        Bundle mPersistentValue = getArguments();
        mDataset = mPersistentValue.getStringArray(DATASET);
        mCurrentLayoutManagerType = (LayoutManagerType) mPersistentValue.getSerializable(KEY_LAYOUT_MANAGER);
        if (mPersistentValue.containsKey(KEY_LAYOUT_MANAGER)) {
            mCurrentLayoutManagerType = (LayoutManagerType) mPersistentValue.getSerializable(KEY_LAYOUT_MANAGER);
        } else {
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new RecyclerViewCustomAdapter(mDataset, (Fragment2ActivityCommunicator) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //region radio button
//        mLinearLayoutRadioButton = rootView.findViewById(R.id.linear_layout_rb);
//        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
//            }
//        });
//        mGridLayoutRadioButton = rootView.findViewById(R.id.grid_layout_rb);
//        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
//            }
//        });
        //endregion
        return rootView;
    }

    private void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
    }

    enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER, GRID_LAYOUT_MANAGER
    }


}
