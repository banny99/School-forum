package benji.and.mishku.inc.viaforum;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import java.util.function.Function;
import benji.and.mishku.inc.viaforum.models.Post;

public class DialogGenerator {
    public static void showConfirmationDialog(String title, String msg, Activity activity, Function confirmAction){
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Do you really wish to delete this post?")
                .setTitle("U sure?");

        // Add the buttons
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                confirmAction.apply(null);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showPostEditingDialog(Post editedPost, Activity activity, Function confirmAction){
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View popUpView = activity.getLayoutInflater().inflate(R.layout.edit_post_popup, null);
        EditText title = popUpView.findViewById(R.id.editPostTitle);
        title.setText(editedPost.getTitle());
        EditText text = popUpView.findViewById(R.id.editPostText);
        text.setText(editedPost.getPostText());

        builder.setView(popUpView);

        // Add the buttons
        builder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editedPost.setTitle(title.getText().toString());
                editedPost.setPostText(text.getText().toString());
                confirmAction.apply(null);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}