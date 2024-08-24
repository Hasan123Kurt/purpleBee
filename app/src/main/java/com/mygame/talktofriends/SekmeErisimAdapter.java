package com.mygame.talktofriends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SekmeErisimAdapter extends FragmentPagerAdapter {


    public SekmeErisimAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public int Renkdegisim=0;

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                Renkdegisim=0;
                return chatsFragment;
            case 1:
                GruplarFragment gruplarFragment = new GruplarFragment();
                Renkdegisim=1;
                return gruplarFragment;
            case 2:
                KisilerFragment kisilerFragment = new KisilerFragment();
                Renkdegisim=2;
                return kisilerFragment;
            case 3:
                TaleplerFragment taleplerFragment = new TaleplerFragment();
                Renkdegisim=3;
                return taleplerFragment;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Chats";
            case 1:
                return "Groups";
            case 2:
                return "My Friends";
            case 3:
                return "Request";
            default:
                return null;
        }
    }
}
