package benji.and.mishku.inc.viaforum.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.*;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.SignInActivity;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.SubforumsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NavigationView navDrawer;
    DrawerLayout drawerLayout;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    FloatingActionButton actionButton;
    Toolbar toolbarBottom;

    PostsViewModel postsViewModel;
    UserViewModel userViewModel;
    SubforumsViewModel subforumsViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(benji.and.mishku.inc.viaforum.R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        //get curr user:
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //set user-listener
        fireAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    //user not login
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    finish();
                }
            }
        };

        initViews();
        navSetup();

        actionButton=findViewById(R.id.addPostButton);
        actionButton.setOnClickListener(view -> navController.navigate(R.id.nav_add_post));

        View headerView=navDrawer.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.userName);
        TextView email=headerView.findViewById(R.id.userEmail);
        subforumsViewModel.addSubforum(new Subforum("Administration", "This is a subforum for..."));
        userViewModel.getLoggedUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username.setText(user.getUsername());
                email.setText(user.getEmail());
            }
        });
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


    private void initViews() {
        drawerLayout = findViewById(benji.and.mishku.inc.viaforum.R.id.drawer_layout);
        navDrawer = findViewById(benji.and.mishku.inc.viaforum.R.id.navigation_drawer);
        toolbarBottom = findViewById(benji.and.mishku.inc.viaforum.R.id.bottomAppBar);

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        subforumsViewModel = new ViewModelProvider(this).get(SubforumsViewModel.class);
    }

    private void navSetup() {
        setSupportActionBar(toolbarBottom);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_saved_posts, R.id.nav_profile, R.id.nav_help, R.id.nav_sub_forums, R.id.nav_your_posts).setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navDrawer, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
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
    public void onClick(View view) {
        if(view.getId()==R.id.addPostButton){
            navController.navigate(R.id.nav_add_post);

        }
    }
}