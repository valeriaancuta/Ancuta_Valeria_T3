package com.example.tema3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserFragment extends Fragment implements ToDoAdapter.ItemClickListener {

    View v;
    int user_id;
    List<ToDo> data = new ArrayList<>();
    ToDoAdapter adapter;
    public UserFragment(int position) {
        user_id = position;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ToDo>> call = jsonPlaceHolderApi.getToDosForUser(user_id);
        call.enqueue(new Callback<List<ToDo>>() {
            @Override
            public void onResponse(Call<List<ToDo>> call, Response<List<ToDo>> response) {
                data = response.body();
                initRW();
            }

            @Override
            public void onFailure(Call<List<ToDo>> call, Throwable t) {

            }
        });
        return v;
    }

    private void initRW(){
        RecyclerView rw = v.findViewById(R.id.rw);
        rw.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ToDoAdapter(getContext(), data);
        rw.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Fragment fragment = new AlarmFragment(data.get(position).getTitle());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }
}

