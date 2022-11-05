package com.omkarph.wa_anyone.ui.about;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.omkarph.wa_anyone.API;
import com.omkarph.wa_anyone.R;

public class AboutFragment extends Fragment {

//    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        aboutViewModel =
//                new ViewModelProvider(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);

        ((TextView) root.findViewById(R.id.aboutTextview)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) root.findViewById(R.id.aboutTextview)).setText(Html.fromHtml(getResources().getString(R.string.my_linkedin_encoded)));

        root.findViewById(R.id.dmMeImage).setOnClickListener((View v) -> {
            API.goToWhatsapp(
                    "+917045270840",
                    API.WHATSAPP,
                    getContext(),
                    "Hello%2C%20I%20got%20here%20via%20your%20app%2C%20Whatsapp%20anyone%0A%0A"
            );
        });

        return root;
    }
}