package com.swack.workshop.adapters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.swack.workshop.R;
import com.swack.workshop.activities.MainActivity;
import com.swack.workshop.activities.ProfileActivity;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.MyViewHolder> {

    private MainActivity context;

    private String TITLE []= {
            "Breakdown \nService",
            "Transport \nService",
            "Vehicals",
            "Drivers",
            "New Request",
            "Order History",
            "Support",
            "Privacy Policy",
            "Logout"};
    private int IMAGES []={
            R.drawable.ic_breakdown,
            R.drawable.ic_repaire,
                    R.drawable.ic_vehical,
                    R.drawable.ic_myvehical,
                    R.drawable.ic_report_icon,
                    R.drawable.ic_history,
                    R.drawable.ic_support,
                    R.drawable.ic_privacy_policy,
                    R.drawable.ic_password_icon};

    private int COlOR []={
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite,
            R.color.colorWhite};

    private Dialog dialog1;

    public HomeMenuAdapter(MainActivity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        int w = holder.cvCategory.getResources().getDisplayMetrics().widthPixels;
        int h = holder.cvCategory.getResources().getDisplayMetrics().heightPixels;
        holder.images.getLayoutParams().width = w/8;
        holder.images.getLayoutParams().height = w/8;
        holder.ltyCategory.getLayoutParams().height = h/5;

        holder.title.setText(TITLE[position]);
        holder.title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.title.setSelected(true);
        holder.images.setImageResource(IMAGES[position]);
        holder.ltyCategory.setBackgroundColor(context.getResources().getColor(COlOR[position]));
        holder.ltyCategory.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                switch (position)
                {
                        case 0:
                          //  context.startActivity(new Intent(context, SalesListingActivity.class));
                        break;
                        case 1:
                           // context.startActivity(new Intent(context, OfferListingActivity.class));
                        break;
                        case 2:
                           // context.startActivity(new Intent(context, ReportsActivity.class));
                        break;
                        case 3:
                           /* dialog1 = new Dialog(context);
                            dialog1.setContentView(R.layout.suport_dailog_box);
                            dialog1.setCancelable(true);
                            int width = ViewGroup.LayoutParams.MATCH_PARENT;
                            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            dialog1.getWindow().setLayout(width, height);
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                            ImageView cancel = dialog1.findViewById(R.id.image_cancel);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                            dialog1.show();*/
                        break;
                        case 4:
                            context.startActivity(new Intent(context, ProfileActivity.class));
                        break;
                        case 5:
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle("Static Demo..!!!");
                            alertDialogBuilder.setIcon(R.mipmap.ic_launcher_round);
                            alertDialogBuilder.setMessage("Are you sure to exit?");
                            alertDialogBuilder.setCancelable(false);

                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //MainActivity.super.onBackPressed();
                                    dialog1.cancel();
                                }
                            });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView images;
        public LinearLayout ltyCategory;
        public CardView cvCategory;
        public MyViewHolder(View view) {
            super(view);
            images = (ImageView) view.findViewById(R.id.imageView_image);
            title = (TextView) view.findViewById(R.id.textView_title);
            ltyCategory = (LinearLayout) view.findViewById(R.id.ltyCategory);
            cvCategory = (CardView) view.findViewById(R.id.cvCategory);

        }
    }
}
