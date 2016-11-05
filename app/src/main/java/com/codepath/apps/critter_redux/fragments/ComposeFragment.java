package com.codepath.apps.critter_redux.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.listeners.PostTwitterListener;


public class ComposeFragment extends DialogFragment {

    private PostTwitterListener postTwitterlistener;

    private static View vComposeFragment;

    private TextInputLayout tiCompose;
    private EditText etComposeTweet;
    private Button btnPost;

    private static final int MAX_CHARACTERS_PER_TWEET = 140;





    public ComposeFragment() {
        // Empty constructor required for DialogFragment
    }


    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set to adjust screen height automatically, when soft keyboard appears on screen
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        vComposeFragment = inflater.inflate(R.layout.fragment_compose_tweet, container);
        return vComposeFragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewsAndBehavior(view);

        setLengthChecker();

        // Show soft keyboard automatically and request focus to field
        etComposeTweet.requestFocus();
        try {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }



    public void setCustomObjectListener(PostTwitterListener listener) {
        postTwitterlistener = listener;
    }


    private void setViewsAndBehavior(View v) {
        tiCompose = (TextInputLayout) vComposeFragment.findViewById(R.id.compose_tweet);
        tiCompose.setCounterMaxLength(MAX_CHARACTERS_PER_TWEET);

        etComposeTweet = tiCompose.getEditText();
        try {
            etComposeTweet.setEms(MAX_CHARACTERS_PER_TWEET);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Button btnCancel = (Button) v.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPost = (Button) v.findViewById(R.id.post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tweetText = etComposeTweet.getText().toString();

                if (tweetText.isEmpty()) {
                    Toast.makeText(getContext(), "Come on, tweet something!", Toast.LENGTH_SHORT).show();
                } else {
                    postTwitterlistener.onPostTwitter(tweetText);
                }
            }
        });
    }



    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = null;
        try {
            params = getDialog().getWindow().getAttributes();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        // Call super onResume after sizing
        super.onResume();
    }




    private void setLengthChecker() {
        etComposeTweet.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > MAX_CHARACTERS_PER_TWEET) {
                    btnPost.setEnabled(false);
                    tiCompose.setError("Tweet must not exceed " + MAX_CHARACTERS_PER_TWEET + " chars");
                    tiCompose.setErrorEnabled(true);
                } else {
                    btnPost.setEnabled(true);
                    tiCompose.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

}
