package com.example.teste1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Dataclass> datalist;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        datalist = new ArrayList<>();
        adapter = new MyAdapter(datalist, MainActivity.this);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorials");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear(); // Limpa os dados antigos
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dataclass dataclass = dataSnapshot.getValue(Dataclass.class);
                    if (dataclass != null) { // Verifica se o objeto não é nulo
                        dataclass.setKey(dataSnapshot.getKey()); // Define a chave
                        datalist.add(dataclass); // Adiciona o item à lista
                    }
                }
                Log.d("DataUpdate", "Total items loaded: " + datalist.size()); // Log do total de itens
                adapter.notifyDataSetChanged(); // Atualiza a RecyclerView
                dialog.dismiss(); // Esconde o diálogo de carregamento
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DataError", "Database error: " + error.getMessage()); // Log de erro
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Ação ao submeter a busca (se necessário)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);  // Atualiza a lista com a pesquisa
                return true;
            }
        });

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UploadActivity.class);
            startActivity(intent);
        });
    }

    public void searchList(String text) {
        ArrayList<Dataclass> searchList = new ArrayList<>();
        for (Dataclass dataclass : datalist) {
            if (dataclass.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataclass);
            }
        }
        adapter.searchDataList(searchList);
    }
}
