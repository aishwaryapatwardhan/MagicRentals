package project.team.cmpe277.com.magicrentals1.landlord;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import project.team.cmpe277.com.magicrentals1.R;

/**
 * Created by savani on 5/7/16.
 */
public  class TextValidator implements TextWatcher {

    private final EditText editText;
    private static final String REQUIRED = "required";
public TextValidator(EditText editText) {
        this.editText = editText;
        }



@Override
final public void afterTextChanged(Editable s) {
    String text = editText.getText().toString();
    editText.setError(null);

    // length 0 means there is no text
    if (text.length() == 0) {
        System.out.println("validationnnn  required ");
        editText.setError(REQUIRED);
    }
}




@Override
final public void beforeTextChanged(CharSequence s, int start, int count, int after) {

}

@Override
final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
