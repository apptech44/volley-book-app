package com.example.volleybookapp.app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.volleybookapp.R;
import com.example.volleybookapp.configs.Constant;
import com.example.volleybookapp.configs.MySingleton;
import com.example.volleybookapp.model.Books;
import com.example.volleybookapp.ui.BookAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Books> booksList;
    private BookAdapter bookAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksList = new ArrayList<>();
        listView = findViewById(R.id.book_list_view);
        listView.setOnItemClickListener(this);
        loadBooks();
    }

    private void loadBooks() {

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_GET_BOOKS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.INVISIBLE);

                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                Books books = new Books();
                                JSONObject items = array.getJSONObject(i);

                                books.setName(items.getString(Constant.KEY_BOOK_NAME));
                                books.setAuthor(items.getString(Constant.KEY_AUTHOR_NAME));
                                books.setSubject(items.getString(Constant.KEY_SUBJECT_NAME));
                                books.setDescription(items.getString(Constant.KEY_BOOK_DESCRIPTION));
                                books.setImages(items.getString(Constant.KEY_IMAGE_URL));
                                booksList.add(books);
                            }

                            bookAdapter = new BookAdapter(MainActivity.this, booksList);
                            listView.setAdapter(bookAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.setting_menu:
                Toast.makeText(MainActivity.this, "Setting Menu ", Toast.LENGTH_LONG).show();
                break;

            case R.id.add_menu:
                startActivity(new Intent(MainActivity.this, AddBooks.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Books books = booksList.get(position);

        Dialog dialog = new Dialog(this, R.style.appDialog);
        dialog.setContentView(R.layout.detail_book_layout);
        dialog.setCanceledOnTouchOutside(true);

        ImageView img_logo = dialog.findViewById(R.id.book_images);
        TextView name_tv = dialog.findViewById(R.id.detail_book_name);
        TextView author_tv = dialog.findViewById(R.id.detail_author_name);
        TextView subject_tv = dialog.findViewById(R.id.detail_subject_name);
        TextView description_tv = dialog.findViewById(R.id.detail_book_description);

        name_tv.setText(books.getName());

        Picasso.get().load(books.getImages())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(img_logo);

        author_tv.setText(books.getAuthor());
        description_tv.setText(books.getDescription());
        description_tv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        subject_tv.setText(books.getSubject());
        dialog.show();

    }
}
