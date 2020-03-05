package com.inc.fsi.fnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.inc.fsi.fnn.R;
import com.inc.fsi.fnn.components.CardFragment;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class HomeFragment extends Fragment {

    ViewPager2 mPager;
    FragmentStateAdapter pagerAdapter;
    TransactionsRVAdapter transactionsRVAdapter;
    RecyclerView rvTransactions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_home, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = fragView.findViewById(R.id.pager_cards);
        pagerAdapter = new CardPagerAdapter(getActivity());
        mPager.setAdapter(pagerAdapter);

        DotsIndicator dotsIndicator = fragView.findViewById(R.id.dots_indicator);

        dotsIndicator.setViewPager2(mPager);

        transactionsRVAdapter = new TransactionsRVAdapter();

        rvTransactions = fragView.findViewById(R.id.rv_transactions);

        rvTransactions.setAdapter(transactionsRVAdapter);

        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    private class CardPagerAdapter extends FragmentStateAdapter {
        public CardPagerAdapter(FragmentActivity fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return CardFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    public class TransactionsRVAdapter extends
            RecyclerView.Adapter<TransactionsRVAdapter.TransactionViewHolder>{

        TransactionsRVAdapter() {

        }

        @NonNull
        @Override
        public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View restaurantView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.transaction, viewGroup, false);
            return new TransactionViewHolder(restaurantView);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionViewHolder viewHolder, int position) {
            viewHolder.bindView();
        }

        @Override
        public int getItemCount() {
            return 6;
        }


        class TransactionViewHolder extends RecyclerView.ViewHolder{

            TransactionViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            void bindView(){

            }
        }
    }
}
