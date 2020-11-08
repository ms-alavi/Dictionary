package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.WordDBRepository;

public class EditDialogFragment extends DialogFragment {

    public static final String ARGS_WORD = "argsWord";
    private EditText mEdtEnglish,mEdtPersian;
    private Button mButtonEdit,mButtonDelete;
    private Word mWord;
    private WordDBRepository mWordDBRepository;

    public EditDialogFragment() {
        // Required empty public constructor
    }


    public static EditDialogFragment newInstance(Word word) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WORD,word);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository=WordDBRepository.getInstance(getContext());
        mWord= (Word) getArguments().getSerializable(ARGS_WORD);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edite,
                null,false);

        findViews(view);
        initViews();
        setListeners();

        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(view).create();

        return alertDialog;
    }

    private void initViews() {
        mEdtEnglish.setText(mWord.getEnglish());
        mEdtPersian.setText(mWord.getPersian());
    }

    private void findViews(View view) {
        mEdtEnglish=view.findViewById(R.id.edt_edit_english);
        mEdtPersian=view.findViewById(R.id.edt_edit_persian);
        mButtonDelete=view.findViewById(R.id.btn_edit_delete);
        mButtonEdit=view.findViewById(R.id.btn_edit_edit);
    }
    private void setListeners(){
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDBRepository.updateWord(mWord);
                sendIntent();
                dismiss();
            }
        });
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDBRepository.deleteWord(mWord);
                sendIntent();
                dismiss();
            }
        });
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
    }
    private void sendIntent(){
        Fragment fragment=getTargetFragment();
        int requestCode=getTargetRequestCode();
        int resultCode= Activity.RESULT_OK;
        Intent intent=new Intent();
        fragment.onActivityResult(requestCode,resultCode,intent);
    }
}