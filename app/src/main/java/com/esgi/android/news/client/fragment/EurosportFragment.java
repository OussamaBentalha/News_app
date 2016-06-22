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
import com.esgi.android.news.physique.wb.DownloadTask;
import com.esgi.android.news.metier.enumeration.EnumNewspaper;
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
    private EnumNewspaper mNewspaper;

    public EurosportFragment() {
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
        mNewspaper = (EnumNewspaper) getArguments().get(EnumNewspaper.class.getSimpleName());

        int userId = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getInt(getString(R.string.user_id_key), 0);


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
        load.setFluxRSS(mNewspaper);
        items = load.downloadNews();
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(context, items);
        rv.setAdapter(adapter);

    }
}
