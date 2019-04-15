package com.example.android.booklisting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context,0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        //Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        //Find the TextView with the ID author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(currentBook.getAuthor());

        //Find the TextView with the ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentBook.getTitle());

        //Return the list item view
        return listItemView;
    }


}
