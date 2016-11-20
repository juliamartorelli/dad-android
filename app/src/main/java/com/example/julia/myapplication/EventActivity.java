package com.example.julia.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Model.Locality;
import com.example.julia.myapplication.Model.Ticket;
import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;
import com.example.julia.myapplication.Util.GeoUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.image_view_model)
    ImageView imageView;

    @BindView(R.id.text_view_event_name)
    TextView textViewEventName;

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
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt("eventId");

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
                textViewTicketPrice.setText("R$" + response.getPrice().replace('.',','));

                try {
                    SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat  humanFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm'hs'");
                    CharSequence dateTime = humanFormat.format(format.parse(response.getDate()));
                    textViewEventDateTime.setText(dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Client.getInstance().locality(response.getIdLocality(), new SuccessListener<Locality>() {
                    @Override
                    public void onSuccess(Locality response) {

                        double latitude = Double.valueOf(response.getLatitude());
                        double longitude = Double.valueOf(response.getLongitude());
                        textViewEventAddress.setText(GeoUtil.addressFromLatLng(EventActivity.this, latitude, longitude));

                        Bundle args = new Bundle();
                        args.putDouble("lat", latitude);
                        args.putDouble("lng", longitude);

                        Fragment fragment = Fragment.instantiate(EventActivity.this, MapsFragment.class.getName());
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        fragment.setArguments(args);
                        ft.replace(R.id.container, fragment);
                        ft.commit();

                    }}, new ErrorListener() { @Override public void onError(RestError restError) { } }
                );

            }}, new ErrorListener() { @Override public void onError(RestError restError) {}}
        );

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        User user = Preferences.getInstance().getCurrentUser();
        Ticket ticket = new Ticket(1, true, "name", Integer.valueOf(user.getId()), eventId);
        Client.getInstance().buyTicket(ticket, new SuccessListener<Ticket>() {
            @Override
            public void onSuccess(Ticket response) {
                showEventDialog("Succeso", "Ingresso comprado com sucesso! :)", true);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {
                showEventDialog("Oops...", "Houve um problema ao comprar o ingresso. Tente novamente em instantes", false);
            }
        });
    }

    private void showEventDialog(String title, String description, final boolean success) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (success) {
                            Intent intent = new Intent(EventActivity.this, EventListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }
 }
