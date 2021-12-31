package com.omkarph.wa_anyone.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omkarph.wa_anyone.ui.chat.ChatFragment;
import com.omkarph.wa_anyone.ui.history.HistoryFragment;

public class TabAdapter extends FragmentStateAdapter {

    Fragment chat, history;

    public TabAdapter(@NonNull Fragment fragment) {
        super(fragment);
        chat = new ChatFragment();
        history = new HistoryFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return chat;
        return history;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
