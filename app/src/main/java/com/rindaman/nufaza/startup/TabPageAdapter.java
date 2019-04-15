package com.rindaman.nufaza.startup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rindaman.nufaza.fragment.Absensi;
import com.rindaman.nufaza.fragment.Kegiatan;
import com.rindaman.nufaza.fragment.Keluar;
import com.rindaman.nufaza.fragment.Lembur;
import com.rindaman.nufaza.fragment.PiketMalam;

public class TabPageAdapter extends FragmentPagerAdapter {

    public static int PAGE_COUNT = 5;

    private String judulTab[] = new String[] {"Absensi","Keluar","Kegiatan","Piket Malam","Lembur"};

    public TabPageAdapter(FragmentManager fm) {super(fm);}

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Absensi();
            case 1:
                return new Keluar();
            case 2:
                return new Kegiatan();
            case 3:
                return new PiketMalam();
            case 4:
                return new Lembur();
        }
        return null;
    }

    @Override
    public int getCount() {return PAGE_COUNT;}

    @Override
    public CharSequence getPageTitle(int position) {
        return judulTab[position];
    }
}
