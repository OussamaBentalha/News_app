package com.esgi.android.news.client.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.android.news.R;
import com.esgi.android.news.client.recycler.RVAdapter;
import com.esgi.android.news.metier.data.DownloadTask;
import com.esgi.android.news.metier.enumeration.Newspaper;
import com.esgi.android.news.metier.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EurosportFragment extends Fragment{

    private List<Item> items;
    private RecyclerView rv;
    private Context context;

    public EurosportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eurosport, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity().getApplicationContext();

        initializeData();
        initializeAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv=(RecyclerView)getView().findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
    }

    private void initializeData(){
        items = new ArrayList<>();

        DownloadTask load = new DownloadTask();
        load.setFluxRSS(Newspaper.LEQUIPE);
        items = load.downloadNews();
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(context, items);
        rv.setAdapter(adapter);

    }
}
