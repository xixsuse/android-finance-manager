package com.example.master.android_finance_manager.EntriesActivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.master.android_finance_manager.R;

import java.util.ArrayList;

import adapters.RecyclerAdapterAccrual;
import adapters.RecyclerAdapterExpense;
import data.FinancialManagerDbHelper;
import entities.Accrual;
import entities.Expense;

import static com.example.master.android_finance_manager.FinanceManagerActivity.CURRENT_ACCOUNT_ID;
import static com.example.master.android_finance_manager.FinanceManagerActivity.CURRENT_APP;

public class EntriesHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    FinancialManagerDbHelper mFinancialManagerDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_history_page);

        mFinancialManagerDbHelper = new FinancialManagerDbHelper(this);

        recyclerView = findViewById(R.id.entriesRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        linearLayoutManager = new LinearLayoutManager(this);

        SharedPreferences preferences = getSharedPreferences(CURRENT_APP, MODE_PRIVATE);
        if(getIntent().getStringExtra("ENTRY_TYPE").equals("EXPENSE")) {
            ArrayList<Expense> expenses =
                    Expense.readAllFromDatabase(mFinancialManagerDbHelper, preferences.getInt(CURRENT_ACCOUNT_ID, 1));
            adapter = new RecyclerAdapterExpense(expenses);
        } else {
            ArrayList<Accrual> accruals =
                    Accrual.readAllFromDatabase(mFinancialManagerDbHelper, preferences.getInt(CURRENT_ACCOUNT_ID, 1));
            adapter = new RecyclerAdapterAccrual(accruals);
        }

        recyclerView.setAdapter(adapter);

    }

}
