package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.fragments.cleaner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.activity.CleanerActivity;
import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.AsyncTaskCoroutine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import videodownload.com.newmusically.R;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.*;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.videoPath;
import static com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility.Utility.videoPathSent;

public class WacleanerFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WacleanerFragment";
    List<File> mFileListDatabases;

    public long videoCounter = 0;
    public long audioCounter = 0;
    public long voiceCounter = 0;
    public long profileCounter = 0;
    public long imageCounter = 0;
    public long wallpaperCounter = 0;
    public long databaseCounter = 0;
    public long stickerCounter = 0;
    public long docCounter = 0;


    TextView mTextViewAudioSize;
    TextView mTextViewVideoSize;
    TextView mTextViewImageSize;
    TextView mTextViewProfileSize;
    TextView mTextViewVoiceSize;
    TextView mTextViewDatabaseSize;
    TextView mTextViewdoc_size;
    TextView mTextViewstick_size;

    RelativeLayout mRelativeLayoutAudio;
    RelativeLayout mRelativeLayoutVideo;
    RelativeLayout mRelativeLayoutImages;
    RelativeLayout mRelativeLayoutProfile;
    RelativeLayout mRelativeLayoutVoice;
    RelativeLayout mRelativeLayoutDatabase;
    RelativeLayout mRelativeLayoutlDocuments;
    RelativeLayout mRelativeLayoutStickers;
    Drawable mDrawable;
    static boolean firstTime = false;
    private boolean setup_successed = false;
    public String what = "";
    AlertDialog alertDialog;
    public android.app.AlertDialog mAlertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public void setValue(String i) {
        what = i;
    }

    public WacleanerFragment() {
        // Required empty public constructor
    }


    public WacleanerFragment newInstance(String param1, String param2) {
        WacleanerFragment fragment = new WacleanerFragment();
        fragment.setValue(param1);
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wacleaner, container, false);
        Log.e(TAG, "onCreateView: " + what);
        bindme(view);
        initmain();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initmain() {
        new AsyncTaskCoroutine<Object, Object>() {
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                mFileListDatabases = new ArrayList<File>();
                videoCounter = 0;
                audioCounter = 0;
                voiceCounter = 0;
                profileCounter = 0;
                imageCounter = 0;
                wallpaperCounter = 0;
                databaseCounter = 0;
                stickerCounter = 0;
                docCounter = 0;
                mTextViewAudioSize.setText("Files: Calculating...");
                mTextViewImageSize.setText("Files: Calculating...");
                mTextViewProfileSize.setText("Files: Calculating...");
                mTextViewVideoSize.setText("Files: Calculating...");
                mTextViewVoiceSize.setText("Files: Calculating...");
                mTextViewDatabaseSize.setText("Files: Calculating...");
                mTextViewstick_size.setText("Files: Calculating...");
                mTextViewdoc_size.setText("Files: Calculating...");
            }

            @Override
            public Object doInBackground(Object... params) {
                getWhatsAppVidoes(what.equals("wa") ? videoPath : videoPath_wb, true);
                getWhatsAppVidoes(what.equals("wa") ? videoPathSent : videoPathSent_wb, false);

                getWhatsImages(what.equals("wa") ? imagepath : imagepath_wb, true);
                getWhatsImages(what.equals("wa") ? imagepathSent : imagepathSent_wb, false);

                getWhatsVoice(what.equals("wa") ? voicePath : voicePath_wb, true);
                getWhatsVoice(what.equals("wa") ? voicePathSent : voicePathSent_wb, false);

                getWhatsAudio(what.equals("wa") ? audioPath : audioPath_wb, true);
                getWhatsAudio(what.equals("wa") ? audioPathSent : audioPathSent_wb, false);

                getWhatsProfileImages(what.equals("wa") ? profilePath : profilePath_wb);

                getdatebasefile(what.equals("wa") ? databasePath : databasePath_wb);


                getWhatsDoc(what.equals("wa") ? documentspath : documentspath_wb);
                getWhatsDoc(what.equals("wa") ? documentspathSent : documentspathSent_wb);

                getWhatsSticker(what.equals("wa") ? stickerpath : stickerpath_wb);
                getWhatsSticker(what.equals("wa") ? stickerpathSent : stickerpathSent_wb);
                return null;
            }

            @Override
            public void onPostExecute(Object result) {
                super.onPostExecute(result);
                mTextViewAudioSize.setText("Files: " + audioCounter);
                mTextViewImageSize.setText("Files: " + imageCounter);
                mTextViewProfileSize.setText("Files: " + profileCounter);
                mTextViewVideoSize.setText("Files: " + videoCounter);
                mTextViewVoiceSize.setText("Files: " + voiceCounter);
                mTextViewDatabaseSize.setText("Files: " + databaseCounter);
                mTextViewstick_size.setText("Files: " + stickerCounter);
                mTextViewdoc_size.setText("Files: " + docCounter);
            }
        }.execute();


    }


    private void bindme(View v) {

        mTextViewAudioSize = (TextView) v.findViewById(R.id.textview_audio_size);
        mTextViewProfileSize = (TextView) v.findViewById(R.id.textview_profile_size);
        mTextViewImageSize = (TextView) v.findViewById(R.id.textview_image_size);
        mTextViewVoiceSize = (TextView) v.findViewById(R.id.textview_voice_size);
        mTextViewVideoSize = (TextView) v.findViewById(R.id.textview_video_size);
        mTextViewDatabaseSize = (TextView) v.findViewById(R.id.textview_database);
        mTextViewstick_size = (TextView) v.findViewById(R.id.textview_stick_size);
        mTextViewdoc_size = (TextView) v.findViewById(R.id.textview_doc_size);
        mTextViewDatabaseSize = (TextView) v.findViewById(R.id.textview_database);


        mRelativeLayoutAudio = (RelativeLayout) v.findViewById(R.id.layout_audio1);
        mRelativeLayoutProfile = (RelativeLayout) v.findViewById(R.id.layout_profile);
        mRelativeLayoutImages = (RelativeLayout) v.findViewById(R.id.layout_images1);
        mRelativeLayoutVoice = (RelativeLayout) v.findViewById(R.id.layout_voice);
        mRelativeLayoutVideo = (RelativeLayout) v.findViewById(R.id.layout_video);
        mRelativeLayoutDatabase = (RelativeLayout) v.findViewById(R.id.layout_database);
        mRelativeLayoutlDocuments = (RelativeLayout) v.findViewById(R.id.layout_documents);
        mRelativeLayoutStickers = (RelativeLayout) v.findViewById(R.id.layout_stickers);

        mRelativeLayoutDatabase.setOnClickListener(this);
        mRelativeLayoutAudio.setOnClickListener(this);
        mRelativeLayoutProfile.setOnClickListener(this);
        mRelativeLayoutImages.setOnClickListener(this);
        mRelativeLayoutVoice.setOnClickListener(this);
        mRelativeLayoutVideo.setOnClickListener(this);
        mRelativeLayoutStickers.setOnClickListener(this);
        mRelativeLayoutlDocuments.setOnClickListener(this);


    }

    public void SetDiolog(final Activity activity, String message) {
        mAlertDialog = new android.app.AlertDialog.Builder(activity).create();
        mAlertDialog.setMessage(message);

        mAlertDialog.setButton(BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {

            case R.id.layout_database:
                if (databaseCounter > 0) {
                    showdialog();
                } else {
                    SetDiolog(getActivity(), "No Database available");
                }
                break;

            case R.id.layout_images1:
                if (imageCounter <= 0) {
                    SetDiolog(getActivity(), "No Images available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? imagepath : imagepath_wb);
                mIntent.putExtra("title", "Whatsapp Images");
                mIntent.putExtra("isaudio", false);
                startActivity(mIntent);
                break;
            case R.id.layout_video:
                if (videoCounter <= 0) {
                    SetDiolog(getActivity(), "No Videos available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? videoPath : videoPath_wb);
                mIntent.putExtra("title", "Whatsapp Videos");
                mIntent.putExtra("isaudio", false);
                startActivity(mIntent);
                break;
            case R.id.layout_profile:
                if (profileCounter <= 0) {
                    SetDiolog(getActivity(), "No profile available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? profilePath : profilePath_wb);
                mIntent.putExtra("title", "Whatsapp Profiles");
                mIntent.putExtra("isaudio", false);
                startActivity(mIntent);
                break;
            case R.id.layout_audio1:
                if (audioCounter <= 0) {
                    SetDiolog(getActivity(), "No Audio available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? audioPath : audioPath_wb);
                mIntent.putExtra("title", "Whatsapp Audios");
                mIntent.putExtra("AUDIO", "audio");
                mIntent.putExtra("isaudio", true);
                startActivity(mIntent);
                break;
            case R.id.layout_voice:
                if (voiceCounter <= 0) {
                    SetDiolog(getActivity(), "No Voice available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? voicePath : voicePath_wb);
                mIntent.putExtra("title", "Whatsapp Voices");
                mIntent.putExtra("isaudio", true);
                startActivity(mIntent);
                break;

            case R.id.layout_documents:
                if (docCounter <= 0) {
                    SetDiolog(getActivity(), "No Document available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? documentspath : documentspath_wb);
                mIntent.putExtra("title", "Whatsapp Documents");
                mIntent.putExtra("isaudio", false);
                startActivity(mIntent);
                break;
            case R.id.layout_stickers:
                if (stickerCounter <= 0) {
                    SetDiolog(getActivity(), "No Sticker available");
                    return;
                }
                mIntent = new Intent(getActivity(), CleanerActivity.class);
                mIntent.putExtra("what", what.equals("wa") ? stickerpath : stickerpath_wb);
                mIntent.putExtra("title", "Whatsapp Stickers");
                mIntent.putExtra("isaudio", false);
                startActivity(mIntent);
                break;
            default:
                break;
        }
    }

    private void showdialog() {

        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure you want to clear all Databases?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alertDialog.dismiss();
                        for (int i = 0; i < mFileListDatabases.size(); i++) {
                            boolean del = mFileListDatabases.get(i).delete();
                        }
                        mFileListDatabases.clear();
                        initmain();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void getWhatsAppVidoes(String path, boolean first) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {

            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    videoCounter += filesFound.length;
                }

            }
        }


    }


    public void getWhatsImages(String path, boolean first) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {
            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    imageCounter += 1;
                }

            }


        }


    }


    public void getWhatsProfileImages(String path) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {
            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    profileCounter += 1;
                }

            }

        }

    }


    public void getWhatsVoice(String path, boolean first) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {

            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    voiceCounter += 1;
                }

            }
        }

    }

    public void getdatebasefile(String path) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {


            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    mFileListDatabases.add(file3);
                    databaseCounter += 1;
                }

            }
        }

    }

    public void getWhatsAudio(String path, boolean first) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {
            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    audioCounter += 1;
                }

            }

        }

    }

    public void getWhatsSticker(String path) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {
            for (File file3 : filesFound) {
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    stickerCounter += 1;
                }

            }


        }

    }

    public void getWhatsDoc(String path) {
        File files = new File(path);
        final File[] filesFound = files.listFiles();
        if (filesFound != null && filesFound.length > 0) {
            int aa = 0;
            for (File file3 : filesFound) {
                Log.e(TAG, "getWhatsDoc:----------> cnt :" + (++aa) + " file:" + file3.getAbsolutePath());
                if (!file3.isDirectory() && !file3.getAbsolutePath().contains(".nomedia")) {
                    docCounter += 1;
                }

            }
        }

    }


}