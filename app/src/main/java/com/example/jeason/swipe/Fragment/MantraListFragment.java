package com.example.jeason.swipe.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeason.swipe.Adapter.RecyclerViewAdapterFragment;
import com.example.jeason.swipe.R;
import com.example.jeason.swipe.SeparatorDecoration;

/**
 * Created by Jeason on 2018/3/6.
 */

public class MantraListFragment extends Fragment implements RecyclerViewAdapterFragment.OnItemClickListener {
    protected static final String DATASET = "data";
    static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    protected RecyclerView mRecyclerView;
    protected RecyclerViewAdapterFragment mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected SeparatorDecoration itemDecoration;
    private String TAG = MantraListFragment.class.getSimpleName();
    private String[] mDataset;

    public static MantraListFragment newInstance(String[] mantraList) {
        MantraListFragment fragment = new MantraListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LAYOUT_MANAGER, LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        args.putStringArray(DATASET, mantraList);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
//        mAdapter = new RecyclerViewAdapterFragment(mDataset, (Fragment2ActivityCommunicator) getActivity());
        mAdapter = new RecyclerViewAdapterFragment(mDataset, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
    }

    @Override
    public void onItemClick(View viewClicked, int clickedPosition) {
        TextView textView = (TextView) viewClicked;
        String text = textView.getText().toString();
//        TODO: Here to start the MantraDetailActivity which should play the selected mantra.
        Toast.makeText(getContext(), text + "   clicked position is   " + clickedPosition, Toast.LENGTH_SHORT).show();
//        Intent mantraDetailIntent = new Intent(getActivity(), MantraDetailActivity.class);
//        mantraDetailIntent.putExtra(DETAIL_KEY, text);
//        startActivity(mantraDetailIntent);
    }

    enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER, GRID_LAYOUT_MANAGER
    }


}
