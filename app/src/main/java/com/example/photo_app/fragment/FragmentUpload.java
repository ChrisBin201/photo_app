package com.example.photo_app.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.R;

import java.util.ArrayList;

public class FragmentUpload extends Fragment {

    private ArrayList<String> selectedImages = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> selectedImages = new ArrayList<>();
        Button btnSelect = view.findViewById(R.id.btnSelectPicture);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        EditText edtCaption = view.findViewById(R.id.edtCaption);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery to select multiple image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 1);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload images to server

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {

                selectedImages.clear();
                // get list of selected images
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        selectedImages.add(data.getClipData().getItemAt(i).getUri().toString());
                    }
                } else if (data.getData() != null) {
                    selectedImages.add(data.getData().toString());
                }
//                ListView imgList = getView().findViewById(R.id.imgList);
//                ImagePagerAdapter adapter = new ImagePagerAdapter(getContext(), selectedImages);
//                imgList.setAdapter(adapter);
            }
        }
    }
}
