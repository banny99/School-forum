package benji.and.mishku.inc.viaforum.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.*;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    NavigationView navDrawer;
    DrawerLayout drawerLayout;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    FloatingActionButton actionButton;
    Toolbar toolbarBottom;
    UserViewModel userViewModel;
    PostsViewModel postsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(benji.and.mishku.inc.viaforum.R.layout.activity_main);
        initViews();
        navSetup();

        actionButton=findViewById(R.id.addPostButton);
        actionButton.setOnClickListener(view -> navController.navigate(R.id.nav_add_post));

        View headerView=navDrawer.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.userName);
        TextView email=headerView.findViewById(R.id.userEmail);

        String emailStr = userViewModel.getCurrentUser().getValue().getEmail();
        username.setText(emailStr.substring(0,emailStr.indexOf('@')));
        email.setText(emailStr);
    }

    private void initViews() {
        drawerLayout = findViewById(benji.and.mishku.inc.viaforum.R.id.drawer_layout);
        navDrawer = findViewById(benji.and.mishku.inc.viaforum.R.id.navigation_drawer);
        toolbarBottom = findViewById(benji.and.mishku.inc.viaforum.R.id.bottomAppBar);
        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        postsViewModel= new ViewModelProvider(this).get(PostsViewModel.class);
    }

    private void navSetup() {
        setSupportActionBar(toolbarBottom);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_saved_posts, R.id.nav_profile, R.id.nav_help, R.id.nav_sub_forums, R.id.nav_your_posts, R.id.nav_add_post, R.id.nav_post_detail).setOpenableLayout(drawerLayout).build();
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
        Log.println(Log.WARN,"Click","Clicked");
        if(view.getId()==R.id.addPostButton){
            navController.navigate(R.id.nav_add_post);
        }
    }
}