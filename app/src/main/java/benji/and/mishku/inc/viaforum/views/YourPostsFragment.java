package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.User;
import benji.and.mishku.inc.viaforum.viewModels.PostsViewModel;
import benji.and.mishku.inc.viaforum.viewModels.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourPostsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PostsViewModel postsViewModel;


    public YourPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourPosts.
     */
    // TODO: Rename and change types and number of parameters
    public static YourPostsFragment newInstance(String param1, String param2) {
        YourPostsFragment fragment = new YourPostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        postsViewModel=new ViewModelProvider(this).get(PostsViewModel.class);
//        postsViewModel.getAllPosts().observe(this, posts -> {
//
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_your_posts, container, false);

        //Initialize objects ...
        RecyclerView postListRV = inflatedView.findViewById(R.id.rv);
        postListRV.hasFixedSize();
        postListRV.setLayoutManager(new LinearLayoutManager(getContext()));

        //fill with dummy posts:



      List<Post> posts=postsViewModel.getAllPosts().getValue();
        PostAdapter postAdapter = new PostAdapter(posts);
        postAdapter.setOnClickListener(post -> {
            Toast.makeText(getContext(), post.getTitle(), Toast.LENGTH_LONG ).show();
        });

        postListRV.setAdapter(postAdapter);

        return inflatedView;
    }

}