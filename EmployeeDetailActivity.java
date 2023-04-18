import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class EmployeeDetailActivity extends AppCompatActivity {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        // Get the employee object from the intent
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("employee");

        // Set the employee details in the UI
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);

        // Set the employee name in camel case
        String camelCaseName = toCamelCase(employee.getName());
        nameTextView.setText(camelCaseName);

        // Set the email address in lowercase and make it clickable
        String lowercaseEmail = employee.getEmail().toLowerCase(Locale.getDefault());
        emailTextView.setText(lowercaseEmail);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", employee.getEmail(), null));
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });

        // Set the phone number and make it clickable
        phoneTextView.setText(employee.getPhone());
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getPhone()));
                startActivity(callIntent);
            }
        });

        // Check for internet connectivity
        if (!isInternetConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInternetConnected() {
        // TODO: Implement internet connectivity check
        return true;
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
}
