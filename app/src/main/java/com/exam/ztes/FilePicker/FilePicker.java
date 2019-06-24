package com.exam.ztes.FilePicker;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.ztes.R;

import java.io.File;
import java.util.ArrayList;

public class FilePicker extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_PICK_FILE = 1;

    private TextView mFilePathTextView;
    private Button mStartActivityButton;
    private File selectedFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        // Set the views
        mFilePathTextView = (TextView)findViewById(R.id.file_path_text_view);
        mStartActivityButton = (Button)findViewById(R.id.start_file_picker_button);
        mStartActivityButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.start_file_picker_button:
                // Create a new Intent for the file picker activity
                Intent intent = new Intent(this, FilePickerActivity.class);

                // Set the initial directory to be the sdcard
//                intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory());

                // Show hidden files
                //intent.putExtra(FilePickerActivity.EXTRA_SHOW_HIDDEN_FILES, true);

                // Only make .png files visible
//                ArrayList<String> extensions = new ArrayList<String>();
//                extensions.add(".pdf");
//                intent.putExtra(FilePickerActivity.EXTRA_ACCEPTED_FILE_EXTENSIONS, extensions);

                // Start the activity
//                startActivityForResult(intent, REQUEST_PICK_FILE);
                showFileChooser();
                break;

           /* case R.id.You_can_handle_more_onclick_events_from_here:
                //Do something
                break;*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case REQUEST_PICK_FILE:
                    mFilePathTextView.setText(FilePath.getPath(this,data.getData()));
                    /*
                    if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                        // Get the file path
                        selectedFile = new File(data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));
                        // Set the file path text view
                        mFilePathTextView.setText(selectedFile.getPath());
                        //Now you have your selected file, You can do your additional requirement with file.
                    }
                    */
            }
        }
    }
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), REQUEST_PICK_FILE);
    }
}
