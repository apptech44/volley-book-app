package com.example.volleybookapp.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volleybookapp.R;
import com.example.volleybookapp.model.Books;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private List<Books> booksList;

    public BookAdapter(Context context, List<Books> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.books_item_layout, null);
        }

        TextView name = convertView.findViewById(R.id.book_name_text_view);
        TextView author = convertView.findViewById(R.id.author_name_text_view);
        TextView subject = convertView.findViewById(R.id.subject_text_view);
        ImageView logos = convertView.findViewById(R.id.book_image_view);

        Books books = booksList.get(position);

        name.setText(books.getName());
        author.setText(books.getAuthor());
        subject.setText(books.getSubject());

        Picasso.get()
                .load(books.getImages())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .resize(140, 180)
                .into(logos);

        return convertView;
    }
}
