package com.omkarph.wa_anyone.ui.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;
import com.omkarph.wa_anyone.API;
import com.omkarph.wa_anyone.R;
import com.omkarph.wa_anyone.db.Entry;
import com.omkarph.wa_anyone.db.HistoryHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private final String SETTINGS_PREF = "com.omkarph.wa_anyone_preferences";
    private final String SAVE_HISTORY = "SAVE_HISTORY";
    private final String DEFAULT_WHATSAPP = "DEFAULT_APP";
    private final String DEFAULT_COUNTRY = "DEFAULT_COUNTRY";
    private Context context;
    private SharedPreferences settingsPreference;
    private SharedPreferences.Editor settingsEditor;
    private CheckBox saveHistory;
    private EditText nickName;
    private RadioButton whatsapp, whatsappB;
    private HistoryHandler historyDB;
    CountryCodePicker ccp;
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();

        saveHistory = view.findViewById(R.id.saveHistory);
        nickName = view.findViewById(R.id.name);
        whatsapp = view.findViewById(R.id.whatsapp);
        whatsappB = view.findViewById(R.id.whatsappB);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);

        settingsPreference = PreferenceManager.getDefaultSharedPreferences(context);
        settingsEditor = settingsPreference.edit();

        if(!settingsPreference.contains(SAVE_HISTORY))
            settingsEditor.putBoolean(SAVE_HISTORY, true).apply();
        if(!settingsPreference.contains(DEFAULT_WHATSAPP))
            settingsEditor.putString(DEFAULT_WHATSAPP, "Whatsapp");

        saveHistory.setOnCheckedChangeListener((CompoundButton btn, boolean isChecked) -> {
            nickName.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            settingsEditor.putBoolean(SAVE_HISTORY, isChecked).apply();
        });
        whatsapp.setOnCheckedChangeListener(
                (CompoundButton btn, boolean isChecked) ->
                        settingsEditor.putString(DEFAULT_WHATSAPP, isChecked ? "Whatsapp" : "Whatsapp Business").apply()
        );
//        whatsappB.setOnCheckedChangeListener(
//                (CompoundButton btn, boolean isChecked) ->
//                        settingsEditor.putString(DEFAULT_WHATSAPP, !isChecked ? "Whatsapp" : "Whatsapp Business").apply()
//        );

        Button submit = view.findViewById(R.id.openChat);
        submit.setOnClickListener(this::openChat);

        // Setting Database handler
        historyDB = new HistoryHandler(context);
        List<Entry> history = historyDB.getEntries();

        loadPreferences();
    }

    private void loadPreferences(){
        saveHistory.setChecked(settingsPreference.getBoolean(SAVE_HISTORY, true));
        nickName.setVisibility(settingsPreference.getBoolean(SAVE_HISTORY, true) ? View.VISIBLE : View.GONE);

        whatsapp.setChecked(settingsPreference.getString(DEFAULT_WHATSAPP, "Whatsapp").equals("Whatsapp"));
        whatsappB.setChecked(!settingsPreference.getString(DEFAULT_WHATSAPP, "Whatsapp").equals("Whatsapp"));
    }


    private void openChat(View view){
        CountryCodePicker ccp = getView().findViewById(R.id.ccp);
        String countryCode = ccp.getSelectedCountryCodeWithPlus();
        RadioButton whatsapp = getView().findViewById(R.id.whatsapp);

        String phoneNumber = ((EditText)getView().findViewById(R.id.phoneno)).getText().toString();

        if(countryCode.equals("+91") && phoneNumber.length() < 10){
            Toast.makeText(context, "Please enter a valid 10 digit Indian phone number", Toast.LENGTH_LONG).show();
            return;
        }
        String fullPhone = countryCode + phoneNumber;
        API.goToWhatsapp(
                fullPhone,
                whatsapp.isChecked() ? API.WHATSAPP : API.WHATSAPP_BUSINESS,
                context);

        historyDB.addEntry(new Entry(fullPhone));
    }
}