package com.example.dictionary.controller.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.Word;
import com.example.dictionary.repository.WordDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WordListFragment extends Fragment {
    public static final String TAG_ADD_DIALOG_FRAGMENT = "tagAddDialogFragment";
    public static final int REQUEST_CODE_ADD = 0;
    public static final int REQUEST_CODE_EDITE = 1;
    public static final String TAG_EDIT_DIALOG_FRAGMENT = "tagEditDialogFragment";
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private WordDBRepository mWordDBRepository;
    private WordAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private boolean flag;
    private MenuItem mItemWordCounter;
    private Menu mMenu;

    private int mCounter=0;

    public static WordListFragment newInstance() {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWordDBRepository = WordDBRepository.getInstance(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        mMenu=menu;
        mItemWordCounter=menu.findItem(R.id.word_counter);
        mItemWordCounter.setTitle("Words : "+mCounter);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Word> words = mWordDBRepository.searchWords(query);
                mRelativeLayout.setVisibility(words.size() == 0 ? View.VISIBLE : View.INVISIBLE);
                if (mAdapter == null) {
                    mAdapter = new WordAdapter(words);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setWords(words);
                    mAdapter.notifyDataSetChanged();
                }
                flag = true;
                mFloatingActionButton.setImageResource(R.drawable.ic_baseline_arrow_back_24);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
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
                if (flag == true) {
                    mFloatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                    updateUI();

                } else {
                    AddDialogFragment addDialogFragment = AddDialogFragment.newInstance();
                    addDialogFragment.setTargetFragment(WordListFragment.this,
                            REQUEST_CODE_ADD);
                    addDialogFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_ADD_DIALOG_FRAGMENT);
                }

            }
        });
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        List<Word> words = mWordDBRepository.getWords();
        mCounter=words.size();
        mRelativeLayout.setVisibility(words.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        if (mAdapter == null) {
            mAdapter = new WordAdapter(words);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWords(words);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDITE) {
            updateUI();
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.word_recyclerView);
        mRelativeLayout = view.findViewById(R.id.rlt_task_empty_list);
        mFloatingActionButton = view.findViewById(R.id.fab);
    }

    private class WordHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewEnglish, mTextViewPersian;
        private Word mWord;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewEnglish = itemView.findViewById(R.id.txt_english);
            mTextViewPersian = itemView.findViewById(R.id.txt_persian);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDialogFragment editFragment = EditDialogFragment.newInstance(mWord);
                    editFragment.setTargetFragment(WordListFragment.this, REQUEST_CODE_EDITE);
                    editFragment.show(getActivity().getSupportFragmentManager(), TAG_EDIT_DIALOG_FRAGMENT);
                }
            });

        }

        public void bindWord(Word word) {
            mWord = word;
            mTextViewEnglish.setText(word.getEnglish());
            mTextViewPersian.setText(word.getPersian());
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<WordHolder> {

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
            View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.row_of_word,
                    parent, false);
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