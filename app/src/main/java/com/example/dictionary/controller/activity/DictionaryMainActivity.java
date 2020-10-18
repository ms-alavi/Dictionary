package com.example.dictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.dictionary.R;
import com.example.dictionary.controller.CallBack;
import com.example.dictionary.controller.fragment.DictionaryMainFragment;

public class DictionaryMainActivity extends AppCompatActivity implements CallBack {
    private DictionaryMainFragment mDictionaryMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
       mDictionaryMainFragment=DictionaryMainFragment.newInstance();
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.container_activity);
        if (fragment==null) {
            fragmentManager.beginTransaction().add(R.id.container_activity, mDictionaryMainFragment)
                    .commit();
        }
    }

    @Override
    public void onListUpdate() {
        mDictionaryMainFragment.updateUI();
    }
}