package com.ravemaster.fuelsave.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ravemaster.fuelsave.fragments.DailyFragment;
import com.ravemaster.fuelsave.fragments.InventoryFragment;
import com.ravemaster.fuelsave.fragments.OperationsFragment;
import com.ravemaster.fuelsave.fragments.SettingsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new OperationsFragment();
            case 2:
                return new InventoryFragment();
            case 3:
                return new SettingsFragment();
            default:
                return new DailyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
