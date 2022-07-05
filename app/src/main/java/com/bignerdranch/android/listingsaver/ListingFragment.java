// Name: Eu Yu Han
// Student ID: 18123547
// Java class to contain the detail view fragment

package com.bignerdranch.android.listingsaver;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListingFragment extends Fragment {

    private static final String ARG_LISTING_ID = "listing_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_PHOTO = 3;


    private Listing sListing;
    private File sPhotoFile;
    private EditText listingTitleField;
    private EditText listingCompanyField;
    private EditText listingLocationField;
    private EditText listingSalaryField;
    private EditText listingLinkField;
    private Button listingDateButton;
    private Button listingTimeButton;
    private CheckBox fullTimeCheckbox;
    private Button listRecruiterButton;
    private ImageButton listPhotoButton;
    private ImageView listPhotoView;

    private Button listDeleteButton;

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
        sPhotoFile = ListingLab.get(getActivity()).getPhotoFile(sListing);
    }

    @Override
    public void onPause() {
        super.onPause();

        ListingLab.get(getActivity())
                .updateListing(sListing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listing, container, false);

        listingTitleField = v.findViewById(R.id.listing_title);
        listingTitleField.setText(sListing.getListTitle());
        listingTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listingCompanyField = v.findViewById(R.id.listing_company);
        listingCompanyField.setText(sListing.getListCompany());
        listingCompanyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListCompany(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listingLocationField = v.findViewById(R.id.listing_location);
        listingLocationField.setText(sListing.getListLocation());
        listingLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listingLinkField = v.findViewById(R.id.listing_link);
        listingLinkField.setText(sListing.getListLink());
        listingLinkField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listingSalaryField = v.findViewById(R.id.listing_salary);
        listingSalaryField.setText(sListing.getListSalary());
        listingSalaryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sListing.setListSalary(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listingDateButton = (Button) v.findViewById(R.id.listing_date);
        updateDate();

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
                TimePickerFragment dialog = new TimePickerFragment()
                        .newInstance(sListing.getListTime());
                dialog.setTargetFragment(ListingFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        fullTimeCheckbox = (CheckBox) v.findViewById(R.id.full_time);
        fullTimeCheckbox.setChecked(sListing.isFullTime());
        fullTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sListing.setFullTime(isChecked);

            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        listRecruiterButton = (Button) v.findViewById(R.id.choose_recruiter);
        listRecruiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if (sListing.getListRecruiter() != null) {
            listRecruiterButton.setText(sListing.getListRecruiter());
        }

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,
                PackageManager.MATCH_DEFAULT_ONLY) == null) {
            listRecruiterButton.setEnabled(false);
        }

        listPhotoButton = (ImageButton) v.findViewById(R.id.listing_camera);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = sPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        listPhotoButton.setEnabled(canTakePhoto);

        listPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.listingsaver.fileprovider",
                        sPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });


        listPhotoView = (ImageView) v.findViewById(R.id.listing_photo);
        updatePhotoView();

        listDeleteButton = (Button) v.findViewById(R.id.delete_button);
        listDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] id = new String[]{String.valueOf(sListing.getListID())};

                ListingLab.get(getActivity()).deleteListing(id);
                Intent intent = new Intent(getActivity(), ListingListActivity.class);
                startActivity(intent);
            }
        });

        return v;

    }

    // Function to handle results received from other Activities
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            sListing.setListDate(date);
            updateDate();
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();

            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            try {
                if (c.getCount() == 0) {
                    return;
                }

                c.moveToFirst();
                String recruiter = c.getString(0);
                sListing.setListRecruiter(recruiter);
                listRecruiterButton.setText(recruiter);
            } finally {
                c.close();
            }
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.listingsaver.fileprovider",
                    sPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            sListing.setListTime(date);
            updateTime();
        }

    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateOnly = dateFormat.format(sListing.getListDate());
        listingDateButton.setText(dateOnly);
    }

    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String timeOnly = dateFormat.format(sListing.getListTime());
        listingTimeButton.setText(timeOnly);
    }

    private void updatePhotoView() {
        if (sPhotoFile == null || !sPhotoFile.exists()) {
            listPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    sPhotoFile.getPath(), getActivity());
            listPhotoView.setImageBitmap(bitmap);
        }
    }

}
