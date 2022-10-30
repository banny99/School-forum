package benji.and.mishku.inc.viaforum.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import benji.and.mishku.inc.viaforum.R;

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
        //TODO - fill with real data later...
//        ArrayList<Post> posts = new ArrayList<>();
//        User tempUser = new User("Ben", "email", "pass",new Date(101199));
//        for (int i=1;i<=20;i++){
//            posts.add(new Post( "title"+i,"text"+i, tempUser, PostCategory.DOCUMENTS, LocalDateTime.now()));
//        }
//
//        //set onClick action for each list-item:
//        PostAdapter postAdapter = new PostAdapter(posts);
//        postAdapter.setOnClickListener(post -> {
//            Toast.makeText(getContext(), post.getTitle(), Toast.LENGTH_LONG ).show();
//        });

        //assign
//        postListRV.setAdapter(postAdapter);

        return inflatedView;
    }
}