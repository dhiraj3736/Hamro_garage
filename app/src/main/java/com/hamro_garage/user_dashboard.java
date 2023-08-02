package com.hamro_garage;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class user_dashboard extends AppCompatActivity {

    LinearLayout ubike;
    LinearLayout user_profile;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        ubike = findViewById(R.id.bikebtn);
        user_profile = findViewById(R.id.profilebtn);
        searchView = findViewById(R.id.searchView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment myFragment = new UserMapsFragment();
        fragmentTransaction.add(R.id.map, myFragment);

        ubike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(user_dashboard.this, nearest_garage.class);
                startActivity(intent1);
                finish();
            }
        });

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(user_dashboard.this, user_profile.class);
                startActivity(intent2);
                finish();
            }
        });

        // Set up the SearchView listener to handle search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Create a request to search the database using the PHP API
                String URL = Endpoints.Search_Garage;
                RequestQueue queue = Volley.newRequestQueue(user_dashboard.this);

                JSONObject requestObject = new JSONObject();
                try {
                    requestObject.put("search_query", query);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, requestObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // Process the response and extract search results as a List<String>
                                List<String> searchResults = extractSearchResultsFromResponse(response);

                                // After receiving the search results, show them in a dialog
                                showSearchResultsDialog(searchResults);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle the error response if needed
                                String errorMessage = "Unknown error";
                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    errorMessage = new String(error.networkResponse.data);
                                }
                                Log.e("VolleyError", errorMessage);
                                Toast.makeText(user_dashboard.this, "Error fetching search results.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                queue.add(request);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle any search query text changes here (if needed)
                return false;
            }
        });
    }

    // Method to show the search results in a dialog
    private void showSearchResultsDialog(List<String> searchResults) {
        if (searchResults == null || searchResults.isEmpty()) {
            // No results found, show a toast or a message
            Toast.makeText(this, "No matching results found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_search_results, null);

        // Create the dialog
        Dialog searchResultsDialog = new Dialog(this);
        searchResultsDialog.setContentView(dialogView);

        // Get a reference to the ListView inside the dialog layout
        ListView listViewSearchResults = dialogView.findViewById(R.id.listView_search_results);

        // Create an ArrayAdapter to populate the ListView with search results
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);

        // Set the adapter for the ListView
        listViewSearchResults.setAdapter(adapter);

        // Set a click listener for the ListView items
        listViewSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click, e.g., open the selected garage details activity
                // You can get the selected item from the adapter and use it as needed.

                String selectedResult = adapter.getItem(position);
                // Example: You can pass the selected result to a new activity to show the details
                Intent intent = new Intent(user_dashboard.this, GarageDetailsActivity.class);
                intent.putExtra("selectedResult", selectedResult);
                startActivity(intent);

                searchResultsDialog.dismiss(); // Dismiss the dialog after handling the click
            }
        });

        // Show the dialog
        searchResultsDialog.show();
    }

    // Method to extract search results from the JSON response
    private List<String> extractSearchResultsFromResponse(JSONObject response) {
        List<String> searchResults = new ArrayList<>();
        try {
            int success = response.getInt("success");
            if (success == 1) {
                JSONArray data = response.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject resultObject = data.getJSONObject(i);
                    String garageName = resultObject.getString("garage_name");
                    String location = resultObject.getString("location");
                    String searchResult = garageName + ", " + location;
                    searchResults.add(searchResult);
                }
            } else {
                searchResults.add("No matching results found.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
