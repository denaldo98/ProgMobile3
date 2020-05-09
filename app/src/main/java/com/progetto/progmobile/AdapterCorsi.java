package com.progetto.progmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.progetto.progmobile.entities.Corso;

public class AdapterCorsi extends FirestoreRecyclerAdapter<Corso, AdapterCorsi.CorsoHolder> {
    private OnItemClickListener listener;

    public AdapterCorsi(@NonNull FirestoreRecyclerOptions<Corso> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CorsoHolder holder, int position, @NonNull Corso model) {
        holder.textNome.setText(model.getNome());
        holder.textCFU.setText(String.format("%d", model.getNumeroCFU()));
    }

    @NonNull
    @Override
    public CorsoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_corso, parent, false);
        return new CorsoHolder(v);
    }

    public void deleteItem(int position) {
        // getSnapshots returns all the document snapshots; getSnapshot returns dhe particular item on this position
        getSnapshots().getSnapshot(position).getReference().delete();
    }



    class CorsoHolder extends RecyclerView.ViewHolder {
        TextView textNome, textCFU;

        public CorsoHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textRigaCorsoNome);
            textCFU = itemView.findViewById(R.id.textRigaCorsoCFU);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot , int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
