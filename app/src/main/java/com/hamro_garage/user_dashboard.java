package com.hamro_garage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.hamro_garage.model.SearchSuggestions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class user_dashboard extends AppCompatActivity {

    LinearLayout ubike;
    LinearLayout user_profile;
    EditText searchView;
    MaterialCardView searchBtn;
    String searchText;
    ArrayList<SearchSuggestions> suggestionsList;
    ListView suggestionsListView;
    ListView resultListView;
    ArrayList<String> onlyLocationSuggestion;
    ArrayList<String > resultOfSearch;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> resultAdapter;

    ImageView moreOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        ubike = findViewById(R.id.bikebtn);
        user_profile = findViewById(R.id.profilebtn);
        searchView = findViewById(R.id.searchView);
        searchBtn=findViewById(R.id.searchBtn);
        suggestionsListView=findViewById(R.id.suggestionList);
        resultListView=findViewById(R.id.resultListView);
        suggestionsListView.setVisibility(View.GONE);
        suggestionsListView.setClickable(false);
        resultListView.setVisibility(View.GONE);
        resultListView.setClickable(false);

        suggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.setText(adapterView.getItemAtPosition(i).toString());
                suggestionsListView.setVisibility(View.GONE);
                suggestionsListView.setClickable(false);
            }
        });

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String garage_name=adapterView.getItemAtPosition(i).toString();
                Log.d("GarageName","The Name is: "+garage_name);
                resultListView.setVisibility(View.GONE);
                resultListView.setClickable(false);
                Intent intent = new Intent(getApplicationContext(), GarageDetailsActivity.class);
                intent.putExtra("garage_name",garage_name);
                intent.putExtra("Source","Search");// Pass the garage details as a JSON string
                startActivity(intent);
            }
        });

        suggestionsList=new ArrayList<SearchSuggestions>();
        onlyLocationSuggestion=new ArrayList<>();
        resultOfSearch=new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL=Endpoints.getSearchSuggestions;
        suggestionsList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response",""+response);
                try {
//                        JSONObject jsonObject = new JSONObject(response);
                    JSONObject parentObject=new JSONObject(response);
                    JSONArray jsonArray=parentObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject value=jsonArray.getJSONObject(i);
                        onlyLocationSuggestion.add(value.getString("location"));



                    }
//                        String success = jsonObject.getString("success");
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, onlyLocationSuggestion);
                suggestionsListView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("searchText", String.valueOf(searchText));
//                    return data;
//                }
        };

        queue.add(request);


        searchBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("Search","Search Button");
            resultOfSearch.clear();
            suggestionsListView.setVisibility(View.GONE);
            suggestionsListView.setClickable(false);

            String algorithmURL=Endpoints.implementSearchAlgorithm;
            suggestionsList.clear();
            StringRequest request = new StringRequest(Request.Method.POST, algorithmURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("Response",""+response);
                    try {
//                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject parentObject=new JSONObject(response);
                        JSONArray jsonArray=parentObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject value=jsonArray.getJSONObject(i);
                            resultOfSearch.add(value.getString("garage_name"));



                        }
//                        String success = jsonObject.getString("success");
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    resultAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, resultOfSearch);
                    resultListView.setAdapter(resultAdapter);
                    resultListView.setVisibility(View.VISIBLE);
                    resultListView.setClickable(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("searchText", searchView.getText().toString());
                    Log.d("SearchText",searchView.getText().toString());
                    return data;
                }
            };

            queue.add(request);

        }
    });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchText = charSequence.toString();
                if(searchText.isEmpty()){
                    suggestionsListView.setVisibility(View.GONE);
                    suggestionsListView.setClickable(false);
                }else{
                    adapter.getFilter().filter(searchText);
                    suggestionsListView.setVisibility(View.VISIBLE);
                    suggestionsListView.setClickable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        moreOptions=findViewById(R.id.moreOptions);
        registerForContextMenu(moreOptions);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder=new AlertDialog.Builder(user_dashboard.this);
            builder.setTitle("Logout");
            builder.setMessage("Do you want to log out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedPreferences=getSharedPreferences("HamroGarage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove("userType");
                    editor.remove("session_id");
                    editor.commit();
                    Intent intent=new Intent(user_dashboard.this,chooseuser.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
