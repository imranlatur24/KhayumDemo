package com.swack.workshop.adapters;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
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

import com.swack.workshop.R;
import com.swack.workshop.activities.FinalBillActivity;
import com.swack.workshop.activities.JobDetailActivity;
import com.swack.workshop.model.FinalListModel;
import com.swack.workshop.model.OrderListModel2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class FinalBillAdapter extends RecyclerView.Adapter<FinalBillAdapter.MyViewHolder> {

    private FinalBillActivity context;

    private ArrayList<OrderListModel2.Order> arrayLists;
    private ArrayList<OrderListModel2.Order> tempArrayLists;

    public FinalBillAdapter(FinalBillActivity context) {
        this.context = context;
    }

    public void setListArray(ArrayList<OrderListModel2.Order> arrayLists) {
        this.arrayLists = arrayLists;
        tempArrayLists = new ArrayList<>();
        tempArrayLists.addAll(arrayLists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finalbilladapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final OrderListModel2.Order mOrder = arrayLists.get(position);

        holder.txt_joborderid.setText(mOrder.getService_typ_name());
        holder.txt_joborderid.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.txt_joborderid.setSelected(true);
        holder.txt_joborderid.setSingleLine(true);
        holder.txt_jobid.setText("Locate on Map");
        holder.txt_jobid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(mOrder.getGar_lat()), Float.parseFloat(mOrder.getGar_long()));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    context.startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toasty.error(context,"Google Map App Not Found").show();
                }
            }
        });
        holder.txtJobDate.setText(context.parseDate(mOrder.getIs_create()));
        holder.txtJobUnique.setText(Html.fromHtml("<b>"+mOrder.getVehicled_regno()+"</b>"+mOrder.getVehicle_name()+" | "+mOrder.getVehicle_cat_name()));
        holder.txtJobOrder.setText("Order Id :"+mOrder.getOrdreq_id());
        holder.txtJobMobile.setText(mOrder.getCus_mob());
        holder.txtJobCustomer.setText(mOrder.getCus_name());
        holder.txtJobLocation.setText("Location : "+mOrder.getOrdreq_location());
        holder.txtJobDistance.setText("Distance : "+mOrder.getDistance());
        holder.txtJobDistance.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.txtJobDistance.setSelected(true);
        holder.txtJobDistance.setSingleLine(true);
        holder.txtJobStatus.setText(mOrder.getStatus_name());
        holder.cvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, JobDetailActivity.class)
                        .putExtra("order_id",mOrder.getOrdreq_id())
                        .putExtra("customer_id",mOrder.getCus_id())
                        .putExtra("button",true));
            }
        });
    }


    @Override
    public int getItemCount() {
        return (arrayLists == null) ? 0 : arrayLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_joborderid,txt_jobid,txtJobDate,txtJobUnique,txtJobCustomer,
                txtJobDistance,txtJobLocation,txtJobStatus,
                txtJobMobile,txtJobOrder;
        public CardView cvCategory;

        public MyViewHolder(View view) {
            super(view);
            txt_jobid = view.findViewById(R.id.txt_jobid);
            txt_joborderid = view.findViewById(R.id.txt_joborderid);
            txtJobDate = view.findViewById(R.id.txtJobDate);
            txtJobUnique = view.findViewById(R.id.txtJobUnique);
            txtJobOrder = view.findViewById(R.id.txtJobOrder);
            txtJobCustomer = view.findViewById(R.id.txtJobCustomer);
            txtJobLocation = view.findViewById(R.id.txtJobLocation);
            txtJobDistance = view.findViewById(R.id.txtJobDistance);
            txtJobStatus = view.findViewById(R.id.txtJobStatus);
            txtJobMobile = view.findViewById(R.id.txtJobMobile);
            cvCategory = view.findViewById(R.id.cvCategory);


        }
    }
}
