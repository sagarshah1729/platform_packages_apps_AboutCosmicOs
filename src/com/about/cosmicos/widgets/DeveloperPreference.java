package com.about.cosmicos.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.about.cosmicos.aboutcosmicos.R;
import com.about.cosmicos.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeveloperPreference extends LinearLayout {
    private static final String TAG = "DeveloperPreference";
    public static final String GRAVATAR_API = "http://www.gravatar.com/avatar/";
    public static int mDefaultAvatarSize = 400;
    private ImageView gplusButton;
    private ImageView donateButton;
    private ImageView githubButton;
    private ImageView photoView;

    private TextView devName;

    private String nameDev;
    private String gplusLink;
    private String donateLink;
    private String githubLink;
    private String devEmail;

    public DeveloperPreference(Context context) {
        this(context, null);
    }

    public DeveloperPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    public DeveloperPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.DeveloperPreference);
            nameDev = typedArray.getString(R.styleable.DeveloperPreference_nameDev);
            gplusLink = typedArray.getString(R.styleable.DeveloperPreference_gplusLink);
            donateLink = typedArray.getString(R.styleable.DeveloperPreference_donateLink);
            githubLink = typedArray.getString(R.styleable.DeveloperPreference_githubLink);
            devEmail = typedArray.getString(R.styleable.DeveloperPreference_emailDev);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        /**
         * Inflate views
         */

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dev_card, this, true);

        donateButton = (ImageView) layout.findViewById(R.id.donate_button);
        githubButton = (ImageView) layout.findViewById(R.id.github_button);
        gplusButton = (ImageView) layout.findViewById(R.id.gplus_button);
        devName = (TextView) layout.findViewById(R.id.name);
        photoView = (ImageView) layout.findViewById(R.id.photo);

        /**
         * Initialize buttons
         */

        if (nameDev != null) {
            devName.setText(nameDev);
            if (devEmail != null) {
                UrlImageViewHelper.setUrlDrawable(this.photoView,
                        getGravatarUrl(devEmail),
                        R.drawable.ic_null,
                        UrlImageViewHelper.CACHE_DURATION_ONE_WEEK);
            } else {
                    UrlImageViewHelper.setUrlDrawable(this.photoView,
                        null,
                        R.drawable.device_developer,
                        UrlImageViewHelper.CACHE_DURATION_ONE_WEEK);
            }
        } else {
            devName.setVisibility(View.GONE);
            photoView.setVisibility(View.GONE);
        }

        if (donateLink != null) {
            final OnClickListener openDonate = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri donateURL = Uri.parse(donateLink);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, donateURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            };
            donateButton.setOnClickListener(openDonate);
        } else {
            donateButton.setVisibility(View.GONE);
        }

        if (gplusLink != null) {
            final OnClickListener openGplus = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gplusURL = Uri.parse(gplusLink);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, gplusURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            };
            gplusButton.setOnClickListener(openGplus);
        } else {
            gplusButton.setVisibility(View.GONE);
        }

        if (githubLink != null) {
            final OnClickListener openGithub = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri githubURL = Uri.parse(githubLink);
                    final Intent intent = new Intent(Intent.ACTION_VIEW, githubURL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            };
            githubButton.setOnClickListener(openGithub);
        } else {
            githubButton.setVisibility(View.GONE);
        }
    }

    public String getGravatarUrl(String email) {
        try {
            String emailMd5 = getMd5(email.trim().toLowerCase());
            return String.format("%s%s?s=%d&d=mm",
                    GRAVATAR_API,
                    emailMd5,
                    mDefaultAvatarSize);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private String getMd5(String devEmail) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(devEmail.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }
}
