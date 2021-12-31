package com.omkarph.wa_anyone.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omkarph.wa_anyone.MainActivity;
import com.omkarph.wa_anyone.R;
import com.omkarph.wa_anyone.db.Entry;
import com.omkarph.wa_anyone.db.HistoryHandler;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TabAdapter tabAdapter = new TabAdapter(this);

        TabLayout tabLayout = getView().findViewById(R.id.tabs);
        ViewPager2 viewPager = getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);

        new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> {
                    if(position == 0)
                        tab.setText("Open chat");
                    else
                        tab.setText("History");
                }
        ).attach();
    }
}