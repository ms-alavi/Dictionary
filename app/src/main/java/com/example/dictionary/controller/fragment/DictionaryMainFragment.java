package com.example.dictionary.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.WordDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DictionaryMainFragment extends Fragment {
    public static final String TAG_ADD_DIALOG_FRAGMENT = "tagAddDialogFragment";
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private WordDBRepository mWordDBRepository;
    private WordAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;



    public DictionaryMainFragment() {
        // Required empty public constructor
    }

    public static DictionaryMainFragment newInstance() {
        DictionaryMainFragment fragment = new DictionaryMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository=WordDBRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater
                .inflate(R.layout.fragment_dictionary_list, container, false);

        findViews(view);

        initViews();

        setListener();

        return view;
    }

    private void setListener() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment=AddDialogFragment.newInstance();
                addDialogFragment.show(getActivity().getSupportFragmentManager(),
                        TAG_ADD_DIALOG_FRAGMENT);

            }
        });
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        List<Word> words=mWordDBRepository.getWords();
        mRelativeLayout.setVisibility(words.size()==0? View.VISIBLE:View.INVISIBLE);
        if(mAdapter==null) {
            mAdapter = new WordAdapter(words);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setWords(words);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mRecyclerView=view.findViewById(R.id.word_recyclerView);
        mRelativeLayout=view.findViewById(R.id.rlt_task_empty_list);
        mFloatingActionButton=view.findViewById(R.id.fab);
    }
    private class WordHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewEnglish,mTextViewPersian;
        private Word mWord;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewEnglish=itemView.findViewById(R.id.txt_english);
            mTextViewPersian=itemView.findViewById(R.id.txt_persian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //TODO
                }
            });

        }
        public void bindWord(Word word){
            mWord=word;
            mTextViewEnglish.setText(word.getEnglish());
            mTextViewPersian.setText(word.getPersian());
        }
    }
    private class WordAdapter extends RecyclerView.Adapter<WordHolder>{

        public List<Word> getWords() {
            return mWords;
        }

        public void setWords(List<Word> words) {
            mWords = words;
        }

        private List<Word> mWords;

        public WordAdapter(List<Word> words) {
            mWords = words;
        }

        @NonNull
        @Override
        public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =getLayoutInflater().from(parent.getContext()).inflate(R.layout.row_of_word,
                    parent,false);
          return new WordHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull WordHolder holder, int position) {

            holder.bindWord(mWords.get(position));

        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}