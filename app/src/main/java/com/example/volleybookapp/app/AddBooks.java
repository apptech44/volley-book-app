package com.example.volleybookapp.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.volleybookapp.R;
import com.example.volleybookapp.configs.Constant;
import com.example.volleybookapp.configs.MySingleton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class AddBooks extends AppCompatActivity {

    private TextInputEditText book_name, author_name, book_description, book_image;
    private Spinner subject_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        book_name = findViewById(R.id.book_name_edit_text);
        author_name = findViewById(R.id.author_name_edit_text);
        subject_name = findViewById(R.id.subject_spinner);
        book_description = findViewById(R.id.book_description_edit_text);
        book_image = findViewById(R.id.image_url_edit_text);

    }

    private void insertBook() {

        final String name = book_name.getText().toString().trim();
        final String author = author_name.getText().toString().trim();
        final String subject = subject_name.getSelectedItem().toString();
        final String description = book_description.getText().toString().trim();
        final String images = book_image.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(AddBooks.this, "please enter book name here ...", Toast.LENGTH_LONG).show();
            return;
        }

        if (author.isEmpty()) {
            Toast.makeText(AddBooks.this, "please enter author name here ...", Toast.LENGTH_LONG).show();
            return;
        }

        if (subject.isEmpty()) {
            Toast.makeText(AddBooks.this, "please enter subject name here ...", Toast.LENGTH_LONG).show();
            return;
        }

        if (description.isEmpty()) {
            Toast.makeText(AddBooks.this, "please enter book description here ...", Toast.LENGTH_LONG).show();
            return;
        }

        if (images.isEmpty()) {
            Toast.makeText(AddBooks.this, "please enter book images here ...", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_ADD_BOOKS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddBooks.this,
                                "Successfully Insert Record",
                                Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddBooks.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(Constant.KEY_BOOK_NAME, name);
                params.put(Constant.KEY_AUTHOR_NAME, author);
                params.put(Constant.KEY_SUBJECT_NAME, subject);
                params.put(Constant.KEY_BOOK_DESCRIPTION, description);
                params.put(Constant.KEY_IMAGE_URL, images);

                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

        book_name.setText("");
        author_name.setText("");
        book_description.setText("");
        book_image.setText("");
    }

    public void addBooks(View view) {
        insertBook();
    }
}
