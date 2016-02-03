
package com.scc.testgrability;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import connector.ImageViews;
import connector.ItemSolicitudAdapter;
import views.GridViewAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ArrayList<ImageViews> listaSolicitudes = new ArrayList<ImageViews>();
    public String answer;
    public static ListView	lv;
    ItemSolicitudAdapter adapter = null;
    EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (isTablet(getApplicationContext())) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.activity_main, getData());
        gridView.setAdapter((ListAdapter) gridAdapter);

        private ArrayList<ImageViews> getData() {
            final ArrayList<ImageViews> imageItems = new ArrayList<>();
            TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
            for (int i = 0; i < imgs.length(); i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
                imageItems.add(new ImageViews(bitmap, "Image#" + i));
            }
            return imageItems;
        }

    }

    public void CargarListView(String JRR) {

        try {

            JSONArray ARR = new JSONArray(JRR);
            Boolean valid = false;
            Boolean valid2 = false;

            if (ARR.length() == 0) {
                Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_LONG).show();
                answer = "error de servidor, conectate más tarde";};
            } catch catch (JSONException e) {
            e.printStackTrace();
        } {

            Object rta = null;
            JSONArray ARR = new JSONArray(rta.Respuesa);

            for (int i = 0; i < ARR.length(); i++) {
                JSONObject jObject = null;
                try {
                    jObject = ARR.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ImageViews sol = new ImageViews();

                try {
                    sol.Color_Switch = Long.parseLong(jObject.getString("cs"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Messenger = jObject.getString("ms");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Instagram = jObject.getString("ig");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Snapchat = jObject.getString("sc");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Facebook = jObject.getString("fb");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Fit_Girls_Guide = jObject.getString("fgg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sol.Trump_Dump = jObject.getString("td");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listaSolicitudes.add(sol);

                }

                lv = (ListView) findViewById(R.id.listView);


                lv.setAdapter(adapter);

                editsearch = (EditText) findViewById(R.id.listView);
                editsearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                        adapter.filter(text);

                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        //finish();
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        //finish();

                    }
                });

            }

        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void onPostExecute(ImageViews rta) {
        
        }

    public static void JsonCatcher(String args[]) throws IOException {

        String value = "";
        URL uri = new URL("https://itunes.apple.com/us/app/color-switch/id1053533457?mt=8&uo=2");
        BufferedReader input = new BufferedReader(new InputStreamReader(uri.openStream(), "UTF-8"));
        while(input.readLine()!=null) {
            value+=input.readLine();
        }

    }

    public static void JsonViews(final String[] argv) throws JSONException {
        final JSONObject obj = new JSONObject(DOWNLOAD_SERVICE);
        final JSONArray rappidata = obj.getJSONArray("rappidata");
        final int n = rappidata.length();
        for (int i = 0; i < n; ++i) {
            final JSONObject person = rappidata.getJSONObject(i);

        }

}

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_request) {

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else if (id == R.id.nav_resume_app) {

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else if (id == R.id.nav_end_session) {
            Intent act = new Intent(this, LoginActivity.class);

            startActivity(act);
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_XLARGE;

    }

    public static boolean isPhone(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }



}
