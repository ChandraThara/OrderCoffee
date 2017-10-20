package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.id.edit;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int totalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText userNameView = (EditText)findViewById(R.id.name_view);
        String name = userNameView.getText().toString();//get cutomer name from UI
        CheckBox whippedCreamView = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamView.isChecked();
        CheckBox chocolateView = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateView.isChecked();
        totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        //Log.v("IsChecked=====>", String.valueOf(hasWhippedCream));
        String message = createOrderSummary(name, totalPrice, hasWhippedCream, hasChocolate);
        String[] Address = {"ctc.star@gmail.com"};
        String subject = "JustJava order for "+name;
        //String body = message;
        composeEmail(Address, subject, message);
        displayMessage( message );
    }
    public void incrementQuantity(View view) {
        if( quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 cups of coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrementQuantity(View view) {
        if( quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    public int calculatePrice( boolean hasWhippedCream, boolean hasChocolate ){
        int basePrice = 5;
        if( hasWhippedCream )
            basePrice = basePrice + 1 ;
        if( hasChocolate )
            basePrice = basePrice + 2 ;
        return quantity * basePrice;
    }
    private String createOrderSummary( String sName, int price, boolean hasWhippedCream, boolean hasChocolate ){
        String message = "";
        message = getString(R.string.order_summary_name, sName);
        message = message + "\nAdd whipped cream?"+hasWhippedCream;
        message = message +"\nAdd Chocolate?"+hasChocolate;
        message = message + "\nQuantity : "+quantity;
        message = message +"\nTotal : $"+totalPrice;
        message = message +"\n"+getString(R.string.thank_you);
        return message;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message){
//        TextView orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
          Toast.makeText(this, "Your order summary has been emailed!", Toast.LENGTH_SHORT).show();
    }
    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
