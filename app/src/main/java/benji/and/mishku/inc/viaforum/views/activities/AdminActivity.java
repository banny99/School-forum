package benji.and.mishku.inc.viaforum.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class AdminActivity extends AppCompatActivity {
    private UserViewModel userViewModel;

    NavigationView navDrawer;
    DrawerLayout drawerLayout;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    FloatingActionButton actionButton;
    Toolbar toolbarBottom;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener fireAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initViews();

        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    //user not login
                    startActivity(new Intent(AdminActivity.this, SignInActivity.class));
                    finish();
                }
            }
        };

        navSetup();
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navDrawer = findViewById(R.id.navigation_drawer_admin);
        toolbarBottom = findViewById(R.id.bottomAppBarAdmin);
        actionButton=findViewById(R.id.addSubforumButton);
        actionButton.setOnClickListener(view -> navController.navigate(R.id.nav_add_subforum));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View headerView=navDrawer.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.userName);

        userViewModel.getLoggedUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@NonNull User user) {
                username.setText(user.getUsername());
            }
        });

        ImageButton signOutButton=headerView.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(l->{
            userViewModel.logOut();
            firebaseAuth.signOut();
        });
    }

    private void navSetup() {
        setSupportActionBar(toolbarBottom);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_subforums_admin, R.id.nav_reported_posts).setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navDrawer, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(fireAuthListener != null){
            firebaseAuth.removeAuthStateListener(fireAuthListener);
        }
    }
}