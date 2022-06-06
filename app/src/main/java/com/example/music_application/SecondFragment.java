package com.example.music_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.music_application.databinding.FragmentSecondBinding;
import com.example.music_application.entity.Customer;
import com.example.music_application.viewmodel.CustomerViewModel;

import java.util.List;

public class SecondFragment extends Fragment {
    private CustomerViewModel customerViewModel;
    private FragmentSecondBinding binding;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSecondBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        customerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable final List<Customer> customers) {
                for (Customer temp : customers) {
                    String customerDetails = ("Customer ID: "+temp.uid + "\n" + "Username: "+temp.username + "\n" + "Email: "+temp.emailAddress + "\n" + "Address: "+temp.address);
                    binding.textViewRead.setText(customerDetails);
                }
            }
        });

        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return view;
    }
}