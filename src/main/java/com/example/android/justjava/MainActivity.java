/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int numberOfCoffees = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name_field = findViewById(R.id.name_field);
        String name = name_field.getText().toString();

        CheckBox checkBox = findViewById(R.id.whip_check_box);
        boolean hasWhippedCream = checkBox.isChecked();

        CheckBox chocCheckBox = findViewById(R.id.choc_check_box);
        boolean hasChocolate = chocCheckBox.isChecked();

        String PriceMessage = getResources().getString(R.string.total_string) +
                                calculatePrice(numberOfCoffees, hasWhippedCream, hasChocolate);
        PriceMessage = getResources().getString(R.string.summary_name, name) + "\n" +
                        getResources().getString(R.string.whip_cream_mesg) + hasWhippedCream + "\n" +
                        getResources().getString(R.string.choc_msg) + hasChocolate + "\n" +
                        getResources().getString(R.string.quantity_msg) + numberOfCoffees + "\n" +
                        PriceMessage + "\n" + getResources().getString(R.string.thank_you_msg);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "coffee@emailaddress.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, PriceMessage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private int calculatePrice(int quantity, Boolean hasWhippedCream, Boolean hasChocolate){
        int toppings = 0;

        if (hasWhippedCream){
            toppings += numberOfCoffees * 1;
        }

        if (hasChocolate){
            toppings += numberOfCoffees * 2;
        }

        return quantity * 5 + toppings;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method decrements quantity by 1.
     */
    public void decrement(View view) {
        if (numberOfCoffees >= 1){
            numberOfCoffees--;
            display(numberOfCoffees);
        }
    }

    /**
     * This method increments quantity by 1.
     */
    public void increment(View view) {
        if (numberOfCoffees < 10){
            numberOfCoffees++;
            display(numberOfCoffees);
        }
    }
}
