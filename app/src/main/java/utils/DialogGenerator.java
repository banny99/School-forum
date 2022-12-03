package utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Function;

import benji.and.mishku.inc.viaforum.R;
import benji.and.mishku.inc.viaforum.models.Post;
import benji.and.mishku.inc.viaforum.models.Subforum;
import benji.and.mishku.inc.viaforum.models.User;

public class DialogGenerator {
    public static void showConfirmationDialog(String title, String msg, Activity activity, Function confirmAction){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(msg)
                .setTitle(title);


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

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showPostEditingDialog(Post editedPost, Activity activity, Function confirmAction){
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

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void showSubforumEditingDialog(Subforum editedSubforum, Activity activity, Function confirmAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View popUpView = activity.getLayoutInflater().inflate(R.layout.edit_subforum_popup, null);
        EditText title = popUpView.findViewById(R.id.editSubforumTitle);
        title.setText(editedSubforum.getName());
        EditText text = popUpView.findViewById(R.id.editSubforumDescription);
        text.setText(editedSubforum.getDescription());

        builder.setView(popUpView);

        // Add the buttons
        builder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editedSubforum.setName(title.getText().toString());
                editedSubforum.setDescription(text.getText().toString());
                confirmAction.apply(null);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void showPostReportingDialog(Post reportedPost, Activity activity, Function confirmAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View popUpView = activity.getLayoutInflater().inflate(R.layout.report_post_popup, null);
        EditText report = popUpView.findViewById(R.id.editReportText);


        builder.setView(popUpView);

        builder.setPositiveButton("Report", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reportedPost.setFlag(true);
                reportedPost.addFlagDescription(report.getText().toString());
                reportedPost.incrementFlagCount();
                confirmAction.apply(null);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showChangeUsernameDialog(User user, Activity activity, Function confirmAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View popUpView = activity.getLayoutInflater().inflate(R.layout.edit_username_popup, null);
        TextView oldUsername = popUpView.findViewById(R.id.old_username);
        oldUsername.setText(user.getUsername());
        EditText newUsername = popUpView.findViewById(R.id.new_username);


        builder.setView(popUpView);

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String u = newUsername.getText().toString();
                if (u.length() > 5){
                    user.setUsername(u);
                    confirmAction.apply(null);
                }
                else
                    Toast.makeText(activity,"at least 5 characters",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showChangePasswordDialog(FirebaseUser currentUser, FragmentActivity activity, Function editPasswordConfirmationAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View popUpView = activity.getLayoutInflater().inflate(R.layout.edit_password_popup, null);
        EditText oldPassword = popUpView.findViewById(R.id.old_password);
        ImageButton showOldPassBtn = popUpView.findViewById(R.id.show_old_pass_btn);
        showOldPassBtn.setOnClickListener(v -> {
            if (oldPassword.getInputType() == 129){
                oldPassword.setInputType(1);
                showOldPassBtn.setImageResource(R.drawable.ic_baseline_show_password_24);
            }
            else{
                oldPassword.setInputType(129);
                showOldPassBtn.setImageResource(R.drawable.ic_baseline_hide_password_24);
            }

        });

        EditText newPassword = popUpView.findViewById(R.id.new_password);
        ImageButton showNewPassBtn = popUpView.findViewById(R.id.show_new_pass_btn);
        showNewPassBtn.setOnClickListener(v -> {
            if (newPassword.getInputType() == 129){
                newPassword.setInputType(1);
                showNewPassBtn.setImageResource(R.drawable.ic_baseline_show_password_24);
            }
            else{
                newPassword.setInputType(129);
                showNewPassBtn.setImageResource(R.drawable.ic_baseline_hide_password_24);
            }
        });

        builder.setView(popUpView);

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final String email = currentUser.getEmail();
                final String oldPass = oldPassword.getText().toString();
                final String newPass = newPassword.getText().toString();

                AuthCredential credential = EmailAuthProvider.getCredential(email,oldPass);

                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(activity,"Failed to change your password",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(activity,"Password Successfully Modified",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(activity,"Authentication Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}