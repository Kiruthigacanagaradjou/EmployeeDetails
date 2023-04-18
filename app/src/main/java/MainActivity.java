import android.content.Intent;

import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.Callback;

import retrofit2.Response;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements EmployeeAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private EmployeeAdapter employeeAdapter;

    private ArrayList<Employee> employeeList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeList = new ArrayList<>();

        // Check for internet connectivity

        if (!isInternetConnected()) {

            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();

        }

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://jsonplaceholder.typicode.com/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

        Call<ArrayList<Employee>> call = employeeApi.getEmployees();

        call.enqueue(new Callback<ArrayList<Employee>>() {

            @Override

            public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();

                    return;

                }

                employeeList = response.body();

                employeeAdapter = new EmployeeAdapter(MainActivity.this, employeeList);

                recyclerView.setAdapter(employeeAdapter);

                employeeAdapter.setOnItemClickListener(MainActivity.this);

            }

            @Override

            public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override

    public void onItemClick(int position) {

        Intent intent = new Intent(this, EmployeeDetailActivity.class);

        intent.putExtra("employee", employeeList.get(position));

        startActivity(intent);

    }

    private boolean isInternetConnected() {

        // TODO: Implement internet connectivity check

        return true;

    }

}

