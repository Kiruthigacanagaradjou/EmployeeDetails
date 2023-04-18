import android.content.Context;

import android.content.Intent;

import android.net.Uri;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import java.util.Locale;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;

    private ArrayList<Employee> employeeList;

    private OnItemClickListener onItemClickListener;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {

        this.context = context;

        this.employeeList = employeeList;

    }

    public interface OnItemClickListener {

        void onItemClick(Employee employee);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.onItemClickListener = listener;

    }

    @NonNull

    @Override

    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.employee_list_item, parent, false);

        return new EmployeeViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {

        Employee employee = employeeList.get(position);

        // Set the employee name in camel case

        String camelCaseName = toCamelCase(employee.getName());

        holder.nameTextView.setText(camelCaseName);

        // Set the email address in lowercase and make it clickable

        String lowercaseEmail = employee.getEmail().toLowerCase(Locale.getDefault());

        holder.emailTextView.setText(lowercaseEmail);

        holder.emailTextView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", employee.getEmail(), null));

                context.startActivity(Intent.createChooser(emailIntent, "Send email"));

            }

        });

        // Set the phone number and make it clickable

        holder.phoneTextView.setText(employee.getPhone());

        holder.phoneTextView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getPhone()));

                context.startActivity(callIntent);

            }

        });

    }

    @Override

    public int getItemCount() {

        return employeeList.size();

    }

    private String toCamelCase(String inputString) {

        String[] words = inputString.split("\\s");

        StringBuilder camelCaseString = new StringBuilder();

        for (String word : words) {

            camelCaseString.append(Character.toUpperCase(word.charAt(0)));

            camelCaseString.append(word.substring(1).toLowerCase(Locale.getDefault()));

        }

        return camelCaseString.toString();

    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, emailTextView, phoneTextView;

        public EmployeeViewHolder(@NonNull View itemView) {

            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);

            emailTextView = itemView.findViewById(R.id.emailTextView);

            phoneTextView = itemView.findViewById(R.id.phoneTextView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {

                        onItemClickListener.onItemClick(employeeList.get(position));

                    }

                }

            });

        }

    }

}

