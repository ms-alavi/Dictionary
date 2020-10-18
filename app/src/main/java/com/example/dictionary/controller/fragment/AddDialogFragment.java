package com.example.dictionary.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dictionary.R;
import com.example.dictionary.controller.CallBack;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.WordDBRepository;


public class AddDialogFragment extends DialogFragment {
    private EditText mEdtEnglish,mEdtPersian;
    private Button mButtonAdd;
    private Word mWord;
    private WordDBRepository mWordDBRepository;
    private CallBack mCallBack;


    public AddDialogFragment() {
        // Required empty public constructor
    }

    public static AddDialogFragment newInstance() {
        AddDialogFragment fragment = new AddDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository=WordDBRepository.getInstance(getActivity());
        mWord=new Word();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_dialog,
                null,false);

        findViews(view);

        setListeners();

        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(view).create();

        return alertDialog;
    }

    private void setListeners() {
        mEdtEnglish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setEnglish(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtPersian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWord.setPersian(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtEnglish.getText().toString().equals(null)||
                        mEdtPersian.getText().toString().equals(null)||
                        isValueTextInput(mEdtEnglish.getText().toString())
                        ||isValueTextInput(mEdtPersian.getText().toString())){
                    if (mEdtEnglish.getText().toString().equals(null)||
                            isValueTextInput(mEdtEnglish.getText().toString())){
                        mEdtEnglish.setText(null);
                        mEdtEnglish.setHint(R.string.emtyEdt);
                        mEdtEnglish.setHintTextColor(Color.RED);
                    }
                     if (mEdtPersian.getText().toString().equals(null)||
                            isValueTextInput(mEdtPersian.getText().toString())){
                        mEdtPersian.setText(null);
                        mEdtPersian.setHint(R.string.emtyEdtPer);
                        mEdtPersian.setHintTextColor(Color.RED);
                    }
                }else {
                    mWordDBRepository.insertWord(mWord);
                    mCallBack.onListUpdate();
                    dismiss();
                }

            }
        });
    }
    public boolean isValueTextInput(String string) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) != ' ') return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBack ) mCallBack = (CallBack) context;
    }

    private void findViews(View view) {
        mEdtEnglish=view.findViewById(R.id.edt_add_english);
        mEdtPersian=view.findViewById(R.id.edt_add_persian);
        mButtonAdd=view.findViewById(R.id.btn_add_add);
    }
}