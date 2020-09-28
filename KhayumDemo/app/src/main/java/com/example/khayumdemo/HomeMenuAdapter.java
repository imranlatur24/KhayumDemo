package com.example.khayumdemo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;


public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.MyViewHolder> {

    private MainActivity context;

    private String TITLE []= {"Sales",
                            "Offers",
                            "Report",
                            "Contact us",
                            "Profile",
                            "Change Password"};
    private int IMAGES []={
                    R.drawable.ic_offer_icon,
                    R.drawable.ic_local_offer,
                    R.drawable.ic_report_icon,
                    R.drawable.ic_support,
                    R.drawable.ic_person,
                    R.drawable.ic_password_icon};

    private int COlOR []={
            R.color.ic_profile_icon,
            R.color.ic_offer_icon,
            R.color.ic_local_offer,
            R.color.ic_report_icon,
            R.color.ic_support,
            R.color.ic_settings_icon};

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
                            Toasty.success(context,"case 1",Toasty.LENGTH_SHORT).show();
                        break;
                        case 1:
                            Toasty.success(context,"case 2",Toasty.LENGTH_SHORT).show();
                        break;
                        case 2:
                            Toasty.success(context,"case 3",Toasty.LENGTH_SHORT).show();
                        break;
                        case 3:
                            Toasty.success(context,"case 4",Toasty.LENGTH_SHORT).show();
                        break;
                        case 4:
                            context.startActivity(new Intent(context,ProfileActivity.class));
                        break;
                        case 5:
                            context.startActivity(new Intent(context,ChangePasswordActivity.class));
                        break;
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
