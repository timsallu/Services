package com.example.instruments.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instruments.R;
import com.example.instruments.model.InstrumentModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RecyclerInstrumentAdapter extends RecyclerView.Adapter<RecyclerInstrumentAdapter.MyViewHolder> implements Filterable {

    private static final String TAG = "Instrument";
    private List<InstrumentModel> modelinstList=new ArrayList<>();
    private List<InstrumentModel> modelinstListFull ;

    private Activity activity;
    ClickListener clickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_recycle_layout, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(modelinstList.get(position),clickListener);
    }

    @Override
    public int getItemCount() {
        return modelinstList.size();
    }


    public void setInstrument(List<InstrumentModel> modelinstList, Activity activity, ClickListener clickListener){
        this.modelinstList = modelinstList;
        this.activity=activity;
        this.clickListener = clickListener;
        modelinstListFull =new ArrayList<>(modelinstList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<InstrumentModel> instrumentfilterdList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0)
            {
                instrumentfilterdList.addAll(modelinstListFull);
            }
            else
            {
                String filterpattern= charSequence.toString().toLowerCase().trim();

                for(InstrumentModel model : modelinstListFull)
                {
                    if(model.getInstrumentName().toLowerCase().contains(filterpattern))
                    {
                        instrumentfilterdList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = instrumentfilterdList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            modelinstList.clear();
            modelinstList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TableRow tableRow;
        TextView tvname;
        TextView tvsymbol;
        TextView tvbid;
        TextView tvask;
        ClickListener clickListener;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tableRow = itemView.findViewById(R.id.tabRow);
            tvname = itemView.findViewById(R.id.tv_instrumentName);
            tvsymbol = itemView.findViewById(R.id.tv_instrumentSymbol);
            tvbid = itemView.findViewById(R.id.tv_bid);
            tvask = itemView.findViewById(R.id.tv_ask);

            tableRow.setOnClickListener(this);
        }


        void bind(InstrumentModel modelinstList,ClickListener clickListener){

            tvname.setText(modelinstList.getInstrumentName());
            tvsymbol.setText(modelinstList.getInstrumentSymbol());
            tvbid.setText(modelinstList.getBid());
            tvask.setText(modelinstList.getAsk());
            this.clickListener = clickListener;

        }


        @Override
        public void onClick(View v) {

            clickListener.OnGridClic(getAdapterPosition());
        }
    }

   public interface ClickListener
    {
        void OnGridClic(int pos);
    }

}
