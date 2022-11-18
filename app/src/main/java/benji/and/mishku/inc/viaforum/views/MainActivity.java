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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.*;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Comment;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.CommentsViewModel;
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
    UserViewModel userViewModel;
    PostsViewModel postsViewModel;
    CommentsViewModel commentsViewModel;
    SubforumsViewModel subforumsViewModel;
    private void initViews() {
        drawerLayout = findViewById(benji.and.mishku.inc.viaforum.R.id.drawer_layout);
        navDrawer = findViewById(benji.and.mishku.inc.viaforum.R.id.navigation_drawer);
        toolbarBottom = findViewById(benji.and.mishku.inc.viaforum.R.id.bottomAppBar);
        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        commentsViewModel=new ViewModelProvider(this).get(CommentsViewModel.class);
        postsViewModel= new ViewModelProvider(this).get(PostsViewModel.class);
        subforumsViewModel=new ViewModelProvider(this).get(SubforumsViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(benji.and.mishku.inc.viaforum.R.layout.activity_main);
        initViews();
        navSetup();
        User tempUser = new User("Ben", "email", "pass",new Date(101199));
        userViewModel.addUser(tempUser);
        for(int i=0; i<8;i++){
            subforumsViewModel.addSubforum(new Subforum("VAR"+i,"oisjadhioashdlkjashldkhasljkdhlas"+i));
        }
        actionButton=findViewById(R.id.addPostButton);
        actionButton.setOnClickListener(view -> navController.navigate(R.id.nav_add_post));
        View headerView=navDrawer.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.userName);
        TextView email=headerView.findViewById(R.id.userEmail);
        User u=userViewModel.getUserByUsername("Ben");
        username.setText(u.getUsername());
        email.setText(u.getEmail());
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