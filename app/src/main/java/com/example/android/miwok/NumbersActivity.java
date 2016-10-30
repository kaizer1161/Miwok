/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //request and setup the {@link Audio Manager} to request audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //arrayList of number in words
        final ArrayList<Word> wordList = new ArrayList<>();


        wordList.add(new Word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        wordList.add(new Word("Two", "Otiiko", R.drawable.number_two, R.raw.number_two));
        wordList.add(new Word("Three", "Tolookosu", R.drawable.number_three, R.raw.number_three));
        wordList.add(new Word("Four", "Oyyisa", R.drawable.number_four, R.raw.number_four));
        wordList.add(new Word("Five", "Massokka", R.drawable.number_five, R.raw.number_five));
        wordList.add(new Word("Six", "Temmokka", R.drawable.number_six, R.raw.number_six));
        wordList.add(new Word("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        wordList.add(new Word("Eight", "Kawinta", R.drawable.number_eight, R.raw.number_eight));
        wordList.add(new Word("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        wordList.add(new Word("Ten", "Na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter itemsAdapter = new WordAdapter(this, wordList, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = wordList.get(position);

                releaseMediaPlayer();

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus.

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getSoundResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            releaseMediaPlayer();

                        }
                    });

                }

            }
        });

    }

    private void releaseMediaPlayer(){

        if(mediaPlayer != null){

            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mediaPlayer != null){

            mediaPlayer.release();
            mediaPlayer = null;

        }

    }

}

