package com.esgi.android.news.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.android.news.R;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.physique.db.dao.ItemDAO;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            String itemId = getArguments().getString(ARG_ITEM_ID);
            if(itemId != null){
                loadItem(Integer.valueOf(itemId));
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getMagazine());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        FloatingActionButton shareFab = (FloatingActionButton) rootView.findViewById(R.id.item_detail_share);
        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = mItem.getUrlItem();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mItem.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, ""));

                Snackbar.make(view, "SHARE", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton httpFab = (FloatingActionButton) rootView.findViewById(R.id.item_detail_open_web);
        httpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof ItemDetailActivity){
                    ((ItemDetailActivity)getActivity()).launchItemWebview(mItem.getUrlItem());
                }
                Snackbar.make(view, "HTTP", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail_title)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.item_detail_description)).setText(mItem.getDescription());
            if(mItem.getUrlPicture() == null){
                ((ImageView) rootView.findViewById(R.id.item_detail_img)).setVisibility(View.GONE);
            } else {
                Picasso.with(getActivity()).load(mItem.getUrlPicture()).into(((ImageView) rootView.findViewById(R.id.item_detail_img)));
            }
            if(mItem.getDate() != null){
                ((TextView) rootView.findViewById(R.id.item_detail_date)).setText(mItem.getDate().toString());
            } else {
                ((TextView) rootView.findViewById(R.id.item_detail_date)).setVisibility(View.GONE);
            }
        }

        return rootView;
    }

    public void loadItem(int idItem){
        ItemDAO itemDAO = new ItemDAO(getActivity());
        itemDAO.open();
        mItem = itemDAO.get(idItem);
        itemDAO.close();
    }
}
