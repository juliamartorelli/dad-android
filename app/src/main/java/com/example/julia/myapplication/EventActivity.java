package com.example.julia.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends Activity {

    @BindView(R.id.image_view_model)
    ImageView imageView;

    @BindView(R.id.text_view_event_name)
    TextView textViewEventName;

    @BindView(R.id.text_view_event_desc)
    TextView textViewEventDesc;

    @BindView(R.id.text_view_event_address)
    TextView textViewEventAddress;

    @BindView(R.id.text_view_event_date_time)
    TextView textViewEventDateTime;

    @BindView(R.id.text_view_ticket_price)
    TextView textViewTicketPrice;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.button)
    Button button;

    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();
        int eventId = bundle.getInt("eventId");

        Client.getInstance().event(eventId, new SuccessListener<Event>() {
            @Override
            public void onSuccess(Event response) {

                final ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.loadImage(response.getUrlImage(), new SimpleImageLoadingListener() {
                    public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {

                        imageView.setImageBitmap(loadedImage);
                        progressBar.setVisibility(View.GONE);
                    }
                });

                textViewEventName.setText(response.getName());
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {

            }
        });

    }
}
