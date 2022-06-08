package com.bignerdranch.android.listingsaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ListingFragment extends Fragment {

    private static final String ARG_LISTING_ID = "listing_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Listing sListing;
    private EditText listingTitleField;
    private EditText listingCompanyField;
    private EditText listingLocationField;
    private EditText listingSalaryField;
    private EditText listingLinkField;
    private Button listingDateButton;
    private Button listingTimeButton;
    private CheckBox fullTimeCheckbox;

    public static ListingFragment newInstance(UUID listingID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LISTING_ID, listingID);

        ListingFragment fragment = new ListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID listingID = (UUID) getArguments().getSerializable(ARG_LISTING_ID);
        sListing = ListingLab.get(getActivity()).getListing(listingID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listing, container, false);

        listingTitleField = v.findViewById(R.id.listing_title);
        listingTitleField.setText(sListing.getListTitle());
        listingTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Blanko
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // blamo
            }
        });

        listingCompanyField = v.findViewById(R.id.listing_company);
        listingCompanyField.setText(sListing.getListCompany());
        listingCompanyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Blanko
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListCompany(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // blamo
            }
        });

        listingLocationField = v.findViewById(R.id.listing_location);
//        REMEMBER TO SET THE FIELDS, OTHERWISE OTHER FIELDS WILL BE BLANK
//        listingCompanyField.setText(sListing.getListCompany());

        listingLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Blanko
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // blamo
            }
        });

        listingLinkField = v.findViewById(R.id.listing_link);
        listingLinkField.setText(sListing.getListTitle());
        listingLinkField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Blanko
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // blamo
            }
        });

        listingSalaryField = v.findViewById(R.id.listing_salary);
        listingSalaryField.setText(sListing.getListTitle());
        listingSalaryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Blanko
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListSalary(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // blamo
            }
        });

        listingDateButton = (Button) v.findViewById(R.id.listing_date);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//        String dateOnly = dateFormat.format(sListing.getListDate());
//        String formatedlistDate = dateOnly;
//        listingDateButton.setText(formatedlistDate);
        updateDate();


        // listingDateButton.setEnabled(false);
        // DatePicker code starts
        listingDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment()
                        .newInstance(sListing.getListDate());
                dialog.setTargetFragment(ListingFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });



        listingTimeButton = (Button) v.findViewById(R.id.listing_time);
        updateTime();

        listingTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
//                TimePickerFragment dialog = new TimePickerFragment();
//                dialog.show(manager, DIALOG_TIME);
                TimePickerFragment dialog = new TimePickerFragment()
                        .newInstance(sListing.getListTime());
                dialog.setTargetFragment(ListingFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        // Add TimePicker code here


        fullTimeCheckbox = (CheckBox) v.findViewById(R.id.full_time);
        fullTimeCheckbox.setChecked(sListing.isFullTime());
        fullTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sListing.setFullTime(isChecked);

            }
        });


        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            sListing.setListDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            sListing.setListTime(date);
            updateTime();
        }

    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateOnly = dateFormat.format(sListing.getListDate());
        String formatedlistDate = dateOnly;
        listingDateButton.setText(dateOnly);
    }

    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String timeOnly = dateFormat.format(sListing.getListTime());
        String formatedlistDate = timeOnly;
        listingTimeButton.setText(timeOnly);
    }

}
