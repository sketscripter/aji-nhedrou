package com.example.ajinhedrou.login.Animation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;

public class BottomDialogFrag extends BottomSheetDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }

}
