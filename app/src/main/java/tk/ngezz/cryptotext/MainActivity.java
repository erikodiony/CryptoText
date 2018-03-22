package tk.ngezz.cryptotext;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.moveTaskToBack(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentHome frag_home = new FragmentHome();
            getSupportActionBar().setTitle("   Home");
            getSupportActionBar().setLogo(R.drawable.ic_home_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_home)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_encrypt) {
            FragmentEncrypt frag_encrypt = new FragmentEncrypt();
            getSupportActionBar().setTitle("   Encrypt");
            getSupportActionBar().setLogo(R.drawable.ic_speaker_notes_off_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_encrypt)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_decrypt) {
            FragmentDecrypt frag_decrypt = new FragmentDecrypt();
            getSupportActionBar().setTitle("   Decrypt");
            getSupportActionBar().setLogo(R.drawable.ic_speaker_notes_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_decrypt)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_key) {
            FragmentKey frag_key = new FragmentKey();
            getSupportActionBar().setTitle("   Create Key");
            getSupportActionBar().setLogo(R.drawable.ic_key_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_key)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_help) {
            FragmentHelp frag_help = new FragmentHelp();
            getSupportActionBar().setTitle("   Help");
            getSupportActionBar().setLogo(R.drawable.ic_help_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_help)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_about) {
            FragmentAbout frag_about = new FragmentAbout();
            getSupportActionBar().setTitle("   About");
            getSupportActionBar().setLogo(R.drawable.ic_info_white);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_frame,frag_about)
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
